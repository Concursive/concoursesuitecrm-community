-- Script (C) 2005 Concursive Corporation, all rights reserved
-- Database upgrade v2.9.2 (2005-01-14)

UPDATE call_log
SET status_id = 2
WHERE call_id IN (SELECT parent_id FROM call_log)
AND status_id = 3;

INSERT [database_version] ([script_filename],[script_version])VALUES('mssql_2005-01-14.sql','2005-01-14');
