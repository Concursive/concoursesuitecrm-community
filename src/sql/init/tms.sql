
INSERT INTO ticket_level (level,description,default_item,enabled) VALUES (0,'Entry level',@FALSE@,@TRUE@);
INSERT INTO ticket_level (level,description,default_item,enabled) VALUES (1,'First level',@FALSE@,@TRUE@);
INSERT INTO ticket_level (level,description,default_item,enabled) VALUES (2,'Second level',@FALSE@,@TRUE@);
INSERT INTO ticket_level (level,description,default_item,enabled) VALUES (3,'Third level',@FALSE@,@TRUE@);
INSERT INTO ticket_level (level,description,default_item,enabled) VALUES (4,'Top level',@FALSE@,@TRUE@);


INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Normal','background-color:lightgreen;color:black;',@FALSE@,0,@TRUE@);
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Important','background-color:yellow;color:black;',@FALSE@,1,@TRUE@);
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES 
  ('Critical','background-color:red;color:black;font-weight:bold;',@FALSE@,2,@TRUE@);


INSERT INTO lookup_ticketsource (level,description) VALUES (1,'Phone');
INSERT INTO lookup_ticketsource (level,description) VALUES (2,'Email');
INSERT INTO lookup_ticketsource (level,description) VALUES (3,'Letter');
INSERT INTO lookup_ticketsource (level,description) VALUES (4,'Other');

INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Scheduled','background-color:lightgreen;color:black;',@FALSE@,0,@TRUE@);
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Next','background-color:yellow;color:black;',@FALSE@,1,@TRUE@);
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES 
  ('Immediate','background-color:red;color:black;font-weight:bold;',@FALSE@,2,@TRUE@);


INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Sales','',@FALSE@,1,@TRUE@);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Billing','',@FALSE@,2,@TRUE@);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Technical','',@FALSE@,3,@TRUE@);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Order','',@FALSE@,4,@TRUE@);
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Other','',@FALSE@,5,@TRUE@);



