#!/bin/sh
# $Id$
# $Revision$
#
# Common shell environment settings
#

export SITE_LIB=/home/matt/webapps/cfs/WEB-INF/lib
export TOMCAT_HOME=/usr/local/jakarta/jakarta-tomcat-4.1.10-LE-jdk14
export CLASSPATH=.:${TOMCAT_HOME}/common/lib/servlet.jar:${TOMCAT_HOME}/common/lib/mail.jar:${TOMCAT_HOME}/common/lib/mailapi.jar:${TOMCAT_HOME}/common/lib/smtp.jar:${TOMCAT_HOME}/common/lib/xerces.jar:${SITE_LIB}/darkhorseventures.jar:${SITE_LIB}/theseus.jar:${SITE_LIB}/zeroio-iteam.jar:${TOMCAT_HOME}/common/lib/activation.jar:${TOMCAT_HOME}/common/lib/pgjdbc2.jar:${SITE_LIB}/jakarta-poi-1.5.0.jar:${TOMCAT_HOME}/common/lib/commons-logging-api.jar:${TOMCAT_HOME}/common/lib/msbase.jar:${TOMCAT_HOME}/common/lib/mssqlserver.jar:${TOMCAT_HOME}/common/lib/msutil.jar:${SITE_LIB}/ccp_integration.jar

# Site specific variables during installation
GKDRIVER="org.postgresql.Driver"
#GKHOST="jdbc:postgresql://127.0.0.1:5432/cfs2gk"
GKHOST="jdbc:postgresql://216.54.81.101:5432/cfs2gk"
SITECODE="cfs"
DBSITECODE="ds21"
SITEHOME="/home/matt/webapps"
DEST_DOCROOT="/home/matt/webapps/cfs"
DEBUGLEVEL="2"
