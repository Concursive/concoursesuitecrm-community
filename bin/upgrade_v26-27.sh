# gatekeeper
ant upgradedb -Darg1=2003-09-05gk.sql
ant upgradedb -Darg1=2003-10-10gk.sql
ant upgradedb -Darg1=2003-11-12gk.sql

# rename ticket priorities, task loe order
ant upgradedb -Darg2= -Darg1=2004-03-11.sql
ant upgradedb -Darg2= -Darg1=2004-03-11-script2.sql
# reports tables needed for permissions too
ant upgradedb -Darg2= -Darg1=2003-10-10.sql
# Add tickets-tickets-tasks/accounts permissions
ant upgradedb -Darg2= -Darg1=2003-08-25.bsh
# admin-sysconfig-categories
ant upgradedb -Darg2= -Darg1=2003-08-26.bsh
# update dates to datetimes
ant upgradedb -Darg2= -Darg1=2003-09-25.sql
# new myhomepage permission
ant upgradedb -Darg2= -Darg1=2003-10-10.bsh
# permission renaming
ant upgradedb -Darg2= -Darg1=2003-10-11.sql
# install reports and permission
ant upgradedb -Darg2= -Darg1=2003-10-13.bsh
ant upgradedb -Darg2= -Darg1=2003-10-13b.bsh
ant upgradedb -Darg2= -Darg1=2003-10-16-script1.bsh
ant upgradedb -Darg2= -Darg1=2003-10-16-script2.bsh
ant upgradedb -Darg2= -Darg1=2003-10-16-script3.bsh
ant upgradedb -Darg2= -Darg1=2003-10-16-script4.bsh
ant upgradedb -Darg2= -Darg1=2003-10-21-script1.bsh
ant upgradedb -Darg2= -Darg1=2003-10-23-script1.bsh
ant upgradedb -Darg2= -Darg1=2003-10-23-script2.bsh
# help tables
ant upgradedb -Darg2= -Darg1=2003-11-10.sql
# employee as a module
ant upgradedb -Darg2= -Darg1=2003-11-11.bsh
# rename permissions
ant upgradedb -Darg2= -Darg1=2003-11-11.sql
# more reports
ant upgradedb -Darg2= -Darg1=2003-11-12-script1.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script2.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script3.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script4.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script5.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script6.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script7.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script8.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script9.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script10.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script11.bsh
ant upgradedb -Darg2= -Darg1=2003-11-12-script12.bsh
# ticket folders and ticket sync
ant upgradedb -Darg2= -Darg1=2003-11-17.bsh
ant upgradedb -Darg2= -Darg1=2003-11-17.sql
ant upgradedb -Darg2= -Darg1=2003-11-17-script2.sql
# more help changes
ant upgradedb -Darg2= -Darg1=2003-11-21.sql
# ticket folder permission
ant upgradedb -Darg2= -Darg1=2003-12-15.bsh
# sync table
ant upgradedb -Darg2= -Darg1=2003-12-15.sql
# new saved_criteriaelement feature
ant upgradedb -Darg2= -Darg1=2003-12-18-script1.sql
ant upgradedb -Darg2= -Darg1=2003-12-18-script2.bsh
ant upgradedb -Darg2= -Darg1=2003-12-18-script3.sql
# rename tickets to help desk
ant upgradedb -Darg2= -Darg1=2003-12-19.sql
# new ticket fields
ant upgradedb -Darg2= -Darg1=2004-01-22.sql
# permissions and opportunity_header constraint
ant upgradedb -Darg2= -Darg1=2004-01-29.sql

# Manually upgrade the help for each site
echo Execute 'ant upgradedb.help' for each site manually

# NOT IMPLEMENTED IN THE SCRIPTS
# [opportunity_header]
# Change: acctlink should not have a default of -1
# Change: contactlink should not have a default of -1

# Add any necessary default permissions manually
# [role_permission]


