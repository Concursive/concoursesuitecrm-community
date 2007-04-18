--Table for Graph Types
CREATE TABLE lookup_graph_type(
 code  SERIAL PRIMARY KEY,
 description VARCHAR(300) NOT NULL,
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER DEFAULT 0,
 enabled BOOLEAN DEFAULT TRUE,
 entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
