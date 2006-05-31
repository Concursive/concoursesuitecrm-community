CREATE TABLE lookup_asset_materials(
 code INT IDENTITY PRIMARY KEY,
 description VARCHAR(300),
 default_item BIT DEFAULT 0,
 level INTEGER,
 enabled BIT DEFAULT 1
);

CREATE TABLE asset_materials_map (
  map_id INT IDENTITY PRIMARY KEY,
  asset_id INTEGER NOT NULL REFERENCES asset(asset_id),
  code INTEGER NOT NULL REFERENCES lookup_asset_materials(code),
  quantity FLOAT,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

