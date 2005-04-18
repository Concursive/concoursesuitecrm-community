#!/bin/sh
# This script upgrades the 2.9 production databases to 3.0


#   **[UPDATED PRODUCTION SERVER TO THIS POINT]**

# Permission Category Constants
ant upgradedb -Darg1=2005-03-09-script01.sql -Darg2=
ant upgradedb -Darg1=2005-03-10-script01.sql -Darg2=

# Product Catalog Editor
ant upgradedb -Darg1=2004-09-21-script01.sql -Darg2=
ant upgradedb -Darg1=2004-09-21-script02.sql -Darg2=
#ant upgradedb -Darg1=2004-09-24-script01.bsh -Darg2=
ant upgradedb -Darg1=2004-09-24-script02.sql -Darg2=
ant upgradedb -Darg1=2004-10-15-script01.bsh -Darg2=
ant upgradedb -Darg1=2004-10-15.sql -Darg2=

# WebDAV feature / Permission Category changes
ant upgradedb -Darg1=2004-11-17-script01.sql -Darg2=
ant upgradedb -Darg1=2005-01-26-script01.sql -Darg2=
ant upgradedb -Darg1=2004-11-17-script02.bsh -Darg2=
ant upgradedb -Darg1=2004-11-17-script03.bsh -Darg2=

# Add Permission Category (Some already have this, so a check is made)
ant upgradedb -Darg1=2004-10-00-script01.bsh -Darg2=
ant upgradedb -Darg1=2004-10-00-script02.bsh -Darg2=
ant upgradedb -Darg1=2004-10-00-script03.bsh -Darg2=

# Quote Management
ant upgradedb -Darg1=2004-11-29-script01.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script02.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script03.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script04.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script05.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script06.bsh -Darg2=
ant upgradedb -Darg1=2004-11-29-script07.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script08.bsh -Darg2=

# Additional Adjustments
ant upgradedb -Darg1=2004-11-29-script09.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script10.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script11.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script12.sql -Darg2=
ant upgradedb -Darg1=2004-11-29-script13.bsh -Darg2=

# Document Management
ant upgradedb -Darg1=2004-12-02-script1.sql -Darg2=
ant upgradedb -Darg1=2004-12-02-script2.bsh -Darg2=
ant upgradedb -Darg1=2004-12-02-script3.bsh -Darg2=
ant upgradedb -Darg1=2004-12-02-script4.bsh -Darg2=
ant upgradedb -Darg1=2004-12-09.bsh -Darg2=

# Merge f-improvements-20041201
ant upgradedb -Darg1=2004-12-07-script01.sql -Darg2=
ant upgradedb -Darg1=2004-12-08gk.sql -Darg2=
ant upgradedb -Darg1=2004-12-20-script01.bsh -Darg2=

# Merge f-contact_request-20041216
ant upgradedb -Darg1=2004-12-16-script1.bsh -Darg2=
ant upgradedb -Darg1=2004-12-17-script1.sql -Darg2=

# Merge bug-version29_20050114
ant upgradedb -Darg1=2005-01-14.sql -Darg2=

# Merge f-contact_request-20050103
ant upgradedb -Darg1=2005-01-18-script01.sql -Darg2=
ant upgradedb -Darg1=2005-01-18-script02.bsh -Darg2=

# Merge f-folders-20050120
ant upgradedb -Darg1=2005-01-20-script01.sql -Darg2=
ant upgradedb -Darg1=2005-01-20-script02.bsh -Darg2=
ant upgradedb -Darg1=2005-01-20-script03.bsh -Darg2=
ant upgradedb -Darg1=2005-01-26-script02.bsh -Darg2=
ant upgradedb -Darg1=2005-01-26-script02.sql -Darg2=
ant upgradedb -Darg1=2005-01-27-script01.sql -Darg2=
ant upgradedb -Darg1=2005-01-27-script02.bsh -Darg2=

# Merge f-contact_request-20050119
ant upgradedb -Darg1=2005-01-20-script01.bsh -Darg2=
ant upgradedb -Darg1=2005-01-24-script01.sql -Darg2=
ant upgradedb -Darg1=2005-02-07-script01.bsh -Darg2=

# Merge Project Management
ant upgradedb -Darg1=2005-03-28-script01.sql -Darg2=
ant upgradedb -Darg1=2005-02-15-script01.sql -Darg2=

# Merge bug-version3-20050210
ant upgradedb -Darg1=2005-02-17-script01.sql -Darg2=

# Industry list
ant upgradedb -Darg1=2005-03-02-script01.bsh -Darg2=

# Account Project permissions and Project permissions
ant upgradedb -Darg1=2005-03-02-script02.bsh -Darg2=
ant upgradedb -Darg1=2005-03-02-script03.bsh -Darg2=
ant upgradedb -Darg1=2005-03-02-script04.sql -Darg2=

# Project templates
ant upgradedb -Darg1=2005-02-07-script01.bsh -Darg2=

# Employee projects
ant upgradedb -Darg1=2005-03-02-script05.sql -Darg2=

# Lookup list changes
ant upgradedb -Darg1=2005-03-03-script01.sql -Darg2=

# Product Catalog Editor v2
ant upgradedb -Darg1=2005-03-01-script01.bsh -Darg2=
ant upgradedb -Darg1=2005-03-01-script02.sql -Darg2=

# Leads Module
ant upgradedb -Darg1=2005-02-28-script01.bsh -Darg2=
ant upgradedb -Darg1=2005-02-28-script02.bsh -Darg2=
ant upgradedb -Darg1=2005-02-28-script03.sql -Darg2=

# Leads Module (merge4)
ant upgradedb -Darg1=2005-03-16-script01.bsh -Darg2=
ant upgradedb -Darg1=2005-03-17-script01.bsh -Darg2=

# Product
ant upgradedb -Darg1=2005-03-11-script01.sql -Darg2=

# Product catalog permissions
ant upgradedb -Darg1=2005-03-28-script01.bsh -Darg2=

# Missing leads lookup editors
ant upgradedb -Darg1=2005-04-09-script01.bsh -Darg2=


# Manually upgrade the help for each site
echo ...
echo Execute 'ant upgradedb.help' for each site manually

# Add any necessary default permissions manually
echo ...
echo Add any necessary default permissions manually


