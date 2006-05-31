-- This schema represents an Campaign User Group Map

-- Each campaign can be associated with several user groups.
-- The users belonging to the user groups will have access to the campaign results.
CREATE GENERATOR campaign_group_map_map_id_seq;
CREATE TABLE campaign_group_map (
  map_id INTEGER NOT NULL PRIMARY KEY,
  campaign_id INTEGER NOT NULL REFERENCES campaign(campaign_id),
  user_group_id INTEGER NOT NULL REFERENCES user_group(group_id)
);
