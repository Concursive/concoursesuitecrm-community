/* 
  New fields for AUTO GUIDE 
  style is the car style: 2000 Ford Explorer XLT
  selling_price_text is a free form price: $299 per month with approved credit
  
  NOTE: also update the pda from sync.sql
*/
ALTER TABLE autoguide_inventory ADD style VARCHAR(40);
ALTER TABLE autoguide_inventory ADD selling_price_text VARCHAR(100);

UPDATE sync_table 
SET create_statement = 
'CREATE TABLE account_inventory (
       inventory_id         int NOT NULL,
       vin                  nvarchar(20) NULL,
       vehicle_id           int NULL,
       account_id           int NULL,
       mileage              nvarchar(20) NULL,
       enteredby            int NULL,
       new                  bit,
       condition            nvarchar(20) NULL,
       comments             nvarchar(255) NULL,
       stock_no             nvarchar(20) NULL,
       ext_color            nvarchar(20) NULL,
       int_color            nvarchar(20) NULL,
       style                nvarchar(40) NULL,
       invoice_price        money NULL,
       selling_price        money NULL,
       selling_price_text   nvarchar(100) NULL,
       modified             datetime NULL,
       sold                 int NULL,
       modifiedby           int NULL,
       record_status_id     int NULL,
       entered              datetime NULL,
       PRIMARY KEY (inventory_id), 
       FOREIGN KEY (account_id)
                             REFERENCES account (account_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
WHERE element_name = 'accountInventoryList' 
  AND mapped_class_name = 'org.aspcfs.modules.media.autoguide.base.InventoryList'
  AND sync_item = 1
;
