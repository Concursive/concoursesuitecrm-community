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
<%@ page import="java.util.*" %>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<%@ include file="../initPage.jsp" %>
<font size="-1" face="Arial, Helvetica, sans-serif">
<dhv:label name="campaign.from.colon" param="<%= "from="+toHtml(Message.getReplyTo()) %>"><strong>From:</strong> <%= toHtml(Message.getReplyTo()) %></dhv:label><br />
<dhv:label name="mail.label.subject" param="<%= "subject="+toHtml(Message.getMessageSubject()) %>"><strong>Subject:</strong> <%= toHtml(Message.getMessageSubject()) %></dhv:label>
<br />&nbsp;<br />
<%= Message.getMessageText() %> 
</font>
