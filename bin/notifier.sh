# This is a temporary script until jcrontab is implemented to run the notifier
# Must run "ant deploy" first
# $Id$
source /etc/profile
ant -f ${CFS_HOME}/WEB-INF/build.xml run.notifier
