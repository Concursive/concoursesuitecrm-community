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
      <zeroio:dateSelect form="contactPortal" field="expires" timestamp="<%= portalUserDetails.getExpires() %>" />
      <%= showAttribute(request, "expiresError") %>
    </td>
  </tr>
  <dhv:evaluate if="<%= (portalUserDetails.getId() != -1) %>" >
  <tr class="containerBody">
    <td class="formLabel" >
      Generate new password?
    </td>
    <td>
    <input type="checkbox" name="autoGenerate" value="on"  <%=("on".equals(request.getParameter("autoGenerate")) ? " checked" : "")%> ></input>
    </td>
  </tr>
  <input type="hidden" name="modified" value="<%=portalUserDetails.getModified()%>" />
  </dhv:evaluate>
  <tr class="containerBody">
    <td class="formLabel">
      Email
    </td>
    <td>
      <% 
        String tmpId = request.getParameter("emailAddressId");
        int emailId = -1;
        if ((tmpId != null) && (!"".equals(tmpId))){
          emailId = Integer.parseInt(tmpId);
        }
      %>
      <%= ContactDetails.getEmailAddressList().getHtmlSelect("emailAddressId", emailId)%><font color="red"> Login information would be sent to this email or to the primary email of the contact</font>
    </td>
  </tr>
</table>
