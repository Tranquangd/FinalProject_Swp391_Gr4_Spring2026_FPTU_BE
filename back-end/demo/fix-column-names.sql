-- Script to fix column names in Users table
-- This script renames columns from snake_case to PascalCase to match the entity

USE SWP391_Tool;
GO

-- First, check current column names
PRINT 'Current column names:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'Users'
ORDER BY ORDINAL_POSITION;
GO

-- Rename columns from snake_case to PascalCase
-- Note: SQL Server is case-insensitive by default, but we need exact match for Hibernate

-- Check if columns exist before renaming
IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Users' AND COLUMN_NAME = 'avatar_url')
BEGIN
    EXEC sp_rename 'Users.avatar_url', 'AvatarUrl', 'COLUMN';
    PRINT 'Renamed avatar_url to AvatarUrl';
END

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Users' AND COLUMN_NAME = 'full_name')
BEGIN
    EXEC sp_rename 'Users.full_name', 'FullName', 'COLUMN';
    PRINT 'Renamed full_name to FullName';
END

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Users' AND COLUMN_NAME = 'password_hash')
BEGIN
    EXEC sp_rename 'Users.password_hash', 'PasswordHash', 'COLUMN';
    PRINT 'Renamed password_hash to PasswordHash';
END

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Users' AND COLUMN_NAME = 'jira_account_id')
BEGIN
    EXEC sp_rename 'Users.jira_account_id', 'JiraAccountId', 'COLUMN';
    PRINT 'Renamed jira_account_id to JiraAccountId';
END

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Users' AND COLUMN_NAME = 'git_hub_username')
BEGIN
    EXEC sp_rename 'Users.git_hub_username', 'GitHubUsername', 'COLUMN';
    PRINT 'Renamed git_hub_username to GitHubUsername';
END

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Users' AND COLUMN_NAME = 'is_active')
BEGIN
    EXEC sp_rename 'Users.is_active', 'IsActive', 'COLUMN';
    PRINT 'Renamed is_active to IsActive';
END

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Users' AND COLUMN_NAME = 'userid')
BEGIN
    EXEC sp_rename 'Users.userid', 'UserID', 'COLUMN';
    PRINT 'Renamed userid to UserID';
END

-- Verify the changes
PRINT 'Updated column names:';
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'Users'
ORDER BY ORDINAL_POSITION;
GO
