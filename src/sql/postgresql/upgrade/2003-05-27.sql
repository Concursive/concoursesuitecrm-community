CREATE TABLE ticket_category_draft ( 
  id serial PRIMARY KEY,
  link_id INT DEFAULT -1,
  cat_level int  NOT NULL DEFAULT 0,
  parent_cat_code int  NOT NULL,
  description VARCHAR(300) NOT NULL,
  full_description text NOT NULL DEFAULT '',
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

ALTER TABLE permission_category ADD COLUMN categories boolean;
ALTER TABLE permission_category ALTER categories SET DEFAULT false;
UPDATE permission_category SET categories = false;
UPDATE permission_category SET categories = true where category = 'Tickets';

