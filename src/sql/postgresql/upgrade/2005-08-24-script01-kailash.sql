create index organization_address_postalcode_idx on organization_address(postalcode);

alter table contact add column revenue float;
alter table contact add column industry_temp_code integer REFERENCES lookup_industry(code);
alter table contact add column potential float;

alter table organization add column source integer;
alter table organization add constraint source_fk foreign key (source) references lookup_contact_source(code);
alter table organization add column rating integer;
alter table organization add constraint rating_fk foreign key (rating) references lookup_contact_rating(code);
alter table organization add column potential float;

