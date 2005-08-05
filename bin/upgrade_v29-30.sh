#!/bin/sh
# This script upgrades the 2.9 production databases to 3.0

ant upgradedb -Darg1=upgrade_v29-30.txt -Darg2=


# Manually upgrade the help for each site
echo ...
echo Execute 'ant upgradedb.help' for each site manually

# Add any necessary default permissions manually
echo ...
echo Add any necessary default permissions manually


