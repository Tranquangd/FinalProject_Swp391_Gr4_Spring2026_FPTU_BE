-- =============================================
-- INSERT SAMPLE DATA
-- =============================================

USE SWP391_Tool;
GO

-- 1. Insert Semesters
INSERT INTO Semesters (SemesterName, StartDate, EndDate) VALUES
('Spring 2026', '2026-01-15', '2026-05-15'),
('Fall 2025', '2025-09-01', '2025-12-20');
GO

-- 2. Insert Users (Admin, Lecturers, Students)
INSERT INTO Users (FullName, Email, PasswordHash, Role, JiraAccountId, GitHubUsername, AvatarUrl, IsActive) VALUES
-- Admin
('System Administrator', 'admin@fpt.edu.vn', '$2a$10$example', 'ADMIN', NULL, 'admin', NULL, 1),
-- Lecturers
('Dr. Nguyen Van A', 'lecturer1@fpt.edu.vn', '$2a$10$example', 'LECTURER', 'lecturer1-jira-id', 'lecturer1', NULL, 1),
('Dr. Tran Thi B', 'lecturer2@fpt.edu.vn', '$2a$10$example', 'LECTURER', 'lecturer2-jira-id', 'lecturer2', NULL, 1),
-- Students
('Le Van C', 'student1@fpt.edu.vn', '$2a$10$example', 'STUDENT', 'student1-jira-id', 'student1', NULL, 1),
('Pham Thi D', 'student2@fpt.edu.vn', '$2a$10$example', 'STUDENT', 'student2-jira-id', 'student2', NULL, 1),
('Hoang Van E', 'student3@fpt.edu.vn', '$2a$10$example', 'STUDENT', 'student3-jira-id', 'student3', NULL, 1),
('Nguyen Thi F', 'student4@fpt.edu.vn', '$2a$10$example', 'STUDENT', 'student4-jira-id', 'student4', NULL, 1),
('Tran Van G', 'student5@fpt.edu.vn', '$2a$10$example', 'STUDENT', 'student5-jira-id', 'student5', NULL, 1),
('Le Thi H', 'student6@fpt.edu.vn', '$2a$10$example', 'STUDENT', 'student6-jira-id', 'student6', NULL, 1);
GO

-- 3. Insert Courses
INSERT INTO Courses (SemesterID, CourseCode, CourseName, LecturerID) VALUES
(1, 'SWP391_SE1709', 'Software Engineering Project', 2),
(1, 'SWP391_SE1710', 'Software Engineering Project', 2),
(1, 'SWP391_SE1711', 'Software Engineering Project', 3);
GO

-- 4. Insert Projects
INSERT INTO Projects (CourseID, GroupName, ProjectName, Description, ProjectStatus, CreatedAt) VALUES
(1, 'SE1709_Group1', 'E-Learning Platform', 'Hệ thống học trực tuyến với đầy đủ tính năng quản lý khóa học, bài giảng, và đánh giá', 'IN_PROGRESS', GETDATE()),
(1, 'SE1709_Group2', 'E-Commerce System', 'Hệ thống thương mại điện tử với quản lý sản phẩm, đơn hàng, thanh toán', 'IN_PROGRESS', GETDATE()),
(1, 'SE1709_Group3', 'Hospital Management System', 'Hệ thống quản lý bệnh viện với quản lý bệnh nhân, lịch hẹn, hồ sơ y tế', 'IN_PROGRESS', GETDATE()),
(2, 'SE1710_Group1', 'Library Management System', 'Hệ thống quản lý thư viện với mượn trả sách, quản lý thành viên', 'IN_PROGRESS', GETDATE());
GO

-- 5. Insert ProjectMembers
INSERT INTO ProjectMembers (ProjectID, UserID, Role, IsActive) VALUES
-- Group 1 members
(1, 4, 'LEADER', 1),
(1, 5, 'MEMBER', 1),
(1, 6, 'MEMBER', 1),
-- Group 2 members
(2, 7, 'LEADER', 1),
(2, 8, 'MEMBER', 1),
(2, 9, 'MEMBER', 1),
-- Group 3 members
(3, 4, 'LEADER', 1),
(3, 5, 'MEMBER', 1),
-- Group 4 members
(4, 6, 'LEADER', 1),
(4, 7, 'MEMBER', 1);
GO

-- 6. Insert ProjectConfigs
INSERT INTO ProjectConfigs (ProjectID, JiraUrl, JiraProjectKey, JiraEmail, JiraApiToken, GitHubToken) VALUES
(1, 'https://se1709group1.atlassian.net', 'SWP', 'group1@fpt.edu.vn', 'ATATT3xFfGF0...', 'ghp_example_token_1'),
(2, 'https://se1709group2.atlassian.net', 'SWP', 'group2@fpt.edu.vn', 'ATATT3xFfGF0...', 'ghp_example_token_2'),
(3, 'https://se1709group3.atlassian.net', 'SWP', 'group3@fpt.edu.vn', 'ATATT3xFfGF0...', 'ghp_example_token_3');
GO

-- 7. Insert ProjectRepositories
INSERT INTO ProjectRepositories (ProjectID, RepoName, RepoOwner, RepoUrl) VALUES
(1, 'elearning-backend', 'SE1709-Group1', 'https://github.com/SE1709-Group1/elearning-backend'),
(1, 'elearning-frontend', 'SE1709-Group1', 'https://github.com/SE1709-Group1/elearning-frontend'),
(2, 'ecommerce-backend', 'SE1709-Group2', 'https://github.com/SE1709-Group2/ecommerce-backend'),
(2, 'ecommerce-frontend', 'SE1709-Group2', 'https://github.com/SE1709-Group2/ecommerce-frontend'),
(3, 'hospital-backend', 'SE1709-Group3', 'https://github.com/SE1709-Group3/hospital-backend');
GO

-- 8. Insert ProjectSprints
INSERT INTO ProjectSprints (ProjectID, JiraSprintId, SprintName, StartDate, EndDate, SprintState) VALUES
(1, 1, 'Sprint 1 - Planning & Setup', '2026-01-15', '2026-01-29', 'closed'),
(1, 2, 'Sprint 2 - Core Features', '2026-01-30', '2026-02-12', 'active'),
(2, 3, 'Sprint 1 - Project Setup', '2026-01-15', '2026-01-29', 'closed'),
(2, 4, 'Sprint 2 - User Management', '2026-01-30', '2026-02-12', 'active');
GO

-- 9. Insert JiraIssues
INSERT INTO JiraIssues (ProjectID, SprintID, JiraKey, IssueType, Summary, Description, AcceptanceCriteria, StoryPoint, Priority, Status, AssigneeJiraID, LastSyncDate) VALUES
(1, 1, 'SWP-1', 'Epic', 'User Authentication System', 'Xây dựng hệ thống xác thực người dùng với đăng nhập, đăng ký, quên mật khẩu', 'User có thể đăng ký, đăng nhập, và khôi phục mật khẩu thành công', 8, 'High', 'Done', 'student1-jira-id', GETDATE()),
(1, 1, 'SWP-2', 'Story', 'Login Feature', 'Cho phép người dùng đăng nhập vào hệ thống', 'User nhập email và password, hệ thống xác thực và cho phép đăng nhập', 3, 'High', 'Done', 'student1-jira-id', GETDATE()),
(1, 2, 'SWP-3', 'Story', 'Course Management', 'Quản lý khóa học: tạo, sửa, xóa khóa học', 'Admin có thể tạo, chỉnh sửa và xóa khóa học', 5, 'High', 'In Progress', 'student2-jira-id', GETDATE()),
(1, 2, 'SWP-4', 'Task', 'Design Database Schema', 'Thiết kế schema database cho module quản lý khóa học', 'Schema được thiết kế và tạo thành công', 2, 'Medium', 'Done', 'student3-jira-id', GETDATE()),
(2, 3, 'SWP-5', 'Epic', 'Product Management', 'Quản lý sản phẩm trong hệ thống e-commerce', 'Admin có thể quản lý toàn bộ sản phẩm', 13, 'High', 'In Progress', 'student4-jira-id', GETDATE());
GO

-- 10. Insert GitHubCommits
INSERT INTO GitHubCommits (RepoID, SHA, CommitterName, CommitterEmail, CommitMessage, Additions, Deletions, CommitDate, CommitUrl) VALUES
(1, 'abc123def456', 'Le Van C', 'student1@fpt.edu.vn', 'Initial commit: Setup project structure', 150, 0, '2026-01-15 10:00:00', 'https://github.com/SE1709-Group1/elearning-backend/commit/abc123def456'),
(1, 'def456ghi789', 'Le Van C', 'student1@fpt.edu.vn', 'Add user authentication module', 200, 10, '2026-01-16 14:30:00', 'https://github.com/SE1709-Group1/elearning-backend/commit/def456ghi789'),
(1, 'ghi789jkl012', 'Pham Thi D', 'student2@fpt.edu.vn', 'Implement login API endpoint', 80, 5, '2026-01-17 09:15:00', 'https://github.com/SE1709-Group1/elearning-backend/commit/ghi789jkl012'),
(1, 'jkl012mno345', 'Hoang Van E', 'student3@fpt.edu.vn', 'Add database migration scripts', 120, 0, '2026-01-18 16:45:00', 'https://github.com/SE1709-Group1/elearning-backend/commit/jkl012mno345'),
(2, 'mno345pqr678', 'Le Van C', 'student1@fpt.edu.vn', 'Setup React frontend project', 180, 0, '2026-01-19 11:20:00', 'https://github.com/SE1709-Group1/elearning-frontend/commit/mno345pqr678'),
(3, 'pqr678stu901', 'Tran Van G', 'student5@fpt.edu.vn', 'Create product model and repository', 95, 3, '2026-01-20 13:10:00', 'https://github.com/SE1709-Group2/ecommerce-backend/commit/pqr678stu901');
GO

-- 11. Insert AnomalyReports
INSERT INTO AnomalyReports (ProjectID, DetectedDate, IssueType, Description, Severity, RelatedUserID) VALUES
(1, '2026-01-25 10:00:00', 'NO_COMMIT_7_DAYS', 'Student Nguyen Thi F has not committed code for 7 days', 'MEDIUM', 9),
(2, '2026-01-24 14:30:00', 'IMBALANCED_WORK', 'Work distribution is uneven: Leader has 60% commits while members have less than 20%', 'HIGH', NULL),
(1, '2026-01-23 09:15:00', 'MISSING_SRS_REQ', 'Story SWP-3 is missing acceptance criteria in SRS document', 'LOW', NULL);
GO

-- 12. Insert SRSDocuments
INSERT INTO SRSDocuments (ProjectID, VersionNumber, ExportedBy, ExportedDate, FileLink, SnapshotSummary) VALUES
(1, 'v1.0', 2, '2026-01-20 15:00:00', 'https://github.com/SE1709-Group1/docs/SRS_v1.0.pdf', '{"totalRequirements": 15, "epics": 3, "stories": 8, "tasks": 4}'),
(1, 'v1.1', 2, '2026-01-25 16:30:00', 'https://github.com/SE1709-Group1/docs/SRS_v1.1.pdf', '{"totalRequirements": 18, "epics": 3, "stories": 11, "tasks": 4}'),
(2, 'v1.0', 3, '2026-01-22 10:00:00', 'https://github.com/SE1709-Group2/docs/SRS_v1.0.pdf', '{"totalRequirements": 12, "epics": 2, "stories": 7, "tasks": 3}');
GO

PRINT '✅ Sample data inserted successfully!';
