<jsp:useBean id="ContactTypeList" class="com.darkhorseventures.cfsbase.ContactTypeList" scope="request"/>
<jsp:useBean id="ContactList" class="com.darkhorseventures.cfsbase.ContactList" scope="request"/>
<%@ include file="initPage.jsp" %>
<%
  ContactTypeList.setJsEvent("ONCHANGE=\"javascript:document.forms[0].submit();\"");
%>
<form name="addUser" action="/Users.do?command=InsertUserForm" method="post">
<table cellpadding=6 cellspacing=0 border=1 width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr>
    <td align="left" bgcolor="#DEE0FA">
    <strong>Add a user account</strong>
    </td>
  </tr>
  <tr>
    <td align="left">
To create a new user account, you must choose a contact from the list below.<br>
Each account is associated with a contact record for tracking purposes.
    </td>
  </tr>
  <tr>
    <td align="left" bgcolor="#E5E5E5">
      <strong>Begin by selecting an existing contact</strong>
    </td>
  </tr>
  <tr>
    <td align="left">
      First, choose a contact category to select from <%= ContactTypeList.getHtmlSelect("typeId") %><font color="red">*</font><br>
      &nbsp;<br>
      Then, choose an existing contact for the new account <%= ContactList.getHtmlSelect("contactId") %><font color="red">*</font>
      <%= showAttribute(request, "usernameError") %><br>
      (*) indicates the contact already has an account
    </td>
  </tr>
<!--  
  <tr>
    <td align="left" bgcolor="E5E5E5">
      <strong>New Contact</strong>
    </td>
  </tr>
  <tr>
    <td align="left">
      or, if contact is not already in the system<br>
      &nbsp;<br>
      <input type="checkbox" name="newAccount" value="on">Mark to create a new Contact and User Account
    </td>
  </tr>
-->  
  <tr>
    <td align="left">
      <input type="button" value="Cancel" onClick="javascript:this.form.action='/Users.do?command=ListUsers';this.form.submit()">
      <input type="submit" value="Next >" onClick="javascript:this.form.action='/Users.do?command=InsertUserDecision'">
    </td>
  </tr>
</table>
</form>
