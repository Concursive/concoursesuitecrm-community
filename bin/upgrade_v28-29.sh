#!/bin/sh
# This script upgrades the 2.8 production databases to 2.9

# Project Management
ant upgradedb -Darg1=2004-07-02-script01.sql -Darg2=cfs2-core
ant upgradedb -Darg1=2004-07-02-script02.bsh -Darg2=cfs2-core
ant upgradedb -Darg1=2004-07-02-script03.bsh -Darg2=cfs2-core
ant upgradedb -Darg1=2004-07-02-script04.bsh -Darg2=cfs2-core

# Service Contract
ant upgradedb -Darg1=2004-07-19.sql -Darg2=cfs2-core

# Activities, Primary Phone, Ticket Indexes
ant upgradedb -Darg1=2004-07-22.sql -Darg2=cfs2-core


# Manually upgrade the help for each site
echo ...
echo Execute 'ant upgradedb.help' for each site manually

# Add any necessary default permissions manually
echo ...
echo Add any necessary default permissions manually


