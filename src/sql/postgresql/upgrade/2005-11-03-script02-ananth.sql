-- Configure Tickets to have custom_list_views

UPDATE permission_category SET custom_list_views = true WHERE constant = 8;
