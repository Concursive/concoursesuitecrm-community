<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,org.aspcfs.modules.contacts.base.Contact" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="CampaignGroupsPreviewInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="scl" class="org.aspcfs.modules.communications.base.SearchCriteriaList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<dhv:evaluate if="<%= !"true".equals(request.getParameter("popup")) %>">
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManagerGroup.do?command=View">View Groups</a> >
<a href="CampaignManagerGroup.do?command=Details&id=<%= request.getAttribute("id") %>">Group Details</a> >
Contact List<br>
<hr color="#BFBFBB" noshade>
<input type="button" name="cmd" value="Back to Criteria" onClick="window.location.href='CampaignManagerGroup.do?command=Details&id=<%= request.getAttribute("id") %>&return=<%= request.getParameter("return") %>'">
</dhv:evaluate>
<dhv:pagedListStatus object="CampaignGroupsPreviewInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="4">
      <strong><%= scl.getGroupName() %></strong>
    </td>     
  </tr>
  <tr class="title">
    <td>
      &nbsp;
    </td>
    <td width="33%">
      Name
    </td>
    <td width="33%">
      Company
    </td>
    <td width="34%">
      Email
    </td>
  </tr>
<%
	Iterator j = ContactList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		int count = CampaignGroupsPreviewInfo.getCurrentOffset();
	  while (j.hasNext()) {
			count++;		
			rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
  <tr class="row<%= rowid %>">
    <td align="right" nowrap>
      <%= count %>.
    </td>
    <td nowrap>
      <%= toHtml(thisContact.getNameLastFirst()) %>
    </td>
    <td>
      <%= toHtml(thisContact.getAffiliation()) %>
    </td>
    <td>
      <%= toHtml(thisContact.getEmailAddress("Business")) %>
    </td>
  </tr>
  <%}%>
</table>
<br>
<dhv:pagedListControl object="CampaignGroupsPreviewInfo" tdClass="row1"/>
<%} else {%>
  <tr bgcolor="white">
    <td colspan="4">
      No contacts matched query.
    </td>
  </tr>
</table>
<%}%>
<br>
<dhv:evaluate if="<%= !"true".equals(request.getParameter("popup")) %>">
<input type="button" name="cmd" value="Back to Criteria" onClick="window.location.href='CampaignManagerGroup.do?command=Details&id=<%= request.getAttribute("id") %>&return=<%= request.getParameter("return") %>'">
</dhv:evaluate>
<dhv:evaluate if="<%= "true".equals(request.getParameter("popup")) %>">
<input type="button" name="cmd" value="Close" onClick="window.close()">
</dhv:evaluate>
