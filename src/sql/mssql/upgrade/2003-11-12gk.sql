/* Cron entry for deleting old reports in the queue */
INSERT INTO events
(minute, hour, task, extrainfo, enabled)
VALUES
('0',
 '*/12',
 'org.aspcfs.apps.reportRunner.ReportCleanup#doTask',
 '${FILEPATH}',
 1
);
