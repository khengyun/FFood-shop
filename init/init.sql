
Create table ffood;
USE ffood;
GO

IF NOT EXISTS (SELECT * FROM sys.sql_logins WHERE name = 'sa')
BEGIN
    CREATE LOGIN [newuser] WITH PASSWORD = 'sa@123456', CHECK_POLICY = OFF;
    ALTER SERVER ROLE [sysadmin] ADD MEMBER [newuser];
END
GO