/* March 3, 2003 */

CREATE TABLE viewpoint(
  viewpoint_id INT IDENTITY PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  vp_user_id INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BIT DEFAULT 1
);

CREATE TABLE viewpoint_permission (
 vp_permission_id INT IDENTITY PRIMARY KEY,
 viewpoint_id INT NOT NULL REFERENCES viewpoint(viewpoint_id),
 permission_id INT NOT NULL REFERENCES permission(permission_id),
 viewpoint_view BIT NOT NULL DEFAULT 0,
 viewpoint_add BIT NOT NULL DEFAULT 0,
 viewpoint_edit BIT NOT NULL DEFAULT 0,
 viewpoint_delete BIT NOT NULL DEFAULT 0
);

ALTER TABLE permission_category ADD viewpoints BIT DEFAULT 0;
UPDATE permission_category SET viewpoints = 0;
UPDATE permission_category SET viewpoints = 1 WHERE category = 'Pipeline Management';

ALTER TABLE permission ADD viewpoints BIT DEFAULT 0;
UPDATE permission SET viewpoints = 0;
UPDATE permission SET viewpoints = 1 WHERE permission = 'pipeline';

