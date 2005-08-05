#!/bin/sh
# This script upgrades the 3.0 production databases to 3.1

ant upgradedb -Darg1=upgrade_v30-31.txt -Darg2=


# Add any necessary default permissions to roles manually
echo ...
echo Add any necessary default permissions manually


