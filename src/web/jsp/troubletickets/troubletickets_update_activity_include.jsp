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
      if (fields[i].name.substring(0,12).match("activityDate")){
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
      if ((!activityDates[i].value == "") && (!checkDate(activityDates[i].value))){
        message += "- Check that Activity Date in row "+ i + " is entered correctly\r\n";
        formTest = false;
      }
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
      if ((!form.alertDate.value == "") && (!checkDate(form.alertDate.value))) { 
        message += "- Check that Alert Date is entered correctly\r\n";
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
      <td width="17%">
        <b>Travel Time</b> <br />
        <b>[Towards Contract</b>
        <% if (activityDetails.getTravelTowardsServiceContract()) { %>
            <input type="checkbox" name="travelTowardsServiceContract" value="on" checked />
         <%}else{%>
            <input type="checkbox" name="travelTowardsServiceContract" value="on" />
         <%}%>
         <b>]</b>
      </td>
      <td width="17%">
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
       Iterator inumber = activityDetails.getTicketPerDayDescriptionList().iterator();
      if(inumber.hasNext()){
      while (inumber.hasNext()){
        icount++;
        TicketPerDayDescription thisDayDescription = (TicketPerDayDescription) inumber.next();
     %>
      <tr valign="top" class="containerBody">
        <input type="hidden" name="activityId<%= icount %>" value="<%= thisDayDescription.getId() %>">
        <td nowrap>
          <input type="text" size="10" name="activityDate<%=icount%>" value="<dhv:tz timestamp="<%=thisDayDescription.getActivityDate()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
          <a href="javascript:popCalendar('details', 'activityDate<%=icount%>');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a><br />
          (mm/dd/yyyy)
        </td >
        <td>
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
          <input type="text" size="10" name="activityDate<%=icount%>" value="">
          <a href="javascript:popCalendar('details', 'activityDate<%=icount%>');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a><br />
          (mm/dd/yyyy)
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
  %>
  <% if (noneSelected == true){ 
  int count = 0;
  while (count <=5){
  count++;
  %>
  <tr valign="top" class="containerBody">
    <td nowrap>
      <input type="text" size="10" name="activityDate<%=count%>">
      <a href="javascript:popCalendar('details', 'activityDate<%=count%>');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a><br />
      (mm/dd/yyyy)
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
      <input type="text" size="10" name="alertDate" value="<dhv:tz timestamp="<%=activityDetails.getAlertDate()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" />">
      <a href="javascript:popCalendar('details', 'alertDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
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
<input type="hidden" name="modified" value="<%=activityDetails.getModified()%>">
<input type="hidden" name="prevTotalTravelMinutes" value="<%=activityDetails.getTotalTravelMinutes()%>" />
<input type="hidden" name="prevTotalTravelHours" value="<%=activityDetails.getTotalTravelHours()%>" />
<input type="hidden" name="prevTotalLaborMinutes" value="<%=activityDetails.getTotalLaborMinutes()%>" />
<input type="hidden" name="prevTotalLaborHours" value="<%=activityDetails.getTotalLaborHours()%>" />
<input type="hidden" name="prevTravelTowardsServiceContract" value="<%=(activityDetails.getTravelTowardsServiceContract() ? "on":"off") %>" />
<input type="hidden" name="prevLaborTowardsServiceContract" value="<%=(activityDetails.getLaborTowardsServiceContract() ? "on":"off") %>" />
