-- Upgrade script for Time Zones
-- author     Akhilesh Mathur
-- created    Aug 25, 2004
ALTER table call_log ADD COLUMN alertdate_timezone VARCHAR(255);
ALTER table call_log ALTER alertdate_timezone set default 'America/New_York';
UPDATE call_log set alertdate_timezone = 'America/New_York';

