<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<body onload="window.opener.location=window.opener.location;">
<p>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      Your message has been queued and will be sent to the following contacts:
    </th>
  </tr>
  <tr class="row2">
    <td>
      <%= Recipient.getNameLastFirst() %> (<%= Recipient.getEmailAddress("Business") %>)
    </td>
  </tr>
</table>
<p>
<input type="button" value="Close" onClick="javascript:window.close()">
</body>
