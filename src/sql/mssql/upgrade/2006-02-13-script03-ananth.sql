--Fields to allow external access to Concourse Suite Community Edition
ALTER TABLE access ADD allow_webdav_access BIT DEFAULT 1;
ALTER TABLE access ADD allow_httpapi_access BIT DEFAULT 1;

UPDATE access SET allow_webdav_access = 1;
UPDATE access SET allow_httpapi_access = 1;

ALTER TABLE access ADD allow_webdav_access BIT NOT NULL;
ALTER TABLE access ADD allow_httpapi_access BIT NOT NULL;

