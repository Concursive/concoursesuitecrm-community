/**
 *  MSSQL Table Creation
 *
 *@author     mrajkowski
 *@created    April 10, 2002
 *@version    $Id$
 */
 
CREATE TABLE sync_client (
  client_id INT IDENTITY PRIMARY KEY,
  type VARCHAR(100),
  version VARCHAR(50),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL
);
