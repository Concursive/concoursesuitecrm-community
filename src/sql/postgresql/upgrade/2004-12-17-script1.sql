-- Address Request

ALTER TABLE campaign_survey_link ADD COLUMN link_type INT;

ALTER TABLE contact ADD information_update_date TIMESTAMP(3);
ALTER TABLE contact ALTER information_update_date SET DEFAULT CURRENT_TIMESTAMP;
