#!/usr/bin/perl
use Pg;
use CGI qw(:standard);

##########################################################
# grab.pl: operates for a particular customer to monitor
# industry space (MINER).
#
# Chris S. Price, Dark Horse Ventures
# 1-10-2001
#
# usage: grab.pl <sitecode>
#
# dbname assumed as cdb_<sitecode>
# 
# $LOGFILE == log destination
##########################################################
##########################################################
# 3-15-01 dcw.
#
# modified to get company names from the site database
# argument is the site code.
##########################################################

$LOGFILE = "/home/chris/grab.log";
$DBNAME = '';
#$SITE_CODE = '';

$base = "http://www.prnewswire.com";

sub insert {
	my $head = shift(@_);
	my $url = shift(@_);
	my $date = shift(@_);
	my $cid = $_[0];

	my $tempcount = 0;

	my $count = scalar( @$head );
	my $idx = 0;

	my $new=0;
	my $old=0;

	#print "COUNT IS $count\n";

        my $type = $rqpairs{'type'};
        my $email = $rqpairs{'email'};

        $conn = Pg::connectdb("port=5432 dbname=$DBNAME user=cfsdba");
	
	while ( $idx < $count ) {
        	my $head = @$head[$idx];

        	$head =~ s/\'/\'\'/g;
		$head =~ s/[\n\r]//g;

		#print "HEADLINE: $head\n";

		$query = "insert into news ( headline, url, base, body, dateentered, org_id, type ) values ( '$head', '@$url[$idx]', 'PRNews', 'no body', '@$date[$idx]', $cid, 'A' );";

		#print "$query\n";
		$query2 = "select rec_id from news where headline = '$head';";

        	$result2 = $conn->exec("$query2");
		$test = $result2->ntuples;

		if ( $test eq 0 ) {
			print LOG "NEW: $head\n\n";

      			$result3 = $conn->exec("$query");
			$tempcount++;
		}
		else {
			$old++;
		}

		$idx++;
	}

	print LOG "$tempcount NEW items\n";
	print LOG "$old OLD items\n";
	
	return;
}

sub main () {

	open LOG, ">>$LOGFILE" or die "Cannot open $LOGFILE for write :$!";

	$c = Pg::connectdb("port=5432 dbname=cfs2gk user=cfsdba");
	$q = "SELECT dbname from sites where enabled = 't' ";
	$r = $c->exec($q);

	if ($r->resultStatus != PGRES_TUPLES_OK)
	{
		$errmsg = $conn->errorMessage;
		print LOG "error: $errmsg\n";
		exit(3);
	}

	for ($rowcount = 0; $rowcount < $r->ntuples; $rowcount++)
	{
		$DBNAME = $r->getvalue($rowcount,0);
		print LOG "trying - $DBNAME got" . ($r->ntuples) . "\n\n";

		print LOG "----------------------------\n";
        	print LOG `date`;
        	print LOG "database: $DBNAME\n";

		if (!$DBNAME)
		{
			print LOG "Exiting: missing parameter(s):\n";
			print LOG "         parameter: DB        ='$DBNAME'\n";
			exit(1);
		}

		print LOG "database: $DBNAME\n"; 
		$conn = Pg::connectdb("port=5432 dbname=$DBNAME user=cfsdba");

		if ($conn->status != PGRES_CONNECTION_OK)
		{
			$errmsg = $conn->errorMessage;
			print LOG "error: $errmsg\n";
			exit(2);
		}

		$query = "SELECT org_id,name from organization where duplicate_id = -1 ";
		$result = $conn->exec($query);
		if ($result->resultStatus != PGRES_TUPLES_OK)
		{
			$errmsg = $conn->errorMessage;
			print LOG "error: $errmsg\n";
			exit(3);
		}

		for ($row = 0; $row < $result->ntuples; $row++)
		{
			$org_id = $result->getvalue($row,0);
			$company_name = $result->getvalue($row,1);

			##	change spaces to plus signs
			$company_name =~ s/ /+/g;

			#print LOG "Processing $company_name, id ($org_id) for '$SITE_CODE'\n";
			print LOG "Processing $company_name, id ($org_id) for '$DBNAME'\n";
   			&doit( $org_id, $company_name );
		}
	}
}


sub doit () {

my $custid = $_[0];
my $comp = $_[1];
my $state = '';
my $indust = '';

my $url = "\"$base/prn/owa/keysearch_sa\?INDUST=" . $indust . "&STATE=" . $state . "&COMP=" . $comp . "\"";

my $command = "snarf -pnv $url out.html";
my $file = "out.html";

my @headlines = ();
my @urls = ();
my @dates = ();

#print "$command\n";

`$command`;

print LOG "\n";

open FILE, $file or die "can't open output file...\n";

while ( $_ = <FILE> ) {
	if ( /<DD>/ ) {
		$temp = $_;
		$temp =~ s/<DD>//g;
		$temp =~ s/<\/DL>//g;

		push ( @headlines, $temp );
	}
	if ( /<DL><DT>/ ) {
		$temp = $_;
		$temp =~ s/<DL><DT><A HREF=\"//g;
		$temp =~ s/\">//g;
		$temp = $base . $temp;

		/([0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9])/;
		$thedate = $1;

		$thedate =~ s/([0-9][0-9])-([0-9][0-9])-([0-9][0-9][0-9][0-9])/\3-\1-\2/;
		push ( @dates, $thedate );
		push ( @urls, $temp );
	}
}

&insert( \@headlines, \@urls, \@dates, $custid );

close (FILE);
`rm -rf out.html`;
print LOG "----------------------------\n";

return;


}

&main();
