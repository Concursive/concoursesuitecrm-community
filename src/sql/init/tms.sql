
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (0,'Entry level',false,true);
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (1,'First level',false,true);
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (2,'Second level',false,true);
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (3,'Third level',false,true);
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (4,'Top level',false,true);


INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Normal','background-color:lightgreen;color:black;',false,0,true);
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Important','background-color:yellow;color:black;',false,1,true);
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Critical','background-color:red;color:black;font-weight:bold;',false,2,true);


INSERT INTO lookup_ticketsource (level,description) VALUES (1,'Phone');
INSERT INTO lookup_ticketsource (level,description) VALUES (2,'Email');
INSERT INTO lookup_ticketsource (level,description) VALUES (3,'Letter');
INSERT INTO lookup_ticketsource (level,description) VALUES (4,'Other');


INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Scheduled','background-color:lightgreen;color:black;',false,0,true);
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Next','background-color:yellow;color:black;',false,1,true);
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Immediate','background-color:red;color:black;font-weight:bold;',false,2,true);


INSERT INTO ticket_source (source_code,source) VALUES (0,'Phone');
INSERT INTO ticket_source (source_code,source) VALUES (1,'Email');
INSERT INTO ticket_source (source_code,source) VALUES (2,'System');
INSERT INTO ticket_source (source_code,source) VALUES (3,'Other');
INSERT INTO ticket_source (source_code,source) VALUES (4,'Message');


INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Sales','',false,1,true);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Billing','',false,2,true);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Technical','',false,3,true);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Order','',false,4,true);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Other','',false,5,true);



