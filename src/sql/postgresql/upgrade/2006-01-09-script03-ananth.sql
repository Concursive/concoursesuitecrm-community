--Changes to support 'Attach Relationships' step type
ALTER TABLE action_step ADD COLUMN target_relationship VARCHAR(80);

--Action Step Account Types
CREATE TABLE action_step_account_types (
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code)
);
