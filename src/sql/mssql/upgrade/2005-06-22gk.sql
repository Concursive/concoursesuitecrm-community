-- Moved into the ScheduledJobs.java Class

DELETE FROM events WHERE task = 'org.aspcfs.apps.users.UserCleanup#doTask';
DELETE FROM events WHERE extrainfo = 'http://${WEBSERVER.URL}/ProcessSystem.do?command=ClearGraphData';
DELETE FROM events WHERE task = 'org.aspcfs.apps.reportRunner.ReportCleanup#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.apps.reportRunner.ReportRunner#doTask';
