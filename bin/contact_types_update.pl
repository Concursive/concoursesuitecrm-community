#!/usr/bin/perl
use Pg;
use CGI qw(:standard);

##########################################################
# contact_type_udpate.pl: Scans the contact table and inserts
# contact types into the new contact_type_levels table.
#
# Mathur, Dark Horse Ventures
# 12-17-2002
#
# usage: contact_types_update.pl <hostname> <dbname>
#
# 
###########################################################


$DBNAME = '';


sub main (){
	$DBName = $ARGV[1];
	$HostName = $ARGV[0];
	print "Connecting to $DBName on $HostName .. \n";
	$c = Pg::connectdb("port=5432 host=$HostName dbname=$DBName");
	$q = "SELECT contact_id,type_id from contact where type_id IS NOT NULL ";
	print "$q\n";
	$r = $c->exec($q);
	if ($r->resultStatus != PGRES_TUPLES_OK){
		$errmsg = $c->errorMessage;
		print "error: $errmsg\n";
		exit(3);
	}

	for ($row = 0; $row < $r->ntuples; $row++){
		$contact_id = $r->getvalue($row,0);
		$type_id = $r->getvalue($row,1);
		$query = "insert into contact_type_levels ( contact_id, type_id, level) values ( '$contact_id', '$type_id', '0');";
		print "$row -- $query\n";
		$result = $c->exec($query);
	}
}

&main();
