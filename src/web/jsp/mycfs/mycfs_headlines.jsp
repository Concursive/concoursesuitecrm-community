<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.Organization" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="HeadlineListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:include page="cfsor2.js" flush="true"/>
<%@ include file="../initPage.jsp" %>
<dhv:permission name="myhomepage-miner-add">
<body onLoad="javascript:document.forms[0].name.focus();">
<form name="addAccount" action="MyCFS.do?command=InsertHeadline" method="post">
</dhv:permission>
<a href="MyCFS.do?command=Home">My Home Page</a> > 
Headlines<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="myhomepage-miner-add">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
      <strong>Monitor a New Company</strong>
    </td>     
  </tr>
  <tr>
    <td class="formLabel">
      Name
    </td>
    <td>
      <input type="text" size="40" name="name">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      Ticker Symbol
    </td>
    <td>
      <input type="text" size="10" name="stockSymbol">
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="submit" value="Insert">
<input type="reset" value="Clear">
</form>
</dhv:permission>
<center><%= HeadlineListInfo.getAlphabeticalPageLinks() %></center>
<br>
<form name="delAccount" action="MyCFS.do?command=DeleteHeadline" method="post">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="myhomepage-miner-delete">
      <td width="3%" valign="center" align="left">
        &nbsp;
      </td>
    </dhv:permission>
    <td>
      <strong>My Monitored Companies</strong>
    </td>
    <td valign=center align=left>
      <strong>Ticker</strong>
    </td>
    <td>
      <strong>Date Entered</strong>
    </td>  
</tr>
<%
	Iterator j = OrgList.iterator();
	int rowid = 0;
	if ( j.hasNext() ) {
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
			Organization thisOrg = (Organization)j.next();
%>      
  <tr class="row<%= rowid %>">
    <dhv:permission name="myhomepage-miner-delete">
    <td width="5" valign="center" align="center">
      <input type="checkbox" name="<%= thisOrg.getOrgId() %>">
    </td>
    </dhv:permission>
    <td valign="center">
      <%= toHtml(thisOrg.getName()) %>
    </td>
    <td width="15" align="center">
      <%= toHtml(thisOrg.getTicker()) %>
    </td>
    <td width="100" valign="center" align="center">
      <%= thisOrg.getEnteredStringLongYear() %>
    </td>
  </tr>
<%}%>
</table>
<dhv:permission name="myhomepage-miner-delete">
	&nbsp;<br>
  <input type="submit" name="action" value="Delete Checked">
  <input type="reset" value="Clear"><br>
</dhv:permission>
<br>
<dhv:pagedListControl object="HeadlineListInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="4">No companies found.</td>
  </tr>
</table>
<%}%>
</form>
<dhv:permission name="myhomepage-miner-add">
</body>
</dhv:permission>
