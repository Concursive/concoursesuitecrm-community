-- 2005-09-15 Upgrade

-- Action Plan Category
CREATE TABLE action_plan_category (
  id serial PRIMARY KEY,
  cat_level int  NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

-- Action Plan Category Draft
CREATE TABLE action_plan_category_draft (
  id serial PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int  NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

ALTER TABLE action_plan ADD COLUMN cat_code INT REFERENCES action_plan_category(id);
ALTER TABLE action_plan ADD COLUMN subcat_code1 INT REFERENCES action_plan_category(id);
ALTER TABLE action_plan ADD COLUMN subcat_code2 INT REFERENCES action_plan_category(id);
ALTER TABLE action_plan ADD COLUMN subcat_code3 INT REFERENCES action_plan_category(id);

-- Ticket Category To Action Plan map table
CREATE TABLE ticket_category_plan_map (
  map_id SERIAL PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category(id)
);

-- Ticket Category Draft To Action Plan map table
CREATE TABLE ticket_category_draft_plan_map (
  map_id SERIAL PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id)
);

--Action Plan Work Notes
CREATE TABLE action_plan_work_notes (
  note_id SERIAL PRIMARY KEY,
  plan_work_id INTEGER NOT NULL REFERENCES action_plan_work(plan_work_id),
  description VARCHAR(300) NOT NULL,
  submitted TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE action_plan_work ADD current_phase INTEGER;


