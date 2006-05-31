-- Action Plan Category
CREATE TABLE action_plan_category ( 
  id INT IDENTITY PRIMARY KEY, 
  cat_level int  NOT NULL DEFAULT 0, 
  parent_cat_code int NOT NULL DEFAULT 0, 
  description VARCHAR(300) NOT NULL, 
  full_description text NOT NULL DEFAULT '',
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

-- Action Plan Category Draft
CREATE TABLE action_plan_category_draft (
  id INT IDENTITY PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int NOT NULL DEFAULT 0,
  parent_cat_code int NOT NULL DEFAULT 0,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1
);

ALTER TABLE action_plan ADD cat_code INT REFERENCES action_plan_category(id);
ALTER TABLE action_plan ADD subcat_code1 INT REFERENCES action_plan_category(id);
ALTER TABLE action_plan ADD subcat_code2 INT REFERENCES action_plan_category(id);
ALTER TABLE action_plan ADD subcat_code3 INT REFERENCES action_plan_category(id);

-- Ticket Category To Action Plan map table
CREATE TABLE ticket_category_plan_map (
  map_id INT IDENTITY PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category(id)
);

-- Ticket Category Draft To Action Plan map table
CREATE TABLE ticket_category_draft_plan_map (
  map_id INT IDENTITY PRIMARY KEY,
  plan_id INTEGER NOT NULL REFERENCES action_plan(plan_id),
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id)
);
