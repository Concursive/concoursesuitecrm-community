/* May 16, 2002  All servers up-to-date */

/* May 23, 2002 */

ALTER TABLE access_log ADD browser VARCHAR(255);

UPDATE lookup_stage SET LEVEL = order_id;

DELETE FROM role_permission WHERE permission_id IN (SELECT permission_id FROM permission WHERE category_id = 11 AND level = 20);
DELETE FROM permission WHERE category_id = 11 AND level = 20;
INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (11, 'autoguide-adruns', 0, 0, 1, 0, 'Ad Run complete status', 20);

INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (5, 'accounts-autoguide-inventory', 1, 1, 1, 1, 'Auto Guide Vehicle Inventory', 200);

UPDATE permission_category SET level = 55 WHERE level = 35;

