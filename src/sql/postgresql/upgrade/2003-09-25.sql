/* Script for updating fields from date's to datetime's
*  Process: - Create a new column called date_bak
*           - Back up date from old column to new column
*           - Drop old column and recreate with datetime
*           - Copy data from date_bak column to new date column
*           - Delete the date_bak column
*/

/* call alertdate */
ALTER TABLE call_log ADD COLUMN date_bak DATE;
UPDATE call_log set date_bak = alertdate;
ALTER TABLE call_log DROP COLUMN alertdate;
ALTER TABLE call_log ADD COLUMN alertdate TIMESTAMP(3);
UPDATE call_log set alertdate = date_bak;
ALTER TABLE call_log DROP COLUMN date_bak;

/* call followup_date */
ALTER TABLE call_log ADD COLUMN date_bak DATE;
UPDATE call_log set date_bak = followup_date;
ALTER TABLE call_log DROP COLUMN followup_date;
ALTER TABLE call_log ADD COLUMN followup_date TIMESTAMP(3);
UPDATE call_log set followup_date = date_bak;
ALTER TABLE call_log DROP COLUMN date_bak;

/* task duedate */
ALTER TABLE task ADD COLUMN date_bak DATE;
UPDATE task set date_bak = duedate;
ALTER TABLE task DROP COLUMN duedate;
ALTER TABLE task ADD COLUMN duedate TIMESTAMP(3);
UPDATE task set duedate = date_bak;
ALTER TABLE task DROP COLUMN date_bak;

/* opportunity alertdate */
ALTER TABLE opportunity_component ADD COLUMN date_bak DATE;
UPDATE opportunity_component set date_bak = alertdate;
ALTER TABLE opportunity_component DROP COLUMN alertdate;
ALTER TABLE opportunity_component ADD COLUMN alertdate TIMESTAMP(3);
UPDATE opportunity_component set alertdate = date_bak;
ALTER TABLE opportunity_component DROP COLUMN date_bak;


/* opportunity closedate */
ALTER TABLE opportunity_component ADD COLUMN date_bak DATE;
UPDATE opportunity_component set date_bak = closedate;
ALTER TABLE opportunity_component DROP COLUMN closedate;
ALTER TABLE opportunity_component ADD COLUMN closedate TIMESTAMP(3);
UPDATE opportunity_component set closedate = date_bak;
ALTER TABLE opportunity_component DROP COLUMN date_bak;


/* opportunity stagedate */
ALTER TABLE opportunity_component ADD COLUMN date_bak DATE;
UPDATE opportunity_component set date_bak = stagedate;
ALTER TABLE opportunity_component DROP COLUMN stagedate;
ALTER TABLE opportunity_component ADD COLUMN stagedate TIMESTAMP(3);
UPDATE opportunity_component set stagedate = date_bak;
ALTER TABLE opportunity_component DROP COLUMN date_bak;

/* access expires */
ALTER TABLE access ADD COLUMN date_bak DATE;
UPDATE access set date_bak = expires;
ALTER TABLE access DROP COLUMN expires;
ALTER TABLE access ADD COLUMN expires TIMESTAMP(3);
UPDATE access set expires = date_bak;
ALTER TABLE access DROP COLUMN date_bak;


/* communications manager  active_date */
ALTER TABLE campaign ADD COLUMN date_bak DATE;
UPDATE campaign set date_bak = active_date;
ALTER TABLE campaign DROP COLUMN active_date;
ALTER TABLE campaign ADD COLUMN active_date TIMESTAMP(3);
UPDATE campaign set active_date = date_bak;
ALTER TABLE campaign DROP COLUMN date_bak;

/* communications manager  inactive_date */
ALTER TABLE campaign ADD COLUMN date_bak DATE;
UPDATE campaign set date_bak = inactive_date;
ALTER TABLE campaign DROP COLUMN inactive_date;
ALTER TABLE campaign ADD COLUMN inactive_date TIMESTAMP(3);
UPDATE campaign set inactive_date = date_bak;
ALTER TABLE campaign DROP COLUMN date_bak;


/* accounts alertdate  */
ALTER TABLE organization ADD COLUMN date_bak DATE;
UPDATE organization set date_bak = alertdate;
ALTER TABLE organization DROP COLUMN alertdate;
ALTER TABLE organization ADD COLUMN alertdate TIMESTAMP(3);
UPDATE organization set alertdate = date_bak;
ALTER TABLE organization DROP COLUMN date_bak;


/* accounts contract_end  */
ALTER TABLE organization ADD COLUMN date_bak DATE;
UPDATE organization set date_bak = contract_end;
ALTER TABLE organization DROP COLUMN contract_end;
ALTER TABLE organization ADD COLUMN contract_end TIMESTAMP(3);
UPDATE organization set contract_end = date_bak;
ALTER TABLE organization DROP COLUMN date_bak;

