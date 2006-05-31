-- Create a new table to group users
CREATE TABLE user_group (
  group_id SERIAL PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL,
  description text,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  enteredby INTEGER NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
  modifiedby INTEGER NOT NULL REFERENCES access(user_id)
);

-- Create the user group map table
CREATE TABLE user_group_map (
  group_map_id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES access(user_id),
  group_id INTEGER NOT NULL REFERENCES user_group(group_id),
  level INTEGER NOT NULL DEFAULT 10,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

