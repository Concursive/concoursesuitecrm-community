<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.Contact, org.aspcfs.modules.base.PhoneNumber, org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="ItemDetailsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ItemDetails" class="org.aspcfs.modules.communications.base.ActiveSurveyAnswerItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<br>
<a href="CampaignManager.do?command=ShowItems&questionId=<%= request.getParameter("questionId") %>"> View Items </a>> Item Details <br>
<hr color="#BFBFBB" noshade>
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr class="containerHeader">
  <td colspan="2" valign="center" align="left">
    <strong>Item : </strong><%= toHtml(ItemDetails.getItem().getDescription()) %>
  </td>     
</tr>
<tr>
  <td class="containerBack">
  <table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
    <tr class="title">
       <td nowrap>
          Name
        </td>
       <td width="15%" nowrap>
          Phone Number(s)
        </td>
      <td width="15%" nowrap>
        Email Addresses
      </td>
      <td nowrap>
        Entered
      </td>
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
      <td class="row2" colspan="4">
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
