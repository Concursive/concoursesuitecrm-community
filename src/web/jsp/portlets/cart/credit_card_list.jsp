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

<%@ page import="java.util.*,org.aspcfs.modules.orders.base.*"%>

<jsp:useBean id="creditCardList"
	class="org.aspcfs.modules.orders.base.CreditCardList" scope="request" />
<jsp:useBean id="creditCard"
	class="org.aspcfs.modules.orders.base.CreditCard" scope="request" />
<jsp:useBean id="creditCardInOrder"
	class="org.aspcfs.modules.orders.base.CreditCard" scope="session" />
<portlet:defineObjects />
<strong>Credit Card List</strong>
<%
if (creditCardList != null && !creditCardList.isEmpty()) {
%>
<form name="cardList" action="<portlet:actionURL />" method="post">

	<table cellpadding="4" cellspacing="0" border="0">
		<tr>
			<th>
				Use in Order
			</th>
			<th>
				Card Info
			</th>
			<th>
				Edit
			</th>
			<th>
				Delete
			</th>

		</tr>
		<%
		        Iterator j = creditCardList.iterator();
		        if (j.hasNext()) {
		          int rowid = 0;
		          int i = 0;
		          while (j.hasNext()) {
		            i++;
		            rowid = (rowid != 1 ? 1 : 2);
		            CreditCard card = (CreditCard) j.next();
		%>
		<tr>
			<td>
				<INPUT type="radio" name="useInOrder" value="<%=card.getId()%>" 
				<%if(creditCardInOrder!=null && card.getId()==creditCardInOrder.getId()) {%>
				checked="checked"
				<%} %>
				/>
			</td>

			<td>
				<%=card.getCardTypeName()%>, 
				<%=card.getCardNumber().substring(card.getCardNumber().length()-4)%>, <%=card.getNameOnCard()%>, Exp:
				<%=card.getExpirationMonth()%>.<%=card.getExpirationYear()%>
			</td>
			<td>
				<portlet:renderURL var="url">
					<portlet:param name="viewType" value="modifyCard" />
					<portlet:param name="cardId"
						value="<%=Integer.toString(card.getId())%>" />
				</portlet:renderURL>
				<A href="<%=pageContext.getAttribute("url")%>">Edit</A>
			</td>
			<td>
				<INPUT type="checkbox" name="deleteCardId" value="<%=card.getId()%>" />
			</td>
		</tr>
		<%
		        }
		        }
		%>
	</table>
	<br />
	<input type="submit" name="deleteCard" value="Delete" onclick="javascript:if(!confirm('Do you wish to delete this card(s)?')){return false;}"/>
	<input type="submit" name="confirmOrder" value="Continue" />
	<input type="reset" name="Clear" value="Clear" />
</form>
<br />
<%
}
%>
<form name="addCard" action="<portlet:actionURL />" method="post">
	<strong>Add Credit Card</strong>
	<%@ include file="credit_card_include.jsp"%>
	<br />
	<input type="submit" name="submit" value="Submit" />
	<input type="reset" name="Clear" value="Clear" />
</form>
