-- This schema represents an Campaign User Group Map

-- Each campaign can be associated with several user groups.
-- The users belonging to the user groups will have access to the campaign results.
CREATE TABLE campaign_group_map (
  map_id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  user_group_id INT NOT NULL REFERENCES user_group(group_id)
);

