
//9-25-2002 (post-data migration)
update permission set permission_add = 't' where permission = 'pipeline-opportunities';
alter table call_log add column alert varchar(100) default null;

//9-30-02
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (8, 'tickets-tickets-reports', true, true, false, true, 'Reports', 30);

//10-2
update search_fields set searchable='f' where field='contactId';
