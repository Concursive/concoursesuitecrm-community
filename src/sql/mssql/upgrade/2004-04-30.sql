update service_contract
set contract_value = null
where contract_value = -1.0;

alter table ticket add expectation int;

