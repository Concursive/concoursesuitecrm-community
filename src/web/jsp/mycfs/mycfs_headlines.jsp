<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.Organization" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="HeadlineListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:include page="cfsor2.js" flush="true"/>
<%@ include file="../initPage.jsp" %>

<dhv:permission name="myhomepage-miner-add">
<body onLoad="javascript:document.forms[0].name.focus();">
</dhv:permission>

<a href="MyCFS.do?command=Home">My Home Page</a> > 
Headlines<br>
<hr color="#BFBFBB" noshade>

<dhv:permission name="myhomepage-miner-add">
<form name="addAccount" action="/MyCFS.do?command=InsertHeadline" method="post">

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Monitor a New Company</strong>
    </td>     
  </tr>
  <tr bgcolor="#FFFFFF">
    <td width="125" colspan=1 valign=center>
      Name
    </td>
    <td colspan=1 valign=center>
      <input type=text size=40 name="name">
    </td>
  </tr>
  
    <tr bgcolor="#FFFFFF">
    <td width="125" colspan=1 valign=center>
      Ticker Symbol
    </td>
    <td colspan=1 valign=center>
      <input type=text size=10 name="stockSymbol">
    </td>
  </tr>
  
  <tr bgcolor="#FFFFFF">
    <td valign=center colspan=2>
      <input type=submit value="Insert">
      <input type=reset value="Clear">
    </td>
  </tr>
  </form>
</table>
&nbsp;<br>
</dhv:permission>

<center>
<%= HeadlineListInfo.getAlphabeticalPageLinks() %><br>
&nbsp;
</center>
<form name="delAccount" action="/MyCFS.do?command=DeleteHeadline" method="post">
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
  
  <dhv:permission name="myhomepage-miner-delete">
    <td width=3% valign=center align=left>
      &nbsp;
    </td>
  </dhv:permission>
  
    <td valign=center align=left>
      <strong>My Monitored Companies</strong>
    </td>
        <td valign=center align=left>
      <strong>Ticker</strong>
    </td>
    <td valign=center>
      <strong>Date Entered</strong>
    </td>  
</tr>

<%
	Iterator j = OrgList.iterator();
	
	if ( j.hasNext() ) {
	    while (j.hasNext()) {
			Organization thisOrg = (Organization)j.next();
%>      
	
  <tr bgcolor="white">
    <dhv:permission name="myhomepage-miner-delete">
    <td width=5 valign=center align=center>
    <input type=checkbox name="<%= thisOrg.getOrgId() %>">
    </td>
    </dhv:permission>
    <td valign=center>
      <%= toHtml(thisOrg.getName()) %>
    </td>
        <td width=15 align=center>
      <%= toHtml(thisOrg.getTicker()) %>
    </td>
    <td width=100 valign=center><%= thisOrg.getEnteredStringLongYear() %></td>
  </tr>
<%}%>
<dhv:permission name="myhomepage-miner-delete">
	<tr bgcolor="white">
    <td valign="center" colspan=4>
      
      <input type=submit name="action" value="Delete Checked">
      <input type=reset value="Clear">
      
    </td>
	</tr>
</dhv:permission>
</table>
<br>
	[<%= HeadlineListInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= HeadlineListInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>]
<%} else {%>
  <tr bgcolor="white">
    <td colspan=4 valign=center>No companies found.</td>
  </tr>
</table>
<%}%>
</form>
</body>

