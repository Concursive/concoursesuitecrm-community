<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="portalUserDetails" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="roleList" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript">
</script>
<form name="contactPortal" action="ContactsPortal.do?command=Save" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">Contacts</a> >
Contact Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
    <% String param1 = "id=" + ContactDetails.getId(); %>
        <strong><%= ContactDetails.getNameLastFirst() %>:</strong>
        [ <dhv:container name="accountscontacts" selected="portal" param="<%= param1 %>"/> ]
      <br /> <br />
  <input	type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
  <input	type="reset" value="Reset" />
  <br /> <br />
  <%@ include file="accounts_contacts_portal_include.jsp" %>
  <br />
  <input	type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
  <input	type="reset" value="Reset" />
  </td>
  </tr>
</table>
</form>
