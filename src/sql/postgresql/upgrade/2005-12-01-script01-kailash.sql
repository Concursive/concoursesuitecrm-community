alter table ticket add column site_id int references lookup_site_id(code);
