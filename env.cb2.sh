#!/bin/sh
# $Id: env.cb2.sh,v 1.8 2001/12/20 19:15:17 mrajkowski Exp $
# $Revision: 1.8 $
#
# Common shell environment settings
#
export TOMCAT_HOME=/usr/local/java/tomcat
export JAVA_HOME=/usr/local/java/j2se
export ANT_HOME=/usr/local/java/ant
PATH=$PATH:${JAVA_HOME}/bin:${ANT_HOME}/bin

export SITE_LIB=/home/cfs/html/WEB-INF/lib

export CLASSPATH=.:${TOMCAT_HOME}/common/lib/servlet.jar:${TOMCAT_HOME}/common/lib/mail.jar:${TOMCAT_HOME}/common/lib/mailapi.jar:${TOMCAT_HOME}/common/lib/smtp.jar:${TOMCAT_HOME}/lib/xerces.jar:${SITE_LIB}/darkhorseventures.jar:${SITE_LIB}/theseus.jar:${TOMCAT_HOME}/common/lib/activation.jar:${TOMCAT_HOME}/common/lib/pgjdbc2.jar:${SITE_LIB}/zeroio-iteam.jar

# Site specific variables during installation
GKDRIVER="org.postgresql.Driver"
GKHOST="jdbc:postgresql://127.0.0.1:5432/cfs2gk"
SITECODE="cfs"
DBSITECODE="ds21"
SITEHOME="/home"
FORCESSL="true"
DEBUGLEVEL="2"
