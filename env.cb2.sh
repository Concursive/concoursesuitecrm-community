#!/bin/sh
# $Id: env.cb2.sh,v 1.8 2001/12/20 19:15:17 mrajkowski Exp $
# $Revision: 1.8 $
#
# Common shell environment settings
#
export TOMCAT_HOME=/usr/local/java/jakarta-tomcat-4.0.2-LE-jdk14
export JAVA_HOME=/usr/local/java/j2sdk1.4.0
export ANT_HOME=/usr/local/java/jakarta-ant-1.4.1
PATH=$PATH:${JAVA_HOME}/bin:${ANT_HOME}/bin

export CLASSPATH=.:${TOMCAT_HOME}/common/lib/servlet.jar:${TOMCAT_HOME}/common/lib/mail.jar:${TOMCAT_HOME}/common/lib/activation.jar:${TOMCAT_HOME}/common/lib/pgjdbc2.jar:/home/cfs/html/WEB-INF/lib/darkhorseventures.jar:/home/cfs/html/WEB-INF/lib/theseus.jar:/home/cfs/html/WEB-INF/lib/zeroio-iteam.jar

# Site specific variables during installation
GKHOST="jdbc:postgresql://127.0.0.1:5432/cfs2gk"
SITECODE="cfs"
SITEHOME="/home"
FORCESSL="true"

