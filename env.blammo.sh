#!/bin/sh
# $Id$
# $Revision$
#
# Common shell environment settings
#
#export JAVA_HOME=/usr/local/java/jdk1.3.1_01
#export APACHE_HOME=/etc/httpd
#export TOMCAT_HOME=/usr/local/java/jakarta-tomcat-4.0
#export TOMCAT_HOME=/usr/local/java/jakarta-tomcat-3.2.3
#export ANT_HOME=/usr/local/java/jakarta-ant-1.4
#export SITE_LIB=/home/cfs/html/WEB-INF/lib

export SITE_LIB=${TOMCAT_HOME}/webapps/cfs2/WEB-INF/lib

export PATH=$PATH:${JAVA_HOME}/bin:${ANT_HOME}/bin

export CLASSPATH=.:${TOMCAT_HOME}/common/lib/servlet.jar:${TOMCAT_HOME}/common/lib/mail.jar:${TOMCAT_HOME}/common/lib/mailapi.jar:${TOMCAT_HOME}/common/lib/smtp.jar:${TOMCAT_HOME}/common/lib/xerces.jar:${SITE_LIB}/darkhorseventures.jar:${SITE_LIB}/theseus.jar:${TOMCAT_HOME}/common/lib/activation.jar:${TOMCAT_HOME}/common/lib/jdbc7.1-1.3.jar

