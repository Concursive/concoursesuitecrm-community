#!/bin/sh
# This script upgrades the 2.8 production databases to 2.9

# Project Management
ant upgradedb -Darg1=2004-07-02-script01.sql -Darg2=
ant upgradedb -Darg1=2004-07-02-script02.bsh -Darg2=
ant upgradedb -Darg1=2004-07-02-script03.bsh -Darg2=
ant upgradedb -Darg1=2004-07-02-script04.bsh -Darg2=
ant upgradedb -Darg1=2004-07-02-script05.sql -Darg2=

# Service Contract
ant upgradedb -Darg1=2004-07-19.sql -Darg2=

# Activities, Primary Phone, Ticket Indexes
ant upgradedb -Darg1=2004-07-22.sql -Darg2=

# Lookup lists
ant upgradedb -Darg1=2004-08-19.bsh -Darg2=

# New permissions for activities
ant upgradedb -Darg1=2004-08-20.bsh -Darg2=

# Timezone field additions
ant upgradedb -Darg1=2004-08-25-f-release9_20040810-ak.sql -Darg2=
ant upgradedb -Darg1=2004-08-26-partha.sql -Darg2=
ant upgradedb -Darg1=2004-08-30-partha.sql -Darg2=
ant upgradedb -Darg1=2004-08-31-partha.bsh -Darg2=

# Rename existing calls permissions
ant upgradedb -Darg1=2004-10-12.sql -Darg2=

# Manually upgrade the help for each site
echo ...
echo Execute 'ant upgradedb.help' for each site manually

# Add any necessary default permissions manually
echo ...
echo Add any necessary default permissions manually


