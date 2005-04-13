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
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.utils.web.* " %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<html>
<head>
  <title>Thank you for visiting our contact information update page</title>
<script language="JavaScript">
</script>
</head>
<body>
  <form name="addContact" action="ProcessAddressSurvey.do?command=Update&auto-populate=true" onSubmit="return doCheck(this);window.close()" method="post">
    &nbsp;<br>
    &nbsp;<br>
    <center>
    <table cellpadding="4" cellspacing="0" border="0" width="85%">
      <tr class="row1">
        <td>
          <font color="#8c8c8c"><strong>Address Confirmation</strong></font>
        </td>
      </tr>
    </table>
    &nbsp;<br />
    <table cellpadding="4" cellspacing="0" border="0" width="85%">
      <tr>
        <td>
          <strong>Please click OK to confirm that your address on file is current and accurate.</strong></font>
        </td>
      </tr>
    </table>
    &nbsp;<br />
    <table cellpadding="4" cellspacing="0" border="0" width="85%">
      <tr>
        <td>
          <input type="submit" value="OK" />
          <input type="button" value="Cancel" onClick="window.opener=self;window.close()" />
          <input type="hidden" name="dosubmit" value="true" />
          <input type="hidden" name="enteredBy" value="<%=ContactDetails.getEnteredBy()%>" />
          <input type="hidden" name="modifiedBy" value="<%=ContactDetails.getModifiedBy()%>" />
          <input type="hidden" name="id" value="<%=request.getAttribute("id")%>" />
        </td>
      </tr>
      <tr class="row1">
        <td align="center">
          <font color="#8c8c8c"><strong>(C) 2000-2005 Dark Horse Ventures, LLC</strong></font>
        </td>
      </tr>
    </table>
    <br>
  </form>
 </body>
</html>
