<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="UserInfo" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="ShortChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="FullRevList" class="com.darkhorseventures.cfsbase.RevenueList" scope="request"/>
<jsp:useBean id="MyRevList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="com.darkhorseventures.cfsbase.RevenueTypeList" scope="request"/>
<jsp:useBean id="DBRevenueListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="YearList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name=Dashboard action="/RevenueManager.do?command=Dashboard" method=POST>
<table width=100% border=0 cellspacing=0 cellpadding=3>
  <tr>
    <!-- Left Column -->
    <td width=275 valign=top>
      <!-- Graphic -->
      <table width=275 cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td width=255 valign=center colspan=1 align=center>
		
		<% if (((String)request.getSession().getAttribute("override")) == null) {%>
		My Dashboard
		<%} else {%>
		Dashboard: <%=toHtml((String)request.getSession().getAttribute("othername"))%>
		<%}%>
        
	</td>
	  <td width=20 valign=center colspan=1 align=center>
	    <% YearList.setJsEvent("onChange=\"document.forms[0].submit();\""); %>
	    
	    <%
	    	if (request.getParameter("year") != null) {
			YearList.setDefaultValue(request.getParameter("year"));
		} else if (((String)request.getSession().getAttribute("year")) != null) {
			YearList.setDefaultValue(((String)request.getSession().getAttribute("year")));
		}
	    %>
	    
	    <%=YearList.getHtml()%>
          </td>
        </tr>
        <tr>
          <td colspan=2>
            <img border="0" width="275" height="200" src="/graphs/<%=request.getAttribute("GraphFileName")%>">
          </td>
        </tr>
	<tr>
          <td width=275 valign=center colspan=2 align=center>
	    Type&nbsp;
	    <% if (request.getParameter("type") != null) { %>
	    <%=RevenueTypeList.getHtmlSelect("type", Integer.parseInt(request.getParameter("type")))%>&nbsp;
	    <%} else if ((String)request.getSession().getAttribute("RevenueGraphType") != null) {%>
	    <%=RevenueTypeList.getHtmlSelect("type", Integer.parseInt((String)request.getSession().getAttribute("RevenueGraphType")))%>&nbsp;
	    <%} else {%>
            <%=RevenueTypeList.getHtmlSelect("type", 0)%>&nbsp;
	    <%}%>
          </td>
        </tr>
	
       <!--tr>
          <td valign="center" width="275" align="center" coslpan=2>
	    Type&nbsp;
	    <% if (request.getParameter("type") != null) { %>
	    <%=RevenueTypeList.getHtmlSelect("type", Integer.parseInt(request.getParameter("type")))%>&nbsp;
	    <%} else {%>
            <%=RevenueTypeList.getHtmlSelect("type", 0)%>&nbsp;
	    <%}%>
	    <input type=submit value="Go">
          </td>
        </tr-->
	
	</table>
	<br>
	       <% if (!(((String)request.getSession().getAttribute("override")) == null)) {%>
	       
	       <div margintop=5 align=center width=285>
        <input type=hidden name="oid" value="<%=((String)request.getSession().getAttribute("override"))%>">
        <a href="RevenueManager.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("previousId"))%>">Up One Level</a> |
        <a href="RevenueManager.do?command=Dashboard&reset=1">Back to My Dashboard</a>
	 </div>
      <%}%>
     
	<table width=285 cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
	
	<!--tr bgcolor="#DEE0FA">
          <td width=100% valign=center colspan=1>
	    Reporting Staff
          </td>
	  <td width=55 valign=center colspan=1>
	    YTD
          </td>
        </tr-->
	
	        <tr bgcolor="#DEE0FA">
	<td>Reporting Staff</td>
          <td>YTD</td>
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
	<td width=100% class="row<%= rowid %>" valign=center nowrap>
	<a href="/RevenueManager.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLast()) %>, <%= toHtml(thisRec.getContact().getNameFirst()) %></a>
	</td>
	<td width=55 nowrap class="row<%= rowid %>" valign=center>
	$<%=toHtml(thisRec.getYTDCurrency())%>
	</td>
	</tr>
	
	<%}
	} else {
	%>
	<tr>
	<td valign=center colspan=2>No Reporting staff.</td>
	</tr>
	<%}%>
	
      </table>
      
      </td>
      
       <td valign=top width="100%">
      <table width=100% cellpadding=3 cellspacing=0 border=1 bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
	<td>Account Name</td>
          <td>YTD</td>
        </tr>
<%
	Iterator n = MyRevList.iterator();
		if ( n.hasNext() ) {
			int rowid = 0;
			while (n.hasNext()) {
		
				if (rowid != 1) {
					rowid = 1;
				} else {
					rowid = 2;
				}
	          	
				Organization thisOrg = (Organization)n.next();
%>    
				<tr>
	<td class="row<%= rowid %>" valign=top><a href="RevenueManager.do?command=View&orgId=<%=thisOrg.getId()%>"><%= toHtml(thisOrg.getName()) %></a></td>
          <td class="row<%= rowid %>" valign=center width=50 nowrap>$<%= thisOrg.getYTDCurrency() %></td>
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
