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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet"%>
<portlet:defineObjects />
<jsp:useBean id="LoginFailedMessage" class="java.lang.String"
	scope="request" />
<form name="loginForm" action="<portlet:actionURL />" method="post">
	<INPUT type="hidden" name="login" value="1" />
	<dhv:evaluate if="<%= LoginFailedMessage!=null %>">
	<font color="red"><%=LoginFailedMessage %></font>
	</dhv:evaluate>
	<table cellpadding="4" cellspacing="0" border="0">
	<tr>
	<td>
	<table cellpadding="4" cellspacing="0" border="0" >
		<tr>
			<th colspan="2" align="left">
				<strong>Login</strong>
			</th>
		</tr>
		<tr>
			<td>
				Username
			</td>
			<td>
				<input type="text" name="username" size="35" />
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td>
				<dhv:label name="">Password</dhv:label>
			</td>
			<td>
				<input type="password" size="35" maxlength="255" name="password"
					value="" />
				<font color="red">*</font>
			</td>
		</tr>
	</table>
	</td>
	<td align="left">
	 <portlet:renderURL var="urlCreate">
           <portlet:param name="viewType" value="createAccount"/>
        </portlet:renderURL>
        <A href="<%= pageContext.getAttribute("urlCreate") %>">Create New Account</A><br><br>
        <portlet:renderURL var="url">
           <portlet:param name="viewType" value="forgotPassword"/>
        </portlet:renderURL>
        <A href="<%= pageContext.getAttribute("url") %>">Forgot your password?</A>
	</td>
	
	</tr>
	</table>
	<br />
	<input type="submit" name="Submit" value="Submit" />

</form>
