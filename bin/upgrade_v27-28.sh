# Service contracts, assets, category editor
ant upgradedb -Darg1=2004-04-01-script01.sql -Darg2=dhv
# Service contract, asset permission category
ant upgradedb -Darg1=2004-04-01-script02.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-01-script03.bsh -Darg2=dhv
# Category editor entries
ant upgradedb -Darg1=2004-04-01-script04.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-01-script05.bsh -Darg2=dhv
# Service contract, asset permissions
ant upgradedb -Darg1=2004-04-01-script06.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-01-script07.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-01-script08.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-01-script09.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-01-script10.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-01-script11.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-04-13-script01.bsh -Darg2=dhv
# Portal user role column
ant upgradedb -Darg1=2004-04-13-script02.sql -Darg2=dhv
# Add customer role
ant upgradedb -Darg1=2004-04-13-script03.bsh -Darg2=dhv
# Remove unused ticket fields
ant upgradedb -Darg1=2004-04-20.sql -Darg2=dhv
# Product category changes
ant upgradedb -Darg1=2004-04-29.sql -Darg2=dhv
# Ticket table changes to support products
ant upgradedb -Darg1=2004-04-30.sql -Darg2=dhv
# Sync client changes, future support for coded clients
ant upgradedb -Darg1=2004-05-04.sql -Darg2=dhv
# Install Jasper Reports
ant upgradedb -Darg1=2004-05-07-script01.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-07-script02.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-07-script03.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-07-script04.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-07-script05.bsh -Darg2=dhv
# Insert Product Catalog folders
ant upgradedb -Darg1=2004-05-10-script01.bsh -Darg2=dhv
# Service contract products
ant upgradedb -Darg1=2004-05-10-script02.sql -Darg2=dhv
# Product-catalog permissions
ant upgradedb -Darg1=2004-05-10-script03.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-10-script04.bsh -Darg2=dhv
# Import table, field additions, permissions
ant upgradedb -Darg1=2004-05-17-script01.sql -Darg2=dhv
ant upgradedb -Darg1=2004-05-17-script02.bsh -Darg2=dhv
# Call volume reports
ant upgradedb -Darg1=2004-05-25-script01.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-25-script02.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-25-script03.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-25-script04.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-25-script05.bsh -Darg2=dhv
ant upgradedb -Darg1=2004-05-25-script06.bsh -Darg2=dhv

# Manually upgrade the help for each site
echo Execute 'ant upgradedb.help' for each site manually

# Add any necessary default permissions manually
echo Add any necessary default permissions manually


