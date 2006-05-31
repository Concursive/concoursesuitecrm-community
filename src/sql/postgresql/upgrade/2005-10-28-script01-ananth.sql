-- New ticket fields

ALTER TABLE ticket ADD COLUMN resolvable BOOLEAN;
UPDATE ticket SET resolvable=TRUE;
ALTER TABLE ticket ALTER COLUMN resolvable SET NOT NULL;
ALTER TABLE ticket ALTER COLUMN resolvable SET DEFAULT TRUE;

ALTER TABLE ticket ADD COLUMN resolvedby INT REFERENCES access(user_id);
ALTER TABLE ticket ADD COLUMN resolvedby_department_code INT REFERENCES lookup_department(code);
