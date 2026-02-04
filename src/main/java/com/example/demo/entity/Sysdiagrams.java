package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "sysdiagrams")
public class Sysdiagrams {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "principal_id", nullable = false)
    private Integer principalID;

    @Column(name = "diagram_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diagramID;

    @Column(name = "version", nullable = true)
    private Integer version;

    @Column(name = "definition", nullable = true)
    private byte[] definition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrincipalID() {
        return principalID;
    }

    public void setPrincipalID(Integer principalID) {
        this.principalID = principalID;
    }

    public Integer getDiagramID() {
        return diagramID;
    }

    public void setDiagramID(Integer diagramID) {
        this.diagramID = diagramID;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public byte[] getDefinition() {
        return definition;
    }

    public void setDefinition(byte[] definition) {
        this.definition = definition;
    }

}
