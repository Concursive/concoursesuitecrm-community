-- New ticket fields

ALTER TABLE ticket ADD resolvable BIT NOT NULL DEFAULT 1;
ALTER TABLE ticket ADD resolvedby INT REFERENCES access(user_id);
ALTER TABLE ticket ADD resolvedby_department_code INT REFERENCES lookup_department(code);
