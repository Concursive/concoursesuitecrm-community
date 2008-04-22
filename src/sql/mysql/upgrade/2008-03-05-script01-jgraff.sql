-- MySQL doesn't do default values for TEXT fields

ALTER TABLE action_plan_category MODIFY full_description TEXT;
ALTER TABLE action_plan_category_draft MODIFY full_description TEXT;
ALTER TABLE asset_category MODIFY full_description TEXT;
ALTER TABLE asset_category_draft MODIFY full_description TEXT;
ALTER TABLE ticket_category MODIFY full_description TEXT;
ALTER TABLE ticket_category_draft MODIFY full_description TEXT;
