<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<html>
  <body onload="javascript:opener.document.getElementById('contactLink').value='<%= ContactDetails.getId() %>';opener.changeDivContent('changecontact', '<%= ContactDetails.getNameLastFirst() %>');window.close();">
</body>
</html>
