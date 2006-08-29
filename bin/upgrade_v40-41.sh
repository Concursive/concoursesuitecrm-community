#!/bin/sh
# This script upgrades the 4.0 production databases to 4.1

ant upgradedb -Darg1=upgrade_v40-41.txt


# Add any necessary default permissions to roles manually
echo ...
echo Add any necessary default permissions manually using the application
