/* December 6, 2002 */

DROP TABLE mod_log;

CREATE TABLE usage_log (
  usage_id SERIAL PRIMARY KEY,
  entered TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NULL,
  action INT NOT NULL,
  record_id INT NULL,
  record_size INT NULL
);

alter table organization add column namesalutation varchar(80);
alter table organization add column namelast varchar(80);
alter table organization add column namefirst varchar(80);
alter table organization add column namemiddle varchar(80);
alter table organization add column namesuffix varchar(80);


/* some new opportunity component stuff */

CREATE TABLE opportunity_header (
  opp_id serial PRIMARY KEY,
  description VARCHAR(80),
	acctlink INT default -1,
	contactlink INT default -1,
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id)
);

CREATE TABLE opportunity_component (
  id serial PRIMARY KEY,
  opp_id int references opportunity_header(opp_id),
	owner INT NOT NULL REFERENCES access(user_id),
	description VARCHAR(80),
	closedate date not null,
	closeprob float,
	terms float,
	units char(1),
	lowvalue float,
	guessvalue float,
	highvalue float,
	stage INT references lookup_stage(code),
	stagedate date NOT NULL DEFAULT CURRENT_TIMESTAMP,
	commission float,
	type char(1),
	alertdate date,
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id),  
  closed TIMESTAMP,
  alert varchar(100) default null,
  enabled BOOLEAN NOT NULL DEFAULT true,
  notes TEXT
);  

CREATE TABLE opportunity_component_levels (
  opp_id INT NOT NULL REFERENCES opportunity_component(id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
  


/*
Contact Types update Author : Mathur ; Dated : 12/17/02
*/

alter table lookup_contact_types add column user_id INT references access(user_id);
alter table lookup_contact_types add column category INT;
alter table lookup_contact_types alter category set DEFAULT 0;
update lookup_contact_types set category = '0';
alter table lookup_contact_types add constraint category_not_null CHECK(category IS NOT NULL) ;

CREATE TABLE contact_type_levels (
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  type_id INT NOT NULL REFERENCES lookup_contact_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/* execute the contact_type_update.pl */

/* TODO: drop type_id from contact */

/* 
Modules update Author : Mathur ; Dated : 01/06/03
*/

drop table permission_category;
DROP sequence permission_cate_category_id_seq;

CREATE TABLE permission_category (
  category_id SERIAL PRIMARY KEY,
  category VARCHAR(80) ,
  description VARCHAR(255),
  level INT NOT NULL DEFAULT 0,
  enabled boolean NOT NULL DEFAULT true,
  active boolean NOT NULL DEFAULT true,
  folders boolean NOT NULL DEFAULT false,
  lookups boolean NOT NULL DEFAULT false
);

CREATE TABLE module_field_categorylink (
  id SERIAL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  category_id INT NOT NULL,
  level INTEGER DEFAULT 0,
  description TEXT,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lookup_lists_lookup(
  id SERIAL PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  lookup_id INT NOT NULL,
  class_name VARCHAR(20),
  table_name VARCHAR(60),
  level INTEGER DEFAULT 0,
  description TEXT,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

insert into permission_category (category_id, category, level, folders, lookups) VALUES (1, 'Account Management', 50, 't', 't');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (2, 'Contacts & Resources', 30, 't', 't');
insert into permission_category (category_id, category, level, enabled, folders, lookups) VALUES (3, 'Auto Guide', 120, 'f', 'f', 'f');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (4, 'Pipeline Management', 40, 'f', 't');
insert into permission_category (category_id, category, level, enabled, folders, lookups) VALUES (11, 'Demo', 110, 'f', 'f', 'f');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (6, 'Campaign Manager', 60, 'f', 'f');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (7, 'Project Management', 70, 'f', 'f');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (8, 'Tickets', 80, 'f', 't');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (9, 'Admin', 90, 'f', 'f');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (10, 'Help', 100, 'f', 'f');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (13, 'System', 10, 'f', 'f');
insert into permission_category (category_id, category, level, folders, lookups) VALUES (14, 'My Home Page', 20, 'f', 'f');
SELECT setval ('"permission_cate_category_id_seq"', 14, true);

ALTER TABLE permission ADD FOREIGN KEY (category_id) REFERENCES permission_category(category_id);
ALTER TABLE custom_field_category ADD FOREIGN KEY (module_id) REFERENCES module_field_categorylink(category_id);

insert into module_field_categorylink (module_id, category_id) VALUES (1, 1);
insert into module_field_categorylink (module_id, category_id) VALUES (2, 2);

insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (1, 1, 'lookupList', 'lookup_account_types', 1, 'Account Types');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (1, 2, 'lookupList', 'lookup_revenue_types', 2, 'Revenue Types');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (1, 3, 'contactType', '', 3, 'Contact Types');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (2, 1, 'contactType', '', 1, 'Contact Types');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (2, 2, 'lookupList', 'lookup_contactemail_types', 2, 'Contact Email Type');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (2, 3, 'lookupList', 'lookup_contactaddress_types', 3, 'Contact Address Type');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (2, 4, 'lookupList', 'lookup_contactphone_types', 4, 'Contact Phone Type');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (2, 5, 'lookupList', 'lookup_department', 5, 'Department');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (4, 1, 'lookupList', 'lookup_stage', 1, 'Stage');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (4, 2, 'lookupList', 'lookup_opportunity_types', 2, 'Opportunity Type');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (8, 1, 'lookupList', 'lookup_ticketsource', 1, 'Ticket Source');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (8, 2, 'lookupList', 'ticket_severity', 2, 'Ticket Severity');
insert into lookup_lists_lookup (module_id, lookup_id, class_name, table_name, level, description) values (8, 3, 'lookupList', 'ticket_priority', 3, 'Ticket Priority');


