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
<jsp:useBean id="result" class="java.lang.String" scope="request" />
<jsp:useBean id="username" class="java.lang.String" scope="request" />
<dhv:evaluate if='<%=("userNotExists".equals(result))%>'><font color="red">
Sorry, name does not exist. Try to use another email or</font>
 <portlet:renderURL var="urlCreate">
		<portlet:param name="viewType" value="createAccount" />
	</portlet:renderURL>
	<A href="<%=pageContext.getAttribute("urlCreate")%>">create new
		account</A>.
	<br>
	<br>
</dhv:evaluate>
<form name="password" action="<portlet:actionURL />" method="post">
	Forgot your password? Enter the email address that you use to log in.
	You will receive an email with a password.
	<table cellpadding="4" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				E-mail
			</td>
			<td>
				<input type="text" name="username" size="35"  value="<%=username %>"/>
				<font color="red">*</font>
			</td>
		</tr>
	</table>
	<br />
	<input type="submit" name="forgotPassword" value="Submit" />
</form>
