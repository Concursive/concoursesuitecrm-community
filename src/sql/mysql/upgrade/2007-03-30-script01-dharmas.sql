--Table for Graph Types
CREATE TABLE lookup_graph_type(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300) NOT NULL,
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER DEFAULT 0,
 enabled BOOLEAN DEFAULT TRUE,
 entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified timestamp NULL
);
