<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.forms[0].searchFirstName.focus();">
<form name="searchContact" action="/ExternalContacts.do?command=ListContacts&auto-populate=true" method="post">
<a href="/ExternalContacts.do">Contacts &amp; Resources</a> > 
Search Contacts<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Search Contacts</strong>
    </td>     
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      First Name
    </td>
    <td>
      <input type=text size=35 name="searchFirstName" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Middle Name
    </td>
    <td>
      <input type=text size=35 name="searchMiddleName" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type=text size=35 name="searchLastName" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Company
    </td>
    <td>
      <input type=text size=35 name="searchCompany" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Title
    </td>
    <td>
      <input type=text size=35 name="searchTitle" value="">
    </td>
  </tr>
</table>
<br>
<input type=submit value="Search">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='ExternalContacts.do?command=ListContacts'">
<input type="reset" value="Reset">
<input type="hidden" name="doSearch" value="true">
</form>
</body>
