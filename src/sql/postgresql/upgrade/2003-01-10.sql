
//9-25-2002 (post-data migration)
update permission set permission_add = 't' where permission = 'pipeline-opportunities';
alter table call_log add column alert varchar(100) default null;

//9-30-02
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (8, 'tickets-tickets-reports', true, true, false, true, 'Reports', 30);

//10-2
update search_fields set searchable='f' where field='contactId';

//10-08-02
alter table saved_criteriaelement add column source integer;
alter table saved_criteriaelement alter column source set default -1;

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

