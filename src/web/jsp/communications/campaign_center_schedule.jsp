<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="FaxEnabled" class="java.lang.String" scope="application"/>
<jsp:useBean id="DeliveryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.activeDate.focus();">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
  
  function checkDelivery(){
    var selectedIndex = document.forms[0].sendMethodId.options.selectedIndex;
    if(document.forms[0].sendMethodId.options[selectedIndex].text.indexOf("Fax") != -1){
      showSpan('faxError');
    }else{
      hideSpan('faxError');
    }
  }
</script>
<form name="inputForm" action="CampaignManager.do?command=InsertSchedule&id=<%= Campaign.getId() %>" method="post" onSubmit="return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> > 
<a href="CampaignManager.do?command=View">Campaign List</a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>">Campaign Details</a> >
Delivery
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr class="containerHeader">
    <td>
      <strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" name="Save" value="Update Campaign Schedule">
</dhv:permission>
<input type="button" value="Cancel" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'"><br>
&nbsp;<br />
<%=showError(request,"actionError")%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Delivery Options</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="center" nowrap>
      Run Date
    </td>
    <td>
      <input type="text" size="10" name="activeDate" value="<zeroio:tz timestamp="<%= Campaign.getActiveDate() %>" dateOnly="true" />">
      <dhv:permission name="campaign-campaigns-edit">
      <a href="javascript:popCalendar('inputForm', 'activeDate', '<%= User.getLocale().getLanguage() %>', '<%= User.getLocale().getCountry() %>');"><img src="images/icons/stock_form-date-field-16.gif" height="16" width="16" border="0" align="absmiddle"></a>
      <%= TimeZoneSelect.getSelect("activeDateTimeZone", Campaign.getActiveDateTimeZone()).getHtml() %>
      </dhv:permission>
      <%=showAttribute(request,"activeDateError")%>
    </td>
  </tr>
  <tr class="containerBody">
      <td class="formLabel" valign="center" nowrap>
      Delivery Method
    </td>
    <td>
        <dhv:evaluate if="<%= "".equals(toString(FaxEnabled)) || "false".equals(toString(FaxEnabled)) %>">
          <% DeliveryList.setJsEvent("onChange=\"javascript:checkDelivery();\""); 
          %>
        </dhv:evaluate>
        <%= DeliveryList.getHtmlSelect("sendMethodId",Campaign.getSendMethodId() ) %>
        <span id="faxError" style="display:none"><font color="red">Fax Server is not configured.</font></span>
    </td>
  </tr>
</table>
<br>
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" name="Save" value="Update Campaign Schedule">
</dhv:permission>
<input type="button" value="Cancel" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>
</body>
