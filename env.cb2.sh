#!/bin/sh
# $Id: env.cb2.sh,v 1.8 2001/12/20 19:15:17 mrajkowski Exp $
# $Revision: 1.8 $
#
# Common shell environment settings
#
export CFS_HOME=/usr/local/cfs2
export JAVA_HOME=/usr/local/java/jdk1.3.1_02
export APACHE_HOME=/etc/httpd
export TOMCAT_HOME=/usr/local/java/jakarta-tomcat-3.2.3
export ANT_HOME=/usr/local/java/jakarta-ant-1.4

export PATH=$PATH:${JAVA_HOME}/bin:${ANT_HOME}/bin
export CLASSPATH=.:${TOMCAT_HOME}/lib/servlet.jar:${TOMCAT_HOME}/lib/mail.jar:${TOMCAT_HOME}/lib/xerces.jar:${TOMCAT_HOME}/lib/activation.jar:${TOMCAT_HOME}/lib/jdbc7.1-1.3.jar:${TOMCAT_HOME}/lib/mail.jar:${TOMCAT_HOME}/lib/mailapi.jar:${TOMCAT_HOME}/lib/smtp.jar:/home/ds21/html/WEB-INF/lib/darkhorseventures.jar:/home/ds21/html/WEB-INF/lib/theseus.jar
