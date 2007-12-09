--Fields to allow external access to Concourse Suite Community Edition
ALTER TABLE access ADD COLUMN allow_webdav_access BOOLEAN;
ALTER TABLE access ADD COLUMN allow_httpapi_access BOOLEAN;

ALTER TABLE access ALTER COLUMN allow_webdav_access SET DEFAULT true;
ALTER TABLE access ALTER COLUMN allow_httpapi_access SET DEFAULT true;

UPDATE access SET allow_webdav_access = true;
UPDATE access SET allow_httpapi_access = true;

ALTER TABLE access ALTER COLUMN allow_webdav_access SET NOT NULL;
ALTER TABLE access ALTER COLUMN allow_httpapi_access SET NOT NULL;

