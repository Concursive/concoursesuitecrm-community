#!/bin/sh

GZIP=/bin/gzip
BKUP_DIR=/home/backup/DB_BACKUP

DB_VERSION=$1
DATA_HOST=$2
DATA_BASE=$3
WEEK_DAY=$4

# Options for PostgreSQL 7.3+
PG_OPTS=-D
# Options for older versions...
if [ "$DATA_BASE" == '723' ] ; then
  PG_OPTS=-NOD
fi


/bin/mkdir -p ${BKUP_DIR}/${DATA_HOST}

# All databases
if [ "$DATA_BASE" == 'all' ] ; then
  PG_DUMP=/var/lib/pgsql/pg_dumpall${DB_VERSION}
  $PG_DUMP $PG_OPTS --ignore-version -h $DATA_HOST| $GZIP > ${BKUP_DIR}/${DATA_HOST}/database_${WEEK_DAY}.gz 
fi

# Specific database
if [ "$DATA_BASE" != 'all' ] ; then
  PG_DUMP=/var/lib/pgsql/pg_dump${DB_VERSION}
  $PG_DUMP $PG_OPTS --ignore-version -h $DATA_HOST $DATA_BASE | $GZIP > ${BKUP_DIR}/${DATA_HOST}/${DATA_BASE}_${WEEK_DAY}.gz 
fi
