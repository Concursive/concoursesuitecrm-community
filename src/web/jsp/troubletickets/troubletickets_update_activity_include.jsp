<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<script language="JavaScript" type="text/javascript">
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    var i = 1;
    var fields = form.elements;
    var activityDates = Array(5);
    var activityDatesTimeZones = Array(5);
    var travelMinutes = Array(5);
    var travelHours = Array(5);
    var laborMinutes = Array(5);
    var laborHours = Array(5);
    var descriptions = Array(5);
    var dateCount = 1;
    var travelMinutesCount = 1;
    var travelHoursCount = 1;
    var laborMinutesCount = 1;
    var laborHoursCount = 1;
    var descriptionCount = 1;
    for (i=0; i < fields.length ; i++){
      if ((fields[i].name.substring(0,12).match("activityDate")) &&
          ("".match(fields[i].name.substring(13,21)))){
        activityDates[dateCount] = fields[i]; 
        dateCount++;
      }
      if (fields[i].name.substring(0,13).match("travelMinutes")){
        travelMinutes[travelMinutesCount] = fields[i]; 
        travelMinutesCount++;
      }
      if (fields[i].name.substring(0,11).match("travelHours")){
        travelHours[travelHoursCount] = fields[i]; 
        travelHoursCount++;
      }
      if (fields[i].name.substring(0,12).match("laborMinutes")){
        laborMinutes[laborMinutesCount] = fields[i]; 
        laborMinutesCount++;
      }
      if (fields[i].name.substring(0,10).match("laborHours")){
        laborHours[laborHoursCount] = fields[i]; 
        laborHoursCount++;
      }
      
      if (fields[i].name.substring(0,20).match("descriptionOfService")){
        descriptions[descriptionCount] = fields[i]; 
        descriptionCount++;
      }
    }
      
    for (i=1;i<=5;i++){
      if ((!activityDates[i].value == "") && (descriptions[i].value=="")){
        message += "- Check that all items in row "+ i +" are filled in\r\n";
        formTest = false;
      }

      if ((activityDates[i].value == "") && 
          ((travelMinutes[i].value > 0) || (travelHours[i].value > 0) || (laborMinutes[i].value > 0) || (laborHours[i].value > 0) || (!descriptions[i].value==""))){
        message += "- Check that all items in row "+ i +" are filled in\r\n";
        formTest = false;
      }
    }

    if (form.followUpRequired.checked) {
      if (form.alertDate.value == ""){
        message += "- Alert Date is mandatory if Follow-up Required is checked\r\n";
        formTest = false;
      }
      
      if (form.followUpDescription.value == ""){
        message += "- Follow-up Description is mandatory if Follow-up Required is checked\r\n";
        formTest = false;
      }
    }
    else{
      if (!form.alertDate.value == ""){
        message += "- Alert Date is required only if Follow-up Required is checked\r\n";
        formTest = false;
      }
      if (!form.followUpDescription.value == ""){
        message += "- Follow-up Description is required only if Follow-up Required is checked\r\n";
        formTest = false;
      }
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
</script>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr class="containerBody">
        <th colspan="2">
          <strong>General Information</strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td valign="top" class="formLabel">
          Service Contract Number
        </td>
        <td>
        <%=toHtml(ticketDetails.getServiceContractNumber())%>
        </td>
      </tr>
    </table>
<br />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="4">
        <strong>Per Day Description of Service</strong>
      </th>
    </tr>
   <tr bgcolor="#E8E8E8">
      <td width="15%" align="left">
      <b>Activity Date</b>
      </td>
      <td width="17%" nowrap>
        <b>Travel Time</b> <br />
        <b>[Towards Contract</b>
        <% if (activityDetails.getTravelTowardsServiceContract()) { %>
            <input type="checkbox" name="travelTowardsServiceContract" value="on" checked />
         <%}else{%>
            <input type="checkbox" name="travelTowardsServiceContract" value="on" />
         <%}%>
         <b>]</b>
      </td>
      <td width="17%" nowrap>
        <b>Labor Time</b><br />
        <b>[Towards Contract</b>
        <% if (activityDetails.getLaborTowardsServiceContract()) { %>
            <input type="checkbox" name="laborTowardsServiceContract" value="on" checked />
         <%}else{%>
            <input type="checkbox" name="laborTowardsServiceContract" value="on" />
         <%}%>
         <b>]</b>
      </td>
      <td width="51%">
        <b>Description of Service</b>
      </td>
    </tr>    
  <%
    boolean noneSelected = false;
    int icount = 0;
    if (activityDetails != null){
      TicketPerDayDescriptionList ticketPerDayDescriptionList = activityDetails.getTicketPerDayDescriptionList();
      if (ticketPerDayDescriptionList == null){
        noneSelected = true;
      }else{
       Iterator inumber = activityDetails.getTicketPerDayDescriptionList().iterator();
       if(inumber.hasNext()){
        while (inumber.hasNext()){
          icount++;
          TicketPerDayDescription thisDayDescription = (TicketPerDayDescription) inumber.next();
     %>
      <tr valign="top" class="containerBody">
        <input type="hidden" name="activityId<%= icount %>" value="<%= thisDayDescription.getId() %>">
        <td nowrap>
          <%
            String activityDate = "activityDate" + icount ;
            String activityDateError = "activityDate" + icount + "Error";
            String activityDateTimeZone = activityDate + "TimeZone";
          %>
          <zeroio:dateSelect form="details" field="<%=activityDate%>" timestamp="<%=thisDayDescription.getActivityDate()%>" />
          <br /><%=TimeZoneSelect.getSelect(activityDateTimeZone,thisDayDescription.getActivityDateTimeZone()).getHtml() %>
          <%= showAttribute(request, activityDateError) %>
          <br />
        </td >
        <td nowrap>
          <zeroio:durationSelect baseName="travel" count="<%=icount%>" hours="<%=thisDayDescription.getTravelHours()%>" minutes="<%=thisDayDescription.getTravelMinutes()%>" />
        </td>
        <td >
          <zeroio:durationSelect baseName="labor" count="<%=icount%>" hours="<%=thisDayDescription.getLaborHours()%>" minutes="<%=thisDayDescription.getLaborMinutes()%>" />
        </td>
        <td >
          <textarea rows="3" cols="50" name="descriptionOfService<%=icount%>"><%=toString(thisDayDescription.getDescriptionOfService())%></textarea>
        </td>
      </tr>
    <%
       }
       if (icount < 5){
       while(icount < 5){
       ++icount;
    %>
      <tr valign="top" class="containerBody">
        <input type="hidden" name="activityId<%= icount %>" value="">
        <td nowrap>
          <%
            String activityDate = "activityDate" + icount ;
            String activityDateError = "activityDate" + icount + "Error";
            String activityDateTimeZone = activityDate + "TimeZone";
          %>
          <zeroio:dateSelect form="details" field="<%=activityDate%>" />
          <br /><%=TimeZoneSelect.getSelect(activityDateTimeZone,User.getUserRecord().getTimeZone()).getHtml() %>
          <%= showAttribute(request, activityDateError) %>
          <br />
        </td >
        <td>
          <zeroio:durationSelect baseName="travel" count="<%=icount%>" />
        </td>
        <td >
          <zeroio:durationSelect baseName="labor" count="<%=icount%>" />
        </td>
        <td >
          <textarea rows="3" cols="50" name="descriptionOfService<%=icount%>"></textarea>
        </td>
      </tr>
      <%
          }
       } 
      }else{
       noneSelected = true;
      }
     }
    }
  %>
  <% if (noneSelected == true){ 
  int count = 0;
  while (count < 5){
  count++;
  %>
  <tr valign="top" class="containerBody">
    <td nowrap>
      <%
        String activityDate = "activityDate" + count ;
        String activityDateError = "activityDate" + count + "Error";
        String activityDateTimeZone = activityDate + "TimeZone";
      %>
      <zeroio:dateSelect form="details" field="<%=activityDate%>" />
      <br /><%=TimeZoneSelect.getSelect(activityDateTimeZone,User.getUserRecord().getTimeZone()).getHtml() %>
      <%= showAttribute(request, activityDateError) %>
      <br />
    </td>
    <td >
      <zeroio:durationSelect baseName="travel" count="<%=count%>" />
    </td>
    <td >
      <zeroio:durationSelect baseName="labor" count="<%=count%>" />
    </td>
    <td >
      <textarea rows="3" cols="50" name="descriptionOfService<%=count%>"></textarea>
    </td>
  </tr>
  <%}
  }%>
  </table>
  <br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Additional Information</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Follow-up Required?
    </td>
    <td>
    <%
      if (activityDetails.getFollowUpRequired()){%>
      <input type="checkbox" name="followUpRequired" value="on" checked></input>
    <%}else{%>
      <input type="checkbox" name="followUpRequired" value="on"></input>
    <%}%>
   </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Alert Date
    </td>
    <td>
      <zeroio:dateSelect form="details" field="alertDate" timestamp="<%=activityDetails.getAlertDate()%>" timeZone="<%=activityDetails.getAlertDateTimeZone()%>" showTimeZone="yes" />
      <%= showAttribute(request, "alertDateError") %><%= showWarningAttribute(request, "alertDateWarning") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Follow-up Description
    </td>
    <td>
      <textarea rows="3" cols="50" name="followUpDescription"><%=toString(activityDetails.getFollowUpDescription())%></textarea>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Phone Response Time
    </td>
    <td>
      <input type="text" size="10" name="phoneResponseTime" maxlength="10" value="<%=toHtmlValue(activityDetails.getPhoneResponseTime())%>"> </input>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      Engineer Response Time
    </td>
    <td>
      <input type="text" size="10" name="engineerResponseTime" maxlength="10" value="<%=toHtmlValue(activityDetails.getEngineerResponseTime())%>"> </input>
    </td>
  </tr>
</table>
<input type="hidden" name="onlyWarnings" value=<%=(activityDetails.getOnlyWarnings()?"on":"off")%> />
<input type="hidden" name="modified" value="<%=activityDetails.getModified()%>">
<input type="hidden" name="prevTotalTravelMinutes" value="<%=activityDetails.getTotalTravelMinutes()%>" />
<input type="hidden" name="prevTotalTravelHours" value="<%=activityDetails.getTotalTravelHours()%>" />
<input type="hidden" name="prevTotalLaborMinutes" value="<%=activityDetails.getTotalLaborMinutes()%>" />
<input type="hidden" name="prevTotalLaborHours" value="<%=activityDetails.getTotalLaborHours()%>" />
<input type="hidden" name="prevTravelTowardsServiceContract" value="<%=(activityDetails.getTravelTowardsServiceContract() ? "on":"off") %>" />
<input type="hidden" name="prevLaborTowardsServiceContract" value="<%=(activityDetails.getLaborTowardsServiceContract() ? "on":"off") %>" />
