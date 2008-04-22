ALTER TABLE web_icelet ADD icelet_description_temp varchar(1500);
UPDATE web_icelet SET icelet_description_temp = icelet_description;
ALTER TABLE web_icelet DROP COLUMN icelet_description;
ALTER TABLE web_icelet ADD icelet_description varchar(1500);
UPDATE web_icelet SET icelet_description = icelet_description_temp;
ALTER TABLE web_icelet DROP COLUMN icelet_description_temp;
UPDATE contact_address SET state = 'ACT' WHERE state = 'CT' AND country = 'AUSTRALIA';
UPDATE contact_address SET state = 'NSW' WHERE state = 'NS' AND country = 'AUSTRALIA';
UPDATE contact_address SET state = 'QLD' WHERE state = 'QL' AND country = 'AUSTRALIA';
UPDATE contact_address SET state = 'TAS' WHERE state = 'TS' AND country = 'AUSTRALIA';
UPDATE contact_address SET state = 'VIC' WHERE state = 'VI' AND country = 'AUSTRALIA';
UPDATE contact_address SET state = null WHERE state = 'AS' AND country = 'AUSTRALIA';
UPDATE contact_address SET state = null WHERE state = 'CR' AND country = 'AUSTRALIA';


UPDATE organization_address SET state = 'ACT' WHERE state = 'CT' AND country = 'AUSTRALIA';
UPDATE organization_address SET state = 'NSW' WHERE state = 'NS' AND country = 'AUSTRALIA';
UPDATE organization_address SET state = 'QLD' WHERE state = 'QL' AND country = 'AUSTRALIA';
UPDATE organization_address SET state = 'TAS' WHERE state = 'TS' AND country = 'AUSTRALIA';
UPDATE organization_address SET state = 'VIC' WHERE state = 'VI' AND country = 'AUSTRALIA';
UPDATE organization_address SET state = null WHERE state = 'AS' AND country = 'AUSTRALIA';
UPDATE organization_address SET state = null WHERE state = 'CR' AND country = 'AUSTRALIA';


UPDATE order_address SET state = 'ACT' WHERE state = 'CT' AND country = 'AUSTRALIA';
UPDATE order_address SET state = 'NSW' WHERE state = 'NS' AND country = 'AUSTRALIA';
UPDATE order_address SET state = 'QLD' WHERE state = 'QL' AND country = 'AUSTRALIA';
UPDATE order_address SET state = 'TAS' WHERE state = 'TS' AND country = 'AUSTRALIA';
UPDATE order_address SET state = 'VIC' WHERE state = 'VI' AND country = 'AUSTRALIA';
UPDATE order_address SET state = null WHERE state = 'AS' AND country = 'AUSTRALIA';
UPDATE order_address SET state = null WHERE state = 'CR' AND country = 'AUSTRALIA';