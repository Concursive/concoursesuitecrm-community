<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="UserInfo" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="ShortChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullOppList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="MyOppList" class="com.darkhorseventures.cfsbase.OpportunityList" scope="request"/>
<jsp:useBean id="GraphTypeList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name=Dashboard action="/Leads.do?command=Dashboard" method=POST>
<table width=100% border=0 cellspacing=0 cellpadding=3>
  <tr>
    <!-- Left Column -->
    <td width=350 valign=top>
      <!-- Graphic -->
      <table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign=center colspan=1 align=center>
            <strong>
      <% if (request.getAttribute("override") == null || request.getAttribute("override").equals("null")) {%>
        My Dashboard
      <%} else if (!(request.getAttribute("othername") == null) && !(request.getAttribute("othername").equals("null"))) {%>
        Dashboard - <%=request.getAttribute("othername")%>
      <%}%>
            </strong>
          </td>
        </tr>
        <tr>
          <td>
            <img border="0" width="300" height="200" src="/graphs/<%=request.getAttribute("GraphFileName")%>">
          </td>
        </tr>
        <tr>
          <td align="center">
            <%= GraphTypeList.getHtml() %>&nbsp;<input type=submit value="Show">
          </td>
        </tr>
      </table>
      
      <br>
      <% if (!(request.getAttribute("override") == null) && !(request.getAttribute("override").equals("null"))) {%>
        <input type=hidden name="oid" value="<%=request.getAttribute("override")%>">
        <a href="Leads.do?command=Dashboard&oid=<%=request.getAttribute("previousId")%>">Up One Level</a> |
        <a href="Leads.do?command=Dashboard">Back to My Dashboard</a>
        
      <%} else {%>
        My Dashboard
      <%}%>
      &nbsp;<br>
      
      <br>
      <table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign=center nowrap>
            <strong>Reporting Staff</strong>
          </td>
          
          <td valign=center width=161>
            <strong>Department</strong>
          </td>
        </tr>
<%
    Iterator x = ShortChildList.iterator();
      if ( x.hasNext() ) {
        int rowid = 0;
        while (x.hasNext()) {
          if (rowid != 1) {
            rowid = 1;
          } else {
            rowid = 2;
          }
          User thisRec = (User)x.next();
%>
        <tr>
          <td class="row<%= rowid %>" valign=center nowrap>
            <a href="/Leads.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLast()) %>, <%= toHtml(thisRec.getContact().getNameFirst()) %></a>
          </td>
          <td class="row<%= rowid %>" valign=center>
            <%= toHtml(thisRec.getContact().getDepartmentName()) %>
          </td>
        </tr>
    
<%      }
      } else {
%>
        <tr>
          <td valign=center colspan=3>No Reporting staff.</td>
        </tr>
      <%}%>
      </table>
    </td>
    <!-- Right Column -->
    <td valign=top width="100%">
      <table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign=center colspan="6">
            <strong>
<% if (request.getAttribute("override") == null || request.getAttribute("override").equals("null")) {%>
	  	My
<%}%>
	  Opportunities</strong>
          </td>
        </tr>
        <tr bgcolor="#DEE0FA">
          <!--td>Type</td-->
          <td>Opportunity</td>
          <td>Organization</td>
          <td align="right">Amnt</td>
          <td align="right" nowrap width=25>%P</td>
          <td align="right" width=90>Revenue Start</td>
          <td valign="right" width=25>Term</td>
        </tr>
<%
	Iterator n = MyOppList.iterator();
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
          <td class="row<%= rowid %>" valign=center><a href="/Leads.do?command=DetailsOpp&id=<%=thisOpp.getId()%>"><%= toHtml(thisOpp.getShortDescription()) %></a></td>
          <td class="row<%= rowid %>" valign=center><%= thisOpp.getAccountName() %></td>
          <td class="row<%= rowid %>" valign=center align="right">$<%= ((thisOpp.getGuess()/1000)) %>K</td>
          <td class="row<%= rowid %>" valign=center align="right" width=25><%= ((int)(thisOpp.getCloseProbValue())) %></td>
          <td class="row<%= rowid %>" valign=center align="right"  width=90><%= toHtml(thisOpp.getCloseDateString()) %></td>
          <td class="row<%= rowid %>" valign=center align="right" width=25><%= thisOpp.getTermsString() %></td>
        </tr>
<%    }
	  } else {
%>
        <tr>
          <td valign=center colspan=7>No Opportunities at this time.</td>
        </tr>
<%}%>
	  
      </table>
    </td>
  </tr>
</table>
</form>
</body>
