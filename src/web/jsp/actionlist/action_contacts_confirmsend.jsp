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
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<jsp:useBean id="bcc" class="java.lang.String" scope="request"/>
<jsp:useBean id="cc" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onload="window.opener.location=window.opener.location;">
<p>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <dhv:label name="actionList.messageSentToContacts.text">Your message has been queued and will be sent to the following contacts:</dhv:label>
    </th>
  </tr>
  <tr class="row2">
    <td>
      <%= Recipient.getNameLastFirst() %> (<%= Recipient.getPrimaryEmailAddress() %>)
    </td>
  </tr>
  <dhv:evaluate if="<%= cc != null && !"".equals(cc) %>">
  <tr class="row2">
    <td>
      <dhv:label name="quotes.cc">CC</dhv:label>: <%= toHtml(cc) %>
    </td>
  </tr>
  </dhv:evaluate>
  <dhv:evaluate if="<%= bcc != null && !"".equals(bcc) %>">
  <tr class="row2">
    <td>
      <dhv:label name="quotes.bcc">BCC</dhv:label>: <%= toHtml(bcc) %>
    </td>
  </tr>
  </dhv:evaluate>
</table>
<p>
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:window.close()">
</body>
