<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="Message" class="org.aspcfs.modules.communications.base.Message" scope="request"/>
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<body onload="window.opener.location=window.opener.location;">
<p>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      Your message is in the queue to be sent to the following: 
    </td>
  </tr>
  <tr class="row2">
    <td>
      <%= Recipient.getNameLastFirst() %> at <%= Recipient.getEmailAddress("Business") %>
    </td>
  </tr>
</table>
</body>
