#!/bin/sh
# This script upgrades the 3.1 production databases to 4.0

ant upgradedb -Darg1=upgrade_v31-32.txt


# Add any necessary default permissions to roles manually
echo ...
echo Add any necessary default permissions manually using the application
