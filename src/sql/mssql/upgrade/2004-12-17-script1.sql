-- Address Request

ALTER TABLE campaign_survey_link ADD link_type INT;

ALTER TABLE contact ADD information_update_date DATETIME DEFAULT CURRENT_TIMESTAMP;
