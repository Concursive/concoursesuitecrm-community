/*	
  sync init
	
  The synchronization process requires that the system be registered so that
  objects can be mapped to requests.
*/

INSERT INTO sync_system (application_name) VALUES ('Vport Telemarketing');
INSERT INTO sync_system (application_name) VALUES ('Land Mark: Auto Guide PocketPC');
INSERT INTO sync_system (application_name) VALUES ('Street Smart Speakers: Web Portal');
INSERT INTO sync_system (application_name) VALUES ('CFSHttpXMLWriter');
INSERT INTO sync_system (application_name) VALUES ('Fluency');

/* VPORT */

INSERT INTO sync_table (system_id, element_name, mapped_class_name, object_key)
 VALUES (1, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', 'id');

/* AUTO GUIDE */

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', 2);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'user', 'org.aspcfs.modules.admin.base.User', 4);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'account', 'org.aspcfs.modules.accounts.base.Organization', 5);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'accountInventory', 'org.aspcfs.modules.media.autoguide.base.Inventory', 6);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'inventoryOption', 'org.aspcfs.modules.media.autoguide.base.InventoryOption', 8);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'adRun', 'org.aspcfs.modules.media.autoguide.base.AdRun', 10);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item)
 VALUES (2, 'tableList', 'org.aspcfs.modules.service.base.SyncTableList', 12, @FALSE@);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'status_master', null, 14);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'system', null, 16);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'userList', 'org.aspcfs.modules.admin.base.UserList', 50, @TRUE@, 
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
 VALUES (2, 'makeList', 'org.aspcfs.modules.media.autoguide.base.MakeList', 70, @TRUE@, 
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
 VALUES (2, 'modelList', 'org.aspcfs.modules.media.autoguide.base.ModelList', 100, @TRUE@, 
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
 VALUES (2, 'vehicleList', 'org.aspcfs.modules.media.autoguide.base.VehicleList', 130, @TRUE@, 
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
 VALUES (2, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList', 170, @TRUE@, 
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
 VALUES (2, 'accountInventoryList', 'org.aspcfs.modules.media.autoguide.base.InventoryList', 190, @TRUE@,
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
			 selling_price_text		nvarchar(100) NULL,
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
 VALUES (2, 'optionList', 'org.aspcfs.modules.media.autoguide.base.OptionList', 330, @TRUE@,
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
 VALUES (2, 'inventoryOptionList', 'org.aspcfs.modules.media.autoguide.base.InventoryOptionList', 350, @TRUE@,
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
 VALUES (2, 'adTypeList', 'org.aspcfs.utils.web.LookupList', 385, @TRUE@,
'CREATE TABLE ad_type (
       ad_type_id           int NOT NULL,
       ad_type_name         nvarchar(20) NULL,
       PRIMARY KEY (ad_type_id)
)'
);

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id, sync_item, create_statement)
 VALUES (2, 'adRunList', 'org.aspcfs.modules.media.autoguide.base.AdRunList', 390, @TRUE@,
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
 VALUES (2, 'inventory_picture', null, 410, @FALSE@,
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
 VALUES (2, 'preferences', null, 440, @FALSE@,
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
 VALUES (2, 'user_account', null, 460, @FALSE@,
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

INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (2, 'deleteInventoryCache', 'org.aspcfs.modules.media.autoguide.actions.DeleteInventoryCache', 500);


/* CFSWriter */

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupIndustry', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupIndustryList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'systemPrefs', 'org.aspcfs.utils.web.CustomLookupElement');

 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'systemModules', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'systemModulesList', 'org.aspcfs.utils.web.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactTypesList', 'org.aspcfs.utils.web.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupAccountTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupAccountTypesList', 'org.aspcfs.utils.web.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDepartment', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDepartmentList', 'org.aspcfs.utils.web.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgAddressTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgAddressTypesList', 'org.aspcfs.utils.web.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgEmailTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgEmailTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgPhoneTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupOrgPhoneTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupInstantMessengerTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupInstantMessengerTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupEmploymentTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupEmploymentTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupLocale', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupLocaleList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactAddressTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactAddressTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactEmailTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactEmailTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactPhoneTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupContactPhoneTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupStage', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupStageList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDeliveryOptions', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupDeliveryOptionsList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupCallTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupCallTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketCategory', 'org.aspcfs.modules.troubletickets.base.TicketCategory');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketCategoryList', 'org.aspcfs.modules.troubletickets.base.TicketCategoryList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketSeverity', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketSeverityList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupTicketSource', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupTicketSourceList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketPriority', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketPriorityList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueDetailTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupRevenueDetailTypesList', 'org.aspcfs.utils.web.LookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupSurveyTypes', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupSurveyTypesList', 'org.aspcfs.utils.web.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'user', 'org.aspcfs.modules.admin.base.User');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'userList', 'org.aspcfs.modules.admin.base.UserList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contact', 'org.aspcfs.modules.contacts.base.Contact');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactList', 'org.aspcfs.modules.contacts.base.ContactList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, object_key)
 VALUES (4, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', 'id');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketList', 'org.aspcfs.modules.troubletickets.base.TicketList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'account', 'org.aspcfs.modules.accounts.base.Organization');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'role', 'org.aspcfs.modules.admin.base.Role');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'roleList', 'org.aspcfs.modules.admin.base.RoleList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'permissionCategory', 'org.aspcfs.modules.admin.base.PermissionCategory');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'permissionCategoryList', 'org.aspcfs.modules.admin.base.PermissionCategoryList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'permission', 'org.aspcfs.modules.admin.base.Permission');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'permissionList', 'org.aspcfs.modules.admin.base.PermissionList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'rolePermission', 'org.aspcfs.modules.admin.base.RolePermission');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'rolePermissionList', 'org.aspcfs.modules.admin.base.RolePermissionList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'opportunity', 'org.aspcfs.modules.pipeline.base.Opportunity');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'opportunityList', 'org.aspcfs.modules.pipeline.base.OpportunityList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'call', 'org.aspcfs.modules.contacts.base.Call');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'callList', 'org.aspcfs.modules.contacts.base.CallList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldCategory', 'org.aspcfs.modules.base.CustomFieldCategory');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldCategoryList', 'org.aspcfs.modules.base.CustomFieldCategoryList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldGroup', 'org.aspcfs.modules.base.CustomFieldGroup');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldGroupList', 'org.aspcfs.modules.base.CustomFieldGroupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customField', 'org.aspcfs.modules.base.CustomField');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldList', 'org.aspcfs.modules.base.CustomFieldList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldLookup', 'org.aspcfs.utils.web.LookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldLookupList', 'org.aspcfs.utils.web.LookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldRecord', 'org.aspcfs.modules.base.CustomFieldRecord');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldRecordList', 'org.aspcfs.modules.base.CustomFieldRecordList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactEmailAddress', 'org.aspcfs.modules.contacts.base.ContactEmailAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactEmailAddressList', 'org.aspcfs.modules.contacts.base.ContactEmailAddressList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'customFieldData', 'org.aspcfs.modules.base.CustomFieldData');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectActivity', 'org.aspcfs.utils.web.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectActivityList', 'org.aspcfs.utils.web.CustomLookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectIssues', 'org.aspcfs.utils.web.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectIssuesList', 'org.aspcfs.utils.web.CustomLookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectLoe', 'org.aspcfs.utils.web.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectLoeList', 'org.aspcfs.utils.web.CustomLookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectPriority', 'org.aspcfs.utils.web.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectPriorityList', 'org.aspcfs.utils.web.CustomLookupList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectStatus', 'org.aspcfs.utils.web.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'lookupProjectStatusList', 'org.aspcfs.utils.web.CustomLookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'project', 'com.zeroio.iteam.base.Project');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'projectList', 'com.zeroio.iteam.base.ProjectList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'requirement', 'com.zeroio.iteam.base.Requirement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'requirementList', 'com.zeroio.iteam.base.RequirementList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'assignment', 'com.zeroio.iteam.base.Assignment');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'assignmentList', 'com.zeroio.iteam.base.AssignmentList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'issue', 'com.zeroio.iteam.base.Issue');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'issueList', 'com.zeroio.iteam.base.IssueList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'issueReply', 'com.zeroio.iteam.base.IssueReply');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'issueReplyList', 'com.zeroio.iteam.base.IssueReplyList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'teamMember', 'com.zeroio.iteam.base.TeamMember');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'fileItem', 'com.zeroio.iteam.base.FileItem');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'fileItemList', 'com.zeroio.iteam.base.FileItemList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'fileItemVersion', 'com.zeroio.iteam.base.FileItemVersion');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'fileItemVersionList', 'com.zeroio.iteam.base.FileItemVersionList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'fileDownloadLog', 'com.zeroio.iteam.base.FileDownloadLog');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactAddress', 'org.aspcfs.modules.contacts.base.ContactAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactAddressList', 'org.aspcfs.modules.contacts.base.ContactAddressList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactPhoneNumber', 'org.aspcfs.modules.contacts.base.ContactPhoneNumber');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'contactPhoneNumberList', 'org.aspcfs.modules.contacts.base.ContactPhoneNumberList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationPhoneNumber', 'org.aspcfs.modules.accounts.base.OrganizationPhoneNumber');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationPhoneNumberList', 'org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationEmailAddress', 'org.aspcfs.modules.accounts.base.OrganizationEmailAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationEmailAddressList', 'org.aspcfs.modules.accounts.base.OrganizationEmailAddressList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationAddress', 'org.aspcfs.modules.accounts.base.OrganizationAddress');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'organizationAddressList', 'org.aspcfs.modules.accounts.base.OrganizationAddressList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketLog', 'org.aspcfs.modules.troubletickets.base.TicketLog');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'ticketLogList', 'org.aspcfs.modules.troubletickets.base.TicketLogList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'message', 'org.aspcfs.modules.communications.base.Message');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'messageList', 'org.aspcfs.modules.communications.base.MessageList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'searchCriteriaElements', 'org.aspcfs.modules.communications.base.SearchCriteriaList');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'searchCriteriaElementsList', 'org.aspcfs.modules.communications.base.SearchCriteriaListList');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'savedCriteriaElement', 'org.aspcfs.modules.communications.base.SavedCriteriaElement');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'searchFieldElement', 'org.aspcfs.utils.web.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'searchFieldElementList', 'org.aspcfs.utils.web.CustomLookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'revenue', 'org.aspcfs.modules.accounts.base.Revenue');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'revenueList', 'org.aspcfs.modules.accounts.base.RevenueList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'campaign', 'org.aspcfs.modules.communications.base.Campaign');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'campaignList', 'org.aspcfs.modules.communications.base.CampaignList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'scheduledRecipient', 'org.aspcfs.modules.communications.base.ScheduledRecipient');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'scheduledRecipientList', 'org.aspcfs.modules.communications.base.ScheduledRecipientList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'accessLog', 'org.aspcfs.modules.admin.base.AccessLog');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'accessLogList', 'org.aspcfs.modules.admin.base.AccessLogList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'accountTypeLevels', 'org.aspcfs.modules.accounts.base.AccountTypeLevel');

INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'fieldTypes', 'org.aspcfs.utils.web.CustomLookupElement');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'fieldTypesList', 'org.aspcfs.utils.web.CustomLookupList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'excludedRecipient', 'org.aspcfs.modules.communications.base.ExcludedRecipient');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'campaignRun', 'org.aspcfs.modules.communications.base.CampaignRun');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'campaignRunList', 'org.aspcfs.modules.communications.base.CampaignRunList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (4, 'campaignListGroups', 'org.aspcfs.modules.communications.base.CampaignListGroup');
 
/* Fluency */

INSERT INTO sync_table (system_id, element_name, mapped_class_name, object_key)
 VALUES (5, 'ticket', 'org.aspcfs.modules.troubletickets.base.Ticket', 'id');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (5, 'ticketCategory', 'org.aspcfs.modules.troubletickets.base.TicketCategory');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (5, 'ticketCategoryList', 'org.aspcfs.modules.troubletickets.base.TicketCategoryList');
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name, order_id)
 VALUES (5, 'syncClient', 'org.aspcfs.modules.service.base.SyncClient', 2);
 
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (5, 'accountList', 'org.aspcfs.modules.accounts.base.OrganizationList');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (5, 'userList', 'org.aspcfs.modules.admin.base.UserList');
INSERT INTO sync_table (system_id, element_name, mapped_class_name)
 VALUES (5, 'contactList', 'org.aspcfs.modules.contacts.base.ContactList');

