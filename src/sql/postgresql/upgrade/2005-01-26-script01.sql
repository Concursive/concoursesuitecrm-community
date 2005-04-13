-- New column for Logos
ALTER TABLE permission_category ADD COLUMN logos boolean;
ALTER TABLE permission_category ALTER logos SET DEFAULT false;
UPDATE permission_category SET logos = false;
