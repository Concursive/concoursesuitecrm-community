<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.cfsbase.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:document.forms[0].firstname.focus();">
<form name="searchContact" action="/ExternalContacts.do?command=ListContacts&auto-populate=true" method="post">
<!--input type=submit value="Search">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/ExternalContacts.do?command=ListContacts'">
<input type="reset" value="Reset"-->
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
      <input type=text size=35 name="searchFirst" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Middle Name
    </td>
    <td>
      <input type=text size=35 name="searchMiddle" value="">
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td>
      <input type=text size=35 name="searchLast" value="">
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
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/ExternalContacts.do?command=ListContacts'">
<input type="reset" value="Reset">
</form>
</body>
