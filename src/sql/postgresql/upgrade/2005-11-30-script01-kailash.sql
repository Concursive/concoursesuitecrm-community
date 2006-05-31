alter table contact add column site_id int references lookup_site_id(code);
