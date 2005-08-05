DELETE FROM events WHERE task = 'org.aspcfs.modules.base.Trasher#doTask';
DELETE FROM events WHERE task = 'org.aspcfs.apps.notifier.Notifier#doTask';

-- Locale update
ALTER TABLE sites ADD language VARCHAR(11);
UPDATE sites SET language = 'en_US';

