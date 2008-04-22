CREATE TABLE lookup_container_menu (
  code INT AUTO_INCREMENT PRIMARY KEY,
  cname VARCHAR(300),
  description VARCHAR(300),
  default_item BOOLEAN DEFAULT FALSE,
  level INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  link_module_id INT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

--CREATE UNIQUE INDEX u_lcontmenu_cname_lmoduleid ON lookup_container_menu (cname);

CREATE TABLE web_page_role_map (
  page_role_map_id INT AUTO_INCREMENT PRIMARY KEY,
  web_page_id INT NOT NULL REFERENCES web_page(page_id),
  role_id INT NOT NULL REFERENCES role(role_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

create table web_icelet_dashboard_map (
  dashboard_map_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_module_id INT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

create table web_icelet_customtab_map (
  custom_map_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_container_id int REFERENCES lookup_container_menu (code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

create table web_icelet_publicwebsite (
  icelet_publicwebsite_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT references web_icelet(icelet_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

alter table web_page add column link_module_id int references permission_category (category_id);
alter table web_page add column link_container_id int references lookup_container_menu(code);

alter table permission_category add column dashboards BOOLEAN NOT NULL DEFAULT false;
UPDATE permission_category SET dashboards = false;

alter table permission_category add column customtabs BOOLEAN NOT NULL DEFAULT false;
UPDATE permission_category SET customtabs = false;
