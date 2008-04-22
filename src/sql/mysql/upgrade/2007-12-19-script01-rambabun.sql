-- Setting the default values into modified fields, Ticket 828.

update lookup_emailserver_types set modified = entered;
update lookup_emailaccount_processing_behavior set modified = entered;
update lookup_emailaccount_inbox_behavior set modified = entered;

