ALTER TABLE action_step ADD group_id INTEGER REFERENCES user_group(group_id);

UPDATE action_step SET group_id = null WHERE permission_type IS NULL OR permission_type <> 8;
UPDATE action_step SET department_id = null WHERE permission_type IS NULL OR permission_type <> 3;
UPDATE action_step SET role_id = null WHERE permission_type IS NULL OR permission_type <> 2;

ALTER TABLE ticket ADD resolvable BIT NOT NULL DEFAULT 1;
ALTER TABLE ticket ADD resolvedby INT REFERENCES access(user_id);
ALTER TABLE ticket ADD resolvedby_department_code INT REFERENCES lookup_department(code);

