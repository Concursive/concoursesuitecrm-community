<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript">
  onLoad = 1;
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

    if (form.roleId.value < 1){ 
      message += "- Portal Role is required\r\n";
      formTest = false;
    }
    if ((!form.expires.value == "") && (!checkDate(form.expires.value))) {
      message += "- Check that Expiration Date is entered correctly\r\n";
      formTest = false;
      initialStartDateValid = false;
    }
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    }
  }
</script>
<input type="hidden" name="userId" value="<%=portalUserDetails.getId()%>">
<input type="hidden" name="enabled" value="<%=portalUserDetails.getEnabled()%>">
<input type="hidden" name="contactId" value="<%=ContactDetails.getId()%>">
<input type="hidden" name="orgId" value="<%=ContactDetails.getOrgId()%>">
<input type="hidden" name="dosubmit" value="true" />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Details</strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= (portalUserDetails.getUsername() != null) %>" >
  <tr class="containerBody">
    <td class="formLabel">
      Username
    </td>
    <td>
      <%=portalUserDetails.getUsername()%>
    </td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel">
      Portal Role
    </td>
    <td>
      <%= roleList.getHtmlSelect("roleId", portalUserDetails.getRoleId())%><font color="red">*</font>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Expiration Date
    </td>
    <td>
      <input type="text" size="10" name="expires" maxlength="10" value="<dhv:tz timestamp="<%= portalUserDetails.getExpires() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>"/>">
      <a href="javascript:popCalendar('contactPortal', 'expires');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a> (mm/dd/yyyy)
    </td>
  </tr>
  <dhv:evaluate if="<%= (portalUserDetails.getUsername() != null) %>" >
  <tr class="containerBody">
    <td class="formLabel" >
      Generate new password?
    </td>
    <td>
    <input type="checkbox" name="autoGenerate" value="on"></input>
    </td>
  </tr>
  <input type="hidden" name="modified" value="<%=portalUserDetails.getModified()%>" />
  </dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel">
      Email
    </td>
    <td>
      <%= ContactDetails.getEmailAddressList().getHtmlSelect("emailAddressId", -1)%><font color="red"> Login information would be sent to this email or to the primary email of the contact</font>
    </td>
  </tr>
</table>
