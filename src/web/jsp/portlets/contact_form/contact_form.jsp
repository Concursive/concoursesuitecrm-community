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
- Version: $Id: accounts_add.jsp 13563 2005-12-12 16:13:25Z mrajkowski $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<jsp:useBean id="introduction" class="java.lang.String" scope="request"/>
<jsp:useBean id="nameFirst" class="java.lang.String" scope="request"/>
<jsp:useBean id="showEmail" class="java.lang.String" scope="request"/>
<jsp:useBean id="source" class="java.lang.String" scope="request"/>
<jsp:useBean id="emailRequired" class="java.lang.String" scope="request"/>
<portlet:defineObjects/>
<dhv:evaluate if="<%= StringUtils.hasText(introduction) %>">
  <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
      <td>
        <%= StringUtils.toHtml(introduction) %>
      </td>
    </tr>
  </table>
  <br />
</dhv:evaluate>
<form name="contactForm" action="<portlet:actionURL />" method="post">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Contact Form</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.FirstName">First Name</dhv:label>
      </td>
      <td>
        <input type="text" size="35" maxlength="80" name="nameFirst" value="<%= StringUtils.toHtmlValue(nameFirst) %>" />
        <%--
        <input type="text" size="35" name="nameFirst" value="<%= StringUtils.toHtmlValue(ContactDetails.getNameFirst()) %>">
        --%>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="accounts.accounts_add.LastName">Last Name</dhv:label>
      </td>
      <td>
        <input type="text" size="35" maxlength="80" name="nameLast" value="" />
        <%--
        <input type="text" size="35" name="nameLast" value="<%= StringUtils.toHtmlValue(ContactDetails.getNameLast()) %>">
        <font color="red">*</font> <%= showAttribute(request, "nameLastError") %>
        --%>
      </td>
    </tr>
    <dhv:evaluate if='<%= "true".equals(showEmail) %>'>
    <tr>
      <td class="formLabel" nowrap><dhv:label name="quotes.emailAddress">Email Address</dhv:label></td>
      <td>
        <input type="text" name="email1address" size="25"/>
        <dhv:evaluate if='<%= "true".equals(emailRequired) %>'>
          <font color="red">*</font>
        </dhv:evaluate>
      </td>
    </tr>
    </dhv:evaluate>
    <tr class="containerBody">
      <td norwap class="formLabel">
        <dhv:label name="">Organization Name</dhv:label>
      </td>
      <td>
        <input type="text" size="35" maxlength="255" name="orgName" value="" />
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Questions/Comments</dhv:label>
      </td>
      <td>
        <textarea name="comments" rows="3" cols="50"></textarea>
      </td>
    </tr>
  </table>
  <br />
  <input type="hidden" name="source" value="<%= source %>" />
  <input type="submit" name="Submit" value="Submit" />
</form>
