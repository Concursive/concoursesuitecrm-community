<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="NewsList" class="java.util.Vector" scope="request"/>
<jsp:useBean id="IndSelect" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="AlertsListSelection" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="AlertOppsList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="CompanyCalendar" class="com.darkhorseventures.utils.CalendarView" scope="request"/>
<table bgcolor=white border=0 width="100%">

	<tr>
	<td valign=top bgcolor=white width=300>
	<!--table bgcolor=white width=275 border=1 cellpadding=2 cellspacing=0 bordercolorlight="#000000" bordercolor="#FFFFFF">
		<tr bgcolor="#DEE0FA">
		<td width=100% valign=center align=center colspan=2>
		<strong>Current Weather</strong>
		</td>
		</tr>
		
		<tr><td valign=center>
		<a href="http://oap.weather.com/fcgi-bin/oap/redirect_magnet?loc_id=USVA0557&par=internal&site=magnet&code=357451&promo=english">
		<img border=0 width=270 height=140 SRC="http://oap.weather.com/fcgi-bin/oap/generate_magnet?loc_id=USVA0557&code=357451"></a>
		</td>
		</tr>
	</table-->
	
  <%  
    CompanyCalendar.setBorderSize(1);
    CompanyCalendar.setCellPadding(4);
    CompanyCalendar.setCellSpacing(0);
  %>
	<%= CompanyCalendar.getHtml() %>
	
	</td>

    <td bgcolor=white valign=top width=100%>
    <table bgcolor=white width=100% border=1 cellpadding=4 cellspacing=0 bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr bgcolor="#DEE0FA">
    <td valign=center>
    <strong>Alerts</strong>
    </td>
    </tr>
    
    <%
	Iterator n = AlertOppsList.iterator();
		
	if ( n.hasNext() ) {
		int rowid = 0;
		while (n.hasNext()) {
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
	          	
			Opportunity thisOpp = (Opportunity)n.next();
%>    
			<tr>
			<td class="row<%= rowid %>" valign=center>[<%=thisOpp.getAlertDate()%>] <a href="/Leads.do?command=DetailsOpp&id=<%= thisOpp.getId() %>&orgId=<%= thisOpp.getAccountLink() %>&contactId=<%= thisOpp.getContactLink() %>&return=list"><%=thisOpp.getDescription()%></a></td>
			<!--td class="row<%= rowid %>" valign=center><%=thisOpp.getAccountName()%></td>
			<td class="row<%= rowid %>" valign=center width=100><%=thisOpp.getAlertDate()%></td-->
			</tr>
<%    		}
	}
%>
    
    
    
    
    </table>
    <!--table bgcolor=white width=100% border=1 cellpadding=4 cellspacing=0 class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
      <form name="alerts" type="post" action="MyCFS.do">
      <input type=hidden name="auto-populate" value="true">
      <input type=hidden name=command value="Home">
      <tr bgcolor="#DEE0FA">
        <td width=100% valign=center align=center>
          <strong>Alerts</strong>
        </td>
      </tr>
      <tr>
        <td bgcolor=white>
          Select Alert Type:
<%
          AlertsListSelection.setJsEvent ("onChange=\"document.forms['alerts'].submit();\"");
%>
          <%= AlertsListSelection.getHtml() %>
        </td>
      </tr>
      </form>
    </table-->
   <!--table border=0 cellpadding=2 cellspacing=1 width="100%" bgcolor="#D4D4D4">
     <tr bgcolor=white>
     <th align=left>Description</th>
     <th align=left width=150>Account</th>
     <th width=100 align=left>Alert Date</th>
     </tr>




    </table-->
<!-- End Table:Alerts -->
</td>
<!-- end ZZ:row one -->
</tr>
<!-- ZZ:row two (spans both row-one columns) -->
<tr>
<td colspan=2>
<%
  Iterator j = NewsList.iterator();
%>
	<form name="miner_select" type="get" action="MyCFS.do">
	<input type=hidden name=command value="Home">
	<table cellpadding="3" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
	<tr bgcolor="#DEE0FA"><td valign=center colspan=2>

	<table bgcolor="#DEE0FA" width=100% cellspacing="0" cellpadding="0" border="0">
	<tr><td width=60% valign=center>
	<strong>Personalized Industry News & Events</strong>
	</td>
	<td width= 40% align="right" valign=center>
    
    	<% if (request.getParameter("industry") == null || request.getParameter("industry").equals("")) { %>
    		<%=IndSelect.getHtmlSelect("industry",1)%>
	<% } else { %>
		<%=IndSelect.getHtmlSelect("industry",Integer.parseInt(request.getParameter("industry")))%>
	<%}%>
	
	</td>
	</tr>
	</table>
	</td>
</tr>
<%
	if ( j.hasNext() ) {
		while (j.hasNext()) {
      			NewsArticle thisNews = (NewsArticle)j.next();
		%>      

		<tr bgcolor="white">
		<td width=11% valign=center>
		<%= thisNews.getDateEntered() %>
		</td>

		<td valign=center><a href="<%= thisNews.getUrl() %>" target="_new"><%= thisNews.getHeadline() %></a></td></tr>
		<%}
	} else {%>
		<tr bgcolor="white"><td width=100% valign=center>No news found.</td></tr>
	<%}%>
			
	</table>
	</form>

<%  
%>
<!-- end ZZ:row two -->
</tr>
<!-- End Table ZZ -->
</table>


