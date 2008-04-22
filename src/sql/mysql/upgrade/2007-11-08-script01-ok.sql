ALTER TABLE web_icelet ADD icelet_description_temp varchar(1500);
UPDATE web_icelet SET icelet_description_temp = icelet_description;
ALTER TABLE web_icelet DROP COLUMN icelet_description;
ALTER TABLE web_icelet ADD icelet_description varchar(1500);
UPDATE web_icelet SET icelet_description = icelet_description_temp;
ALTER TABLE web_icelet DROP COLUMN icelet_description_temp;