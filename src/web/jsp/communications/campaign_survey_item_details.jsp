<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
