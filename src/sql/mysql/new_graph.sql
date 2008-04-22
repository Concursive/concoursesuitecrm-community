-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Zhenya Zhidok
--  @created    September 24, 2007
--  @version    $Id: Exp $
-- ----------------------------------------------------------------------------

CREATE TABLE lookup_graph_type(
 code INT AUTO_INCREMENT PRIMARY KEY,
 description VARCHAR(300) NOT NULL,
 default_item BOOLEAN DEFAULT FALSE,
 level INTEGER DEFAULT 0,
 enabled BOOLEAN DEFAULT TRUE,
 entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 modified TIMESTAMP NULL
);

CREATE TRIGGER lookup_graph_type_entries BEFORE INSERT ON  lookup_graph_type FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);