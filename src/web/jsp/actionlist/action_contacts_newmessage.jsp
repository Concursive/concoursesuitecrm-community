<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<form name="newMessageForm" action="MyCFSInbox.do?command=SendMessage&actionSource=MyActionContacts" method="post" onSubmit="return sendMessage();">
<%@ include file="../newmessage.jsp" %>
<br>
<input type="submit" value="Send">
<input type="button" value="Cancel" onClick="javascript:window.close();">
<input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
</form>
