
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (0,'Entry level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (1,'First level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (2,'Second level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (3,'Third level','f','t');
INSERT INTO ticket_level (level_code,level,default_item,enabled) VALUES (4,'Top level','f','t');


INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES ('Normal','background-color:lightgreen;colo
r:black;','f',0,'t');
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES ('Important','background-color:yellow;color
:black;','f',1,'t');
INSERT INTO ticket_severity (description,style,default_item,level,enabled) VALUES ('Critical','background-color:red;color:bla
ck;font-weight:bold;','f',2,'t');


INSERT INTO lookup_ticketsource (level,description) VALUES (1,'Phone');
INSERT INTO lookup_ticketsource (level,description) VALUES (2,'Email');
INSERT INTO lookup_ticketsource (level,description) VALUES (3,'Letter');
INSERT INTO lookup_ticketsource (level,description) VALUES (4,'Other');


INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES ('Scheduled','background-color:lightgreen;c
olor:black;','f',0,'t');
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES ('Next','background-color:yellow;color:blac
k;','f',1,'t');
INSERT INTO ticket_priority (description,style,default_item,level,enabled) VALUES ('Immediate','background-color:red;color:bl
ack;font-weight:bold;','f',2,'t');


INSERT INTO ticket_source (source_code,source) VALUES (0,'Phone');
INSERT INTO ticket_source (source_code,source) VALUES (1,'Email');
INSERT INTO ticket_source (source_code,source) VALUES (2,'System');
INSERT INTO ticket_source (source_code,source) VALUES (3,'Other');
INSERT INTO ticket_source (source_code,source) VALUES (4,'Message');


INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Sales','','f',1,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Billing','','f',2,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Technical','','f',3,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Order','','f',4,'t');
INSERT INTO ticket_category (cat_level,parent_cat_code,description,full_description,default_item,level,enabled) VALUES (0,0,'Other','','f',5,'t');



