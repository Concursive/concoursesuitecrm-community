/** Updates from 3/18/2003 - 4/1/2003 **/
UPDATE sync_table SET mapped_class_name = 'org.aspcfs.modules.service.base.ProcessLog' WHERE mapped_class_name = 'com.darkhorseventures.cfsbase.ProcessLog';

ALTER TABLE contact_address ADD addrline3 VARCHAR(80);
ALTER TABLE organization_address ADD addrline3 VARCHAR(80);

ALTER TABLE contact ADD employee BIT DEFAULT 0;
ALTER TABLE contact ADD personal BIT DEFAULT 0;

update contact set employee = 1 where contact.contact_id in (select contact_id from contact_type_levels ctl where ctl.contact_id = contact.contact_id AND ctl.type_id = '1');
update contact set employee = 0 where employee is null;
update contact set personal = 0;
