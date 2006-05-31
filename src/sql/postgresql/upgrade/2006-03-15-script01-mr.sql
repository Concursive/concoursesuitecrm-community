-- Fix permission category constant, upgrade script left it as -1 for some upgrades
-- Need to re-run any bsh scripts that tried to reference this category unsuccessfully

UPDATE permission_category SET constant = 228051100 WHERE category = 'Leads';
UPDATE permission_category SET constant = 1202041528 WHERE category = 'Documents';

UPDATE permission_category SET constant = 228051100 WHERE constant = -1;

UPDATE sync_client SET enabled = true WHERE enabled IS NULL;
