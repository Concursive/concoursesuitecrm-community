/* contacts */
CREATE INDEX "contactlist_namecompany" ON "contact" (namelast, namefirst, company);
CREATE INDEX "contactlist_company" ON "contact" (company, namelast, namefirst);

/* organizations */
CREATE INDEX "orglist_name" ON "organization" (name);

/* opportunities */
CREATE INDEX "oppcomplist_closedate" ON "opportunity_component" (closedate);
CREATE INDEX "oppcomplist_description" ON "opportunity_component" (description);

/* tickets */
CREATE INDEX "ticketlist_entered" ON "ticket" (entered);

