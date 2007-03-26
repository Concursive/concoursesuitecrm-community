-- ip addr fields need to have length = 30
ALTER TABLE access ALTER COLUMN last_ip TYPE VARCHAR(30);
ALTER TABLE access_log ALTER COLUMN ip TYPE VARCHAR(30);
ALTER TABLE active_survey_responses ALTER COLUMN ip_address TYPE VARCHAR(30);
ALTER TABLE sync_log ALTER COLUMN ip TYPE VARCHAR(30);