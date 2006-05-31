<script language="JavaScript">
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
    if (checkNullString(form.type.value)) {
      message += label("check.clientName", "- Name is a required field\r\n");
      formTest = false;
    }
    if (checkNullString(form.code.value)) {
      message += label("check.clientCode", "- Code is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
  }
</script>
<input type="hidden" name="id" value="<%= syncClient.getId() %>">
<input type="hidden" name="modifiedBy"
       value="<%= syncClient.getModifiedBy() %>">
<input type="hidden" name="enteredBy" value="<%= syncClient.getEnteredBy() %>">
<br/>
<dhv:formMessage/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="<%= titleLabel %>"><%= title %></dhv:label>
      </strong>
    </th>
  </tr>
  <dhv:evaluate if="<%= syncClient.getId() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.Id">ID</dhv:label>
      </td>
      <td>
        <%= syncClient.getId() %>
      </td>
    </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="dynamicForm.name">Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="type" maxlength="100"
             value="<%= toHtmlValue(syncClient.getType()) %>">
      <font color="red">*</font> <%= showAttribute(request, "typeError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="version" maxlength="50"
             value="<%= toHtmlValue(syncClient.getVersion()) %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel" nowrap><dhv:label name="product.enabled">
      Enabled</dhv:label></td>
    <td>
      <% String enabledChecked = syncClient.getEnabled() ? " checked" : ""; %>
      <input type="checkbox" name="enabled" value="true" <%= enabledChecked %> >
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="product.code">Code</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="code" maxlength="255"
             value="<%= toHtmlValue(syncClient.getCode()) %>">
      <font color="red">*</font> <%= showAttribute(request, "codeError") %>
    </td>
  </tr>
  <dhv:evaluate if="<%= syncClient.getId() != -1 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.EnteredBy">Entered By</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= syncClient.getEnteredBy() %>"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= entered %>"
                   timeFormat="<%= DateFormat.LONG %>"
                   timeZone="<%= User.getTimeZone()%>"/><br/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label>
      </td>
      <td>
        <dhv:username id="<%= syncClient.getModifiedBy() %>"/>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="dynamicForm.survey.modified">Modified</dhv:label>
      </td>
      <td>
        <zeroio:tz timestamp="<%= modified %>"
                   timeFormat="<%= DateFormat.LONG %>"
                   timeZone="<%= User.getTimeZone()%>"/><br/>
      </td>
    </tr>
  </dhv:evaluate>
</table>
&nbsp;<br>
<%= addHiddenParams(request, "popup|source") %>

