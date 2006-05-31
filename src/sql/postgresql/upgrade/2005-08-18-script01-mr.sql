
-- TODO: Use .bsh to retrieve translated description

UPDATE lookup_delivery_options SET description = 'Email Only' WHERE code = 1;
UPDATE lookup_delivery_options SET description = 'Fax only' WHERE code = 2;
UPDATE lookup_delivery_options SET description = 'Letter only' WHERE code = 3;
UPDATE lookup_delivery_options SET description = 'Email then Fax' WHERE code = 4;
UPDATE lookup_delivery_options SET description = 'Email then Letter' WHERE code = 5;
UPDATE lookup_delivery_options SET description = 'Email, Fax, then Letter' WHERE code = 6;
UPDATE lookup_delivery_options SET description = 'Instant Message', enabled = false WHERE code = 7;
UPDATE lookup_delivery_options SET description = 'Secure Socket', enabled = false WHERE code = 8;
UPDATE lookup_delivery_options SET description = 'Broadcast', enabled = true WHERE code = 9;
UPDATE campaign SET send_method_id = 9 where send_method_id = 7;
