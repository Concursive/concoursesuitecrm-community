ALTER TABLE sync_client ADD COLUMN enabled BOOLEAN;
ALTER TABLE sync_client ALTER enabled SET DEFAULT false;

ALTER TABLE sync_client ADD COLUMN code VARCHAR(255);

