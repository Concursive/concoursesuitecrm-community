/* Cron entry for cleaning up the server, every day at 12:00am 
   The OS level cron entry should be removed
*/
INSERT INTO events
(minute, hour, task, extrainfo, enabled)
VALUES
('0', '0',
 'org.aspcfs.modules.service.tasks.GetURL#doTask',
 'http://127.0.0.1/ProcessSystem.do?command=ClearGraphData',
 1
);
