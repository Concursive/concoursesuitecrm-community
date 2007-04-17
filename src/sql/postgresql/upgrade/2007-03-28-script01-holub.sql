ALTER TABLE campaign_run ADD COLUMN entered TIMESTAMP(3);
UPDATE campaign_run SET entered = CURRENT_TIMESTAMP;
ALTER TABLE campaign_run ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE campaign_run ALTER COLUMN entered SET NOT NULL;

ALTER TABLE campaign_run ADD COLUMN modified TIMESTAMP(3);
UPDATE campaign_run SET modified = CURRENT_TIMESTAMP;
ALTER TABLE campaign_run ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE campaign_run ALTER COLUMN modified SET NOT NULL;

ALTER TABLE scheduled_recipient ADD COLUMN entered TIMESTAMP(3);
UPDATE scheduled_recipient SET entered = CURRENT_TIMESTAMP;
ALTER TABLE scheduled_recipient ALTER COLUMN entered SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE scheduled_recipient ALTER COLUMN entered SET NOT NULL;

ALTER TABLE scheduled_recipient ADD COLUMN modified TIMESTAMP(3);
UPDATE scheduled_recipient SET modified = CURRENT_TIMESTAMP;
ALTER TABLE scheduled_recipient ALTER COLUMN modified SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE scheduled_recipient ALTER COLUMN modified SET NOT NULL;


UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.ScheduledRecipient'
	WHERE element_name = 'scheduledRecipient';

UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.ScheduledRecipientList'
	WHERE element_name = 'scheduledRecipientList';

UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.CampaignRun'
	WHERE element_name = 'campaignRun';

UPDATE sync_table
	SET mapped_class_name = 'org.aspcfs.modules.communications.base.CampaignRunList'
	WHERE element_name = 'campaignRunList';