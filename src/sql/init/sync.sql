/*	
  sync init
	
  The synchronization process requires that the system be registered so that
  objects can be mapped to requests.
*/

INSERT INTO sync_system (application_name) VALUES ('Vport Telemarketing');
INSERT INTO sync_system (application_name) VALUES ('Land Mark: Auto Guide PocketPC');
INSERT INTO sync_system (application_name) VALUES ('Street Smart Speakers: Web Portal');
INSERT INTO sync_system (application_name) VALUES ('CFSHttpXMLWriter');

/* VPORT */

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (1, 'ticket', 'com.darkhorseventures.cfsbase.Ticket');

/* AUTO GUIDE */

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'syncClient', 'com.darkhorseventures.cfsbase.SyncClient', 2);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'user', 'com.darkhorseventures.cfsbase.User', 4);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'account', 'com.darkhorseventures.cfsbase.Organization', 5);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'accountInventory', 'com.darkhorseventures.autoguide.base.Inventory', 6);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'inventoryOption', 'com.darkhorseventures.autoguide.base.InventoryOption', 8);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'adRun', 'com.darkhorseventures.autoguide.base.AdRun', 10);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item)
 VALUES (2, 'tableList', 'com.darkhorseventures.cfsbase.SyncTableList', 12, false);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'status_master', null, 14);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'system', null, 16);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'userList', 'com.darkhorseventures.cfsbase.UserList', 50, true, 
'CREATE TABLE users (
       user_id              int NOT NULL,
       record_status_id     int NULL,
       user_name            nvarchar(20) NULL,
       pin                  nvarchar(20) NULL,
       modified             datetime NULL,
       PRIMARY KEY (user_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF18users', null, 60,
'CREATE INDEX XIF18users ON users
(
       record_status_id
)'
);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'makeList', 'com.darkhorseventures.autoguide.base.MakeList', 70, true, 
'CREATE TABLE make (
       make_id              int NOT NULL,
       make_name            nvarchar(20) NULL,
       record_status_id     int NULL,
       entered              datetime NULL,
       modified             datetime NULL,
       enteredby            int NULL,
       modifiedby           int NULL,
       PRIMARY KEY (make_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF2make', null, 80,
'CREATE INDEX XIF2make ON make
(
       record_status_id
)'
);


INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'modelList', 'com.darkhorseventures.autoguide.base.ModelList', 100, true, 
'CREATE TABLE model (
       model_id             int NOT NULL,
       make_id              int NULL,
       record_status_id     int NULL,
       model_name           nvarchar(40) NULL,
       entered              datetime NULL,
       modified             datetime NULL,
       enteredby            int NULL,
       modifiedby           int NULL,
       PRIMARY KEY (model_id), 
       FOREIGN KEY (make_id)
                             REFERENCES make (make_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF3model', null, 110,
'CREATE INDEX XIF3model ON model
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF5model', null, 120,
'CREATE INDEX XIF5model ON model
(
       make_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'vehicleList', 'com.darkhorseventures.autoguide.base.VehicleList', 130, true, 
'CREATE TABLE vehicle (
       year                 nvarchar(4) NOT NULL,
       vehicle_id           int NOT NULL,
       model_id             int NULL,
       make_id              int NULL,
       record_status_id     int NULL,
       entered              datetime NULL,
       modified             datetime NULL,
       enteredby            int NULL,
       modifiedby           int NULL,
       PRIMARY KEY (vehicle_id), 
       FOREIGN KEY (model_id)
                             REFERENCES model (model_id), 
       FOREIGN KEY (make_id)
                             REFERENCES make (make_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF30vehicle', null, 140,
'CREATE INDEX XIF30vehicle ON vehicle
(
       make_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF31vehicle', null, 150,
'CREATE INDEX XIF31vehicle ON vehicle
(
       model_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF4vehicle', null, 160,
'CREATE INDEX XIF4vehicle ON vehicle
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'accountList', 'com.darkhorseventures.cfsbase.OrganizationList', 170, true, 
'CREATE TABLE account (
       account_id           int NOT NULL,
       account_name         nvarchar(80) NULL,
       record_status_id     int NULL,
       address              nvarchar(80) NULL,
       modified             datetime NULL,
       city                 nvarchar(80) NULL,
       state                nvarchar(2) NULL,
       notes                nvarchar(255) NULL,
       zip                  nvarchar(11) NULL,
       phone                nvarchar(20) NULL,
       contact              nvarchar(20) NULL,
       dmv_number           nvarchar(20) NULL,
       owner_id             int NULL,
       entered              datetime NULL,
       enteredby            int NULL,
       modifiedby           int NULL,
       PRIMARY KEY (account_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF16account', null, 180,
'CREATE INDEX XIF16account ON account
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'accountInventoryList', 'com.darkhorseventures.autoguide.base.InventoryList', 190, true,
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
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF10account_inventory', null, 200,
'CREATE INDEX XIF10account_inventory ON account_inventory
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF10account_inventory', null, 210,
'CREATE INDEX XIF11account_inventory ON account_inventory
(
       modifiedby
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF19account_inventory', null, 220,
'CREATE INDEX XIF19account_inventory ON account_inventory
(
       account_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF35account_inventory', null, 230,
'CREATE INDEX XIF35account_inventory ON account_inventory
(
       vehicle_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'optionList', 'com.darkhorseventures.autoguide.base.OptionList', 330, true,
'CREATE TABLE options (
       option_id            int NOT NULL,
       option_name          nvarchar(20) NULL,
       record_status_id     int NULL,
       record_status_date   datetime NULL,
       PRIMARY KEY (option_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF24options', null, 340,
'CREATE INDEX XIF24options ON options
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'inventoryOptionList', 'com.darkhorseventures.autoguide.base.InventoryOptionList', 350, true,
'CREATE TABLE inventory_options (
       inventory_id         int NOT NULL,
       option_id            int NOT NULL,
       record_status_id     int NULL,
       modified             datetime NULL,
       PRIMARY KEY (option_id, inventory_id), 
       FOREIGN KEY (inventory_id)
                             REFERENCES account_inventory (inventory_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (option_id)
                             REFERENCES options (option_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF25inventory_options', null, 360,
'CREATE INDEX XIF25inventory_options ON inventory_options
(
       option_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF27inventory_options', null, 370,
'CREATE INDEX XIF27inventory_options ON inventory_options
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF33inventory_options', null, 380,
'CREATE INDEX XIF33inventory_options ON inventory_options
(
       inventory_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'adTypeList', 'com.darkhorseventures.webutils.LookupList', 385, true,
'CREATE TABLE ad_type (
       ad_type_id           int NOT NULL,
       ad_type_name         nvarchar(20) NULL,
       PRIMARY KEY (ad_type_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'adRunList', 'com.darkhorseventures.autoguide.base.AdRunList', 390, true,
'CREATE TABLE ad_run (
       ad_run_id            int NOT NULL,
       record_status_id     int NULL,
       inventory_id         int NULL,
       ad_type_id           int NULL,
       ad_run_date          datetime NULL,
       has_picture          int NULL,
       modified             datetime NULL,
       entered              datetime NULL,
       modifiedby           int NULL,
       enteredby            int NULL,
       PRIMARY KEY (ad_run_id), 
       FOREIGN KEY (inventory_id)
                             REFERENCES account_inventory (inventory_id), 
       FOREIGN KEY (ad_type_id)
                             REFERENCES ad_type (ad_type_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF22ad_run', null, 400,
'CREATE INDEX XIF22ad_run ON ad_run
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF36ad_run', null, 402,
'CREATE INDEX XIF36ad_run ON ad_run
(
       ad_type_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF37ad_run', null, 404,
'CREATE INDEX XIF37ad_run ON ad_run
(
       inventory_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'inventory_picture', null, 410, false,
'CREATE TABLE inventory_picture (
       picture_name         nvarchar(20) NOT NULL,
       inventory_id         int NOT NULL,
       record_status_id     int NULL,
       entered              datetime NULL,
       modified             datetime NULL,
       modifiedby           int NULL,
       enteredby            int NULL,
       PRIMARY KEY (picture_name, inventory_id), 
       FOREIGN KEY (inventory_id)
                             REFERENCES account_inventory (inventory_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF23inventory_picture', null, 420,
'CREATE INDEX XIF23inventory_picture ON inventory_picture
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF32inventory_picture', null, 430,
'CREATE INDEX XIF32inventory_picture ON inventory_picture
(
       inventory_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'preferences', null, 440, false,
'CREATE TABLE preferences (
       user_id              int NOT NULL,
       record_status_id     int NULL,
       modified             datetime NULL,
       PRIMARY KEY (user_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (user_id)
                             REFERENCES users (user_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF29preferences', null, 450,
'CREATE INDEX XIF29preferences ON preferences
(
       record_status_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'user_account', null, 460, false,
'CREATE TABLE user_account (
       user_id              int NOT NULL,
       account_id           int NOT NULL,
       record_status_id     int NULL,
       modified             datetime NULL,
       PRIMARY KEY (user_id, account_id), 
       FOREIGN KEY (record_status_id)
                             REFERENCES status_master (record_status_id), 
       FOREIGN KEY (account_id)
                             REFERENCES account (account_id), 
       FOREIGN KEY (user_id)
                             REFERENCES users (user_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF14user_account', null, 470,
'CREATE INDEX XIF14user_account ON user_account
(
       user_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF15user_account', null, 480,
'CREATE INDEX XIF15user_account ON user_account
(
       account_id
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, create_statement)
 VALUES (2, 'XIF17user_account', null, 490,
'CREATE INDEX XIF17user_account ON user_account
(
       record_status_id
)'
);


/* CFSWriter */

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupIndustry', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupIndustryList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'systemModules', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'systemModulesList', 'com.darkhorseventures.webutils.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactTypesList', 'com.darkhorseventures.webutils.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupAccountTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupAccountTypesList', 'com.darkhorseventures.webutils.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDepartment', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDepartmentList', 'com.darkhorseventures.webutils.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgAddressTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgAddressTypesList', 'com.darkhorseventures.webutils.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgEmailTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgEmailTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgPhoneTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgPhoneTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupInstantMessengerTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupInstantMessengerTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupEmploymentTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupEmploymentTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupLocale', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupLocaleList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactAddressTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactAddressTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactEmailTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactEmailTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactPhoneTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactPhoneTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupStage', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupStageList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDeliveryOptions', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDeliveryOptionsList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupCallTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupCallTypesList', 'com.darkhorseventures.webutils.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketSeverity', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketSeverityList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupTicketSource', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupTicketSourceList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketPriority', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketPriorityList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueDetailTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueDetailTypesList', 'com.darkhorseventures.webutils.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupSurveyTypes', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupSurveyTypesList', 'com.darkhorseventures.webutils.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'syncClient', 'com.darkhorseventures.cfsbase.SyncClient');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'user', 'com.darkhorseventures.cfsbase.User');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'userList', 'com.darkhorseventures.cfsbase.UserList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contact', 'com.darkhorseventures.cfsbase.Contact');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactList', 'com.darkhorseventures.cfsbase.ContactList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticket', 'com.darkhorseventures.cfsbase.Ticket');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketList', 'com.darkhorseventures.cfsbase.TicketList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'account', 'com.darkhorseventures.cfsbase.Organization');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'accountList', 'com.darkhorseventures.cfsbase.OrganizationList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'role', 'com.darkhorseventures.cfsbase.Role');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'roleList', 'com.darkhorseventures.cfsbase.RoleList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'permission', 'com.darkhorseventures.cfsbase.Permission');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'permissionList', 'com.darkhorseventures.cfsbase.PermissionList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'opportunity', 'com.darkhorseventures.cfsbase.Opportunity');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'opportunityList', 'com.darkhorseventures.cfsbase.OpportunityList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'project', 'com.zeroio.iteam.base.Project');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'projectList', 'com.zeroio.iteam.base.ProjectList');
 
 INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'call', 'com.darkhorseventures.cfsbase.Call');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'callList', 'com.darkhorseventures.cfsbase.CallList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldCategory', 'com.darkhorseventures.cfsbase.CustomFieldCategory');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldCategoryList', 'com.darkhorseventures.cfsbase.CustomFieldCategoryList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldGroup', 'com.darkhorseventures.cfsbase.CustomFieldGroup');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldGroupList', 'com.darkhorseventures.cfsbase.CustomFieldGroupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customField', 'com.darkhorseventures.cfsbase.CustomField');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldList', 'com.darkhorseventures.cfsbase.CustomFieldList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldLookup', 'com.darkhorseventures.webutils.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldLookupList', 'com.darkhorseventures.webutils.LookupList');
 
 INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactEmailAddress', 'com.darkhorseventures.cfsbase.ContactEmailAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactEmailAddressList', 'com.darkhorseventures.cfsbase.ContactEmailAddressList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldData', 'com.darkhorseventures.cfsbase.CustomFieldData');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectActivity', 'com.darkhorseventures.webutils.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectActivityList', 'com.darkhorseventures.webutils.CustomLookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'project', 'com.zeroio.iteam.base.Project');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'projectList', 'com.zeroio.iteam.base.ProjectList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactAddress', 'com.darkhorseventures.cfsbase.ContactAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactAddressList', 'com.darkhorseventures.cfsbase.ContactAddressList');
 
 INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactPhoneNumber', 'com.darkhorseventures.cfsbase.ContactPhoneNumber');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactPhoneNumberList', 'com.darkhorseventures.cfsbase.ContactPhoneNumberList');
 
  INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationPhoneNumber', 'com.darkhorseventures.cfsbase.OrganizationPhoneNumber');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationPhoneNumberList', 'com.darkhorseventures.cfsbase.OrganizationPhoneNumberList');
 
   INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationEmailAddress', 'com.darkhorseventures.cfsbase.OrganizationEmailAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationEmailAddressList', 'com.darkhorseventures.cfsbase.OrganizationEmailAddressList');
 
    INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationAddress', 'com.darkhorseventures.cfsbase.OrganizationAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationAddressList', 'com.darkhorseventures.cfsbase.OrganizationAddressList');
 
     INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketLog', 'com.darkhorseventures.cfsbase.TicketLog');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketLogList', 'com.darkhorseventures.cfsbase.TicketLogList');
 
      INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'message', 'com.darkhorseventures.cfsbase.Message');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'messageList', 'com.darkhorseventures.cfsbase.MessageList');

