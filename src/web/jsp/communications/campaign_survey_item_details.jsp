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
       <td valign="center" align="left" nowrap>
          Name
        </td>
       <td valign="center" align="left" nowrap>
          Phone Number(s)
        </td>
      <td valign="center" align="left" nowrap>
        Email Addresses
      </td>
      <td valign="center" align="left" nowrap>
        Entered
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
        ActiveSurveyAnswerItem thisItem = (ActiveSurveyAnswerItem)i.next();
  %>      
     <tr class="row<%= rowid %>" nowrap>
        <td valign="top" align="center" class="row<%= rowid %>" nowrap>
          <%= thisItem.getRecipient().getNameLastFirst() %>
        </td>
        <td width="20" nowrap>
          <%= toHtml(thisItem.getRecipient().getPhoneNumber(PhoneNumber.BUSSINESS)) %>
        </td>
        <td width="40" nowrap>
          <%= toHtml(thisItem.getRecipient().getEmailAddress(EmailAddress.BUSSINESS)) %>
        </td>
        <td width="40" nowrap>
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
      <td class="row2" valign="center">
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
