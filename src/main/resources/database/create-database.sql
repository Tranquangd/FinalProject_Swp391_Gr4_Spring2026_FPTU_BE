-- =============================================
-- CREATE DATABASE AND TABLES
-- =============================================

-- Tạo database (chạy riêng nếu chưa có)
-- Nếu database đã tồn tại, bỏ qua dòng này
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'SWP391_Tool')
BEGIN
    CREATE DATABASE SWP391_Tool;
END
GO

USE SWP391_Tool;
GO

-- KHỐI 1: QUẢN TRỊ HỆ THỐNG (ACADEMIC STRUCTURE)
-- 1. Semesters: Quản lý học kỳ
CREATE TABLE Semesters (
    SemesterID INT IDENTITY(1,1) PRIMARY KEY,
    SemesterName NVARCHAR(50) NOT NULL, -- Ví dụ: Spring 2026
    StartDate DATE,
    EndDate DATE
);

-- 2. Courses: Quản lý môn học/lớp học
CREATE TABLE Courses (
    CourseID INT IDENTITY(1,1) PRIMARY KEY,
    SemesterID INT FOREIGN KEY REFERENCES Semesters(SemesterID),
    CourseCode NVARCHAR(20) NOT NULL, -- Ví dụ: SWP391_SE1709
    CourseName NVARCHAR(100),
    LecturerID INT -- Sẽ link tới User sau
);

-- 3. Users: Quản lý người dùng chung
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

-- Link Lecturer vào Course sau khi có bảng User
ALTER TABLE Courses ADD CONSTRAINT FK_Courses_Lecturer FOREIGN KEY (LecturerID) REFERENCES Users(UserID);

-- 4. Projects: Nhóm sinh viên
CREATE TABLE Projects (
    ProjectID INT IDENTITY(1,1) PRIMARY KEY,
    CourseID INT FOREIGN KEY REFERENCES Courses(CourseID),
    GroupName NVARCHAR(50), -- Ví dụ: SE1709_Group1
    ProjectName NVARCHAR(200),
    Description NVARCHAR(MAX),
    ProjectStatus NVARCHAR(20) DEFAULT 'IN_PROGRESS', -- IN_PROGRESS, COMPLETED
    CreatedAt DATETIME DEFAULT GETDATE()
);

-- 5. ProjectMembers: Phân quyền trong nhóm
CREATE TABLE ProjectMembers (
    ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    Role NVARCHAR(20) CHECK (Role IN ('LEADER', 'MEMBER')),
    IsActive BIT DEFAULT 1,
    PRIMARY KEY (ProjectID, UserID)
);

-- =============================================
-- KHỐI 2: CẤU HÌNH & TÍCH HỢP (CONFIGURATION)
-- =============================================

-- 6. ProjectConfigs: Chứa Token bí mật (Tách riêng để bảo mật)
CREATE TABLE ProjectConfigs (
    ConfigID INT IDENTITY(1,1) PRIMARY KEY,
    ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID) UNIQUE,
    JiraUrl NVARCHAR(255),          -- https://group1.atlassian.net
    JiraProjectKey NVARCHAR(50),    -- KEY: SWP
    JiraEmail NVARCHAR(100),        -- Email dùng tạo Token
    JiraApiToken NVARCHAR(MAX),     -- Token bí mật
    GitHubToken NVARCHAR(MAX)       -- Token GitHub (Personal Access Token)
);

-- 7. ProjectRepositories: Một nhóm có thể có nhiều Repo (BE, FE, Mobile)
CREATE TABLE ProjectRepositories (
    RepoID INT IDENTITY(1,1) PRIMARY KEY,
    ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
    RepoName NVARCHAR(100),         -- Tên repo
    RepoOwner NVARCHAR(100),        -- Chủ sở hữu (Organization hoặc User)
    RepoUrl NVARCHAR(500)
);

-- 8. ProjectSprints: Đồng bộ Sprint từ Jira để tính KPI theo giai đoạn
CREATE TABLE ProjectSprints (
    SprintID INT IDENTITY(1,1) PRIMARY KEY,
    ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
    JiraSprintId INT, -- ID gốc bên Jira
    SprintName NVARCHAR(100),
    StartDate DATETIME,
    EndDate DATETIME,
    SprintState NVARCHAR(20) -- future, active, closed
);

-- =============================================
-- KHỐI 3: DỮ LIỆU ĐỒNG BỘ (SYNC DATA LAYER)
-- =============================================

-- 9. JiraIssues: Lưu yêu cầu (Requirement)
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

-- 10. GitHubCommits: Lưu lịch sử Code
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

-- =============================================
-- KHỐI 4: BÁO CÁO & XUẤT BẢN (OUTPUT)
-- =============================================

-- 11. AnomalyReports: Bảng lưu các cảnh báo "Bất thường"
CREATE TABLE AnomalyReports (
    ReportID INT IDENTITY(1,1) PRIMARY KEY,
    ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
    DetectedDate DATETIME DEFAULT GETDATE(),
    
    IssueType NVARCHAR(50), -- NO_COMMIT_7_DAYS, IMBALANCED_WORK, MISSING_SRS_REQ
    Description NVARCHAR(MAX),
    Severity NVARCHAR(20), -- HIGH, MEDIUM, LOW
    RelatedUserID INT FOREIGN KEY REFERENCES Users(UserID) -- Nếu lỗi do cá nhân
);

-- 12. SRSDocuments: Lưu lịch sử xuất file SRS
CREATE TABLE SRSDocuments (
    DocID INT IDENTITY(1,1) PRIMARY KEY,
    ProjectID INT FOREIGN KEY REFERENCES Projects(ProjectID),
    
    VersionNumber NVARCHAR(20), -- v1.0
    ExportedBy INT FOREIGN KEY REFERENCES Users(UserID),
    ExportedDate DATETIME DEFAULT GETDATE(),
    FileLink NVARCHAR(500), -- Link file trên GitHub hoặc Local
    SnapshotSummary NVARCHAR(MAX) -- JSON tóm tắt (Có bao nhiêu Req tại thời điểm export)
);
