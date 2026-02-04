-- =============================================
-- CREATE DATABASE AND TABLES (SAFE VERSION)
-- Script này kiểm tra bảng đã tồn tại chưa trước khi tạo
-- Không mất dữ liệu nếu bảng đã có
-- =============================================

-- Tạo database (chạy riêng nếu chưa có)
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'SWP391_Tool')
BEGIN
    CREATE DATABASE SWP391_Tool;
END
GO

USE SWP391_Tool;
GO

-- KHỐI 1: QUẢN TRỊ HỆ THỐNG (ACADEMIC STRUCTURE)
-- 1. Semesters: Quản lý học kỳ
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Semesters')
BEGIN
    CREATE TABLE Semesters (
        SemesterID INT IDENTITY(1,1) PRIMARY KEY,
        SemesterName NVARCHAR(50) NOT NULL, -- Ví dụ: Spring 2026
        StartDate DATE,
        EndDate DATE
    );
    PRINT 'Table Semesters created successfully';
END
ELSE
BEGIN
    PRINT 'Table Semesters already exists';
END
GO

-- 2. Courses: Quản lý môn học/lớp học
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Courses')
BEGIN
    CREATE TABLE Courses (
        CourseID INT IDENTITY(1,1) PRIMARY KEY,
        SemesterID INT FOREIGN KEY REFERENCES Semesters(SemesterID),
        CourseCode NVARCHAR(20) NOT NULL, -- Ví dụ: SWP391_SE1709
        CourseName NVARCHAR(100),
        LecturerID INT -- Sẽ link tới User sau
    );
    PRINT 'Table Courses created successfully';
END
ELSE
BEGIN
    PRINT 'Table Courses already exists';
END
GO

-- 3. Users: Quản lý người dùng chung
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Users')
BEGIN
    CREATE TABLE Users (
        UserID INT IDENTITY(1,1) PRIMARY KEY,
        FullName NVARCHAR(100) NOT NULL,
        Email NVARCHAR(100) UNIQUE NOT NULL,
        PasswordHash NVARCHAR(255), 
        Role NVARCHAR(20) CHECK (Role IN ('ADMIN', 'LECTURER', 'STUDENT')),
        
        -- Mapping Identity: Cực quan trọng để biết ai là ai trên Jira/Git
        JiraAccountId NVARCHAR(100), -- ID định danh của Jira
        GitHubUsername NVARCHAR(100), -- Username GitHub
        AvatarUrl NVARCHAR(500),
        IsActive BIT DEFAULT 1
    );
    PRINT 'Table Users created successfully';
END
ELSE
BEGIN
    PRINT 'Table Users already exists';
END
GO

-- Link Lecturer vào Course sau khi có bảng User
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Courses_Lecturer')
BEGIN
    ALTER TABLE Courses ADD CONSTRAINT FK_Courses_Lecturer FOREIGN KEY (LecturerID) REFERENCES Users(UserID);
    PRINT 'Foreign key FK_Courses_Lecturer created successfully';
END
ELSE
BEGIN
    PRINT 'Foreign key FK_Courses_Lecturer already exists';
END
GO

-- 4. Projects: Nhóm sinh viên
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Projects')
BEGIN
    CREATE TABLE Projects (
        ProjectID INT IDENTITY(1,1) PRIMARY KEY,
        CourseID INT FOREIGN KEY REFERENCES Courses(CourseID),
        GroupName NVARCHAR(50), -- Ví dụ: SE1709_Group1
        ProjectName NVARCHAR(200),
        Description NVARCHAR(MAX),
        ProjectStatus NVARCHAR(20) DEFAULT 'IN_PROGRESS', -- IN_PROGRESS, COMPLETED
        CreatedAt DATETIME DEFAULT GETDATE()
    );
    PRINT 'Table Projects created successfully';
END
ELSE
BEGIN
    PRINT 'Table Projects already exists';
END
GO

-- 5. ProjectMembers: Phân quyền trong nhóm
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ProjectMembers')
BEGIN
    CREATE TABLE ProjectMembers (
        ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
        UserID INT FOREIGN KEY REFERENCES Users(UserID),
        Role NVARCHAR(20) CHECK (Role IN ('LEADER', 'MEMBER')),
        IsActive BIT DEFAULT 1,
        PRIMARY KEY (ProjectID, UserID)
    );
    PRINT 'Table ProjectMembers created successfully';
END
ELSE
BEGIN
    PRINT 'Table ProjectMembers already exists';
END
GO

-- =============================================
-- KHỐI 2: CẤU HÌNH & TÍCH HỢP (CONFIGURATION)
-- =============================================

-- 6. ProjectConfigs: Chứa Token bí mật (Tách riêng để bảo mật)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ProjectConfigs')
BEGIN
    CREATE TABLE ProjectConfigs (
        ConfigID INT IDENTITY(1,1) PRIMARY KEY,
        ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID) UNIQUE,
        JiraUrl NVARCHAR(255),          -- https://group1.atlassian.net
        JiraProjectKey NVARCHAR(50),    -- KEY: SWP
        JiraEmail NVARCHAR(100),        -- Email dùng tạo Token
        JiraApiToken NVARCHAR(MAX),     -- Token bí mật
        GitHubToken NVARCHAR(MAX)       -- Token GitHub (Personal Access Token)
    );
    PRINT 'Table ProjectConfigs created successfully';
END
ELSE
BEGIN
    PRINT 'Table ProjectConfigs already exists';
END
GO

-- 7. ProjectRepositories: Một nhóm có thể có nhiều Repo (BE, FE, Mobile)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ProjectRepositories')
BEGIN
    CREATE TABLE ProjectRepositories (
        RepoID INT IDENTITY(1,1) PRIMARY KEY,
        ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
        RepoName NVARCHAR(100),         -- Tên repo
        RepoOwner NVARCHAR(100),        -- Chủ sở hữu (Organization hoặc User)
        RepoUrl NVARCHAR(500)
    );
    PRINT 'Table ProjectRepositories created successfully';
END
ELSE
BEGIN
    PRINT 'Table ProjectRepositories already exists';
END
GO

-- 8. ProjectSprints: Đồng bộ Sprint từ Jira để tính KPI theo giai đoạn
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'ProjectSprints')
BEGIN
    CREATE TABLE ProjectSprints (
        SprintID INT IDENTITY(1,1) PRIMARY KEY,
        ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
        JiraSprintId INT, -- ID gốc bên Jira
        SprintName NVARCHAR(100),
        StartDate DATETIME,
        EndDate DATETIME,
        SprintState NVARCHAR(20) -- future, active, closed
    );
    PRINT 'Table ProjectSprints created successfully';
END
ELSE
BEGIN
    PRINT 'Table ProjectSprints already exists';
END
GO

-- =============================================
-- KHỐI 3: DỮ LIỆU ĐỒNG BỘ (SYNC DATA LAYER)
-- =============================================

-- 9. JiraIssues: Lưu yêu cầu (Requirement)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'JiraIssues')
BEGIN
    CREATE TABLE JiraIssues (
        IssueID INT IDENTITY(1,1) PRIMARY KEY,
        ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
        SprintID INT FOREIGN KEY REFERENCES ProjectSprints(SprintID), -- Issue thuộc sprint nào
        
        JiraKey NVARCHAR(20),     -- Ví dụ: SWP-12
        IssueType NVARCHAR(50),   -- Epic, Story, Task
        Summary NVARCHAR(MAX),    -- Tiêu đề
        Description NVARCHAR(MAX),
        AcceptanceCriteria NVARCHAR(MAX), -- Cần parse từ Jira Description hoặc Custom Field
        StoryPoint INT,
        Priority NVARCHAR(20),
        Status NVARCHAR(50),      -- To Do, In Progress, Done
        AssigneeJiraID NVARCHAR(100), -- Map với User.JiraAccountId
        
        LastSyncDate DATETIME DEFAULT GETDATE()
    );
    PRINT 'Table JiraIssues created successfully';
END
ELSE
BEGIN
    PRINT 'Table JiraIssues already exists';
END
GO

-- 10. GitHubCommits: Lưu lịch sử Code
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'GitHubCommits')
BEGIN
    CREATE TABLE GitHubCommits (
        CommitID INT IDENTITY(1,1) PRIMARY KEY,
        RepoID INT FOREIGN KEY REFERENCES ProjectRepositories(RepoID),
        
        SHA NVARCHAR(100), -- Mã Hash của commit
        CommitterName NVARCHAR(100), -- Tên người commit trên Git
        CommitterEmail NVARCHAR(100),
        CommitMessage NVARCHAR(MAX),
        Additions INT, -- Số dòng code thêm
        Deletions INT, -- Số dòng code xóa
        CommitDate DATETIME,
        CommitUrl NVARCHAR(500)
    );
    PRINT 'Table GitHubCommits created successfully';
END
ELSE
BEGIN
    PRINT 'Table GitHubCommits already exists';
END
GO

-- =============================================
-- KHỐI 4: BÁO CÁO & XUẤT BẢN (OUTPUT)
-- =============================================

-- 11. AnomalyReports: Bảng lưu các cảnh báo "Bất thường"
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'AnomalyReports')
BEGIN
    CREATE TABLE AnomalyReports (
        ReportID INT IDENTITY(1,1) PRIMARY KEY,
        ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
        DetectedDate DATETIME DEFAULT GETDATE(),
        
        IssueType NVARCHAR(50), -- NO_COMMIT_7_DAYS, IMBALANCED_WORK, MISSING_SRS_REQ
        Description NVARCHAR(MAX),
        Severity NVARCHAR(20), -- HIGH, MEDIUM, LOW
        RelatedUserID INT FOREIGN KEY REFERENCES Users(UserID) -- Nếu lỗi do cá nhân
    );
    PRINT 'Table AnomalyReports created successfully';
END
ELSE
BEGIN
    PRINT 'Table AnomalyReports already exists';
END
GO

-- 12. SRSDocuments: Lưu lịch sử xuất file SRS
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'SRSDocuments')
BEGIN
    CREATE TABLE SRSDocuments (
        DocID INT IDENTITY(1,1) PRIMARY KEY,
        ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
        
        VersionNumber NVARCHAR(20), -- v1.0
        ExportedBy INT FOREIGN KEY REFERENCES Users(UserID),
        ExportedDate DATETIME DEFAULT GETDATE(),
        FileLink NVARCHAR(500), -- Link file trên GitHub hoặc Local
        SnapshotSummary NVARCHAR(MAX) -- JSON tóm tắt (Có bao nhiêu Req tại thời điểm export)
    );
    PRINT 'Table SRSDocuments created successfully';
END
ELSE
BEGIN
    PRINT 'Table SRSDocuments already exists';
END
GO

PRINT '========================================';
PRINT 'Database setup completed!';
PRINT '========================================';
