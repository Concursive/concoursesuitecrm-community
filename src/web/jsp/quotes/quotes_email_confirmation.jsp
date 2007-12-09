<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.*,java.lang.*,org.aspcfs.modules.quotes.base.*" %>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="sentEmailAddresses" class="java.util.HashMap" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <dhv:label name="quotes.messageQueuedForEmail.text">Your message has been queued and will be sent to the following email addresses:</dhv:label>
    </th>
  </tr>
  <tr class="row1">
    <td>
      <%= toHtml((String) sentEmailAddresses.get("to")) %>
    </td>
  </tr>
<dhv:evaluate if='<%= sentEmailAddresses.get("from") != null && !"".equals(((String) sentEmailAddresses.get("from")).trim()) %>'>
  <tr class="row2">
    <td>
      <%= toHtml((String) sentEmailAddresses.get("from")) %>
    </td>
  </tr>
</dhv:evaluate>
</table>
<p>
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:window.close()">
