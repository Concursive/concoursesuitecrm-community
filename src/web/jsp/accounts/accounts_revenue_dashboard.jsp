<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="UserInfo" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="ShortChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullRevList" class="com.darkhorseventures.cfsbase.RevenueList" scope="request"/>
<jsp:useBean id="MyRevList" class="com.darkhorseventures.cfsbase.RevenueList" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="com.darkhorseventures.cfsbase.RevenueTypeList" scope="request"/>
<jsp:useBean id="DBRevenueListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<form name=Dashboard action="/RevenueManager.do?command=Dashboard" method=POST>
<table width=100% border=0 cellspacing=0 cellpadding=3>
  <tr>
    <!-- Left Column -->
    <td width=275 valign=top>
      <!-- Graphic -->
      <table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign=center colspan=1 align=center>
	    My Dashboard
          </td>
        </tr>
        <tr>
          <td>
            <img border="0" width="275" height="200" src="/graphs/<%=request.getAttribute("GraphFileName")%>">
          </td>
        </tr>
       <tr>
          <td align="center">
	    Type&nbsp;
	    <% if (request.getParameter("type") != null) { %>
	    <%=RevenueTypeList.getHtmlSelect("type", Integer.parseInt(request.getParameter("type")))%>&nbsp;
	    <%} else {%>
            <%=RevenueTypeList.getHtmlSelect("type", 0)%>&nbsp;
	    <%}%>
	    <input type=submit value="Go">
          </td>
        </tr>
      </table>
      
      </td>
      
       <td valign=top width="100%">
      <table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
	<td>Description</td>
          <td>Amount</td>
          <td>Owner</td>
        </tr>
<%
	Iterator n = FullRevList.iterator();
		if ( n.hasNext() ) {
			int rowid = 0;
			while (n.hasNext()) {
		
				if (rowid != 1) {
					rowid = 1;
				} else {
					rowid = 2;
				}
	          	
				Revenue thisRev = (Revenue)n.next();
%>    
				<tr>
	<td class="row<%= rowid %>" valign=top><a href="RevenueManager.do?command=Details&id=<%=thisRev.getId()%>"><%= toHtml(thisRev.getDescription()) %></a></td>
          <td class="row<%= rowid %>" valign=center width=50 nowrap>$<%= thisRev.getAmountCurrency() %></td>
          <td class="row<%= rowid %>" valign=center width=75 nowrap><%= toHtml(thisRev.getOwnerNameAbbr()) %></td>
        </tr>
<%    }
	  } else {
%>
        <tr>
          <td valign=center colspan=7>No Revenue at this time.</td>
        </tr>
<%}%>
	  
      </table>
      <br>
      <!--dhv:pagedListControl object="DBRevenueListInfo" tdClass="row1"/-->
    </td>
</table>
      <br>
      <dhv:pagedListControl object="DBRevenueListInfo" tdClass="row1"/>
</form>
</body>
