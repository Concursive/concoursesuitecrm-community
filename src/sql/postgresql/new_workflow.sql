/**
 *  PostgreSQL Table Creation
 *
 *@author     mrajkowski
 *@created    May 14, 2003
 *@version    $Id$
 */

CREATE SEQUENCE business_process_e_event_id_seq;
CREATE TABLE business_process_events (
  event_id INTEGER DEFAULT nextval('business_process_e_event_id_seq') NOT NULL PRIMARY KEY,
  second VARCHAR(64) DEFAULT '0',
  minute VARCHAR(64) DEFAULT '*', 
  hour VARCHAR(64) DEFAULT '*',
  dayofmonth VARCHAR(64) DEFAULT '*',
  month VARCHAR(64) DEFAULT '*', 
  dayofweek VARCHAR(64) DEFAULT '*',
  year VARCHAR(64) DEFAULT '*',
  task VARCHAR(255),
  extrainfo VARCHAR(255),
  businessDays VARCHAR(6) DEFAULT 'true',
  enabled BOOLEAN DEFAULT false,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

