ALTER TABLE projects ADD COLUMN accounts_enabled BOOLEAN;
UPDATE projects SET accounts_enabled = true;
ALTER TABLE projects ALTER COLUMN accounts_enabled SET DEFAULT true;
ALTER TABLE projects ALTER COLUMN accounts_enabled SET NOT NULL;

ALTER TABLE projects ADD COLUMN accounts_label VARCHAR(50) NULL;

CREATE TABLE project_accounts (
  id SERIAL PRIMARY KEY,
  project_id INTEGER NOT NULL REFERENCES projects(project_id),
  org_id INTEGER NOT NULL REFERENCES organization(org_id),
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX proj_acct_project_idx ON project_accounts (project_id);
CREATE INDEX proj_acct_org_idx ON project_accounts (org_id);

