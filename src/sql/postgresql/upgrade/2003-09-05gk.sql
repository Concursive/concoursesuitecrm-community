/* Notifier path is now dynamically modified
*/
UPDATE events
SET extrainfo = '${FILEPATH}'
WHERE task = 'org.aspcfs.apps.notifier.Notifier#doTask';
