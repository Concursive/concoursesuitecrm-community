ALTER TABLE action_plan_category_draft ADD COLUMN
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE action_plan_category_draft ADD COLUMN
  modified timestamp NULL;