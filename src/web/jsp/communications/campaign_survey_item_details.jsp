<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.Contact, org.aspcfs.modules.base.PhoneNumber, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ItemDetailsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ItemDetails" class="org.aspcfs.modules.communications.base.ActiveSurveyAnswerItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<br>
<a href="CampaignManager.do?command=ShowItems&questionId=<%= request.getParameter("questionId") %>"> View Items </a>> Item Details <br>
<hr color="#BFBFBB" noshade>
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
     <td width="25" valign="top" align="left">
        Name
      </td>
     <td valign="top" align="left">
        Phone Number(s)
      </td>
    <td valign="center" align="left">
      Email Addresses
    </td>
  </tr>
<%    
	Iterator i = ItemDetails.iterator();
	if (i.hasNext()) {
    int rowid = 0;
		while (i.hasNext()) {
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
      Contact thisContact = (Contact)i.next();
%>      
   <tr class="row<%= rowid %>">
      <td valign="top" align="center" class="row<%= rowid %>" nowrap>
        <%= thisContact.getNameLastFirst() %>
      </td>
      <td width="80%">
        <%= toHtml(thisContact.getPhoneNumber(PhoneNumber.BUSSINESS)) %>
      </td>
      <td width="80%">
        <%= toHtml(thisContact.getEmailAddress(EmailAddress.BUSSINESS)) %>
      </td>
    </tr>
    <%
   }
    %>
    </table>
    <br>
    <dhv:pagedListControl object="ItemDetailsListInfo" />
    <dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="ItemDetailsListInfo"/>
  <%} else {%>  
  <tr>
    <td class="row2" valign="center">
      No responses found for this item.
    </td>
  </tr>
  </table>
<%}%>
<br>
<input type="button" value="Close Window" onClick="javascript:window.close();">
