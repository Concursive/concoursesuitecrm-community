<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="registration" class="org.aspcfs.modules.setup.beans.RegistrationBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th><dhv:label name="setup.registrationSent">Registration Sent</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.registrationInformationSubmitted.text">Your registration information has been submitted to Dark Horse Ventures, LLC.</dhv:label><br />
      <br />
      <dhv:label name="setup.confirmationByEmail.text">A confirmation will be sent by email, as well as the license file for this system.</dhv:label><br />
      <br />
      <dhv:label name="setup.emailSentTo.colon">The email will be sent to:</dhv:label> <b><%= toHtml(registration.getEmail()) %></b>
      <br />&nbsp;
    </td>
  </tr>
  <tr class="sectionTitle">
    <th><dhv:label name="setup.nextAction">Next Action</dhv:label></th>
  </tr>
  <tr>
    <td>
      <dhv:label name="setup.proceedToValidationStep.text">Proceed to the validation step once the license file has been been received by email.</dhv:label>
      <br />&nbsp;<br />
      <input type="button" value="<dhv:label name="button.continueR">Continue ></dhv:label>" onClick="javascript:window.location.href='Setup.do?command=Register&doReg=have'" />
    </td>
  </tr>
</table>
