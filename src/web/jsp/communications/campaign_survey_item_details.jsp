<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.Contact, org.aspcfs.modules.base.PhoneNumber, org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="ItemDetailsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ItemDetails" class="org.aspcfs.modules.communications.base.ActiveSurveyAnswerItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="CampaignManager.do?command=ShowItems&questionId=<%= request.getParameter("questionId") %>">Item List</a> >
Item Details
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr class="containerHeader">
  <th colspan="2" valign="center">
    <strong>Item:</strong> <%= toHtml(ItemDetails.getItem().getDescription()) %>
  </th>
</tr>
<tr>
  <td class="containerBack">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
       <th>
          Name
        </th>
       <th width="15%" nowrap>
          Phone Number(s)
        </th>
      <th width="15%" nowrap>
        Email Addresses
      </th>
      <th nowrap>
        Entered
      </th>
    </tr>
  <%    
    Iterator i = ItemDetails.iterator();
    if (i.hasNext()) {
      int rowid = 0;
      while (i.hasNext()) {
        rowid = (rowid != 1?1:2);
        SurveyAnswerItem thisItem = (SurveyAnswerItem)i.next();
  %>      
     <tr class="row<%= rowid %>" nowrap>
        <td class="row<%= rowid %>" nowrap>
          <%= thisItem.getRecipient().getNameLastFirst() %>
        </td>
        <td width="15%" nowrap>
          <%= toHtml(thisItem.getRecipient().getPhoneNumber(PhoneNumber.BUSINESS)) %>
        </td>
        <td width="15%" nowrap>
          <%= toHtml(thisItem.getRecipient().getEmailAddress(EmailAddress.BUSINESS)) %>
        </td>
        <td nowrap>
          <%= toDateTimeString(thisItem.getEntered()) %>
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
      <td class="containerBody" colspan="4">
        No responses found for this item.
      </td>
    </tr>
    </table>
    <%}%>
  </td>
 </tr>
</table>
<br>
<input type="button" value="Close Window" onClick="javascript:window.close();">
