<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="found" class="java.lang.String" scope="request"/>
<script language="JavaScript">
  function checkForm(form) {
    if (form.doReg[0].checked == 0 && form.doReg[1].checked == 0) {
      alert("Please select a registration option to continue");
      return false;
    }
    return true;
  }
</script>
<form name="register" action="Setup.do?command=Register" method="post" onSubmit="return checkForm(this)">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>Welcome to CFS</th>
  </tr>
  <tr>
    <td>
      In order to begin using CFS, the setup process will guide you through
      several steps.<br>
      <br>
      Although installation can be completed in just a few minutes, you will
      have the option at any time during the setup to continue at a later time.<br>
      <br>
      By registering this application with Dark Horse Ventures:<br>
      - This system will be entitled to five (5) users that can be added to CFS<br>
      - The CFS administrator will receive information about software updates
      as they become available
      <br>&nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th>Registration</th>
  </tr>
  <tr>
    <td nowrap>
      <input type="radio" name="doReg" value="need" <%= !"true".equals(found) ? "checked" : "" %>/>
      Request a <b>new</b> license for this installation<br>
      <input type="radio" name="doReg" value="have" <%= !"true".equals(found) ? "disabled" : "checked" %>/>
      <dhv:evaluate if="<%= !"true".equals(found) %>"><font color="#888888"></dhv:evaluate>Continue setup from a previously started session<dhv:evaluate if="<%= !"true".equals(found) %>"></font></dhv:evaluate><br>
      <br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
