ALTER TABLE ticket_category ADD COLUMN entered TIMESTAMP(3);
UPDATE ticket_category SET entered = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ticket_category ALTER COLUMN entered SET NOT NULL;

ALTER TABLE ticket_category ADD COLUMN modified TIMESTAMP(3);
UPDATE ticket_category SET modified = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ticket_category ALTER COLUMN modified SET NOT NULL;

ALTER TABLE ticket_category_draft ADD COLUMN entered TIMESTAMP(3);
UPDATE ticket_category_draft SET entered = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_draft ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_draft ALTER COLUMN entered SET NOT NULL;

ALTER TABLE ticket_category_draft ADD COLUMN modified TIMESTAMP(3);
UPDATE ticket_category_draft SET modified = CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_draft ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE ticket_category_draft ALTER COLUMN modified SET NOT NULL;
