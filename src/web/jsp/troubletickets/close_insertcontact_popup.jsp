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
<script type="text/javascript">
  function addContact(text, value){
    opener.insertOption(text, value, 'contactId');
  }
</script>
  <body onload="javascript:addContact('<%= ContactDetails.getNameLastFirst() %>','<%= ContactDetails.getId() %>');window.close();">
</body>
</html>
