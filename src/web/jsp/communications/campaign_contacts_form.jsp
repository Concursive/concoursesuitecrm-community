<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.modules.contacts.base.ContactList" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="CampaignGroupsContactsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<form>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="3">
      <strong>List of Contacts</strong>
    </td>
  </tr>
  <tr class="title">
    <td width="8" align="right" nowrap>
      Action
    </td>
    <td width="50%" nowrap>
      Name
    </td>
    <td width="50%" nowrap>
      Company
    </td>
  </tr>
<%
	Iterator j = ContactList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = 0;
	  while (j.hasNext()) {
			count++;		
			rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
  <tr>
    <td align="center" nowrap>
      <a href="javascript:window.opener.addValueFromChild('<%=thisContact.getId()%>', '<%=thisContact.getNameLastFirst()%>');javascript:window.focus();">Add</a>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameLastFirst()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getAffiliation()) %>
    </td>
  </tr>
  <%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignGroupsContactsInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="3">
      No contacts matched query.
    </td>
  </tr>
</table>
<%}%>
<p>
<input type="button" value="Close" onClick="javascript:window.close();">
</form>
