#!/bin/sh
#
# Sample script which will process yesterday's web access log through webalizer.
#
# The default location for the usage report is /var/www/html/usage/index.html
#
# Execute from CRON using:
#   0 1 * * * /bin/sh /home/webapps/ROOT/WEB-INF/weblog.sh
#
webalizer -Q ${CATALINA_HOME}/logs/cfs_access_log.`date --date='yesterday' +%Y-%m-%d`.txt

