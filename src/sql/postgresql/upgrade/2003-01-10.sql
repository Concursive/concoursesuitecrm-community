/** all aspcfs updated with everything before this line 6/17/02
*/

CREATE TABLE survey_answer (
  id SERIAL primary key,
  question_id int not null,
  comments text default null,
  quant_ans int DEFAULT -1,
  text_ans VARCHAR(100) DEFAULT null,
  enteredby int not null,
  survey_id int not null default -1
);

alter table survey_item add column average float default 0.00;
alter table survey_item add column total1 int default 0;
alter table survey_item add column total2 int default 0;
alter table survey_item add column total3 int default 0;
alter table survey_item add column total4 int default 0;
alter table survey_item add column total5 int default 0;
alter table survey_item add column total6 int default 0;
alter table survey_item add column total7 int default 0;
  
/* June 20 */

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'account', 'com.darkhorseventures.cfsbase.Organization', 5);
 
<<<<<<< upgrade.sql
=======
alter table survey_answer add column survey_id int not null default -1;
 
/* June 24 */
 
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
       invoice_price        money NULL,
       selling_price        money NULL,
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
WHERE system_id = 2
  AND element_name = 'accountInventoryList'
;
>>>>>>> 1.47
