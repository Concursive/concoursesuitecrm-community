/* To alter the data type of column purchase_cost from DECIMAL to float  */
EXEC sp_rename 'asset.purchase_cost', 'purchase_cost_to_delete', 'COLUMN';

ALTER TABLE asset 
ADD purchase_cost float;

UPDATE asset
SET purchase_cost = purchase_cost_to_delete;

ALTER TABLE asset
DROP COLUMN purchase_cost_to_delete;
