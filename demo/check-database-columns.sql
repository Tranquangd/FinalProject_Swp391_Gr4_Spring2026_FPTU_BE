-- Script to check and fix column names in Users table
USE SWP391_Tool;
GO

-- Check current column names
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'Users'
ORDER BY ORDINAL_POSITION;
GO

-- If column names are wrong, you can rename them:
-- ALTER TABLE Users RENAME COLUMN avatar_url TO AvatarUrl;
-- (Note: SQL Server uses different syntax, see below)

-- SQL Server syntax to rename column (if needed):
-- EXEC sp_rename 'Users.avatar_url', 'AvatarUrl', 'COLUMN';
-- EXEC sp_rename 'Users.git_hub_username', 'GitHubUsername', 'COLUMN';
-- EXEC sp_rename 'Users.jira_account_id', 'JiraAccountId', 'COLUMN';
-- EXEC sp_rename 'Users.full_name', 'FullName', 'COLUMN';
-- EXEC sp_rename 'Users.password_hash', 'PasswordHash', 'COLUMN';
-- EXEC sp_rename 'Users.is_active', 'IsActive', 'COLUMN';
