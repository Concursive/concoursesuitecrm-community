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
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
  
  function checkDelivery(){
    var selectedIndex = document.inputForm.sendMethodId.options.selectedIndex;
    if(document.inputForm.sendMethodId.options[selectedIndex].text.indexOf("Fax") != -1){
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
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> > 
<a href="CampaignManager.do?command=View"><dhv:label name="campaign.campaignList">Campaign List</dhv:label></a> >
<a href="CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<dhv:label name="quotes.delivery">Delivery</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr class="containerHeader">
    <td>
      <dhv:label name="campaign.campaign.colon" param='<%= "name="+toHtml(Campaign.getName()) %>'><strong>Campaign:</strong> <%= toHtml(Campaign.getName()) %></dhv:label>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" name="Save" value="<dhv:label name="campaign.updateCampaignSchedule">Update Campaign Schedule</dhv:label>">
</dhv:permission>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'"><br />
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="campaign.deliveryOptions">Delivery Options</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="center" nowrap>
      <dhv:label name="accounts.accounts_contacts_messages_view.RunDate">Run Date</dhv:label>
    </td>
    <td>
      <dhv:permission name="campaign-campaigns-edit">
        <zeroio:dateSelect form="inputForm" field="activeDate" timestamp="<%= Campaign.getActiveDate() %>"/>
        <dhv:label name="project.at">at</dhv:label>
        <zeroio:timeSelect baseName="activeDate" value="<%= Campaign.getActiveDate() %>" timeZone="<%= (Campaign.getActiveDateTimeZone() == null) ? User.getTimeZone(): Campaign.getActiveDateTimeZone() %>" showTimeZone="true"/>
        <font color="red">*</font>
      </dhv:permission>
      <%=showAttribute(request,"activeDateError")%>
    </td>
  </tr>
  <tr class="containerBody">
      <td class="formLabel" valign="center" nowrap>
      <dhv:label name="campaign.deliveryMethod">Delivery Method</dhv:label>
    </td>
    <td>
        <dhv:evaluate if='<%= "".equals(toString(FaxEnabled)) || "false".equals(toString(FaxEnabled)) %>'>
          <% DeliveryList.setJsEvent("onChange=\"javascript:checkDelivery();\""); 
          %>
        </dhv:evaluate>
        <%= DeliveryList.getHtmlSelect("sendMethodId",Campaign.getSendMethodId() ) %>
        <span id="faxError" style="display:none"><font color="red"><dhv:label name="campaign.faxServerNotSupported">Fax Server is not configured.</dhv:label></font></span>
    </td>
  </tr>
</table>
<br />
<dhv:permission name="campaign-campaigns-edit">
<input type="submit" name="Save" value="<dhv:label name="campaign.updateCampaignSchedule">Update Campaign Schedule</dhv:label>">
</dhv:permission>
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='CampaignManager.do?command=ViewDetails&id=<%= Campaign.getId() %>'">
  </td>
  </tr>
</table>
</form>
</body>
