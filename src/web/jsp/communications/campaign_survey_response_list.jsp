<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="SurveyResponseListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SurveyResponseList" class="org.aspcfs.modules.communications.base.SurveyResponseList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="/javascript/confirmDelete.js"></script>
<a href="CampaignManager.do">Communications Manager</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
<a href="CampaignManager.do?command=Details&id=<%=Campaign.getId()%>">Campaign Details</a> >
Response
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td colspan="2">
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr class="containerMenu">
    <td colspan="2">
      <% String param1 = "id=" + Campaign.getId(); %>
      <dhv:container name="communications" selected="response" param="<%= param1 %>" /><br>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<center><%= SurveyResponseListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="SurveyResponseListInfo"/>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td width="20%" nowrap>
      <strong><a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>&column=c.namelast">Name</a></strong>
      <%= SurveyResponseListInfo.getSortIcon("c.namelast") %>
      </td>
    <td nowrap>
      <strong><a href="CampaignManager.do?command=ViewResponse&id=<%= Campaign.getId() %>&column=sr.entered">Submitted</a></strong>
      <%= SurveyResponseListInfo.getSortIcon("sr.entered") %>
    </td>
    <td nowrap><strong>IP Address</strong></td>
    <td nowrap><strong>Email Address</strong></td>
    <td nowrap><strong>Phone Number</strong></td>
  </tr>
<%
  Iterator j = SurveyResponseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      SurveyResponse thisResponse = (SurveyResponse) j.next();
%>      
    <tr class="row<%= rowid %>">
      <td width="30" nowrap>
        <a href="CampaignManager.do?command=ResponseDetails&id=<%= Campaign.getId() %>&contactId=<%= thisResponse.getContactId() %>&responseId=<%= thisResponse.getId() %>"><%= toHtml(thisResponse.getContact().getNameLastFirst()) %></a>
      </td>
      <td nowrap>
        <%= toDateTimeString(thisResponse.getEntered()) %>&nbsp;
      </td>
      <td>
        <%= toHtml(thisResponse.getIpAddress()) %>&nbsp;
      </td>
      <td nowrap>
        <%= thisResponse.getContact().getEmailAddress("Business") %>&nbsp;
      </td>
      <td nowrap>
        <%= thisResponse.getContact().getPhoneNumber("Business") %>&nbsp;
      </td>
    </tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="SurveyResponseListInfo"/>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        No Response Found.
      </td>
    </tr>
  </table>
<%}%>
</td>
</tr>
</table>

