<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*" %>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<%@ include file="../initPage.jsp" %>
<font size="-1" face="Arial, Helvetica, sans-serif">
From: <%= Message.getReplyTo() %><br>
Subject: <%= toHtml(Message.getMessageSubject()) %><br>&nbsp;<br>
<%= Message.getMessageText() %> 
</font>
