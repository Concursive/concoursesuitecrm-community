#!/bin/sh

DATA_BASE=$1

BKUP_DIR=/home/backup/DB_BACKUP

BZIP2=/usr/bin/bzip2
GZIP=/bin/gzip
PG_DUMP=/usr/bin/pg_dump
PG_DUMPALL=/usr/bin/pg_dumpall

DATE=`date +%Y%m%d`
MONTH=`date +%Y%m`
WEEK_DAY=`date +%a`
 
PG_OPTS=-D

if [ -z $DATA_BASE ] ; then
  echo "Usage: postgres-backup [all|database name]"
  exit
fi

/bin/mkdir -p ${BKUP_DIR}

# All databases
if [ "$DATA_BASE" == 'all' ] ; then
  $PG_DUMPALL $PG_OPTS | $BZIP2 > ${BKUP_DIR}/database_all_${WEEK_DAY}.bz2
fi

# Specific database
if [ "$DATA_BASE" != 'all' ] ; then
  $PG_DUMP $PG_OPTS $DATA_BASE | $BZIP2 > ${BKUP_DIR}/${DATA_BASE}_${WEEK_DAY}.bz2
fi
