package com.example.demo.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to generate JPA entities from existing database schema
 * Similar to .NET scaffold command
 * 
 * Usage: Run this class as a Java application
 */
public class EntityGenerator {
    
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=SWP391_Tool;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "123";
    private static final String PACKAGE_NAME = "com.example.demo.entity";
    private static final String OUTPUT_DIR = "src/main/java/com/example/demo/entity";
    
    public static void main(String[] args) {
        EntityGenerator generator = new EntityGenerator();
        try {
            generator.generateEntities();
            System.out.println("✅ Entities generated successfully!");
            System.out.println("📁 Location: " + new File(OUTPUT_DIR).getAbsolutePath());
        } catch (Exception e) {
            System.err.println("❌ Error generating entities: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void generateEntities() throws SQLException, IOException {
        // Create output directory - use absolute path from project root
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            boolean created = outputDir.mkdirs();
            if (!created && !outputDir.exists()) {
                // Try relative to current working directory
                File currentDir = new File(System.getProperty("user.dir"));
                File projectRoot = currentDir.getName().equals("demo") ? currentDir : new File(currentDir, "demo");
                outputDir = new File(projectRoot, OUTPUT_DIR);
                outputDir.mkdirs();
            }
        }
        
        System.out.println("📂 Output directory: " + outputDir.getAbsolutePath());
        
        // Connect to database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            DatabaseMetaData metaData = conn.getMetaData();
            
            // Get all tables
            List<String> tables = getTables(metaData);
            
            System.out.println("Found " + tables.size() + " tables:");
            
            // Generate entity for each table
            for (String tableName : tables) {
                System.out.println("Generating entity for table: " + tableName);
                generateEntity(metaData, tableName, outputDir);
            }
        }
    }
    
    private List<String> getTables(DatabaseMetaData metaData) throws SQLException {
        List<String> tables = new ArrayList<>();
        try (ResultSet rs = metaData.getTables(null, "dbo", null, new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
            }
        }
        return tables;
    }
    
    private void generateEntity(DatabaseMetaData metaData, String tableName, File outputDir) 
            throws SQLException, IOException {
        
        String className = toPascalCase(tableName);
        String fileName = className + ".java";
        File entityFile = new File(outputDir, fileName);
        
        try (FileWriter writer = new FileWriter(entityFile)) {
            // Package declaration
            writer.write("package " + PACKAGE_NAME + ";\n\n");
            
            // Imports
            writer.write("import jakarta.persistence.*;\n");
            writer.write("import java.time.*;\n");
            writer.write("import java.math.BigDecimal;\n");
            writer.write("import java.util.List;\n");
            writer.write("import java.util.ArrayList;\n\n");
            
            // Class declaration
            writer.write("@Entity\n");
            writer.write("@Table(name = \"" + tableName + "\")\n");
            writer.write("public class " + className + " {\n\n");
            
            // Get columns
            List<ColumnInfo> columns = getColumns(metaData, tableName);
            
            // Generate fields
            for (ColumnInfo column : columns) {
                writer.write("    @Column(name = \"" + column.name + "\"");
                if (column.nullable) {
                    writer.write(", nullable = true");
                } else {
                    writer.write(", nullable = false");
                }
                writer.write(")\n");
                
                if (column.isPrimaryKey) {
                    writer.write("    @Id\n");
                    if (column.isAutoIncrement) {
                        writer.write("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                    }
                }
                
                writer.write("    private " + column.javaType + " " + toCamelCase(column.name) + ";\n\n");
            }
            
            // Generate relationships (Foreign Keys)
            generateRelationships(metaData, tableName, writer);
            
            // Generate getters and setters
            for (ColumnInfo column : columns) {
                String fieldName = toCamelCase(column.name);
                String capitalizedName = capitalize(fieldName);
                
                // Getter
                writer.write("    public " + column.javaType + " get" + capitalizedName + "() {\n");
                writer.write("        return " + fieldName + ";\n");
                writer.write("    }\n\n");
                
                // Setter
                writer.write("    public void set" + capitalizedName + "(" + column.javaType + " " + fieldName + ") {\n");
                writer.write("        this." + fieldName + " = " + fieldName + ";\n");
                writer.write("    }\n\n");
            }
            
            writer.write("}\n");
        }
        
        System.out.println("  ✓ Generated: " + fileName);
    }
    
    private void generateRelationships(DatabaseMetaData metaData, String tableName, FileWriter writer) 
            throws SQLException, IOException {
        // Get foreign keys
        try (ResultSet rs = metaData.getImportedKeys(null, "dbo", tableName)) {
            while (rs.next()) {
                String fkColumnName = rs.getString("FKCOLUMN_NAME");
                String pkTableName = rs.getString("PKTABLE_NAME");
                String pkColumnName = rs.getString("PKCOLUMN_NAME");
                
                // Determine relationship type (OneToMany, ManyToOne, etc.)
                // For simplicity, we'll generate ManyToOne relationships
                String relationshipName = toCamelCase(pkTableName);
                String relationshipType = toPascalCase(pkTableName);
                
                writer.write("    @ManyToOne\n");
                writer.write("    @JoinColumn(name = \"" + fkColumnName + "\", referencedColumnName = \"" + pkColumnName + "\")\n");
                writer.write("    private " + relationshipType + " " + relationshipName + ";\n\n");
            }
        }
    }
    
    private List<ColumnInfo> getColumns(DatabaseMetaData metaData, String tableName) throws SQLException {
        List<ColumnInfo> columns = new ArrayList<>();
        
        // Get primary keys
        List<String> primaryKeys = new ArrayList<>();
        try (ResultSet rs = metaData.getPrimaryKeys(null, "dbo", tableName)) {
            while (rs.next()) {
                primaryKeys.add(rs.getString("COLUMN_NAME"));
            }
        }
        
        // Get all columns
        try (ResultSet rs = metaData.getColumns(null, "dbo", tableName, null)) {
            while (rs.next()) {
                ColumnInfo column = new ColumnInfo();
                column.name = rs.getString("COLUMN_NAME");
                column.type = rs.getInt("DATA_TYPE");
                column.typeName = rs.getString("TYPE_NAME");
                column.nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                column.isPrimaryKey = primaryKeys.contains(column.name);
                column.isAutoIncrement = "YES".equals(rs.getString("IS_AUTOINCREMENT"));
                column.javaType = mapToJavaType(column.type, column.typeName);
                
                columns.add(column);
            }
        }
        
        return columns;
    }
    
    private String mapToJavaType(int sqlType, String typeName) {
        switch (sqlType) {
            case Types.BIT:
            case Types.BOOLEAN:
                return "Boolean";
            case Types.TINYINT:
            case Types.SMALLINT:
                return "Integer";
            case Types.INTEGER:
                return "Integer";
            case Types.BIGINT:
                return "Long";
            case Types.FLOAT:
            case Types.REAL:
                return "Float";
            case Types.DOUBLE:
                return "Double";
            case Types.NUMERIC:
            case Types.DECIMAL:
                return "BigDecimal";
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.LONGNVARCHAR:
                return "String";
            case Types.DATE:
                return "LocalDate";
            case Types.TIME:
                return "LocalTime";
            case Types.TIMESTAMP:
            case Types.TIMESTAMP_WITH_TIMEZONE:
                if (typeName != null && typeName.toLowerCase().contains("datetime2")) {
                    return "LocalDateTime";
                }
                return "LocalDateTime";
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                return "byte[]";
            default:
                return "String";
        }
    }
    
    private String toPascalCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        
        String[] parts = name.split("_");
        StringBuilder result = new StringBuilder();
        
        for (String part : parts) {
            if (!part.isEmpty()) {
                // Handle special cases: GitHub, Jira, SRS, API, URL, ID
                if (part.equalsIgnoreCase("github")) {
                    result.append("GitHub");
                } else if (part.equalsIgnoreCase("jira")) {
                    result.append("Jira");
                } else if (part.equalsIgnoreCase("srs")) {
                    result.append("SRS");
                } else if (part.equalsIgnoreCase("api")) {
                    result.append("API");
                } else if (part.equalsIgnoreCase("url")) {
                    result.append("URL");
                } else if (part.equalsIgnoreCase("id")) {
                    result.append("ID");
                } else {
                    // Normal PascalCase conversion
                    result.append(Character.toUpperCase(part.charAt(0)));
                    if (part.length() > 1) {
                        result.append(part.substring(1).toLowerCase());
                    }
                }
            }
        }
        return result.toString();
    }
    
    private String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        String pascal = toPascalCase(name);
        return Character.toLowerCase(pascal.charAt(0)) + pascal.substring(1);
    }
    
    private String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    
    private static class ColumnInfo {
        String name;
        int type;
        String typeName;
        boolean nullable;
        boolean isPrimaryKey;
        boolean isAutoIncrement;
        String javaType;
    }
}
