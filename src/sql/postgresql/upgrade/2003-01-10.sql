/* May 16, 2002  All servers up-to-date */

CREATE TABLE access_log (
  id serial,
  user_id INT NOT NULL DEFAULT -1,
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(15),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255)
);

UPDATE lookup_stage SET LEVEL = order_id;

INSERT INTO permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) VALUES (6, 'campaign-campaigns-surveys', true, true, true, true, 'Campaign Survey Records', 60);


