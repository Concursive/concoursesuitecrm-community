#!/bin/sh
 
if [ -n "$1" ] ; then DB="$1"; shift ; fi

if [ -z "${DB}" ] ; then echo "Missing database name" ; exit 1 ; fi

# Backup
pg_dump ${DB} -naDt task > ${DB}-task.sql
pg_dump ${DB} -naDt tasklink_contact > ${DB}-tasklink_contact.sql
pg_dump ${DB} -naDt tasklink_ticket > ${DB}-tasklink_ticket.sql
pg_dump ${DB} -naDt tasklink_project > ${DB}-tasklink_project.sql
 
# Drop
psql ${DB} -c "DROP TABLE tasklink_project"
psql ${DB} -c "DROP TABLE tasklink_ticket"
psql ${DB} -c "DROP TABLE tasklink_contact"
psql ${DB} -c "DROP TABLE task"
psql ${DB} -c "DROP SEQUENCE task_task_id_seq"
 
# Create temporary table and restore
psql ${DB} -f 2003-02-17-script1.sql
psql ${DB} -f ${DB}-task.sql 

# Table mods
psql ${DB} -c "UPDATE task SET owner = contact.user_id FROM contact WHERE task.owner = contact.contact_id"
pg_dump ${DB} -naDt task > ${DB}-task.sql
psql ${DB} -c "DROP TABLE task"
psql ${DB} -c "DROP SEQUENCE task_task_id_seq"

psql ${DB} -f 2003-02-17-script2.sql

psql ${DB} -f ${DB}-task.sql 
psql ${DB} -f ${DB}-tasklink_contact.sql
psql ${DB} -f ${DB}-tasklink_ticket.sql
psql ${DB} -f ${DB}-tasklink_project.sql

# The task sequence needs to be updated since the restore script doesn't
# update the sequence when new records are inserted
psql ${DB} -c "SELECT max(task_id) FROM task"
echo psql ${DB} -c "SELECT setval('task_task_id_seq', x)"

