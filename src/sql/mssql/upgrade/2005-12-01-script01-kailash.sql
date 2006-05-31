alter table ticket add site_id int references lookup_site_id(code);
