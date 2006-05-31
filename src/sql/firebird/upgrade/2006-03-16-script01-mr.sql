-- Speed ups for heavily referenced columns

CREATE INDEX ticketlink_project_idx ON ticketlink_project(ticket_id);

CREATE INDEX contact_org_id_idx ON contact(org_id);
CREATE INDEX contact_islead_idx ON contact(lead);

CREATE INDEX contact_address_prim_idx ON contact_address(primary_address);

CREATE INDEX contact_email_prim_idx ON contact_emailaddress(primary_email);

CREATE INDEX contact_lead_skip_u_idx ON contact_lead_skipped_map(user_id);
CREATE INDEX contact_lead_read_u_idx ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read_c_idx ON contact_lead_read_map(contact_id);
