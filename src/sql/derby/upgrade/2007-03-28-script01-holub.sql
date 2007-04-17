ALTER TABLE campaign_run ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE campaign_run ADD COLUMN modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE scheduled_recipient ADD COLUMN entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE scheduled_recipient ADD COLUMN modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;


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
