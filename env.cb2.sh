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

export CLASSPATH=.:${TOMCAT_HOME}/common/lib/servlet.jar:${TOMCAT_HOME}/common/lib/mail.jar:${TOMCAT_HOME}/common/lib/activation.jar:${TOMCAT_HOME}/common/lib/pgjdbc2.jar:/home/cfs/html/WEB-INF/lib/darkhorseventures.jar:/home/cfs/html/WEB-INF/lib/theseus.jar:/home/cfs/html/WEB-INF/lib/zeroio-iteam.jar

# Site specific variables during installation
GKDRIVER="org.postgresql.Driver"
GKHOST="jdbc:postgresql://127.0.0.1:5432/cfs2gk"
SITECODE="cfs"
DBSITECODE="ds21"
SITEHOME="/home"
FORCESSL="true"
DEBUGLEVEL="2"
