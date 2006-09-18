<%@ page import="org.aspcfs.utils.StringUtils"%>
<%--
- Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
- rights reserved. This material cannot be distributed without written
- permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
- this material for internal use is hereby granted, provided that the above
- copyright notice and this permission notice appear in all copies. DARK HORSE
- VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
- IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
- PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
- INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
- EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
- ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
- DAMAGES RELATING TO THE SOFTWARE.
-
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet"%>
<jsp:useBean id="error" class="java.lang.String" scope="request" />
<jsp:useBean id="emailaddress" class="java.lang.String" scope="request" />
<jsp:useBean id="nameFirst" class="java.lang.String" scope="request" />
<jsp:useBean id="nameLast" class="java.lang.String" scope="request" />
<jsp:useBean id="orgName" class="java.lang.String" scope="request" />
<%@ include file="../../initPage.jsp"%>
<portlet:defineObjects />

<dhv:evaluate if="<%= StringUtils.hasText(error) %>">
  <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
       <font color="red"> <%=StringUtils.toHtml(error)%></font>
       <br>
       <portlet:renderURL var="url">
       <portlet:param name="username" value="<%=emailaddress %>"/>
       <portlet:param name="viewType" value="forgotPassword" />
       </portlet:renderURL>
      <a href="<%=pageContext.getAttribute("url") %>"> Forgot your password?</a> 
      </td>
    </tr>
  </table>
  <br />
</dhv:evaluate>
<form name="createAccount" action="<portlet:actionURL />" method="post">
  <INPUT type="hidden" name="createAccount" value="1" />
  <table cellpadding="4" cellspacing="0" border="0" width="100%">
    <tr >
      <th colspan="2" align="left"> 
        <strong>Create Account</strong>
      </th>
    </tr>
    <tr >
      <td>
        <dhv:label name="quotes.emailAddress">Email Address</dhv:label>
      </td>
      <td>
        <input type="text" name="emailaddress" size="35" value="<%= toHtmlValue(emailaddress) %>"/>
        <font color="red">*</font>
      </td>
    </tr>
    <tr >
      <td >
        <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
      </td>
      <td>
        <input type="text" size="35" maxlength="80" name="nameFirst" value="<%= toHtmlValue(nameFirst) %>" />
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
      </td>
      <td>
        <input type="text" size="35" name="nameLast" value="<%= toHtmlValue(nameLast) %>">
        <font color="red">*</font>
      </td>
    </tr>
    <tr>
      <td >
        <dhv:label name="">Company</dhv:label>
      </td>
      <td>
        <input type="text" size="35" maxlength="255" name="orgName" value="<%= toHtmlValue(orgName) %>" />
      </td>
    </tr>
    <tr>
      <td >
        <dhv:label name="">Password</dhv:label>
      </td>
      <td>
        <input type="password" size="35" maxlength="255" name="password" value="" />
        <font color="red">*</font>
      </td>
    </tr>
    <tr>
      <td>
        <dhv:label name="">Confirm password</dhv:label>
      </td>
      <td>
        <input type="password" size="35" maxlength="255" name="confirmPassword" value="" />
        <font color="red">*</font>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" name="Submit" value="Submit" />
  <input type="reset" name="Clear" value="Clear" />
</form>
