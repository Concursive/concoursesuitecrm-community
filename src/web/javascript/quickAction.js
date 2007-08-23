function quickAction(id) {
  if (id == '0') {
    return true;
  }
   if (id == 'lead') {
    popURL('Sales.do?command=Add&actionSource=GlobalItem&popup=true','Lead','600','500','yes','yes');
  }
  if (id == 'quote') {
    popURL('Quotes.do?command=AddQuoteForm&actionSource=GlobalItem&popup=true','Quote','600','500','yes','yes');
  }
  if (id == 'ticket') {
    popURL('TroubleTickets.do?command=Add&actionSource=GlobalItem&popup=true','Ticket','600','500','yes','yes');
  }
  if (id == 'opportunity') {
    popURL('Leads.do?command=Prepare&actionSource=GlobalItem&popup=true','Opportunity','600','500','yes','yes');
  }
}
