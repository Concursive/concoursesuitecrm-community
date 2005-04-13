/* Cron entry for hiding disabled/expired users with no data and no sub-users. */
INSERT INTO events
(minute, hour, task, extrainfo, enabled)
VALUES
('0',
 '*/1',
 'org.aspcfs.apps.users.UserCleanup#doTask',
 '${FILEPATH} http://${WEBSERVER.URL}/ProcessSystem.do?command=UpdateUserCache',
 true
);
