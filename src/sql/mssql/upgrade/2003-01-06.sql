/* May 16, 2002  All servers up-to-date */

/* May 23, 2002 */

ALTER TABLE access_log ADD COLUMN browser VARCHAR(255);

UPDATE lookup_stage SET LEVEL = order_id;

DELETE FROM role_permission WHERE permission_id IN (SELECT permission_id FROM permission WHERE category_id = 11 AND level = 20);
DELETE FROM permission WHERE category_id = 11 AND level = 20;
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (11, 'autoguide-adruns', false, false, true, false, 'Ad Run complete status', 20);

INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-autoguide-inventory', true, true, true, true, 'Auto Guide Vehicle Inventory', 200);


