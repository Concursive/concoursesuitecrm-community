<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    if (form.license.value.length == 0) {
      alert("Enter the license key in the field to continue");
      return false;
    }
    return true;
  }
</script>
<body onLoad="javascript:document.forms[0].license.focus();">
<%= showError(request, "actionError", false) %>
<form name="register" action="Setup.do?command=Validate" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>Information</th>
  </tr>
  <tr>
    <td>
      If you have already registered for Dark Horse CRM, you should have received a
      registration key by email.<br>
      <br>
      This registration key can only be used on the system that requested it.<br>
      <br>
      If you have misplaced your key or you are installing Dark Horse CRM on a different server, then you can
      <a href="Setup.do?command=Register">request a new key</a>
      to be sent by email.<br>
      &nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th>Validation</th>
  </tr>
  <tr>
    <td nowrap>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            License Key
          </td>
          <td nowrap>
            Paste your registration key into the text field to continue:<br>
            <textarea cols="60" rows="5" name="license"></textarea>
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
</body>
