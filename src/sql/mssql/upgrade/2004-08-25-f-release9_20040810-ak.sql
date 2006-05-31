-- Upgrade script for Time Zones
-- author     Akhilesh Mathur
-- created    Aug 25, 2004
ALTER table call_log ADD alertdate_timezone VARCHAR(255) CONSTRAINT DF__call_log__alertd__14B10FFA DEFAULT 'America/New_York'; 

