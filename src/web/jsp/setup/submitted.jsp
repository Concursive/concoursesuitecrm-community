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
