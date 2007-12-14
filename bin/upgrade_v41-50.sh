#!/bin/sh
# This script upgrades a 4.1 database to 5.0

ant upgradedb -Darg1=upgrade_v41-42.txt

# Manually upgrade the help
echo ...
echo Execute 'ant upgradedb.help' manually

# Add any necessary default permissions to roles manually
echo ...
echo Add any necessary default permissions manually using the application
