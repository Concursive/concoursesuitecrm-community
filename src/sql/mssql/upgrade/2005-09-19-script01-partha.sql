-- Create a new table to group users
CREATE TABLE user_group (
  group_id INT IDENTITY PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  description text,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified DATETIME DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

-- Create the user group map table
CREATE TABLE user_group_map (
  group_map_id INT IDENTITY PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  level INTEGER NOT NULL DEFAULT 10,
  enabled BIT NOT NULL DEFAULT 1,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

