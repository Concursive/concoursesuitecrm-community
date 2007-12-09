<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.Contact, org.aspcfs.modules.base.PhoneNumber, org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="ItemDetailsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ItemDetails" class="org.aspcfs.modules.communications.base.ActiveSurveyAnswerItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do?command=ShowItems&questionId=<%= request.getParameter("questionId") %>"><dhv:label name="campaign.itemList">Item List</dhv:label></a> >
<dhv:label name="product.itemDetails">Item Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communicationitemdetails" selected="details" object="ItemDetails.item" item="<%= ItemDetails.getItem() %>" param='<%= "name=" + toHtml(ItemDetails.getItem().getDescription()) %>'>
<%--<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr class="containerHeader">
  <th colspan="2" valign="center">
    <dhv:label name="campaign.item.colon" param='<%= "description="+toHtml(ItemDetails.getItem().getDescription()) %>'><strong>Item:</strong> <%= toHtml(ItemDetails.getItem().getDescription()) %></dhv:label>
  </th>
</tr>
<tr>
  <td class="containerBack"> --%>
  <dhv:pagedListStatus title='<%= showAttribute(request, "actionError") %>' object="ItemDetailsListInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
       <th>
          <dhv:label name="contacts.name">Name</dhv:label>
        </th>
       <th width="15%" nowrap>
          <dhv:label name="campaign.phoneNumbers">Phone Number(s)</dhv:label>
        </th>
      <th width="15%" nowrap>
        <dhv:label name="accounts.accounts_add.EmailAddresses">Email Addresses</dhv:label>
      </th>
      <th nowrap>
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
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
          <%= thisItem.getRecipient().getNameFull() %>
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
      <%}%>
      </table>
      <br>
      <dhv:pagedListControl object="ItemDetailsListInfo" />
    <%} else {%>
    <tr>
      <td class="containerBody" colspan="4">
        <dhv:label name="campaign.noResponsesFoundForItem">No responses found for this item.</dhv:label>
      </td>
    </tr>
    </table>
    <%}%>
<%--  </td>
 </tr>
</table> --%>
<br>
<input type="button" value="<dhv:label name="button.closeWindow">Close Window</dhv:label>" onClick="javascript:window.close();">
</dhv:container>
