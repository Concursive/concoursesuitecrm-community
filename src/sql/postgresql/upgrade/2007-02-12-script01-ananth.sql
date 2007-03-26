-- missing id field in action_step_account_types
-- TODO: Verify script on existing systems. Primary Key Data?

ALTER TABLE action_step_account_types ADD COLUMN id INT;
ALTER TABLE action_step_account_types ADD PRIMARY KEY (id);

CREATE SEQUENCE action_step_account_types_id_seq;
ALTER TABLE action_step_account_types ALTER COLUMN id SET DEFAULT nextval('action_step_account_types_id_seq');
