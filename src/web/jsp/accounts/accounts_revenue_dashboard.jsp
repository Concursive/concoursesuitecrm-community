<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="ShortChildList" class="com.darkhorseventures.cfsbase.UserList" scope="request"/>
<jsp:useBean id="MyRevList" class="com.darkhorseventures.cfsbase.OrganizationList" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="com.darkhorseventures.cfsbase.RevenueTypeList" scope="request"/>
<jsp:useBean id="YearList" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<form name="Dashboard" action="RevenueManager.do?command=Dashboard" method="POST">
<a href="Accounts.do">Account Management</a> > 
Revenue Dashboard<br>
<hr color="#BFBFBB" noshade>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <%-- Left Column --%>
    <td width="275" valign="top">
      <%-- Graphic --%>
      <table width="275" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td width=255 valign=center colspan="1" align="center">
		<% if (((String)request.getSession().getAttribute("override")) == null) {%>
      My Dashboard
		<%} else {%>
      Dashboard: <%=toHtml((String)request.getSession().getAttribute("othername"))%>
		<%}%>
	</td>
	  <td width="20" valign="center" align="center">
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
          <td colspan="2">
            <img border="0" width="275" height="200" src="graphs/<%=request.getAttribute("GraphFileName")%>">
          </td>
        </tr>
        <tr>
          <td width="275" valign="center" colspan="2" align="center">
	    Type&nbsp;
	    <% if (request.getParameter("type") != null) { %>
        <%= RevenueTypeList.getHtmlSelect("type", Integer.parseInt(request.getParameter("type"))) %>&nbsp;
	    <%} else if ((String)request.getSession().getAttribute("type") != null) {%>
        <%= RevenueTypeList.getHtmlSelect("type", Integer.parseInt((String)request.getSession().getAttribute("type"))) %>&nbsp;
	    <%} else {%>
        <%= RevenueTypeList.getHtmlSelect("type", 0) %>&nbsp;
	    <%}%>
          </td>
        </tr>
      </table>
      <%-- Up a level --%>
      <table width="285" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td align="center" width="100%">
	<% if (!(((String)request.getSession().getAttribute("override")) == null)) {%>
      <input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("override"))%>">
      <a href="RevenueManager.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("previousId"))%>">Up One Level</a> |
      <a href="RevenueManager.do?command=Dashboard&reset=1">Back to My Dashboard</a>
	<%} else {%>
      &nbsp;
  <%}%>
          </td>
        </tr>
      </table>
      <%-- User List --%>
      <table width="285" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
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
          <td width="100%" class="row<%= rowid %>" valign="center" nowrap>
            <a href="RevenueManager.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLastFirst()) %></a>
            <dhv:evaluate exp="<%=!(thisRec.getEnabled())%>"><font color="red">*</font></dhv:evaluate>
          </td>
          <td width="55" nowrap class="row<%= rowid %>" valign="center">
            $<%=toHtml(thisRec.getYTDCurrency())%>
          </td>
        </tr>
      <%}
    } else {
%>
        <tr>
          <td valign="center" colspan="2">No Reporting staff.</td>
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
          <td class="row<%= rowid %>" valign="top">
            <a href="RevenueManager.do?command=View&orgId=<%=thisOrg.getId()%>"><%= toHtml(thisOrg.getName()) %></a>
          </td>
          <td class="row<%= rowid %>" valign="center" width="50" nowrap>
            $<%= thisOrg.getYTDCurrency() %>
          </td>
        </tr>
<%    }
	  } else {
%>
        <tr>
          <td valign=center colspan="7">No accounts found with revenue.</td>
        </tr>
<%}%>
      </table>
      <br>
    </td>
  </table>
  <br>
</form>
