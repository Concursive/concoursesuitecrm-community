ALTER TABLE permission_category ADD importer BIT NOT NULL DEFAULT 0;

ALTER TABLE product_catalog ADD comments varchar(255);  

ALTER TABLE product_catalog ADD import_id INT;  

ALTER TABLE product_catalog ADD site_id INT;
  
ALTER TABLE [product_catalog]  ADD
        FOREIGN KEY
        (
                [site_id]
        ) REFERENCES [lookup_site_id] (
                [code]
        )
GO

ALTER TABLE [product_catalog] ADD
        FOREIGN KEY
        (
                [import_id]
        ) REFERENCES [import] (
                [import_id]
        )
GO
      
ALTER TABLE product_catalog ADD status_id INT;