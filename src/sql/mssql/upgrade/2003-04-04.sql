/* Fix the org_id's which did not get set properly */
UPDATE contact SET org_id = 0 WHERE employee = 1;
