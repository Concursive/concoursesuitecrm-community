CREATE TABLE help_module (
  module_id serial PRIMARY KEY,
  category_id INT REFERENCES permission_category(category_id), 
  module_brief_description TEXT,
  module_detail_description TEXT
);

ALTER TABLE help_contents ADD COLUMN category_id INT REFERENCES permission_category(category_id); 
ALTER TABLE help_contents ADD COLUMN link_module_id INT NULL REFERENCES help_module(module_id);
ALTER TABLE help_contents ADD COLUMN title VARCHAR (255);
ALTER TABLE help_contents ADD COLUMN nextcontent INT REFERENCES help_contents(help_id);
ALTER TABLE help_contents ADD COLUMN prevcontent INT REFERENCES help_contents(help_id);
ALTER TABLE help_contents ADD COLUMN upcontent INT REFERENCES help_contents(help_id);

CREATE TABLE help_tableof_contents (
  content_id SERIAL PRIMARY KEY,
  displaytext VARCHAR (255),
  firstchild INT REFERENCES help_tableof_contents (content_id),
  nextsibling INT REFERENCES help_tableof_contents (content_id),
  parent INT REFERENCES help_tableof_contents (content_id),
  category_id INT REFERENCES permission_category(category_id),
  contentlevel INT NOT NULL,
  contentorder INT NOT NULL,
  enteredby INT NOT NULL REFERENCES access,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN DEFAULT true
);


CREATE TABLE help_tableofcontentitem_links (
  link_id SERIAL PRIMARY KEY,
  global_link_id INT NOT NULL REFERENCES help_tableof_contents(content_id),
  linkto_content_id INT NOT NULL REFERENCES help_contents(help_id),
  enteredby INT NOT NULL REFERENCES access,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE help_related_links (
  relatedlink_id SERIAL PRIMARY KEY,
  owning_module_id INT REFERENCES  help_module(module_id),
  linkto_content_id INT REFERENCES help_contents(help_id),
  displaytext VARCHAR(255) NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enabled boolean NOT NULL DEFAULT true
);

CREATE TABLE help_faqs (
  faq_id SERIAL PRIMARY KEY,
  owning_module_id INT NOT NULL REFERENCES help_module(module_id),
  question VARCHAR(1000) NOT NULL,
  answer VARCHAR(1000) NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  completedate TIMESTAMP(3),
  completedby INT REFERENCES access(user_id),
  enabled boolean NOT NULL DEFAULT true
);


