<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<html>
  <body onload="javascript:opener.document.getElementById('contactLink').value='<%= ContactDetails.getId() %>';opener.changeDivContent('changecontact', '<%= ContactDetails.getNameLastFirst() %>');window.close();">
</body>
</html>
