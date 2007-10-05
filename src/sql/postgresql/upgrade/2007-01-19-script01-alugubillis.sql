
-- Adding few fields  to knowledge_base table

ALTER TABLE knowledge_base ADD COLUMN status varchar(10) DEFAULT 'Draft' NOT NULL ;
ALTER TABLE knowledge_base ADD COLUMN portal_access_allowed BOOLEAN  NOT NULL DEFAULT false;

