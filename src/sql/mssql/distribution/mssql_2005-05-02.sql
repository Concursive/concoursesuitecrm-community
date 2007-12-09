-- Script (C) 2005 Concursive Corporation, all rights reserved
-- Database upgrade v3.0.1 (2005-05-02)

DELETE FROM role_permission WHERE permission_id IN (SELECT permission_id FROM permission WHERE permission = 'orders' and category_id IN (SELECT category_id FROM permission_category WHERE constant = 420041017));
DELETE FROM permission WHERE permission = 'orders' AND category_id IN (SELECT category_id FROM permission_category WHERE constant = 420041017);
UPDATE permission SET category_id = (SELECT category_id FROM permission_category WHERE constant = 420041018) WHERE permission = 'orders-orders';
UPDATE permission SET category_id = (SELECT category_id FROM permission_category WHERE constant = 420041017) WHERE permission = 'quotes-quotes';
UPDATE permission SET category_id = (SELECT category_id FROM permission_category WHERE constant = 1111031131) WHERE permission = 'contacts-internal_contacts-folders';
UPDATE permission SET category_id = (SELECT category_id FROM permission_category WHERE constant = 1111031131) WHERE permission = 'contacts-internal_contacts-projects';

UPDATE lookup_lists_lookup SET lookup_id = 819041649 WHERE table_name = 'lookup_orgaddress_types';
UPDATE lookup_lists_lookup SET lookup_id = 819041650 WHERE table_name = 'lookup_orgphone_types';

INSERT [database_version] ([script_filename],[script_version])VALUES('mssql_2005-05-02.sql','2005-05-02');
