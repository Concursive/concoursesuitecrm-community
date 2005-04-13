-- New column for webdav
ALTER TABLE permission_category ADD COLUMN webdav boolean;
ALTER TABLE permission_category ALTER webdav SET DEFAULT false;
UPDATE permission_category SET webdav = false;

-- table representing a webdav module eg: Accounts, Projects etc.
CREATE TABLE webdav (
  id SERIAL PRIMARY KEY,
  category_id INT NOT NULL REFERENCES permission_category(category_id),
  class_name VARCHAR(300) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

-- A new hash is used for webdav
ALTER TABLE access ADD COLUMN webdav_password VARCHAR(80);

