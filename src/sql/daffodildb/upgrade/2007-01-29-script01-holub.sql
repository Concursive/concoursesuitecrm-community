-- "Account Search Speedup"

CREATE INDEX org_owner_idx ON organization(owner);
CREATE INDEX org_enteredby_idx ON organization(enteredby);
CREATE INDEX org_modifiedby_idx ON organization(modifiedby);
CREATE INDEX org_industry_temp_code_idx ON organization(industry_temp_code);
CREATE INDEX org_account_size_idx ON organization(account_size);
CREATE INDEX org_org_id_miner_only_idx ON organization(org_id, miner_only);
CREATE INDEX org_site_id_idx ON organization(site_id);
CREATE INDEX org_status_id_idx ON organization(status_id);
CREATE INDEX org_addr_pri_address_idx ON organization_address(primary_address);
CREATE INDEX org_addr_org_id_idx ON organization_address(org_id);
