-- New column for webdav
ALTER TABLE permission_category ADD webdav bit DEFAULT 0;
UPDATE permission_category SET webdav = 0;

-- table representing a webdav module eg: Accounts, Projects etc.
CREATE TABLE webdav(
  id INT IDENTITY PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  class_name VARCHAR(100),
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id)
);

-- A new hash is used for webdav
ALTER TABLE access ADD webdav_password VARCHAR(80);

