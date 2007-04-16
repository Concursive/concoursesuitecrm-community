CREATE TABLE lookup_container_menu (
  code SERIAL PRIMARY KEY,
  cname VARCHAR(300),
  description VARCHAR(300),
  default_item BOOLEAN DEFAULT FALSE,
  level INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  link_module_id INT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--CREATE UNIQUE INDEX u_lcontmenu_cname_lmoduleid ON lookup_container_menu (cname);

CREATE TABLE web_page_role_map (
  page_role_map_id SERIAL PRIMARY KEY,
  web_page_id INT NOT NULL REFERENCES web_page(page_id),
  role_id INT NOT NULL REFERENCES role(role_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES "access"(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES "access"(user_id)
);

create table web_icelet_dashboard_map (
  dashboard_map_id serial PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_module_id INT,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
);

create table web_icelet_customtab_map (
  custom_map_id serial PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_container_id int REFERENCES lookup_container_menu (code),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
);

create  table web_icelet_publicwebsite (
  icelet_publicwebsite_id serial PRIMARY KEY,
  icelet_id INT references web_icelet(icelet_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL references access(user_id)
);

alter table web_page add column link_module_id int references permission_category (category_id);
alter table web_page add column link_container_id int references lookup_container_menu(code);

alter table permission_category add column dashboards BOOLEAN;
UPDATE permission_category SET dashboards = false;
ALTER TABLE permission_category ALTER COLUMN dashboards SET DEFAULT false;
ALTER TABLE permission_category ALTER COLUMN dashboards SET DEFAULT NOT NULL;

alter table permission_category add column customtabs BOOLEAN;
UPDATE permission_category SET customtabs = false;
ALTER TABLE permission_category ALTER COLUMN customtabs SET DEFAULT false;
ALTER TABLE permission_category ALTER COLUMN customtabs SET DEFAULT NOT NULL;
