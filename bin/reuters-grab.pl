#!/usr/bin/perl
use Pg;
use CGI qw(:standard);

##########################################################
# reuters-grab.pl: operates for a particular customer to monitor
# industry space (MINER).
#
# Chris S. Price, Dark Horse Ventures
# 1-10-2001
#
# reuters-grab.pl <company webroot folder>
#
# file site.cfg must be present in <webroot>/database directory
#
# $LOGFILE == log destination
##########################################################

$LOGFILE = "/home/chris/reuters-grab.log";
$DBNAME = '';
$SITE_CODE = '';

$base = "http://www.reuters.com/";

sub insert {
	my $head = shift(@_);
	my $url = shift(@_);
	my $date = shift(@_);
	my $cid = $_[0];

	my $tempcount = 0;
	my $old = 0;

	my $count = scalar( @$head );
	my $idx = 0;

	#print "COUNT IS $count\n";

        my $type = $rqpairs{'type'};
        my $email = $rqpairs{'email'};

        $conn = Pg::connectdb("port=5432 dbname=$DBNAME user=chris");

	while ( $idx < $count ) {
        	my $head = @$head[$idx];

        	$head =~ s/\'/\'\'/g;
		$head =~ s/[\n\r]//g;

		$query = "insert into news ( headline, url, base, body, dateentered, org_id, type ) values ( '$head', '@$url[$idx]', 'Reuters', 'no body', '@$date[$idx]', $cid, 'A' );";

		#print LOG "$query\n";

		$query2 = "select rec_id from news where headline = '$head';";
        	$result2 = $conn->exec("$query2");
		$test = $result2->ntuples;
		print LOG "HEADLINE $head\n";

		if ( $test eq 0 ) {
			#print LOG "FOUND NEW: $head\n";
			#print LOG "NEW: $head\n\n";
      			$result = $conn->exec("$query");
			
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

my $site_config = "$ARGV[0]" . "/database/site.cfg";
open LOG, ">>$LOGFILE" or die "Cannot open $LOGFILE for write :$!";

print LOG `date`;
print LOG "$0\n";
print LOG "\nconfiguration: $site_config\n";
print LOG "database: $DBNAME\n";

open CONF, $site_config or die "Cannot open $site_config for read :$!";
        
        while (<CONF>) {
                chomp;
                next if (/^#/);
                s/^[ \t]*//;
                s/[ \t\r\n]*$//;
                next if (/^$/);

                if ( /^([A-Za-z_][A-Za-z0-9_]*)=(.*)$/ ) {
                        $key = $1;
                        $val = $2;
                        print LOG "Processing Entry: '$key'\n";

                        $SITE_CODE = $val if ($key =~ /SITE_CODE/);
                        $DBNAME = $val if ($key =~ /DB/);

                }
        }

        if (!$SITE_CODE || !$DBNAME)
        {
                print LOG "Exiting: missing parameter(s):\n";
                print LOG "         parameter: SITE_CODE ='$SITE_CODE'\n";
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

        $query = "SELECT org_id,name,ticker_symbol from organization where duplicate_id = -1 and ticker_symbol is not null ";
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
                $sym = $result->getvalue($row,2);

                ##      change spaces to plus signs
                $company_name =~ s/ /+/g;

                print LOG "Processing $company_name, id ($org_id) for '$SITE_CODE'\n";
                &doit( $org_id, $company_name, $sym );
        }



}

sub doit () {

my $custid = $_[0];
my $comp = $_[1];
my $sym = $_[2];
my $state = '';
my $indust = '';

my $url = "\"$base" . "quote.jhtml\?ticker=" . $sym . "\"";
my $command = "snarf -pnv $url out.html";
my $file = "out.html";

my @headlines = ();
my @urls = ();
my @dates = ();

`$command`;

print LOG "\n";
open FILE, $file or die "can't open output file...\n";

while ( $_ = <FILE> ) {
	if ( /class=\"newsLink\"/ ) {
		$temp = $_;
		$temp =~ s/
//g;
		$temp =~ s/	//g;
		$temp =~ s/<a href=\"//g;
		$temp =~ s/\" class=\"newsLink\">//g;
		$temp =~ s/ *//g;
		$temp = $base . $temp;

		#print "$temp\n";

		push ( @urls, $temp );
		if ( $temp =~ /NEWS\.REUT\.([0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])/ || $temp =~ /NEWS\.BW\.([0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])/ ) {
			$thedate = $1;
			$thedate =~ s/([0-9][0-9][0-9][0-9])([0-9][0-9])([0-9][0-9])/\1-\2-\3/;
			print LOG "Pushing $thedate\n";
			push ( @dates, $thedate );
		}
	}

	if ( /reformat headlines if necessary/ ) {
		$_ = <FILE>;
		$_ = <FILE>;
			
		$thishead = $_;
		$thishead =~ s/^\s*(.*?)\s*$/$1/;

		push ( @headlines, $thishead );
	}
}

&insert( \@headlines, \@urls, \@dates, $custid );

close (FILE);

`rm -rf out.html`;
print LOG "----------------------------\n";

}

&main();
