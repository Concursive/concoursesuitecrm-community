
CREATE TABLE [events] (
	[event_id] [int] IDENTITY (1, 1) NOT NULL ,
	[second] [varchar] (64) NULL ,
	[minute] [varchar] (64) NULL ,
	[hour] [varchar] (64) NULL ,
	[dayofmonth] [varchar] (64) NULL ,
	[month] [varchar] (64) NULL ,
	[dayofweek] [varchar] (64) NULL ,
	[year] [varchar] (64) NULL ,
	[task] [varchar] (255) NULL ,
	[extrainfo] [varchar] (255) NULL ,
	[businessDays] [varchar] (6) NULL ,
	[enabled] [bit] NULL ,
	[entered] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [events_log] (
	[log_id] [int] IDENTITY (1, 1) NOT NULL ,
	[event_id] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[status] [int] NULL ,
	[message] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [sites] (
	[site_id] [int] IDENTITY (1, 1) NOT NULL ,
	[sitecode] [varchar] (255) NOT NULL ,
	[vhost] [varchar] (255) NOT NULL ,
	[dbhost] [varchar] (255) NOT NULL ,
	[dbname] [varchar] (255) NOT NULL ,
	[dbport] [int] NOT NULL ,
	[dbuser] [varchar] (255) NOT NULL ,
	[dbpw] [varchar] (255) NOT NULL ,
	[driver] [varchar] (255) NOT NULL ,
	[code] [varchar] (255) NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [events] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[event_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [sites] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[site_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [events_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[log_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [events] WITH NOCHECK ADD 
	CONSTRAINT [DF__events__second__014935CB] DEFAULT ('0') FOR [second],
	CONSTRAINT [DF__events__minute__023D5A04] DEFAULT ('*') FOR [minute],
	CONSTRAINT [DF__events__hour__03317E3D] DEFAULT ('*') FOR [hour],
	CONSTRAINT [DF__events__dayofmon__0425A276] DEFAULT ('*') FOR [dayofmonth],
	CONSTRAINT [DF__events__month__0519C6AF] DEFAULT ('*') FOR [month],
	CONSTRAINT [DF__events__dayofwee__060DEAE8] DEFAULT ('*') FOR [dayofweek],
	CONSTRAINT [DF__events__year__07020F21] DEFAULT ('*') FOR [year],
	CONSTRAINT [DF__events__business__07F6335A] DEFAULT ('true') FOR [businessDays],
	CONSTRAINT [DF__events__enabled__08EA5793] DEFAULT (0) FOR [enabled],
	CONSTRAINT [DF__events__entered__09DE7BCC] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [sites] WITH NOCHECK ADD 
	CONSTRAINT [DF__sites__vhost__77BFCB91] DEFAULT ('') FOR [vhost],
	CONSTRAINT [DF__sites__dbhost__78B3EFCA] DEFAULT ('') FOR [dbhost],
	CONSTRAINT [DF__sites__dbname__79A81403] DEFAULT ('') FOR [dbname],
	CONSTRAINT [DF__sites__dbport__7A9C383C] DEFAULT (1433) FOR [dbport],
	CONSTRAINT [DF__sites__dbuser__7B905C75] DEFAULT ('') FOR [dbuser],
	CONSTRAINT [DF__sites__dbpw__7C8480AE] DEFAULT ('') FOR [dbpw],
	CONSTRAINT [DF__sites__driver__7D78A4E7] DEFAULT ('') FOR [driver],
	CONSTRAINT [DF__sites__enabled__7E6CC920] DEFAULT (0) FOR [enabled]
GO

ALTER TABLE [events_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__events_lo__enter__0DAF0CB0] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [events_log] ADD 
	 FOREIGN KEY 
	(
		[event_id]
	) REFERENCES [events] (
		[event_id]
	)
GO


CREATE TABLE [access] (
	[user_id] [int] IDENTITY (0, 1) NOT NULL ,
	[username] [varchar] (80) NOT NULL ,
	[password] [varchar] (80) NULL ,
	[contact_id] [int] NULL ,
	[role_id] [int] NULL ,
	[manager_id] [int] NULL ,
	[startofday] [int] NULL ,
	[endofday] [int] NULL ,
	[locale] [varchar] (255) NULL ,
	[timezone] [varchar] (255) NULL ,
	[last_ip] [varchar] (15) NULL ,
	[last_login] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[expires] [datetime] NULL ,
	[alias] [int] NULL ,
	[assistant] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [access_log] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[user_id] [int] NOT NULL ,
	[username] [varchar] (80) NOT NULL ,
	[ip] [varchar] (15) NULL ,
	[entered] [datetime] NOT NULL ,
	[browser] [varchar] (255) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [account_type_levels] (
	[org_id] [int] NOT NULL ,
	[type_id] [int] NOT NULL ,
	[level] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modified] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [action_item] (
	[item_id] [int] IDENTITY (1, 1) NOT NULL ,
	[action_id] [int] NOT NULL ,
	[link_item_id] [int] NOT NULL ,
	[completedate] [datetime] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [action_item_log] (
	[log_id] [int] IDENTITY (1, 1) NOT NULL ,
	[item_id] [int] NOT NULL ,
	[link_item_id] [int] NULL ,
	[type] [int] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [action_list] (
	[action_id] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (255) NOT NULL ,
	[owner] [int] NOT NULL ,
	[completedate] [datetime] NULL ,
	[link_module_id] [int] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [active_campaign_groups] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[campaign_id] [int] NOT NULL ,
	[groupname] [varchar] (80) NOT NULL ,
	[groupcriteria] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [active_survey] (
	[active_survey_id] [int] IDENTITY (1, 1) NOT NULL ,
	[campaign_id] [int] NOT NULL ,
	[name] [varchar] (80) NOT NULL ,
	[description] [varchar] (255) NULL ,
	[intro] [text] NULL ,
	[outro] [text] NULL ,
	[itemLength] [int] NULL ,
	[type] [int] NOT NULL ,
	[enabled] [bit] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [active_survey_answer_avg] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[question_id] [int] NOT NULL ,
	[item_id] [int] NOT NULL ,
	[total] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [active_survey_answer_items] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[item_id] [int] NOT NULL ,
	[answer_id] [int] NOT NULL ,
	[comments] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [active_survey_answers] (
	[answer_id] [int] IDENTITY (1, 1) NOT NULL ,
	[response_id] [int] NOT NULL ,
	[question_id] [int] NOT NULL ,
	[comments] [text] NULL ,
	[quant_ans] [int] NULL ,
	[text_ans] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [active_survey_items] (
	[item_id] [int] IDENTITY (1, 1) NOT NULL ,
	[question_id] [int] NOT NULL ,
	[type] [int] NULL ,
	[description] [varchar] (255) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [active_survey_questions] (
	[question_id] [int] IDENTITY (1, 1) NOT NULL ,
	[active_survey_id] [int] NULL ,
	[type] [int] NOT NULL ,
	[description] [varchar] (255) NULL ,
	[required] [bit] NOT NULL ,
	[position] [int] NOT NULL ,
	[average] [float] NULL ,
	[total1] [int] NULL ,
	[total2] [int] NULL ,
	[total3] [int] NULL ,
	[total4] [int] NULL ,
	[total5] [int] NULL ,
	[total6] [int] NULL ,
	[total7] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [active_survey_responses] (
	[response_id] [int] IDENTITY (1, 1) NOT NULL ,
	[active_survey_id] [int] NOT NULL ,
	[contact_id] [int] NOT NULL ,
	[unique_code] [varchar] (255) NULL ,
	[ip_address] [varchar] (15) NOT NULL ,
	[entered] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_ad_run] (
	[ad_run_id] [int] IDENTITY (1, 1) NOT NULL ,
	[inventory_id] [int] NOT NULL ,
	[run_date] [datetime] NOT NULL ,
	[ad_type] [varchar] (20) NULL ,
	[include_photo] [bit] NULL ,
	[complete_date] [datetime] NULL ,
	[completedby] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_ad_run_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (20) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[entered] [datetime] NOT NULL ,
	[modified] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_inventory] (
	[inventory_id] [int] IDENTITY (1, 1) NOT NULL ,
	[vehicle_id] [int] NOT NULL ,
	[account_id] [int] NULL ,
	[vin] [varchar] (20) NULL ,
	[mileage] [varchar] (20) NULL ,
	[is_new] [bit] NULL ,
	[condition] [varchar] (20) NULL ,
	[comments] [varchar] (255) NULL ,
	[stock_no] [varchar] (20) NULL ,
	[ext_color] [varchar] (20) NULL ,
	[int_color] [varchar] (20) NULL ,
	[style] [varchar] (40) NULL ,
	[invoice_price] [float] NULL ,
	[selling_price] [float] NULL ,
	[selling_price_text] [varchar] (100) NULL ,
	[sold] [bit] NULL ,
	[status] [varchar] (20) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_inventory_options] (
	[inventory_id] [int] NOT NULL ,
	[option_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_make] (
	[make_id] [int] IDENTITY (1, 1) NOT NULL ,
	[make_name] [varchar] (30) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_model] (
	[model_id] [int] IDENTITY (1, 1) NOT NULL ,
	[make_id] [int] NOT NULL ,
	[model_name] [varchar] (50) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_options] (
	[option_id] [int] IDENTITY (1, 1) NOT NULL ,
	[option_name] [varchar] (20) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[entered] [datetime] NOT NULL ,
	[modified] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [autoguide_vehicle] (
	[vehicle_id] [int] IDENTITY (1, 1) NOT NULL ,
	[year] [varchar] (4) NOT NULL ,
	[make_id] [int] NOT NULL ,
	[model_id] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process] (
	[process_id] [int] IDENTITY (1, 1) NOT NULL ,
	[process_name] [varchar] (255) NOT NULL ,
	[description] [varchar] (510) NULL ,
	[type_id] [int] NOT NULL ,
	[link_module_id] [int] NOT NULL ,
	[component_start_id] [int] NULL ,
	[enabled] [bit] NOT NULL ,
	[entered] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_component] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[process_id] [int] NOT NULL ,
	[component_id] [int] NOT NULL ,
	[parent_id] [int] NULL ,
	[parent_result_id] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_component_library] (
	[component_id] [int] IDENTITY (1, 1) NOT NULL ,
	[component_name] [varchar] (255) NOT NULL ,
	[type_id] [int] NOT NULL ,
	[class_name] [varchar] (255) NOT NULL ,
	[description] [varchar] (510) NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_component_parameter] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[component_id] [int] NOT NULL ,
	[parameter_id] [int] NOT NULL ,
	[param_value] [varchar] (4000) NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_component_result_lookup] (
	[result_id] [int] IDENTITY (1, 1) NOT NULL ,
	[component_id] [int] NOT NULL ,
	[return_id] [int] NOT NULL ,
	[description] [varchar] (255) NULL ,
	[level] [int] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_events] (
	[event_id] [int] IDENTITY (1, 1) NOT NULL ,
	[second] [varchar] (64) NULL ,
	[minute] [varchar] (64) NULL ,
	[hour] [varchar] (64) NULL ,
	[dayofmonth] [varchar] (64) NULL ,
	[month] [varchar] (64) NULL ,
	[dayofweek] [varchar] (64) NULL ,
	[year] [varchar] (64) NULL ,
	[task] [varchar] (255) NULL ,
	[extrainfo] [varchar] (255) NULL ,
	[businessDays] [varchar] (6) NULL ,
	[enabled] [bit] NULL ,
	[entered] [datetime] NOT NULL ,
	[process_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_hook] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[trigger_id] [int] NOT NULL ,
	[process_id] [int] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_hook_library] (
	[hook_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_module_id] [int] NOT NULL ,
	[hook_class] [varchar] (255) NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_hook_triggers] (
	[trigger_id] [int] IDENTITY (1, 1) NOT NULL ,
	[action_type_id] [int] NOT NULL ,
	[hook_id] [int] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_log] (
	[process_name] [varchar] (255) NOT NULL ,
	[anchor] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_parameter] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[process_id] [int] NOT NULL ,
	[param_name] [varchar] (255) NULL ,
	[param_value] [varchar] (4000) NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [business_process_parameter_library] (
	[parameter_id] [int] IDENTITY (1, 1) NOT NULL ,
	[component_id] [int] NULL ,
	[param_name] [varchar] (255) NULL ,
	[description] [varchar] (510) NULL ,
	[default_value] [varchar] (4000) NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [call_log] (
	[call_id] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NULL ,
	[contact_id] [int] NULL ,
	[opp_id] [int] NULL ,
	[call_type_id] [int] NULL ,
	[length] [int] NULL ,
	[subject] [varchar] (255) NULL ,
	[notes] [text] NULL ,
	[followup_date] [datetime] NULL ,
	[alertdate] [datetime] NULL ,
	[followup_notes] [text] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[alert] [varchar] (100) NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [campaign] (
	[campaign_id] [int] IDENTITY (1, 1) NOT NULL ,
	[name] [varchar] (80) NOT NULL ,
	[description] [varchar] (255) NULL ,
	[list_id] [int] NULL ,
	[message_id] [int] NULL ,
	[reply_addr] [varchar] (255) NULL ,
	[subject] [varchar] (255) NULL ,
	[message] [text] NULL ,
	[status_id] [int] NULL ,
	[status] [varchar] (255) NULL ,
	[active] [bit] NULL ,
	[active_date] [datetime] NULL ,
	[send_method_id] [int] NOT NULL ,
	[inactive_date] [datetime] NULL ,
	[approval_date] [datetime] NULL ,
	[approvedby] [int] NULL ,
	[enabled] [bit] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[type] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [campaign_list_groups] (
	[campaign_id] [int] NOT NULL ,
	[group_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [campaign_run] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[campaign_id] [int] NOT NULL ,
	[status] [int] NOT NULL ,
	[run_date] [datetime] NOT NULL ,
	[total_contacts] [int] NULL ,
	[total_sent] [int] NULL ,
	[total_replied] [int] NULL ,
	[total_bounced] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [campaign_survey_link] (
	[campaign_id] [int] NULL ,
	[survey_id] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [cfsinbox_message] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[subject] [varchar] (255) NULL ,
	[body] [text] NOT NULL ,
	[reply_id] [int] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[sent] [datetime] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[type] [int] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[delete_flag] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [cfsinbox_messagelink] (
	[id] [int] NOT NULL ,
	[sent_to] [int] NOT NULL ,
	[status] [int] NOT NULL ,
	[viewed] [datetime] NULL ,
	[enabled] [bit] NOT NULL ,
	[sent_from] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [contact] (
	[contact_id] [int] IDENTITY (1, 1) NOT NULL ,
	[user_id] [int] NULL ,
	[org_id] [int] NULL ,
	[company] [varchar] (255) NULL ,
	[title] [varchar] (80) NULL ,
	[department] [int] NULL ,
	[super] [int] NULL ,
	[namesalutation] [varchar] (80) NULL ,
	[namelast] [varchar] (80) NOT NULL ,
	[namefirst] [varchar] (80) NOT NULL ,
	[namemiddle] [varchar] (80) NULL ,
	[namesuffix] [varchar] (80) NULL ,
	[assistant] [int] NULL ,
	[birthdate] [datetime] NULL ,
	[notes] [text] NULL ,
	[site] [int] NULL ,
	[imname] [varchar] (30) NULL ,
	[imservice] [int] NULL ,
	[locale] [int] NULL ,
	[employee_id] [varchar] (80) NULL ,
	[employmenttype] [int] NULL ,
	[startofday] [varchar] (10) NULL ,
	[endofday] [varchar] (10) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL ,
	[owner] [int] NULL ,
	[custom1] [int] NULL ,
	[url] [varchar] (100) NULL ,
	[primary_contact] [bit] NULL ,
	[employee] [bit] NULL ,
	[org_name] [varchar] (255) NULL ,
	[access_type] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [contact_address] (
	[address_id] [int] IDENTITY (1, 1) NOT NULL ,
	[contact_id] [int] NULL ,
	[address_type] [int] NULL ,
	[addrline1] [varchar] (80) NULL ,
	[addrline2] [varchar] (80) NULL ,
	[addrline3] [varchar] (80) NULL ,
	[city] [varchar] (80) NULL ,
	[state] [varchar] (80) NULL ,
	[country] [varchar] (80) NULL ,
	[postalcode] [varchar] (12) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [contact_emailaddress] (
	[emailaddress_id] [int] IDENTITY (1, 1) NOT NULL ,
	[contact_id] [int] NULL ,
	[emailaddress_type] [int] NULL ,
	[email] [varchar] (256) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [contact_phone] (
	[phone_id] [int] IDENTITY (1, 1) NOT NULL ,
	[contact_id] [int] NULL ,
	[phone_type] [int] NULL ,
	[number] [varchar] (30) NULL ,
	[extension] [varchar] (10) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [contact_type_levels] (
	[contact_id] [int] NOT NULL ,
	[type_id] [int] NOT NULL ,
	[level] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modified] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [custom_field_category] (
	[module_id] [int] NOT NULL ,
	[category_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_name] [varchar] (255) NOT NULL ,
	[level] [int] NULL ,
	[description] [text] NULL ,
	[start_date] [datetime] NULL ,
	[end_date] [datetime] NULL ,
	[default_item] [bit] NULL ,
	[entered] [datetime] NOT NULL ,
	[enabled] [bit] NULL ,
	[multiple_records] [bit] NULL ,
	[read_only] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [custom_field_data] (
	[record_id] [int] NOT NULL ,
	[field_id] [int] NOT NULL ,
	[selected_item_id] [int] NULL ,
	[entered_value] [text] NULL ,
	[entered_number] [int] NULL ,
	[entered_float] [float] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [custom_field_group] (
	[category_id] [int] NOT NULL ,
	[group_id] [int] IDENTITY (1, 1) NOT NULL ,
	[group_name] [varchar] (255) NOT NULL ,
	[level] [int] NULL ,
	[description] [text] NULL ,
	[start_date] [datetime] NULL ,
	[end_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [custom_field_info] (
	[group_id] [int] NOT NULL ,
	[field_id] [int] IDENTITY (1, 1) NOT NULL ,
	[field_name] [varchar] (255) NOT NULL ,
	[level] [int] NULL ,
	[field_type] [int] NOT NULL ,
	[validation_type] [int] NULL ,
	[required] [bit] NULL ,
	[parameters] [text] NULL ,
	[start_date] [datetime] NULL ,
	[end_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enabled] [bit] NULL ,
	[additional_text] [varchar] (255) NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [custom_field_lookup] (
	[field_id] [int] NOT NULL ,
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (255) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[start_date] [datetime] NULL ,
	[end_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [custom_field_record] (
	[link_module_id] [int] NOT NULL ,
	[link_item_id] [int] NOT NULL ,
	[category_id] [int] NOT NULL ,
	[record_id] [int] IDENTITY (1, 1) NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [database_version] (
	[version_id] [int] IDENTITY (1, 1) NOT NULL ,
	[script_filename] [varchar] (255) NOT NULL ,
	[script_version] [varchar] (255) NOT NULL ,
	[entered] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [excluded_recipient] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[campaign_id] [int] NOT NULL ,
	[contact_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [field_types] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[data_typeid] [int] NOT NULL ,
	[data_type] [varchar] (20) NULL ,
	[operator] [varchar] (50) NULL ,
	[display_text] [varchar] (50) NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_business_rules] (
	[rule_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_contents] (
	[help_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NULL ,
	[link_module_id] [int] NULL ,
	[module] [varchar] (255) NULL ,
	[section] [varchar] (255) NULL ,
	[subsection] [varchar] (255) NULL ,
	[title] [varchar] (255) NULL ,
	[description] [text] NULL ,
	[nextcontent] [int] NULL ,
	[prevcontent] [int] NULL ,
	[upcontent] [int] NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [help_faqs] (
	[faq_id] [int] IDENTITY (1, 1) NOT NULL ,
	[owning_module_id] [int] NOT NULL ,
	[question] [varchar] (1000) NOT NULL ,
	[answer] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_features] (
	[feature_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[link_feature_id] [int] NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL ,
	[level] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_module] (
	[module_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NULL ,
	[module_brief_description] [text] NULL ,
	[module_detail_description] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [help_notes] (
	[note_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[completedate] [datetime] NULL ,
	[completedby] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_related_links] (
	[relatedlink_id] [int] IDENTITY (1, 1) NOT NULL ,
	[owning_module_id] [int] NULL ,
	[linkto_content_id] [int] NULL ,
	[displaytext] [varchar] (255) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_tableof_contents] (
	[content_id] [int] IDENTITY (1, 1) NOT NULL ,
	[displaytext] [varchar] (255) NULL ,
	[firstchild] [int] NULL ,
	[nextsibling] [int] NULL ,
	[parent] [int] NULL ,
	[category_id] [int] NULL ,
	[contentlevel] [int] NOT NULL ,
	[contentorder] [int] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_tableofcontentitem_links] (
	[link_id] [int] IDENTITY (1, 1) NOT NULL ,
	[global_link_id] [int] NOT NULL ,
	[linkto_content_id] [int] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [help_tips] (
	[tip_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_help_id] [int] NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_access_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[link_module_id] [int] NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[rule_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_account_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_call_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_contact_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[user_id] [int] NULL ,
	[category] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_contactaddress_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_contactemail_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_contactphone_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_delivery_options] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_department] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_employment_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_help_features] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (1000) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_industry] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[order_id] [int] NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_instantmessenger_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_lists_lookup] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[module_id] [int] NOT NULL ,
	[lookup_id] [int] NOT NULL ,
	[class_name] [varchar] (20) NULL ,
	[table_name] [varchar] (60) NULL ,
	[level] [int] NULL ,
	[description] [text] NULL ,
	[entered] [datetime] NULL ,
	[category_id] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [lookup_locale] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_opportunity_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[order_id] [int] NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_orgaddress_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_orgemail_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_orgphone_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_project_activity] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL ,
	[template_id] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_project_issues] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_project_loe] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[base_value] [int] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_project_priority] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL ,
	[graphic] [varchar] (75) NULL ,
	[type] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_project_status] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL ,
	[group_id] [int] NOT NULL ,
	[graphic] [varchar] (75) NULL ,
	[type] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_revenue_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_revenuedetail_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_stage] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[order_id] [int] NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_survey_types] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_task_category] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_task_loe] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_task_priority] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (50) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [lookup_ticketsource] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [message] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[name] [varchar] (80) NOT NULL ,
	[description] [varchar] (255) NULL ,
	[template_id] [int] NULL ,
	[subject] [varchar] (255) NULL ,
	[body] [text] NULL ,
	[reply_addr] [varchar] (100) NULL ,
	[url] [varchar] (255) NULL ,
	[img] [varchar] (80) NULL ,
	[enabled] [bit] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[access_type] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [message_template] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[name] [varchar] (80) NOT NULL ,
	[description] [varchar] (255) NULL ,
	[template_file] [varchar] (80) NULL ,
	[num_imgs] [int] NULL ,
	[num_urls] [int] NULL ,
	[enabled] [bit] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [module_field_categorylink] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[module_id] [int] NOT NULL ,
	[category_id] [int] NOT NULL ,
	[level] [int] NULL ,
	[description] [text] NULL ,
	[entered] [datetime] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [news] (
	[rec_id] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NULL ,
	[url] [text] NULL ,
	[base] [text] NULL ,
	[headline] [text] NULL ,
	[body] [text] NULL ,
	[dateEntered] [datetime] NULL ,
	[type] [char] (1) NULL ,
	[created] [datetime] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [notification] (
	[notification_id] [int] IDENTITY (1, 1) NOT NULL ,
	[notify_user] [int] NOT NULL ,
	[module] [varchar] (255) NOT NULL ,
	[item_id] [int] NOT NULL ,
	[item_modified] [datetime] NOT NULL ,
	[attempt] [datetime] NOT NULL ,
	[notify_type] [varchar] (30) NULL ,
	[subject] [text] NULL ,
	[message] [text] NULL ,
	[result] [int] NOT NULL ,
	[errorMessage] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [opportunity_component] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[opp_id] [int] NULL ,
	[owner] [int] NOT NULL ,
	[description] [varchar] (80) NULL ,
	[closedate] [datetime] NOT NULL ,
	[closeprob] [float] NULL ,
	[terms] [float] NULL ,
	[units] [char] (1) NULL ,
	[lowvalue] [float] NULL ,
	[guessvalue] [float] NULL ,
	[highvalue] [float] NULL ,
	[stage] [int] NULL ,
	[stagedate] [datetime] NOT NULL ,
	[commission] [float] NULL ,
	[type] [char] (1) NULL ,
	[alertdate] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[closed] [datetime] NULL ,
	[alert] [varchar] (100) NULL ,
	[enabled] [bit] NOT NULL ,
	[notes] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [opportunity_component_levels] (
	[opp_id] [int] NOT NULL ,
	[type_id] [int] NOT NULL ,
	[level] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modified] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [opportunity_header] (
	[opp_id] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (80) NULL ,
	[acctlink] [int] NULL ,
	[contactlink] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [organization] (
	[org_id] [int] IDENTITY (0, 1) NOT NULL ,
	[name] [varchar] (80) NOT NULL ,
	[account_number] [varchar] (50) NULL ,
	[account_group] [int] NULL ,
	[url] [text] NULL ,
	[revenue] [float] NULL ,
	[employees] [int] NULL ,
	[notes] [text] NULL ,
	[sic_code] [varchar] (40) NULL ,
	[ticker_symbol] [varchar] (10) NULL ,
	[taxid] [char] (80) NULL ,
	[lead] [varchar] (40) NULL ,
	[sales_rep] [int] NOT NULL ,
	[miner_only] [bit] NOT NULL ,
	[defaultlocale] [int] NULL ,
	[fiscalmonth] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL ,
	[industry_temp_code] [smallint] NULL ,
	[owner] [int] NULL ,
	[duplicate_id] [int] NULL ,
	[custom1] [int] NULL ,
	[custom2] [int] NULL ,
	[contract_end] [datetime] NULL ,
	[alertdate] [datetime] NULL ,
	[alert] [varchar] (100) NULL ,
	[custom_data] [text] NULL ,
	[namesalutation] [varchar] (80) NULL ,
	[namelast] [varchar] (80) NULL ,
	[namefirst] [varchar] (80) NULL ,
	[namemiddle] [varchar] (80) NULL ,
	[namesuffix] [varchar] (80) NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [organization_address] (
	[address_id] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NULL ,
	[address_type] [int] NULL ,
	[addrline1] [varchar] (80) NULL ,
	[addrline2] [varchar] (80) NULL ,
	[addrline3] [varchar] (80) NULL ,
	[city] [varchar] (80) NULL ,
	[state] [varchar] (80) NULL ,
	[country] [varchar] (80) NULL ,
	[postalcode] [varchar] (12) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [organization_emailaddress] (
	[emailaddress_id] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NULL ,
	[emailaddress_type] [int] NULL ,
	[email] [varchar] (256) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [organization_phone] (
	[phone_id] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NULL ,
	[phone_type] [int] NULL ,
	[number] [varchar] (30) NULL ,
	[extension] [varchar] (10) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [permission] (
	[permission_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NOT NULL ,
	[permission] [varchar] (80) NOT NULL ,
	[permission_view] [bit] NOT NULL ,
	[permission_add] [bit] NOT NULL ,
	[permission_edit] [bit] NOT NULL ,
	[permission_delete] [bit] NOT NULL ,
	[description] [varchar] (255) NOT NULL ,
	[level] [int] NOT NULL ,
	[enabled] [bit] NOT NULL ,
	[active] [bit] NOT NULL ,
	[viewpoints] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [permission_category] (
	[category_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category] [varchar] (80) NULL ,
	[description] [varchar] (255) NULL ,
	[level] [int] NOT NULL ,
	[enabled] [bit] NOT NULL ,
	[active] [bit] NOT NULL ,
	[folders] [bit] NOT NULL ,
	[lookups] [bit] NOT NULL ,
	[viewpoints] [bit] NULL ,
	[categories] [bit] NULL ,
	[scheduled_events] [bit] NULL ,
	[object_events] [bit] NULL ,
	[reports] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [process_log] (
	[process_id] [int] IDENTITY (1, 1) NOT NULL ,
	[system_id] [int] NOT NULL ,
	[client_id] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[process_name] [varchar] (255) NULL ,
	[process_version] [varchar] (20) NULL ,
	[status] [int] NULL ,
	[message] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [project_assignments] (
	[assignment_id] [int] IDENTITY (1, 1) NOT NULL ,
	[project_id] [int] NOT NULL ,
	[requirement_id] [int] NULL ,
	[assignedBy] [int] NULL ,
	[user_assign_id] [int] NULL ,
	[activity_id] [int] NULL ,
	[technology] [varchar] (50) NULL ,
	[role] [varchar] (255) NULL ,
	[estimated_loevalue] [int] NULL ,
	[estimated_loetype] [int] NULL ,
	[actual_loevalue] [int] NULL ,
	[actual_loetype] [int] NULL ,
	[priority_id] [int] NULL ,
	[assign_date] [datetime] NULL ,
	[est_start_date] [datetime] NULL ,
	[start_date] [datetime] NULL ,
	[due_date] [datetime] NULL ,
	[status_id] [int] NULL ,
	[status_date] [datetime] NOT NULL ,
	[complete_date] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [project_assignments_status] (
	[status_id] [int] IDENTITY (1, 1) NOT NULL ,
	[assignment_id] [int] NOT NULL ,
	[user_id] [int] NOT NULL ,
	[description] [text] NOT NULL ,
	[status_date] [datetime] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [project_files] (
	[item_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_module_id] [int] NOT NULL ,
	[link_item_id] [int] NOT NULL ,
	[folder_id] [int] NULL ,
	[client_filename] [varchar] (255) NOT NULL ,
	[filename] [varchar] (255) NOT NULL ,
	[subject] [varchar] (500) NOT NULL ,
	[size] [int] NULL ,
	[version] [float] NULL ,
	[enabled] [bit] NULL ,
	[downloads] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [project_files_download] (
	[item_id] [int] NOT NULL ,
	[version] [float] NULL ,
	[user_download_id] [int] NULL ,
	[download_date] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [project_files_version] (
	[item_id] [int] NULL ,
	[client_filename] [varchar] (255) NOT NULL ,
	[filename] [varchar] (255) NOT NULL ,
	[subject] [varchar] (500) NOT NULL ,
	[size] [int] NULL ,
	[version] [float] NULL ,
	[enabled] [bit] NULL ,
	[downloads] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [project_folders] (
	[folder_id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_module_id] [int] NOT NULL ,
	[link_item_id] [int] NOT NULL ,
	[subject] [varchar] (255) NOT NULL ,
	[description] [text] NULL ,
	[parent] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [project_issue_replies] (
	[reply_id] [int] IDENTITY (1, 1) NOT NULL ,
	[issue_id] [int] NOT NULL ,
	[reply_to] [int] NULL ,
	[subject] [varchar] (50) NOT NULL ,
	[message] [text] NOT NULL ,
	[importance] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [project_issues] (
	[issue_id] [int] IDENTITY (1, 1) NOT NULL ,
	[project_id] [int] NOT NULL ,
	[type_id] [int] NULL ,
	[subject] [varchar] (255) NOT NULL ,
	[message] [text] NOT NULL ,
	[importance] [int] NULL ,
	[enabled] [bit] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [project_requirements] (
	[requirement_id] [int] IDENTITY (1, 1) NOT NULL ,
	[project_id] [int] NOT NULL ,
	[submittedBy] [varchar] (50) NULL ,
	[departmentBy] [varchar] (30) NULL ,
	[shortDescription] [varchar] (255) NOT NULL ,
	[description] [text] NOT NULL ,
	[dateReceived] [datetime] NULL ,
	[estimated_loevalue] [int] NULL ,
	[estimated_loetype] [int] NULL ,
	[actual_loevalue] [int] NULL ,
	[actual_loetype] [int] NULL ,
	[deadline] [datetime] NULL ,
	[approvedBy] [int] NULL ,
	[approvalDate] [datetime] NULL ,
	[closedBy] [int] NULL ,
	[closeDate] [datetime] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [project_team] (
	[project_id] [int] NOT NULL ,
	[user_id] [int] NOT NULL ,
	[userLevel] [int] NULL ,
	[entered] [datetime] NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [projects] (
	[project_id] [int] IDENTITY (1, 1) NOT NULL ,
	[group_id] [int] NULL ,
	[department_id] [int] NULL ,
	[template_id] [int] NULL ,
	[title] [varchar] (100) NOT NULL ,
	[shortDescription] [varchar] (200) NOT NULL ,
	[requestedBy] [varchar] (50) NULL ,
	[requestedDept] [varchar] (50) NULL ,
	[requestDate] [datetime] NULL ,
	[approvalDate] [datetime] NULL ,
	[closeDate] [datetime] NULL ,
	[owner] [int] NULL ,
	[entered] [datetime] NULL ,
	[enteredBy] [int] NOT NULL ,
	[modified] [datetime] NULL ,
	[modifiedBy] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [report] (
	[report_id] [int] IDENTITY (1, 1) NOT NULL ,
	[category_id] [int] NOT NULL ,
	[permission_id] [int] NULL ,
	[filename] [varchar] (300) NOT NULL ,
	[type] [int] NOT NULL ,
	[title] [varchar] (300) NOT NULL ,
	[description] [varchar] (1024) NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NOT NULL ,
	[custom] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [report_criteria] (
	[criteria_id] [int] IDENTITY (1, 1) NOT NULL ,
	[report_id] [int] NOT NULL ,
	[owner] [int] NOT NULL ,
	[subject] [varchar] (512) NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [report_criteria_parameter] (
	[parameter_id] [int] IDENTITY (1, 1) NOT NULL ,
	[criteria_id] [int] NOT NULL ,
	[parameter] [varchar] (255) NOT NULL ,
	[value] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [report_queue] (
	[queue_id] [int] IDENTITY (1, 1) NOT NULL ,
	[report_id] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[processed] [datetime] NULL ,
	[status] [int] NOT NULL ,
	[filename] [varchar] (256) NULL ,
	[filesize] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [report_queue_criteria] (
	[criteria_id] [int] IDENTITY (1, 1) NOT NULL ,
	[queue_id] [int] NOT NULL ,
	[parameter] [varchar] (255) NOT NULL ,
	[value] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [revenue] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NULL ,
	[transaction_id] [int] NULL ,
	[month] [int] NULL ,
	[year] [int] NULL ,
	[amount] [float] NULL ,
	[type] [int] NULL ,
	[owner] [int] NULL ,
	[description] [varchar] (255) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [revenue_detail] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[revenue_id] [int] NULL ,
	[amount] [float] NULL ,
	[type] [int] NULL ,
	[owner] [int] NULL ,
	[description] [varchar] (255) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [role] (
	[role_id] [int] IDENTITY (1, 1) NOT NULL ,
	[role] [varchar] (80) NOT NULL ,
	[description] [varchar] (255) NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [role_permission] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[role_id] [int] NOT NULL ,
	[permission_id] [int] NOT NULL ,
	[role_view] [bit] NOT NULL ,
	[role_add] [bit] NOT NULL ,
	[role_edit] [bit] NOT NULL ,
	[role_delete] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [saved_criteriaelement] (
	[id] [int] NOT NULL ,
	[field] [int] NOT NULL ,
	[operator] [varchar] (50) NOT NULL ,
	[operatorid] [int] NOT NULL ,
	[value] [varchar] (80) NOT NULL ,
	[source] [int] NOT NULL ,
	[value_id] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [saved_criterialist] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[owner] [int] NOT NULL ,
	[name] [varchar] (80) NOT NULL ,
	[contact_source] [int] NULL ,
	[enabled] [bit] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [scheduled_recipient] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[campaign_id] [int] NOT NULL ,
	[contact_id] [int] NOT NULL ,
	[run_id] [int] NULL ,
	[status_id] [int] NULL ,
	[status] [varchar] (255) NULL ,
	[status_date] [datetime] NULL ,
	[scheduled_date] [datetime] NULL ,
	[sent_date] [datetime] NULL ,
	[reply_date] [datetime] NULL ,
	[bounce_date] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [search_fields] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[field] [varchar] (80) NULL ,
	[description] [varchar] (255) NULL ,
	[searchable] [bit] NOT NULL ,
	[field_typeid] [int] NOT NULL ,
	[table_name] [varchar] (80) NULL ,
	[object_class] [varchar] (80) NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [state] (
	[state_code] [char] (2) NOT NULL ,
	[state] [varchar] (80) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey] (
	[survey_id] [int] IDENTITY (1, 1) NOT NULL ,
	[name] [varchar] (80) NOT NULL ,
	[description] [varchar] (255) NULL ,
	[intro] [text] NULL ,
	[outro] [text] NULL ,
	[itemLength] [int] NULL ,
	[type] [int] NULL ,
	[enabled] [bit] NOT NULL ,
	[status] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [survey_items] (
	[item_id] [int] IDENTITY (1, 1) NOT NULL ,
	[question_id] [int] NOT NULL ,
	[type] [int] NULL ,
	[description] [varchar] (255) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [survey_questions] (
	[question_id] [int] IDENTITY (1, 1) NOT NULL ,
	[survey_id] [int] NOT NULL ,
	[type] [int] NOT NULL ,
	[description] [varchar] (255) NULL ,
	[required] [bit] NOT NULL ,
	[position] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [sync_client] (
	[client_id] [int] IDENTITY (1, 1) NOT NULL ,
	[type] [varchar] (100) NULL ,
	[version] [varchar] (50) NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[anchor] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [sync_conflict_log] (
	[client_id] [int] NOT NULL ,
	[table_id] [int] NOT NULL ,
	[record_id] [int] NOT NULL ,
	[status_date] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [sync_log] (
	[log_id] [int] IDENTITY (1, 1) NOT NULL ,
	[system_id] [int] NOT NULL ,
	[client_id] [int] NOT NULL ,
	[ip] [varchar] (15) NULL ,
	[entered] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [sync_map] (
	[client_id] [int] NOT NULL ,
	[table_id] [int] NOT NULL ,
	[record_id] [int] NOT NULL ,
	[cuid] [int] NOT NULL ,
	[complete] [bit] NULL ,
	[status_date] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [sync_system] (
	[system_id] [int] IDENTITY (1, 1) NOT NULL ,
	[application_name] [varchar] (255) NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [sync_table] (
	[table_id] [int] IDENTITY (1, 1) NOT NULL ,
	[system_id] [int] NOT NULL ,
	[element_name] [varchar] (255) NULL ,
	[mapped_class_name] [varchar] (255) NULL ,
	[entered] [datetime] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[create_statement] [text] NULL ,
	[order_id] [int] NULL ,
	[sync_item] [bit] NULL ,
	[object_key] [varchar] (50) NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [sync_transaction_log] (
	[transaction_id] [int] IDENTITY (1, 1) NOT NULL ,
	[log_id] [int] NOT NULL ,
	[reference_id] [varchar] (50) NULL ,
	[element_name] [varchar] (255) NULL ,
	[action] [varchar] (20) NULL ,
	[link_item_id] [int] NULL ,
	[status_code] [int] NULL ,
	[record_count] [int] NULL ,
	[message] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [task] (
	[task_id] [int] IDENTITY (1, 1) NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[priority] [int] NOT NULL ,
	[description] [varchar] (80) NULL ,
	[duedate] [datetime] NULL ,
	[reminderid] [int] NULL ,
	[notes] [text] NULL ,
	[sharing] [int] NOT NULL ,
	[complete] [bit] NOT NULL ,
	[enabled] [bit] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NULL ,
	[estimatedloe] [float] NULL ,
	[estimatedloetype] [int] NULL ,
	[type] [int] NULL ,
	[owner] [int] NULL ,
	[completedate] [datetime] NULL ,
	[category_id] [int] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [taskcategory_project] (
	[category_id] [int] NOT NULL ,
	[project_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [tasklink_contact] (
	[task_id] [int] NOT NULL ,
	[contact_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [tasklink_project] (
	[task_id] [int] NOT NULL ,
	[project_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [tasklink_ticket] (
	[task_id] [int] NOT NULL ,
	[ticket_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [ticket] (
	[ticketid] [int] IDENTITY (1, 1) NOT NULL ,
	[org_id] [int] NULL ,
	[contact_id] [int] NULL ,
	[problem] [text] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[closed] [datetime] NULL ,
	[pri_code] [int] NULL ,
	[level_code] [int] NULL ,
	[department_code] [int] NULL ,
	[source_code] [int] NULL ,
	[cat_code] [int] NULL ,
	[subcat_code1] [int] NULL ,
	[subcat_code2] [int] NULL ,
	[subcat_code3] [int] NULL ,
	[assigned_to] [int] NULL ,
	[comment] [text] NULL ,
	[solution] [text] NULL ,
	[scode] [int] NULL ,
	[critical] [datetime] NULL ,
	[notified] [datetime] NULL ,
	[custom_data] [text] NULL ,
	[location] [varchar] (256) NULL ,
	[assigned_date] [datetime] NULL ,
	[est_resolution_date] [datetime] NULL ,
	[resolution_date] [datetime] NULL ,
	[cause] [text] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [ticket_category] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[cat_level] [int] NOT NULL ,
	[parent_cat_code] [int] NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[full_description] [text] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [ticket_category_draft] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[link_id] [int] NULL ,
	[cat_level] [int] NOT NULL ,
	[parent_cat_code] [int] NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[full_description] [text] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [ticket_level] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [ticket_priority] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[style] [text] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [ticket_severity] (
	[code] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [varchar] (300) NOT NULL ,
	[style] [text] NOT NULL ,
	[default_item] [bit] NULL ,
	[level] [int] NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [ticketlog] (
	[id] [int] IDENTITY (1, 1) NOT NULL ,
	[ticketid] [int] NULL ,
	[assigned_to] [int] NULL ,
	[comment] [text] NULL ,
	[closed] [bit] NULL ,
	[pri_code] [int] NULL ,
	[level_code] [int] NULL ,
	[department_code] [int] NULL ,
	[cat_code] [int] NULL ,
	[scode] [int] NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [usage_log] (
	[usage_id] [int] IDENTITY (1, 1) NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NULL ,
	[action] [int] NOT NULL ,
	[record_id] [int] NULL ,
	[record_size] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [viewpoint] (
	[viewpoint_id] [int] IDENTITY (1, 1) NOT NULL ,
	[user_id] [int] NOT NULL ,
	[vp_user_id] [int] NOT NULL ,
	[entered] [datetime] NOT NULL ,
	[enteredby] [int] NOT NULL ,
	[modified] [datetime] NOT NULL ,
	[modifiedby] [int] NOT NULL ,
	[enabled] [bit] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [viewpoint_permission] (
	[vp_permission_id] [int] IDENTITY (1, 1) NOT NULL ,
	[viewpoint_id] [int] NOT NULL ,
	[permission_id] [int] NOT NULL ,
	[viewpoint_view] [bit] NOT NULL ,
	[viewpoint_add] [bit] NOT NULL ,
	[viewpoint_edit] [bit] NOT NULL ,
	[viewpoint_delete] [bit] NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [access] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[user_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [access_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [action_item] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[item_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [action_item_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[log_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [action_list] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[action_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_campaign_groups] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_survey] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[active_survey_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_survey_answer_avg] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_survey_answer_items] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_survey_answers] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[answer_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_survey_items] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[item_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_survey_questions] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[question_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [active_survey_responses] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[response_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [autoguide_ad_run] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[ad_run_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [autoguide_ad_run_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [autoguide_inventory] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[inventory_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [autoguide_make] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[make_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [autoguide_model] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[model_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [autoguide_options] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[option_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [autoguide_vehicle] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[vehicle_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[process_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_component] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_component_library] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[component_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_component_parameter] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_component_result_lookup] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[result_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_events] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[event_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_hook] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_hook_library] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[hook_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_hook_triggers] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[trigger_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_parameter] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_parameter_library] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[parameter_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [call_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[call_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [campaign] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[campaign_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [campaign_run] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [cfsinbox_message] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [contact] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[contact_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [contact_address] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[address_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [contact_emailaddress] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[emailaddress_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [contact_phone] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[phone_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [custom_field_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[category_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [custom_field_group] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[group_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [custom_field_info] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[field_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [custom_field_lookup] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [custom_field_record] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[record_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [database_version] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[version_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [excluded_recipient] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [field_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_business_rules] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[rule_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_contents] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[help_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_faqs] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[faq_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_features] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[feature_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_module] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[module_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_notes] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[note_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_related_links] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[relatedlink_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_tableof_contents] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[content_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_tableofcontentitem_links] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[link_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [help_tips] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[tip_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_access_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_account_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_call_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_contact_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_contactaddress_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_contactemail_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_contactphone_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_delivery_options] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_department] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_employment_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_help_features] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_industry] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_instantmessenger_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_lists_lookup] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_locale] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_opportunity_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_orgaddress_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_orgemail_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_orgphone_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_activity] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_issues] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_loe] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_priority] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_project_status] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_revenue_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_revenuedetail_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_stage] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_survey_types] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_task_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_task_loe] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_task_priority] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [lookup_ticketsource] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [message] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [message_template] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [module_field_categorylink] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [news] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[rec_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [notification] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[notification_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [opportunity_component] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [opportunity_header] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[opp_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [organization] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[org_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [organization_address] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[address_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [organization_emailaddress] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[emailaddress_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [organization_phone] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[phone_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [permission] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[permission_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [permission_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[category_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [process_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[process_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_assignments] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[assignment_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_assignments_status] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[status_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_files] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[item_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_folders] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[folder_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_issue_replies] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[reply_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_issues] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[issue_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [project_requirements] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[requirement_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [projects] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[project_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [report] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[report_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [report_criteria] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[criteria_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [report_criteria_parameter] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[parameter_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [report_queue] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[queue_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [report_queue_criteria] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[criteria_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [revenue] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [revenue_detail] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [role] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[role_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [role_permission] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [saved_criterialist] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [scheduled_recipient] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [search_fields] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [state] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[state_code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [survey] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[survey_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [survey_items] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[item_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [survey_questions] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[question_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [sync_client] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[client_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [sync_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[log_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [sync_system] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[system_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [sync_table] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[table_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [sync_transaction_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[transaction_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [task] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[task_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[ticketid]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_category] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_category_draft] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_level] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_priority] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_severity] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[code]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticketlog] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [usage_log] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[usage_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [viewpoint] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[viewpoint_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [viewpoint_permission] WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		[vp_permission_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [access] WITH NOCHECK ADD 
	CONSTRAINT [DF__access__contact___77BFCB91] DEFAULT ((-1)) FOR [contact_id],
	CONSTRAINT [DF__access__role_id__78B3EFCA] DEFAULT ((-1)) FOR [role_id],
	CONSTRAINT [DF__access__manager___79A81403] DEFAULT ((-1)) FOR [manager_id],
	CONSTRAINT [DF__access__startofd__7A9C383C] DEFAULT (8) FOR [startofday],
	CONSTRAINT [DF__access__endofday__7B905C75] DEFAULT (18) FOR [endofday],
	CONSTRAINT [DF__access__timezone__7C8480AE] DEFAULT ('America/New_York') FOR [timezone],
	CONSTRAINT [DF__access__last_log__7D78A4E7] DEFAULT (getdate()) FOR [last_login],
	CONSTRAINT [DF__access__entered__7E6CC920] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__access__modified__7F60ED59] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__access__expires__00551192] DEFAULT (null) FOR [expires],
	CONSTRAINT [DF__access__alias__014935CB] DEFAULT ((-1)) FOR [alias],
	CONSTRAINT [DF__access__assistan__023D5A04] DEFAULT ((-1)) FOR [assistant],
	CONSTRAINT [DF__access__enabled__03317E3D] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [access_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__access_lo__enter__0BC6C43E] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [account_type_levels] WITH NOCHECK ADD 
	CONSTRAINT [DF__account_t__enter__690797E6] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__account_t__modif__69FBBC1F] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [action_item] WITH NOCHECK ADD 
	CONSTRAINT [DF__action_it__enter__318258D2] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__action_it__modif__336AA144] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__action_it__enabl__345EC57D] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [action_item_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__action_it__link___382F5661] DEFAULT ((-1)) FOR [link_item_id],
	CONSTRAINT [DF__action_it__enter__3A179ED3] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__action_it__modif__3BFFE745] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [action_list] WITH NOCHECK ADD 
	CONSTRAINT [DF__action_li__enter__29E1370A] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__action_li__modif__2BC97F7C] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__action_li__enabl__2CBDA3B5] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [active_campaign_groups] WITH NOCHECK ADD 
	CONSTRAINT [DF__active_ca__group__02284B6B] DEFAULT (null) FOR [groupcriteria]
GO

ALTER TABLE [active_survey] WITH NOCHECK ADD 
	CONSTRAINT [DF__active_su__itemL__2B2A60FE] DEFAULT ((-1)) FOR [itemLength],
	CONSTRAINT [DF__active_su__enabl__2D12A970] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__active_su__enter__2E06CDA9] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__active_su__modif__2FEF161B] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [active_survey_answer_avg] WITH NOCHECK ADD 
	CONSTRAINT [DF__active_su__total__542C7691] DEFAULT (0) FOR [total]
GO

ALTER TABLE [active_survey_answers] WITH NOCHECK ADD 
	CONSTRAINT [DF__active_su__quant__4B973090] DEFAULT ((-1)) FOR [quant_ans]
GO

ALTER TABLE [active_survey_items] WITH NOCHECK ADD 
	CONSTRAINT [DF__active_sur__type__420DC656] DEFAULT ((-1)) FOR [type]
GO

ALTER TABLE [active_survey_questions] WITH NOCHECK ADD 
	CONSTRAINT [DF__active_su__requi__35A7EF71] DEFAULT (0) FOR [required],
	CONSTRAINT [DF__active_su__posit__369C13AA] DEFAULT (0) FOR [position],
	CONSTRAINT [DF__active_su__avera__379037E3] DEFAULT (0.00) FOR [average],
	CONSTRAINT [DF__active_su__total__38845C1C] DEFAULT (0) FOR [total1],
	CONSTRAINT [DF__active_su__total__39788055] DEFAULT (0) FOR [total2],
	CONSTRAINT [DF__active_su__total__3A6CA48E] DEFAULT (0) FOR [total3],
	CONSTRAINT [DF__active_su__total__3B60C8C7] DEFAULT (0) FOR [total4],
	CONSTRAINT [DF__active_su__total__3C54ED00] DEFAULT (0) FOR [total5],
	CONSTRAINT [DF__active_su__total__3D491139] DEFAULT (0) FOR [total6],
	CONSTRAINT [DF__active_su__total__3E3D3572] DEFAULT (0) FOR [total7]
GO

ALTER TABLE [active_survey_responses] WITH NOCHECK ADD 
	CONSTRAINT [DF__active_su__conta__45DE573A] DEFAULT ((-1)) FOR [contact_id],
	CONSTRAINT [DF__active_su__enter__46D27B73] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [autoguide_ad_run] WITH NOCHECK ADD 
	CONSTRAINT [DF__autoguide__inclu__100C566E] DEFAULT (0) FOR [include_photo],
	CONSTRAINT [DF__autoguide__compl__11007AA7] DEFAULT ((-1)) FOR [completedby],
	CONSTRAINT [DF__autoguide__enter__11F49EE0] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__autoguide__modif__12E8C319] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [autoguide_ad_run_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__autoguide__defau__15C52FC4] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__autoguide__level__16B953FD] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__autoguide__enabl__17AD7836] DEFAULT (0) FOR [enabled],
	CONSTRAINT [DF__autoguide__enter__18A19C6F] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__autoguide__modif__1995C0A8] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [autoguide_inventory] WITH NOCHECK ADD 
	CONSTRAINT [DF__autoguide__is_ne__00CA12DE] DEFAULT (0) FOR [is_new],
	CONSTRAINT [DF__autoguide___sold__01BE3717] DEFAULT (0) FOR [sold],
	CONSTRAINT [DF__autoguide__enter__02B25B50] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__autoguide__modif__03A67F89] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [autoguide_make] WITH NOCHECK ADD 
	CONSTRAINT [DF__autoguide__enter__7093AB15] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__autoguide__modif__7187CF4E] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [autoguide_model] WITH NOCHECK ADD 
	CONSTRAINT [DF__autoguide__enter__75586032] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__autoguide__modif__764C846B] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [autoguide_options] WITH NOCHECK ADD 
	CONSTRAINT [DF__autoguide__defau__0682EC34] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__autoguide__level__0777106D] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__autoguide__enabl__086B34A6] DEFAULT (0) FOR [enabled],
	CONSTRAINT [DF__autoguide__enter__095F58DF] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__autoguide__modif__0A537D18] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [autoguide_vehicle] WITH NOCHECK ADD 
	CONSTRAINT [DF__autoguide__enter__7B113988] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__autoguide__modif__7C055DC1] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [business_process] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__6E765879] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__business___enter__6F6A7CB2] DEFAULT (getdate()) FOR [entered],
	 UNIQUE  NONCLUSTERED 
	(
		[process_name]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_component] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__75235608] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [business_process_component_library] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__62108194] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [business_process_component_parameter] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__7DB89C09] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [business_process_component_result_lookup] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___level__65E11278] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__business___enabl__66D536B1] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [business_process_events] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___secon__009508B4] DEFAULT ('0') FOR [second],
	CONSTRAINT [DF__business___minut__01892CED] DEFAULT ('*') FOR [minute],
	CONSTRAINT [DF__business_p__hour__027D5126] DEFAULT ('*') FOR [hour],
	CONSTRAINT [DF__business___dayof__0371755F] DEFAULT ('*') FOR [dayofmonth],
	CONSTRAINT [DF__business___month__04659998] DEFAULT ('*') FOR [month],
	CONSTRAINT [DF__business___dayof__0559BDD1] DEFAULT ('*') FOR [dayofweek],
	CONSTRAINT [DF__business_p__year__064DE20A] DEFAULT ('*') FOR [year],
	CONSTRAINT [DF__business___busin__07420643] DEFAULT ('true') FOR [businessDays],
	CONSTRAINT [DF__business___enabl__08362A7C] DEFAULT (0) FOR [enabled],
	CONSTRAINT [DF__business___enter__092A4EB5] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [business_process_hook] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__186C9245] DEFAULT (0) FOR [enabled]
GO

ALTER TABLE [business_process_hook_library] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__0FD74C44] DEFAULT (0) FOR [enabled]
GO

ALTER TABLE [business_process_hook_triggers] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__13A7DD28] DEFAULT (0) FOR [enabled]
GO

ALTER TABLE [business_process_log] WITH NOCHECK ADD 
	 UNIQUE  NONCLUSTERED 
	(
		[process_name]
	)  ON [PRIMARY] 
GO

ALTER TABLE [business_process_parameter] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__78F3E6EC] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [business_process_parameter_library] WITH NOCHECK ADD 
	CONSTRAINT [DF__business___enabl__69B1A35C] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [call_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__call_log__entere__66EA454A] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__call_log__modifi__68D28DBC] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__call_log__alert__6ABAD62E] DEFAULT (null) FOR [alert]
GO

ALTER TABLE [campaign] WITH NOCHECK ADD 
	CONSTRAINT [DF__campaign__messag__5FD33367] DEFAULT ((-1)) FOR [message_id],
	CONSTRAINT [DF__campaign__reply___60C757A0] DEFAULT (null) FOR [reply_addr],
	CONSTRAINT [DF__campaign__subjec__61BB7BD9] DEFAULT (null) FOR [subject],
	CONSTRAINT [DF__campaign__messag__62AFA012] DEFAULT (null) FOR [message],
	CONSTRAINT [DF__campaign__status__63A3C44B] DEFAULT (0) FOR [status_id],
	CONSTRAINT [DF__campaign__active__6497E884] DEFAULT (0) FOR [active],
	CONSTRAINT [DF__campaign__active__658C0CBD] DEFAULT (null) FOR [active_date],
	CONSTRAINT [DF__campaign__send_m__668030F6] DEFAULT ((-1)) FOR [send_method_id],
	CONSTRAINT [DF__campaign__inacti__6774552F] DEFAULT (null) FOR [inactive_date],
	CONSTRAINT [DF__campaign__approv__68687968] DEFAULT (null) FOR [approval_date],
	CONSTRAINT [DF__campaign__enable__6A50C1DA] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__campaign__entere__6B44E613] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__campaign__modifi__6D2D2E85] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__campaign__type__6F1576F7] DEFAULT (1) FOR [type]
GO

ALTER TABLE [campaign_run] WITH NOCHECK ADD 
	CONSTRAINT [DF__campaign___statu__72E607DB] DEFAULT (0) FOR [status],
	CONSTRAINT [DF__campaign___run_d__73DA2C14] DEFAULT (getdate()) FOR [run_date],
	CONSTRAINT [DF__campaign___total__74CE504D] DEFAULT (0) FOR [total_contacts],
	CONSTRAINT [DF__campaign___total__75C27486] DEFAULT (0) FOR [total_sent],
	CONSTRAINT [DF__campaign___total__76B698BF] DEFAULT (0) FOR [total_replied],
	CONSTRAINT [DF__campaign___total__77AABCF8] DEFAULT (0) FOR [total_bounced]
GO

ALTER TABLE [cfsinbox_message] WITH NOCHECK ADD 
	CONSTRAINT [DF__cfsinbox___subje__57DD0BE4] DEFAULT (null) FOR [subject],
	CONSTRAINT [DF__cfsinbox_m__sent__59C55456] DEFAULT (getdate()) FOR [sent],
	CONSTRAINT [DF__cfsinbox___enter__5AB9788F] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__cfsinbox___modif__5BAD9CC8] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__cfsinbox_m__type__5CA1C101] DEFAULT ((-1)) FOR [type],
	CONSTRAINT [DF__cfsinbox___delet__5E8A0973] DEFAULT (0) FOR [delete_flag]
GO

ALTER TABLE [cfsinbox_messagelink] WITH NOCHECK ADD 
	CONSTRAINT [DF__cfsinbox___statu__625A9A57] DEFAULT (0) FOR [status],
	CONSTRAINT [DF__cfsinbox___viewe__634EBE90] DEFAULT (null) FOR [viewed],
	CONSTRAINT [DF__cfsinbox___enabl__6442E2C9] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [contact] WITH NOCHECK ADD 
	CONSTRAINT [DF__contact__entered__6754599E] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__contact__modifie__693CA210] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__contact__enabled__6B24EA82] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__contact__custom1__6D0D32F4] DEFAULT ((-1)) FOR [custom1],
	CONSTRAINT [DF__contact__primary__6E01572D] DEFAULT (0) FOR [primary_contact],
	CONSTRAINT [DF__contact__employe__6EF57B66] DEFAULT (0) FOR [employee]
GO

ALTER TABLE [contact_address] WITH NOCHECK ADD 
	CONSTRAINT [DF__contact_a__enter__3F115E1A] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__contact_a__modif__40F9A68C] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [contact_emailaddress] WITH NOCHECK ADD 
	CONSTRAINT [DF__contact_e__enter__46B27FE2] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__contact_e__modif__489AC854] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [contact_phone] WITH NOCHECK ADD 
	CONSTRAINT [DF__contact_p__enter__4E53A1AA] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__contact_p__modif__503BEA1C] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [contact_type_levels] WITH NOCHECK ADD 
	CONSTRAINT [DF__contact_t__enter__6DCC4D03] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__contact_t__modif__6EC0713C] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [custom_field_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__custom_fi__level__3335971A] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__custom_fi__start__3429BB53] DEFAULT (getdate()) FOR [start_date],
	CONSTRAINT [DF__custom_fi__defau__351DDF8C] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__custom_fi__enter__361203C5] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__custom_fi__enabl__370627FE] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__custom_fi__multi__37FA4C37] DEFAULT (0) FOR [multiple_records],
	CONSTRAINT [DF__custom_fi__read___38EE7070] DEFAULT (0) FOR [read_only]
GO

ALTER TABLE [custom_field_data] WITH NOCHECK ADD 
	CONSTRAINT [DF__custom_fi__selec__5C37ACAD] DEFAULT (0) FOR [selected_item_id]
GO

ALTER TABLE [custom_field_group] WITH NOCHECK ADD 
	CONSTRAINT [DF__custom_fi__level__3CBF0154] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__custom_fi__start__3DB3258D] DEFAULT (getdate()) FOR [start_date],
	CONSTRAINT [DF__custom_fi__enter__3EA749C6] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__custom_fi__enabl__3F9B6DFF] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [custom_field_info] WITH NOCHECK ADD 
	CONSTRAINT [DF__custom_fi__level__436BFEE3] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__custom_fi__valid__4460231C] DEFAULT (0) FOR [validation_type],
	CONSTRAINT [DF__custom_fi__requi__45544755] DEFAULT (0) FOR [required],
	CONSTRAINT [DF__custom_fi__start__46486B8E] DEFAULT (getdate()) FOR [start_date],
	CONSTRAINT [DF__custom_fi__end_d__473C8FC7] DEFAULT (null) FOR [end_date],
	CONSTRAINT [DF__custom_fi__enter__4830B400] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__custom_fi__enabl__4924D839] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [custom_field_lookup] WITH NOCHECK ADD 
	CONSTRAINT [DF__custom_fi__defau__4CF5691D] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__custom_fi__level__4DE98D56] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__custom_fi__start__4EDDB18F] DEFAULT (getdate()) FOR [start_date],
	CONSTRAINT [DF__custom_fi__enter__4FD1D5C8] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__custom_fi__enabl__50C5FA01] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [custom_field_record] WITH NOCHECK ADD 
	CONSTRAINT [DF__custom_fi__enter__54968AE5] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__custom_fi__modif__567ED357] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__custom_fi__enabl__58671BC9] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [database_version] WITH NOCHECK ADD 
	CONSTRAINT [DF__database___enter__3EDC53F0] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [field_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__field_typ__data___5708E33C] DEFAULT ((-1)) FOR [data_typeid],
	CONSTRAINT [DF__field_typ__enabl__57FD0775] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_business_rules] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_busi__enter__375B2DB9] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_busi__modif__3943762B] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_busi__enabl__3B2BBE9D] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_contents] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_cont__enter__7C3A67EB] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_cont__modif__7E22B05D] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_cont__enabl__7F16D496] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_faqs] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_faqs__enter__2EC5E7B8] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_faqs__modif__30AE302A] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_faqs__enabl__3296789C] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_features] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_feat__enter__1CA7377D] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_feat__modif__1E8F7FEF] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_feat__enabl__2077C861] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__help_feat__level__216BEC9A] DEFAULT (0) FOR [level]
GO

ALTER TABLE [help_notes] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_note__enter__3FF073BA] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_note__modif__41D8BC2C] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_note__enabl__43C1049E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_related_links] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_rela__enter__2724C5F0] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_rela__modif__290D0E62] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_rela__enabl__2A01329B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_tableof_contents] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_tabl__enter__06B7F65E] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_tabl__modif__08A03ED0] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_tabl__enabl__09946309] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_tableofcontentitem_links] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_tabl__enter__0F4D3C5F] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_tabl__modif__113584D1] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_tabl__enabl__1229A90A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [help_tips] WITH NOCHECK ADD 
	CONSTRAINT [DF__help_tips__enter__4885B9BB] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__help_tips__modif__4A6E022D] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__help_tips__enabl__4B622666] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_access_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ac__defau__4E88ABD4] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ac__enabl__4F7CD00D] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_account_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ac__defau__182C9B23] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ac__level__1920BF5C] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ac__enabl__1A14E395] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_call_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ca__defau__41B8C09B] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ca__level__42ACE4D4] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ca__enabl__43A1090D] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_contact_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_co__defau__117F9D94] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_co__level__1273C1CD] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_co__enabl__1367E606] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_co__categ__15502E78] DEFAULT (0) FOR [category]
GO

ALTER TABLE [lookup_contactaddress_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_co__defau__403A8C7D] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_co__level__412EB0B6] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_co__enabl__4222D4EF] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_contactemail_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_co__defau__44FF419A] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_co__level__45F365D3] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_co__enabl__46E78A0C] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_contactphone_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_co__defau__49C3F6B7] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_co__level__4AB81AF0] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_co__enabl__4BAC3F29] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_delivery_options] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_de__defau__1DB06A4F] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_de__level__1EA48E88] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_de__enabl__1F98B2C1] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_department] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_de__defau__1ED998B2] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_de__level__1FCDBCEB] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_de__enabl__20C1E124] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_employment_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_em__defau__36B12243] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_em__level__37A5467C] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_em__enabl__38996AB5] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_help_features] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_he__defau__150615B5] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_he__level__15FA39EE] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_he__enabl__16EE5E27] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_industry] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_in__defau__060DEAE8] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_in__level__07020F21] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_in__enabl__07F6335A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_instantmessenger_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_in__defau__31EC6D26] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_in__level__32E0915F] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_in__enabl__33D4B598] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_lists_lookup] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_li__level__72910220] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_li__enter__73852659] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [lookup_locale] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_lo__defau__3B75D760] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_lo__level__3C69FB99] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_lo__enabl__3D5E1FD2] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_opportunity_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_op__defau__467D75B8] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_op__level__477199F1] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_op__enabl__4865BE2A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_orgaddress_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__239E4DCF] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__24927208] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__25869641] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_orgemail_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__286302EC] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__29572725] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__2A4B4B5E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_orgphone_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_or__defau__2D27B809] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_or__level__2E1BDC42] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_or__enabl__2F10007B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_project_activity] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__5F141958] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__60083D91] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__60FC61CA] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__61F08603] DEFAULT (0) FOR [group_id],
	CONSTRAINT [DF__lookup_pr__templ__62E4AA3C] DEFAULT (0) FOR [template_id]
GO

ALTER TABLE [lookup_project_issues] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__6B79F03D] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__6C6E1476] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__6D6238AF] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__6E565CE8] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_project_loe] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__base___76EBA2E9] DEFAULT (0) FOR [base_value],
	CONSTRAINT [DF__lookup_pr__defau__77DFC722] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__78D3EB5B] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__79C80F94] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__7ABC33CD] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_project_priority] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__65C116E7] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__66B53B20] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__67A95F59] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__689D8392] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_project_status] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_pr__defau__7132C993] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_pr__level__7226EDCC] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_pr__enabl__731B1205] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__lookup_pr__group__740F363E] DEFAULT (0) FOR [group_id]
GO

ALTER TABLE [lookup_revenue_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_re__defau__1C722D53] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_re__level__1D66518C] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_re__enabl__1E5A75C5] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_revenuedetail_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_re__defau__2136E270] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_re__level__222B06A9] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_re__enabl__231F2AE2] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_stage] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_st__defau__18EBB532] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_st__level__19DFD96B] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_st__enabl__1AD3FDA4] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_survey_types] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_su__defau__0F824689] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_su__level__10766AC2] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_su__enabl__116A8EFB] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_task_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ta__defau__457442E6] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ta__level__4668671F] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ta__enabl__475C8B58] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_task_loe] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ta__defau__40AF8DC9] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ta__level__41A3B202] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ta__enabl__4297D63B] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_task_priority] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ta__defau__3BEAD8AC] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ta__level__3CDEFCE5] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ta__enabl__3DD3211E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [lookup_ticketsource] WITH NOCHECK ADD 
	CONSTRAINT [DF__lookup_ti__defau__7AF13DF7] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__lookup_ti__level__7BE56230] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__lookup_ti__enabl__7CD98669] DEFAULT (1) FOR [enabled],
	 UNIQUE  NONCLUSTERED 
	(
		[description]
	)  ON [PRIMARY] 
GO

ALTER TABLE [message] WITH NOCHECK ADD 
	CONSTRAINT [DF__message__subject__5F9E293D] DEFAULT (null) FOR [subject],
	CONSTRAINT [DF__message__enabled__60924D76] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__message__entered__618671AF] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__message__modifie__636EBA21] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [message_template] WITH NOCHECK ADD 
	CONSTRAINT [DF__message_t__enabl__68336F3E] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__message_t__enter__69279377] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__message_t__modif__6B0FDBE9] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [module_field_categorylink] WITH NOCHECK ADD 
	CONSTRAINT [DF__module_fi__level__2E70E1FD] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__module_fi__enter__2F650636] DEFAULT (getdate()) FOR [entered],
	 UNIQUE  NONCLUSTERED 
	(
		[category_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [news] WITH NOCHECK ADD 
	CONSTRAINT [DF__news__created__236943A5] DEFAULT (getdate()) FOR [created]
GO

ALTER TABLE [notification] WITH NOCHECK ADD 
	CONSTRAINT [DF__notificat__item___540C7B00] DEFAULT (getdate()) FOR [item_modified],
	CONSTRAINT [DF__notificat__attem__55009F39] DEFAULT (getdate()) FOR [attempt]
GO

ALTER TABLE [opportunity_component] WITH NOCHECK ADD 
	CONSTRAINT [DF__opportuni__stage__55BFB948] DEFAULT (getdate()) FOR [stagedate],
	CONSTRAINT [DF__opportuni__enter__56B3DD81] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__opportuni__modif__589C25F3] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__opportuni__alert__5A846E65] DEFAULT (null) FOR [alert],
	CONSTRAINT [DF__opportuni__enabl__5B78929E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [opportunity_component_levels] WITH NOCHECK ADD 
	CONSTRAINT [DF__opportuni__enter__5F492382] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__opportuni__modif__603D47BB] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [opportunity_header] WITH NOCHECK ADD 
	CONSTRAINT [DF__opportuni__enter__4D2A7347] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__opportuni__modif__4F12BBB9] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [organization] WITH NOCHECK ADD 
	CONSTRAINT [DF__organizat__ticke__52593CB8] DEFAULT (null) FOR [ticker_symbol],
	CONSTRAINT [DF__organizat__sales__534D60F1] DEFAULT (0) FOR [sales_rep],
	CONSTRAINT [DF__organizat__miner__5441852A] DEFAULT (0) FOR [miner_only],
	CONSTRAINT [DF__organizat__enter__5535A963] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__organizat__modif__571DF1D5] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__organizat__enabl__59063A47] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__organizat__dupli__5AEE82B9] DEFAULT ((-1)) FOR [duplicate_id],
	CONSTRAINT [DF__organizat__custo__5BE2A6F2] DEFAULT ((-1)) FOR [custom1],
	CONSTRAINT [DF__organizat__custo__5CD6CB2B] DEFAULT ((-1)) FOR [custom2],
	CONSTRAINT [DF__organizat__contr__5DCAEF64] DEFAULT (null) FOR [contract_end],
	CONSTRAINT [DF__organizat__alert__5EBF139D] DEFAULT (null) FOR [alertdate],
	CONSTRAINT [DF__organizat__alert__5FB337D6] DEFAULT (null) FOR [alert]
GO

ALTER TABLE [organization_address] WITH NOCHECK ADD 
	CONSTRAINT [DF__organizat__enter__282DF8C2] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__organizat__modif__2A164134] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [organization_emailaddress] WITH NOCHECK ADD 
	CONSTRAINT [DF__organizat__enter__2FCF1A8A] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__organizat__modif__31B762FC] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [organization_phone] WITH NOCHECK ADD 
	CONSTRAINT [DF__organizat__enter__37703C52] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__organizat__modif__395884C4] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [permission] WITH NOCHECK ADD 
	CONSTRAINT [DF__permissio__permi__06CD04F7] DEFAULT (0) FOR [permission_view],
	CONSTRAINT [DF__permissio__permi__07C12930] DEFAULT (0) FOR [permission_add],
	CONSTRAINT [DF__permissio__permi__08B54D69] DEFAULT (0) FOR [permission_edit],
	CONSTRAINT [DF__permissio__permi__09A971A2] DEFAULT (0) FOR [permission_delete],
	CONSTRAINT [DF__permissio__descr__0A9D95DB] DEFAULT ('') FOR [description],
	CONSTRAINT [DF__permissio__level__0B91BA14] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__permissio__enabl__0C85DE4D] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__permissio__activ__0D7A0286] DEFAULT (1) FOR [active],
	CONSTRAINT [DF__permissio__viewp__0E6E26BF] DEFAULT (0) FOR [viewpoints]
GO

ALTER TABLE [permission_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__permissio__level__7A672E12] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__permissio__enabl__7B5B524B] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__permissio__activ__7C4F7684] DEFAULT (1) FOR [active],
	CONSTRAINT [DF__permissio__folde__7D439ABD] DEFAULT (0) FOR [folders],
	CONSTRAINT [DF__permissio__looku__7E37BEF6] DEFAULT (0) FOR [lookups],
	CONSTRAINT [DF__permissio__viewp__7F2BE32F] DEFAULT (0) FOR [viewpoints],
	CONSTRAINT [DF__permissio__categ__00200768] DEFAULT (0) FOR [categories],
	CONSTRAINT [DF__permissio__sched__01142BA1] DEFAULT (0) FOR [scheduled_events],
	CONSTRAINT [DF__permissio__objec__02084FDA] DEFAULT (0) FOR [object_events],
	CONSTRAINT [DF__permissio__repor__02FC7413] DEFAULT (0) FOR [reports]
GO

ALTER TABLE [process_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__process_l__enter__6DB73E6A] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [project_assignments] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_a__assig__1758727B] DEFAULT (getdate()) FOR [assign_date],
	CONSTRAINT [DF__project_a__statu__1940BAED] DEFAULT (getdate()) FOR [status_date],
	CONSTRAINT [DF__project_a__enter__1A34DF26] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_a__modif__1C1D2798] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [project_assignments_status] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_a__statu__21D600EE] DEFAULT (getdate()) FOR [status_date]
GO

ALTER TABLE [project_files] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_fi__size__38B96646] DEFAULT (0) FOR [size],
	CONSTRAINT [DF__project_f__versi__39AD8A7F] DEFAULT (0) FOR [version],
	CONSTRAINT [DF__project_f__enabl__3AA1AEB8] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__project_f__downl__3B95D2F1] DEFAULT (0) FOR [downloads],
	CONSTRAINT [DF__project_f__enter__3C89F72A] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_f__modif__3E723F9C] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [project_files_download] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_f__versi__4BCC3ABA] DEFAULT (0) FOR [version],
	CONSTRAINT [DF__project_f__downl__4DB4832C] DEFAULT (getdate()) FOR [download_date]
GO

ALTER TABLE [project_files_version] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_fi__size__4242D080] DEFAULT (0) FOR [size],
	CONSTRAINT [DF__project_f__versi__4336F4B9] DEFAULT (0) FOR [version],
	CONSTRAINT [DF__project_f__enabl__442B18F2] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__project_f__downl__451F3D2B] DEFAULT (0) FOR [downloads],
	CONSTRAINT [DF__project_f__enter__46136164] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_f__modif__47FBA9D6] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [project_issue_replies] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_i__reply__2F2FFC0C] DEFAULT (0) FOR [reply_to],
	CONSTRAINT [DF__project_i__enter__30242045] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_i__modif__320C68B7] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [project_issues] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_i__impor__269AB60B] DEFAULT (0) FOR [importance],
	CONSTRAINT [DF__project_i__enabl__278EDA44] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__project_i__enter__2882FE7D] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_i__modif__2A6B46EF] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [project_requirements] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_r__enter__09FE775D] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_r__modif__0BE6BFCF] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [project_team] WITH NOCHECK ADD 
	CONSTRAINT [DF__project_t__enter__51851410] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__project_t__modif__536D5C82] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [projects] WITH NOCHECK ADD 
	CONSTRAINT [DF__projects__reques__7E8CC4B1] DEFAULT (getdate()) FOR [requestDate],
	CONSTRAINT [DF__projects__entere__7F80E8EA] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__projects__modifi__0169315C] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [report] WITH NOCHECK ADD 
	CONSTRAINT [DF__report__type__0880433F] DEFAULT (1) FOR [type],
	CONSTRAINT [DF__report__entered__09746778] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__report__modified__0B5CAFEA] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__report__enabled__0D44F85C] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__report__custom__0E391C95] DEFAULT (0) FOR [custom]
GO

ALTER TABLE [report_criteria] WITH NOCHECK ADD 
	CONSTRAINT [DF__report_cr__enter__12FDD1B2] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__report_cr__modif__14E61A24] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__report_cr__enabl__16CE6296] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [report_queue] WITH NOCHECK ADD 
	CONSTRAINT [DF__report_qu__enter__1D7B6025] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__report_qu__proce__1F63A897] DEFAULT (null) FOR [processed],
	CONSTRAINT [DF__report_qu__statu__2057CCD0] DEFAULT (0) FOR [status],
	CONSTRAINT [DF__report_qu__files__214BF109] DEFAULT ((-1)) FOR [filesize],
	CONSTRAINT [DF__report_qu__enabl__22401542] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [revenue] WITH NOCHECK ADD 
	CONSTRAINT [DF__revenue__transac__26EFBBC6] DEFAULT ((-1)) FOR [transaction_id],
	CONSTRAINT [DF__revenue__month__27E3DFFF] DEFAULT ((-1)) FOR [month],
	CONSTRAINT [DF__revenue__year__28D80438] DEFAULT ((-1)) FOR [year],
	CONSTRAINT [DF__revenue__amount__29CC2871] DEFAULT (0) FOR [amount],
	CONSTRAINT [DF__revenue__entered__2CA8951C] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__revenue__modifie__2E90DD8E] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [revenue_detail] WITH NOCHECK ADD 
	CONSTRAINT [DF__revenue_d__amoun__335592AB] DEFAULT (0) FOR [amount],
	CONSTRAINT [DF__revenue_d__enter__3631FF56] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__revenue_d__modif__381A47C8] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [role] WITH NOCHECK ADD 
	CONSTRAINT [DF__role__descriptio__72C60C4A] DEFAULT ('') FOR [description],
	CONSTRAINT [DF__role__entered__74AE54BC] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__role__modified__76969D2E] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__role__enabled__778AC167] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [role_permission] WITH NOCHECK ADD 
	CONSTRAINT [DF__role_perm__role___1332DBDC] DEFAULT (0) FOR [role_view],
	CONSTRAINT [DF__role_perm__role___14270015] DEFAULT (0) FOR [role_add],
	CONSTRAINT [DF__role_perm__role___151B244E] DEFAULT (0) FOR [role_edit],
	CONSTRAINT [DF__role_perm__role___160F4887] DEFAULT (0) FOR [role_delete]
GO

ALTER TABLE [saved_criteriaelement] WITH NOCHECK ADD 
	CONSTRAINT [DF__saved_cri__sourc__70C8B53F] DEFAULT ((-1)) FOR [source]
GO

ALTER TABLE [saved_criterialist] WITH NOCHECK ADD 
	CONSTRAINT [DF__saved_cri__enter__573DED66] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__saved_cri__modif__592635D8] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__saved_cri__conta__5C02A283] DEFAULT ((-1)) FOR [contact_source],
	CONSTRAINT [DF__saved_cri__enabl__5CF6C6BC] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [scheduled_recipient] WITH NOCHECK ADD 
	CONSTRAINT [DF__scheduled__run_i__06ED0088] DEFAULT ((-1)) FOR [run_id],
	CONSTRAINT [DF__scheduled__statu__07E124C1] DEFAULT (0) FOR [status_id],
	CONSTRAINT [DF__scheduled__statu__08D548FA] DEFAULT (getdate()) FOR [status_date],
	CONSTRAINT [DF__scheduled__sched__09C96D33] DEFAULT (getdate()) FOR [scheduled_date],
	CONSTRAINT [DF__scheduled__sent___0ABD916C] DEFAULT (null) FOR [sent_date],
	CONSTRAINT [DF__scheduled__reply__0BB1B5A5] DEFAULT (null) FOR [reply_date],
	CONSTRAINT [DF__scheduled__bounc__0CA5D9DE] DEFAULT (null) FOR [bounce_date]
GO

ALTER TABLE [search_fields] WITH NOCHECK ADD 
	CONSTRAINT [DF__search_fi__searc__5AD97420] DEFAULT (1) FOR [searchable],
	CONSTRAINT [DF__search_fi__field__5BCD9859] DEFAULT ((-1)) FOR [field_typeid],
	CONSTRAINT [DF__search_fi__enabl__5CC1BC92] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [survey] WITH NOCHECK ADD 
	CONSTRAINT [DF__survey__itemLeng__1446FBA6] DEFAULT ((-1)) FOR [itemLength],
	CONSTRAINT [DF__survey__type__153B1FDF] DEFAULT ((-1)) FOR [type],
	CONSTRAINT [DF__survey__enabled__162F4418] DEFAULT (1) FOR [enabled],
	CONSTRAINT [DF__survey__status__17236851] DEFAULT ((-1)) FOR [status],
	CONSTRAINT [DF__survey__entered__18178C8A] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__survey__modified__19FFD4FC] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [survey_items] WITH NOCHECK ADD 
	CONSTRAINT [DF__survey_ite__type__2759D01A] DEFAULT ((-1)) FOR [type]
GO

ALTER TABLE [survey_questions] WITH NOCHECK ADD 
	CONSTRAINT [DF__survey_qu__requi__22951AFD] DEFAULT (0) FOR [required],
	CONSTRAINT [DF__survey_qu__posit__23893F36] DEFAULT (0) FOR [position]
GO

ALTER TABLE [sync_client] WITH NOCHECK ADD 
	CONSTRAINT [DF__sync_clie__enter__4E3E9311] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__sync_clie__modif__4F32B74A] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__sync_clie__ancho__5026DB83] DEFAULT (null) FOR [anchor]
GO

ALTER TABLE [sync_conflict_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__sync_conf__statu__61516785] DEFAULT (getdate()) FOR [status_date]
GO

ALTER TABLE [sync_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__sync_log__entere__66161CA2] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [sync_map] WITH NOCHECK ADD 
	CONSTRAINT [DF__sync_map__comple__5D80D6A1] DEFAULT (0) FOR [complete]
GO

ALTER TABLE [sync_system] WITH NOCHECK ADD 
	CONSTRAINT [DF__sync_syst__enabl__5303482E] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [sync_table] WITH NOCHECK ADD 
	CONSTRAINT [DF__sync_tabl__enter__56D3D912] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__sync_tabl__modif__57C7FD4B] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__sync_tabl__order__58BC2184] DEFAULT ((-1)) FOR [order_id],
	CONSTRAINT [DF__sync_tabl__sync___59B045BD] DEFAULT (0) FOR [sync_item]
GO

ALTER TABLE [task] WITH NOCHECK ADD 
	CONSTRAINT [DF__task__entered__4A38F803] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__task__complete__4D1564AE] DEFAULT (0) FOR [complete],
	CONSTRAINT [DF__task__enabled__4E0988E7] DEFAULT (0) FOR [enabled],
	CONSTRAINT [DF__task__modified__4EFDAD20] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__task__type__51DA19CB] DEFAULT (1) FOR [type]
GO

ALTER TABLE [ticket] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket__entered__1699586C] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__ticket__modified__1881A0DE] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [ticket_category] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket_ca__cat_l__0662F0A3] DEFAULT (0) FOR [cat_level],
	CONSTRAINT [DF__ticket_ca__full___075714DC] DEFAULT ('') FOR [full_description],
	CONSTRAINT [DF__ticket_ca__defau__084B3915] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__ticket_ca__level__093F5D4E] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__ticket_ca__enabl__0A338187] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [ticket_category_draft] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket_ca__link___0D0FEE32] DEFAULT ((-1)) FOR [link_id],
	CONSTRAINT [DF__ticket_ca__cat_l__0E04126B] DEFAULT (0) FOR [cat_level],
	CONSTRAINT [DF__ticket_ca__full___0EF836A4] DEFAULT ('') FOR [full_description],
	CONSTRAINT [DF__ticket_ca__defau__0FEC5ADD] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__ticket_ca__level__10E07F16] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__ticket_ca__enabl__11D4A34F] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [ticket_level] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket_le__defau__6E8B6712] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__ticket_le__level__6F7F8B4B] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__ticket_le__enabl__7073AF84] DEFAULT (1) FOR [enabled],
	 UNIQUE  NONCLUSTERED 
	(
		[description]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_priority] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket_pr__style__00AA174D] DEFAULT ('') FOR [style],
	CONSTRAINT [DF__ticket_pr__defau__019E3B86] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__ticket_pr__level__02925FBF] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__ticket_pr__enabl__038683F8] DEFAULT (1) FOR [enabled],
	 UNIQUE  NONCLUSTERED 
	(
		[description]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticket_severity] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticket_se__style__74444068] DEFAULT ('') FOR [style],
	CONSTRAINT [DF__ticket_se__defau__753864A1] DEFAULT (0) FOR [default_item],
	CONSTRAINT [DF__ticket_se__level__762C88DA] DEFAULT (0) FOR [level],
	CONSTRAINT [DF__ticket_se__enabl__7720AD13] DEFAULT (1) FOR [enabled],
	 UNIQUE  NONCLUSTERED 
	(
		[description]
	)  ON [PRIMARY] 
GO

ALTER TABLE [ticketlog] WITH NOCHECK ADD 
	CONSTRAINT [DF__ticketlog__enter__26CFC035] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__ticketlog__modif__28B808A7] DEFAULT (getdate()) FOR [modified]
GO

ALTER TABLE [usage_log] WITH NOCHECK ADD 
	CONSTRAINT [DF__usage_log__enter__0EA330E9] DEFAULT (getdate()) FOR [entered]
GO

ALTER TABLE [viewpoint] WITH NOCHECK ADD 
	CONSTRAINT [DF__viewpoint__enter__7849DB76] DEFAULT (getdate()) FOR [entered],
	CONSTRAINT [DF__viewpoint__modif__7A3223E8] DEFAULT (getdate()) FOR [modified],
	CONSTRAINT [DF__viewpoint__enabl__7C1A6C5A] DEFAULT (1) FOR [enabled]
GO

ALTER TABLE [viewpoint_permission] WITH NOCHECK ADD 
	CONSTRAINT [DF__viewpoint__viewp__00DF2177] DEFAULT (0) FOR [viewpoint_view],
	CONSTRAINT [DF__viewpoint__viewp__01D345B0] DEFAULT (0) FOR [viewpoint_add],
	CONSTRAINT [DF__viewpoint__viewp__02C769E9] DEFAULT (0) FOR [viewpoint_edit],
	CONSTRAINT [DF__viewpoint__viewp__03BB8E22] DEFAULT (0) FOR [viewpoint_delete]
GO

 CREATE  UNIQUE  INDEX [idx_autog_inv_opt] ON [autoguide_inventory_options]([inventory_id], [option_id]) ON [PRIMARY]
GO

 CREATE  INDEX [call_log_cidx] ON [call_log]([alertdate], [enteredby]) ON [PRIMARY]
GO

 CREATE  INDEX [contact_user_id_idx] ON [contact]([user_id]) ON [PRIMARY]
GO

 CREATE  INDEX [contactlist_namecompany] ON [contact]([namelast], [namefirst], [company]) ON [PRIMARY]
GO

 CREATE  INDEX [contactlist_company] ON [contact]([company], [namelast], [namefirst]) ON [PRIMARY]
GO

 CREATE  INDEX [custom_field_cat_idx] ON [custom_field_category]([module_id]) ON [PRIMARY]
GO

 CREATE  INDEX [custom_field_dat_idx] ON [custom_field_data]([record_id], [field_id]) ON [PRIMARY]
GO

 CREATE  INDEX [custom_field_grp_idx] ON [custom_field_group]([category_id]) ON [PRIMARY]
GO

 CREATE  INDEX [custom_field_inf_idx] ON [custom_field_info]([group_id]) ON [PRIMARY]
GO

 CREATE  INDEX [custom_field_rec_idx] ON [custom_field_record]([link_module_id], [link_item_id], [category_id]) ON [PRIMARY]
GO

 CREATE  INDEX [oppcomplist_closedate] ON [opportunity_component]([closedate]) ON [PRIMARY]
GO

 CREATE  INDEX [oppcomplist_description] ON [opportunity_component]([description]) ON [PRIMARY]
GO

 CREATE  INDEX [orglist_name] ON [organization]([name]) ON [PRIMARY]
GO

 CREATE  INDEX [project_assignments_idx] ON [project_assignments]([activity_id]) ON [PRIMARY]
GO

 CREATE  INDEX [project_assignments_cidx] ON [project_assignments]([complete_date], [user_assign_id]) ON [PRIMARY]
GO

 CREATE  INDEX [project_files_cidx] ON [project_files]([link_module_id], [link_item_id]) ON [PRIMARY]
GO

 CREATE  INDEX [project_issues_limit_idx] ON [project_issues]([type_id], [project_id], [enteredBy]) ON [PRIMARY]
GO

 CREATE  INDEX [project_issues_idx] ON [project_issues]([issue_id]) ON [PRIMARY]
GO

 CREATE  INDEX [projects_idx] ON [projects]([group_id], [project_id]) ON [PRIMARY]
GO

 CREATE  UNIQUE  INDEX [idx_sync_map] ON [sync_map]([client_id], [table_id], [record_id]) ON [PRIMARY]
GO

 CREATE  INDEX [ticket_cidx] ON [ticket]([assigned_to], [closed]) ON [PRIMARY]
GO

 CREATE  INDEX [ticketlist_entered] ON [ticket]([entered]) ON [PRIMARY]
GO

ALTER TABLE [access_log] ADD 
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [account_type_levels] ADD 
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [lookup_account_types] (
		[code]
	)
GO

ALTER TABLE [action_item] ADD 
	 FOREIGN KEY 
	(
		[action_id]
	) REFERENCES [action_list] (
		[action_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [action_item_log] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [action_item] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [action_list] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [active_campaign_groups] ADD 
	 FOREIGN KEY 
	(
		[campaign_id]
	) REFERENCES [campaign] (
		[campaign_id]
	)
GO

ALTER TABLE [active_survey] ADD 
	 FOREIGN KEY 
	(
		[campaign_id]
	) REFERENCES [campaign] (
		[campaign_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[type]
	) REFERENCES [lookup_survey_types] (
		[code]
	)
GO

ALTER TABLE [active_survey_answer_avg] ADD 
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [active_survey_items] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[question_id]
	) REFERENCES [active_survey_questions] (
		[question_id]
	)
GO

ALTER TABLE [active_survey_answer_items] ADD 
	 FOREIGN KEY 
	(
		[answer_id]
	) REFERENCES [active_survey_answers] (
		[answer_id]
	),
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [active_survey_items] (
		[item_id]
	)
GO

ALTER TABLE [active_survey_answers] ADD 
	 FOREIGN KEY 
	(
		[question_id]
	) REFERENCES [active_survey_questions] (
		[question_id]
	),
	 FOREIGN KEY 
	(
		[response_id]
	) REFERENCES [active_survey_responses] (
		[response_id]
	)
GO

ALTER TABLE [active_survey_items] ADD 
	 FOREIGN KEY 
	(
		[question_id]
	) REFERENCES [active_survey_questions] (
		[question_id]
	)
GO

ALTER TABLE [active_survey_questions] ADD 
	 FOREIGN KEY 
	(
		[active_survey_id]
	) REFERENCES [active_survey] (
		[active_survey_id]
	),
	 FOREIGN KEY 
	(
		[type]
	) REFERENCES [lookup_survey_types] (
		[code]
	)
GO

ALTER TABLE [active_survey_responses] ADD 
	 FOREIGN KEY 
	(
		[active_survey_id]
	) REFERENCES [active_survey] (
		[active_survey_id]
	)
GO

ALTER TABLE [autoguide_ad_run] ADD 
	 FOREIGN KEY 
	(
		[inventory_id]
	) REFERENCES [autoguide_inventory] (
		[inventory_id]
	)
GO

ALTER TABLE [autoguide_inventory] ADD 
	 FOREIGN KEY 
	(
		[account_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[vehicle_id]
	) REFERENCES [autoguide_vehicle] (
		[vehicle_id]
	)
GO

ALTER TABLE [autoguide_inventory_options] ADD 
	 FOREIGN KEY 
	(
		[inventory_id]
	) REFERENCES [autoguide_inventory] (
		[inventory_id]
	)
GO

ALTER TABLE [autoguide_model] ADD 
	 FOREIGN KEY 
	(
		[make_id]
	) REFERENCES [autoguide_make] (
		[make_id]
	)
GO

ALTER TABLE [autoguide_vehicle] ADD 
	 FOREIGN KEY 
	(
		[make_id]
	) REFERENCES [autoguide_make] (
		[make_id]
	),
	 FOREIGN KEY 
	(
		[model_id]
	) REFERENCES [autoguide_model] (
		[model_id]
	)
GO

ALTER TABLE [business_process] ADD 
	 FOREIGN KEY 
	(
		[link_module_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [business_process_component] ADD 
	 FOREIGN KEY 
	(
		[component_id]
	) REFERENCES [business_process_component_library] (
		[component_id]
	),
	 FOREIGN KEY 
	(
		[parent_id]
	) REFERENCES [business_process_component] (
		[id]
	),
	 FOREIGN KEY 
	(
		[process_id]
	) REFERENCES [business_process] (
		[process_id]
	)
GO

ALTER TABLE [business_process_component_parameter] ADD 
	 FOREIGN KEY 
	(
		[component_id]
	) REFERENCES [business_process_component] (
		[id]
	),
	 FOREIGN KEY 
	(
		[parameter_id]
	) REFERENCES [business_process_parameter_library] (
		[parameter_id]
	)
GO

ALTER TABLE [business_process_component_result_lookup] ADD 
	 FOREIGN KEY 
	(
		[component_id]
	) REFERENCES [business_process_component_library] (
		[component_id]
	)
GO

ALTER TABLE [business_process_events] ADD 
	 FOREIGN KEY 
	(
		[process_id]
	) REFERENCES [business_process] (
		[process_id]
	)
GO

ALTER TABLE [business_process_hook] ADD 
	 FOREIGN KEY 
	(
		[process_id]
	) REFERENCES [business_process] (
		[process_id]
	),
	 FOREIGN KEY 
	(
		[trigger_id]
	) REFERENCES [business_process_hook_triggers] (
		[trigger_id]
	)
GO

ALTER TABLE [business_process_hook_library] ADD 
	 FOREIGN KEY 
	(
		[link_module_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [business_process_hook_triggers] ADD 
	 FOREIGN KEY 
	(
		[hook_id]
	) REFERENCES [business_process_hook_library] (
		[hook_id]
	)
GO

ALTER TABLE [business_process_parameter] ADD 
	 FOREIGN KEY 
	(
		[process_id]
	) REFERENCES [business_process] (
		[process_id]
	)
GO

ALTER TABLE [call_log] ADD 
	 FOREIGN KEY 
	(
		[call_type_id]
	) REFERENCES [lookup_call_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[opp_id]
	) REFERENCES [opportunity_header] (
		[opp_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	)
GO

ALTER TABLE [campaign] ADD 
	 FOREIGN KEY 
	(
		[approvedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [campaign_list_groups] ADD 
	 FOREIGN KEY 
	(
		[campaign_id]
	) REFERENCES [campaign] (
		[campaign_id]
	),
	 FOREIGN KEY 
	(
		[group_id]
	) REFERENCES [saved_criterialist] (
		[id]
	)
GO

ALTER TABLE [campaign_run] ADD 
	 FOREIGN KEY 
	(
		[campaign_id]
	) REFERENCES [campaign] (
		[campaign_id]
	)
GO

ALTER TABLE [campaign_survey_link] ADD 
	 FOREIGN KEY 
	(
		[campaign_id]
	) REFERENCES [campaign] (
		[campaign_id]
	),
	 FOREIGN KEY 
	(
		[survey_id]
	) REFERENCES [survey] (
		[survey_id]
	)
GO

ALTER TABLE [cfsinbox_message] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [cfsinbox_messagelink] ADD 
	 FOREIGN KEY 
	(
		[sent_to]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[sent_from]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[id]
	) REFERENCES [cfsinbox_message] (
		[id]
	)
GO

ALTER TABLE [contact] ADD 
	 FOREIGN KEY 
	(
		[access_type]
	) REFERENCES [lookup_access_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[assistant]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[department]
	) REFERENCES [lookup_department] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[super]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [contact_address] ADD 
	 FOREIGN KEY 
	(
		[address_type]
	) REFERENCES [lookup_contactaddress_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [contact_emailaddress] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[emailaddress_type]
	) REFERENCES [lookup_contactemail_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [contact_phone] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[phone_type]
	) REFERENCES [lookup_contactphone_types] (
		[code]
	)
GO

ALTER TABLE [contact_type_levels] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [lookup_contact_types] (
		[code]
	)
GO

ALTER TABLE [custom_field_category] ADD 
	 FOREIGN KEY 
	(
		[module_id]
	) REFERENCES [module_field_categorylink] (
		[category_id]
	)
GO

ALTER TABLE [custom_field_data] ADD 
	 FOREIGN KEY 
	(
		[field_id]
	) REFERENCES [custom_field_info] (
		[field_id]
	),
	 FOREIGN KEY 
	(
		[record_id]
	) REFERENCES [custom_field_record] (
		[record_id]
	)
GO

ALTER TABLE [custom_field_group] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [custom_field_category] (
		[category_id]
	)
GO

ALTER TABLE [custom_field_info] ADD 
	 FOREIGN KEY 
	(
		[group_id]
	) REFERENCES [custom_field_group] (
		[group_id]
	)
GO

ALTER TABLE [custom_field_lookup] ADD 
	 FOREIGN KEY 
	(
		[field_id]
	) REFERENCES [custom_field_info] (
		[field_id]
	)
GO

ALTER TABLE [custom_field_record] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [custom_field_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [excluded_recipient] ADD 
	 FOREIGN KEY 
	(
		[campaign_id]
	) REFERENCES [campaign] (
		[campaign_id]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	)
GO

ALTER TABLE [help_business_rules] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_contents] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_module_id]
	) REFERENCES [help_module] (
		[module_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[nextcontent]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[prevcontent]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[upcontent]
	) REFERENCES [help_contents] (
		[help_id]
	)
GO

ALTER TABLE [help_faqs] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owning_module_id]
	) REFERENCES [help_module] (
		[module_id]
	)
GO

ALTER TABLE [help_features] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[link_feature_id]
	) REFERENCES [lookup_help_features] (
		[code]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_module] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [help_notes] ADD 
	 FOREIGN KEY 
	(
		[completedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_related_links] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[linkto_content_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owning_module_id]
	) REFERENCES [help_module] (
		[module_id]
	)
GO

ALTER TABLE [help_tableof_contents] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[firstchild]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[nextsibling]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	),
	 FOREIGN KEY 
	(
		[parent]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	)
GO

ALTER TABLE [help_tableofcontentitem_links] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[global_link_id]
	) REFERENCES [help_tableof_contents] (
		[content_id]
	),
	 FOREIGN KEY 
	(
		[linkto_content_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [help_tips] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[link_help_id]
	) REFERENCES [help_contents] (
		[help_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [lookup_contact_types] ADD 
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [lookup_lists_lookup] ADD 
	 FOREIGN KEY 
	(
		[module_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [message] ADD 
	 FOREIGN KEY 
	(
		[access_type]
	) REFERENCES [lookup_access_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [message_template] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [module_field_categorylink] ADD 
	 FOREIGN KEY 
	(
		[module_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [news] ADD 
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	)
GO

ALTER TABLE [opportunity_component] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[opp_id]
	) REFERENCES [opportunity_header] (
		[opp_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[stage]
	) REFERENCES [lookup_stage] (
		[code]
	)
GO

ALTER TABLE [opportunity_component_levels] ADD 
	 FOREIGN KEY 
	(
		[opp_id]
	) REFERENCES [opportunity_component] (
		[id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [lookup_opportunity_types] (
		[code]
	)
GO

ALTER TABLE [opportunity_header] ADD 
	 FOREIGN KEY 
	(
		[acctlink]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[contactlink]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [organization] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [organization_address] ADD 
	 FOREIGN KEY 
	(
		[address_type]
	) REFERENCES [lookup_orgaddress_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	)
GO

ALTER TABLE [organization_emailaddress] ADD 
	 FOREIGN KEY 
	(
		[emailaddress_type]
	) REFERENCES [lookup_orgemail_types] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	)
GO

ALTER TABLE [organization_phone] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[phone_type]
	) REFERENCES [lookup_orgphone_types] (
		[code]
	)
GO

ALTER TABLE [permission] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	)
GO

ALTER TABLE [process_log] ADD 
	 FOREIGN KEY 
	(
		[client_id]
	) REFERENCES [sync_client] (
		[client_id]
	),
	 FOREIGN KEY 
	(
		[system_id]
	) REFERENCES [sync_system] (
		[system_id]
	)
GO

ALTER TABLE [project_assignments] ADD 
	 FOREIGN KEY 
	(
		[activity_id]
	) REFERENCES [lookup_project_activity] (
		[code]
	),
	 FOREIGN KEY 
	(
		[actual_loetype]
	) REFERENCES [lookup_project_loe] (
		[code]
	),
	 FOREIGN KEY 
	(
		[assignedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[estimated_loetype]
	) REFERENCES [lookup_project_loe] (
		[code]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[priority_id]
	) REFERENCES [lookup_project_priority] (
		[code]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	),
	 FOREIGN KEY 
	(
		[requirement_id]
	) REFERENCES [project_requirements] (
		[requirement_id]
	),
	 FOREIGN KEY 
	(
		[status_id]
	) REFERENCES [lookup_project_status] (
		[code]
	),
	 FOREIGN KEY 
	(
		[user_assign_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [project_assignments_status] ADD 
	 FOREIGN KEY 
	(
		[assignment_id]
	) REFERENCES [project_assignments] (
		[assignment_id]
	),
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [project_files] ADD 
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[folder_id]
	) REFERENCES [project_folders] (
		[folder_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [project_files_download] ADD 
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[user_download_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [project_files_version] ADD 
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[item_id]
	) REFERENCES [project_files] (
		[item_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [project_issue_replies] ADD 
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[issue_id]
	) REFERENCES [project_issues] (
		[issue_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [project_issues] ADD 
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	),
	 FOREIGN KEY 
	(
		[type_id]
	) REFERENCES [lookup_project_issues] (
		[code]
	)
GO

ALTER TABLE [project_requirements] ADD 
	 FOREIGN KEY 
	(
		[actual_loetype]
	) REFERENCES [lookup_project_loe] (
		[code]
	),
	 FOREIGN KEY 
	(
		[approvedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[closedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[estimated_loetype]
	) REFERENCES [lookup_project_loe] (
		[code]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	)
GO

ALTER TABLE [project_team] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	),
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [projects] ADD 
	 FOREIGN KEY 
	(
		[department_id]
	) REFERENCES [lookup_department] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredBy]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedBy]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [report] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [permission_category] (
		[category_id]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[permission_id]
	) REFERENCES [permission] (
		[permission_id]
	)
GO

ALTER TABLE [report_criteria] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[report_id]
	) REFERENCES [report] (
		[report_id]
	)
GO

ALTER TABLE [report_criteria_parameter] ADD 
	 FOREIGN KEY 
	(
		[criteria_id]
	) REFERENCES [report_criteria] (
		[criteria_id]
	)
GO

ALTER TABLE [report_queue] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[report_id]
	) REFERENCES [report] (
		[report_id]
	)
GO

ALTER TABLE [report_queue_criteria] ADD 
	 FOREIGN KEY 
	(
		[queue_id]
	) REFERENCES [report_queue] (
		[queue_id]
	)
GO

ALTER TABLE [revenue] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[type]
	) REFERENCES [lookup_revenue_types] (
		[code]
	)
GO

ALTER TABLE [revenue_detail] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[revenue_id]
	) REFERENCES [revenue] (
		[id]
	),
	 FOREIGN KEY 
	(
		[type]
	) REFERENCES [lookup_revenue_types] (
		[code]
	)
GO

ALTER TABLE [role] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [role_permission] ADD 
	 FOREIGN KEY 
	(
		[permission_id]
	) REFERENCES [permission] (
		[permission_id]
	),
	 FOREIGN KEY 
	(
		[role_id]
	) REFERENCES [role] (
		[role_id]
	)
GO

ALTER TABLE [saved_criteriaelement] ADD 
	 FOREIGN KEY 
	(
		[field]
	) REFERENCES [search_fields] (
		[id]
	),
	 FOREIGN KEY 
	(
		[operatorid]
	) REFERENCES [field_types] (
		[id]
	),
	 FOREIGN KEY 
	(
		[id]
	) REFERENCES [saved_criterialist] (
		[id]
	)
GO

ALTER TABLE [saved_criterialist] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [scheduled_recipient] ADD 
	 FOREIGN KEY 
	(
		[campaign_id]
	) REFERENCES [campaign] (
		[campaign_id]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	)
GO

ALTER TABLE [survey] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [survey_items] ADD 
	 FOREIGN KEY 
	(
		[question_id]
	) REFERENCES [survey_questions] (
		[question_id]
	)
GO

ALTER TABLE [survey_questions] ADD 
	 FOREIGN KEY 
	(
		[survey_id]
	) REFERENCES [survey] (
		[survey_id]
	),
	 FOREIGN KEY 
	(
		[type]
	) REFERENCES [lookup_survey_types] (
		[code]
	)
GO

ALTER TABLE [sync_conflict_log] ADD 
	 FOREIGN KEY 
	(
		[client_id]
	) REFERENCES [sync_client] (
		[client_id]
	),
	 FOREIGN KEY 
	(
		[table_id]
	) REFERENCES [sync_table] (
		[table_id]
	)
GO

ALTER TABLE [sync_log] ADD 
	 FOREIGN KEY 
	(
		[client_id]
	) REFERENCES [sync_client] (
		[client_id]
	),
	 FOREIGN KEY 
	(
		[system_id]
	) REFERENCES [sync_system] (
		[system_id]
	)
GO

ALTER TABLE [sync_map] ADD 
	 FOREIGN KEY 
	(
		[client_id]
	) REFERENCES [sync_client] (
		[client_id]
	),
	 FOREIGN KEY 
	(
		[table_id]
	) REFERENCES [sync_table] (
		[table_id]
	)
GO

ALTER TABLE [sync_table] ADD 
	 FOREIGN KEY 
	(
		[system_id]
	) REFERENCES [sync_system] (
		[system_id]
	)
GO

ALTER TABLE [sync_transaction_log] ADD 
	 FOREIGN KEY 
	(
		[log_id]
	) REFERENCES [sync_log] (
		[log_id]
	)
GO

ALTER TABLE [task] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [lookup_task_category] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[estimatedloetype]
	) REFERENCES [lookup_task_loe] (
		[code]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[owner]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[priority]
	) REFERENCES [lookup_task_priority] (
		[code]
	)
GO

ALTER TABLE [taskcategory_project] ADD 
	 FOREIGN KEY 
	(
		[category_id]
	) REFERENCES [lookup_task_category] (
		[code]
	),
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	)
GO

ALTER TABLE [tasklink_contact] ADD 
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[task_id]
	) REFERENCES [task] (
		[task_id]
	)
GO

ALTER TABLE [tasklink_project] ADD 
	 FOREIGN KEY 
	(
		[project_id]
	) REFERENCES [projects] (
		[project_id]
	),
	 FOREIGN KEY 
	(
		[task_id]
	) REFERENCES [task] (
		[task_id]
	)
GO

ALTER TABLE [tasklink_ticket] ADD 
	 FOREIGN KEY 
	(
		[task_id]
	) REFERENCES [task] (
		[task_id]
	),
	 FOREIGN KEY 
	(
		[ticket_id]
	) REFERENCES [ticket] (
		[ticketid]
	)
GO

ALTER TABLE [ticket] ADD 
	 FOREIGN KEY 
	(
		[assigned_to]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[contact_id]
	) REFERENCES [contact] (
		[contact_id]
	),
	 FOREIGN KEY 
	(
		[department_code]
	) REFERENCES [lookup_department] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[level_code]
	) REFERENCES [ticket_level] (
		[code]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[org_id]
	) REFERENCES [organization] (
		[org_id]
	),
	 FOREIGN KEY 
	(
		[pri_code]
	) REFERENCES [ticket_priority] (
		[code]
	),
	 FOREIGN KEY 
	(
		[scode]
	) REFERENCES [ticket_severity] (
		[code]
	),
	 FOREIGN KEY 
	(
		[source_code]
	) REFERENCES [lookup_ticketsource] (
		[code]
	)
GO

ALTER TABLE [ticketlog] ADD 
	 FOREIGN KEY 
	(
		[assigned_to]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[department_code]
	) REFERENCES [lookup_department] (
		[code]
	),
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[pri_code]
	) REFERENCES [ticket_priority] (
		[code]
	),
	 FOREIGN KEY 
	(
		[scode]
	) REFERENCES [ticket_severity] (
		[code]
	),
	 FOREIGN KEY 
	(
		[ticketid]
	) REFERENCES [ticket] (
		[ticketid]
	)
GO

ALTER TABLE [viewpoint] ADD 
	 FOREIGN KEY 
	(
		[enteredby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[modifiedby]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[user_id]
	) REFERENCES [access] (
		[user_id]
	),
	 FOREIGN KEY 
	(
		[vp_user_id]
	) REFERENCES [access] (
		[user_id]
	)
GO

ALTER TABLE [viewpoint_permission] ADD 
	 FOREIGN KEY 
	(
		[permission_id]
	) REFERENCES [permission] (
		[permission_id]
	),
	 FOREIGN KEY 
	(
		[viewpoint_id]
	) REFERENCES [viewpoint] (
		[viewpoint_id]
	)
GO

-- Insert default events
SET NOCOUNT ON
SET IDENTITY_INSERT [events] ON
GO
INSERT [events] ([event_id],[second],[minute],[hour],[dayofmonth],[month],[dayofweek],[year],[task],[extrainfo],[businessDays],[enabled],[entered])VALUES(1,'0','*/5','*','*','*','*','*','org.aspcfs.apps.notifier.Notifier#doTask','${FILEPATH}','true',1,'Feb  4 2004  1:36:16:717PM')
INSERT [events] ([event_id],[second],[minute],[hour],[dayofmonth],[month],[dayofweek],[year],[task],[extrainfo],[businessDays],[enabled],[entered])VALUES(2,'0','*/1','*','*','*','*','*','org.aspcfs.apps.reportRunner.ReportRunner#doTask','${FILEPATH}','true',1,'Feb  4 2004  1:36:16:750PM')
INSERT [events] ([event_id],[second],[minute],[hour],[dayofmonth],[month],[dayofweek],[year],[task],[extrainfo],[businessDays],[enabled],[entered])VALUES(3,'0','0','*/12','*','*','*','*','org.aspcfs.apps.reportRunner.ReportCleanup#doTask','${FILEPATH}','true',1,'Feb  4 2004  1:36:16:750PM')
INSERT [events] ([event_id],[second],[minute],[hour],[dayofmonth],[month],[dayofweek],[year],[task],[extrainfo],[businessDays],[enabled],[entered])VALUES(4,'0','0','0','*','*','*','*','org.aspcfs.modules.service.tasks.GetURL#doTask','http://${WEBSERVER.URL}/ProcessSystem.do?command=ClearGraphData','true',1,'Feb  4 2004  1:36:16:763PM')
SET IDENTITY_INSERT [events] OFF
GO
SET NOCOUNT OFF

-- Insert default access
SET NOCOUNT ON
SET IDENTITY_INSERT [access] ON
GO
INSERT [access] ([user_id],[username],[password],[contact_id],[role_id],[manager_id],[startofday],[endofday],[locale],[timezone],[last_ip],[last_login],[enteredby],[entered],[modifiedby],[modified],[expires],[alias],[assistant],[enabled])VALUES(0,'dhvadmin','---',-1,1,-1,8,18,NULL,'America/New_York',NULL,'Feb 17 2004 10:46:11:717AM',0,'Feb 17 2004 10:46:11:717AM',0,'Feb 17 2004 10:46:11:717AM',NULL,-1,-1,1)
SET IDENTITY_INSERT [access] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_industry
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_industry] ON
GO
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(1,NULL,'Automotive',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(2,NULL,'Biotechnology',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(3,NULL,'Broadcasting and Cable',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(4,NULL,'Computer',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(5,NULL,'Consulting',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(6,NULL,'Defense',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(7,NULL,'Energy',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(8,NULL,'Financial Services',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(9,NULL,'Food',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(10,NULL,'Healthcare',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(11,NULL,'Hospitality',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(12,NULL,'Insurance',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(13,NULL,'Internet',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(14,NULL,'Law Firms',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(15,NULL,'Media',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(16,NULL,'Pharmaceuticals',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(17,NULL,'Real Estate',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(18,NULL,'Retail',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(19,NULL,'Telecommunications',0,0,1)
INSERT [lookup_industry] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(20,NULL,'Transportation',0,0,1)
SET IDENTITY_INSERT [lookup_industry] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_contact_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_contact_types] ON
GO
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(1,'Acquaintance',0,0,1,NULL,0)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(2,'Competitor',0,0,1,NULL,0)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(3,'Customer',0,0,1,NULL,0)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(4,'Friend',0,0,1,NULL,0)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(5,'Prospect',0,0,1,NULL,0)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(6,'Shareholder',0,0,1,NULL,0)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(7,'Vendor',0,0,1,NULL,0)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(8,'Accounting',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(9,'Administrative',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(10,'Business Development',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(11,'Customer Service',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(12,'Engineering',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(13,'Executive',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(14,'Finance',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(15,'Marketing',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(16,'Operations',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(17,'Procurement',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(18,'Sales',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(19,'Shipping/Receiving',0,0,1,NULL,1)
INSERT [lookup_contact_types] ([code],[description],[default_item],[level],[enabled],[user_id],[category])VALUES(20,'Technology',0,0,1,NULL,1)
SET IDENTITY_INSERT [lookup_contact_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_account_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_account_types] ON
GO
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Small',0,10,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Medium',0,20,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Large',0,30,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Contract',0,40,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Territory 1: Northeast',0,50,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Territory 2: Southeast',0,60,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Territory 3: Midwest',0,70,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Territory 4: Northwest',0,80,1)
INSERT [lookup_account_types] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Territory 5: Southwest',0,90,1)
SET IDENTITY_INSERT [lookup_account_types] OFF
GO
SET NOCOUNT OFF

-- Insert default state
SET NOCOUNT ON
INSERT [state] ([state_code],[state])VALUES('AK','Alaska')
INSERT [state] ([state_code],[state])VALUES('AL','Alabama')
INSERT [state] ([state_code],[state])VALUES('AR','Arkansas')
INSERT [state] ([state_code],[state])VALUES('AZ','Arizona')
INSERT [state] ([state_code],[state])VALUES('CA','California')
INSERT [state] ([state_code],[state])VALUES('CO','Colorado')
INSERT [state] ([state_code],[state])VALUES('CT','Connecticut')
INSERT [state] ([state_code],[state])VALUES('DC','Washington D.C.')
INSERT [state] ([state_code],[state])VALUES('DE','Delaware')
INSERT [state] ([state_code],[state])VALUES('FL','Florida')
INSERT [state] ([state_code],[state])VALUES('GA','Georgia')
INSERT [state] ([state_code],[state])VALUES('HI','Hawaii')
INSERT [state] ([state_code],[state])VALUES('ID','Idaho')
INSERT [state] ([state_code],[state])VALUES('IL','Illinois')
INSERT [state] ([state_code],[state])VALUES('IN','Indiana')
INSERT [state] ([state_code],[state])VALUES('KS','Kansas')
INSERT [state] ([state_code],[state])VALUES('KY','Kentucky')
INSERT [state] ([state_code],[state])VALUES('LA','Louisiana')
INSERT [state] ([state_code],[state])VALUES('MA','Massachusetts')
INSERT [state] ([state_code],[state])VALUES('MD','Maryland')
INSERT [state] ([state_code],[state])VALUES('ME','Maine')
INSERT [state] ([state_code],[state])VALUES('MI','Michigan')
INSERT [state] ([state_code],[state])VALUES('MN','Minnesota')
INSERT [state] ([state_code],[state])VALUES('MO','Mossouri')
INSERT [state] ([state_code],[state])VALUES('MS','Mississippi')
INSERT [state] ([state_code],[state])VALUES('MT','Montana')
INSERT [state] ([state_code],[state])VALUES('NC','North Carolina')
INSERT [state] ([state_code],[state])VALUES('ND','North Dakota')
INSERT [state] ([state_code],[state])VALUES('NE','Nebraska')
INSERT [state] ([state_code],[state])VALUES('NH','New Hampshire')
INSERT [state] ([state_code],[state])VALUES('NJ','New Jersey')
INSERT [state] ([state_code],[state])VALUES('NM','New Mexico')
INSERT [state] ([state_code],[state])VALUES('NV','Nevada')
INSERT [state] ([state_code],[state])VALUES('NY','New York')
INSERT [state] ([state_code],[state])VALUES('OH','Ohio')
INSERT [state] ([state_code],[state])VALUES('OK','Oklahoma')
INSERT [state] ([state_code],[state])VALUES('OR','Oregon')
INSERT [state] ([state_code],[state])VALUES('PA','Pennsylvania')
INSERT [state] ([state_code],[state])VALUES('RI','Rhode Island')
INSERT [state] ([state_code],[state])VALUES('SC','South Carolina')
INSERT [state] ([state_code],[state])VALUES('SD','South Dakota')
INSERT [state] ([state_code],[state])VALUES('TN','Tennessee')
INSERT [state] ([state_code],[state])VALUES('TX','Texas')
INSERT [state] ([state_code],[state])VALUES('UT','Utah')
INSERT [state] ([state_code],[state])VALUES('VA','Virginia')
INSERT [state] ([state_code],[state])VALUES('VT','Vermont')
INSERT [state] ([state_code],[state])VALUES('WA','Washington')
INSERT [state] ([state_code],[state])VALUES('WI','Wisconsin')
INSERT [state] ([state_code],[state])VALUES('WV','West Virginia')
INSERT [state] ([state_code],[state])VALUES('WY','Wyoming')
SET NOCOUNT OFF

-- Insert default lookup_department
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_department] ON
GO
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Accounting',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Administration',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Billing',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Customer Relations',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Engineering',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Finance',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Human Resources',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Legal',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Marketing',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(10,'Operations',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(11,'Purchasing',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(12,'Sales',0,0,1)
INSERT [lookup_department] ([code],[description],[default_item],[level],[enabled])VALUES(13,'Shipping/Receiving',0,0,1)
SET IDENTITY_INSERT [lookup_department] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_orgaddress_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_orgaddress_types] ON
GO
INSERT [lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Primary',0,10,1)
INSERT [lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Auxiliary',0,20,1)
INSERT [lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Billing',0,30,1)
INSERT [lookup_orgaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Shipping',0,40,1)
SET IDENTITY_INSERT [lookup_orgaddress_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_orgemail_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_orgemail_types] ON
GO
INSERT [lookup_orgemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Primary',0,10,1)
INSERT [lookup_orgemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Auxiliary',0,20,1)
SET IDENTITY_INSERT [lookup_orgemail_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_orgphone_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_orgphone_types] ON
GO
INSERT [lookup_orgphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Main',0,10,1)
INSERT [lookup_orgphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Fax',0,20,1)
SET IDENTITY_INSERT [lookup_orgphone_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_contactaddress_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_contactaddress_types] ON
GO
INSERT [lookup_contactaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Business',0,10,1)
INSERT [lookup_contactaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Home',0,20,1)
INSERT [lookup_contactaddress_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Other',0,30,1)
SET IDENTITY_INSERT [lookup_contactaddress_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_contactemail_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_contactemail_types] ON
GO
INSERT [lookup_contactemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Business',0,10,1)
INSERT [lookup_contactemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Personal',0,20,1)
INSERT [lookup_contactemail_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Other',0,30,1)
SET IDENTITY_INSERT [lookup_contactemail_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_contactphone_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_contactphone_types] ON
GO
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Business',0,10,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Business2',0,20,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Business Fax',0,30,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Home',0,40,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Home2',0,50,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Home Fax',0,60,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Mobile',0,70,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Pager',0,80,1)
INSERT [lookup_contactphone_types] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Other',0,90,1)
SET IDENTITY_INSERT [lookup_contactphone_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_access_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_access_types] ON
GO
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(1,626030330,'Controlled-Hierarchy',1,1,1,626030335)
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(2,626030330,'Public',0,2,1,626030334)
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(3,626030330,'Personal',0,3,1,626030333)
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(4,626030331,'Public',1,1,1,626030334)
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(5,626030332,'Public',1,1,1,626030334)
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(6,707031028,'Controlled-Hierarchy',1,1,1,626030335)
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(7,707031028,'Public',0,2,1,626030334)
INSERT [lookup_access_types] ([code],[link_module_id],[description],[default_item],[level],[enabled],[rule_id])VALUES(8,707031028,'Personal',0,3,1,626030333)
SET IDENTITY_INSERT [lookup_access_types] OFF
GO
SET NOCOUNT OFF

-- Insert default organization
SET NOCOUNT ON
SET IDENTITY_INSERT [organization] ON
GO
INSERT [organization] ([org_id],[name],[account_number],[account_group],[url],[revenue],[employees],[notes],[sic_code],[ticker_symbol],[taxid],[lead],[sales_rep],[miner_only],[defaultlocale],[fiscalmonth],[entered],[enteredby],[modified],[modifiedby],[enabled],[industry_temp_code],[owner],[duplicate_id],[custom1],[custom2],[contract_end],[alertdate],[alert],[custom_data],[namesalutation],[namelast],[namefirst],[namemiddle],[namesuffix])VALUES(0,'My Company',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,'Feb 17 2004 10:46:11:780AM',0,'Feb 17 2004 10:46:11:780AM',0,1,NULL,0,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)
SET IDENTITY_INSERT [organization] OFF
GO
SET NOCOUNT OFF

-- Insert default role
SET NOCOUNT ON
SET IDENTITY_INSERT [role] ON
GO
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,'Administrator','Performs system configuration and maintenance',0,'Feb 17 2004 10:46:19:297AM',0,'Feb 17 2004 10:46:19:297AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,'Operations Manager','Manages operations',0,'Feb 17 2004 10:46:20:233AM',0,'Feb 17 2004 10:46:20:233AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(3,'Sales Manager','Manages all accounts and opportunities',0,'Feb 17 2004 10:46:21:357AM',0,'Feb 17 2004 10:46:21:357AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(4,'Salesperson','Manages own accounts and opportunities',0,'Feb 17 2004 10:46:22:233AM',0,'Feb 17 2004 10:46:22:233AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(5,'Customer Service Manager','Manages all tickets',0,'Feb 17 2004 10:46:22:997AM',0,'Feb 17 2004 10:46:22:997AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(6,'Customer Service Representative','Manages own tickets',0,'Feb 17 2004 10:46:23:480AM',0,'Feb 17 2004 10:46:23:480AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(7,'Marketing Manager','Manages communications',0,'Feb 17 2004 10:46:24:137AM',0,'Feb 17 2004 10:46:24:137AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(8,'Accounting Manager','Reviews revenue and opportunities',0,'Feb 17 2004 10:46:25:013AM',0,'Feb 17 2004 10:46:25:013AM',1)
INSERT [role] ([role_id],[role],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(9,'HR Representative','Manages employee information',0,'Feb 17 2004 10:46:25:750AM',0,'Feb 17 2004 10:46:25:750AM',1)
SET IDENTITY_INSERT [role] OFF
GO
SET NOCOUNT OFF

-- Insert default permission_category
SET NOCOUNT ON
SET IDENTITY_INSERT [permission_category] ON
GO
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(1,'Accounts',NULL,600,1,1,1,1,0,0,0,0,1)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(2,'Contacts',NULL,400,1,1,1,1,0,0,0,0,1)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(3,'Auto Guide',NULL,700,0,0,0,0,0,0,0,0,0)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(4,'Pipeline',NULL,500,1,1,0,1,1,0,0,0,1)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(5,'Demo',NULL,1500,0,0,0,0,0,0,0,0,0)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(6,'Communications',NULL,800,1,1,0,0,0,0,0,0,1)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(7,'Projects',NULL,900,0,0,0,0,0,0,0,0,0)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(8,'Help Desk',NULL,1000,1,1,1,1,0,1,1,1,1)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(9,'Admin',NULL,1200,1,1,0,0,0,0,0,0,1)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(10,'Help',NULL,1300,1,1,0,0,0,0,0,0,0)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(11,'System',NULL,100,1,1,0,0,0,0,0,0,0)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(12,'My Home Page',NULL,200,1,1,0,0,0,0,0,0,1)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(13,'QA',NULL,1400,0,0,0,0,0,0,0,0,0)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(14,'Reports',NULL,1100,1,1,0,0,0,0,0,0,0)
INSERT [permission_category] ([category_id],[category],[description],[level],[enabled],[active],[folders],[lookups],[viewpoints],[categories],[scheduled_events],[object_events],[reports])VALUES(15,'Employees',NULL,300,1,1,0,1,0,0,0,0,1)
SET IDENTITY_INSERT [permission_category] OFF
GO
SET NOCOUNT OFF

-- Insert default permission
SET NOCOUNT ON
SET IDENTITY_INSERT [permission] ON
GO
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(1,1,'accounts',1,0,0,0,'Access to Accounts module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(2,1,'accounts-accounts',1,1,1,1,'Account Records',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(3,1,'accounts-accounts-folders',1,1,1,1,'Folders',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(4,1,'accounts-accounts-contacts',1,1,1,1,'Contacts',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(5,1,'accounts-accounts-contacts-opportunities',1,1,1,1,'Contact Opportunities',50,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(6,1,'accounts-accounts-contacts-calls',1,1,1,1,'Contact Calls',60,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(7,1,'accounts-accounts-contacts-messages',1,1,1,1,'Contact Messages',70,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(8,1,'accounts-accounts-opportunities',1,1,1,1,'Opportunities',80,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(9,1,'accounts-accounts-tickets',1,1,1,1,'Tickets',90,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(10,1,'accounts-accounts-tickets-tasks',1,1,1,1,'Ticket Tasks',100,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(11,1,'accounts-accounts-tickets-folders',1,1,1,1,'Ticket Folders',110,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(12,1,'accounts-accounts-tickets-documents',1,1,1,1,'Ticket Documents',120,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(13,1,'accounts-accounts-documents',1,1,1,1,'Documents',130,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(14,1,'accounts-accounts-reports',1,1,0,1,'Export Account Data',140,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(15,1,'accounts-dashboard',1,0,0,0,'Dashboard',150,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(16,1,'accounts-accounts-revenue',1,1,1,1,'Revenue',160,0,0,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(17,1,'accounts-autoguide-inventory',1,1,1,1,'Auto Guide Vehicle Inventory',170,0,0,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(18,2,'contacts',1,0,0,0,'Access to Contacts module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(19,2,'contacts-external_contacts',1,1,1,1,'General Contact Records',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(20,2,'contacts-external_contacts-reports',1,1,0,1,'Export Contact Data',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(21,2,'contacts-external_contacts-folders',1,1,1,1,'Folders',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(22,2,'contacts-external_contacts-calls',1,1,1,1,'Calls',50,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(23,2,'contacts-external_contacts-messages',1,0,0,0,'Messages',60,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(24,2,'contacts-external_contacts-opportunities',1,1,1,1,'Opportunities',70,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(25,3,'autoguide',1,0,0,0,'Access to the Auto Guide module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(26,3,'autoguide-adruns',0,0,1,0,'Ad Run complete status',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(27,4,'pipeline',1,0,0,0,'Access to Pipeline module',10,1,1,1)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(28,4,'pipeline-opportunities',1,1,1,1,'Opportunity Records',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(29,4,'pipeline-dashboard',1,0,0,0,'Dashboard',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(30,4,'pipeline-reports',1,1,0,1,'Export Opportunity Data',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(31,4,'pipeline-opportunities-calls',1,1,1,1,'Calls',50,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(32,4,'pipeline-opportunities-documents',1,1,1,1,'Documents',60,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(33,5,'demo',1,1,1,1,'Access to Demo/Non-working features',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(34,6,'campaign',1,0,0,0,'Access to Communications module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(35,6,'campaign-dashboard',1,0,0,0,'Dashboard',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(36,6,'campaign-campaigns',1,1,1,1,'Campaign Records',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(37,6,'campaign-campaigns-groups',1,1,1,1,'Group Records',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(38,6,'campaign-campaigns-messages',1,1,1,1,'Message Records',50,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(39,6,'campaign-campaigns-surveys',1,1,1,1,'Survey Records',60,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(40,7,'projects',1,0,0,0,'Access to Project Management module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(41,7,'projects-personal',1,0,0,0,'Personal View',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(42,7,'projects-enterprise',1,0,0,0,'Enterprise View',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(43,7,'projects-projects',1,1,1,1,'Project Records',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(44,8,'tickets',1,0,0,0,'Access to Help Desk module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(45,8,'tickets-tickets',1,1,1,1,'Ticket Records',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(46,8,'tickets-reports',1,1,1,1,'Export Ticket Data',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(47,8,'tickets-tickets-tasks',1,1,1,1,'Tasks',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(48,9,'admin',1,0,0,0,'Access to Admin module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(49,9,'admin-users',1,1,1,1,'Users',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(50,9,'admin-roles',1,1,1,1,'Roles',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(51,9,'admin-usage',1,0,0,0,'System Usage',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(52,9,'admin-sysconfig',1,0,1,0,'System Configuration',50,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(53,9,'admin-sysconfig-lists',1,0,1,0,'Configure Lookup Lists',60,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(54,9,'admin-sysconfig-folders',1,1,1,1,'Configure Custom Folders & Fields',70,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(55,9,'admin-object-workflow',1,1,1,1,'Configure Object Workflow',80,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(56,9,'admin-sysconfig-categories',1,1,1,1,'Categories',90,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(57,10,'help',1,0,0,0,'Access to Help System',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(58,11,'globalitems-search',1,0,0,0,'Access to Global Search',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(59,11,'globalitems-myitems',1,0,0,0,'Access to My Items',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(60,11,'globalitems-recentitems',1,0,0,0,'Access to Recent Items',30,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(61,12,'myhomepage',1,0,0,0,'Access to My Home Page module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(62,12,'myhomepage-dashboard',1,0,0,0,'View Performance Dashboard',20,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(63,12,'myhomepage-miner',1,1,0,1,'Industry News records',30,0,0,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(64,12,'myhomepage-inbox',1,0,0,0,'Mailbox',40,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(65,12,'myhomepage-tasks',1,1,1,1,'Tasks',50,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(66,12,'myhomepage-reassign',1,0,1,0,'Re-assign Items',60,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(67,12,'myhomepage-profile',1,0,0,0,'Profile',70,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(68,12,'myhomepage-profile-personal',1,0,1,0,'Personal Information',80,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(69,12,'myhomepage-profile-settings',1,0,1,0,'Settings',90,0,0,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(70,12,'myhomepage-profile-password',0,0,1,0,'Password',100,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(71,12,'myhomepage-action-lists',1,1,1,1,'Action Lists',110,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(72,13,'qa',1,1,1,1,'Access to QA Tool',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(73,14,'reports',1,0,0,0,'Access to Reports module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(74,15,'employees',1,0,0,0,'Access to Employee module',10,1,1,0)
INSERT [permission] ([permission_id],[category_id],[permission],[permission_view],[permission_add],[permission_edit],[permission_delete],[description],[level],[enabled],[active],[viewpoints])VALUES(75,15,'contacts-internal_contacts',1,1,1,1,'Employees',20,1,1,0)
SET IDENTITY_INSERT [permission] OFF
GO
SET NOCOUNT OFF

-- Insert default role_permission
SET NOCOUNT ON
SET IDENTITY_INSERT [role_permission] ON
GO
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(1,1,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(2,1,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(3,1,63,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(4,1,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(5,1,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(6,1,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(7,1,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(8,1,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(9,1,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(10,1,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(11,1,18,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(12,1,19,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(13,1,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(14,1,2,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(15,1,3,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(16,1,4,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(17,1,5,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(18,1,6,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(19,1,7,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(20,1,8,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(21,1,9,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(22,1,10,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(23,1,11,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(24,1,12,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(25,1,13,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(26,1,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(27,1,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(28,1,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(29,1,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(30,1,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(31,1,45,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(32,1,46,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(33,1,47,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(34,1,74,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(35,1,75,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(36,1,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(37,1,48,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(38,1,49,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(39,1,50,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(40,1,52,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(41,1,51,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(42,1,53,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(43,1,54,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(44,1,55,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(45,1,56,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(46,1,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(47,1,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(48,1,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(49,1,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(50,2,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(51,2,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(52,2,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(53,2,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(54,2,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(55,2,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(56,2,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(57,2,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(58,2,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(59,2,71,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(60,2,18,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(61,2,19,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(62,2,20,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(63,2,21,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(64,2,22,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(65,2,23,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(66,2,24,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(67,2,27,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(68,2,28,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(69,2,29,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(70,2,30,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(71,2,31,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(72,2,32,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(73,2,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(74,2,2,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(75,2,3,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(76,2,4,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(77,2,5,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(78,2,6,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(79,2,7,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(80,2,8,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(81,2,9,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(82,2,13,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(83,2,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(84,2,16,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(85,2,12,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(86,2,11,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(87,2,10,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(88,2,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(89,2,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(90,2,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(91,2,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(92,2,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(93,2,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(94,2,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(95,2,45,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(96,2,46,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(97,2,47,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(98,2,74,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(99,2,75,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(100,2,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(101,2,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(102,2,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(103,2,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(104,2,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(105,3,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(106,3,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(107,3,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(108,3,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(109,3,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(110,3,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(111,3,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(112,3,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(113,3,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(114,3,71,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(115,3,18,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(116,3,19,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(117,3,20,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(118,3,21,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(119,3,22,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(120,3,23,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(121,3,24,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(122,3,27,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(123,3,28,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(124,3,29,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(125,3,30,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(126,3,31,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(127,3,32,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(128,3,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(129,3,2,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(130,3,3,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(131,3,4,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(132,3,5,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(133,3,6,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(134,3,7,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(135,3,8,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(136,3,9,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(137,3,13,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(138,3,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(139,3,15,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(140,3,16,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(141,3,12,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(142,3,11,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(143,3,10,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(144,3,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(145,3,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(146,3,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(147,3,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(148,3,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(149,3,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(150,3,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(151,3,45,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(152,3,46,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(153,3,47,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(154,3,74,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(155,3,75,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(156,3,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(157,3,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(158,3,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(159,3,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(160,3,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(161,4,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(162,4,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(163,4,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(164,4,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(165,4,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(166,4,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(167,4,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(168,4,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(169,4,71,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(170,4,18,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(171,4,19,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(172,4,20,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(173,4,22,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(174,4,23,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(175,4,24,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(176,4,27,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(177,4,28,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(178,4,29,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(179,4,30,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(180,4,31,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(181,4,32,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(182,4,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(183,4,2,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(184,4,3,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(185,4,4,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(186,4,5,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(187,4,6,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(188,4,7,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(189,4,8,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(190,4,9,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(191,4,13,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(192,4,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(193,4,15,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(194,4,16,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(195,4,12,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(196,4,11,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(197,4,10,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(198,4,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(199,4,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(200,4,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(201,4,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(202,4,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(203,4,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(204,4,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(205,4,45,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(206,4,46,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(207,4,47,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(208,4,74,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(209,4,75,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(210,4,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(211,4,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(212,4,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(213,4,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(214,4,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(215,5,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(216,5,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(217,5,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(218,5,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(219,5,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(220,5,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(221,5,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(222,5,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(223,5,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(224,5,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(225,5,2,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(226,5,3,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(227,5,4,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(228,5,6,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(229,5,7,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(230,5,9,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(231,5,13,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(232,5,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(233,5,12,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(234,5,11,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(235,5,10,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(236,5,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(237,5,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(238,5,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(239,5,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(240,5,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(241,5,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(242,5,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(243,5,45,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(244,5,46,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(245,5,47,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(246,5,74,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(247,5,75,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(248,5,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(249,5,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(250,5,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(251,5,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(252,5,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(253,6,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(254,6,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(255,6,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(256,6,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(257,6,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(258,6,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(259,6,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(260,6,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(261,6,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(262,6,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(263,6,2,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(264,6,3,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(265,6,4,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(266,6,6,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(267,6,7,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(268,6,9,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(269,6,13,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(270,6,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(271,6,12,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(272,6,11,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(273,6,10,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(274,6,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(275,6,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(276,6,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(277,6,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(278,6,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(279,6,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(280,6,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(281,6,45,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(282,6,46,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(283,6,47,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(284,6,74,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(285,6,75,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(286,6,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(287,6,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(288,6,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(289,6,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(290,6,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(291,7,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(292,7,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(293,7,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(294,7,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(295,7,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(296,7,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(297,7,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(298,7,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(299,7,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(300,7,71,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(301,7,18,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(302,7,19,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(303,7,20,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(304,7,21,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(305,7,22,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(306,7,23,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(307,7,24,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(308,7,27,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(309,7,28,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(310,7,29,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(311,7,30,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(312,7,31,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(313,7,32,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(314,7,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(315,7,2,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(316,7,3,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(317,7,4,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(318,7,5,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(319,7,6,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(320,7,7,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(321,7,8,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(322,7,9,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(323,7,13,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(324,7,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(325,7,15,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(326,7,16,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(327,7,12,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(328,7,11,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(329,7,10,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(330,7,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(331,7,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(332,7,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(333,7,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(334,7,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(335,7,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(336,7,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(337,7,45,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(338,7,46,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(339,7,47,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(340,7,74,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(341,7,75,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(342,7,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(343,7,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(344,7,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(345,7,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(346,7,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(347,8,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(348,8,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(349,8,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(350,8,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(351,8,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(352,8,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(353,8,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(354,8,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(355,8,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(356,8,18,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(357,8,19,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(358,8,20,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(359,8,21,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(360,8,22,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(361,8,23,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(362,8,24,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(363,8,27,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(364,8,28,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(365,8,29,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(366,8,30,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(367,8,31,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(368,8,32,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(369,8,1,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(370,8,2,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(371,8,3,1,1,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(372,8,4,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(373,8,6,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(374,8,7,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(375,8,8,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(376,8,9,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(377,8,13,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(378,8,14,1,1,0,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(379,8,16,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(380,8,12,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(381,8,11,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(382,8,10,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(383,8,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(384,8,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(385,8,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(386,8,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(387,8,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(388,8,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(389,8,44,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(390,8,45,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(391,8,46,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(392,8,47,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(393,8,74,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(394,8,75,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(395,8,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(396,8,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(397,8,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(398,8,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(399,8,60,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(400,9,61,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(401,9,62,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(402,9,64,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(403,9,65,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(404,9,66,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(405,9,67,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(406,9,68,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(407,9,69,1,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(408,9,70,0,0,1,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(409,9,34,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(410,9,35,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(411,9,36,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(412,9,37,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(413,9,38,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(414,9,39,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(415,9,74,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(416,9,75,1,1,1,1)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(417,9,73,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(418,9,57,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(419,9,58,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(420,9,59,1,0,0,0)
INSERT [role_permission] ([id],[role_id],[permission_id],[role_view],[role_add],[role_edit],[role_delete])VALUES(421,9,60,1,0,0,0)
SET IDENTITY_INSERT [role_permission] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_stage
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_stage] ON
GO
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(1,1,'Prospecting',0,1,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(2,2,'Qualification',0,2,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(3,3,'Needs Analysis',0,3,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(4,4,'Value Proposition',0,4,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(5,5,'Perception Analysis',0,5,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(6,6,'Proposal/Price Quote',0,6,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(7,7,'Negotiation/Review',0,7,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(8,8,'Closed Won',0,8,1)
INSERT [lookup_stage] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(9,9,'Closed Lost',0,9,1)
SET IDENTITY_INSERT [lookup_stage] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_delivery_options
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_delivery_options] ON
GO
INSERT [lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Email only',0,1,1)
INSERT [lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Fax only',0,2,1)
INSERT [lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Letter only',0,3,1)
INSERT [lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Email then Fax',0,4,1)
INSERT [lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Email then Letter',0,5,1)
INSERT [lookup_delivery_options] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Email, Fax, then Letter',0,6,1)
SET IDENTITY_INSERT [lookup_delivery_options] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_lists_lookup
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_lists_lookup] ON
GO
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(1,1,1,'lookupList','lookup_account_types',10,'Account Types','Feb 17 2004 10:46:17:343AM',1)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(2,1,2,'lookupList','lookup_revenue_types',20,'Revenue Types','Feb 17 2004 10:46:17:357AM',1)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(3,1,3,'contactType','lookup_contact_types',30,'Contact Types','Feb 17 2004 10:46:17:373AM',1)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(4,2,1,'contactType','lookup_contact_types',10,'Types','Feb 17 2004 10:46:17:640AM',2)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(5,2,2,'lookupList','lookup_contactemail_types',20,'Email Types','Feb 17 2004 10:46:17:653AM',2)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(6,2,3,'lookupList','lookup_contactaddress_types',30,'Address Types','Feb 17 2004 10:46:17:670AM',2)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(7,2,4,'lookupList','lookup_contactphone_types',40,'Phone Types','Feb 17 2004 10:46:17:670AM',2)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(8,4,1,'lookupList','lookup_stage',10,'Stage','Feb 17 2004 10:46:17:920AM',4)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(9,4,2,'lookupList','lookup_opportunity_types',20,'Opportunity Types','Feb 17 2004 10:46:17:937AM',4)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(10,8,1,'lookupList','lookup_ticketsource',10,'Ticket Source','Feb 17 2004 10:46:18:403AM',8)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(11,8,2,'lookupList','ticket_severity',20,'Ticket Severity','Feb 17 2004 10:46:18:403AM',8)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(12,8,3,'lookupList','ticket_priority',30,'Ticket Priority','Feb 17 2004 10:46:18:420AM',8)
INSERT [lookup_lists_lookup] ([id],[module_id],[lookup_id],[class_name],[table_name],[level],[description],[entered],[category_id])VALUES(13,15,1111031132,'lookupList','lookup_department',10,'Departments','Feb 17 2004 10:46:19:217AM',1111031131)
SET IDENTITY_INSERT [lookup_lists_lookup] OFF
GO
SET NOCOUNT OFF

-- Insert default report
SET NOCOUNT ON
SET IDENTITY_INSERT [report] ON
GO
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(1,1,NULL,'accounts_type.xml',1,'Accounts by Type','What are my accounts by type?','Feb 17 2004 10:46:17:390AM',0,'Feb 17 2004 10:46:17:390AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(2,1,NULL,'accounts_recent.xml',1,'Accounts by Date Added','What are my recent accounts?','Feb 17 2004 10:46:17:403AM',0,'Feb 17 2004 10:46:17:403AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(3,1,NULL,'accounts_expire.xml',1,'Accounts by Contract End Date','Which accounts are expiring?','Feb 17 2004 10:46:17:420AM',0,'Feb 17 2004 10:46:17:420AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(4,1,NULL,'accounts_current.xml',1,'Current Accounts','What are my current accounts?','Feb 17 2004 10:46:17:437AM',0,'Feb 17 2004 10:46:17:437AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(5,1,NULL,'accounts_contacts.xml',1,'Account Contacts','Who are the contacts in each account?','Feb 17 2004 10:46:17:437AM',0,'Feb 17 2004 10:46:17:437AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(6,1,NULL,'folder_accounts.xml',1,'Account Folders','What is the folder data for each account?','Feb 17 2004 10:46:17:450AM',0,'Feb 17 2004 10:46:17:450AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(7,2,NULL,'contacts_user.xml',1,'Contacts','Who are my contacts?','Feb 17 2004 10:46:17:687AM',0,'Feb 17 2004 10:46:17:687AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(8,4,NULL,'opportunity_pipeline.xml',1,'Opportunities by Stage','What are my upcoming opportunities by stage?','Feb 17 2004 10:46:17:937AM',0,'Feb 17 2004 10:46:17:937AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(9,4,NULL,'opportunity_account.xml',1,'Opportunities by Account','What are all the accounts associated with my opportunities?','Feb 17 2004 10:46:17:950AM',0,'Feb 17 2004 10:46:17:950AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(10,4,NULL,'opportunity_owner.xml',1,'Opportunities by Owner','What are all the opportunities based on ownership?','Feb 17 2004 10:46:17:950AM',0,'Feb 17 2004 10:46:17:950AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(11,4,NULL,'opportunity_contact.xml',1,'Opportunity Contacts','Who are the contacts of my opportunities?','Feb 17 2004 10:46:17:967AM',0,'Feb 17 2004 10:46:17:967AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(12,6,NULL,'campaign.xml',1,'Campaigns by date','What are my active campaigns?','Feb 17 2004 10:46:18:187AM',0,'Feb 17 2004 10:46:18:187AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(13,8,NULL,'tickets_department.xml',1,'Tickets by Department','What tickets are there in each department?','Feb 17 2004 10:46:18:420AM',0,'Feb 17 2004 10:46:18:420AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(14,8,NULL,'ticket_summary_date.xml',1,'Ticket counts by Department','How man tickets are there in the system on a particular date?','Feb 17 2004 10:46:18:437AM',0,'Feb 17 2004 10:46:18:437AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(15,8,NULL,'ticket_summary_range.xml',1,'Ticket activity by Department','How many tickets exist within a date range?','Feb 17 2004 10:46:18:437AM',0,'Feb 17 2004 10:46:18:437AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(16,9,NULL,'users.xml',1,'System Users','Who are all the users of the system?','Feb 17 2004 10:46:18:653AM',0,'Feb 17 2004 10:46:18:653AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(17,12,NULL,'task_date.xml',1,'Task list by due date','What are the tasks due withing a date range?','Feb 17 2004 10:46:19:047AM',0,'Feb 17 2004 10:46:19:047AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(18,12,NULL,'task_nodate.xml',1,'Task list','What are all the tasks in the system?','Feb 17 2004 10:46:19:060AM',0,'Feb 17 2004 10:46:19:060AM',0,1,0)
INSERT [report] ([report_id],[category_id],[permission_id],[filename],[type],[title],[description],[entered],[enteredby],[modified],[modifiedby],[enabled],[custom])VALUES(19,15,NULL,'employees.xml',1,'Employees','Who are the employees in my organization?','Feb 17 2004 10:46:19:233AM',0,'Feb 17 2004 10:46:19:233AM',0,1,0)
SET IDENTITY_INSERT [report] OFF
GO
SET NOCOUNT OFF

-- Insert default database_version
SET NOCOUNT ON
SET IDENTITY_INSERT [database_version] ON
GO
INSERT [database_version] ([version_id],[script_filename],[script_version],[entered])VALUES(1,'mssql.sql','2004-02-17','Feb 17 2004 10:46:43:983AM')
SET IDENTITY_INSERT [database_version] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_call_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_call_types] ON
GO
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Incoming Call',1,10,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Outgoing Call',0,20,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Proactive Call',0,30,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Inhouse Meeting',0,40,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Outside Appointment',0,50,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(6,'Proactive Meeting',0,60,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(7,'Email Servicing',0,70,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(8,'Email Proactive',0,80,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(9,'Fax Servicing',0,90,1)
INSERT [lookup_call_types] ([code],[description],[default_item],[level],[enabled])VALUES(10,'Fax Proactive',0,100,1)
SET IDENTITY_INSERT [lookup_call_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_opportunity_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_opportunity_types] ON
GO
INSERT [lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(1,NULL,'Annuity',0,0,1)
INSERT [lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(2,NULL,'Consultation',0,1,1)
INSERT [lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(3,NULL,'Development',0,2,1)
INSERT [lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(4,NULL,'Maintenance',0,3,1)
INSERT [lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(5,NULL,'Product Sales',0,4,1)
INSERT [lookup_opportunity_types] ([code],[order_id],[description],[default_item],[level],[enabled])VALUES(6,NULL,'Services',0,5,1)
SET IDENTITY_INSERT [lookup_opportunity_types] OFF
GO
SET NOCOUNT OFF

-- Insert default ticket_level
SET NOCOUNT ON
SET IDENTITY_INSERT [ticket_level] ON
GO
INSERT [ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Entry level',0,0,1)
INSERT [ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(2,'First level',0,1,1)
INSERT [ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Second level',0,2,1)
INSERT [ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Third level',0,3,1)
INSERT [ticket_level] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Top level',0,4,1)
SET IDENTITY_INSERT [ticket_level] OFF
GO
SET NOCOUNT OFF

-- Insert default ticket_severity
SET NOCOUNT ON
SET IDENTITY_INSERT [ticket_severity] ON
GO
INSERT [ticket_severity] ([code],[description],[style],[default_item],[level],[enabled])VALUES(1,'Normal','background-color:lightgreen;color:black;',1,0,1)
INSERT [ticket_severity] ([code],[description],[style],[default_item],[level],[enabled])VALUES(2,'Important','background-color:yellow;color:black;',0,1,1)
INSERT [ticket_severity] ([code],[description],[style],[default_item],[level],[enabled])VALUES(3,'Critical','background-color:red;color:black;font-weight:bold;',0,2,1)
SET IDENTITY_INSERT [ticket_severity] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_ticketsource
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_ticketsource] ON
GO
INSERT [lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Phone',0,1,1)
INSERT [lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Email',0,2,1)
INSERT [lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Web',0,3,1)
INSERT [lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Letter',0,4,1)
INSERT [lookup_ticketsource] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Other',0,5,1)
SET IDENTITY_INSERT [lookup_ticketsource] OFF
GO
SET NOCOUNT OFF

-- Insert default ticket_priority
SET NOCOUNT ON
SET IDENTITY_INSERT [ticket_priority] ON
GO
INSERT [ticket_priority] ([code],[description],[style],[default_item],[level],[enabled])VALUES(1,'As Scheduled','background-color:lightgreen;color:black;',1,0,1)
INSERT [ticket_priority] ([code],[description],[style],[default_item],[level],[enabled])VALUES(2,'Urgent','background-color:yellow;color:black;',0,1,1)
INSERT [ticket_priority] ([code],[description],[style],[default_item],[level],[enabled])VALUES(3,'Critical','background-color:red;color:black;font-weight:bold;',0,2,1)
SET IDENTITY_INSERT [ticket_priority] OFF
GO
SET NOCOUNT OFF

-- Insert default ticket_category
SET NOCOUNT ON
SET IDENTITY_INSERT [ticket_category] ON
GO
INSERT [ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(1,0,0,'Sales',' ',0,1,1)
INSERT [ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(2,0,0,'Billing',' ',0,2,1)
INSERT [ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(3,0,0,'Technical',' ',0,3,1)
INSERT [ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(4,0,0,'Order',' ',0,4,1)
INSERT [ticket_category] ([id],[cat_level],[parent_cat_code],[description],[full_description],[default_item],[level],[enabled])VALUES(5,0,0,'Other',' ',0,5,1)
SET IDENTITY_INSERT [ticket_category] OFF
GO
SET NOCOUNT OFF

-- Insert default module_field_categorylink
SET NOCOUNT ON
SET IDENTITY_INSERT [module_field_categorylink] ON
GO
INSERT [module_field_categorylink] ([id],[module_id],[category_id],[level],[description],[entered])VALUES(1,1,1,10,'Accounts','Feb 17 2004 10:46:17:297AM')
INSERT [module_field_categorylink] ([id],[module_id],[category_id],[level],[description],[entered])VALUES(2,2,2,10,'Contacts','Feb 17 2004 10:46:17:640AM')
INSERT [module_field_categorylink] ([id],[module_id],[category_id],[level],[description],[entered])VALUES(3,8,11072003,10,'Tickets','Feb 17 2004 10:46:18:373AM')
SET IDENTITY_INSERT [module_field_categorylink] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_project_activity
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_activity] ON
GO
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(1,'Project Initialization',0,1,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(2,'Analysis/Software Requirements',0,2,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(3,'Functional Specifications',0,3,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(4,'Prototype',0,4,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(5,'System Development',0,5,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(6,'Testing',0,6,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(7,'Training',0,7,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(8,'Documentation',0,8,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(9,'Deployment',0,9,1,0,0)
INSERT [lookup_project_activity] ([code],[description],[default_item],[level],[enabled],[group_id],[template_id])VALUES(10,'Post Implementation Review',0,10,1,0,0)
SET IDENTITY_INSERT [lookup_project_activity] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_project_priority
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_priority] ON
GO
INSERT [lookup_project_priority] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(1,'Low',0,1,1,0,NULL,10)
INSERT [lookup_project_priority] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(2,'Normal',1,2,1,0,NULL,20)
INSERT [lookup_project_priority] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(3,'High',0,3,1,0,NULL,30)
SET IDENTITY_INSERT [lookup_project_priority] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_project_issues
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_issues] ON
GO
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(1,'Status Update',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(2,'Bug Report',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(3,'Network',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(4,'Hardware',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(5,'Permissions',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(6,'User',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(7,'Documentation',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(8,'Feature',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(9,'Procedure',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(10,'Training',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(11,'3rd-Party Software',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(12,'Database',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(13,'Information',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(14,'Testing',0,0,1,0)
INSERT [lookup_project_issues] ([code],[description],[default_item],[level],[enabled],[group_id])VALUES(15,'Security',0,0,1,0)
SET IDENTITY_INSERT [lookup_project_issues] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_project_status
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_status] ON
GO
INSERT [lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(1,'Not Started',0,1,1,0,'box.gif',1)
INSERT [lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(2,'In Progress',0,2,1,0,'box.gif',2)
INSERT [lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(3,'On Hold',0,5,1,0,'box-hold.gif',5)
INSERT [lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(4,'Waiting on Reqs',0,6,1,0,'box-hold.gif',5)
INSERT [lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(5,'Complete',0,3,1,0,'box-checked.gif',3)
INSERT [lookup_project_status] ([code],[description],[default_item],[level],[enabled],[group_id],[graphic],[type])VALUES(6,'Closed',0,4,1,0,'box-checked.gif',4)
SET IDENTITY_INSERT [lookup_project_status] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_project_loe
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_project_loe] ON
GO
INSERT [lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(1,'Minute(s)',60,0,1,1,0)
INSERT [lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(2,'Hour(s)',3600,1,1,1,0)
INSERT [lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(3,'Day(s)',86400,0,1,1,0)
INSERT [lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(4,'Week(s)',604800,0,1,1,0)
INSERT [lookup_project_loe] ([code],[description],[base_value],[default_item],[level],[enabled],[group_id])VALUES(5,'Month(s)',18144000,0,1,1,0)
SET IDENTITY_INSERT [lookup_project_loe] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_survey_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_survey_types] ON
GO
INSERT [lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Open-Ended',0,0,1)
INSERT [lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Quantitative (no comments)',0,0,1)
INSERT [lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Quantitative (with comments)',0,0,1)
INSERT [lookup_survey_types] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Item List',0,0,1)
SET IDENTITY_INSERT [lookup_survey_types] OFF
GO
SET NOCOUNT OFF

-- Insert default field_types
SET NOCOUNT ON
SET IDENTITY_INSERT [field_types] ON
GO
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(1,0,'string','=','is',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(2,0,'string','!=','is not',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(3,0,'string','= | or field_name is null','is empty',0)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(4,0,'string','!= | and field_name is not null','is not empty',0)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(5,0,'string','like %search_value%','contains',0)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(6,0,'string','not like %search_value%','does not contain',0)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(7,1,'date','<','before',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(8,1,'date','>','after',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(9,1,'date','between','between',0)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(10,1,'date','<=','on or before',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(11,1,'date','>=','on or after',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(12,2,'number','>','greater than',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(13,2,'number','<','less than',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(14,2,'number','=','equals',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(15,2,'number','>=','greater than or equal to',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(16,2,'number','<=','less than or equal to',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(17,2,'number','is not null','exist',1)
INSERT [field_types] ([id],[data_typeid],[data_type],[operator],[display_text],[enabled])VALUES(18,2,'number','is null','does not exist',1)
SET IDENTITY_INSERT [field_types] OFF
GO
SET NOCOUNT OFF

-- Insert default search_fields
SET NOCOUNT ON
SET IDENTITY_INSERT [search_fields] ON
GO
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(1,'company','Company Name',1,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(2,'namefirst','Contact First Name',1,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(3,'namelast','Contact Last Name',1,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(4,'entered','Entered Date',1,1,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(5,'zip','Zip Code',1,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(6,'areacode','Area Code',1,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(7,'city','City',1,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(8,'typeId','Contact Type',1,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(9,'contactId','Contact ID',0,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(10,'title','Contact Title',0,0,NULL,NULL,1)
INSERT [search_fields] ([id],[field],[description],[searchable],[field_typeid],[table_name],[object_class],[enabled])VALUES(11,'accountTypeId','Account Type',1,0,NULL,NULL,1)
SET IDENTITY_INSERT [search_fields] OFF
GO
SET NOCOUNT OFF

-- Insert default help_module
SET NOCOUNT ON
SET IDENTITY_INSERT [help_module] ON
GO
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(1,12,'This is the "Home Page". The top set of tabs shows the individual modules that you can access. The second row are the functions available to you in this module.
If you are looking for a particular module or function you have seen during training or on someone else''s machine, and it''s NOT visible, then it probably means that you do not have permission for that module or function.

Permissions within the system are assigned to Roles and then Users are assigned to Roles by the System Administrator.
','The Home Page has several main features.

Welcome: This is the welcome page. Here you will see a calendar of all of your upcoming alerts in the system, as well as for users that report to you.

Mailbox: The message system is designed to support INTERNAL business messaging needs and to prepare OUTGOING emails to addresses who are already in the system. Messaging is NOT a normal email replacement. It will not send email to addresses assigned "on-the-fly" and it will not receive OUTSIDE email.

Tasks: Tasks allows you to create and assign tasks. Tasks created can be assigned to the creator of the task or an employee working in the system. This page lists the tasks present along with their priorities, due dates and age.

Action Lists: Action Lists allow you to create a list of contacts that are related in some way. For each of the contacts in a list, you can add calls, opportunities, tickets, tasks or send a message, which would correspondingly show up in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket module.

Re-assignments: You can reassign data from one employee to another employee as long as each employee reports to you. The data can be of different types related to accounts, contacts, opportunities, activities, tickets etc, which the newly reassigned employee could view.

Settings: This feature allows you to modify the information about yourself and your location, and also change your password to the system.')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(2,2,'The purpose of this module is for the users to view contacts and add new contacts. You can also search for contacts as well as export contact data.
','The Contacts module has three main features

Add: A new contact can be added into the system. The contact can be a general contact, or one that is associated with an account. All the details about the contact like the email address, phone numbers, address and some additional details can be added.

Search: Use this page to search for contacts in the system based on different filters.

Export: Contact data can be exported and displayed in different formats, and can be filtered in different ways. The Export page also lets you view the data, download it, and show the number of times the exported data has been downloaded.')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(3,4,'Pipeline helps in creating opportunities or leads in the company. Each opportunity helps to follow-up on a lead, which might eventually turn into a client. Here you can create an opportunity, search for an existing opportunity in the system, or export the data in different formats. The dashboard displays a progress chart in different views for all the employees working under the hierarchy of the owner of the opportunity.
','This Pipeline System has four main features:

Dashboard: Gives you a quick, visual overview of opportunities.

Add: This page lets you add an opportunity into the system. Here a new opportunity can be added by giving the description of the opportunity. You can add a component that is associated with the opportunity. Each component can be assigned to an employee and the type of the component can be selected. For each component the probability of closing the component, the date estimated, the best guess for closing the deal and the duration for that component can be entered.

Search: You can search for an opportunity already existing in the system based on different filters.

Export: The data can be filtered, exported and displayed in different formats.')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(4,1,'You are looking at the Accounts module homepage, which has a dashboard view of all your accounts. This view is based on a date range selected from the calendar; by default it shows the schedule for the next seven days. You can optionally view the calendar of those below you in your hierarchy. The scheduled actions of each user can also be viewed. In the Accounts Module, new accounts can be added, existing accounts can be searched based on different filters, revenue for each account can be created/maintained and finally, data can be exported to different formats.
','The Accounts module has five main features.

Dashboard: Displays a dashboard view account contract expirations.

Add: Allows you to add a new account

Search: This page provides a search feature for the accounts present in the system

Revenue: Graphically visualizes revenue if the historical data is present in the system. All the accounts with revenue are shown along with a list of employees working under you under the progress chart. You can add accounts, search for the existing ones in the system based on different filters and export the data to different formats

Export: Data can be exported and displayed in different formats. The exported data can be filtered in different ways. You can view the data, download it, and see the number of times an exported report has been downloaded')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(5,6,'Communications is a "Campaign Manager" Module where you can manage complex email. fax, or mail communications with your customers. Communications allows you to create and choose Groups, Messages, and Schedules to define communications scenarios from the simple to the very complex. Groups can range from a single contact chosen from a pick list to the result of a complex query of the Account/Contact database. Messages can be anything from a single-line email to a rich, multimedia product catalog to an interactive survey.','The Communication module has six main features.

Dashboard: Track and analyze campaigns that have been activated and executed.
Messages can be sent out by any combination of email, fax, or mail merge. The Dashboard shows an overview of sent messages and allows you to drill down and view recipients and any survey results.

Add: This page lets you add a new campaign.

Campaign List: This page lets you add a new campaign into the system.

Groups: Each campaign needs at least one group to send a message to. Use criteria to filter the contacts you need to reach and use them over and over again. As new contacts meet the criteria, they will be included in future campaigns. This page lists the group names. It shows the groups created by you or everybody i.e. all the groups. 

Messages: Compose a message to reach your audience. Each campaign requires a message that will be sent to selected groups. Write the message once, then use it in any number of future campaigns. Modified messages will only affect future campaigns. Displays the list of messages, with the description and other details.

Attachments: Customize and configure your Campaigns with attachments. Attachments can include interactive items, like surveys, or provide additional materials like files.')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(6,8,'You are looking at the Help Desk module home page. The dashboard shows the most recent tickets that have been assigned to you, as well as tickets that are in your department, and tickets that have been created by you. You can add new tickets, search for existing tickets based on different filters and export ticket data.
','The Help Desk module has four main features.

View: Lists all the tickets assigned to you and the tickets assigned in your department. Details such as the ticket number, priority, age, i.e. how old the ticket is, the company and the tickets assignment are displayed.

Add: You can add a new ticket here.

Search: Form used for searching the tickets that already exist in the system based on different filters and parameters.

Export: This page shows exported data. The data can be exported to different formats. The exported data can be viewed with its subject, the size of the exported data file, when it was created and by whom. It also shows the number of times that particular exported file was downloaded. The exported data, created by you or all the employees can be viewed in two different views. A new data file can also be exported.')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(7,15,'You are looking at the Employee module home page. This page displays the details of all the employees present in the system.','The main feature of the employee module is the view.

View: This page displays the details of each employee, which can be viewed, modified or deleted. Each employee record contains details such as the name of the employee, their department, title and phone number. This also lets you add a new employee into the system.')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(8,14,'You are looking at the Reports module home page. This page displays a list of generated reports that are ready to be downloaded. It also displays a list of reports that are scheduled to be processed by server. You can add new reports too.
','The Reports module has two main features.

Queue: Queue shows the list of reports that are scheduled to be processed by the server.

Add: This shows the different modules present and displays the list of corresponding reports present in each module, allowing you to add a report to the schedule queue.')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(9,9,'The admin module lets the user review the system usage, configure modules, and configure the global/system parameters.','This Admin System has five main features.

Users: This section allows the administrator to view and add users and manage user hierarchies. The users are typically employees in your company who interact with your clients or customers, but can be outsides that you have granted permissions on the system.

Roles: This page lists the different roles you have defined in the system, their role description and the number of people present in the system who carry out that role. New roles can be added into the system at any time.

Modules: This page lets you configure various parameters in the modules that meet the needs of your organization, including configuration of lookup lists and custom fields. There are four types of modules. Each module has different number of configure options. The changes in the module affect all the users.

System: You can configure the system for the session timeout and set the time limit for the time out.

Usage: Current System Usage and Billing Usage Information are displayed.
')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(10,3,'Auto Guide Brief','Auto Guide Detail')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(11,10,'Help Brief','Help Detail')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(12,5,'Demo Brief','Demo Detail')
INSERT [help_module] ([module_id],[category_id],[module_brief_description],[module_detail_description])VALUES(13,11,'System Brief','System Detail')
SET IDENTITY_INSERT [help_module] OFF
GO
SET NOCOUNT OFF

-- Insert default help_contents
SET NOCOUNT ON
SET IDENTITY_INSERT [help_contents] ON
GO
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,12,1,'MyCFS.do','Home',NULL,'Overview','You are looking at Home Page view, which has a dashboard view of all your assigned tasks, tickets, assignments, calls you have to make, and accounts that need attention. This view is based on a time selected from the calendar; by default it shows the schedule for the next seven days. You can optionally view the calendar of those below you in your hierarchy.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:310AM',0,'Feb 17 2004 10:46:28:310AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,12,1,'MyCFSInbox.do','Inbox',NULL,'Mailbox','The messaging feature is designed to support INTERNAL business messaging needs and to prepare OUTGOING emails to addresses who are already in the system. Messaging is NOT a normal email replacement. It will not send email to address assigned "on-the-fly" and it will not receive OUTSIDE email.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:450AM',0,'Feb 17 2004 10:46:28:450AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(3,12,1,'MyCFSInbox.do','CFSNoteDetails',NULL,'Message Details','This page shows the message details, shows who sent the mail, when it was received and also the text in the mail box.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:513AM',0,'Feb 17 2004 10:46:28:513AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(4,12,1,'MyCFSInbox.do','NewMessage',NULL,'New Message','Sending mail to other users of the system',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:530AM',0,'Feb 17 2004 10:46:28:530AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(5,12,1,'MyCFSInbox.do','ReplyToMessage',NULL,'Reply Message','This pages lets you reply to email. You can also select the list of recipients for your email by clicking the Add Recipients link.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:543AM',0,'Feb 17 2004 10:46:28:543AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(6,12,1,'MyCFSInbox.do','SendMessage',NULL,NULL,'This page shows the list of recipients for whom your email has been sent to',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:607AM',0,'Feb 17 2004 10:46:28:607AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(7,12,1,'MyCFSInbox.do','ForwardMessage',NULL,'Forward message','Each message can be forwarded to any number of recipients',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:623AM',0,'Feb 17 2004 10:46:28:623AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(8,12,1,'MyTasks.do',NULL,NULL,'Tasks','Tasks created can be assigned to the owner/creator of the task or an employee working in the system. This page lists the tasks present along with their priorities, their due dates and age.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:653AM',0,'Feb 17 2004 10:46:28:653AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(9,12,1,'MyTasks.do','New',NULL,'Advanced Task','Allows an advanced task to be created',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:700AM',0,'Feb 17 2004 10:46:28:700AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(10,12,1,'MyTasksForward.do','ForwardMessage',NULL,'Forwarding a Task','A task can be forwarded to any of the recipients. Recipients can either be users of the system or any of the contacts stored in the system. Checking the options fields check box indicates that if the recipient is a user of the system, then a copy of the task is also send to the recipient''s mailbox.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:780AM',0,'Feb 17 2004 10:46:28:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(11,12,1,'MyTasks.do','Modify',NULL,'Modify task','Allows you to modify any infomation about a task',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:887AM',0,'Feb 17 2004 10:46:28:887AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(12,12,1,'MyActionLists.do','List',NULL,'Action Lists','Action Lists allow you to create new Action Lists and assign contacts to the Action Lists created. For each of the contacts in a list, you can add a call, opportunity, ticket, task or send a message, which would correspondingly show up in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket module.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:933AM',0,'Feb 17 2004 10:46:28:933AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(13,12,1,'MyActionContacts.do','List',NULL,'Action Contacts','This page will list out all the contacts present for the particular contact list. This also shows the status of the call, opportunity, ticket, task, or the message associated with the contact. This also shows when the contact information was last updated.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:28:967AM',0,'Feb 17 2004 10:46:28:967AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(14,12,1,'MyActionLists.do','Add',NULL,'Add Action List','Allows you to add an action list. Basically, you describe the group of contacts you will add, then visually design a query to generate the group of contacts.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:013AM',0,'Feb 17 2004 10:46:29:013AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(15,12,1,'MyActionLists.do','Modify',NULL,'Modify Action','The Action Lists details, like the description and status of the Action Lists, can be modified.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:137AM',0,'Feb 17 2004 10:46:29:137AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(16,12,1,'Reassignments.do','Reassign',NULL,'Re-assignments','A user can reassign data from one employee to another employee working under him. The data can be of different types related to accounts, contacts opportunities, activities, tickets etc, which the newly reassigned employee could view in his schedule.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:153AM',0,'Feb 17 2004 10:46:29:153AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(17,12,1,'MyCFS.do','MyProfile',NULL,'My Settings','This is the personal settings page, where you can modify the information about yourself, your location and also change your password to the system.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:200AM',0,'Feb 17 2004 10:46:29:200AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(18,12,1,'MyCFSProfile.do','MyCFSProfile',NULL,'Personal Information','This page lets you update/add your personal information.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:230AM',0,'Feb 17 2004 10:46:29:230AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(19,12,1,'MyCFSSettings.do','MyCFSSettings',NULL,'Location Settings','You can change your location settings',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:263AM',0,'Feb 17 2004 10:46:29:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(20,12,1,'MyCFSPassword.do','MyCFSPassword',NULL,'Update password','Your password to the system can be changed',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:280AM',0,'Feb 17 2004 10:46:29:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(21,12,1,'MyTasks.do','ListTasks',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:310AM',0,'Feb 17 2004 10:46:29:310AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(22,12,1,'MyTasks.do',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:357AM',0,'Feb 17 2004 10:46:29:357AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(23,12,1,'MyCFS.do','Home',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:373AM',0,'Feb 17 2004 10:46:29:373AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(24,12,1,'MyTasksForward.do','SendMessage',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:387AM',0,'Feb 17 2004 10:46:29:387AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(25,12,1,'MyCFSProfile.do','UpdateProfile',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:387AM',0,'Feb 17 2004 10:46:29:387AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(26,12,1,'MyCFSInbox.do','CFSNoteTrash',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:480AM',0,'Feb 17 2004 10:46:29:480AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(27,12,1,'MyActionContacts.do','Update',NULL,NULL,'No introduction available',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:497AM',0,'Feb 17 2004 10:46:29:497AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(28,12,1,'MyActionContacts.do','Prepare',NULL,NULL,'You can add the new Action List and also select the contacts to be present in the Action List.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:513AM',0,'Feb 17 2004 10:46:29:513AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(29,12,1,'MyTasks.do','ListTasks',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:543AM',0,'Feb 17 2004 10:46:29:543AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(30,12,1,'MyCFSInbox.do','Inbox',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:543AM',0,'Feb 17 2004 10:46:29:543AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(31,12,1,'MyActionContacts.do','Modify',NULL,'Modify Action List','Allows you to manually add or remove contacts to or from an existing Action List.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:560AM',0,'Feb 17 2004 10:46:29:560AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(32,12,1,'Reassignments.do','DoReassign',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:590AM',0,'Feb 17 2004 10:46:29:590AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(33,12,1,'TaskForm.do','Prepare',NULL,NULL,'Addition of a new Advanced task that can be assigned to the owner or any other employee working  under him.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:590AM',0,'Feb 17 2004 10:46:29:590AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(34,2,2,'ExternalContacts.do','Prepare',NULL,'Add a Contact','A new contact can be added to the system. The contact can be a general contact, or one that is associated with an account. All the details about the contact such as the email address, phone numbers, address and some additional details can be added.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:653AM',0,'Feb 17 2004 10:46:29:653AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(35,2,2,'ExternalContacts.do','SearchContactsForm',NULL,'Search Contacts','Use this page to search for contacts in the system based on different filters',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:730AM',0,'Feb 17 2004 10:46:29:730AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(36,2,2,'ExternalContacts.do','Reports',NULL,'Export Data','Contact data can be exported and displayed in different formats, and can be filtered in different ways. The Export page also lets you view the data, download it, and shows the number of times the exported data has been downloaded.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:747AM',0,'Feb 17 2004 10:46:29:747AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(37,2,2,'ExternalContacts.do','GenerateForm',NULL,'Exporting data','Use this page to generate a Contacts export report.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:780AM',0,'Feb 17 2004 10:46:29:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(38,2,2,'ExternalContacts.do','ModifyContact',NULL,'Modify Contact','The details about the contact can be modified here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:840AM',0,'Feb 17 2004 10:46:29:840AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(39,2,2,'ExternalContacts.do',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:857AM',0,'Feb 17 2004 10:46:29:857AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(40,2,2,'ExternalContacts.do','Save',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:873AM',0,'Feb 17 2004 10:46:29:873AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(41,2,2,'ExternalContactsCalls.do','View',NULL,'Call Details','The calls related to the contact are listed here along with the other details such as the length of the call and the date the call was made. You can also add a call.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:887AM',0,'Feb 17 2004 10:46:29:887AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(42,2,2,'ExternalContactsCalls.do','Add',NULL,'Adding a Call','A new call can be added which is associated with the contact. The type of call can be selected using the drop down list present.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:920AM',0,'Feb 17 2004 10:46:29:920AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(43,2,2,'ExternalContactsCallsForward.do','ForwardMessage',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:933AM',0,'Feb 17 2004 10:46:29:933AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(44,2,2,'ExternalContactsCallsForward.do','SendMessage',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:950AM',0,'Feb 17 2004 10:46:29:950AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(45,2,2,'ExternalContactsOpps.do','ViewOpps',NULL,'Opportunity Details','All the opportunities associated with the contact are shown here, with its best possible total and the when the opportunity was last modified. You can filter the different types of opportunities using the drop down.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:29:950AM',0,'Feb 17 2004 10:46:29:950AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(46,2,2,'ExternalContactsCalls.do','Details',NULL,'Call Details','Calls associated with the contacts are displayed. The call details are shown with the length of the call, associated notes, alert description, alert date etc.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:090AM',0,'Feb 17 2004 10:46:30:090AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(47,2,2,'ExternalContactsOppComponents.do','Prepare',NULL,'Add a component','A component can be added to an opportunity and associated to any employee present in the system. The component type can also be selected.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:107AM',0,'Feb 17 2004 10:46:30:107AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(48,2,2,'ExternalContactsOpps.do','DetailsOpp',NULL,'Opportunity Details','You can view all the details about the components here, such as the status, the guess amount and the current stage. A new component can also be added to an existing opportunity.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:137AM',0,'Feb 17 2004 10:46:30:137AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(49,2,2,'ExternalContactsOppComponents.do','DetailsComponent',NULL,'Component Details','This page shows the details about the opportunity such as the probability of closing the opportunity, the current stage of the opportunity, etc.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:183AM',0,'Feb 17 2004 10:46:30:183AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(50,2,2,'ExternalContactsCalls.do','Modify',NULL,'Modifying call details','You can modify all the details of the calls.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:200AM',0,'Feb 17 2004 10:46:30:200AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(51,2,2,'ExternalContacts.do','InsertFields',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:217AM',0,'Feb 17 2004 10:46:30:217AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(52,2,2,'ExternalContacts.do','UpdateFields',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:230AM',0,'Feb 17 2004 10:46:30:230AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(53,2,2,'ExternalContacts.do','ListContacts',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:247AM',0,'Feb 17 2004 10:46:30:247AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(54,2,2,'ExternalContacts.do','Clone',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:247AM',0,'Feb 17 2004 10:46:30:247AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(55,2,2,'ExternalContacts.do','AddFolderRecord',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:263AM',0,'Feb 17 2004 10:46:30:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(56,2,2,'ExternalContactsOpps.do','Save',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:263AM',0,'Feb 17 2004 10:46:30:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(57,2,2,'ExternalContacts.do','SearchContacts',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:280AM',0,'Feb 17 2004 10:46:30:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(58,2,2,'ExternalContacts.do','MessageDetails',NULL,NULL,'The selected message is displayed with the message text and attachments, if any.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:293AM',0,'Feb 17 2004 10:46:30:293AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(59,2,2,'ExternalContactsOppComponents.do','SaveComponent',NULL,NULL,'This page shows the details of an opportunity, such as the probability of closing the opportunity, the current stage of the opportunity, etc.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:310AM',0,'Feb 17 2004 10:46:30:310AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(60,2,2,'ExternalContacts.do','SearchContacts',NULL,'List of Contacts','This page shows the list of contacts in the system. The name of the contact along with the company name and phone numbers are shown. If the name of the contact is an account, it''s shown right next to it. You can also add a contact.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:327AM',0,'Feb 17 2004 10:46:30:327AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(61,2,2,'ExternalContacts.do','ExportReport',NULL,'Overview','Data can be filtered, exported and displayed in different formats. This also shows the number of times an exported data has been downloaded.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:357AM',0,'Feb 17 2004 10:46:30:357AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(62,2,2,'ExternalContacts.do','ContactDetails',NULL,'Contact Details','The details about the contact are displayed here along with the record information containing the owner, the employee who entered the details and finally the person who last modified these details.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:403AM',0,'Feb 17 2004 10:46:30:403AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(63,2,2,'ExternalContacts.do','ViewMessages',NULL,'Message Details','This page lists all the messages associated with the contact, showing the name of the message, its date and its status.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:420AM',0,'Feb 17 2004 10:46:30:420AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(64,2,2,'ExternalContactsCallsForward.do','ForwardCall',NULL,'Forwarding a call','The details of the calls that are associated with a contact can be forwarded to different employees present in the system.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:450AM',0,'Feb 17 2004 10:46:30:450AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(65,2,2,'ExternalContactsCallsForward.do','SendCall',NULL,'List of recipients','This page shows the list of recipients to whom the call details that are associated with a contact have been forwarded.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:467AM',0,'Feb 17 2004 10:46:30:467AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(66,2,2,'ExternalContactsOppComponents.do','ModifyComponent',NULL,'Modifying the component details','The details of the component associated with an opportunity can be modified.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:480AM',0,'Feb 17 2004 10:46:30:480AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(67,2,2,'ExternalContactsOpps.do','ModifyOpp',NULL,'Modify the opportunity','The description of the opportunity can be renamed / updated.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:497AM',0,'Feb 17 2004 10:46:30:497AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(68,2,2,'ExternalContacts.do','Fields',NULL,'List of Folder Records','Each contact can have several folders, and each folder further can have multiple records. You can add a record to a folder. Each record present in the folder displays the record name, when it was entered, who modified this record last and when.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:590AM',0,'Feb 17 2004 10:46:30:590AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(69,2,2,'ExternalContacts.do','ModifyFields',NULL,'Modify Folder Record','Here you can modify the details of the folder record.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:653AM',0,'Feb 17 2004 10:46:30:653AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(70,2,2,'ExternalContactsOpps.do','UpdateOpp',NULL,'Opportunity Details','All the opportunities associated with the contact are shown here, with its best possible total and the when the opportunity was last modified. You can filter the different types of opportunities that can be selected using the drop down and display them.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:670AM',0,'Feb 17 2004 10:46:30:670AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(71,2,2,'ExternalContacts.do',NULL,NULL,'Overview','If a contact already exists in the system, you can search for that contact by name, company, title, contact type or source, by typing the search term in the appropriate field, and clicking the Search button.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:700AM',0,'Feb 17 2004 10:46:30:700AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(72,2,2,'ContactsList.do','ContactList',NULL,NULL,'Enables you to select contacts from a list and then add them to the Action List. It shows the name of the contact along with his email and type of contact. 

',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:730AM',0,'Feb 17 2004 10:46:30:730AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(73,2,2,'Contacts.do','Prepare',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:763AM',0,'Feb 17 2004 10:46:30:763AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(74,2,2,'Contacts.do','Details',NULL,'Contact detail page','The contact details associated with the account are displayed here. The details such as the account number, email address, phone number and the addresses are shown.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:780AM',0,'Feb 17 2004 10:46:30:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(75,2,2,'Contacts.do','Modify',NULL,'Modify contact','The details of a contact can be modified or updated here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:793AM',0,'Feb 17 2004 10:46:30:793AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(76,2,2,'Contacts.do','ViewMessages',NULL,'List of Messages','The list of messages related to the contacts.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:840AM',0,'Feb 17 2004 10:46:30:840AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(77,2,2,'ContactForm.do','Prepare',NULL,NULL,'Adding/Modifying a new contact',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:857AM',0,'Feb 17 2004 10:46:30:857AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(78,4,3,'Leads.do','Dashboard',NULL,'Overview','The progress chart is displayed in different views for all the employees working under the owner or creator of the opportunity. The opportunities are shown, with their names and the probable gross revenue associated with that opportunity. Finally the list of employees reporting to a particular employee/supervisor is also shown below the progress chart',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:920AM',0,'Feb 17 2004 10:46:30:920AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(79,4,3,'Leads.do','Prepare',NULL,'Add a Opportunity','This page lets you add a opportunity into the system.

Here a new opportunity can be added by giving a description of the opportunity, then adding a component that is associated with the opportunity. An opportunity can have one or more components. Each component can be assigned to a different employee and the type of the component can be selected. For each component the probability of closing the component, the date estimated, the best guess for closing the deal and the duration for that component are required. 
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:933AM',0,'Feb 17 2004 10:46:30:933AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(80,4,3,'Leads.do','SearchForm',NULL,'Search Opportunities','You can search for an existing opportunity based on different filters.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:30:997AM',0,'Feb 17 2004 10:46:30:997AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(81,4,3,'LeadsReports.do','Default',NULL,'Export Data','Pipeline data can be exported, filtered, and displayed in different formats. You can also view the data online in html format, and see number of times the exported data has been downloaded.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:013AM',0,'Feb 17 2004 10:46:31:013AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(82,4,3,'Leads.do','SearchOpp',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:043AM',0,'Feb 17 2004 10:46:31:043AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(83,4,3,'Leads.do','Reports',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:060AM',0,'Feb 17 2004 10:46:31:060AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(84,4,3,'LeadsComponents.do','ModifyComponent',NULL,'Modify Component','You can modify the component details associated with an opportunity in a pipeline.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:153AM',0,'Feb 17 2004 10:46:31:153AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(85,4,3,'LeadsCalls.do','Add',NULL,'Adding a call','You can add a new call here associated with the opportunity.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:217AM',0,'Feb 17 2004 10:46:31:217AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(86,4,3,'Leads.do','ModifyOpp',NULL,'Modify the opportunity:','The description of the opportunity can be modified / updated.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:230AM',0,'Feb 17 2004 10:46:31:230AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(87,4,3,'LeadsCalls.do','Insert',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:263AM',0,'Feb 17 2004 10:46:31:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(88,4,3,'LeadsDocuments.do','Modify',NULL,'Modify Document','Modify the Subject or filename of a document.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:280AM',0,'Feb 17 2004 10:46:31:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(89,4,3,'LeadsCallsForward.do','ForwardMessage',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:293AM',0,'Feb 17 2004 10:46:31:293AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(90,4,3,'Leads.do','Save',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:293AM',0,'Feb 17 2004 10:46:31:293AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(91,4,3,'Leads.do','GenerateForm',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:310AM',0,'Feb 17 2004 10:46:31:310AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(92,4,3,'LeadsComponents.do','DetailsComponent',NULL,'Component Details','The component details for the opportunity are shown here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:327AM',0,'Feb 17 2004 10:46:31:327AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(93,4,3,'LeadsDocuments.do','AddVersion',NULL,'Upload a new version of document','You can upload a new version of the document and the new version of the file can be selected and uploaded.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:327AM',0,'Feb 17 2004 10:46:31:327AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(94,4,3,'Leads.do','Search',NULL,'List of components','The components resulted from the search are shown here. Different views of the components and its types are displayed. The name of the component with the estimated amount of money associated with the opportunity and the probability of that components being a success is shown. This also displays the time for closing the deal (term) and the organization name or the contact name if they are associated with the opportunity. A new opportunity can also be added.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:357AM',0,'Feb 17 2004 10:46:31:357AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(95,4,3,'Leads.do','UpdateOpp',NULL,'Opportunity Details','You can view all the details about the components here and also add a new component to a particular opportunity. Calls and the documents can be associated with the opportunity',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:403AM',0,'Feb 17 2004 10:46:31:403AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(96,4,3,'LeadsCallsForward.do','ForwardCall',NULL,'Forwarding a call','The details of calls associated with a contact can be forwarded to different users present in the system.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:467AM',0,'Feb 17 2004 10:46:31:467AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(97,4,3,'LeadsDocuments.do','View',NULL,'Document Details','In the Documents tab, the documents associated with a particular opportunity can be added to, and viewed.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:513AM',0,'Feb 17 2004 10:46:31:513AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(98,4,3,'LeadsCalls.do','Modify',NULL,'Modify call details','You can modify the details of the calls.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:543AM',0,'Feb 17 2004 10:46:31:543AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(99,4,3,'Leads.do','DetailsOpp',NULL,'Opportunity Details','You can view all the details about an opportunity components here and also add a new component to a particular opportunity. Calls and documents can also be added to an opportunity or viewed by clicking on the appropriate tab.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:560AM',0,'Feb 17 2004 10:46:31:560AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(100,4,3,'LeadsCalls.do','View',NULL,'Call Details','Calls associated with the opportunity are shown. Calls can be added to the opportunity, and details about listed calls can be examined.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:683AM',0,'Feb 17 2004 10:46:31:683AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(101,4,3,'Leads.do','ViewOpp',NULL,NULL,'The opportunities resulted from the search are shown here. Different views of the opportunities and its types are displayed. The name of the component with the estimated amount of money associated with the opportunity and the probability of that opportunity being a success are shown. This also displays the time for closing the deal (term) and the organization name or the contact name if they are associated with the opportunity.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:730AM',0,'Feb 17 2004 10:46:31:730AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(102,4,3,'Leads.do',NULL,NULL,NULL,'Pipeline helps in creating prospective opportunities or leads in the company. Each opportunity helps to follow up a lead, who might eventually turn into a client. Here you can create an opportunity, search for an existing opportunity in the system, export the data to different formats. The dashboard reflects the progress chart in different views for all the employees working under the owner/creator of the opportunity.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:780AM',0,'Feb 17 2004 10:46:31:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(103,4,3,'LeadsComponents.do','SaveComponent',NULL,'Component Details','
The component details for the opportunity are shown here.
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:810AM',0,'Feb 17 2004 10:46:31:810AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(104,4,3,'LeadsComponents.do','Prepare',NULL,'Add a component','A component can be added to an opportunity and assigned to any employee in the system. The component type can also be selected.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:827AM',0,'Feb 17 2004 10:46:31:827AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(105,4,3,'LeadsCalls.do','Update',NULL,'Call Details','The calls associated with the opportunity are shown. Calls can be added to the opportunity.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:857AM',0,'Feb 17 2004 10:46:31:857AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(106,4,3,'LeadsCalls.do','Details',NULL,'Call Details','Details about the call associated with the opportunity',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:903AM',0,'Feb 17 2004 10:46:31:903AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(107,4,3,'LeadsDocuments.do','Add',NULL,'Upload a document','New documents related to the opportunity can be uploaded into the system.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:31:920AM',0,'Feb 17 2004 10:46:31:920AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(108,4,3,'LeadsDocuments.do','Details',NULL,'Document Details','This shows all versions of the updated document. The name of the file with it''s size, version and the number of downloads are shown here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:000AM',0,'Feb 17 2004 10:46:32:000AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(109,4,3,'LeadsReports.do','ExportList',NULL,'List of exported data','The data present can be used to export data and display that in different formats. The exported data can be filtered in different ways. This would also let you view the data download it. This also shows the number of times an exported data has been downloaded',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:030AM',0,'Feb 17 2004 10:46:32:030AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(110,4,3,'LeadsReports.do','ExportForm',NULL,'Generating Export data','Generate an exported report from pipeline data',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:107AM',0,'Feb 17 2004 10:46:32:107AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(111,1,4,'Accounts.do','Dashboard',NULL,'Overview','The date range can be modified which is shown in the right hand window by clicking on a specific date on the calendar. Accounts with contract end dates or other required actions appear in the right hand window reminding the user to take action on them. The schedule, actions, alert dates and contract end dates are displayed for user or the employees under him by using the dropdown at the top of the page. Clicking on the alert link will let the user modify the details of the account owner.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:233AM',0,'Feb 17 2004 10:46:32:233AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(112,1,4,'Accounts.do','Add',NULL,'Add an Account','A new account can be added here',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:280AM',0,'Feb 17 2004 10:46:32:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(113,1,4,'Accounts.do','Modify',NULL,'Modify Account','The details of an account can be modified here',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:403AM',0,'Feb 17 2004 10:46:32:403AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(114,1,4,'Contacts.do','View',NULL,'Contact Details','A contact can be associated with an account. The lists of the contacts associated with the account are shown along with the title.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:450AM',0,'Feb 17 2004 10:46:32:450AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(115,1,4,'Accounts.do','Fields',NULL,'Folder Record Details','You create folders for accounts. Each folder can have one or more records associated with it, depending on the type of the folder. The details about records associated with the folder are shown',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:483AM',0,'Feb 17 2004 10:46:32:483AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(116,1,4,'Opportunities.do','View',NULL,'Opportunity Details','Opportunities associated with the contact, showing the best guess total and last modified date.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:530AM',0,'Feb 17 2004 10:46:32:530AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(117,1,4,'RevenueManager.do','View',NULL,'Revenue Details','The revenue associated with the account is shown here. The details about the revenue like the description, the date and the amount associated are displayed.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:560AM',0,'Feb 17 2004 10:46:32:560AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(118,1,4,'RevenueManager.do','View',NULL,'Revenue Details','The revenue associated with the account is shown here. Details about the revenue such as the description, the date, and the amount associated are displayed.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:607AM',0,'Feb 17 2004 10:46:32:607AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(119,1,4,'RevenueManager.do','Add',NULL,'Add Revenue','Adding new revenue to an account',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:653AM',0,'Feb 17 2004 10:46:32:653AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(120,1,4,'RevenueManager.do','Modify',NULL,'Modify Revenue','Here revenue details can be modified',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:670AM',0,'Feb 17 2004 10:46:32:670AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(121,1,4,'Accounts.do','ViewTickets',NULL,'Ticket Details','This page shows the complete list of the tickets related to an account, and lets you add a new ticket',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:687AM',0,'Feb 17 2004 10:46:32:687AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(122,1,4,'AccountsDocuments.do','View',NULL,'Document Details','Here the documents associated with the account are listed. New documents related to the account can be added.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:750AM',0,'Feb 17 2004 10:46:32:750AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(123,1,4,'Accounts.do','SearchForm',NULL,'Search Accounts','This page provides the search feature for accounts in the system.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:797AM',0,'Feb 17 2004 10:46:32:797AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(124,1,4,'Accounts.do','Details',NULL,'Account Details','This shows the details of the account, which can be modified. Each account can have folders, contacts, opportunities, revenue, tickets, and documents, for which there are separate tabs.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:810AM',0,'Feb 17 2004 10:46:32:810AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(125,1,4,'RevenueManager.do','Dashboard',NULL,'Revenue Dashboard','This revenue dashboard shows a progress chart for different years and types. All the accounts with revenue are also shown along with a list of employees working under you are also listed under the progress chart. You can add accounts, search for the existing ones in the system based on different filters and export the data in different formats.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:827AM',0,'Feb 17 2004 10:46:32:827AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(126,1,4,'Accounts.do','Reports',NULL,'Export Data','The data can be filtered, exported, displayed, and downloaded in different formats.You can also see the number of times an exported report has been downloaded.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:857AM',0,'Feb 17 2004 10:46:32:857AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(127,1,4,'Accounts.do','ModifyFields',NULL,'Modify folder record','The Folder record details can be updated.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:967AM',0,'Feb 17 2004 10:46:32:967AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(128,1,4,'AccountTickets.do','ReopenTicket',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:983AM',0,'Feb 17 2004 10:46:32:983AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(129,1,4,'Accounts.do','Delete',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:32:983AM',0,'Feb 17 2004 10:46:32:983AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(130,1,4,'Accounts.do','GenerateForm',NULL,'Generate New Export','To generate the Export data',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:000AM',0,'Feb 17 2004 10:46:33:000AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(131,1,4,'AccountContactsCalls.do','View',NULL,'Call Details','Calls associated with the contact',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:013AM',0,'Feb 17 2004 10:46:33:013AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(132,1,4,'Accounts.do','AddFolderRecord',NULL,'Add folder record','A new Folder record can be added to the Folder.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:047AM',0,'Feb 17 2004 10:46:33:047AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(133,1,4,'AccountContactsCalls.do','Add',NULL,'Add a call','You can add a new call, which is associated with a particular contact.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:077AM',0,'Feb 17 2004 10:46:33:077AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(134,1,4,'AccountsDocuments.do','Add',NULL,'Upload Document','New documents can be uploaded and associated with an account',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:093AM',0,'Feb 17 2004 10:46:33:093AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(135,1,4,'AccountsDocuments.do','Add',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:107AM',0,'Feb 17 2004 10:46:33:107AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(136,1,4,'AccountContactsOpps.do','UpdateOpp',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:123AM',0,'Feb 17 2004 10:46:33:123AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(137,1,4,'AccountTicketsDocuments.do','AddVersion',NULL,NULL,'Upload a New Version of an existing Document',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:123AM',0,'Feb 17 2004 10:46:33:123AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(138,1,4,'Accounts.do','Details',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:153AM',0,'Feb 17 2004 10:46:33:153AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(139,1,4,'Accounts.do','View',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:170AM',0,'Feb 17 2004 10:46:33:170AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(140,1,4,'AccountTickets.do','AddTicket',NULL,'Adding a new Ticket','This page lets you create a new ticket for the account',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:187AM',0,'Feb 17 2004 10:46:33:187AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(141,1,4,'AccountTicketsDocuments.do','View',NULL,'Document Details','Here the documents associated with the ticket are listed. New documents related to the ticket can be added',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:200AM',0,'Feb 17 2004 10:46:33:200AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(142,1,4,'AccountTickets.do','UpdateTicket',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:263AM',0,'Feb 17 2004 10:46:33:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(143,1,4,'AccountContactsOppComponents.do','Prepare',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:280AM',0,'Feb 17 2004 10:46:33:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(144,1,4,'Accounts.do','InsertFields',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:280AM',0,'Feb 17 2004 10:46:33:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(145,1,4,'AccountContactsOpps.do','Save',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:297AM',0,'Feb 17 2004 10:46:33:297AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(146,1,4,'Accounts.do','Search',NULL,NULL,'Lists the Accounts present and also lets you create an account',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:310AM',0,'Feb 17 2004 10:46:33:310AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(147,1,4,'AccountTicketsDocuments.do','Details',NULL,NULL,'Page shows all the versions of the current document',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:357AM',0,'Feb 17 2004 10:46:33:357AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(148,1,4,'AccountTicketsDocuments.do','Modify',NULL,NULL,'Modify the current document',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:373AM',0,'Feb 17 2004 10:46:33:373AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(149,1,4,'Accounts.do','Insert',NULL,'Account Details','Displays the details of the account, which can be modified. Each account can have folders, the contacts, opportunities, revenue, and tickets. You can update several documents associated with each account.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:390AM',0,'Feb 17 2004 10:46:33:390AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(150,1,4,'AccountContactsCalls.do','Details',NULL,'Call details','The details of the call are shown here which can be modified, deleted or forwarded to any of the users.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:483AM',0,'Feb 17 2004 10:46:33:483AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(151,1,4,'AccountContactsCalls.do','Modify',NULL,'Add / update a call','You can add a new call to a contact.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:500AM',0,'Feb 17 2004 10:46:33:500AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(152,1,4,'AccountContactsCalls.do','Save',NULL,'Call details','The details of the call are shown here, and can be modified, deleted or forwarded to any of the employees',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:513AM',0,'Feb 17 2004 10:46:33:513AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(153,1,4,'AccountContactsCalls.do','ForwardCall',NULL,'Forward Call','The details of the calls that are associated with a contact can be forwarded to different employees.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:530AM',0,'Feb 17 2004 10:46:33:530AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(154,1,4,'AccountContactsOpps.do','ViewOpps',NULL,'List of Opportunities','Opportunities associated with the contact, showing the best guess total and last modified date.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:547AM',0,'Feb 17 2004 10:46:33:547AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(155,1,4,'AccountContactsOpps.do','DetailsOpp',NULL,'Opportunity Details','You can view all the details about the components here and also add a new component to a particular opportunity. The opportunity can be renamed and its details can be modified',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:593AM',0,'Feb 17 2004 10:46:33:593AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(156,1,4,'AccountTickets.do','TicketDetails',NULL,'Ticket Details','This page lets you view the details of the ticket, and also lets you modify or delete the ticket.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:623AM',0,'Feb 17 2004 10:46:33:623AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(157,1,4,'AccountTickets.do','ModifyTicket',NULL,'Modify ticket','This page lets you modify the ticket information and update its details',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:653AM',0,'Feb 17 2004 10:46:33:653AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(158,1,4,'AccountTicketTasks.do','List',NULL,'Task Details','This page lists the tasks assigned for a particular account. New tasks can be added, which would then appear in the list of tasks, showing their priority and their assignment.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:670AM',0,'Feb 17 2004 10:46:33:670AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(159,1,4,'AccountTicketsDocuments.do','Add',NULL,'Uploading a Document','Upload a new document related to the account.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:717AM',0,'Feb 17 2004 10:46:33:717AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(160,1,4,'AccountTickets.do','ViewHistory',NULL,'Ticket Log History','This page maintains a complete log history of each ticket from its creation till the ticket is closed.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:733AM',0,'Feb 17 2004 10:46:33:733AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(161,1,4,'AccountsDocuments.do','Details',NULL,'Document Details','All the versions of the current document are listed here',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:750AM',0,'Feb 17 2004 10:46:33:750AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(162,1,4,'AccountsDocuments.do','Modify',NULL,'Modify Document','Modify the document information',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:763AM',0,'Feb 17 2004 10:46:33:763AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(163,1,4,'AccountsDocuments.do','AddVersion',NULL,'Upload New Version','Upload a new document version',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:780AM',0,'Feb 17 2004 10:46:33:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(164,1,4,'Accounts.do','ExportReport',NULL,'List of Exported data','The data can be filtered, exported and displayed in different formats. You can then view the data and also download it.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:903AM',0,'Feb 17 2004 10:46:33:903AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(165,1,4,'RevenueManager.do','Details',NULL,'Revenue Details','Details about revenue',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:950AM',0,'Feb 17 2004 10:46:33:950AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(166,1,4,'RevenueManager.do','Dashboard',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:967AM',0,'Feb 17 2004 10:46:33:967AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(167,1,4,'OpportunityForm.do','Prepare',NULL,'Add Opportunity','A new opportunity associated with the contact can be added',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:967AM',0,'Feb 17 2004 10:46:33:967AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(168,1,4,'Opportunities.do','Add',NULL,'Add opportunity','A new opportunity associated with the contact can be added',NULL,NULL,NULL,0,'Feb 17 2004 10:46:33:983AM',0,'Feb 17 2004 10:46:33:983AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(169,1,4,'Opportunities.do','Details',NULL,'Opportunity Details','You can view all the details about the components here like the status, the guess amount and the current stage. A new component can also be added to a particular opportunity.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:047AM',0,'Feb 17 2004 10:46:34:047AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(170,1,4,'Opportunities.do','Modify',NULL,'Modify Opportunity','The details of the opportunity can be modified',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:217AM',0,'Feb 17 2004 10:46:34:217AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(171,1,4,'OpportunitiesComponents.do','DetailsComponent',NULL,'Component Details','This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:233AM',0,'Feb 17 2004 10:46:34:233AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(172,1,4,'OpportunitiesComponents.do','Prepare',NULL,'Add a component','A component can be added to an opportunity and assigned to any employee present in the system. The component type can also be selected.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:250AM',0,'Feb 17 2004 10:46:34:250AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(173,1,4,'OpportunitiesComponents.do','ModifyComponent',NULL,'Modify Component','The details of the component can be added / updated to an opportunity and assigned to any employee present in the system. The component type can also be selected.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:280AM',0,'Feb 17 2004 10:46:34:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(174,1,4,'OpportunitiesComponents.do','SaveComponent',NULL,'Component Details','This page shows the details about the opportunity like what is the probability of closing the opportunity, what is the current stage of the opportunity etc',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:327AM',0,'Feb 17 2004 10:46:34:327AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(175,6,5,'CampaignManager.do','Dashboard',NULL,'Communications Dashboard','Track and analyze campaigns that have been activated and executed.

Messages can be sent out by any combination of email, fax, or mail merge. The Dashboard shows an overview of sent messages and allows you to drill down and view recipients and any survey results.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:343AM',0,'Feb 17 2004 10:46:34:343AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(176,6,5,'CampaignManager.do','Add',NULL,'Add a campaign','This page lets you add a new campaign into the system.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:390AM',0,'Feb 17 2004 10:46:34:390AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(177,6,5,'CampaignManager.do','View',NULL,'Campaign List','Create or work on existing campaigns.

The Campaign Builder allows you to select groups of contacts that you would like to send a message to, as well as schedule a delivery date. Additional options are available.
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:403AM',0,'Feb 17 2004 10:46:34:403AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(178,6,5,'CampaignManagerGroup.do','View',NULL,'View Groups','Each campaign needs at least one group to which to send a message. Use criteria to filter the contacts you need to reach and use them over and over again. As new contacts meet the criteria, they will be automatically included in future campaigns. This page lists the group names. It shows the groups created by you or everybody; i.e. all the groups.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:467AM',0,'Feb 17 2004 10:46:34:467AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(179,6,5,'CampaignManagerGroup.do','Add',NULL,'Add a Group','A new contact group can be added. Separate criteria can be specified when creating the group. The criteria can be defined to generate a list. The list to be added can be previewed before saving the group details.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:500AM',0,'Feb 17 2004 10:46:34:500AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(180,6,5,'CampaignManagerMessage.do','View',NULL,'Message List','Compose a message to reach your audience.
Each campaign requires a message that will be sent to selected groups. Write the message once, then use it in any number of future campaigns. Modified messages will only affect future campaigns. Displays the list of messages, with the description and other details.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:547AM',0,'Feb 17 2004 10:46:34:547AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(181,6,5,'CampaignManagerMessage.do','Add',NULL,'Adding a Message','You can add a new message for the campaign, which would show up in the message list.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:593AM',0,'Feb 17 2004 10:46:34:593AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(182,6,5,'CampaignManagerAttachment.do',NULL,NULL,'Create Attachments','Customize and configure your Campaigns with attachments. Attachments can include interactive items, like surveys, or provide additional materials like files.
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:607AM',0,'Feb 17 2004 10:46:34:607AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(183,6,5,'CampaignManagerGroup.do','Details',NULL,NULL,'Contacts of the group are displayed',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:623AM',0,'Feb 17 2004 10:46:34:623AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(184,6,5,'CampaignDocuments.do','AddVersion',NULL,NULL,'version change of a document',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:733AM',0,'Feb 17 2004 10:46:34:733AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(185,6,5,'CampaignManager.do','Dashboard',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:750AM',0,'Feb 17 2004 10:46:34:750AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(186,6,5,'CampaignManagerMessage.do','Insert',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:763AM',0,'Feb 17 2004 10:46:34:763AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(187,6,5,'CampaignManagerGroup.do','Update',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:780AM',0,'Feb 17 2004 10:46:34:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(188,6,5,'CampaignDocuments.do','Add',NULL,NULL,'New documents can be uploaded and be associated with the campaign.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:780AM',0,'Feb 17 2004 10:46:34:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(189,6,5,'CampaignManager.do','ResponseDetails',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:797AM',0,'Feb 17 2004 10:46:34:797AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(190,6,5,'CampaignManagerGroup.do','Preview',NULL,NULL,'Here details about the contacts, i.e. the name, their company, and their email address are displayed.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:810AM',0,'Feb 17 2004 10:46:34:810AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(191,6,5,'CampaignManager.do','PrepareDownload',NULL,'Campaign Details','This page shows the details about the campaign and also shows the list of available documents.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:827AM',0,'Feb 17 2004 10:46:34:827AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(192,6,5,'CampaignManager.do','ViewGroups',NULL,'Groups','The group name along with the contacts present in the Group are listed',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:857AM',0,'Feb 17 2004 10:46:34:857AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(193,6,5,'CampaignManagerSurvey.do','Add',NULL,'Adding a survey','You can add a new survey here',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:873AM',0,'Feb 17 2004 10:46:34:873AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(194,6,5,'CampaignManagerSurvey.do','MoveQuestion',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:890AM',0,'Feb 17 2004 10:46:34:890AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(195,6,5,'CampaignManager.do','Details',NULL,'Campaign Details','Campaign details are the number of groups selected for the campaign, the text message of the campaign, when is it scheduled to run, how the delivery of the message done, who entered these details and who modified it are shown here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:903AM',0,'Feb 17 2004 10:46:34:903AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(196,6,5,'CampaignDocuments.do','Details',NULL,NULL,'All Versions of this current Document are shown here with the details like the size of the uploaded file, the number of downloads etc.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:920AM',0,'Feb 17 2004 10:46:34:920AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(197,6,5,'CampaignDocuments.do','Modify',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:937AM',0,'Feb 17 2004 10:46:34:937AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(198,6,5,'CampaignManager.do','Insert',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:950AM',0,'Feb 17 2004 10:46:34:950AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(199,6,5,'CampaignManagerSurvey.do','ViewReturn',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:950AM',0,'Feb 17 2004 10:46:34:950AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(200,6,5,'CampaignManagerMessage.do','Details',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:967AM',0,'Feb 17 2004 10:46:34:967AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(201,6,5,'CampaignManager.do','ViewSchedule',NULL,NULL,'For the campaign you can schedule a delivery date to send the message to the recipients.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:967AM',0,'Feb 17 2004 10:46:34:967AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(202,6,5,'CampaignManagerGroup.do','Modify',NULL,NULL,'Here you can update the group details and also the update contact criteria.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:34:983AM',0,'Feb 17 2004 10:46:34:983AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(203,6,5,'CampaignManager.do','PreviewRecipients',NULL,'List of Recipients','The page displays a list of recipients with their name, company name, the date when the campaign was sent to those recipients and its status.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:030AM',0,'Feb 17 2004 10:46:35:030AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(204,6,5,'CampaignManager.do','PreviewMessage',NULL,'Message Details','The message details are shown here, in the form of an email with a subject and message.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:030AM',0,'Feb 17 2004 10:46:35:030AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(205,6,5,'CampaignManager.do','PreviewSchedule',NULL,'Campaign Schedule','This shows the delivery date and the delivery method or the campaign.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:060AM',0,'Feb 17 2004 10:46:35:060AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(206,6,5,'CampaignManager.do','ViewResults',NULL,'Campaign Results','This page shows the results of the responses received from all the recipients in the group. This also shows the Last response received.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:060AM',0,'Feb 17 2004 10:46:35:060AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(207,6,5,'CampaignManager.do','ViewResponse',NULL,'Campaign Response','This page shows the responses of all the recipients along with their system IP addresses and their email address',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:077AM',0,'Feb 17 2004 10:46:35:077AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(208,6,5,'CampaignDocuments.do','View',NULL,'Campaign Document details','This page lists all the documents associated with this campaign and for each document it lists the size of the file, the extension and the version of the file.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:077AM',0,'Feb 17 2004 10:46:35:077AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(209,6,5,'CampaignManager.do','ViewDetails',NULL,'Campaign Details','This is the detail page for the campaign, where step-by-step information is given on how to activate the campaign; i.e. what should be selected before a campaign is activated.

This campaign can be configured and can now be activated.
Once activated, today''s campaigns will begin processing in under 5 minutes and cannot be cancelled.
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:123AM',0,'Feb 17 2004 10:46:35:123AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(210,6,5,'CampaignManager.do','AddGroups',NULL,'Choose Groups','Selecting or updating the group / groups for the campaign can be done here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:217AM',0,'Feb 17 2004 10:46:35:217AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(211,6,5,'CampaignManager.do','ViewMessage',NULL,'Message Details','Updating a message for the campaign',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:250AM',0,'Feb 17 2004 10:46:35:250AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(212,6,5,'CampaignManager.do','ViewAttachmentsOverview',NULL,'Attachment Details','For each message, we can add the attachments.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:280AM',0,'Feb 17 2004 10:46:35:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(213,6,5,'CampaignManager.do','ViewAttachment',NULL,'Surveys','A survey can be selected for this campaign.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:297AM',0,'Feb 17 2004 10:46:35:297AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(214,6,5,'CampaignManager.do','ManageFileAttachments',NULL,'File Attachments','Campaign can also have a file as an attachment',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:327AM',0,'Feb 17 2004 10:46:35:327AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(215,6,5,'CampaignManager.do','Modify',NULL,'Modify Campaign Details','Updating the campaign name /description',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:327AM',0,'Feb 17 2004 10:46:35:327AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(216,6,5,'CampaignManagerMessage.do','Details',NULL,'Message Details','This page shows the details of the message.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:343AM',0,'Feb 17 2004 10:46:35:343AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(217,6,5,'CampaignManagerMessage.do','Modify',NULL,'Modify Message','This page lets you Add/Update a new message. The message can have an access type, limiting who can view a message.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:357AM',0,'Feb 17 2004 10:46:35:357AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(218,6,5,'CampaignManagerMessage.do','Update',NULL,'Message Details','This page shows the details of the message.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:390AM',0,'Feb 17 2004 10:46:35:390AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(219,6,5,'CampaignManagerMessage.do','Clone',NULL,'Adding a Message','This page lets you Add a new message or Update an existing one. The message can have an access type, defining who can view it.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:420AM',0,'Feb 17 2004 10:46:35:420AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(220,6,5,'CampaignManagerSurvey.do','View',NULL,'List of Surveys','This page displays the surveys created and lets you update them.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:450AM',0,'Feb 17 2004 10:46:35:450AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(221,6,5,'CampaignManagerSurvey.do','InsertAndAdd',NULL,'Survey Questions','Here you can add a new survey question. If the question type is "Item List", you can edit the list of items present in that list and also mark whether the particular question is required or not.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:640AM',0,'Feb 17 2004 10:46:35:640AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(222,6,5,'CampaignManagerSurvey.do','Details',NULL,'Survey Details','The details about the survey are displayed here along with the option to modify, delete and preview the survey.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:687AM',0,'Feb 17 2004 10:46:35:687AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(223,6,5,'CampaignManagerSurvey.do','Modify',NULL,'Survey Details','This page displays all the questions added to a particular survey. It also enables you to add new questions. The order of the questions can be changed by moving questions up or down in the list. Questions can also be edited or deleted.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:717AM',0,'Feb 17 2004 10:46:35:717AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(224,6,5,'CampaignManager.do',NULL,NULL,'Overview','You are looking at the communications module. This page reviews and manages campaigns with the following options: Dashboard, Campaign Builder, Build groups, Create messages and create attachments.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:763AM',0,'Feb 17 2004 10:46:35:763AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(225,8,6,'TroubleTickets.do','Details',NULL,'Ticket Details','This page lets you view the details of the ticket also lets you modify or delete the ticket.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:903AM',0,'Feb 17 2004 10:46:35:903AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(226,8,6,'TroubleTickets.do','Add',NULL,'Add a Ticket','You can add a new ticket here',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:920AM',0,'Feb 17 2004 10:46:35:920AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(227,8,6,'TroubleTickets.do','SearchTicketsForm',NULL,'Search Existing Tickets','Form used for searching existing tickets based on different filters and parameters.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:937AM',0,'Feb 17 2004 10:46:35:937AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(228,8,6,'TroubleTickets.do','Reports',NULL,'Export Data','This is the page shows exported data.
The data can be exported in different formats. The exported data can be viewed with its subject, the size of the exported data file, when it was created and by whom. It also shows the number of times that particular exported file was downloaded. A new data file can also be exported.
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:35:950AM',0,'Feb 17 2004 10:46:35:950AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(229,8,6,'TroubleTickets.do','Modify',NULL,'Modify Ticket Details','Here you can modify ticket details like information, classification, assignment and resolution.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:000AM',0,'Feb 17 2004 10:46:36:000AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(230,8,6,'TroubleTickets.do','Modify',NULL,'Modify Ticket Details','Here you can modify the ticket details like ticket information, it''s classification, the ticket?s assignment and it''s resolution.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:013AM',0,'Feb 17 2004 10:46:36:013AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(231,8,6,'TroubleTicketTasks.do','List',NULL,'List of Tasks','This page lists the tasks assigned for a particular ticket. New tasks can be added, which would then appear in the list of tasks, showing their priority, their assignment and other details.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:030AM',0,'Feb 17 2004 10:46:36:030AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(232,8,6,'TroubleTicketsDocuments.do','View',NULL,'List of Documents','Here the documents associated with a ticket are listed. New documents related to the ticket can be added.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:060AM',0,'Feb 17 2004 10:46:36:060AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(233,8,6,'TroubleTicketsFolders.do','Fields',NULL,'List of Folder Records','New folders can be created for each ticket. New Folders are defined and configured in the Admin Module. This page also displays a list of records with their details such as when the record was created, last modified, the action performed on the record etc.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:107AM',0,'Feb 17 2004 10:46:36:107AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(234,8,6,'TroubleTicketsFolders.do','AddFolderRecord',NULL,'Add Folder Record','The details of the record are added
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:140AM',0,'Feb 17 2004 10:46:36:140AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(235,8,6,'TroubleTickets.do','ViewHistory',NULL,'Ticket Log History','The log history of the ticket is maintained.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:153AM',0,'Feb 17 2004 10:46:36:153AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(236,8,6,'TroubleTickets.do',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:170AM',0,'Feb 17 2004 10:46:36:170AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(237,8,6,'TroubleTickets.do','Insert',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:187AM',0,'Feb 17 2004 10:46:36:187AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(238,8,6,'TroubleTicketsFolders.do','InsertFields',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:200AM',0,'Feb 17 2004 10:46:36:200AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(239,8,6,'TroubleTickets.do','GenerateForm',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:200AM',0,'Feb 17 2004 10:46:36:200AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(240,8,6,'TroubleTickets.do','Details',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:217AM',0,'Feb 17 2004 10:46:36:217AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(241,8,6,'TroubleTickets.do','Update',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:233AM',0,'Feb 17 2004 10:46:36:233AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(242,8,6,'TroubleTickets.do','ExportReport',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:233AM',0,'Feb 17 2004 10:46:36:233AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(243,8,6,'TroubleTicketsFolders.do','ModifyFields',NULL,'Modify Folder Record','This lists the details of the folder record, which can be modified.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:250AM',0,'Feb 17 2004 10:46:36:250AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(244,8,6,'TroubleTicketsFolders.do','UpdateFields',NULL,'Folder Record Details','The details about the folder along with the record information such as when the record was created and when it was modified is displayed here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:263AM',0,'Feb 17 2004 10:46:36:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(245,8,6,'TroubleTickets.do','SearchTickets',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:280AM',0,'Feb 17 2004 10:46:36:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(246,8,6,'TroubleTickets.do','Home',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:280AM',0,'Feb 17 2004 10:46:36:280AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(247,8,6,'TroubleTicketsDocuments.do','Add',NULL,'Adding a Document','A new document related to a ticket can be uploaded.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:373AM',0,'Feb 17 2004 10:46:36:373AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(248,8,6,'TroubleTicketsDocuments.do','Details',NULL,'Document Details','This page shows all the versions of the current document.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:390AM',0,'Feb 17 2004 10:46:36:390AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(249,8,6,'TroubleTicketsDocuments.do','Modify',NULL,'Modify Document Details','This page lets you modify the ticket information and update the details.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:403AM',0,'Feb 17 2004 10:46:36:403AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(250,8,6,'TroubleTicketsDocuments.do','AddVersion',NULL,'Add version number to Documents','You can upload a new version of an existing document.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:420AM',0,'Feb 17 2004 10:46:36:420AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(251,8,6,'TroubleTickets.do','Home',NULL,'Overview','This page displays the complete list of the tickets assigned to the user, the list of the tickets present in his department and finally the list of the tickets created by the user. For each ticket, the details about the ticket, such as the ticket number, priority, age of the ticket, the company and finally the assignment details are displayed. The issue details are also shown separately for each ticket.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:437AM',0,'Feb 17 2004 10:46:36:437AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(252,15,7,'CompanyDirectory.do','ListEmployees',NULL,'Overview','The details of each employee can be viewed, modified or deleted and a new detailed employee record can be added.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:530AM',0,'Feb 17 2004 10:46:36:530AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(253,15,7,'CompanyDirectory.do','EmployeeDetails',NULL,'Employee Details','This is the employee detail page. This page displays the email, phone number, addresses and additional details of each employee.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:577AM',0,'Feb 17 2004 10:46:36:577AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(254,15,7,'CompanyDirectory.do','Prepare',NULL,'Add an Employee','You can add an employee into the system. The details of the employee such as his email address; phone numbers, address and other additional details can be given along with his name',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:577AM',0,'Feb 17 2004 10:46:36:577AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(255,15,7,'CompanyDirectory.do','ModifyEmployee',NULL,'Modify Employee Details','Employee details such as the name of the employee, email address, phone numbers and address can be modified here.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:640AM',0,'Feb 17 2004 10:46:36:640AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(256,15,7,'CompanyDirectory.do','Save',NULL,NULL,'This page shows the details of the employee record, which can be modified or deleted.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:653AM',0,'Feb 17 2004 10:46:36:653AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(257,14,8,'Reports.do','ViewQueue',NULL,'Overview','This is the home of the reports. 

A list of customized reports can be viewed and the queue of the reports that are scheduled to be processed by the server are also displayed. Each report that is ready to be retrieved is displayed along with its details such as the subject of the report, the date when the report was generated, report status and finally the size of the report for the user to download.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:700AM',0,'Feb 17 2004 10:46:36:700AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(258,14,8,'Reports.do','RunReport',NULL,'List of Modules','This shows the different modules present and displays the list of corresponding reports present in each module.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:717AM',0,'Feb 17 2004 10:46:36:717AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(259,14,8,'Reports.do',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:750AM',0,'Feb 17 2004 10:46:36:750AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(260,14,8,'Reports.do','CancelReport',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:763AM',0,'Feb 17 2004 10:46:36:763AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(261,14,8,'Reports.do','ParameterList',NULL,'Parameters specification','This page takes the parameters that need to be specified to run the report.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:763AM',0,'Feb 17 2004 10:46:36:763AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(262,14,8,'Reports.do','ListReports',NULL,'Lis of Reports','In this module, you can choose the report that you want to run from the list of the reports.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:797AM',0,'Feb 17 2004 10:46:36:797AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(263,14,8,'Reports.do','CriteriaList',NULL,'Criteria List','You can choose to base this report on previously saved criteria, or continue and create new criteria.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:810AM',0,'Feb 17 2004 10:46:36:810AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(264,14,8,'Reports.do','GenerateReport',NULL,'Reports Added To Queue','This page shows that the requested report is added to the queue. Also lets you know the details about the report and queue status.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:827AM',0,'Feb 17 2004 10:46:36:827AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(265,14,8,'Reports.do',NULL,NULL,'Overview','
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:920AM',0,'Feb 17 2004 10:46:36:920AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(266,9,9,'Users.do','ListUsers',NULL,'List of Users','This section allows the administrator to view and add users and manage user hierarchies. The users are typically employees in your company who interact with your clients or customers.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:36:983AM',0,'Feb 17 2004 10:46:36:983AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(267,9,9,'Users.do','InsertUserForm',NULL,'Adding a New User','This form allows new users to be added to the system and records their contact information.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:030AM',0,'Feb 17 2004 10:46:37:030AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(268,9,9,'Users.do','ModifyUser',NULL,'Modify User Details','This form provides the administrator with an editable view of the user information, and also allows the administrator to view the users login history and view points.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:093AM',0,'Feb 17 2004 10:46:37:093AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(269,9,9,'Users.do','ViewLog',NULL,'User Login History','Provides a login history of the chosen user.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:153AM',0,'Feb 17 2004 10:46:37:153AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(270,9,9,'Viewpoints.do','ListViewpoints',NULL,'Viewpoints of User','The page displays the viewpoints of the employees regarding a particular module in the system. Lets you add a new viewpoint. The details displayed are when the viewpoint was entered and whether it is enabled or not.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:187AM',0,'Feb 17 2004 10:46:37:187AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(271,9,9,'Viewpoints.do','InsertViewpointForm',NULL,'Add Viewpoint','The contact name can be selected and the permissions /access for the modules can be given.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:217AM',0,'Feb 17 2004 10:46:37:217AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(272,9,9,'Viewpoints.do','ViewpointDetails',NULL,'Update Viewpoint','You can update a viewpoint and set the permissions to access different modules.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:263AM',0,'Feb 17 2004 10:46:37:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(273,9,9,'Roles.do','ListRoles',NULL,'List of Roles','You are looking at roles. 

This page lists the different roles present in the system, their role descriptions and the number of people present in the system who are assigned that role. New roles can be added at any time.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:297AM',0,'Feb 17 2004 10:46:37:297AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(274,9,9,'Roles.do','InsertRoleForm',NULL,'Add a New Role','This page will let you Add/Update the roles in the system. Also lets you change the permissions. The permissions can be changed or set for each module separately depending on the role.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:327AM',0,'Feb 17 2004 10:46:37:327AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(275,9,9,'Roles.do','RoleDetails',NULL,'Update Role','This page will let you update the roles in the system. Also lets you change the permissions. The permissions can be changed or set for each module separately depending on the role.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:343AM',0,'Feb 17 2004 10:46:37:343AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(276,9,9,'Admin.do','Config',NULL,'Configure Modules','This page lets you configure modules that meet the needs of your organization, including configuration of lookup lists and custom fields. Depending on permissions, each module that you can configure is listed and each module has a different number of configure options. The changes typically affect all users immediately.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:373AM',0,'Feb 17 2004 10:46:37:373AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(277,9,9,'Admin.do','ConfigDetails',NULL,'Configuration Options','You can configure different options in each module.The following are some of the configuration options that you might see in the modules. Some of these options are specific to the module so they might NOT be present in all the modules.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:390AM',0,'Feb 17 2004 10:46:37:390AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(278,9,9,'Admin.do','ModifyList',NULL,'Edit Lookup List','This page lets you edit and add to the list items.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:513AM',0,'Feb 17 2004 10:46:37:513AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(279,9,9,'AdminFieldsFolder.do','AddFolder',NULL,'Adding a New Folder','Add/Update the existing folder here',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:530AM',0,'Feb 17 2004 10:46:37:530AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(280,9,9,'AdminFieldsFolder.do','ModifyFolder',NULL,'Modify Existing Folder','Update the existing folder here',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:547AM',0,'Feb 17 2004 10:46:37:547AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(281,9,9,'AdminConfig.do','ListGlobalParams',NULL,'Configure System','You can configure the system for the session idle timeout and set the time for the time out.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:560AM',0,'Feb 17 2004 10:46:37:560AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(282,9,9,'AdminConfig.do','ModifyTimeout',NULL,'Modify Timeout','The session timeout is the time in which a user will automatically be logged out if the specified period of inactivity is reached.
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:577AM',0,'Feb 17 2004 10:46:37:577AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(283,9,9,'Admin.do','Usage',NULL,'Resource Usage Details','Current System Usage and Billing Usage Information are displayed.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:607AM',0,'Feb 17 2004 10:46:37:607AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(284,9,9,'Admin.do',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:640AM',0,'Feb 17 2004 10:46:37:640AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(285,9,9,'Users.do','DisableUserConfirm',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:640AM',0,'Feb 17 2004 10:46:37:640AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(286,9,9,'AdminFieldsFolder.do',NULL,NULL,'Custom Folders','This page lists all the custom folders created in the General Contacts; let''s you edit them and also allow you to enable/disable the folders.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:653AM',0,'Feb 17 2004 10:46:37:653AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(287,9,9,'AdminFieldsFolder.do','ListFolders',NULL,'List of Custom Folders','This page lists all the custom folders created in the General Contacts; let''s you edit them and also allow you to enable/disable the folders.
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:700AM',0,'Feb 17 2004 10:46:37:700AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(288,9,9,'AdminFields.do','ModifyField',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:763AM',0,'Feb 17 2004 10:46:37:763AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(289,9,9,'Admin.do','ListGlobalParams',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:780AM',0,'Feb 17 2004 10:46:37:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(290,9,9,'Admin.do','ModifyTimeout',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:780AM',0,'Feb 17 2004 10:46:37:780AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(291,9,9,'AdminObjectEvents.do',NULL,NULL,'Object Events:','The list of Object Events are displayed along with the corresponding Triggered Processes. The number of components and whether that Object Event is available or not is also shown.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:797AM',0,'Feb 17 2004 10:46:37:797AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(292,9,9,'AdminFieldsGroup.do','AddGroup',NULL,NULL,'Add a group',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:827AM',0,'Feb 17 2004 10:46:37:827AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(293,9,9,'AdminFields.do','AddField',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:843AM',0,'Feb 17 2004 10:46:37:843AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(294,9,9,'Admin.do','UpdateList',NULL,NULL,'The Lookup List displays all the list names, which can be edited, the number of items can be known and the ones present can be previewed.

',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:857AM',0,'Feb 17 2004 10:46:37:857AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(295,9,9,'AdminScheduledEvents.do',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:873AM',0,'Feb 17 2004 10:46:37:873AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(296,9,9,'Admin.do','Config',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:890AM',0,'Feb 17 2004 10:46:37:890AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(297,9,9,'Admin.do','EditLists',NULL,'Lookup Lists','The Lookup List displays all the list names, which can be edited, the number of items is displayed and the ones present can be previewed.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:890AM',0,'Feb 17 2004 10:46:37:890AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(298,9,9,'AdminFieldsGroup.do','ListGroups',NULL,'Folder Details','This page lists the folder details and the groups added to this folder. Each group can further have a custom field created or deleted. You can also place it in the desired position in the dropdown list.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:37:950AM',0,'Feb 17 2004 10:46:37:950AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(299,9,9,'AdminCategories.do','ViewActive',NULL,'Active Category Details','The four different levels for the "Active" and "Draft" categories are displayed. The level1 has the category name, which is further classified into sub directories/levels. The level1 has the sublevel called level2 which in turn has sublevel called level3 and so on.

',NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:060AM',0,'Feb 17 2004 10:46:38:060AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(300,9,9,'AdminCategories.do','View',NULL,'Draft Category Details','The four different levels for the active and the draft categories are displayed. The level1 has the category name, which is further classified into sub directories/levels. The level1 has the sublevel called level2 which in turn has sublevel called level3 and so on. The draft categories can be edited and activated. The activated draft categories would be then reflected in the Active Categories list. The modified/updated draft category can also be reverted to its original state.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:077AM',0,'Feb 17 2004 10:46:38:077AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(301,9,9,'Users.do','InsertUserForm',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:140AM',0,'Feb 17 2004 10:46:38:140AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(302,9,9,'Users.do','UpdateUser',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:153AM',0,'Feb 17 2004 10:46:38:153AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(303,9,9,'Users.do','AddUser',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:170AM',0,'Feb 17 2004 10:46:38:170AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(304,9,9,'Users.do','UserDetails',NULL,'User Details','This form provides the administrator with more information about the user, namely information pertaining to the users login history and view points.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:187AM',0,'Feb 17 2004 10:46:38:187AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(305,9,9,'Roles.do',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:233AM',0,'Feb 17 2004 10:46:38:233AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(306,9,9,'Roles.do','ListRoles',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:250AM',0,'Feb 17 2004 10:46:38:250AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(307,9,9,'Viewpoints.do','InsertViewpoint',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:250AM',0,'Feb 17 2004 10:46:38:250AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(308,9,9,'Viewpoints.do','InsertViewpoint',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:263AM',0,'Feb 17 2004 10:46:38:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(309,9,9,'Admin.do',NULL,NULL,'Overview','You are looking at the Admin module home page. Here you can manage the system by reviewing its usage, configuring specific modules and system parameters. 
',NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:263AM',0,'Feb 17 2004 10:46:38:263AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(310,11,13,'Search.do','SiteSearch',NULL,NULL,NULL,NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:390AM',0,'Feb 17 2004 10:46:38:390AM',1)
INSERT [help_contents] ([help_id],[category_id],[link_module_id],[module],[section],[subsection],[title],[description],[nextcontent],[prevcontent],[upcontent],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(311,11,13,'Search.do','SiteSearch',NULL,'General Search','You can search the system for data associated with a particular key term. This can be done using the search data text box present on the left side of the window. The data associated with the corresponding key term is looked for in different modules for a match and the results are displayed per module. The search results are shown with detail description.',NULL,NULL,NULL,0,'Feb 17 2004 10:46:38:403AM',0,'Feb 17 2004 10:46:38:403AM',1)
SET IDENTITY_INSERT [help_contents] OFF
GO
SET NOCOUNT OFF

-- Insert default help_tableof_contents
SET NOCOUNT ON
SET IDENTITY_INSERT [help_tableof_contents] ON
GO
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,'Modules',NULL,NULL,NULL,NULL,1,5,0,'Feb 17 2004 10:46:38:403AM',0,'Feb 17 2004 10:46:38:403AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,'My Home Page',NULL,NULL,1,12,2,5,0,'Feb 17 2004 10:46:38:450AM',0,'Feb 17 2004 10:46:38:450AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(3,'Overview',NULL,NULL,2,12,3,5,0,'Feb 17 2004 10:46:38:450AM',0,'Feb 17 2004 10:46:38:450AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(4,'Mailbox',NULL,NULL,2,12,3,10,0,'Feb 17 2004 10:46:38:483AM',0,'Feb 17 2004 10:46:38:483AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(5,'Message Details',NULL,NULL,4,12,4,15,0,'Feb 17 2004 10:46:38:500AM',0,'Feb 17 2004 10:46:38:500AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(6,'New Message',NULL,NULL,4,12,4,20,0,'Feb 17 2004 10:46:38:593AM',0,'Feb 17 2004 10:46:38:593AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(7,'Reply Message',NULL,NULL,4,12,4,25,0,'Feb 17 2004 10:46:38:607AM',0,'Feb 17 2004 10:46:38:607AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(8,'SendMessage',NULL,NULL,4,12,4,30,0,'Feb 17 2004 10:46:38:623AM',0,'Feb 17 2004 10:46:38:623AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(9,'Forward message',NULL,NULL,4,12,4,35,0,'Feb 17 2004 10:46:38:640AM',0,'Feb 17 2004 10:46:38:640AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(10,'Tasks',NULL,NULL,2,12,3,40,0,'Feb 17 2004 10:46:38:653AM',0,'Feb 17 2004 10:46:38:653AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(11,'Advanced Task',NULL,NULL,10,12,4,45,0,'Feb 17 2004 10:46:38:670AM',0,'Feb 17 2004 10:46:38:670AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(12,'Forwarding a Task',NULL,NULL,10,12,4,50,0,'Feb 17 2004 10:46:38:700AM',0,'Feb 17 2004 10:46:38:700AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(13,'Modify task',NULL,NULL,10,12,4,55,0,'Feb 17 2004 10:46:38:717AM',0,'Feb 17 2004 10:46:38:717AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(14,'Action Lists',NULL,NULL,2,12,3,60,0,'Feb 17 2004 10:46:38:750AM',0,'Feb 17 2004 10:46:38:750AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(15,'Action Contacts',NULL,NULL,14,12,4,65,0,'Feb 17 2004 10:46:38:763AM',0,'Feb 17 2004 10:46:38:763AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(16,'Add Action List',NULL,NULL,14,12,4,70,0,'Feb 17 2004 10:46:38:780AM',0,'Feb 17 2004 10:46:38:780AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(17,'Modify Action',NULL,NULL,14,12,4,75,0,'Feb 17 2004 10:46:38:797AM',0,'Feb 17 2004 10:46:38:797AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(18,'Re-assignments',NULL,NULL,2,12,3,80,0,'Feb 17 2004 10:46:38:827AM',0,'Feb 17 2004 10:46:38:827AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(19,'My Settings',NULL,NULL,2,12,3,85,0,'Feb 17 2004 10:46:38:827AM',0,'Feb 17 2004 10:46:38:827AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(20,'Personal Information',NULL,NULL,19,12,4,90,0,'Feb 17 2004 10:46:38:857AM',0,'Feb 17 2004 10:46:38:857AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(21,'Location Settings',NULL,NULL,19,12,4,95,0,'Feb 17 2004 10:46:38:873AM',0,'Feb 17 2004 10:46:38:873AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(22,'Update password',NULL,NULL,19,12,4,100,0,'Feb 17 2004 10:46:38:890AM',0,'Feb 17 2004 10:46:38:890AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(23,'Contacts',NULL,NULL,1,2,2,10,0,'Feb 17 2004 10:46:38:903AM',0,'Feb 17 2004 10:46:38:903AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(24,'Add a Contact',NULL,NULL,23,2,3,5,0,'Feb 17 2004 10:46:38:920AM',0,'Feb 17 2004 10:46:38:920AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(25,'Search Contacts',NULL,NULL,23,2,3,10,0,'Feb 17 2004 10:46:39:013AM',0,'Feb 17 2004 10:46:39:013AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(26,'Export Data',NULL,NULL,23,2,3,15,0,'Feb 17 2004 10:46:39:030AM',0,'Feb 17 2004 10:46:39:030AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(27,'Exporting data',NULL,NULL,23,2,3,20,0,'Feb 17 2004 10:46:39:047AM',0,'Feb 17 2004 10:46:39:047AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(28,'Pipeline',NULL,NULL,1,4,2,15,0,'Feb 17 2004 10:46:39:060AM',0,'Feb 17 2004 10:46:39:060AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(29,'Overview',NULL,NULL,28,4,3,5,0,'Feb 17 2004 10:46:39:077AM',0,'Feb 17 2004 10:46:39:077AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(30,'Add a Opportunity',NULL,NULL,28,4,3,10,0,'Feb 17 2004 10:46:39:093AM',0,'Feb 17 2004 10:46:39:093AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(31,'Search Opportunities',NULL,NULL,28,4,3,15,0,'Feb 17 2004 10:46:39:107AM',0,'Feb 17 2004 10:46:39:107AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(32,'Export Data',NULL,NULL,28,4,3,20,0,'Feb 17 2004 10:46:39:123AM',0,'Feb 17 2004 10:46:39:123AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(33,'Accounts',NULL,NULL,1,1,2,20,0,'Feb 17 2004 10:46:39:140AM',0,'Feb 17 2004 10:46:39:140AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(34,'Overview',NULL,NULL,33,1,3,5,0,'Feb 17 2004 10:46:39:153AM',0,'Feb 17 2004 10:46:39:153AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(35,'Add an Account',NULL,NULL,33,1,3,10,0,'Feb 17 2004 10:46:39:170AM',0,'Feb 17 2004 10:46:39:170AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(36,'Modify Account',NULL,NULL,33,1,3,15,0,'Feb 17 2004 10:46:39:187AM',0,'Feb 17 2004 10:46:39:187AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(37,'Contact Details',NULL,NULL,36,1,4,20,0,'Feb 17 2004 10:46:39:217AM',0,'Feb 17 2004 10:46:39:217AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(38,'Folder Record Details',NULL,NULL,36,1,4,25,0,'Feb 17 2004 10:46:39:250AM',0,'Feb 17 2004 10:46:39:250AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(39,'Opportunity Details',NULL,NULL,36,1,4,30,0,'Feb 17 2004 10:46:39:263AM',0,'Feb 17 2004 10:46:39:263AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(40,'Revenue Details',NULL,NULL,36,1,4,35,0,'Feb 17 2004 10:46:39:280AM',0,'Feb 17 2004 10:46:39:280AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(41,'Revenue Details',NULL,NULL,40,1,5,40,0,'Feb 17 2004 10:46:39:297AM',0,'Feb 17 2004 10:46:39:297AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(42,'Add Revenue',NULL,NULL,40,1,5,45,0,'Feb 17 2004 10:46:39:310AM',0,'Feb 17 2004 10:46:39:310AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(43,'Modify Revenue',NULL,NULL,40,1,5,50,0,'Feb 17 2004 10:46:39:343AM',0,'Feb 17 2004 10:46:39:343AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(44,'Ticket Details',NULL,NULL,36,1,4,55,0,'Feb 17 2004 10:46:39:357AM',0,'Feb 17 2004 10:46:39:357AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(45,'Document Details',NULL,NULL,36,1,4,60,0,'Feb 17 2004 10:46:39:373AM',0,'Feb 17 2004 10:46:39:373AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(46,'Search Accounts',NULL,NULL,33,1,3,65,0,'Feb 17 2004 10:46:39:390AM',0,'Feb 17 2004 10:46:39:390AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(47,'Account Details',NULL,NULL,33,1,3,70,0,'Feb 17 2004 10:46:39:403AM',0,'Feb 17 2004 10:46:39:403AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(48,'Revenue Dashboard',NULL,NULL,33,1,3,75,0,'Feb 17 2004 10:46:39:420AM',0,'Feb 17 2004 10:46:39:420AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(49,'Export Data',NULL,NULL,33,1,3,80,0,'Feb 17 2004 10:46:39:437AM',0,'Feb 17 2004 10:46:39:437AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(50,'Communications',NULL,NULL,1,6,2,25,0,'Feb 17 2004 10:46:39:467AM',0,'Feb 17 2004 10:46:39:467AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(51,'Communications Dashboard',NULL,NULL,50,6,3,5,0,'Feb 17 2004 10:46:39:560AM',0,'Feb 17 2004 10:46:39:560AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(52,'Add a campaign',NULL,NULL,50,6,3,10,0,'Feb 17 2004 10:46:39:560AM',0,'Feb 17 2004 10:46:39:560AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(53,'Campaign List',NULL,NULL,50,6,3,15,0,'Feb 17 2004 10:46:39:593AM',0,'Feb 17 2004 10:46:39:593AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(54,'View Groups',NULL,NULL,50,6,3,20,0,'Feb 17 2004 10:46:39:607AM',0,'Feb 17 2004 10:46:39:607AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(55,'Add a Group',NULL,NULL,50,6,3,25,0,'Feb 17 2004 10:46:39:623AM',0,'Feb 17 2004 10:46:39:623AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(56,'Message List',NULL,NULL,50,6,3,30,0,'Feb 17 2004 10:46:39:640AM',0,'Feb 17 2004 10:46:39:640AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(57,'Adding a Message',NULL,NULL,50,6,3,35,0,'Feb 17 2004 10:46:39:653AM',0,'Feb 17 2004 10:46:39:653AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(58,'Create Attachments',NULL,NULL,50,6,3,40,0,'Feb 17 2004 10:46:39:670AM',0,'Feb 17 2004 10:46:39:670AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(59,'Help Desk',NULL,NULL,1,8,2,30,0,'Feb 17 2004 10:46:39:687AM',0,'Feb 17 2004 10:46:39:687AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(60,'Ticket Details',NULL,NULL,59,8,3,5,0,'Feb 17 2004 10:46:39:700AM',0,'Feb 17 2004 10:46:39:700AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(61,'Add a Ticket',NULL,NULL,59,8,3,10,0,'Feb 17 2004 10:46:39:717AM',0,'Feb 17 2004 10:46:39:717AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(62,'Search Existing Tickets',NULL,NULL,59,8,3,15,0,'Feb 17 2004 10:46:39:733AM',0,'Feb 17 2004 10:46:39:733AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(63,'Export Data',NULL,NULL,59,8,3,20,0,'Feb 17 2004 10:46:39:780AM',0,'Feb 17 2004 10:46:39:780AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(64,'Modify Ticket Details',NULL,NULL,59,8,3,25,0,'Feb 17 2004 10:46:39:810AM',0,'Feb 17 2004 10:46:39:810AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(65,'Modify Ticket Details',NULL,NULL,64,8,4,30,0,'Feb 17 2004 10:46:39:827AM',0,'Feb 17 2004 10:46:39:827AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(66,'List of Tasks',NULL,NULL,64,8,4,35,0,'Feb 17 2004 10:46:39:843AM',0,'Feb 17 2004 10:46:39:843AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(67,'List of Documents',NULL,NULL,64,8,4,40,0,'Feb 17 2004 10:46:39:857AM',0,'Feb 17 2004 10:46:39:857AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(68,'List of Folder Records',NULL,NULL,64,8,4,45,0,'Feb 17 2004 10:46:39:873AM',0,'Feb 17 2004 10:46:39:873AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(69,'Add Folder Record',NULL,NULL,64,8,4,50,0,'Feb 17 2004 10:46:39:890AM',0,'Feb 17 2004 10:46:39:890AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(70,'Ticket Log History',NULL,NULL,64,8,4,55,0,'Feb 17 2004 10:46:39:903AM',0,'Feb 17 2004 10:46:39:903AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(71,'Employees',NULL,NULL,1,15,2,35,0,'Feb 17 2004 10:46:39:920AM',0,'Feb 17 2004 10:46:39:920AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(72,'Overview',NULL,NULL,71,15,3,5,0,'Feb 17 2004 10:46:39:937AM',0,'Feb 17 2004 10:46:39:937AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(73,'Employee Details',NULL,NULL,71,15,3,10,0,'Feb 17 2004 10:46:39:950AM',0,'Feb 17 2004 10:46:39:950AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(74,'Add an Employee',NULL,NULL,71,15,3,15,0,'Feb 17 2004 10:46:39:967AM',0,'Feb 17 2004 10:46:39:967AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(75,'Modify Employee Details',NULL,NULL,71,15,3,20,0,'Feb 17 2004 10:46:39:983AM',0,'Feb 17 2004 10:46:39:983AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(76,'Reports',NULL,NULL,1,14,2,40,0,'Feb 17 2004 10:46:40:000AM',0,'Feb 17 2004 10:46:40:000AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(77,'Overview',NULL,NULL,76,14,3,5,0,'Feb 17 2004 10:46:40:013AM',0,'Feb 17 2004 10:46:40:013AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(78,'List of Modules',NULL,NULL,76,14,3,10,0,'Feb 17 2004 10:46:40:107AM',0,'Feb 17 2004 10:46:40:107AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(79,'Admin',NULL,NULL,1,9,2,45,0,'Feb 17 2004 10:46:40:123AM',0,'Feb 17 2004 10:46:40:123AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(80,'List of Users',NULL,NULL,79,9,3,5,0,'Feb 17 2004 10:46:40:123AM',0,'Feb 17 2004 10:46:40:123AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(81,'Adding a New User',NULL,NULL,80,9,4,10,0,'Feb 17 2004 10:46:40:140AM',0,'Feb 17 2004 10:46:40:140AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(82,'Modify User Details',NULL,NULL,80,9,4,15,0,'Feb 17 2004 10:46:40:170AM',0,'Feb 17 2004 10:46:40:170AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(83,'User Login History',NULL,NULL,80,9,4,20,0,'Feb 17 2004 10:46:40:187AM',0,'Feb 17 2004 10:46:40:187AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(84,'Viewpoints of User',NULL,NULL,80,9,4,25,0,'Feb 17 2004 10:46:40:200AM',0,'Feb 17 2004 10:46:40:200AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(85,'Add Viewpoint',NULL,NULL,84,9,5,30,0,'Feb 17 2004 10:46:40:217AM',0,'Feb 17 2004 10:46:40:217AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(86,'Update Viewpoint',NULL,NULL,84,9,5,35,0,'Feb 17 2004 10:46:40:233AM',0,'Feb 17 2004 10:46:40:233AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(87,'List of Roles',NULL,NULL,79,9,3,40,0,'Feb 17 2004 10:46:40:250AM',0,'Feb 17 2004 10:46:40:250AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(88,'Add a New Role',NULL,NULL,87,9,4,45,0,'Feb 17 2004 10:46:40:263AM',0,'Feb 17 2004 10:46:40:263AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(89,'Update Role',NULL,NULL,87,9,4,50,0,'Feb 17 2004 10:46:40:280AM',0,'Feb 17 2004 10:46:40:280AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(90,'Configure Modules',NULL,NULL,79,9,3,55,0,'Feb 17 2004 10:46:40:310AM',0,'Feb 17 2004 10:46:40:310AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(91,'Configuration Options',NULL,NULL,90,9,4,60,0,'Feb 17 2004 10:46:40:327AM',0,'Feb 17 2004 10:46:40:327AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(92,'Edit Lookup List',NULL,NULL,91,9,5,65,0,'Feb 17 2004 10:46:40:343AM',0,'Feb 17 2004 10:46:40:343AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(93,'Adding a New Folder',NULL,NULL,91,9,5,70,0,'Feb 17 2004 10:46:40:357AM',0,'Feb 17 2004 10:46:40:357AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(94,'Modify Existing Folder',NULL,NULL,91,9,5,75,0,'Feb 17 2004 10:46:40:373AM',0,'Feb 17 2004 10:46:40:373AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(95,'Configure System',NULL,NULL,79,9,3,80,0,'Feb 17 2004 10:46:40:390AM',0,'Feb 17 2004 10:46:40:390AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(96,'Modify Timeout',NULL,NULL,95,9,4,85,0,'Feb 17 2004 10:46:40:403AM',0,'Feb 17 2004 10:46:40:403AM',1)
INSERT [help_tableof_contents] ([content_id],[displaytext],[firstchild],[nextsibling],[parent],[category_id],[contentlevel],[contentorder],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(97,'Resource Usage Details',NULL,NULL,79,9,3,90,0,'Feb 17 2004 10:46:40:500AM',0,'Feb 17 2004 10:46:40:500AM',1)
SET IDENTITY_INSERT [help_tableof_contents] OFF
GO
SET NOCOUNT OFF

-- Insert default help_tableofcontentitem_links
SET NOCOUNT ON
SET IDENTITY_INSERT [help_tableofcontentitem_links] ON
GO
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,3,1,0,'Feb 17 2004 10:46:38:467AM',0,'Feb 17 2004 10:46:38:467AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,4,2,0,'Feb 17 2004 10:46:38:500AM',0,'Feb 17 2004 10:46:38:500AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(3,5,3,0,'Feb 17 2004 10:46:38:593AM',0,'Feb 17 2004 10:46:38:593AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(4,6,4,0,'Feb 17 2004 10:46:38:607AM',0,'Feb 17 2004 10:46:38:607AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(5,7,5,0,'Feb 17 2004 10:46:38:623AM',0,'Feb 17 2004 10:46:38:623AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(6,8,6,0,'Feb 17 2004 10:46:38:640AM',0,'Feb 17 2004 10:46:38:640AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(7,9,7,0,'Feb 17 2004 10:46:38:653AM',0,'Feb 17 2004 10:46:38:653AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(8,10,8,0,'Feb 17 2004 10:46:38:670AM',0,'Feb 17 2004 10:46:38:670AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(9,11,9,0,'Feb 17 2004 10:46:38:687AM',0,'Feb 17 2004 10:46:38:687AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(10,12,10,0,'Feb 17 2004 10:46:38:717AM',0,'Feb 17 2004 10:46:38:717AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(11,13,11,0,'Feb 17 2004 10:46:38:733AM',0,'Feb 17 2004 10:46:38:733AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(12,14,12,0,'Feb 17 2004 10:46:38:763AM',0,'Feb 17 2004 10:46:38:763AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(13,15,13,0,'Feb 17 2004 10:46:38:780AM',0,'Feb 17 2004 10:46:38:780AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(14,16,14,0,'Feb 17 2004 10:46:38:797AM',0,'Feb 17 2004 10:46:38:797AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(15,17,15,0,'Feb 17 2004 10:46:38:810AM',0,'Feb 17 2004 10:46:38:810AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(16,18,16,0,'Feb 17 2004 10:46:38:827AM',0,'Feb 17 2004 10:46:38:827AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(17,19,17,0,'Feb 17 2004 10:46:38:843AM',0,'Feb 17 2004 10:46:38:843AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(18,20,18,0,'Feb 17 2004 10:46:38:857AM',0,'Feb 17 2004 10:46:38:857AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(19,21,19,0,'Feb 17 2004 10:46:38:873AM',0,'Feb 17 2004 10:46:38:873AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(20,22,20,0,'Feb 17 2004 10:46:38:890AM',0,'Feb 17 2004 10:46:38:890AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(21,24,34,0,'Feb 17 2004 10:46:38:937AM',0,'Feb 17 2004 10:46:38:937AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(22,25,35,0,'Feb 17 2004 10:46:39:013AM',0,'Feb 17 2004 10:46:39:013AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(23,26,36,0,'Feb 17 2004 10:46:39:030AM',0,'Feb 17 2004 10:46:39:030AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(24,27,37,0,'Feb 17 2004 10:46:39:060AM',0,'Feb 17 2004 10:46:39:060AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(25,29,78,0,'Feb 17 2004 10:46:39:077AM',0,'Feb 17 2004 10:46:39:077AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(26,30,79,0,'Feb 17 2004 10:46:39:093AM',0,'Feb 17 2004 10:46:39:093AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(27,31,80,0,'Feb 17 2004 10:46:39:123AM',0,'Feb 17 2004 10:46:39:123AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(28,32,81,0,'Feb 17 2004 10:46:39:140AM',0,'Feb 17 2004 10:46:39:140AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(29,34,111,0,'Feb 17 2004 10:46:39:170AM',0,'Feb 17 2004 10:46:39:170AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(30,35,112,0,'Feb 17 2004 10:46:39:187AM',0,'Feb 17 2004 10:46:39:187AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(31,36,113,0,'Feb 17 2004 10:46:39:200AM',0,'Feb 17 2004 10:46:39:200AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(32,37,114,0,'Feb 17 2004 10:46:39:233AM',0,'Feb 17 2004 10:46:39:233AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(33,38,115,0,'Feb 17 2004 10:46:39:263AM',0,'Feb 17 2004 10:46:39:263AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(34,39,116,0,'Feb 17 2004 10:46:39:280AM',0,'Feb 17 2004 10:46:39:280AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(35,40,117,0,'Feb 17 2004 10:46:39:297AM',0,'Feb 17 2004 10:46:39:297AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(36,41,118,0,'Feb 17 2004 10:46:39:310AM',0,'Feb 17 2004 10:46:39:310AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(37,42,119,0,'Feb 17 2004 10:46:39:327AM',0,'Feb 17 2004 10:46:39:327AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(38,43,120,0,'Feb 17 2004 10:46:39:343AM',0,'Feb 17 2004 10:46:39:343AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(39,44,121,0,'Feb 17 2004 10:46:39:373AM',0,'Feb 17 2004 10:46:39:373AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(40,45,122,0,'Feb 17 2004 10:46:39:390AM',0,'Feb 17 2004 10:46:39:390AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(41,46,123,0,'Feb 17 2004 10:46:39:403AM',0,'Feb 17 2004 10:46:39:403AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(42,47,124,0,'Feb 17 2004 10:46:39:420AM',0,'Feb 17 2004 10:46:39:420AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(43,48,125,0,'Feb 17 2004 10:46:39:437AM',0,'Feb 17 2004 10:46:39:437AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(44,49,126,0,'Feb 17 2004 10:46:39:450AM',0,'Feb 17 2004 10:46:39:450AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(45,51,175,0,'Feb 17 2004 10:46:39:560AM',0,'Feb 17 2004 10:46:39:560AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(46,52,176,0,'Feb 17 2004 10:46:39:577AM',0,'Feb 17 2004 10:46:39:577AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(47,53,177,0,'Feb 17 2004 10:46:39:593AM',0,'Feb 17 2004 10:46:39:593AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(48,54,178,0,'Feb 17 2004 10:46:39:607AM',0,'Feb 17 2004 10:46:39:607AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(49,55,179,0,'Feb 17 2004 10:46:39:623AM',0,'Feb 17 2004 10:46:39:623AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(50,56,180,0,'Feb 17 2004 10:46:39:640AM',0,'Feb 17 2004 10:46:39:640AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(51,57,181,0,'Feb 17 2004 10:46:39:670AM',0,'Feb 17 2004 10:46:39:670AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(52,58,182,0,'Feb 17 2004 10:46:39:687AM',0,'Feb 17 2004 10:46:39:687AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(53,60,225,0,'Feb 17 2004 10:46:39:717AM',0,'Feb 17 2004 10:46:39:717AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(54,61,226,0,'Feb 17 2004 10:46:39:733AM',0,'Feb 17 2004 10:46:39:733AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(55,62,227,0,'Feb 17 2004 10:46:39:780AM',0,'Feb 17 2004 10:46:39:780AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(56,63,228,0,'Feb 17 2004 10:46:39:797AM',0,'Feb 17 2004 10:46:39:797AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(57,64,229,0,'Feb 17 2004 10:46:39:810AM',0,'Feb 17 2004 10:46:39:810AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(58,65,230,0,'Feb 17 2004 10:46:39:827AM',0,'Feb 17 2004 10:46:39:827AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(59,66,231,0,'Feb 17 2004 10:46:39:843AM',0,'Feb 17 2004 10:46:39:843AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(60,67,232,0,'Feb 17 2004 10:46:39:857AM',0,'Feb 17 2004 10:46:39:857AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(61,68,233,0,'Feb 17 2004 10:46:39:873AM',0,'Feb 17 2004 10:46:39:873AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(62,69,234,0,'Feb 17 2004 10:46:39:890AM',0,'Feb 17 2004 10:46:39:890AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(63,70,235,0,'Feb 17 2004 10:46:39:920AM',0,'Feb 17 2004 10:46:39:920AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(64,72,252,0,'Feb 17 2004 10:46:39:950AM',0,'Feb 17 2004 10:46:39:950AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(65,73,253,0,'Feb 17 2004 10:46:39:967AM',0,'Feb 17 2004 10:46:39:967AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(66,74,254,0,'Feb 17 2004 10:46:39:983AM',0,'Feb 17 2004 10:46:39:983AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(67,75,255,0,'Feb 17 2004 10:46:40:000AM',0,'Feb 17 2004 10:46:40:000AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(68,77,257,0,'Feb 17 2004 10:46:40:107AM',0,'Feb 17 2004 10:46:40:107AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(69,78,258,0,'Feb 17 2004 10:46:40:107AM',0,'Feb 17 2004 10:46:40:107AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(70,80,266,0,'Feb 17 2004 10:46:40:140AM',0,'Feb 17 2004 10:46:40:140AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(71,81,267,0,'Feb 17 2004 10:46:40:153AM',0,'Feb 17 2004 10:46:40:153AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(72,82,268,0,'Feb 17 2004 10:46:40:187AM',0,'Feb 17 2004 10:46:40:187AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(73,83,269,0,'Feb 17 2004 10:46:40:200AM',0,'Feb 17 2004 10:46:40:200AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(74,84,270,0,'Feb 17 2004 10:46:40:217AM',0,'Feb 17 2004 10:46:40:217AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(75,85,271,0,'Feb 17 2004 10:46:40:233AM',0,'Feb 17 2004 10:46:40:233AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(76,86,272,0,'Feb 17 2004 10:46:40:250AM',0,'Feb 17 2004 10:46:40:250AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(77,87,273,0,'Feb 17 2004 10:46:40:263AM',0,'Feb 17 2004 10:46:40:263AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(78,88,274,0,'Feb 17 2004 10:46:40:280AM',0,'Feb 17 2004 10:46:40:280AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(79,89,275,0,'Feb 17 2004 10:46:40:297AM',0,'Feb 17 2004 10:46:40:297AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(80,90,276,0,'Feb 17 2004 10:46:40:310AM',0,'Feb 17 2004 10:46:40:310AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(81,91,277,0,'Feb 17 2004 10:46:40:343AM',0,'Feb 17 2004 10:46:40:343AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(82,92,278,0,'Feb 17 2004 10:46:40:357AM',0,'Feb 17 2004 10:46:40:357AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(83,93,279,0,'Feb 17 2004 10:46:40:373AM',0,'Feb 17 2004 10:46:40:373AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(84,94,280,0,'Feb 17 2004 10:46:40:390AM',0,'Feb 17 2004 10:46:40:390AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(85,95,281,0,'Feb 17 2004 10:46:40:403AM',0,'Feb 17 2004 10:46:40:403AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(86,96,282,0,'Feb 17 2004 10:46:40:500AM',0,'Feb 17 2004 10:46:40:500AM',1)
INSERT [help_tableofcontentitem_links] ([link_id],[global_link_id],[linkto_content_id],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(87,97,283,0,'Feb 17 2004 10:46:40:513AM',0,'Feb 17 2004 10:46:40:513AM',1)
SET IDENTITY_INSERT [help_tableofcontentitem_links] OFF
GO
SET NOCOUNT OFF

-- Insert default help_features
SET NOCOUNT ON
SET IDENTITY_INSERT [help_features] ON
GO
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(1,1,NULL,'You can view the accounts that need attention',0,'Feb 17 2004 10:46:28:340AM',0,'Feb 17 2004 10:46:28:340AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(2,1,NULL,'You can make calls with the contact information readily accessible',0,'Feb 17 2004 10:46:28:373AM',0,'Feb 17 2004 10:46:28:373AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(3,1,NULL,'You can view the tasks assigned to you',0,'Feb 17 2004 10:46:28:373AM',0,'Feb 17 2004 10:46:28:373AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(4,1,NULL,'You can view the tickets assigned to you',0,'Feb 17 2004 10:46:28:387AM',0,'Feb 17 2004 10:46:28:387AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(5,2,NULL,'The select button can be used to view the details, reply, forward or delete a particular message.',0,'Feb 17 2004 10:46:28:467AM',0,'Feb 17 2004 10:46:28:467AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(6,2,NULL,'You can add a new message',0,'Feb 17 2004 10:46:28:467AM',0,'Feb 17 2004 10:46:28:467AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(7,2,NULL,'Clicking on the message will show the details of the message',0,'Feb 17 2004 10:46:28:480AM',0,'Feb 17 2004 10:46:28:480AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(8,2,NULL,'The drop down can be used to select the messages present in the inbox, sent messages, or archived ones',0,'Feb 17 2004 10:46:28:480AM',0,'Feb 17 2004 10:46:28:480AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(9,2,NULL,'Sort on one of the column headers by clicking on the column of your choice',0,'Feb 17 2004 10:46:28:497AM',0,'Feb 17 2004 10:46:28:497AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(10,3,NULL,'You can reply, archive, forward or delete each message by clicking the corresponding button',0,'Feb 17 2004 10:46:28:530AM',0,'Feb 17 2004 10:46:28:530AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(11,4,NULL,'A new message can be composed either to the contacts or the employees present in the recipients list. The options field can be checked to send a copy to the employees task list apart from sending the employee an email.',0,'Feb 17 2004 10:46:28:543AM',0,'Feb 17 2004 10:46:28:543AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(12,5,NULL,'You can send the email by clicking the send button',0,'Feb 17 2004 10:46:28:577AM',0,'Feb 17 2004 10:46:28:577AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(13,5,NULL,'You can add to the list of recipients by using the link "Add Recipients"',0,'Feb 17 2004 10:46:28:577AM',0,'Feb 17 2004 10:46:28:577AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(14,5,NULL,'You can click the check box to send an Internet email to the recipients',0,'Feb 17 2004 10:46:28:590AM',0,'Feb 17 2004 10:46:28:590AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(15,5,NULL,'Type directly in the Body test field to modify the message',0,'Feb 17 2004 10:46:28:590AM',0,'Feb 17 2004 10:46:28:590AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(16,7,NULL,'You can add the list of recipients by using the link "Add Recipients" and also click the check box which would send an email to the recipients',0,'Feb 17 2004 10:46:28:637AM',0,'Feb 17 2004 10:46:28:637AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(17,7,NULL,'You can send the email by clicking the send button',0,'Feb 17 2004 10:46:28:637AM',0,'Feb 17 2004 10:46:28:637AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(18,7,NULL,'You can edit the message by typing directly in the Body text area',0,'Feb 17 2004 10:46:28:637AM',0,'Feb 17 2004 10:46:28:637AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(19,8,NULL,'You can add a quick task. This task would have just the description and whether the task is personal or not',0,'Feb 17 2004 10:46:28:670AM',0,'Feb 17 2004 10:46:28:670AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(20,8,NULL,'For each of the existing tasks, you can view, modify, forward or delete the tasks by clicking on the Action button, and making a selection.',0,'Feb 17 2004 10:46:28:670AM',0,'Feb 17 2004 10:46:28:670AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(21,8,NULL,'You can select to view your tasks or tasks assigned by you to others working under you. Each can be viewed in three different modes. i.e. the completed tasks, uncompleted tasks or both.',0,'Feb 17 2004 10:46:28:683AM',0,'Feb 17 2004 10:46:28:683AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(22,8,NULL,'You can add a detailed (advanced) task, where you can set up the priority, status, whether the task is shared or not, task assignment, give the estimated time and add some detailed notes for it.',0,'Feb 17 2004 10:46:28:683AM',0,'Feb 17 2004 10:46:28:683AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(23,9,NULL,'Link this task to a contact and when you look at the task list, there will be a link to the contact record next to the task, allowing you to go directly to the contact.',0,'Feb 17 2004 10:46:28:730AM',0,'Feb 17 2004 10:46:28:730AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(24,9,NULL,'Filling in a Due Date will make ths task show up on that date in the Home Page calendar.',0,'Feb 17 2004 10:46:28:747AM',0,'Feb 17 2004 10:46:28:747AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(25,9,NULL,'Making the task personal will hide it from your hierarchy.',0,'Feb 17 2004 10:46:28:747AM',0,'Feb 17 2004 10:46:28:747AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(26,9,NULL,'You can assign a task to people lower than you in your hierarchy.',0,'Feb 17 2004 10:46:28:763AM',0,'Feb 17 2004 10:46:28:763AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(27,9,NULL,'Marking a task as complete will document the task as having been done, and immediately remove it from the Task List.',0,'Feb 17 2004 10:46:28:763AM',0,'Feb 17 2004 10:46:28:763AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(28,10,NULL,'Allows you to forward a task to one or more users of the system. Checking the options fields check box indicates that if the recipient is a user of the system, then a copy of the task is send to the recipient''s Internet email.',0,'Feb 17 2004 10:46:28:793AM',0,'Feb 17 2004 10:46:28:793AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(29,10,NULL,'The Subject line is mandatory',0,'Feb 17 2004 10:46:28:873AM',0,'Feb 17 2004 10:46:28:873AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(30,10,NULL,'You can add more text to the body of the message by typing directly in the Body text area',0,'Feb 17 2004 10:46:28:873AM',0,'Feb 17 2004 10:46:28:873AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(31,11,NULL,'Due dates will show on the Home Page calendar',0,'Feb 17 2004 10:46:28:903AM',0,'Feb 17 2004 10:46:28:903AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(32,11,NULL,'Completing a task will remove it from the task list',0,'Feb 17 2004 10:46:28:903AM',0,'Feb 17 2004 10:46:28:903AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(33,11,NULL,'You can assign a task to someone lower than you in your heirarchy',0,'Feb 17 2004 10:46:28:920AM',0,'Feb 17 2004 10:46:28:920AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(34,11,NULL,'You can Add or Change the contact that this task is linked to. When viewing the task, you will be able to view the contact information with one click.',0,'Feb 17 2004 10:46:28:920AM',0,'Feb 17 2004 10:46:28:920AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(35,12,NULL,'You can also view all the in progress Action Lists, completed lists, or both together.',0,'Feb 17 2004 10:46:28:950AM',0,'Feb 17 2004 10:46:28:950AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(36,12,NULL,'You can also keep track of the progress of your contacts. The number of them Completed and the Total are shown in the Progress Columns.',0,'Feb 17 2004 10:46:28:950AM',0,'Feb 17 2004 10:46:28:950AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(37,12,NULL,'You can add a new Action List with a description and status. You can select the contacts for this new Action List. For each of the contacts in the Action List, you can select a corresponding action with the Action Button: view details, modify contact, add contacts or delete the Action List.',0,'Feb 17 2004 10:46:28:967AM',0,'Feb 17 2004 10:46:28:967AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(38,13,NULL,'Clicking on the contact name will give you a pop up with more details about the contact and also about the related folders, calls, messages and opportunities.',0,'Feb 17 2004 10:46:28:980AM',0,'Feb 17 2004 10:46:28:980AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(39,13,NULL,'You can add contacts to the list and also Modify the List using "Add Contacts to list" and "Modify List" respectively.',0,'Feb 17 2004 10:46:28:997AM',0,'Feb 17 2004 10:46:28:997AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(40,13,NULL,'For the Action List you can also view all the in progress contacts, completed contacts or both.',0,'Feb 17 2004 10:46:28:997AM',0,'Feb 17 2004 10:46:28:997AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(41,13,NULL,'For each of the contacts you can add a call, opportunity, ticket, task or send a message, which would correspondingly appear in their respective tabs. For example, adding a ticket to the contact would be reflected in the Ticket tab.',0,'Feb 17 2004 10:46:28:997AM',0,'Feb 17 2004 10:46:28:997AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(42,14,NULL,'Select where the contacts will come from (General Contact, Account Contacts) in the From dropdown.',0,'Feb 17 2004 10:46:29:030AM',0,'Feb 17 2004 10:46:29:030AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(43,14,NULL,'Enter some search text, depending on the Operator you chose.',0,'Feb 17 2004 10:46:29:030AM',0,'Feb 17 2004 10:46:29:030AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(44,14,NULL,'Choose an Operator based on the Field you chose.',0,'Feb 17 2004 10:46:29:030AM',0,'Feb 17 2004 10:46:29:030AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(45,14,NULL,'Choose one of the many Field Names on which to base your query.',0,'Feb 17 2004 10:46:29:043AM',0,'Feb 17 2004 10:46:29:043AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(46,14,NULL,'You can Add or Remove contacts manually with the Add/Remove Contacts link.',0,'Feb 17 2004 10:46:29:060AM',0,'Feb 17 2004 10:46:29:060AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(47,14,NULL,'Add your query with the Add button at the bottom of the query frame. You can have multipe queries that make up the criteria for a group. You will get the result of all the queries.',0,'Feb 17 2004 10:46:29:077AM',0,'Feb 17 2004 10:46:29:077AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(48,14,NULL,'Save the Action List and generate the list of contacts by clicking the Save button at the bottom or top of the page.',0,'Feb 17 2004 10:46:29:107AM',0,'Feb 17 2004 10:46:29:107AM',NULL,NULL,1,7)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(49,15,NULL,'You can check the status checkbox to indicate that the New Action List is complete.',0,'Feb 17 2004 10:46:29:153AM',0,'Feb 17 2004 10:46:29:153AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(50,15,NULL,'The details of the New Action List can be saved by clicking the save button.',0,'Feb 17 2004 10:46:29:153AM',0,'Feb 17 2004 10:46:29:153AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(51,16,NULL,'Click Update at the bottom of the page to save your reassignment, Cancel to quit the page without saving, and Reset to reset all the fields to their defaults and start over.',0,'Feb 17 2004 10:46:29:170AM',0,'Feb 17 2004 10:46:29:170AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(52,16,NULL,'Choose a User to reassign data from in the top dropdown. Only users below you in your hierarchy will be present here.',0,'Feb 17 2004 10:46:29:183AM',0,'Feb 17 2004 10:46:29:183AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(53,16,NULL,'Select one or more To Users in the To User column to reassign the various assets to. The number of each type of asset available to be reassigned is shown in parentheses after the asset in the first column.',0,'Feb 17 2004 10:46:29:200AM',0,'Feb 17 2004 10:46:29:200AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(54,17,NULL,'The location of the employee can be changed, i.e. the time zone can be changed by clicking on "Configure my location."',0,'Feb 17 2004 10:46:29:217AM',0,'Feb 17 2004 10:46:29:217AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(55,17,NULL,'You can update your personal information by clicking on "Update my personal information."',0,'Feb 17 2004 10:46:29:217AM',0,'Feb 17 2004 10:46:29:217AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(56,17,NULL,'You can change your password by clicking on "Change my password."',0,'Feb 17 2004 10:46:29:230AM',0,'Feb 17 2004 10:46:29:230AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(57,18,NULL,'Save your changes by clicking the Update button at the top or bottom of the page. The Cancel button forgets the changes and quits the page. The Reset button resets all fields to their original values so you can start over.',0,'Feb 17 2004 10:46:29:247AM',0,'Feb 17 2004 10:46:29:247AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(58,18,NULL,'The only required field is your last name, but you should fill in as much as you can to make the system as useful as possible. Email address is particularly useful.',0,'Feb 17 2004 10:46:29:247AM',0,'Feb 17 2004 10:46:29:247AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(59,19,NULL,'The location settings can be changed by selecting the time zone from the drop down list and clicking the update button to update the settings.',0,'Feb 17 2004 10:46:29:280AM',0,'Feb 17 2004 10:46:29:280AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(60,20,NULL,'You can update your password by clicking on the update button.',0,'Feb 17 2004 10:46:29:293AM',0,'Feb 17 2004 10:46:29:293AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(61,21,NULL,'For each of the existing tasks, you can view, modify, forward or delete the tasks by clicking on the Action button',0,'Feb 17 2004 10:46:29:310AM',0,'Feb 17 2004 10:46:29:310AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(62,21,NULL,'You can add a quick task. This task would have just the description and whether the task is personal or not',0,'Feb 17 2004 10:46:29:327AM',0,'Feb 17 2004 10:46:29:327AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(63,21,NULL,'You can add or update a detailed task called advanced task, wherein you can set up the priority, the status, whether the task is shared or not, also is the task assigned to self or someone working under the owner of the tasks, give the estimated time and add some detailed notes in it.',0,'Feb 17 2004 10:46:29:327AM',0,'Feb 17 2004 10:46:29:327AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(64,21,NULL,'Checking the existing task''s check box indicates that the particular task is completed.',0,'Feb 17 2004 10:46:29:340AM',0,'Feb 17 2004 10:46:29:340AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(65,21,NULL,'You can select to view your tasks or tasks assigned by you to others. Each task can be viewed in three different modes i.e. the completed tasks, uncompleted tasks or all the tasks.',0,'Feb 17 2004 10:46:29:340AM',0,'Feb 17 2004 10:46:29:340AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(66,28,NULL,'You can add the new Action List here. Along with description and the status you need to select the contacts you want in this Action List. You can populate the list in two ways. The first is to use the Add/Remove contacts.',0,'Feb 17 2004 10:46:29:530AM',0,'Feb 17 2004 10:46:29:530AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(67,28,NULL,'The second is to define the criteria to generate the list. Once it''s generated we can add them to the selected criteria and contacts by using the add feature present.',0,'Feb 17 2004 10:46:29:530AM',0,'Feb 17 2004 10:46:29:530AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(68,31,NULL,'Check new contacts to add them to your list, uncheck existing contacts to remove them from your list.',0,'Feb 17 2004 10:46:29:560AM',0,'Feb 17 2004 10:46:29:560AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(69,31,NULL,'Select All Contact, My Contacts or Account Contacts from the dropdown at the top.',0,'Feb 17 2004 10:46:29:577AM',0,'Feb 17 2004 10:46:29:577AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(70,31,NULL,'Finish by clicking Done at the bottom of the page.',0,'Feb 17 2004 10:46:29:577AM',0,'Feb 17 2004 10:46:29:577AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(71,33,NULL,'You can add or update a detailed task called advanced task, wherein you can set up a priority, status, whether the task is shared or not, also is the task assigned to you or someone working under the owner of the tasks, give the estimated time and add some detailed notes to it.',0,'Feb 17 2004 10:46:29:623AM',0,'Feb 17 2004 10:46:29:623AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(72,34,NULL,'You can select the contact category using the radio button and if the contact category is someone permanently associated with an account, then you can select the contact using the "select" next to it.',0,'Feb 17 2004 10:46:29:670AM',0,'Feb 17 2004 10:46:29:670AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(73,34,NULL,'You can save the details about the employee using the "Save" button.',0,'Feb 17 2004 10:46:29:670AM',0,'Feb 17 2004 10:46:29:670AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(74,34,NULL,'The "Save & New" button saves the details of the employee and also opens up a blank form start another contact.',0,'Feb 17 2004 10:46:29:683AM',0,'Feb 17 2004 10:46:29:683AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(75,34,NULL,'The only mandatory field is the Last Name, however, it is important to fill in as much as possible. These fields can be used for various types of queries later.',0,'Feb 17 2004 10:46:29:700AM',0,'Feb 17 2004 10:46:29:700AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(76,34,NULL,'The contact type can be selected using the "select" link next to the contact type.',0,'Feb 17 2004 10:46:29:717AM',0,'Feb 17 2004 10:46:29:717AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(77,35,NULL,'If the contact already exists in the system, you can search for that contact by name, company, title, contact type or source.',0,'Feb 17 2004 10:46:29:730AM',0,'Feb 17 2004 10:46:29:730AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(78,36,NULL,'New export data can be generated by choosing the "Generate new export" link at the top of the page',0,'Feb 17 2004 10:46:29:747AM',0,'Feb 17 2004 10:46:29:747AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(79,36,NULL,'Use the dropdown to choose which data to display: the list of all the exported data in the system or only your own.',0,'Feb 17 2004 10:46:29:763AM',0,'Feb 17 2004 10:46:29:763AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(80,36,NULL,'The exported data can be viewed in html format by clicking on the report name. The exported data can also be downloaded in CSV format or deleted by clicking the Select button in the action field.',0,'Feb 17 2004 10:46:29:763AM',0,'Feb 17 2004 10:46:29:763AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(81,37,NULL,'You can add all the fields or add / delete single fields from the report by using the buttons in the middle of the page. First highlight a field on the left to add or a field on the right to delete.',0,'Feb 17 2004 10:46:29:793AM',0,'Feb 17 2004 10:46:29:793AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(82,37,NULL,'Use the Up and Down buttons on the right to sort the fields.',0,'Feb 17 2004 10:46:29:793AM',0,'Feb 17 2004 10:46:29:793AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(83,37,NULL,'The Subject is mandatory. Select which set of contacts the export will come from with Criteria. Select the Primary sort with the Sorting dropdown.',0,'Feb 17 2004 10:46:29:810AM',0,'Feb 17 2004 10:46:29:810AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(84,37,NULL,'Click the Generate button when you are ready to generate the exported report.',0,'Feb 17 2004 10:46:29:840AM',0,'Feb 17 2004 10:46:29:840AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(85,38,NULL,'You can update, cancel or reset the details of the contact using the corresponding buttons.',0,'Feb 17 2004 10:46:29:857AM',0,'Feb 17 2004 10:46:29:857AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(86,41,NULL,'You can also click on the select button under the action field to view, modify, forward or delete a call.',0,'Feb 17 2004 10:46:29:903AM',0,'Feb 17 2004 10:46:29:903AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(87,41,NULL,'Clicking on the subject of the call will give complete details about the call.',0,'Feb 17 2004 10:46:29:903AM',0,'Feb 17 2004 10:46:29:903AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(88,41,NULL,'You can add a call associated with a contact.',0,'Feb 17 2004 10:46:29:903AM',0,'Feb 17 2004 10:46:29:903AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(89,42,NULL,'The save button lets you create a new call which is associated with the call.',0,'Feb 17 2004 10:46:29:933AM',0,'Feb 17 2004 10:46:29:933AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(90,45,NULL,'You can click the select button under the action column for viewing, modifying and deleting an opportunity.',0,'Feb 17 2004 10:46:29:967AM',0,'Feb 17 2004 10:46:29:967AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(91,45,NULL,'Clicking on the name of the opportunity will show a detailed description of the opportunity.',0,'Feb 17 2004 10:46:29:967AM',0,'Feb 17 2004 10:46:29:967AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(92,45,NULL,'Choosing the different types of opportunities from the drop down can filter the display.',0,'Feb 17 2004 10:46:29:980AM',0,'Feb 17 2004 10:46:29:980AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(93,45,NULL,'Add an opportunity associated with a contact.',0,'Feb 17 2004 10:46:30:090AM',0,'Feb 17 2004 10:46:30:090AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(94,46,NULL,'You can modify, delete or forward each call using the corresponding buttons.',0,'Feb 17 2004 10:46:30:107AM',0,'Feb 17 2004 10:46:30:107AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(95,47,NULL,'The component type can be selected using the "select" button.',0,'Feb 17 2004 10:46:30:123AM',0,'Feb 17 2004 10:46:30:123AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(96,47,NULL,'You can assign the component to any of the employees present using the dropdown list present.',0,'Feb 17 2004 10:46:30:137AM',0,'Feb 17 2004 10:46:30:137AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(97,48,NULL,'An opportunity can be renamed or deleted using the buttons present at the bottom of the page.',0,'Feb 17 2004 10:46:30:153AM',0,'Feb 17 2004 10:46:30:153AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(98,48,NULL,'Clicking on the select button lets you view, modify or delete the details about a component.',0,'Feb 17 2004 10:46:30:170AM',0,'Feb 17 2004 10:46:30:170AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(99,48,NULL,'Clicking on the name of the component shows the details about that component.',0,'Feb 17 2004 10:46:30:170AM',0,'Feb 17 2004 10:46:30:170AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(100,48,NULL,'Add a new component associated with the contact.',0,'Feb 17 2004 10:46:30:183AM',0,'Feb 17 2004 10:46:30:183AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(101,49,NULL,'You can modify or delete the opportunity using the modify or delete button.',0,'Feb 17 2004 10:46:30:200AM',0,'Feb 17 2004 10:46:30:200AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(102,50,NULL,'The type of the call can be selected using the drop down list and all the other details related to the call are updated using the update button.',0,'Feb 17 2004 10:46:30:217AM',0,'Feb 17 2004 10:46:30:217AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(103,58,NULL,'You can click the attachments or the surveys link present along with the message text.',0,'Feb 17 2004 10:46:30:293AM',0,'Feb 17 2004 10:46:30:293AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(104,59,NULL,'You can modify or delete the opportunity using the modify or the delete button.',0,'Feb 17 2004 10:46:30:310AM',0,'Feb 17 2004 10:46:30:310AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(105,60,NULL,'You can also click the select button under the action field to view, modify, clone or delete a contact.',0,'Feb 17 2004 10:46:30:327AM',0,'Feb 17 2004 10:46:30:327AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(106,60,NULL,'Clicking the name of the contact will display additional details about the contact.',0,'Feb 17 2004 10:46:30:340AM',0,'Feb 17 2004 10:46:30:340AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(107,60,NULL,'Add a contact using the link "Add a Contact" at the top of the page',0,'Feb 17 2004 10:46:30:340AM',0,'Feb 17 2004 10:46:30:340AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(108,61,NULL,'You can also choose to display the list of all the exported data in the system or the exported data created by you.',0,'Feb 17 2004 10:46:30:373AM',0,'Feb 17 2004 10:46:30:373AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(109,61,NULL,'The exported data can be viewed as a .csv file or in html format. The exported data can also be deleted when the select button in the action field is clicked.',0,'Feb 17 2004 10:46:30:373AM',0,'Feb 17 2004 10:46:30:373AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(110,61,NULL,'New export data can be generated, which lets you choose from the contacts list.',0,'Feb 17 2004 10:46:30:387AM',0,'Feb 17 2004 10:46:30:387AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(111,62,NULL,'You can modify, clone, or delete the details of the contact.',0,'Feb 17 2004 10:46:30:403AM',0,'Feb 17 2004 10:46:30:403AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(112,63,NULL,'Clicking on the name of the message displays more details about the message.',0,'Feb 17 2004 10:46:30:433AM',0,'Feb 17 2004 10:46:30:433AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(113,63,NULL,'You can view the messages in two different views, i.e. all the messages present or the messages created/assigned by you.',0,'Feb 17 2004 10:46:30:433AM',0,'Feb 17 2004 10:46:30:433AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(114,64,NULL,'You can select the list of the recipients to whom you want to forward the particular call to by using the "Add Recipients" link.',0,'Feb 17 2004 10:46:30:450AM',0,'Feb 17 2004 10:46:30:450AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(115,66,NULL,'The component type can be selected using the "select" link.',0,'Feb 17 2004 10:46:30:480AM',0,'Feb 17 2004 10:46:30:480AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(116,66,NULL,'You can assign the component to any user using the dropdown list provided.',0,'Feb 17 2004 10:46:30:480AM',0,'Feb 17 2004 10:46:30:480AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(117,67,NULL,'You can update or cancel the information changed using the "update" or "cancel" button.',0,'Feb 17 2004 10:46:30:513AM',0,'Feb 17 2004 10:46:30:513AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(118,68,NULL,'The folders can be selected using the drop down list.',0,'Feb 17 2004 10:46:30:607AM',0,'Feb 17 2004 10:46:30:607AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(119,68,NULL,'You can click on the record name, to view the folder record details.',0,'Feb 17 2004 10:46:30:607AM',0,'Feb 17 2004 10:46:30:607AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(120,68,NULL,'You can view the details and modify them by clicking the select button under the action column.',0,'Feb 17 2004 10:46:30:623AM',0,'Feb 17 2004 10:46:30:623AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(121,68,NULL,'You can add a new record to a folder using the "Add a record to this folder" link.',0,'Feb 17 2004 10:46:30:623AM',0,'Feb 17 2004 10:46:30:623AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(122,68,NULL,'The folders can be selected using the drop down list.',0,'Feb 17 2004 10:46:30:637AM',0,'Feb 17 2004 10:46:30:637AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(123,69,NULL,'The changes made in the details of the folders can be updated or canceled using the "Update" or "Cancel" button.',0,'Feb 17 2004 10:46:30:653AM',0,'Feb 17 2004 10:46:30:653AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(124,70,NULL,'You can also click the select button under the action column for viewing, modifying and deleting an opportunity.',0,'Feb 17 2004 10:46:30:670AM',0,'Feb 17 2004 10:46:30:670AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(125,70,NULL,'Clicking on the name of the opportunity will display the details of the opportunity.',0,'Feb 17 2004 10:46:30:683AM',0,'Feb 17 2004 10:46:30:683AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(126,70,NULL,'Choosing the different types of opportunities from the drop down filters the display.',0,'Feb 17 2004 10:46:30:683AM',0,'Feb 17 2004 10:46:30:683AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(127,70,NULL,'Add an opportunity associated with a contact.',0,'Feb 17 2004 10:46:30:700AM',0,'Feb 17 2004 10:46:30:700AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(128,71,NULL,'If the contact already exists in the system, you can search for that contact by name, company, title, contact type or source, by typing the search term in the appropriate field, and clicking the Search button.',0,'Feb 17 2004 10:46:30:717AM',0,'Feb 17 2004 10:46:30:717AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(129,71,NULL,'You can filter, export, and display data in different formats by clicking the Export link at the top of the page.',0,'Feb 17 2004 10:46:30:717AM',0,'Feb 17 2004 10:46:30:717AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(130,71,NULL,'Click Add to add a new contact into the system.',0,'Feb 17 2004 10:46:30:730AM',0,'Feb 17 2004 10:46:30:730AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(131,72,NULL,'You can filter the contact list in three different views. The views are all contacts, your contacts and Account contacts.',0,'Feb 17 2004 10:46:30:747AM',0,'Feb 17 2004 10:46:30:747AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(132,72,NULL,'Check any or all the contacts from the list you want to assign to your action List.',0,'Feb 17 2004 10:46:30:747AM',0,'Feb 17 2004 10:46:30:747AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(133,74,NULL,'You can also view, modify, clone or delete the contact by clicking the corresponding button.',0,'Feb 17 2004 10:46:30:780AM',0,'Feb 17 2004 10:46:30:780AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(134,74,NULL,'You can associate calls, messages and opportunities with each of the contacts already in the system.',0,'Feb 17 2004 10:46:30:793AM',0,'Feb 17 2004 10:46:30:793AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(135,75,NULL,'The contact type can be selected using the ?select? link.',0,'Feb 17 2004 10:46:30:827AM',0,'Feb 17 2004 10:46:30:827AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(136,75,NULL,'The details can be updated using the update button.',0,'Feb 17 2004 10:46:30:840AM',0,'Feb 17 2004 10:46:30:840AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(137,76,NULL,'You can view all the messages related to the contact or only the messages owned by you. (My messages)',0,'Feb 17 2004 10:46:30:857AM',0,'Feb 17 2004 10:46:30:857AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(138,77,NULL,'This is for adding or updating a new detailed employee record into the system. The last name is the only mandatory field in creating an employee record, However it is important to add as much information as you can.',0,'Feb 17 2004 10:46:30:873AM',0,'Feb 17 2004 10:46:30:873AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(139,78,NULL,'You can view the progress chart in different views for all the employees working under the owner or creator of the opportunity. The views can be selected from the drop down box present under the chart. The mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point.',0,'Feb 17 2004 10:46:30:920AM',0,'Feb 17 2004 10:46:30:920AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(140,78,NULL,'The opportunities created are also shown, with their names and the probable gross revenue associated with that opportunity. Clicking on the opportunities shows a details page for the opportunity.',0,'Feb 17 2004 10:46:30:920AM',0,'Feb 17 2004 10:46:30:920AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(141,78,NULL,'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart. Clicking on an employee shows the Opportunity page from that person''s point of view. You can then work your way back up the chain by clicking the Up One Level link.',0,'Feb 17 2004 10:46:30:920AM',0,'Feb 17 2004 10:46:30:920AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(142,79,NULL,'The probability of Close, Estimated Close Date, Best Guess Estimate (what will the gross revenue be for this component?), and Estimated Term (over what time period?), are mandatory fields.',0,'Feb 17 2004 10:46:30:950AM',0,'Feb 17 2004 10:46:30:950AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(143,79,NULL,'You can assign the component to yourself or one of the users in your hierarchy.',0,'Feb 17 2004 10:46:30:950AM',0,'Feb 17 2004 10:46:30:950AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(144,79,NULL,'The Component Description is a mandatory field. Be descriptive as you will be using this to search on later.',0,'Feb 17 2004 10:46:30:967AM',0,'Feb 17 2004 10:46:30:967AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(145,79,NULL,'Use the Save button to save your changes and exit, Cancel to cancel your changes and exit, and Reset to cancel your changes and start over.',0,'Feb 17 2004 10:46:30:967AM',0,'Feb 17 2004 10:46:30:967AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(146,79,NULL,'Enter an Alert Description and Date to remind yourself to follow up on this component at a later date.',0,'Feb 17 2004 10:46:30:980AM',0,'Feb 17 2004 10:46:30:980AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(147,79,NULL,'You must associate the component with either a Contact or an Account. Choose one of the radio buttons, then one of the Select links.',0,'Feb 17 2004 10:46:30:980AM',0,'Feb 17 2004 10:46:30:980AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(148,80,NULL,'Existing opportunities can be searched using this feature. Opportunities can be searched on description, account name, or contact name with whom the opportunity is associated. It can also be searched by current progress / stage of the opportunity or the closing date range.',0,'Feb 17 2004 10:46:31:013AM',0,'Feb 17 2004 10:46:31:013AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(149,81,NULL,'The exported data can be viewed or downloaded as a .csv file or in html format. The exported data can also be deleted when the select button in the action column is clicked.',0,'Feb 17 2004 10:46:31:030AM',0,'Feb 17 2004 10:46:31:030AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(150,81,NULL,'You can also choose to display the list of all the exported data in the system or the exported data created by you.',0,'Feb 17 2004 10:46:31:030AM',0,'Feb 17 2004 10:46:31:030AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(151,81,NULL,'New export data can be generated by choosing from the contacts list.',0,'Feb 17 2004 10:46:31:043AM',0,'Feb 17 2004 10:46:31:043AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(152,84,NULL,'Component Description is a mandatory field',0,'Feb 17 2004 10:46:31:153AM',0,'Feb 17 2004 10:46:31:153AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(153,84,NULL,'Use Update at the top or bottom to save your changes, Cancel to quit this page without saving, and Reset to reset all fields to default and start over.',0,'Feb 17 2004 10:46:31:170AM',0,'Feb 17 2004 10:46:31:170AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(154,84,NULL,'Add an Alert Description and Date to alert you via a CRM System Message to take a new action',0,'Feb 17 2004 10:46:31:170AM',0,'Feb 17 2004 10:46:31:170AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(155,84,NULL,'Probability of close, Estimated Close Date (when you will get the revenue), Best Guess Estimate (how much revenue you will get), and Estimated Term (what term the revenue will be realized over) are all the mandatory fields.',0,'Feb 17 2004 10:46:31:183AM',0,'Feb 17 2004 10:46:31:183AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(156,84,NULL,'You can select a Component Type from the dropdown. These component types are configurable by your System Administrator.',0,'Feb 17 2004 10:46:31:183AM',0,'Feb 17 2004 10:46:31:183AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(157,84,NULL,'You can assign the component to any User using the dropdown list present.',0,'Feb 17 2004 10:46:31:200AM',0,'Feb 17 2004 10:46:31:200AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(158,85,NULL,'The type of the call can be a phone, fax or in person. Some notes regarding the call can be noted. You can add an alert to remind you to follow up on this call.',0,'Feb 17 2004 10:46:31:217AM',0,'Feb 17 2004 10:46:31:217AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(159,85,NULL,'The Contact dropdown is automatically populated with the correct contacts for the company or account you are dealing with.',0,'Feb 17 2004 10:46:31:230AM',0,'Feb 17 2004 10:46:31:230AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(160,86,NULL,'You can update or cancel the information changed using the "update" or "cancel" button present.',0,'Feb 17 2004 10:46:31:247AM',0,'Feb 17 2004 10:46:31:247AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(161,88,NULL,'You can update the details of the documents using the update button.',0,'Feb 17 2004 10:46:31:280AM',0,'Feb 17 2004 10:46:31:280AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(162,92,NULL,'The component details are shown with additional options for modifying and deleting the component.',0,'Feb 17 2004 10:46:31:327AM',0,'Feb 17 2004 10:46:31:327AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(163,93,NULL,'The document can be uploaded using the upload button.',0,'Feb 17 2004 10:46:31:340AM',0,'Feb 17 2004 10:46:31:340AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(164,93,NULL,'The new version of the document can be selected from your local computer using the browse button.',0,'Feb 17 2004 10:46:31:340AM',0,'Feb 17 2004 10:46:31:340AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(165,94,NULL,'For each of the component you can view the details, modify the content or delete it completely using the select button in the Action column.',0,'Feb 17 2004 10:46:31:373AM',0,'Feb 17 2004 10:46:31:373AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(166,94,NULL,'You can add an opportunity here by giving complete details about the opportunity.',0,'Feb 17 2004 10:46:31:373AM',0,'Feb 17 2004 10:46:31:373AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(167,94,NULL,'The search results for existing opportunities are displayed here.',0,'Feb 17 2004 10:46:31:387AM',0,'Feb 17 2004 10:46:31:387AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(168,94,NULL,'There are different views of the opportunities you can choose from the drop down list and the corresponding types for the opportunities.',0,'Feb 17 2004 10:46:31:387AM',0,'Feb 17 2004 10:46:31:387AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(169,95,NULL,'In the Documents tab, documents associated with an opportunity can be added. This also displays the documents already linked with this opportunity and other details about the document. Details can be viewed, downloaded, modified or deleted by using the select button in the action column',0,'Feb 17 2004 10:46:31:403AM',0,'Feb 17 2004 10:46:31:403AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(170,95,NULL,'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column.',0,'Feb 17 2004 10:46:31:420AM',0,'Feb 17 2004 10:46:31:420AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(171,95,NULL,'You can rename or delete the opportunity itself using the buttons below.',0,'Feb 17 2004 10:46:31:420AM',0,'Feb 17 2004 10:46:31:420AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(172,95,NULL,'You can modify, view and delete the details of any particular component by clicking the select button in the action column.',0,'Feb 17 2004 10:46:31:433AM',0,'Feb 17 2004 10:46:31:433AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(173,95,NULL,'In the Components tab, you can add a component. It also displays the status, amount and the date when the component will be closed.',0,'Feb 17 2004 10:46:31:433AM',0,'Feb 17 2004 10:46:31:433AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(174,95,NULL,'There are three tabs in each opportunity i.e. components, calls and documents.',0,'Feb 17 2004 10:46:31:450AM',0,'Feb 17 2004 10:46:31:450AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(175,95,NULL,'You get the organization name or the contact name at the top, which on clicking will take you to the Account details.',0,'Feb 17 2004 10:46:31:450AM',0,'Feb 17 2004 10:46:31:450AM',NULL,NULL,1,7)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(176,96,NULL,'You can select the list of the recipients to whom you want to forward a call to by using the "Add Recipients" link. This will bring up a window with all users, from which you can then choose using check boxes.',0,'Feb 17 2004 10:46:31:480AM',0,'Feb 17 2004 10:46:31:480AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(177,96,NULL,'You can email a copy of the call to a user''s Internet email by checking the Email check box.',0,'Feb 17 2004 10:46:31:497AM',0,'Feb 17 2004 10:46:31:497AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(178,96,NULL,'You can add to the message by simply typing in the Body text box.',0,'Feb 17 2004 10:46:31:497AM',0,'Feb 17 2004 10:46:31:497AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(179,97,NULL,'The version of the particular document can be modified using the add version link.',0,'Feb 17 2004 10:46:31:513AM',0,'Feb 17 2004 10:46:31:513AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(180,97,NULL,'A document can be viewed, downloaded, modified or deleted by using the select button in the action column.',0,'Feb 17 2004 10:46:31:530AM',0,'Feb 17 2004 10:46:31:530AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(181,97,NULL,'A click on the subject of the document will show all the versions present.',0,'Feb 17 2004 10:46:31:530AM',0,'Feb 17 2004 10:46:31:530AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(182,98,NULL,'The type of the call can be selected using the drop down list and all the other details related to the call are updated using the update button',0,'Feb 17 2004 10:46:31:543AM',0,'Feb 17 2004 10:46:31:543AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(183,99,NULL,'In the Calls tab you can add a call to the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column',0,'Feb 17 2004 10:46:31:577AM',0,'Feb 17 2004 10:46:31:577AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(184,99,NULL,'In the Components tab, you can add a component. It also displays the status, amount and the date when the component will be closed.',0,'Feb 17 2004 10:46:31:577AM',0,'Feb 17 2004 10:46:31:577AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(185,99,NULL,'There are three tabs in each opportunity i.e. components, calls and documents.',0,'Feb 17 2004 10:46:31:577AM',0,'Feb 17 2004 10:46:31:577AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(186,99,NULL,'You can rename or delete the whole opportunity (not just one of these components) using the buttons at the bottom.',0,'Feb 17 2004 10:46:31:590AM',0,'Feb 17 2004 10:46:31:590AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(187,99,NULL,'The organization or contact name appears on top, above the Components Tab, which when clicked, will take you to the Account details.',0,'Feb 17 2004 10:46:31:590AM',0,'Feb 17 2004 10:46:31:590AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(188,99,NULL,'You can modify, view and delete the details of any component by clicking the select button in the Action column, on the far left.',0,'Feb 17 2004 10:46:31:670AM',0,'Feb 17 2004 10:46:31:670AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(189,99,NULL,'In the Documents tab, documents associated with the particular opportunity can be added. This also displays the documents already linked with this opportunity and other details about the document. Details can be viewed, downloaded, modified or deleted by using the select button in the action column',0,'Feb 17 2004 10:46:31:683AM',0,'Feb 17 2004 10:46:31:683AM',NULL,NULL,1,7)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(190,100,NULL,'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. The call details can be viewed, modified, forwarded or deleted by using the select button in the action column',0,'Feb 17 2004 10:46:31:700AM',0,'Feb 17 2004 10:46:31:700AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(191,100,NULL,'If the call subject is clicked then complete details about the call are displayed.',0,'Feb 17 2004 10:46:31:717AM',0,'Feb 17 2004 10:46:31:717AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(192,101,NULL,'There are different views of the opportunities you can choose from the dropdown list and the corresponding types of the opportunities.',0,'Feb 17 2004 10:46:31:747AM',0,'Feb 17 2004 10:46:31:747AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(193,101,NULL,'You can add an opportunity here by giving complete details about the opportunity.',0,'Feb 17 2004 10:46:31:763AM',0,'Feb 17 2004 10:46:31:763AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(194,101,NULL,'The search results for existing opportunities are displayed here.',0,'Feb 17 2004 10:46:31:763AM',0,'Feb 17 2004 10:46:31:763AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(195,101,NULL,'For each of the components you can view the details, modify the content or delete it completely using the select button in the Action column. You can click on any of the component names, which shows more details about the component, such as the calls and documents associated with it.',0,'Feb 17 2004 10:46:31:780AM',0,'Feb 17 2004 10:46:31:780AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(196,102,NULL,'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart.',0,'Feb 17 2004 10:46:31:793AM',0,'Feb 17 2004 10:46:31:793AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(197,102,NULL,'Opportunities are displayed, with name and probable revemue. Clicking on the opportunities displays more details of the opportunity.',0,'Feb 17 2004 10:46:31:793AM',0,'Feb 17 2004 10:46:31:793AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(198,102,NULL,'You can view the progress chart in different views for all the employees working under the owner or creator of the opportunity. The views can be selected from the drop down box under the chart. The mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point.',0,'Feb 17 2004 10:46:31:810AM',0,'Feb 17 2004 10:46:31:810AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(199,103,NULL,'The component details are shown with additional options for modifying and deleting the component.',0,'Feb 17 2004 10:46:31:827AM',0,'Feb 17 2004 10:46:31:827AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(200,104,NULL,'The component type can be selected using the "select" link.',0,'Feb 17 2004 10:46:31:840AM',0,'Feb 17 2004 10:46:31:840AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(201,104,NULL,'You can assign the component to any of the employee present using the dropdown list.',0,'Feb 17 2004 10:46:31:857AM',0,'Feb 17 2004 10:46:31:857AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(202,105,NULL,'In the Calls tab you can add a call associated with the opportunity. This also displays the calls already linked with this opportunity and other details about the call. Call details can be viewed, modified, forwarded or deleted by using the select button in the action column.',0,'Feb 17 2004 10:46:31:887AM',0,'Feb 17 2004 10:46:31:887AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(203,105,NULL,'If the call subject is clicked then it will display complete details about the call.',0,'Feb 17 2004 10:46:31:887AM',0,'Feb 17 2004 10:46:31:887AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(204,106,NULL,'You can modify, delete and forward each of the calls.',0,'Feb 17 2004 10:46:31:903AM',0,'Feb 17 2004 10:46:31:903AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(205,107,NULL,'Clicking the Upload button will upload the selected document into the system.',0,'Feb 17 2004 10:46:31:933AM',0,'Feb 17 2004 10:46:31:933AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(206,107,NULL,'Clicking the Browse button opens a file browser on your own system. Simply navigate to the file on your drive that you want to upload and click Open. This will close the window and bring you back to the upload page.',0,'Feb 17 2004 10:46:31:950AM',0,'Feb 17 2004 10:46:31:950AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(207,107,NULL,'Add a very descriptive Subject for the file. This is a mandatory field.',0,'Feb 17 2004 10:46:31:983AM',0,'Feb 17 2004 10:46:31:983AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(208,108,NULL,'All the versions of the document can be downloaded from here. Simply select the version you want and click the Download link on the far left.',0,'Feb 17 2004 10:46:32:013AM',0,'Feb 17 2004 10:46:32:013AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(209,109,NULL,'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked.',0,'Feb 17 2004 10:46:32:047AM',0,'Feb 17 2004 10:46:32:047AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(210,109,NULL,'You can also choose to display the list of all the exported data in the system or the exported data created by you.',0,'Feb 17 2004 10:46:32:060AM',0,'Feb 17 2004 10:46:32:060AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(211,109,NULL,'New export data can be generated, which lets you choose from the contacts list.',0,'Feb 17 2004 10:46:32:077AM',0,'Feb 17 2004 10:46:32:077AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(212,109,NULL,'Click on the subject of the new export, the data is displayed in html format',0,'Feb 17 2004 10:46:32:093AM',0,'Feb 17 2004 10:46:32:093AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(213,110,NULL,'Click the Generate button at top or bottom to generate the report from the fields you have included. Click cancel to quit and go back to the Export Data page.',0,'Feb 17 2004 10:46:32:123AM',0,'Feb 17 2004 10:46:32:123AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(214,110,NULL,'Highlight the fields you want to include in the left column and click the Add or All link. Highlight fields in the right column and click the Del link to remove them.',0,'Feb 17 2004 10:46:32:140AM',0,'Feb 17 2004 10:46:32:140AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(215,110,NULL,'Use the Sorting dropdown to sort the report by one of a variety of fields',0,'Feb 17 2004 10:46:32:153AM',0,'Feb 17 2004 10:46:32:153AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(216,110,NULL,'Use the Criteria dropdown to use opportunities from My or All Opportunities',0,'Feb 17 2004 10:46:32:170AM',0,'Feb 17 2004 10:46:32:170AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(217,110,NULL,'The Subject is a mandatory field.',0,'Feb 17 2004 10:46:32:200AM',0,'Feb 17 2004 10:46:32:200AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(218,111,NULL,'Clicking on the alert link will let you modify the details of the account owner.',0,'Feb 17 2004 10:46:32:250AM',0,'Feb 17 2004 10:46:32:250AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(219,111,NULL,'Accounts with contract end dates or other required actions will appear in the right hand window where you can take action on them.',0,'Feb 17 2004 10:46:32:250AM',0,'Feb 17 2004 10:46:32:250AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(220,111,NULL,'You can view the schedule, actions, alert dates and contract end dates for yourself or your employees by using the dropdown at the top of the page.',0,'Feb 17 2004 10:46:32:263AM',0,'Feb 17 2004 10:46:32:263AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(221,111,NULL,'You can modify the date range shown in the right hand window by clicking on a specific date on the calendar, or on one of the arrows to the left of each week on the calendar to give you a week''s view. Clicking on "Back To Next 7 Days View" at the top of the right window changes the view to the next seven days. The day or week you are currently viewing is highlighted in yellow. Today''s date is highlighted in blue. You can change the month and year using the dropdowns at the top of the calendar, and you can always return to today by using the Today link, also at the top of the calendar.',0,'Feb 17 2004 10:46:32:263AM',0,'Feb 17 2004 10:46:32:263AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(222,112,NULL,'Use the Insert button at top or bottom to save your changes, Cancel to quit without saving, and Reset to reset all the fields to their default values and start over.',0,'Feb 17 2004 10:46:32:280AM',0,'Feb 17 2004 10:46:32:280AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(223,112,NULL,'It''s a faily straightforward "fill in the blanks" exercise. There should be a "Primary" or "Business", or "Main" version of phone/fax numbers and addresses because other modules such as Communications Manager use these to perform other actions.',0,'Feb 17 2004 10:46:32:297AM',0,'Feb 17 2004 10:46:32:297AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(224,112,NULL,'Fill in as many fields as possible. Most of them can be used later as search terms and for queries in reports.',0,'Feb 17 2004 10:46:32:310AM',0,'Feb 17 2004 10:46:32:310AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(225,112,NULL,'Depending on whether you have chosen Organization or Individual, there are mandatory description fields to fill out about the account.',0,'Feb 17 2004 10:46:32:310AM',0,'Feb 17 2004 10:46:32:310AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(226,112,NULL,'Choose whether this account is an Organization or an Individual with the appropriate radio button.',0,'Feb 17 2004 10:46:32:310AM',0,'Feb 17 2004 10:46:32:310AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(227,112,NULL,'Clicking the Select link next to Account Type(s) will open a window with a variety of choices for Account Types. You cah choose and number by clicking the checkboxes to the left. It is important to use this feature as your choice(s) are used for searches and as the subject of querries in reports in other parts of the application.',0,'Feb 17 2004 10:46:32:390AM',0,'Feb 17 2004 10:46:32:390AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(228,113,NULL,'The account owner can also be changed using the drop down list',0,'Feb 17 2004 10:46:32:420AM',0,'Feb 17 2004 10:46:32:420AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(229,113,NULL,'The account type can be selected using the "Select" button',0,'Feb 17 2004 10:46:32:420AM',0,'Feb 17 2004 10:46:32:420AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(230,113,NULL,'This is for adding or updating account details. The last name or the organization name, based on the classification, is the only mandatory field in creating a new account. The type of account can be selected using the select option given next to the account type',0,'Feb 17 2004 10:46:32:437AM',0,'Feb 17 2004 10:46:32:437AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(231,113,NULL,'If the Account has a contract, you should enter a contract end date in the fields provided. This will generate an icon on the Home Page and an alert for the owner of the account that action must be taken at a prearranged time.',0,'Feb 17 2004 10:46:32:437AM',0,'Feb 17 2004 10:46:32:437AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(232,114,NULL,'You can also view, modify, clone and delete the contact by clicking the select button under the action column.',0,'Feb 17 2004 10:46:32:467AM',0,'Feb 17 2004 10:46:32:467AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(233,114,NULL,'When the name of the contact is clicked, it shows details of that contact, with the options to modify, clone and delete the contact details.',0,'Feb 17 2004 10:46:32:467AM',0,'Feb 17 2004 10:46:32:467AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(234,114,NULL,'You can add a contact, which is associated with the account.',0,'Feb 17 2004 10:46:32:483AM',0,'Feb 17 2004 10:46:32:483AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(235,115,NULL,'Using the select button in the action column you can view details and modify the record.',0,'Feb 17 2004 10:46:32:500AM',0,'Feb 17 2004 10:46:32:500AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(236,115,NULL,'You can click on the record type to view the folders details and modify them.',0,'Feb 17 2004 10:46:32:500AM',0,'Feb 17 2004 10:46:32:500AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(237,115,NULL,'A new record can be added to the folder.',0,'Feb 17 2004 10:46:32:513AM',0,'Feb 17 2004 10:46:32:513AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(238,115,NULL,'The folders can be populated by configuring the module in the admin tab.. The type of the folder can be changed using the drop down list shown.',0,'Feb 17 2004 10:46:32:513AM',0,'Feb 17 2004 10:46:32:513AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(239,116,NULL,'Opportunities associated with the contact, showing the best guess total and last modified date.',0,'Feb 17 2004 10:46:32:547AM',0,'Feb 17 2004 10:46:32:547AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(240,116,NULL,'You can add an opportunity.',0,'Feb 17 2004 10:46:32:547AM',0,'Feb 17 2004 10:46:32:547AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(241,116,NULL,'Three types of opportunities are present which can be selected from the drop down list.',0,'Feb 17 2004 10:46:32:547AM',0,'Feb 17 2004 10:46:32:547AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(242,116,NULL,'When the description of the opportunity is clicked, it will give you more details about the opportunity and the components present in it.',0,'Feb 17 2004 10:46:32:560AM',0,'Feb 17 2004 10:46:32:560AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(243,117,NULL,'By clicking on the description of the revenue you get the details about that revenue along with the options to modify and delete its details.',0,'Feb 17 2004 10:46:32:577AM',0,'Feb 17 2004 10:46:32:577AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(244,117,NULL,'You can view your revenue or all the revenues associated with the account using the drop down box.',0,'Feb 17 2004 10:46:32:577AM',0,'Feb 17 2004 10:46:32:577AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(245,117,NULL,'You can also view, modify and delete the details of the revenue by clicking the select button in the action column.',0,'Feb 17 2004 10:46:32:593AM',0,'Feb 17 2004 10:46:32:593AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(246,117,NULL,'Add / update a new revenue associated with the account.',0,'Feb 17 2004 10:46:32:607AM',0,'Feb 17 2004 10:46:32:607AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(247,118,NULL,'Clicking on the description of the revenue displays its details, along with options to modify and delete them.',0,'Feb 17 2004 10:46:32:623AM',0,'Feb 17 2004 10:46:32:623AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(248,118,NULL,'You can view your revenue or all the revenues associated with the account using the drop down box.',0,'Feb 17 2004 10:46:32:623AM',0,'Feb 17 2004 10:46:32:623AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(249,118,NULL,'You can also view, modify and delete the details of the revenue by clicking the select button in the action column.',0,'Feb 17 2004 10:46:32:640AM',0,'Feb 17 2004 10:46:32:640AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(250,118,NULL,'Add / update a new revenue associated with the account.',0,'Feb 17 2004 10:46:32:640AM',0,'Feb 17 2004 10:46:32:640AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(251,119,NULL,'Add new revenue to an account.',0,'Feb 17 2004 10:46:32:670AM',0,'Feb 17 2004 10:46:32:670AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(252,120,NULL,'Fill in the blanks and use "Update" to save your changes or "Reset" to return to the original values.',0,'Feb 17 2004 10:46:32:687AM',0,'Feb 17 2004 10:46:32:687AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(253,121,NULL,'You can also click the select button under the action column to view, modify or delete the ticket.',0,'Feb 17 2004 10:46:32:700AM',0,'Feb 17 2004 10:46:32:700AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(254,121,NULL,'Clicking on the ticket number will let you view the details, modify or delete the ticket.',0,'Feb 17 2004 10:46:32:717AM',0,'Feb 17 2004 10:46:32:717AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(255,121,NULL,'Add a new ticket.',0,'Feb 17 2004 10:46:32:750AM',0,'Feb 17 2004 10:46:32:750AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(256,122,NULL,'The details of the documents can be viewed or modified by clicking on the select button under the Action column.',0,'Feb 17 2004 10:46:32:763AM',0,'Feb 17 2004 10:46:32:763AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(257,122,NULL,'Document versions can be updated by using the "add version" link.',0,'Feb 17 2004 10:46:32:763AM',0,'Feb 17 2004 10:46:32:763AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(258,122,NULL,'A new document can be added which is associated with the account.',0,'Feb 17 2004 10:46:32:780AM',0,'Feb 17 2004 10:46:32:780AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(259,122,NULL,'You can view the details of, modify, download or delete the documents associated with the account.',0,'Feb 17 2004 10:46:32:780AM',0,'Feb 17 2004 10:46:32:780AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(260,123,NULL,'You can search for accounts in the system. The search can be based on the account name, phone number or the account type. Three types of accounts can be selected from the drop down list shown.',0,'Feb 17 2004 10:46:32:810AM',0,'Feb 17 2004 10:46:32:810AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(261,124,NULL,'Click Modify at the top or bottom of the page to modify these datails.',0,'Feb 17 2004 10:46:32:827AM',0,'Feb 17 2004 10:46:32:827AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(262,125,NULL,'The list of employees reporting to a particular employee/supervisor is also shown below the progress chart.',0,'Feb 17 2004 10:46:32:843AM',0,'Feb 17 2004 10:46:32:843AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(263,125,NULL,'The Accounts present are also shown, with name and the amount of money associated with that Account. Clicking on the Account displays the details of the Account.',0,'Feb 17 2004 10:46:32:843AM',0,'Feb 17 2004 10:46:32:843AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(264,125,NULL,'You can view the progress chart in different views for all the employees working under the owner or creator of the Account. The views can be selected from the drop down box present under the chart. A mouse over or a click on the break point on the progress chart will give the date and exact value associated with that point.',0,'Feb 17 2004 10:46:32:857AM',0,'Feb 17 2004 10:46:32:857AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(265,126,NULL,'The exported data can be viewed as a .csv file or in the html format. The exported data can also be deleted when the select button in the action field is clicked.',0,'Feb 17 2004 10:46:32:873AM',0,'Feb 17 2004 10:46:32:873AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(266,126,NULL,'You can also choose to display the list of all the exported data in the system or the exported data created by you.',0,'Feb 17 2004 10:46:32:873AM',0,'Feb 17 2004 10:46:32:873AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(267,126,NULL,'New export data can be generated using the "Generate new export" link.',0,'Feb 17 2004 10:46:32:890AM',0,'Feb 17 2004 10:46:32:890AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(268,127,NULL,'The details are updated by clicking the Update button.',0,'Feb 17 2004 10:46:32:967AM',0,'Feb 17 2004 10:46:32:967AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(269,130,NULL,'There are filters through which you can exactly select the data needed to generate the export data. Apart from selecting the type of accounts and the criteria, you can also select the fields required and then sort them.',0,'Feb 17 2004 10:46:33:000AM',0,'Feb 17 2004 10:46:33:000AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(270,131,NULL,'Using the select button under the action column you can view the details about the call, modify the call, forward the call or delete the call on the whole.',0,'Feb 17 2004 10:46:33:013AM',0,'Feb 17 2004 10:46:33:013AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(271,131,NULL,'Clicking on the subject of the call will show you the details about the call that was made to the contact.',0,'Feb 17 2004 10:46:33:030AM',0,'Feb 17 2004 10:46:33:030AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(272,131,NULL,'You can add a call associated with the contact using the "Add a call" link.',0,'Feb 17 2004 10:46:33:030AM',0,'Feb 17 2004 10:46:33:030AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(273,132,NULL,'Record details can be saved using the save button.',0,'Feb 17 2004 10:46:33:060AM',0,'Feb 17 2004 10:46:33:060AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(274,133,NULL,'The details of the new call can be saved using the save button.',0,'Feb 17 2004 10:46:33:077AM',0,'Feb 17 2004 10:46:33:077AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(275,133,NULL,'The call type can be selected from the dropdown box.',0,'Feb 17 2004 10:46:33:093AM',0,'Feb 17 2004 10:46:33:093AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(276,134,NULL,'You can browse your local system to select a new document to upload.',0,'Feb 17 2004 10:46:33:107AM',0,'Feb 17 2004 10:46:33:107AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(277,137,NULL,'You can upload a new version of an existing document.',0,'Feb 17 2004 10:46:33:140AM',0,'Feb 17 2004 10:46:33:140AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(278,140,NULL,'You can insert a new ticket, add the ticket source and also assign new contact.',0,'Feb 17 2004 10:46:33:187AM',0,'Feb 17 2004 10:46:33:187AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(279,141,NULL,'The details of the documents can be viewed or modified by clicking on the select button under the Action column',0,'Feb 17 2004 10:46:33:200AM',0,'Feb 17 2004 10:46:33:200AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(280,141,NULL,'You can view the details, modify, download or delete the documents associated with the ticket',0,'Feb 17 2004 10:46:33:217AM',0,'Feb 17 2004 10:46:33:217AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(281,141,NULL,'A new document can be added which is associated with the ticket',0,'Feb 17 2004 10:46:33:217AM',0,'Feb 17 2004 10:46:33:217AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(282,141,NULL,'The document versions can be updated by using the "add version" link',0,'Feb 17 2004 10:46:33:233AM',0,'Feb 17 2004 10:46:33:233AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(283,146,NULL,'Clicking on the account name shows complete details about the account',0,'Feb 17 2004 10:46:33:310AM',0,'Feb 17 2004 10:46:33:310AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(284,146,NULL,'You can add a new account',0,'Feb 17 2004 10:46:33:327AM',0,'Feb 17 2004 10:46:33:327AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(285,146,NULL,'The select button in the Action column allows you to view, modify and archive the account. Archiving makes the account invisible, but it is still in the database.',0,'Feb 17 2004 10:46:33:327AM',0,'Feb 17 2004 10:46:33:327AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(286,147,NULL,'You can download all the versions of the documents',0,'Feb 17 2004 10:46:33:357AM',0,'Feb 17 2004 10:46:33:357AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(287,148,NULL,'You can modify / update the current document information, such as the subject and the filename',0,'Feb 17 2004 10:46:33:373AM',0,'Feb 17 2004 10:46:33:373AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(288,149,NULL,'The details of the account can be modified here. The details can be saved using the Modify button.',0,'Feb 17 2004 10:46:33:390AM',0,'Feb 17 2004 10:46:33:390AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(289,150,NULL,'You can modify, delete or forward the calls using the corresponding buttons.',0,'Feb 17 2004 10:46:33:483AM',0,'Feb 17 2004 10:46:33:483AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(290,151,NULL,'The details of the new call can be saved using the save button.',0,'Feb 17 2004 10:46:33:500AM',0,'Feb 17 2004 10:46:33:500AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(291,151,NULL,'The call type can be selected from the dropdown box.',0,'Feb 17 2004 10:46:33:513AM',0,'Feb 17 2004 10:46:33:513AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(292,152,NULL,'You can modify, delete or forward the calls using the corresponding buttons.',0,'Feb 17 2004 10:46:33:530AM',0,'Feb 17 2004 10:46:33:530AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(293,153,NULL,'You can select the list of the recipients to whom you want to forward the particular call to by using the "Add Recipients" link.',0,'Feb 17 2004 10:46:33:547AM',0,'Feb 17 2004 10:46:33:547AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(294,154,NULL,'You can also view, modify and delete the opportunity associated with the contact.',0,'Feb 17 2004 10:46:33:560AM',0,'Feb 17 2004 10:46:33:560AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(295,154,NULL,'When the description of the opportunity is clicked, it will display more details about the opportunity and its components.',0,'Feb 17 2004 10:46:33:577AM',0,'Feb 17 2004 10:46:33:577AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(296,154,NULL,'Add an opportunity.',0,'Feb 17 2004 10:46:33:577AM',0,'Feb 17 2004 10:46:33:577AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(297,154,NULL,'Select an opportunity type from the drop down list.',0,'Feb 17 2004 10:46:33:593AM',0,'Feb 17 2004 10:46:33:593AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(298,155,NULL,'You can rename or delete the opportunity itself using the buttons below.',0,'Feb 17 2004 10:46:33:607AM',0,'Feb 17 2004 10:46:33:607AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(299,155,NULL,'You can modify, view and delete the details of any particular component by clicking the select button in the action column.',0,'Feb 17 2004 10:46:33:607AM',0,'Feb 17 2004 10:46:33:607AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(300,155,NULL,'You can add a new component associated with the account. It also displays the status, amount and the date when the component will be closed.',0,'Feb 17 2004 10:46:33:623AM',0,'Feb 17 2004 10:46:33:623AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(301,156,NULL,'Lets you modify or delete the ticket information',0,'Feb 17 2004 10:46:33:640AM',0,'Feb 17 2004 10:46:33:640AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(302,156,NULL,'You can view the tasks and documents related to a ticket along with the history of that document by clicking on the corresponding links.',0,'Feb 17 2004 10:46:33:640AM',0,'Feb 17 2004 10:46:33:640AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(303,157,NULL,'You can also have tasks and documents related to a ticket along with the document history.',0,'Feb 17 2004 10:46:33:670AM',0,'Feb 17 2004 10:46:33:670AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(304,157,NULL,'Lets you modify / update the ticket information.',0,'Feb 17 2004 10:46:33:670AM',0,'Feb 17 2004 10:46:33:670AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(305,158,NULL,'The details of the task can be viewed or modified by clicking on the select button under the Action column.',0,'Feb 17 2004 10:46:33:687AM',0,'Feb 17 2004 10:46:33:687AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(306,158,NULL,'You can update the task by clicking on the description of the task.',0,'Feb 17 2004 10:46:33:700AM',0,'Feb 17 2004 10:46:33:700AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(307,158,NULL,'You can add a task which is associated with the existing ticket.',0,'Feb 17 2004 10:46:33:717AM',0,'Feb 17 2004 10:46:33:717AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(308,159,NULL,'The document can be uploaded using the browse button.',0,'Feb 17 2004 10:46:33:733AM',0,'Feb 17 2004 10:46:33:733AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(309,161,NULL,'You can download all the different versions of the documents using the "Download" link in the Action column.',0,'Feb 17 2004 10:46:33:750AM',0,'Feb 17 2004 10:46:33:750AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(310,162,NULL,'The subject and the filename of the document can be modified.',0,'Feb 17 2004 10:46:33:780AM',0,'Feb 17 2004 10:46:33:780AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(311,163,NULL,'The subject and the file name can be changed. The version number is updated when an updated document is uploaded.',0,'Feb 17 2004 10:46:33:797AM',0,'Feb 17 2004 10:46:33:797AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(312,164,NULL,'The exported data can be viewed as a .csv file or in html format. The exported data can also be deleted when the select button in the action field is clicked.',0,'Feb 17 2004 10:46:33:920AM',0,'Feb 17 2004 10:46:33:920AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(313,164,NULL,'You can also choose to display a list of all the exported data in the system or the exported data created by you.',0,'Feb 17 2004 10:46:33:920AM',0,'Feb 17 2004 10:46:33:920AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(314,164,NULL,'New export data can be generated using the "Generate new export" link.',0,'Feb 17 2004 10:46:33:937AM',0,'Feb 17 2004 10:46:33:937AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(315,165,NULL,'Revenue details along with the option to modify and delete revenue.',0,'Feb 17 2004 10:46:33:950AM',0,'Feb 17 2004 10:46:33:950AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(316,167,NULL,'You can add / update an opportunity here and assign it to an employee. The opportunity can be associated with an account or a contact. Each opportunity created requires the estimate or the probability of closing the deal, the duration and the best estimate of the person following up the lead.',0,'Feb 17 2004 10:46:33:983AM',0,'Feb 17 2004 10:46:33:983AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(317,168,NULL,'You can add / update an opportunity here and assign it to an employee. The opportunity can be associated with an account or a contact. Each opportunity created requires the estimate or the probability of closing the deal, the duration and the best estimate of the person following up the lead.',0,'Feb 17 2004 10:46:34:047AM',0,'Feb 17 2004 10:46:34:047AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(318,169,NULL,'An opportunity can be renamed or deleted using the buttons present at the bottom of the page',0,'Feb 17 2004 10:46:34:140AM',0,'Feb 17 2004 10:46:34:140AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(319,169,NULL,'Clicking on the select button lets you view, modify or delete the details about the component',0,'Feb 17 2004 10:46:34:170AM',0,'Feb 17 2004 10:46:34:170AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(320,169,NULL,'Clicking on the name of the component would show the details about the component',0,'Feb 17 2004 10:46:34:200AM',0,'Feb 17 2004 10:46:34:200AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(321,169,NULL,'Add a new component which is associated with the account.',0,'Feb 17 2004 10:46:34:217AM',0,'Feb 17 2004 10:46:34:217AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(322,170,NULL,'The description of the opportunity can be changed using the update button.',0,'Feb 17 2004 10:46:34:233AM',0,'Feb 17 2004 10:46:34:233AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(323,171,NULL,'You can modify and delete the opportunity created using the modify and the delete buttons',0,'Feb 17 2004 10:46:34:250AM',0,'Feb 17 2004 10:46:34:250AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(324,172,NULL,'The component type can be selected using the ?select ? link',0,'Feb 17 2004 10:46:34:263AM',0,'Feb 17 2004 10:46:34:263AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(325,172,NULL,'You can assign the component to any of the employee present using the dropdown list present.',0,'Feb 17 2004 10:46:34:263AM',0,'Feb 17 2004 10:46:34:263AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(326,173,NULL,'The component type can be selected using the ?select ? link',0,'Feb 17 2004 10:46:34:297AM',0,'Feb 17 2004 10:46:34:297AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(327,173,NULL,'You can assign the component to any of the employee present using the dropdown list present.',0,'Feb 17 2004 10:46:34:310AM',0,'Feb 17 2004 10:46:34:310AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(328,173,NULL,'Clicking the update button can save the changes made to the component',0,'Feb 17 2004 10:46:34:310AM',0,'Feb 17 2004 10:46:34:310AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(329,174,NULL,'You can modify and delete the opportunity created using the modify and the delete buttons',0,'Feb 17 2004 10:46:34:327AM',0,'Feb 17 2004 10:46:34:327AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(330,175,NULL,'Clicking the select button under the action column gives you the option to view the details about the campaign, download the mail merge and also lets you to export it to Excel.',0,'Feb 17 2004 10:46:34:373AM',0,'Feb 17 2004 10:46:34:373AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(331,175,NULL,'Clicking on the campaign name gives you complete details about the campaign.',0,'Feb 17 2004 10:46:34:390AM',0,'Feb 17 2004 10:46:34:390AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(332,175,NULL,'You can display the campaigns created and their details using three different views by selecting from the drop down list.',0,'Feb 17 2004 10:46:34:390AM',0,'Feb 17 2004 10:46:34:390AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(333,176,NULL,'This creates a new Campaign. This takes in both the campaign and its description.',0,'Feb 17 2004 10:46:34:403AM',0,'Feb 17 2004 10:46:34:403AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(334,177,NULL,'You can view, modify and delete details by clicking the select button under the action column.',0,'Feb 17 2004 10:46:34:420AM',0,'Feb 17 2004 10:46:34:420AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(335,177,NULL,'For each of the campaign, the groups, message and delivery columns show whether they are complete or not. Clicking on these will help you choose the group, message and the delivery date.',0,'Feb 17 2004 10:46:34:437AM',0,'Feb 17 2004 10:46:34:437AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(336,177,NULL,'Clicking the name of the campaign shows you more details about the campaign and also shows the list of the things to be selected before a campaign can be activated',0,'Feb 17 2004 10:46:34:437AM',0,'Feb 17 2004 10:46:34:437AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(337,177,NULL,'You can view your incomplete campaigns or all the incomplete campaigns. You can select the view with the drop down list at the top.',0,'Feb 17 2004 10:46:34:450AM',0,'Feb 17 2004 10:46:34:450AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(338,177,NULL,'Add a campaign',0,'Feb 17 2004 10:46:34:450AM',0,'Feb 17 2004 10:46:34:450AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(339,178,NULL,'You can also click the select button under the Action column for viewing, modifying or deleting the details.',0,'Feb 17 2004 10:46:34:467AM',0,'Feb 17 2004 10:46:34:467AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(340,178,NULL,'Clicking the group name will show the list of contacts present in the group.',0,'Feb 17 2004 10:46:34:483AM',0,'Feb 17 2004 10:46:34:483AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(341,178,NULL,'Add a contact group using the link "Add a Contact Group".',0,'Feb 17 2004 10:46:34:483AM',0,'Feb 17 2004 10:46:34:483AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(342,178,NULL,'You can filter the list of groups displayed by selecting from the drop down.',0,'Feb 17 2004 10:46:34:500AM',0,'Feb 17 2004 10:46:34:500AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(343,179,NULL,'You can preview the details of the group by clicking on the preview button.',0,'Feb 17 2004 10:46:34:513AM',0,'Feb 17 2004 10:46:34:513AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(344,179,NULL,'You can also select from the list of "Selected criteria and contacts" and remove them by clicking the remove button.',0,'Feb 17 2004 10:46:34:513AM',0,'Feb 17 2004 10:46:34:513AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(345,179,NULL,'You can define the criteria to generate the list by using the different filters present and then add them to the "Selected criteria and contacts".',0,'Feb 17 2004 10:46:34:530AM',0,'Feb 17 2004 10:46:34:530AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(346,179,NULL,'You can select the criteria for the group to be created. Clicking the "Add/Remove Contacts" can choose the specific contacts.',0,'Feb 17 2004 10:46:34:547AM',0,'Feb 17 2004 10:46:34:547AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(347,180,NULL,'You can view, modify, clone or delete each of the messages.',0,'Feb 17 2004 10:46:34:560AM',0,'Feb 17 2004 10:46:34:560AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(348,180,NULL,'The dropdown list acts as filters for displaying the messages that meet certain criteria.',0,'Feb 17 2004 10:46:34:560AM',0,'Feb 17 2004 10:46:34:560AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(349,180,NULL,'Clicking on the message name will show details about the message, which can be updated.',0,'Feb 17 2004 10:46:34:577AM',0,'Feb 17 2004 10:46:34:577AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(350,180,NULL,'Add a new message',0,'Feb 17 2004 10:46:34:577AM',0,'Feb 17 2004 10:46:34:577AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(351,181,NULL,'The new message can be saved by clicking the save message button.',0,'Feb 17 2004 10:46:34:593AM',0,'Feb 17 2004 10:46:34:593AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(352,181,NULL,'The permissions or the access type for the message can be chosen from drop down box.',0,'Feb 17 2004 10:46:34:607AM',0,'Feb 17 2004 10:46:34:607AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(353,182,NULL,'Clicking on the "surveys" will let you create new interactive surveys.',0,'Feb 17 2004 10:46:34:623AM',0,'Feb 17 2004 10:46:34:623AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(354,183,NULL,'You can use the preview button to view the details about the contacts in a group.',0,'Feb 17 2004 10:46:34:640AM',0,'Feb 17 2004 10:46:34:640AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(355,183,NULL,'You can modify or delete a group.',0,'Feb 17 2004 10:46:34:640AM',0,'Feb 17 2004 10:46:34:640AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(356,184,NULL,'You can change the version of the document when ever an updated document is uploaded.',0,'Feb 17 2004 10:46:34:750AM',0,'Feb 17 2004 10:46:34:750AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(357,188,NULL,'You can browse to select a new document to upload if its related to the campaign.',0,'Feb 17 2004 10:46:34:797AM',0,'Feb 17 2004 10:46:34:797AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(358,190,NULL,'You can also go back from the current detailed view to the group details criteria.',0,'Feb 17 2004 10:46:34:810AM',0,'Feb 17 2004 10:46:34:810AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(359,191,NULL,'You can download from the list of documents available by using the "download" link under the action column.',0,'Feb 17 2004 10:46:34:857AM',0,'Feb 17 2004 10:46:34:857AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(360,193,NULL,'The name of the survey is a mandatory field for creating a survey. A description, introduction and thank-you note can also be added.',0,'Feb 17 2004 10:46:34:890AM',0,'Feb 17 2004 10:46:34:890AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(361,195,NULL,'You can download the mail merge shown at the bottom of the details.',0,'Feb 17 2004 10:46:34:903AM',0,'Feb 17 2004 10:46:34:903AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(362,196,NULL,'Different versions of the document can be downloaded using the "download" link in the action column.',0,'Feb 17 2004 10:46:34:920AM',0,'Feb 17 2004 10:46:34:920AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(363,201,NULL,'You can update the campaign schedule by filling in the run date and the delivery method whether it''s an email, fax or letter or any other method.',0,'Feb 17 2004 10:46:34:983AM',0,'Feb 17 2004 10:46:34:983AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(364,202,NULL,'You can also generate a list of contacts be selecting from the filters and adding them to the "selected criteria and contacts" list.',0,'Feb 17 2004 10:46:35:000AM',0,'Feb 17 2004 10:46:35:000AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(365,202,NULL,'You can choose the contacts in the group using the "Add / Remove contacts" link present.',0,'Feb 17 2004 10:46:35:000AM',0,'Feb 17 2004 10:46:35:000AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(366,202,NULL,'You can update the name of the group',0,'Feb 17 2004 10:46:35:013AM',0,'Feb 17 2004 10:46:35:013AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(367,204,NULL,'There can be multiple attachments to a single message. The attachment that needs to be downloaded has to be selected first and then downloaded.',0,'Feb 17 2004 10:46:35:047AM',0,'Feb 17 2004 10:46:35:047AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(368,208,NULL,'The details of the documents can be viewed or modified by clicking on the select button under the Action column.',0,'Feb 17 2004 10:46:35:093AM',0,'Feb 17 2004 10:46:35:093AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(369,208,NULL,'You can view the details, modify, download or delete the documents associated with the account.',0,'Feb 17 2004 10:46:35:093AM',0,'Feb 17 2004 10:46:35:093AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(370,208,NULL,'A new document can be added to the account.',0,'Feb 17 2004 10:46:35:107AM',0,'Feb 17 2004 10:46:35:107AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(371,208,NULL,'The document versions can be updated by using the "add version" link.',0,'Feb 17 2004 10:46:35:107AM',0,'Feb 17 2004 10:46:35:107AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(372,209,NULL,'The name of the campaign can be changed or deleted by using the buttons at the bottom of the page.',0,'Feb 17 2004 10:46:35:140AM',0,'Feb 17 2004 10:46:35:140AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(373,209,NULL,'You can choose a group / groups, a message for the campaign, and a delivery date for the campaign to start. You can also add attachments to the messages you send to recipients.',0,'Feb 17 2004 10:46:35:217AM',0,'Feb 17 2004 10:46:35:217AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(374,210,NULL,'You can check the groups you want for the current campaign.',0,'Feb 17 2004 10:46:35:233AM',0,'Feb 17 2004 10:46:35:233AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(375,210,NULL,'You can also add attachments to the messages you send to recipients by clicking the preview recipient''s link next to each group.',0,'Feb 17 2004 10:46:35:233AM',0,'Feb 17 2004 10:46:35:233AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(376,210,NULL,'You can view all the groups present or the groups created by you just by choosing from the drop down box.',0,'Feb 17 2004 10:46:35:250AM',0,'Feb 17 2004 10:46:35:250AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(377,211,NULL,'You can select a message for this campaign from the dropdown list of all the messages or just your messages.',0,'Feb 17 2004 10:46:35:263AM',0,'Feb 17 2004 10:46:35:263AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(378,211,NULL,'The messages can be of multiple types, which can be used as filters and can be selected from the drop down list. For each type you have further classification.',0,'Feb 17 2004 10:46:35:280AM',0,'Feb 17 2004 10:46:35:280AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(379,212,NULL,'The attachments configured are the surveys or the file attachments. Using the links "change survey" and "change file attachments", you can change either of them.',0,'Feb 17 2004 10:46:35:297AM',0,'Feb 17 2004 10:46:35:297AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(380,213,NULL,'You can view and select from all, or only your own surveys.',0,'Feb 17 2004 10:46:35:310AM',0,'Feb 17 2004 10:46:35:310AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(381,214,NULL,'You can download or remove the file name. You can also upload files using the browse button.',0,'Feb 17 2004 10:46:35:327AM',0,'Feb 17 2004 10:46:35:327AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(382,215,NULL,'The name and the description of the campaign can be changed.',0,'Feb 17 2004 10:46:35:343AM',0,'Feb 17 2004 10:46:35:343AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(383,216,NULL,'You can modify, delete or clone the message details by clicking on corresponding buttons.',0,'Feb 17 2004 10:46:35:357AM',0,'Feb 17 2004 10:46:35:357AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(384,217,NULL,'You can select font properties for the text of the message along with the size and indentation.',0,'Feb 17 2004 10:46:35:373AM',0,'Feb 17 2004 10:46:35:373AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(385,217,NULL,'The name of the message and the access type can be given, which specifies who can view the message.',0,'Feb 17 2004 10:46:35:390AM',0,'Feb 17 2004 10:46:35:390AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(386,218,NULL,'You can modify, delete or clone the message details by clicking on corresponding buttons.',0,'Feb 17 2004 10:46:35:403AM',0,'Feb 17 2004 10:46:35:403AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(387,219,NULL,'You can select font properties for the text of the message along with the size and indentation.',0,'Feb 17 2004 10:46:35:437AM',0,'Feb 17 2004 10:46:35:437AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(388,219,NULL,'The name of the message and the access type can be given, which specifies who can view the message.',0,'Feb 17 2004 10:46:35:437AM',0,'Feb 17 2004 10:46:35:437AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(389,220,NULL,'You can also view, modify and delete the details of a survey.',0,'Feb 17 2004 10:46:35:467AM',0,'Feb 17 2004 10:46:35:467AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(390,220,NULL,'Clicking on the name of the survey shows its details.',0,'Feb 17 2004 10:46:35:593AM',0,'Feb 17 2004 10:46:35:593AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(391,220,NULL,'Add a new survey',0,'Feb 17 2004 10:46:35:607AM',0,'Feb 17 2004 10:46:35:607AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(392,220,NULL,'You can view all or your own surveys using the drop down list.',0,'Feb 17 2004 10:46:35:623AM',0,'Feb 17 2004 10:46:35:623AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(393,221,NULL,'The "Save & Add" button saves the current question and lets you add another one immediately.',0,'Feb 17 2004 10:46:35:653AM',0,'Feb 17 2004 10:46:35:653AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(394,221,NULL,'You can also specify whether the particular question is required or not by checking the checkbox.',0,'Feb 17 2004 10:46:35:653AM',0,'Feb 17 2004 10:46:35:653AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(395,221,NULL,'If the selected question type is "Item List", then an Edit button is enabled which helps in adding new elements to the existing list.',0,'Feb 17 2004 10:46:35:670AM',0,'Feb 17 2004 10:46:35:670AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(396,221,NULL,'A new question type can be selected through the drop down list.',0,'Feb 17 2004 10:46:35:670AM',0,'Feb 17 2004 10:46:35:670AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(397,222,NULL,'The preview button shows you the survey questions in a pop-up window.',0,'Feb 17 2004 10:46:35:700AM',0,'Feb 17 2004 10:46:35:700AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(398,222,NULL,'You can modify, delete, and preview the survey details using the buttons at the top of the page.',0,'Feb 17 2004 10:46:35:700AM',0,'Feb 17 2004 10:46:35:700AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(399,222,NULL,'You can view the survey introduction text, the questions and the thank-you text.',0,'Feb 17 2004 10:46:35:700AM',0,'Feb 17 2004 10:46:35:700AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(400,223,NULL,'You can add questions to the survey here.',0,'Feb 17 2004 10:46:35:733AM',0,'Feb 17 2004 10:46:35:733AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(401,223,NULL,'Clicking the "Done" button can save the survey and you can also traverse back by clicking the "Back" button.',0,'Feb 17 2004 10:46:35:733AM',0,'Feb 17 2004 10:46:35:733AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(402,223,NULL,'The survey questions can be moved up or down using the "Up" or "Down" links present in the action field.',0,'Feb 17 2004 10:46:35:733AM',0,'Feb 17 2004 10:46:35:733AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(403,223,NULL,'You can edit or delete any of the survey questions using the "edit" or "del" link under the action field.',0,'Feb 17 2004 10:46:35:750AM',0,'Feb 17 2004 10:46:35:750AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(404,223,NULL,'You can add new survey questions here.',0,'Feb 17 2004 10:46:35:763AM',0,'Feb 17 2004 10:46:35:763AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(405,224,NULL,'You can click on "Create Attachments" and include interactive items, like surveys, or provide additional materials like files.',0,'Feb 17 2004 10:46:35:780AM',0,'Feb 17 2004 10:46:35:780AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(406,224,NULL,'Clicking the "Create Message"  link lets you compose a message to reach your audience.',0,'Feb 17 2004 10:46:35:780AM',0,'Feb 17 2004 10:46:35:780AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(407,224,NULL,'You can click the "Build Groups" link to assemble dynamic distribution of groups. Each campaign needs at least one group to send a message to.',0,'Feb 17 2004 10:46:35:797AM',0,'Feb 17 2004 10:46:35:797AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(408,224,NULL,'The "Campaign Builder" can be clicked to select groups of contacts that you would like to send a message to, schedule a delivery date, etc.',0,'Feb 17 2004 10:46:35:797AM',0,'Feb 17 2004 10:46:35:797AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(409,224,NULL,'You can click on the "Dashboard" to view the sent messages and to drill down and view recipients and survey results.',0,'Feb 17 2004 10:46:35:810AM',0,'Feb 17 2004 10:46:35:810AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(410,225,NULL,'Lets you modify or delete ticket information',0,'Feb 17 2004 10:46:35:903AM',0,'Feb 17 2004 10:46:35:903AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(411,225,NULL,'You can also store tasks and documents related to a ticket.',0,'Feb 17 2004 10:46:35:920AM',0,'Feb 17 2004 10:46:35:920AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(412,226,NULL,'For each new ticket you can select the organization, the contact and also the issue for which the ticket is being created. The assignment and the resolution of the ticket can also be entered.',0,'Feb 17 2004 10:46:35:937AM',0,'Feb 17 2004 10:46:35:937AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(413,227,NULL,'The search can be done based on different parameters like the ticket number, account associated, priority, employee whom the ticket is assigned etc.',0,'Feb 17 2004 10:46:35:950AM',0,'Feb 17 2004 10:46:35:950AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(414,228,NULL,'Clicking on the subject of the exported data shows you the details of the ticket like the ticket ID, the organization and its issue (why the particular ticket was generated).',0,'Feb 17 2004 10:46:35:967AM',0,'Feb 17 2004 10:46:35:967AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(415,228,NULL,'Clicking on the select button under the action column lets you view the data, download the data in .CSV format or delete the data.',0,'Feb 17 2004 10:46:35:967AM',0,'Feb 17 2004 10:46:35:967AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(416,228,NULL,'You can filter the exported date generated, by you or by all employees using the dropdown list.',0,'Feb 17 2004 10:46:35:983AM',0,'Feb 17 2004 10:46:35:983AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(417,228,NULL,'You can generate a new exported data by clicking the link "Generate new export".',0,'Feb 17 2004 10:46:36:000AM',0,'Feb 17 2004 10:46:36:000AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(418,229,NULL,'You can save the details of the modified ticket by clicking the "Update" button.',0,'Feb 17 2004 10:46:36:013AM',0,'Feb 17 2004 10:46:36:013AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(419,230,NULL,'You can save the details of the modified ticket by clicking the "Update" button.',0,'Feb 17 2004 10:46:36:030AM',0,'Feb 17 2004 10:46:36:030AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(420,231,NULL,'The details of the task can be viewed or modified by clicking on the select button under the Action column.',0,'Feb 17 2004 10:46:36:047AM',0,'Feb 17 2004 10:46:36:047AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(421,231,NULL,'You can update the task by clicking on the description of the task.',0,'Feb 17 2004 10:46:36:047AM',0,'Feb 17 2004 10:46:36:047AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(422,231,NULL,'You can add a task which is associated with the existing ticket.',0,'Feb 17 2004 10:46:36:060AM',0,'Feb 17 2004 10:46:36:060AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(423,232,NULL,'The details of the documents can be viewed or modified by clicking on the select button under the Action column.',0,'Feb 17 2004 10:46:36:077AM',0,'Feb 17 2004 10:46:36:077AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(424,232,NULL,'You can view the details, modify, download or delete the documents associated with the ticket.',0,'Feb 17 2004 10:46:36:077AM',0,'Feb 17 2004 10:46:36:077AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(425,232,NULL,'A new document associated with the ticket can be added.',0,'Feb 17 2004 10:46:36:093AM',0,'Feb 17 2004 10:46:36:093AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(426,232,NULL,'The document versions can be updated by using the "add version" link.',0,'Feb 17 2004 10:46:36:107AM',0,'Feb 17 2004 10:46:36:107AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(427,233,NULL,'A new record is added into the folder using the link "Add a record to this folder". Multiple records can be added to this folder if the folder has the necessary settings.',0,'Feb 17 2004 10:46:36:123AM',0,'Feb 17 2004 10:46:36:123AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(428,233,NULL,'You can select the custom folder using the drop down list.',0,'Feb 17 2004 10:46:36:123AM',0,'Feb 17 2004 10:46:36:123AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(429,234,NULL,'The details are saved by clicking the save button.',0,'Feb 17 2004 10:46:36:153AM',0,'Feb 17 2004 10:46:36:153AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(430,235,NULL,'A chronological history of all actions associated with a ticket is maintined.',0,'Feb 17 2004 10:46:36:170AM',0,'Feb 17 2004 10:46:36:170AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(431,243,NULL,'The changes can be saved using the "Update" button.',0,'Feb 17 2004 10:46:36:250AM',0,'Feb 17 2004 10:46:36:250AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(432,244,NULL,'You can modify the folder information along with the record details by clicking on the Modify button.',0,'Feb 17 2004 10:46:36:263AM',0,'Feb 17 2004 10:46:36:263AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(433,247,NULL,'The document can be uploaded using the browse button.',0,'Feb 17 2004 10:46:36:373AM',0,'Feb 17 2004 10:46:36:373AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(434,248,NULL,'You can download all the versions of a document.',0,'Feb 17 2004 10:46:36:390AM',0,'Feb 17 2004 10:46:36:390AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(435,249,NULL,'You can also have tasks and documents related to a ticket.',0,'Feb 17 2004 10:46:36:403AM',0,'Feb 17 2004 10:46:36:403AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(436,249,NULL,'Lets you modify / update the ticket information.',0,'Feb 17 2004 10:46:36:420AM',0,'Feb 17 2004 10:46:36:420AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(437,250,NULL,'A new version of a file can be uploaded using the browse button.',0,'Feb 17 2004 10:46:36:437AM',0,'Feb 17 2004 10:46:36:437AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(438,251,NULL,'You can delete a record by clicking on "Del" next to the record.',0,'Feb 17 2004 10:46:36:450AM',0,'Feb 17 2004 10:46:36:450AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(439,251,NULL,'You can add a record by clicking on "Add Ticket".',0,'Feb 17 2004 10:46:36:467AM',0,'Feb 17 2004 10:46:36:467AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(440,251,NULL,'You can view more records in a particular section by clicking "Show More".',0,'Feb 17 2004 10:46:36:467AM',0,'Feb 17 2004 10:46:36:467AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(441,251,NULL,'You can view more details by clicking on the record.',0,'Feb 17 2004 10:46:36:483AM',0,'Feb 17 2004 10:46:36:483AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(442,251,NULL,'You can update a record by clicking on "Edit" next to the record.',0,'Feb 17 2004 10:46:36:483AM',0,'Feb 17 2004 10:46:36:483AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(443,252,NULL,'A new detailed employee record can be added.',0,'Feb 17 2004 10:46:36:560AM',0,'Feb 17 2004 10:46:36:560AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(444,252,NULL,'The details of each employee can be viewed, modified or deleted using the select button in the action column.',0,'Feb 17 2004 10:46:36:560AM',0,'Feb 17 2004 10:46:36:560AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(445,253,NULL,'You can modify or delete the employee details using the modify or delete buttons.',0,'Feb 17 2004 10:46:36:577AM',0,'Feb 17 2004 10:46:36:577AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(446,254,NULL,'The "Save" button saves the details of the employee entered.',0,'Feb 17 2004 10:46:36:623AM',0,'Feb 17 2004 10:46:36:623AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(447,254,NULL,'The "Save & New" button lets you to save the details of one employee and enter another employee in one operation.',0,'Feb 17 2004 10:46:36:623AM',0,'Feb 17 2004 10:46:36:623AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(448,255,NULL,'Clicking on the update button saves the modified details of the employee.',0,'Feb 17 2004 10:46:36:640AM',0,'Feb 17 2004 10:46:36:640AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(449,256,NULL,'The employee record can be modified or deleted from the system completely.',0,'Feb 17 2004 10:46:36:653AM',0,'Feb 17 2004 10:46:36:653AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(450,257,NULL,'You can cancel the reports that are scheduled to be processed by the server by the clicking the select button.',0,'Feb 17 2004 10:46:36:700AM',0,'Feb 17 2004 10:46:36:700AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(451,257,NULL,'The generated reports can be deleted or viewed/downloaded in .pdf format by clicking the select button under the action column.',0,'Feb 17 2004 10:46:36:700AM',0,'Feb 17 2004 10:46:36:700AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(452,257,NULL,'Add a new report',0,'Feb 17 2004 10:46:36:717AM',0,'Feb 17 2004 10:46:36:717AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(453,258,NULL,'There are four different modules and you can click on the module where you want to generate the report.',0,'Feb 17 2004 10:46:36:750AM',0,'Feb 17 2004 10:46:36:750AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(454,261,NULL,'You can use the "generate report" button to run the report.',0,'Feb 17 2004 10:46:36:780AM',0,'Feb 17 2004 10:46:36:780AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(455,261,NULL,'If the parameters exist, you can specify the name of the criteria for future reference and click the check box present at the bottom of the page.',0,'Feb 17 2004 10:46:36:780AM',0,'Feb 17 2004 10:46:36:780AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(456,262,NULL,'You can run the report by clicking on the title of the report.',0,'Feb 17 2004 10:46:36:810AM',0,'Feb 17 2004 10:46:36:810AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(457,263,NULL,'If the criteria are present, select the criteria, then continue to enter the parameters to run the report.',0,'Feb 17 2004 10:46:36:827AM',0,'Feb 17 2004 10:46:36:827AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(458,264,NULL,'You can view the queue either by using the link in the text or using the view queue button.',0,'Feb 17 2004 10:46:36:920AM',0,'Feb 17 2004 10:46:36:920AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(459,265,NULL,'You can cancel the report that is scheduled to be processed by the server by clicking the select button and selecting "Cancel".',0,'Feb 17 2004 10:46:36:937AM',0,'Feb 17 2004 10:46:36:937AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(460,265,NULL,'You can view the reports generated, download them or delete them by clicking on the select button under the action column.',0,'Feb 17 2004 10:46:36:937AM',0,'Feb 17 2004 10:46:36:937AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(461,265,NULL,'A new report can be generated by clicking on the link "Add a Report".',0,'Feb 17 2004 10:46:36:950AM',0,'Feb 17 2004 10:46:36:950AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(462,266,NULL,'The alphabetical slide rule allows users to be listed based on their last name. Simply click on the starting letter desired.',0,'Feb 17 2004 10:46:37:000AM',0,'Feb 17 2004 10:46:37:000AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(463,266,NULL,'The columns "Name", ''Username" and "Role" can be clicked to display the users in the ascending or descending order of the chosen criteria.',0,'Feb 17 2004 10:46:37:000AM',0,'Feb 17 2004 10:46:37:000AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(464,266,NULL,'The "Add New User" link opens a window that allows the administrator to add new users.',0,'Feb 17 2004 10:46:37:000AM',0,'Feb 17 2004 10:46:37:000AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(465,266,NULL,'The "select" buttons in the "Action" column alongside the name of each user opens a pop-up menu that provides the administrator with options to view more information, modify user information, or disable (or inactivate) the user.',0,'Feb 17 2004 10:46:37:000AM',0,'Feb 17 2004 10:46:37:000AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(466,266,NULL,'The list is displayed with 10 names per page by default. Additional items in the list may be viewed by clicking on the "Previous" and "Next" navigation links at the bottom of the table or by changing the number of items to be displayed per page.',0,'Feb 17 2004 10:46:37:013AM',0,'Feb 17 2004 10:46:37:013AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(467,266,NULL,'The users of the CRM system are listed in alphabetical order. Their user name, role and who they report to are also listed to provide a quick overview of information for each user.',0,'Feb 17 2004 10:46:37:013AM',0,'Feb 17 2004 10:46:37:013AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(468,266,NULL,'The drop list provides a filter to either view only the active or only the inactive users. Inactive users are those who do not have the privilege to use the system currently either because their user names have been disabled or they have expired. These users may be activated (enabled) at a later time.',0,'Feb 17 2004 10:46:37:030AM',0,'Feb 17 2004 10:46:37:030AM',NULL,NULL,1,7)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(469,267,NULL,'The ''Reports To" field allows the administrator to setup a user hierarchy. The drop list displays all the users of the system and allows one to be chosen.',0,'Feb 17 2004 10:46:37:047AM',0,'Feb 17 2004 10:46:37:047AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(470,267,NULL,'The "Role" drop list allows a role to be associated with a user. This association determines the privileges the user may have when he accesses the system.',0,'Feb 17 2004 10:46:37:047AM',0,'Feb 17 2004 10:46:37:047AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(471,267,NULL,'The "Password" fields allows the administrator to setup a password for the user. The password is used along with the Username to login to the system. Since the password is stored in encrypted form and cannot be interpreted, the administrator is asked to confirm the users password. The user may subsequently change his password according to personal preferences.',0,'Feb 17 2004 10:46:37:060AM',0,'Feb 17 2004 10:46:37:060AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(472,267,NULL,'The Username is the phrase that is used by the user to login to the system. It must be unique.',0,'Feb 17 2004 10:46:37:077AM',0,'Feb 17 2004 10:46:37:077AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(473,267,NULL,'An "Expire Date" may be set for each user after which the user is disabled. If this field is left blank the user is active indefinitely. This date can either be typed in the mm/dd/yyyy format or chosen from a calendar that can be accessed from the icon at the right of the field.',0,'Feb 17 2004 10:46:37:077AM',0,'Feb 17 2004 10:46:37:077AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(474,267,NULL,'The contact field allows the administrator to associate contact information with the user. The administrator may either create new contact information or choose one from the existing list of contacts. This information provides the administrator with the user''s e-mail, telephone and (or) fax number, postal address and any other information that may help the administrator or the system manager to contact the user.
',0,'Feb 17 2004 10:46:37:093AM',0,'Feb 17 2004 10:46:37:093AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(475,268,NULL,'The "Cancel" button allows current and uncommitted changes to be undone.',0,'Feb 17 2004 10:46:37:107AM',0,'Feb 17 2004 10:46:37:107AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(476,268,NULL,'When the "Generate new password" field is checked, the system constructs a password for the user and uses the contact information to email the new password to the user.',0,'Feb 17 2004 10:46:37:123AM',0,'Feb 17 2004 10:46:37:123AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(477,268,NULL,'The "Disable" button provides a quick link to the administrator to disable the user.',0,'Feb 17 2004 10:46:37:123AM',0,'Feb 17 2004 10:46:37:123AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(478,268,NULL,'The "Username", "Role", "Reports To" and password of the user are editable. For more information about each of these fields see help on "Add user".',0,'Feb 17 2004 10:46:37:140AM',0,'Feb 17 2004 10:46:37:140AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(479,269,NULL,'The list is displayed by default with 10 items per page, additional items in the login history may be viewed by clicking on the "Previous" and "Next" navigation links at the bottom of the table or by changing the number of items to be displayed on a page.',0,'Feb 17 2004 10:46:37:170AM',0,'Feb 17 2004 10:46:37:170AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(480,269,NULL,'The login history of the user displays the IP address of the computer from which the user logged in, and the date/time when the user logged in.',0,'Feb 17 2004 10:46:37:187AM',0,'Feb 17 2004 10:46:37:187AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(481,270,NULL,'Clicking on the select button under the action column would let you to view the details about the viewpoint also modify them.',0,'Feb 17 2004 10:46:37:200AM',0,'Feb 17 2004 10:46:37:200AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(482,270,NULL,'You can click on the contact under the viewpoint column to know more details about that viewpoint and its permissions.',0,'Feb 17 2004 10:46:37:200AM',0,'Feb 17 2004 10:46:37:200AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(483,270,NULL,'You can add a new viewpoint using the link "Add New Viewpoint".',0,'Feb 17 2004 10:46:37:217AM',0,'Feb 17 2004 10:46:37:217AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(484,271,NULL,'You can add a new viewpoint by any employee by clicking the add button.',0,'Feb 17 2004 10:46:37:233AM',0,'Feb 17 2004 10:46:37:233AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(485,271,NULL,'The permissions for the different modules can be given by checking the Access checkbox.',0,'Feb 17 2004 10:46:37:233AM',0,'Feb 17 2004 10:46:37:233AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(486,271,NULL,'The contact can be selected and removed using the links "change contact" and "clear contact".',0,'Feb 17 2004 10:46:37:250AM',0,'Feb 17 2004 10:46:37:250AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(487,272,NULL,'The details can be updated using the update button.',0,'Feb 17 2004 10:46:37:263AM',0,'Feb 17 2004 10:46:37:263AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(488,272,NULL,'You can also set the permissions to access different modules by checking the check box under the Access column.',0,'Feb 17 2004 10:46:37:280AM',0,'Feb 17 2004 10:46:37:280AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(489,272,NULL,'You can enable the viewpoint by checking the "Enabled" checkbox.',0,'Feb 17 2004 10:46:37:280AM',0,'Feb 17 2004 10:46:37:280AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(490,273,NULL,'You can also click the select button under the action column to view or modify the details of roles.',0,'Feb 17 2004 10:46:37:297AM',0,'Feb 17 2004 10:46:37:297AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(491,273,NULL,'Clicking on the role name gives you details about the role and the permissions it provides.',0,'Feb 17 2004 10:46:37:310AM',0,'Feb 17 2004 10:46:37:310AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(492,273,NULL,'You can add a new role into the system.',0,'Feb 17 2004 10:46:37:310AM',0,'Feb 17 2004 10:46:37:310AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(493,274,NULL,'Clicking the update button updates the role.',0,'Feb 17 2004 10:46:37:343AM',0,'Feb 17 2004 10:46:37:343AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(494,275,NULL,'The update of the role can be done by clicking the update button.',0,'Feb 17 2004 10:46:37:357AM',0,'Feb 17 2004 10:46:37:357AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(495,276,NULL,'Clicking on the module name will display a list of module items that can be configured.',0,'Feb 17 2004 10:46:37:390AM',0,'Feb 17 2004 10:46:37:390AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(496,277,NULL,'Scheduled Events: A timer triggers a customizable workflow process.',0,'Feb 17 2004 10:46:37:403AM',0,'Feb 17 2004 10:46:37:403AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(497,277,NULL,'Object Events: An Action triggers customizable workflow process. For example, when an object is inserted, updated, deleted or selected, a process is triggered.',0,'Feb 17 2004 10:46:37:467AM',0,'Feb 17 2004 10:46:37:467AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(498,277,NULL,'Categories: This lets you create hierarchical categories for a specific feature in the module.',0,'Feb 17 2004 10:46:37:483AM',0,'Feb 17 2004 10:46:37:483AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(499,277,NULL,'Lookup Lists: You can view the drop-down lists used in the module and make changes.',0,'Feb 17 2004 10:46:37:500AM',0,'Feb 17 2004 10:46:37:500AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(500,277,NULL,'Custom Folders and Fields: Custom folders allows you to create forms that will be present within each module, essentially custom fields.',0,'Feb 17 2004 10:46:37:513AM',0,'Feb 17 2004 10:46:37:513AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(501,278,NULL,'You can create a new item type using the add button and add it to the existing list. You can position the item in the list using the up and down buttons, remove it using the remove button and also sort the list. The final changes can be saved using the "Save Changes" button.',0,'Feb 17 2004 10:46:37:530AM',0,'Feb 17 2004 10:46:37:530AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(502,279,NULL,'You can update the existing the folder, set the options for the records and the permissions for the users.',0,'Feb 17 2004 10:46:37:547AM',0,'Feb 17 2004 10:46:37:547AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(503,280,NULL,'You can update the existing the folder, set the options for the records and the permissions for the users.',0,'Feb 17 2004 10:46:37:560AM',0,'Feb 17 2004 10:46:37:560AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(504,281,NULL,'The "Edit" link will let you alter the time for which the users session ends.',0,'Feb 17 2004 10:46:37:577AM',0,'Feb 17 2004 10:46:37:577AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(505,282,NULL,'The time out can be set by selecting the time from the drop down and clicking the update button.',0,'Feb 17 2004 10:46:37:593AM',0,'Feb 17 2004 10:46:37:593AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(506,283,NULL,'The usage can be displayed for the current date or a custom date can be specified. This can be selected from the drop down of the date range.',0,'Feb 17 2004 10:46:37:623AM',0,'Feb 17 2004 10:46:37:623AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(507,283,NULL,'The start date and the end date can be specified if the date range is "custom date range". The update can be done using the update button.',0,'Feb 17 2004 10:46:37:623AM',0,'Feb 17 2004 10:46:37:623AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(508,286,NULL,'You can also enable or disable the custom folders by clicking "yes" or "no".',0,'Feb 17 2004 10:46:37:653AM',0,'Feb 17 2004 10:46:37:653AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(509,286,NULL,'Clicking on the custom folder will give details about that folder and also lets you add groups.',0,'Feb 17 2004 10:46:37:670AM',0,'Feb 17 2004 10:46:37:670AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(510,286,NULL,'You can update an existing folder using the edit button under the action column.',0,'Feb 17 2004 10:46:37:670AM',0,'Feb 17 2004 10:46:37:670AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(511,286,NULL,'You can update an existing folder using the edit button under the action column.',0,'Feb 17 2004 10:46:37:687AM',0,'Feb 17 2004 10:46:37:687AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(512,286,NULL,'Add a folder to the general contacts module.',0,'Feb 17 2004 10:46:37:687AM',0,'Feb 17 2004 10:46:37:687AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(513,287,NULL,'You can also enable or disable the custom folders by clicking "yes" or "no".',0,'Feb 17 2004 10:46:37:717AM',0,'Feb 17 2004 10:46:37:717AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(514,287,NULL,'You can update an existing folder using the edit button under the action column.',0,'Feb 17 2004 10:46:37:733AM',0,'Feb 17 2004 10:46:37:733AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(515,287,NULL,'Clicking on the custom folder will give details about that folder and also lets you add groups.',0,'Feb 17 2004 10:46:37:750AM',0,'Feb 17 2004 10:46:37:750AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(516,287,NULL,'You can update an existing folder using the edit button under the action column.',0,'Feb 17 2004 10:46:37:750AM',0,'Feb 17 2004 10:46:37:750AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(517,287,NULL,'Add a folder to the general contacts module.',0,'Feb 17 2004 10:46:37:763AM',0,'Feb 17 2004 10:46:37:763AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(518,291,NULL,'You can view the process details by clicking on the select button under the Action column or by clicking on the name of the Triggered Process.',0,'Feb 17 2004 10:46:37:810AM',0,'Feb 17 2004 10:46:37:810AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(519,291,NULL,'You can view the process details by clicking on the select button under the Action column or by clicking on the name of the Triggered Process.',0,'Feb 17 2004 10:46:37:810AM',0,'Feb 17 2004 10:46:37:810AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(520,292,NULL,'You can add a group name and save it using the "save" button.',0,'Feb 17 2004 10:46:37:843AM',0,'Feb 17 2004 10:46:37:843AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(521,294,NULL,'You can click "Edit" in the Action column to update or delete a contact type.',0,'Feb 17 2004 10:46:37:857AM',0,'Feb 17 2004 10:46:37:857AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(522,294,NULL,'You can preview all the items present in a List name using the drop down in the preview column.',0,'Feb 17 2004 10:46:37:873AM',0,'Feb 17 2004 10:46:37:873AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(523,297,NULL,'You can click "Edit" in the Action column to update or delete a contact type.',0,'Feb 17 2004 10:46:37:903AM',0,'Feb 17 2004 10:46:37:903AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(524,297,NULL,'You can preview all the items present in a List name using the drop down in the preview column.',0,'Feb 17 2004 10:46:37:903AM',0,'Feb 17 2004 10:46:37:903AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(525,298,NULL,'You can also delete the folder and all the fields using the "Delete this folder and all fields" at the bottom of the page.',0,'Feb 17 2004 10:46:38:000AM',0,'Feb 17 2004 10:46:38:000AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(526,298,NULL,'The groups can also be moved up or down using the "Up" and "Down". They can also be edited and deleted using the "Edit" and "Del" links.',0,'Feb 17 2004 10:46:38:013AM',0,'Feb 17 2004 10:46:38:013AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(527,298,NULL,'The custom field can also be edited and deleted using the corresponding links "Edit" and "Del".',0,'Feb 17 2004 10:46:38:013AM',0,'Feb 17 2004 10:46:38:013AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(528,298,NULL,'The custom field created can be moved up or down for the display using the corresponding links "Up" and "Down".',0,'Feb 17 2004 10:46:38:030AM',0,'Feb 17 2004 10:46:38:030AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(529,298,NULL,'You can add a custom field for the group using the "Add a custom field" link.',0,'Feb 17 2004 10:46:38:030AM',0,'Feb 17 2004 10:46:38:030AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(530,298,NULL,'Add a group to the folder selected',0,'Feb 17 2004 10:46:38:047AM',0,'Feb 17 2004 10:46:38:047AM',NULL,NULL,1,6)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(531,298,NULL,'You can select the folder by using the drop down box under the general contacts module.',0,'Feb 17 2004 10:46:38:047AM',0,'Feb 17 2004 10:46:38:047AM',NULL,NULL,1,7)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(532,299,NULL,'Clicking on the list of categories displayed in level1 shows you its sub levels or sub-directories present in level2 and clicking on these in turn shows its subdirectories in level3 and so on.',0,'Feb 17 2004 10:46:38:077AM',0,'Feb 17 2004 10:46:38:077AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(533,299,NULL,'You can select to display either the Active Categories or the Draft Categories by clicking on the tabs "Active Categories" and "Draft Categories" respectively.',0,'Feb 17 2004 10:46:38:077AM',0,'Feb 17 2004 10:46:38:077AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(534,300,NULL,'The activated list can be brought back / reverted to the active list by clicking the "Revert to Active List".',0,'Feb 17 2004 10:46:38:093AM',0,'Feb 17 2004 10:46:38:093AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(535,300,NULL,'You can activate each level by using the "Activate now" button.',0,'Feb 17 2004 10:46:38:107AM',0,'Feb 17 2004 10:46:38:107AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(536,300,NULL,'In the draft categories you can edit your category using the edit button present at the bottom of each level.',0,'Feb 17 2004 10:46:38:107AM',0,'Feb 17 2004 10:46:38:107AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(537,300,NULL,'You can select to display either the Active Categories or the Draft Categories by clicking on the tabs "Active Categories" and "Draft Categories" respectively.',0,'Feb 17 2004 10:46:38:123AM',0,'Feb 17 2004 10:46:38:123AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(538,300,NULL,'Clicking on the list of categories displayed in level1 shows you its sub-levels or sub-directories present in level2 and clicking on these in turn would shows their subdirectories in level3 and so on.',0,'Feb 17 2004 10:46:38:123AM',0,'Feb 17 2004 10:46:38:123AM',NULL,NULL,1,5)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(539,304,NULL,'The "Modify" button in the "Details" tab provides a quick link that allows the users information to be modified without having to browse back to the previous window.',0,'Feb 17 2004 10:46:38:200AM',0,'Feb 17 2004 10:46:38:200AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(540,304,NULL,'The "Employee Link" in the ''Primary Information" table header provides a quick link to view the user''s contact information.',0,'Feb 17 2004 10:46:38:200AM',0,'Feb 17 2004 10:46:38:200AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(541,304,NULL,'The "Details" tab displays the information about the user in a non-editable format.',0,'Feb 17 2004 10:46:38:200AM',0,'Feb 17 2004 10:46:38:200AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(542,304,NULL,'The "Disable" button provides a quick link to disable/inactivate the user.',0,'Feb 17 2004 10:46:38:217AM',0,'Feb 17 2004 10:46:38:217AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(543,309,NULL,'The user and module section allows the administrator to manage users, roles, role hierarchy and manage modules.',0,'Feb 17 2004 10:46:38:280AM',0,'Feb 17 2004 10:46:38:280AM',NULL,NULL,1,1)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(544,309,NULL,'The global parameters and server configuration module allows the administrator to set the session timeout parameter.',0,'Feb 17 2004 10:46:38:280AM',0,'Feb 17 2004 10:46:38:280AM',NULL,NULL,1,2)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(545,309,NULL,'The usage section allows the administrator to view the total number of users, memory used, and system usage parameters for various time intervals.',0,'Feb 17 2004 10:46:38:297AM',0,'Feb 17 2004 10:46:38:297AM',NULL,NULL,1,3)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(546,309,NULL,'The administration module is divided into distinct categories such as managing users, module configuration, setting global parameters, server configuration and monitoring system usage and resources.',0,'Feb 17 2004 10:46:38:297AM',0,'Feb 17 2004 10:46:38:297AM',NULL,NULL,1,4)
INSERT [help_features] ([feature_id],[link_help_id],[link_feature_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled],[level])VALUES(547,311,NULL,'Clicking on the different links of the search results will direct you to the corresponding details in the modules.',0,'Feb 17 2004 10:46:38:403AM',0,'Feb 17 2004 10:46:38:403AM',NULL,NULL,1,1)
SET IDENTITY_INSERT [help_features] OFF
GO
SET NOCOUNT OFF

-- Insert default help_business_rules
SET NOCOUNT ON
SET IDENTITY_INSERT [help_business_rules] ON
GO
INSERT [help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(1,1,'You can view your calendar and the calendars of those who work for you',0,'Feb 17 2004 10:46:28:403AM',0,'Feb 17 2004 10:46:28:403AM',NULL,NULL,1)
INSERT [help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(2,251,'Other tickets in my Department: These are records that are assigned to anyone in your department, are unassigned in your department, and are open.',0,'Feb 17 2004 10:46:36:500AM',0,'Feb 17 2004 10:46:36:500AM',NULL,NULL,1)
INSERT [help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(3,251,'Tickets assigned to me: These are records that are assigned to you and are open',0,'Feb 17 2004 10:46:36:500AM',0,'Feb 17 2004 10:46:36:500AM',NULL,NULL,1)
INSERT [help_business_rules] ([rule_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[completedate],[completedby],[enabled])VALUES(4,251,'Tickets created by me: These are records that have been entered by you and are open',0,'Feb 17 2004 10:46:36:513AM',0,'Feb 17 2004 10:46:36:513AM',NULL,NULL,1)
SET IDENTITY_INSERT [help_business_rules] OFF
GO
SET NOCOUNT OFF

-- Insert default help_tips
SET NOCOUNT ON
SET IDENTITY_INSERT [help_tips] ON
GO
INSERT [help_tips] ([tip_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(1,1,'Assign due dates for tasks so that you can be alerted',0,'Feb 17 2004 10:46:28:433AM',0,'Feb 17 2004 10:46:28:433AM',1)
INSERT [help_tips] ([tip_id],[link_help_id],[description],[enteredby],[entered],[modifiedby],[modified],[enabled])VALUES(2,251,'Make sure to resolve your tickets as soon as possible so they don''t appear here!',0,'Feb 17 2004 10:46:36:513AM',0,'Feb 17 2004 10:46:36:513AM',1)
SET IDENTITY_INSERT [help_tips] OFF
GO
SET NOCOUNT OFF

-- Insert default sync_system
SET NOCOUNT ON
SET IDENTITY_INSERT [sync_system] ON
GO
INSERT [sync_system] ([system_id],[application_name],[enabled])VALUES(1,'Vport Telemarketing',1)
INSERT [sync_system] ([system_id],[application_name],[enabled])VALUES(2,'Land Mark: Auto Guide PocketPC',1)
INSERT [sync_system] ([system_id],[application_name],[enabled])VALUES(3,'Street Smart Speakers: Web Portal',1)
INSERT [sync_system] ([system_id],[application_name],[enabled])VALUES(4,'CFSHttpXMLWriter',1)
INSERT [sync_system] ([system_id],[application_name],[enabled])VALUES(5,'Fluency',1)
SET IDENTITY_INSERT [sync_system] OFF
GO
SET NOCOUNT OFF

-- Insert default sync_table
SET NOCOUNT ON
SET IDENTITY_INSERT [sync_table] ON
GO
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(1,1,'ticket','org.aspcfs.modules.troubletickets.base.Ticket','Feb 17 2004 10:46:13:920AM','Feb 17 2004 10:46:13:920AM',NULL,-1,0,'id')
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(2,2,'syncClient','org.aspcfs.modules.service.base.SyncClient','Feb 17 2004 10:46:13:933AM','Feb 17 2004 10:46:13:933AM',NULL,2,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(3,2,'user','org.aspcfs.modules.admin.base.User','Feb 17 2004 10:46:13:933AM','Feb 17 2004 10:46:13:933AM',NULL,4,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(4,2,'account','org.aspcfs.modules.accounts.base.Organization','Feb 17 2004 10:46:13:950AM','Feb 17 2004 10:46:13:950AM',NULL,5,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(5,2,'accountInventory','org.aspcfs.modules.media.autoguide.base.Inventory','Feb 17 2004 10:46:13:950AM','Feb 17 2004 10:46:13:950AM',NULL,6,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(6,2,'inventoryOption','org.aspcfs.modules.media.autoguide.base.InventoryOption','Feb 17 2004 10:46:13:950AM','Feb 17 2004 10:46:13:950AM',NULL,8,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(7,2,'adRun','org.aspcfs.modules.media.autoguide.base.AdRun','Feb 17 2004 10:46:13:950AM','Feb 17 2004 10:46:13:950AM',NULL,10,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(8,2,'tableList','org.aspcfs.modules.service.base.SyncTableList','Feb 17 2004 10:46:13:950AM','Feb 17 2004 10:46:13:950AM',NULL,12,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(9,2,'status_master',NULL,'Feb 17 2004 10:46:13:950AM','Feb 17 2004 10:46:13:950AM',NULL,14,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(10,2,'system',NULL,'Feb 17 2004 10:46:13:967AM','Feb 17 2004 10:46:13:967AM',NULL,16,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(11,2,'userList','org.aspcfs.modules.admin.base.UserList','Feb 17 2004 10:46:13:967AM','Feb 17 2004 10:46:13:967AM','CREATE TABLE users ( user_id              int NOT NULL, record_status_id     int NULL, user_name            nvarchar(20) NULL, pin                  nvarchar(20) NULL, modified             datetime NULL, PRIMARY KEY (user_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',50,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(12,2,'XIF18users',NULL,'Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE INDEX XIF18users ON users ( record_status_id )',60,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(13,2,'makeList','org.aspcfs.modules.media.autoguide.base.MakeList','Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE TABLE make ( make_id              int NOT NULL, make_name            nvarchar(20) NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',70,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(14,2,'XIF2make',NULL,'Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE INDEX XIF2make ON make ( record_status_id )',80,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(15,2,'modelList','org.aspcfs.modules.media.autoguide.base.ModelList','Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE TABLE model ( model_id             int NOT NULL, make_id              int NULL, record_status_id     int NULL, model_name           nvarchar(40) NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (model_id), FOREIGN KEY (make_id) REFERENCES make (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',100,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(16,2,'XIF3model',NULL,'Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE INDEX XIF3model ON model ( record_status_id )',110,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(17,2,'XIF5model',NULL,'Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE INDEX XIF5model ON model ( make_id )',120,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(18,2,'vehicleList','org.aspcfs.modules.media.autoguide.base.VehicleList','Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE TABLE vehicle ( year                 nvarchar(4) NOT NULL, vehicle_id           int NOT NULL, model_id             int NULL, make_id              int NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (vehicle_id), FOREIGN KEY (model_id) REFERENCES model (model_id), FOREIGN KEY (make_id) REFERENCES make (make_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',130,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(19,2,'XIF30vehicle',NULL,'Feb 17 2004 10:46:13:980AM','Feb 17 2004 10:46:13:980AM','CREATE INDEX XIF30vehicle ON vehicle ( make_id )',140,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(20,2,'XIF31vehicle',NULL,'Feb 17 2004 10:46:13:997AM','Feb 17 2004 10:46:13:997AM','CREATE INDEX XIF31vehicle ON vehicle ( model_id )',150,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(21,2,'XIF4vehicle',NULL,'Feb 17 2004 10:46:13:997AM','Feb 17 2004 10:46:13:997AM','CREATE INDEX XIF4vehicle ON vehicle ( record_status_id )',160,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(22,2,'accountList','org.aspcfs.modules.accounts.base.OrganizationList','Feb 17 2004 10:46:13:997AM','Feb 17 2004 10:46:13:997AM','CREATE TABLE account ( account_id           int NOT NULL, account_name         nvarchar(80) NULL, record_status_id     int NULL, address              nvarchar(80) NULL, modified             datetime NULL, city                 nvarchar(80) NULL, state                nvarchar(2) NULL, notes                nvarchar(255) NULL, zip                  nvarchar(11) NULL, phone                nvarchar(20) NULL, contact              nvarchar(20) NULL, dmv_number           nvarchar(20) NULL, owner_id             int NULL, entered              datetime NULL, enteredby            int NULL, modifiedby           int NULL, PRIMARY KEY (account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',170,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(23,2,'XIF16account',NULL,'Feb 17 2004 10:46:13:997AM','Feb 17 2004 10:46:13:997AM','CREATE INDEX XIF16account ON account ( record_status_id )',180,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(24,2,'accountInventoryList','org.aspcfs.modules.media.autoguide.base.InventoryList','Feb 17 2004 10:46:13:997AM','Feb 17 2004 10:46:13:997AM','CREATE TABLE account_inventory ( inventory_id         int NOT NULL, vin                  nvarchar(20) NULL, vehicle_id           int NULL, account_id           int NULL, mileage              nvarchar(20) NULL, enteredby            int NULL, new                  bit, condition            nvarchar(20) NULL, comments             nvarchar(255) NULL, stock_no             nvarchar(20) NULL, ext_color            nvarchar(20) NULL, int_color            nvarchar(20) NULL, style                nvarchar(40) NULL, invoice_price        money NULL, selling_price        money NULL, selling_price_text		nvarchar(100) NULL, modified             datetime NULL, sold                 int NULL, modifiedby           int NULL, record_status_id     int NULL, entered              datetime NULL, PRIMARY KEY (inventory_id), FOREIGN KEY (account_id) REFERENCES account (account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',190,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(25,2,'XIF10account_inventory',NULL,'Feb 17 2004 10:46:13:997AM','Feb 17 2004 10:46:13:997AM','CREATE INDEX XIF10account_inventory ON account_inventory ( record_status_id )',200,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(26,2,'XIF10account_inventory',NULL,'Feb 17 2004 10:46:13:997AM','Feb 17 2004 10:46:13:997AM','CREATE INDEX XIF11account_inventory ON account_inventory ( modifiedby )',210,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(27,2,'XIF19account_inventory',NULL,'Feb 17 2004 10:46:14:013AM','Feb 17 2004 10:46:14:013AM','CREATE INDEX XIF19account_inventory ON account_inventory ( account_id )',220,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(28,2,'XIF35account_inventory',NULL,'Feb 17 2004 10:46:14:013AM','Feb 17 2004 10:46:14:013AM','CREATE INDEX XIF35account_inventory ON account_inventory ( vehicle_id )',230,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(29,2,'optionList','org.aspcfs.modules.media.autoguide.base.OptionList','Feb 17 2004 10:46:14:013AM','Feb 17 2004 10:46:14:013AM','CREATE TABLE options ( option_id            int NOT NULL, option_name          nvarchar(20) NULL, record_status_id     int NULL, record_status_date   datetime NULL, PRIMARY KEY (option_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',330,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(30,2,'XIF24options',NULL,'Feb 17 2004 10:46:14:030AM','Feb 17 2004 10:46:14:030AM','CREATE INDEX XIF24options ON options ( record_status_id )',340,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(31,2,'inventoryOptionList','org.aspcfs.modules.media.autoguide.base.InventoryOptionList','Feb 17 2004 10:46:14:030AM','Feb 17 2004 10:46:14:030AM','CREATE TABLE inventory_options ( inventory_id         int NOT NULL, option_id            int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (option_id, inventory_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (option_id) REFERENCES options (option_id) )',350,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(32,2,'XIF25inventory_options',NULL,'Feb 17 2004 10:46:14:030AM','Feb 17 2004 10:46:14:030AM','CREATE INDEX XIF25inventory_options ON inventory_options ( option_id )',360,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(33,2,'XIF27inventory_options',NULL,'Feb 17 2004 10:46:14:030AM','Feb 17 2004 10:46:14:030AM','CREATE INDEX XIF27inventory_options ON inventory_options ( record_status_id )',370,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(34,2,'XIF33inventory_options',NULL,'Feb 17 2004 10:46:14:030AM','Feb 17 2004 10:46:14:030AM','CREATE INDEX XIF33inventory_options ON inventory_options ( inventory_id )',380,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(35,2,'adTypeList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:030AM','Feb 17 2004 10:46:14:030AM','CREATE TABLE ad_type ( ad_type_id           int NOT NULL, ad_type_name         nvarchar(20) NULL, PRIMARY KEY (ad_type_id) )',385,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(36,2,'adRunList','org.aspcfs.modules.media.autoguide.base.AdRunList','Feb 17 2004 10:46:14:030AM','Feb 17 2004 10:46:14:030AM','CREATE TABLE ad_run ( ad_run_id            int NOT NULL, record_status_id     int NULL, inventory_id         int NULL, ad_type_id           int NULL, ad_run_date          datetime NULL, has_picture          int NULL, modified             datetime NULL, entered              datetime NULL, modifiedby           int NULL, enteredby            int NULL, PRIMARY KEY (ad_run_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (ad_type_id) REFERENCES ad_type (ad_type_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',390,1,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(37,2,'XIF22ad_run',NULL,'Feb 17 2004 10:46:14:043AM','Feb 17 2004 10:46:14:043AM','CREATE INDEX XIF22ad_run ON ad_run ( record_status_id )',400,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(38,2,'XIF36ad_run',NULL,'Feb 17 2004 10:46:14:043AM','Feb 17 2004 10:46:14:043AM','CREATE INDEX XIF36ad_run ON ad_run ( ad_type_id )',402,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(39,2,'XIF37ad_run',NULL,'Feb 17 2004 10:46:14:043AM','Feb 17 2004 10:46:14:043AM','CREATE INDEX XIF37ad_run ON ad_run ( inventory_id )',404,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(40,2,'inventory_picture',NULL,'Feb 17 2004 10:46:14:060AM','Feb 17 2004 10:46:14:060AM','CREATE TABLE inventory_picture ( picture_name         nvarchar(20) NOT NULL, inventory_id         int NOT NULL, record_status_id     int NULL, entered              datetime NULL, modified             datetime NULL, modifiedby           int NULL, enteredby            int NULL, PRIMARY KEY (picture_name, inventory_id), FOREIGN KEY (inventory_id) REFERENCES account_inventory (inventory_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id) )',410,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(41,2,'XIF23inventory_picture',NULL,'Feb 17 2004 10:46:14:060AM','Feb 17 2004 10:46:14:060AM','CREATE INDEX XIF23inventory_picture ON inventory_picture ( record_status_id )',420,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(42,2,'XIF32inventory_picture',NULL,'Feb 17 2004 10:46:14:060AM','Feb 17 2004 10:46:14:060AM','CREATE INDEX XIF32inventory_picture ON inventory_picture ( inventory_id )',430,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(43,2,'preferences',NULL,'Feb 17 2004 10:46:14:060AM','Feb 17 2004 10:46:14:060AM','CREATE TABLE preferences ( user_id              int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (user_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (user_id) REFERENCES users (user_id) )',440,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(44,2,'XIF29preferences',NULL,'Feb 17 2004 10:46:14:060AM','Feb 17 2004 10:46:14:060AM','CREATE INDEX XIF29preferences ON preferences ( record_status_id )',450,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(45,2,'user_account',NULL,'Feb 17 2004 10:46:14:060AM','Feb 17 2004 10:46:14:060AM','CREATE TABLE user_account ( user_id              int NOT NULL, account_id           int NOT NULL, record_status_id     int NULL, modified             datetime NULL, PRIMARY KEY (user_id, account_id), FOREIGN KEY (record_status_id) REFERENCES status_master (record_status_id), FOREIGN KEY (account_id) REFERENCES account (account_id), FOREIGN KEY (user_id) REFERENCES users (user_id) )',460,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(46,2,'XIF14user_account',NULL,'Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM','CREATE INDEX XIF14user_account ON user_account ( user_id )',470,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(47,2,'XIF15user_account',NULL,'Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM','CREATE INDEX XIF15user_account ON user_account ( account_id )',480,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(48,2,'XIF17user_account',NULL,'Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM','CREATE INDEX XIF17user_account ON user_account ( record_status_id )',490,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(49,2,'deleteInventoryCache','org.aspcfs.modules.media.autoguide.actions.DeleteInventoryCache','Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM',NULL,500,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(50,4,'lookupIndustry','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(51,4,'lookupIndustryList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(52,4,'systemPrefs','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(53,4,'systemModules','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:077AM','Feb 17 2004 10:46:14:077AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(54,4,'systemModulesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(55,4,'lookupContactTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(56,4,'lookupContactTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(57,4,'lookupAccountTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(58,4,'lookupAccountTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(59,4,'lookupDepartment','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(60,4,'lookupDepartmentList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(61,4,'lookupOrgAddressTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(62,4,'lookupOrgAddressTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(63,4,'lookupOrgEmailTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(64,4,'lookupOrgEmailTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:090AM','Feb 17 2004 10:46:14:090AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(65,4,'lookupOrgPhoneTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:107AM','Feb 17 2004 10:46:14:107AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(66,4,'lookupOrgPhoneTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:107AM','Feb 17 2004 10:46:14:107AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(67,4,'lookupInstantMessengerTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:107AM','Feb 17 2004 10:46:14:107AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(68,4,'lookupInstantMessengerTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:107AM','Feb 17 2004 10:46:14:107AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(69,4,'lookupEmploymentTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:107AM','Feb 17 2004 10:46:14:107AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(70,4,'lookupEmploymentTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:107AM','Feb 17 2004 10:46:14:107AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(71,4,'lookupLocale','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:107AM','Feb 17 2004 10:46:14:107AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(72,4,'lookupLocaleList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:123AM','Feb 17 2004 10:46:14:123AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(73,4,'lookupContactAddressTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:123AM','Feb 17 2004 10:46:14:123AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(74,4,'lookupContactAddressTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:123AM','Feb 17 2004 10:46:14:123AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(75,4,'lookupContactEmailTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:123AM','Feb 17 2004 10:46:14:123AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(76,4,'lookupContactEmailTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:123AM','Feb 17 2004 10:46:14:123AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(77,4,'lookupContactPhoneTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:123AM','Feb 17 2004 10:46:14:123AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(78,4,'lookupContactPhoneTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(79,4,'lookupStage','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(80,4,'lookupStageList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(81,4,'lookupDeliveryOptions','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(82,4,'lookupDeliveryOptionsList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(83,4,'lookupCallTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(84,4,'lookupCallTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(85,4,'ticketCategory','org.aspcfs.modules.troubletickets.base.TicketCategory','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(86,4,'ticketCategoryList','org.aspcfs.modules.troubletickets.base.TicketCategoryList','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(87,4,'ticketSeverity','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(88,4,'ticketSeverityList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(89,4,'lookupTicketSource','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:137AM','Feb 17 2004 10:46:14:137AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(90,4,'lookupTicketSourceList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(91,4,'ticketPriority','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(92,4,'ticketPriorityList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(93,4,'lookupRevenueTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(94,4,'lookupRevenueTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(95,4,'lookupRevenueDetailTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(96,4,'lookupRevenueDetailTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(97,4,'lookupSurveyTypes','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(98,4,'lookupSurveyTypesList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(99,4,'syncClient','org.aspcfs.modules.service.base.SyncClient','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(100,4,'user','org.aspcfs.modules.admin.base.User','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(101,4,'userList','org.aspcfs.modules.admin.base.UserList','Feb 17 2004 10:46:14:153AM','Feb 17 2004 10:46:14:153AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(102,4,'contact','org.aspcfs.modules.contacts.base.Contact','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,'id')
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(103,4,'contactList','org.aspcfs.modules.contacts.base.ContactList','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(104,4,'ticket','org.aspcfs.modules.troubletickets.base.Ticket','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,'id')
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(105,4,'ticketList','org.aspcfs.modules.troubletickets.base.TicketList','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(106,4,'account','org.aspcfs.modules.accounts.base.Organization','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,'id')
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(107,4,'accountList','org.aspcfs.modules.accounts.base.OrganizationList','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(108,4,'role','org.aspcfs.modules.admin.base.Role','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(109,4,'roleList','org.aspcfs.modules.admin.base.RoleList','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(110,4,'permissionCategory','org.aspcfs.modules.admin.base.PermissionCategory','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(111,4,'permissionCategoryList','org.aspcfs.modules.admin.base.PermissionCategoryList','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(112,4,'permission','org.aspcfs.modules.admin.base.Permission','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(113,4,'permissionList','org.aspcfs.modules.admin.base.PermissionList','Feb 17 2004 10:46:14:170AM','Feb 17 2004 10:46:14:170AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(114,4,'rolePermission','org.aspcfs.modules.admin.base.RolePermission','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(115,4,'rolePermissionList','org.aspcfs.modules.admin.base.RolePermissionList','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(116,4,'opportunity','org.aspcfs.modules.pipeline.base.Opportunity','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(117,4,'opportunityList','org.aspcfs.modules.pipeline.base.OpportunityList','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(118,4,'call','org.aspcfs.modules.contacts.base.Call','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(119,4,'callList','org.aspcfs.modules.contacts.base.CallList','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(120,4,'customFieldCategory','org.aspcfs.modules.base.CustomFieldCategory','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(121,4,'customFieldCategoryList','org.aspcfs.modules.base.CustomFieldCategoryList','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(122,4,'customFieldGroup','org.aspcfs.modules.base.CustomFieldGroup','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(123,4,'customFieldGroupList','org.aspcfs.modules.base.CustomFieldGroupList','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(124,4,'customField','org.aspcfs.modules.base.CustomField','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(125,4,'customFieldList','org.aspcfs.modules.base.CustomFieldList','Feb 17 2004 10:46:14:183AM','Feb 17 2004 10:46:14:183AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(126,4,'customFieldLookup','org.aspcfs.utils.web.LookupElement','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(127,4,'customFieldLookupList','org.aspcfs.utils.web.LookupList','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(128,4,'customFieldRecord','org.aspcfs.modules.base.CustomFieldRecord','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(129,4,'customFieldRecordList','org.aspcfs.modules.base.CustomFieldRecordList','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(130,4,'contactEmailAddress','org.aspcfs.modules.contacts.base.ContactEmailAddress','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(131,4,'contactEmailAddressList','org.aspcfs.modules.contacts.base.ContactEmailAddressList','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(132,4,'customFieldData','org.aspcfs.modules.base.CustomFieldData','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(133,4,'lookupProjectActivity','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(134,4,'lookupProjectActivityList','org.aspcfs.utils.web.CustomLookupList','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(135,4,'lookupProjectIssues','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(136,4,'lookupProjectIssuesList','org.aspcfs.utils.web.CustomLookupList','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(137,4,'lookupProjectLoe','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:200AM','Feb 17 2004 10:46:14:200AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(138,4,'lookupProjectLoeList','org.aspcfs.utils.web.CustomLookupList','Feb 17 2004 10:46:14:217AM','Feb 17 2004 10:46:14:217AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(139,4,'lookupProjectPriority','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:217AM','Feb 17 2004 10:46:14:217AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(140,4,'lookupProjectPriorityList','org.aspcfs.utils.web.CustomLookupList','Feb 17 2004 10:46:14:217AM','Feb 17 2004 10:46:14:217AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(141,4,'lookupProjectStatus','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:217AM','Feb 17 2004 10:46:14:217AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(142,4,'lookupProjectStatusList','org.aspcfs.utils.web.CustomLookupList','Feb 17 2004 10:46:14:217AM','Feb 17 2004 10:46:14:217AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(143,4,'project','com.zeroio.iteam.base.Project','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(144,4,'projectList','com.zeroio.iteam.base.ProjectList','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(145,4,'requirement','com.zeroio.iteam.base.Requirement','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(146,4,'requirementList','com.zeroio.iteam.base.RequirementList','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(147,4,'assignment','com.zeroio.iteam.base.Assignment','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(148,4,'assignmentList','com.zeroio.iteam.base.AssignmentList','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(149,4,'issue','com.zeroio.iteam.base.Issue','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(150,4,'issueList','com.zeroio.iteam.base.IssueList','Feb 17 2004 10:46:14:230AM','Feb 17 2004 10:46:14:230AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(151,4,'issueReply','com.zeroio.iteam.base.IssueReply','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(152,4,'issueReplyList','com.zeroio.iteam.base.IssueReplyList','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(153,4,'teamMember','com.zeroio.iteam.base.TeamMember','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(154,4,'fileItem','com.zeroio.iteam.base.FileItem','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(155,4,'fileItemList','com.zeroio.iteam.base.FileItemList','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(156,4,'fileItemVersion','com.zeroio.iteam.base.FileItemVersion','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(157,4,'fileItemVersionList','com.zeroio.iteam.base.FileItemVersionList','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(158,4,'fileDownloadLog','com.zeroio.iteam.base.FileDownloadLog','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(159,4,'contactAddress','org.aspcfs.modules.contacts.base.ContactAddress','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(160,4,'contactAddressList','org.aspcfs.modules.contacts.base.ContactAddressList','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(161,4,'contactPhoneNumber','org.aspcfs.modules.contacts.base.ContactPhoneNumber','Feb 17 2004 10:46:14:247AM','Feb 17 2004 10:46:14:247AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(162,4,'contactPhoneNumberList','org.aspcfs.modules.contacts.base.ContactPhoneNumberList','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(163,4,'organizationPhoneNumber','org.aspcfs.modules.accounts.base.OrganizationPhoneNumber','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(164,4,'organizationPhoneNumberList','org.aspcfs.modules.accounts.base.OrganizationPhoneNumberList','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(165,4,'organizationEmailAddress','org.aspcfs.modules.accounts.base.OrganizationEmailAddress','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(166,4,'organizationEmailAddressList','org.aspcfs.modules.accounts.base.OrganizationEmailAddressList','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(167,4,'organizationAddress','org.aspcfs.modules.accounts.base.OrganizationAddress','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(168,4,'organizationAddressList','org.aspcfs.modules.accounts.base.OrganizationAddressList','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(169,4,'ticketLog','org.aspcfs.modules.troubletickets.base.TicketLog','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(170,4,'ticketLogList','org.aspcfs.modules.troubletickets.base.TicketLogList','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(171,4,'message','org.aspcfs.modules.communications.base.Message','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(172,4,'messageList','org.aspcfs.modules.communications.base.MessageList','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(173,4,'searchCriteriaElements','org.aspcfs.modules.communications.base.SearchCriteriaList','Feb 17 2004 10:46:14:263AM','Feb 17 2004 10:46:14:263AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(174,4,'searchCriteriaElementsList','org.aspcfs.modules.communications.base.SearchCriteriaListList','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(175,4,'savedCriteriaElement','org.aspcfs.modules.communications.base.SavedCriteriaElement','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(176,4,'searchFieldElement','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(177,4,'searchFieldElementList','org.aspcfs.utils.web.CustomLookupList','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(178,4,'revenue','org.aspcfs.modules.accounts.base.Revenue','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(179,4,'revenueList','org.aspcfs.modules.accounts.base.RevenueList','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(180,4,'campaign','org.aspcfs.modules.communications.base.Campaign','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(181,4,'campaignList','org.aspcfs.modules.communications.base.CampaignList','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(182,4,'scheduledRecipient','org.aspcfs.modules.communications.base.ScheduledRecipient','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(183,4,'scheduledRecipientList','org.aspcfs.modules.communications.base.ScheduledRecipientList','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(184,4,'accessLog','org.aspcfs.modules.admin.base.AccessLog','Feb 17 2004 10:46:14:280AM','Feb 17 2004 10:46:14:280AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(185,4,'accessLogList','org.aspcfs.modules.admin.base.AccessLogList','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(186,4,'accountTypeLevels','org.aspcfs.modules.accounts.base.AccountTypeLevel','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(187,4,'fieldTypes','org.aspcfs.utils.web.CustomLookupElement','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(188,4,'fieldTypesList','org.aspcfs.utils.web.CustomLookupList','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(189,4,'excludedRecipient','org.aspcfs.modules.communications.base.ExcludedRecipient','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(190,4,'campaignRun','org.aspcfs.modules.communications.base.CampaignRun','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(191,4,'campaignRunList','org.aspcfs.modules.communications.base.CampaignRunList','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(192,4,'campaignListGroups','org.aspcfs.modules.communications.base.CampaignListGroup','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(193,5,'ticket','org.aspcfs.modules.troubletickets.base.Ticket','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,'id')
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(194,5,'ticketCategory','org.aspcfs.modules.troubletickets.base.TicketCategory','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(195,5,'ticketCategoryList','org.aspcfs.modules.troubletickets.base.TicketCategoryList','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(196,5,'syncClient','org.aspcfs.modules.service.base.SyncClient','Feb 17 2004 10:46:14:293AM','Feb 17 2004 10:46:14:293AM',NULL,2,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(197,5,'accountList','org.aspcfs.modules.accounts.base.OrganizationList','Feb 17 2004 10:46:14:310AM','Feb 17 2004 10:46:14:310AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(198,5,'userList','org.aspcfs.modules.admin.base.UserList','Feb 17 2004 10:46:14:310AM','Feb 17 2004 10:46:14:310AM',NULL,-1,0,NULL)
INSERT [sync_table] ([table_id],[system_id],[element_name],[mapped_class_name],[entered],[modified],[create_statement],[order_id],[sync_item],[object_key])VALUES(199,5,'contactList','org.aspcfs.modules.contacts.base.ContactList','Feb 17 2004 10:46:14:310AM','Feb 17 2004 10:46:14:310AM',NULL,-1,0,NULL)
SET IDENTITY_INSERT [sync_table] OFF
GO
SET NOCOUNT OFF

-- Insert default autoguide_options
SET NOCOUNT ON
SET IDENTITY_INSERT [autoguide_options] ON
GO
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(1,'A/T',0,10,0,'Feb 17 2004 10:46:14:403AM','Feb 17 2004 10:46:14:403AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(2,'4-CYL',0,20,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(3,'6-CYL',0,30,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(4,'V-8',0,40,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(5,'CRUISE',0,50,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(6,'5-SPD',0,60,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(7,'4X4',0,70,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(8,'2-DOOR',0,80,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(9,'4-DOOR',0,90,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(10,'LEATHER',0,100,0,'Feb 17 2004 10:46:14:420AM','Feb 17 2004 10:46:14:420AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(11,'P/DL',0,110,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(12,'T/W',0,120,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(13,'P/SEATS',0,130,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(14,'P/WIND',0,140,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(15,'P/S',0,150,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(16,'BEDLINE',0,160,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(17,'LOW MILES',0,170,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(18,'EX CLEAN',0,180,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(19,'LOADED',0,190,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(20,'A/C',0,200,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(21,'SUNROOF',0,210,0,'Feb 17 2004 10:46:14:433AM','Feb 17 2004 10:46:14:433AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(22,'AM/FM ST',0,220,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(23,'CASS',0,225,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(24,'CD PLYR',0,230,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(25,'ABS',0,240,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(26,'ALARM',0,250,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(27,'SLD R. WIN',0,260,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(28,'AIRBAG',0,270,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(29,'1 OWNER',0,280,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
INSERT [autoguide_options] ([option_id],[option_name],[default_item],[level],[enabled],[entered],[modified])VALUES(30,'ALLOY WH',0,290,0,'Feb 17 2004 10:46:14:450AM','Feb 17 2004 10:46:14:450AM')
SET IDENTITY_INSERT [autoguide_options] OFF
GO
SET NOCOUNT OFF

-- Insert default autoguide_ad_run_types
SET NOCOUNT ON
SET IDENTITY_INSERT [autoguide_ad_run_types] ON
GO
INSERT [autoguide_ad_run_types] ([code],[description],[default_item],[level],[enabled],[entered],[modified])VALUES(1,'In Column',0,1,1,'Feb 17 2004 10:46:14:480AM','Feb 17 2004 10:46:14:480AM')
INSERT [autoguide_ad_run_types] ([code],[description],[default_item],[level],[enabled],[entered],[modified])VALUES(2,'Display',0,2,1,'Feb 17 2004 10:46:14:497AM','Feb 17 2004 10:46:14:497AM')
INSERT [autoguide_ad_run_types] ([code],[description],[default_item],[level],[enabled],[entered],[modified])VALUES(3,'Both',0,3,1,'Feb 17 2004 10:46:14:497AM','Feb 17 2004 10:46:14:497AM')
SET IDENTITY_INSERT [autoguide_ad_run_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_revenue_types
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_revenue_types] ON
GO
INSERT [lookup_revenue_types] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Technical',0,0,1)
SET IDENTITY_INSERT [lookup_revenue_types] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_task_priority
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_task_priority] ON
GO
INSERT [lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(1,'1',1,1,1)
INSERT [lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(2,'2',0,2,1)
INSERT [lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(3,'3',0,3,1)
INSERT [lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(4,'4',0,4,1)
INSERT [lookup_task_priority] ([code],[description],[default_item],[level],[enabled])VALUES(5,'5',0,5,1)
SET IDENTITY_INSERT [lookup_task_priority] OFF
GO
SET NOCOUNT OFF

-- Insert default lookup_task_loe
SET NOCOUNT ON
SET IDENTITY_INSERT [lookup_task_loe] ON
GO
INSERT [lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(1,'Minute(s)',0,1,1)
INSERT [lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(2,'Hour(s)',1,2,1)
INSERT [lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(3,'Day(s)',0,3,1)
INSERT [lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(4,'Week(s)',0,4,1)
INSERT [lookup_task_loe] ([code],[description],[default_item],[level],[enabled])VALUES(5,'Month(s)',0,5,1)
SET IDENTITY_INSERT [lookup_task_loe] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_component_library
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_component_library] ON
GO
INSERT [business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(1,'org.aspcfs.modules.troubletickets.components.LoadTicketDetails',1,'org.aspcfs.modules.troubletickets.components.LoadTicketDetails','Load all ticket information for use in other steps',1)
INSERT [business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(2,'org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed',1,'org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed','Was the ticket just closed?',1)
INSERT [business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(3,'org.aspcfs.modules.components.SendUserNotification',1,'org.aspcfs.modules.components.SendUserNotification','Send an email notification to a user',1)
INSERT [business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(4,'org.aspcfs.modules.troubletickets.components.SendTicketSurvey',1,'org.aspcfs.modules.troubletickets.components.SendTicketSurvey','org.aspcfs.modules.troubletickets.components.SendTicketSurvey',1)
INSERT [business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(5,'org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned',1,'org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned','Was the ticket just assigned or reassigned?',1)
INSERT [business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(6,'org.aspcfs.modules.troubletickets.components.GenerateTicketList',2,'org.aspcfs.modules.troubletickets.components.GenerateTicketList','Generate a list of tickets based on specified parameters.  Are there any tickets matching the parameters?',1)
INSERT [business_process_component_library] ([component_id],[component_name],[type_id],[class_name],[description],[enabled])VALUES(7,'org.aspcfs.modules.troubletickets.components.SendTicketListReport',2,'org.aspcfs.modules.troubletickets.components.SendTicketListReport','Sends a ticket report to specified users with the specified parameters',1)
SET IDENTITY_INSERT [business_process_component_library] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_component_result_lookup
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_component_result_lookup] ON
GO
INSERT [business_process_component_result_lookup] ([result_id],[component_id],[return_id],[description],[level],[enabled])VALUES(1,2,1,'Yes',0,1)
INSERT [business_process_component_result_lookup] ([result_id],[component_id],[return_id],[description],[level],[enabled])VALUES(2,2,0,'No',1,1)
INSERT [business_process_component_result_lookup] ([result_id],[component_id],[return_id],[description],[level],[enabled])VALUES(3,5,1,'Yes',0,1)
SET IDENTITY_INSERT [business_process_component_result_lookup] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_parameter_library
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_parameter_library] ON
GO
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(1,3,'notification.module',NULL,'Tickets',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(2,3,'notification.itemId',NULL,'${this.id}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(3,3,'notification.itemModified',NULL,'${this.modified}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(4,3,'notification.userToNotify',NULL,'${previous.enteredBy}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(5,3,'notification.subject',NULL,'Dark Horse CRM Ticket Closed: ${this.paddedId}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(6,3,'notification.body',NULL,'The following ticket in Dark Horse CRM has been closed:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Comment: ${this.comment}

Closed by: ${ticketModifiedByContact.nameFirstLast}

Solution: ${this.solution}
',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(7,6,'notification.module',NULL,'Tickets',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(8,6,'notification.itemId',NULL,'${this.id}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(9,6,'notification.itemModified',NULL,'${this.modified}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(10,6,'notification.userToNotify',NULL,'${this.assignedTo}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(11,6,'notification.subject',NULL,'Dark Horse CRM Ticket Assigned: ${this.paddedId}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(12,6,'notification.body',NULL,'The following ticket in Dark Horse CRM has been assigned to you:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Assigned By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}
',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(13,7,'ticketList.onlyOpen',NULL,'true',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(14,7,'ticketList.onlyAssigned',NULL,'true',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(15,7,'ticketList.onlyUnassigned',NULL,'true',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(16,7,'ticketList.minutesOlderThan',NULL,'10',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(17,7,'ticketList.lastAnchor',NULL,'${process.lastAnchor}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(18,7,'ticketList.nextAnchor',NULL,'${process.nextAnchor}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(19,8,'notification.users.to',NULL,'${this.enteredBy}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(20,8,'notification.contacts.to',NULL,'${this.contactId}',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(21,8,'notification.subject',NULL,'Dark Horse CRM Unassigned Ticket Report (${objects.size})',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(22,8,'notification.body',NULL,'** This is an automated message **

The following tickets in Dark Horse CRM are unassigned and need attention:

',1)
INSERT [business_process_parameter_library] ([parameter_id],[component_id],[param_name],[description],[default_value],[enabled])VALUES(23,8,'report.ticket.content',NULL,'----- Ticket Details -----
Ticket # ${this.paddedId}
Created: ${this.enteredString}
Organization: ${ticketOrganization.name}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Last Modified By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}


',1)
SET IDENTITY_INSERT [business_process_parameter_library] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process] ON
GO
INSERT [business_process] ([process_id],[process_name],[description],[type_id],[link_module_id],[component_start_id],[enabled],[entered])VALUES(1,'dhv.ticket.insert','Ticket change notification',1,8,1,1,'Feb 17 2004 10:46:42:670AM')
INSERT [business_process] ([process_id],[process_name],[description],[type_id],[link_module_id],[component_start_id],[enabled],[entered])VALUES(2,'dhv.report.ticketList.overdue','Overdue ticket notification',2,8,7,1,'Feb 17 2004 10:46:43:310AM')
SET IDENTITY_INSERT [business_process] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_component
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_component] ON
GO
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(1,1,1,NULL,NULL,1)
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(2,1,2,1,NULL,1)
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(3,1,3,2,1,1)
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(4,1,4,2,1,0)
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(5,1,5,2,0,1)
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(6,1,3,5,1,1)
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(7,2,6,NULL,NULL,1)
INSERT [business_process_component] ([id],[process_id],[component_id],[parent_id],[parent_result_id],[enabled])VALUES(8,2,7,7,NULL,1)
SET IDENTITY_INSERT [business_process_component] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_component_parameter
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_component_parameter] ON
GO
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(1,3,1,'Tickets',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(2,3,2,'${this.id}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(3,3,3,'${this.modified}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(4,3,4,'${previous.enteredBy}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(5,3,5,'Dark Horse CRM Ticket Closed: ${this.paddedId}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(6,3,6,'The following ticket in Dark Horse CRM has been closed:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Comment: ${this.comment}

Closed by: ${ticketModifiedByContact.nameFirstLast}

Solution: ${this.solution}
',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(7,6,7,'Tickets',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(8,6,8,'${this.id}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(9,6,9,'${this.modified}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(10,6,10,'${this.assignedTo}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(11,6,11,'Dark Horse CRM Ticket Assigned: ${this.paddedId}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(12,6,12,'The following ticket in Dark Horse CRM has been assigned to you:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Assigned By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}
',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(13,7,13,'true',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(14,7,14,'true',0)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(15,7,15,'true',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(16,7,16,'10',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(17,7,17,'${process.lastAnchor}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(18,7,18,'${process.nextAnchor}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(19,8,19,'${this.enteredBy}',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(20,8,20,'${this.contactId}',0)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(21,8,21,'Dark Horse CRM Unassigned Ticket Report (${objects.size})',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(22,8,22,'** This is an automated message **

The following tickets in Dark Horse CRM are unassigned and need attention:

',1)
INSERT [business_process_component_parameter] ([id],[component_id],[parameter_id],[param_value],[enabled])VALUES(23,8,23,'----- Ticket Details -----
Ticket # ${this.paddedId}
Created: ${this.enteredString}
Organization: ${ticketOrganization.name}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Last Modified By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}


',1)
SET IDENTITY_INSERT [business_process_component_parameter] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_hook_library
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_hook_library] ON
GO
INSERT [business_process_hook_library] ([hook_id],[link_module_id],[hook_class],[enabled])VALUES(1,8,'org.aspcfs.modules.troubletickets.base.Ticket',1)
SET IDENTITY_INSERT [business_process_hook_library] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_hook_triggers
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_hook_triggers] ON
GO
INSERT [business_process_hook_triggers] ([trigger_id],[action_type_id],[hook_id],[enabled])VALUES(1,2,1,1)
INSERT [business_process_hook_triggers] ([trigger_id],[action_type_id],[hook_id],[enabled])VALUES(2,1,1,1)
SET IDENTITY_INSERT [business_process_hook_triggers] OFF
GO
SET NOCOUNT OFF

-- Insert default business_process_hook
SET NOCOUNT ON
SET IDENTITY_INSERT [business_process_hook] ON
GO
INSERT [business_process_hook] ([id],[trigger_id],[process_id],[enabled])VALUES(1,1,1,1)
INSERT [business_process_hook] ([id],[trigger_id],[process_id],[enabled])VALUES(2,2,1,1)
SET IDENTITY_INSERT [business_process_hook] OFF
GO
SET NOCOUNT OFF

