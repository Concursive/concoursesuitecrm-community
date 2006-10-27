#!/bin/sh

ant restore.database -Darg1=http://127.0.0.1:8080/centric40/ProcessPacket.do -Darg2=127.0.0.1 -Darg3=md5passwordHash
