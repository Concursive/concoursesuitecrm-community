#!/bin/sh
# This script upgrades the 2.7 production databases to 2.8

# Service contracts, assets, category editor
ant upgradedb -Darg1=2004-04-01-script01.sql -Darg2=

# Service contract, asset permission category
ant upgradedb -Darg1=2004-04-01-script02.bsh -Darg2=
ant upgradedb -Darg1=2004-04-01-script03.bsh -Darg2=

# Category editor entries
ant upgradedb -Darg1=2004-04-01-script04.bsh -Darg2=
ant upgradedb -Darg1=2004-04-01-script05.bsh -Darg2=

# Service contract, asset permissions
ant upgradedb -Darg1=2004-04-01-script06.bsh -Darg2=
ant upgradedb -Darg1=2004-04-01-script07.bsh -Darg2=
ant upgradedb -Darg1=2004-04-01-script08.bsh -Darg2=
ant upgradedb -Darg1=2004-04-01-script09.bsh -Darg2=
ant upgradedb -Darg1=2004-04-01-script10.bsh -Darg2=
ant upgradedb -Darg1=2004-04-01-script11.bsh -Darg2=
ant upgradedb -Darg1=2004-04-13-script01.bsh -Darg2=

# Portal user role column
ant upgradedb -Darg1=2004-04-13-script02.sql -Darg2=

# Add customer role
ant upgradedb -Darg1=2004-04-13-script03.bsh -Darg2=

# Remove unused ticket fields
ant upgradedb -Darg1=2004-04-20.sql -Darg2=

# Product category changes
ant upgradedb -Darg1=2004-04-29.sql -Darg2=

# Ticket table changes to support products
ant upgradedb -Darg1=2004-04-30.sql -Darg2=

# Sync client changes, future support for coded clients
ant upgradedb -Darg1=2004-05-04.sql -Darg2=

# Install Jasper Reports
ant upgradedb -Darg1=2004-05-07-script01.bsh -Darg2=
ant upgradedb -Darg1=2004-05-07-script02.bsh -Darg2=
ant upgradedb -Darg1=2004-05-07-script03.bsh -Darg2=
ant upgradedb -Darg1=2004-05-07-script04.bsh -Darg2=
ant upgradedb -Darg1=2004-05-07-script05.bsh -Darg2=

# Insert Product Catalog folders
ant upgradedb -Darg1=2004-05-10-script01.bsh -Darg2=

# Service contract products
ant upgradedb -Darg1=2004-05-10-script02.sql -Darg2=

# Product-catalog permissions (currently set to disabled)
ant upgradedb -Darg1=2004-05-10-script03.bsh -Darg2=
ant upgradedb -Darg1=2004-05-10-script04.bsh -Darg2=


# Import table, field additions, permissions
ant upgradedb -Darg1=2004-05-17-script01.sql -Darg2=
ant upgradedb -Darg1=2004-05-17-script02.bsh -Darg2=

# Call volume reports
ant upgradedb -Darg1=2004-05-25-script01.bsh -Darg2=
ant upgradedb -Darg1=2004-05-25-script02.bsh -Darg2=
ant upgradedb -Darg1=2004-05-25-script03.bsh -Darg2=
ant upgradedb -Darg1=2004-05-25-script04.bsh -Darg2=
ant upgradedb -Darg1=2004-05-25-script05.bsh -Darg2=
ant upgradedb -Darg1=2004-05-25-script06.bsh -Darg2=

# quote tables
ant upgradedb -Darg1=2004-06-04.sql -Darg2=

# Admin admin-sysconfig-products permission
ant upgradedb -Darg1=2004-06-04-script02.bsh -Darg2=

# Default data for new capabilities
ant upgradedb -Darg1=2004-06-04-script03.sql -Darg2=

# Added for final release of 2.8
ant upgradedb -Darg1=2004-06-21.bsh -Darg2=
ant upgradedb -Darg1=2004-06-22.sql -Darg2=
ant upgradedb -Darg1=2004-06-23.sql -Darg2=


# Manually upgrade the help for each site
echo ...
echo Execute 'ant upgradedb.help' for each site manually

# Add any necessary default permissions manually
echo ...
echo Add any necessary default permissions manually


