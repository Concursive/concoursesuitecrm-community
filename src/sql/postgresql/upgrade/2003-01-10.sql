/*08/07/2002*/
ALTER TABLE  survey_item ALTER COLUMN average SET DEFAULT 0.00;
ALTER TABLE  survey_item ALTER COLUMN total1 SET DEFAULT 0;
ALTER TABLE  survey_item ALTER COLUMN total2 SET DEFAULT 0;
ALTER TABLE  survey_item ALTER COLUMN total3 SET DEFAULT 0;
ALTER TABLE  survey_item ALTER COLUMN total4 SET DEFAULT 0;
ALTER TABLE  survey_item ALTER COLUMN total5 SET DEFAULT 0;
ALTER TABLE  survey_item ALTER COLUMN total6 SET DEFAULT 0;

ALTER TABLE  survey_answer ALTER COLUMN survey_id SET DEFAULT -1;

alter table  cfsinbox_message add column delete_flag BOOLEAN;
ALTER TABLE  cfsinbox_message ALTER COLUMN delete_flag SET DEFAULT false;

/*8/12/2002*/
alter table opportunity add column alert varchar(100) default null;

/*8/16/2002*/
UPDATE cfsinbox_messagelink
        SET    sent_to = contact.contact_id
        FROM   contact
        WHERE  cfsinbox_messagelink.sent_to = contact.user_id; 
/*8/19/2002*/
alter table  opportunity add column enabled BOOLEAN;
ALTER TABLE  opportunity ALTER COLUMN enabled SET DEFAULT true;
update opportunity set enabled = 't';

//might need to do this
alter table  search_fields add column enabled BOOLEAN;
ALTER TABLE  search_fields ALTER COLUMN enabled SET DEFAULT true;
update search_fields set enabled = 't';

//9-25-2002 (post-data migration)
update permission set permission_add = 't' where permission = 'pipeline-opportunities';
alter table call_log add column alert varchar(100) default null;

//9-30-02
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (8, 'tickets-tickets-reports', true, true, false, true, 'Reports', 30);

//10-2
update search_fields set searchable='f' where field='contactId';
