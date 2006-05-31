UPDATE document_store_user_member SET site_id = (SELECT site_id FROM access ac WHERE ac.user_id = item_id);
