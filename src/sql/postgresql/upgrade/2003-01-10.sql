//9-30-02
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (8, 'tickets-tickets-reports', true, true, false, true, 'Reports', 30);

//10-31-02
alter table permission_category add column folders boolean;
alter table permission_category alter column folders set default false;
update table permission_category set folders='f';

alter table permission_category add column lookups boolean;
alter table permission_category alter column lookups set default false;
update table permission_category set lookups='f';

//
//do the database switch-around right before this to make accounts id 1 and contacts id 2
//

update permission_category set folders='t' where category_id in (1,2);
update permission_category set lookups='t' where category_id=4;
update permission_category set lookups='t' where category_id=1;
update permission_category set lookups='t' where category_id=2;
update permission_category set lookups='t' where category_id=8;

alter table opportunity add column notes text;

//ignore the revenue, employees fields in accounts
insert into system_prefs (category, data, enabled, enteredby, modifiedby) values ('system.fields.ignore', '<config><ignore>accounts-employees</ignore><ignore>accounts-revenue</ignore></config>', 't', '0', '0');

CREATE TABLE lookup_opportunity_types (
  code SERIAL PRIMARY KEY,
  order_id INT,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE opportunity_type_levels (
  opp_id INT NOT NULL REFERENCES opportunity(opp_id),
  type_id INT NOT NULL REFERENCES lookup_opportunity_types(code),
  level INTEGER not null,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

alter table field_types add column enabled boolean;
alter table field_types alter column enabled set default 't';
update field_types set enabled = 't';
update field_types set enabled = 'f' where id in (3,4,5,6);
update field_types set enabled = 'f' where id in (9);



