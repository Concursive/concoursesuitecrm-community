/* Cron entry for running reports in the queue */
INSERT INTO events
(minute, task, extrainfo, enabled)
VALUES
('*/2',
 'org.aspcfs.apps.reportRunner.ReportRunner#doTask',
 '${FILEPATH}',
 true
);
