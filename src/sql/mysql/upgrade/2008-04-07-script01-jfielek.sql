/*
 2008-04-07-script01-jfielek.sql
 Add the sic_text field to the organization and contacts table.
 This is a patch to allow imporation and preservation of SIC codes until
 thelookup lists are fully implemented.
 */

ALTER TABLE contact ADD sic_text VARCHAR(8);
ALTER TABLE organization ADD sic_text VARCHAR(8);
