CREATE TABLE viewpoint(
  viewpoint_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES access(user_id),
  vp_user_id INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE viewpoint_permission (
 vp_permission_id SERIAL PRIMARY KEY,
 viewpoint_id INT NOT NULL references viewpoint(viewpoint_id),
 permission_id INT NOT NULL references permission(permission_id),
 viewpoint_view BOOLEAN NOT NULL DEFAULT false,
 viewpoint_add BOOLEAN NOT NULL DEFAULT false,
 viewpoint_edit BOOLEAN NOT NULL DEFAULT false,
 viewpoint_delete BOOLEAN NOT NULL DEFAULT false
);

ALTER TABLE permission_category ADD COLUMN viewpoints boolean;
ALTER TABLE permission_category ALTER viewpoints SET DEFAULT FALSE;
UPDATE permission_category SET viewpoints = 'f';
UPDATE permission_category SET viewpoints = 't' WHERE category = 'Pipeline Management';
                           
ALTER TABLE permission ADD COLUMN viewpoints boolean;
ALTER TABLE permission ALTER viewpoints SET DEFAULT FALSE;
UPDATE permission SET viewpoints = 'f';
UPDATE permission SET viewpoints = 't' WHERE permission = 'pipeline';
                  
