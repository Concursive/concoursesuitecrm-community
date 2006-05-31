INSERT INTO lookup_quote_delivery (description) VALUES ('Email');
INSERT INTO lookup_quote_delivery (description) VALUES ('Fax');
INSERT INTO lookup_quote_delivery (description) VALUES ('USPS');
INSERT INTO lookup_quote_delivery (description) VALUES ('FedEx');
INSERT INTO lookup_quote_delivery (description) VALUES ('UPS');
INSERT INTO lookup_quote_delivery (description) VALUES ('In Person');

-- Update and enable permissions (quotes) (2004-11-29-script05.sql)
UPDATE permission 
SET enabled = 1, 
permission_add = 0, 
permission_edit = 0, 
permission_delete = 0 
WHERE permission = 'quotes';

UPDATE permission SET enabled = 1 WHERE permission = 'accounts-quotes';

UPDATE permission_category SET enabled = 1 WHERE category = 'Quotes';

UPDATE lookup_quote_status SET level = (code * 10);

-- Updates permission_category to enable lookup lists for the Quotes (2004-11-29-script12.sql)
UPDATE permission_category SET lookups = 1 WHERE category = 'Quotes';

INSERT INTO lookup_document_store_role (description,default_item, level, enabled, group_id) VALUES ('Manager', 0, 1, 1, 1);
INSERT INTO lookup_document_store_role (description,default_item, level, enabled, group_id) VALUES ('Contributor level 3', 0, 2, 1, 1);
INSERT INTO lookup_document_store_role (description,default_item, level, enabled, group_id) VALUES ('Contributor level 2', 0, 3, 1, 1);
INSERT INTO lookup_document_store_role (description,default_item, level, enabled, group_id) VALUES ('Contributor level 1', 0, 4, 1, 1);
INSERT INTO lookup_document_store_role (description,default_item, level, enabled, group_id) VALUES ('Guest', 0, 5, 1, 1);

--Document Store Permissions (2004-12-02-script1.sql)
INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Document Store Details', 0, 10, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-view', 'View document store details', 0, 10, 1, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-edit', 'Modify document store details', 0, 20, 1, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (1, 'documentcenter-details-delete', 'Delete document store', 0, 30, 1, 1, 1);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Team Members', 0, 20, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-view', 'View team members', 0, 10, 1, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-view-email', 'See team member email addresses', 0, 20, 1, 1, 4);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-edit', 'Modify team', 0, 30, 1, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (2, 'documentcenter-team-edit-role', 'Modify team member role', 0, 40, 1, 1, 1);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Document Library', 0, 80, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-view', 'View documents', 0, 10, 1, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-add', 'Create folders', 0, 20, 1, 1, 2);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-edit', 'Modify folders', 0, 30, 1, 1, 3);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-folders-delete', 'Delete folders', 0, 40, 1, 1, 2);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-upload', 'Upload files', 0, 50, 1, 1, 4);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-download', 'Download files', 0, 60, 1, 1, 5);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-rename', 'Rename files', 0, 70, 1, 1, 3);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (3, 'documentcenter-documents-files-delete', 'Delete files', 0, 80, 1, 1, 2);

INSERT INTO lookup_document_store_permission_category (description,default_item, level, enabled, group_id) VALUES ('Setup', 0, 90, 1, 1);
INSERT INTO lookup_document_store_permission (category_id, permission, description, default_item, level, enabled, group_id, default_role) VALUES (4, 'documentcenter-setup-permissions', 'Configure document store permissions', 0, 20, 1, 1, 1);

-- 2005-01-18-script01.sql
INSERT INTO lookup_im_types (description, level) VALUES ('Business',10);
INSERT INTO lookup_im_types (description, level) VALUES ('Personal',20);
INSERT INTO lookup_im_types (description, level) VALUES ('Other',30);

INSERT INTO lookup_im_services (description, level) VALUES ('AOL Instant Messenger',10);
INSERT INTO lookup_im_services (description, level) VALUES ('Jabber Instant Messenger',20);
INSERT INTO lookup_im_services (description, level) VALUES ('MSN Instant Messenger',30);

INSERT INTO lookup_textmessage_types (description, level) VALUES ('Business',10);
INSERT INTO lookup_textmessage_types (description, level) VALUES ('Personal',20);
INSERT INTO lookup_textmessage_types (description, level) VALUES ('Other',30);

INSERT INTO lookup_delivery_options (description,level,enabled) VALUES ('Instant Message', 7, 0);
INSERT INTO lookup_delivery_options (description,level,enabled) VALUES ('Secure Socket', 8, 0);
INSERT INTO lookup_delivery_options (description,level) VALUES ('Broadcast', 9);

INSERT INTO survey (name, description, intro, outro, type, enteredby, modifiedby) values ('Address Update Request', '','','',2,0,0);

--2005-02-28-script03.sql
INSERT INTO lookup_contact_source (description) VALUES ('Advertisement');
INSERT INTO lookup_contact_source (description) VALUES ('Employee Referral');
INSERT INTO lookup_contact_source (description) VALUES ('External Referral');
INSERT INTO lookup_contact_source (description) VALUES ('Partner');
INSERT INTO lookup_contact_source (description) VALUES ('Public Relations');
INSERT INTO lookup_contact_source (description) VALUES ('Trade Show');
INSERT INTO lookup_contact_source (description) VALUES ('Web');
INSERT INTO lookup_contact_source (description) VALUES ('Word of Mouth');
INSERT INTO lookup_contact_source (description) VALUES ('Other');

INSERT INTO lookup_contact_rating (description) VALUES ('Qualified');
INSERT INTO lookup_contact_rating (description) VALUES ('Unqualified');

UPDATE contact SET lead = 0 WHERE lead IS NULL;

--2005-03-30-script01.sql
SET IDENTITY_INSERT [lookup_relationship_types] ON
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(1,42420034,42420034,'Subsidiary of','Parent of',10,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(2,42420034,42420034,'Customer of','Supplier to',20,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(3,42420034,42420034,'Partner of','Partner of',30,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(4,42420034,42420034,'Competitor of','Competitor of',40,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(5,42420034,42420034,'Employee of','Employer of',50,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(6,42420034,42420034,'Department of','Organization made up of',60,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(7,42420034,42420034,'Group of','Organization made up of',70,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(8,42420034,42420034,'Member of','Organization made up of',80,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(9,42420034,42420034,'Consultant to','Consultant of',90,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(10,42420034,42420034,'Influencer of','Influenced by',100,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(11,42420034,42420034,'Enemy of','Enemy of',110,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(12,42420034,42420034,'Proponent of','Endorsed by',120,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(13,42420034,42420034,'Ally of','Ally of',130,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(14,42420034,42420034,'Sponsor of','Sponsored by',140,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(15,42420034,42420034,'Relative of','Relative of',150,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(16,42420034,42420034,'Affiliated with','Affiliated with',160,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(17,42420034,42420034,'Teammate of','Teammate of',170,0,1)
INSERT [lookup_relationship_types] ([type_id],[category_id_maps_from],[category_id_maps_to],[reciprocal_name_1],[reciprocal_name_2],[level],[default_item],[enabled])VALUES(18,42420034,42420034,'Financier of','Financed by',180,0,1)
SET IDENTITY_INSERT [lookup_relationship_types] OFF

--2005-06-27-script01.sql
UPDATE product_catalog SET active = enabled;
UPDATE product_catalog SET enabled = 1;
