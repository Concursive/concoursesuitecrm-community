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
