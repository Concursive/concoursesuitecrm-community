<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.cfsbase.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactPhoneTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactEmailTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="ContactAddressTypeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:document.forms[0].nameFirst.focus();">
<form name="addContact" action="/Contacts.do?command=Insert&auto-populate=true" method="post">
<a href="/Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>">Back to Contact List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <a href="/Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Details</font></a><dhv:permission name="accounts-accounts-folders-view"> | 
      <a href="/Accounts.do?command=Fields&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Folders</font></a></dhv:permission><dhv:permission name="accounts-accounts-contacts-view"> |
      <a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#0000FF">Contacts</font></a></dhv:permission><dhv:permission name="accounts-accounts-opportunities-view"> | 
      <a href="Opportunities.do?command=View&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Opportunities</font></a></dhv:permission><dhv:permission name="accounts-accounts-tickets-view"> | 
      <a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>"><font color="#000000">Tickets</font></a></dhv:permission><dhv:permission name="accounts-accounts-documents-view"> |
      <a href="AccountsDocuments.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><font color="#000000">Documents</font></a></dhv:permission>
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<input type="hidden" name="orgId" value="<%= request.getParameter("orgId") %>">
<input type=submit value="Save">
<input type=reset value="Reset">
<br>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan=2 valign=center align=left>
      <strong>Add a New Contact</strong>
    </td>     
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Contact Type
    </td>
    <td valign=center>
      <%=ContactTypeList.getHtmlSelect("typeId", ContactDetails.getTypeId())%>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      First Name
    </td>
    <td valign=center>
      <input type=text size=35 name="nameFirst" value="<%= toHtmlValue(ContactDetails.getNameFirst()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    Middle Name
    </td>
    <td valign=center>
      <input type=text size=35 name="nameMiddle" value="<%= toHtmlValue(ContactDetails.getNameMiddle()) %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Last Name
    </td>
    <td valign=center>
      <input type=text size=35 name="nameLast" value="<%= toHtmlValue(ContactDetails.getNameLast()) %>">
      <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Title
    </td>
    <td valign=center>
      <input type=text size=35 name="title" value="<%= toHtmlValue(ContactDetails.getTitle()) %>">
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
	    <strong>Email Addresses</strong>
	  </td>
  </tr>
  <tr class="containerBody">
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email1type", ((ContactDetails.getEmailAddressTypeId(1)==-1)?1:ContactDetails.getEmailAddressTypeId(1))) %>
      <input type=text size=40 name="email1address" maxlength=255>
    </td>
  </tr>
  <tr class="containerBody">
    <td>
      <%= ContactEmailTypeList.getHtmlSelect("email2type", ((ContactDetails.getEmailAddressTypeId(2)==-1)?2:ContactDetails.getEmailAddressTypeId(2))) %>
      <input type=text size=40 name="email2address" maxlength=255>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left>
	    <strong>Phone Numbers</strong>
	  </td>
  </tr>
  <tr class="containerBody">
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone1type", "Business") %>
      <input type=text size=3 name="phone1ac" maxlength=3>-
      <input type=text size=3 name="phone1pre" maxlength=3>-
      <input type=text size=4 name="phone1number" maxlength=4>ext.
      <input type=text size=5 name="phone1ext" maxlength=10>
    </td>
  </tr>
  <tr class="containerBody">
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone2type", "Home") %>
      <input type=text size=3 name="phone2ac" maxlength=3>-
      <input type=text size=3 name="phone2pre" maxlength=3>-
      <input type=text size=4 name="phone2number" maxlength=4>ext.
      <input type=text size=5 name="phone2ext" maxlength=10>
    </td>
  </tr>
  <tr class="containerBody">
    <td>
      <%= ContactPhoneTypeList.getHtmlSelect("phone3type", "Mobile") %>
      <input type=text size=3 name="phone3ac" maxlength=3>-
      <input type=text size=3 name="phone3pre" maxlength=3>-
      <input type=text size=4 name="phone3number" maxlength=4>ext.
      <input type=text size=5 name="phone3ext" maxlength=10>
    </td>
  </tr>
</table>
&nbsp;<br>  
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td valign=center align=left colspan="2">
      <strong>Addresses</strong>
    </td>
  </tr>
  <tr class="containerBody">
    <td>
      &nbsp;
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address1type", "Business") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address1line1" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address1line2" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address1city" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <input type=text size=12 name="address1state" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=28 name="address1zip" maxlength=12>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <input type=text size=28 name="address1country" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
		<td colspan="2">&nbsp;</td>
	</tr>
  <tr class="containerBody">
    <td>
      &nbsp;
    </td>
    <td>
      <%= ContactAddressTypeList.getHtmlSelect("address2type", "Home") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 1
    </td>
    <td>
      <input type=text size=40 name="address2line1" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Address Line 2
    </td>
    <td>
      <input type=text size=40 name="address2line2" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      City
    </td>
    <td>
      <input type=text size=28 name="address2city" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      State/Province
    </td>
    <td>
      <input type=text size=12 name="address2state" maxlength=80>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Zip/Postal Code
    </td>
    <td>
      <input type=text size=28 name="address2zip" maxlength=12>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      Country
    </td>
    <td>
      <input type=text size=28 name="address2country" maxlength=80>
    </td>
  </tr>
</table>
<br>
<input type=submit value="Save">
<input type=reset value="Reset">
    </td>
  </tr>
</table>
</form>
</body>
