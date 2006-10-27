#!/bin/sh

ant import.database -Darg1=jdbc:jtds:sqlserver://127.0.0.1:1433/centric_crm -Darg2=username -Darg3=password -Darg4=database-backup.xml
