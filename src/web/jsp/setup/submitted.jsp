<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="registration" class="org.aspcfs.modules.setup.beans.RegistrationBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>Registration Sent</th>
  </tr>
  <tr>
    <td>
      Your registration information has been submitted to
      Dark Horse Ventures.<br>
      <br>
      A confirmation will be sent by email, as well as the
      license file for this system.<br>
      <br>
      The email will be sent to: <%= toHtml(registration.getEmail()) %>
      <br>&nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th>Next Action</th>
  </tr>
  <tr>
    <td>
      Proceed to the validation step once the
      license file has been been received by email.
      <br>&nbsp;<br>
      <input type="button" value="Continue >" onClick="javascript:window.location.href='Setup.do?command=Register&doReg=have'"/>
    </td>
  </tr>
</table>
