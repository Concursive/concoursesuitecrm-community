<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="ShortChildList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="MyRevList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="RevenueTypeList" class="org.aspcfs.modules.accounts.base.RevenueTypeList" scope="request"/>
<jsp:useBean id="YearList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Read in the image map for the graph --%>
<% String includePage = "../graphs/" + (String) request.getAttribute("GraphFileName") + ".map"; %>          
<jsp:include page="<%= includePage %>" flush="true"/>
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
        <tr class="title">
          <td width="255" valign="center" align="center">
		<% if (((String)request.getSession().getAttribute("revenueoverride")) == null) {%>
      My Dashboard
		<%} else {%>
      Dashboard: <%=toHtml((String)request.getSession().getAttribute("revenueothername"))%>
		<%}%>
	</td>
	  <td width="20" valign="center" align="center">
	    <% YearList.setJsEvent("onChange=\"document.forms[0].submit();\""); %>
<%
    if (request.getParameter("year") != null) {
			YearList.setDefaultValue(request.getParameter("year"));
		} else if (((String)request.getSession().getAttribute("revenueyear")) != null) {
			YearList.setDefaultValue(((String)request.getSession().getAttribute("revenueyear")));
		}
%>
	    <%=YearList.getHtml()%>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <img src="graphs/<%= request.getAttribute("GraphFileName") %>.jpg" width="275" height="200" border="0" usemap="#<%= request.getAttribute("GraphFileName") %>">
          </td>
        </tr>
        <tr>
          <td width="275" valign="center" colspan="2" align="center">
	    Type&nbsp;
	    <% if (request.getParameter("type") != null) { %>
        <%= RevenueTypeList.getHtmlSelect("type", Integer.parseInt(request.getParameter("type"))) %>&nbsp;
	    <%} else if ((String)request.getSession().getAttribute("revenuetype") != null) {%>
        <%= RevenueTypeList.getHtmlSelect("type", Integer.parseInt((String)request.getSession().getAttribute("revenuetype"))) %>&nbsp;
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
	<% if (!(((String)request.getSession().getAttribute("revenueoverride")) == null)) {%>
      <input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("revenueoverride"))%>">
      <a href="RevenueManager.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("revenuepreviousId"))%>">Up One Level</a> |
      <a href="RevenueManager.do?command=Dashboard&reset=1">Back to My Dashboard</a>
	<%} else {%>
      &nbsp;
  <%}%>
          </td>
        </tr>
      </table>
      <%-- User List --%>
      <table width="285" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
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
      <table width="100%" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td>Account Name</td>
          <td>YTD</td>
        </tr>
<%
	Iterator n = MyRevList.iterator();
		if ( n.hasNext() ) {
			int rowid = 0;
			while (n.hasNext()) {
        rowid = (rowid != 1?1:2);
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
