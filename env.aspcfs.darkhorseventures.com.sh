#!/bin/sh
# $Id$
# $Revision$
#
# Common shell environment settings
#

#Added to /etc/profile
#export CATALINA_HOME=/usr/java/jakarta-tomcat-4.1.9-LE-jdk14
#export TOMCAT_HOME=${CATALINA_HOME}
#export JAVA_HOME=/usr/java/j2sdk1.4.0_01
#export ANT_HOME=/usr/java/jakarta-ant-1.5
#PATH=$PATH:${JAVA_HOME}/bin:${ANT_HOME}/bin

export SITE_LIB=/home/cfs/html/WEB-INF/lib

export CLASSPATH=.:${TOMCAT_HOME}/common/lib/servlet.jar:${TOMCAT_HOME}/common/lib/mail.jar:${TOMCAT_HOME}/common/lib/mailapi.jar:${TOMCAT_HOME}/common/lib/smtp.jar:${TOMCAT_HOME}/lib/xerces.jar:${SITE_LIB}/darkhorseventures.jar:${SITE_LIB}/theseus.jar:${TOMCAT_HOME}/common/lib/activation.jar:${TOMCAT_HOME}/common/lib/pgjdbc2.jar:${SITE_LIB}/zeroio-iteam.jar

# Site specific variables during installation
GKDRIVER="org.postgresql.Driver"
GKHOST="jdbc:postgresql://127.0.0.1:5432/cfs2gk"
SITECODE="cfs"
DBSITECODE="cfs"
SITEHOME="/home"
FORCESSL="true"
DEBUGLEVEL="2"

