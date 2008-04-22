-- ip addr fields need to have length = 30
ALTER TABLE `access` MODIFY last_ip VARCHAR(30);
ALTER TABLE access_log MODIFY ip VARCHAR(30);
ALTER TABLE active_survey_responses MODIFY ip_address VARCHAR(30);
ALTER TABLE sync_log MODIFY ip VARCHAR(30);
