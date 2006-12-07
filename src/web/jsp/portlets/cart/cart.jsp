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
- Version: $Id:  $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.QuoteProductBean"%>
<%@ include file="../../initPage.jsp"%>
<%@ include file="../../initPopupMenu.jsp"%>
<jsp:useBean id="CART_EMPTY_MESSAGE" class="java.lang.String" scope="request" />
<jsp:useBean id="INCLUDE_ORDER" class="java.lang.String" scope="request" />
<portlet:defineObjects />
<script language="javascript">
  function formValidate(){
    var email =  document.getElementById('userEmail').value;
		if( new RegExp('^[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+@[-!#$%&\'*+\\/0-9=?A-Z^_`a-z{|}~]+\.[-!#$%&\'*+\\./0-9=?A-Z^_`a-z{|}~]+$').test(email)){
			return true
		}else{ alert('E-mail address is not valid');
      return false;
    }
  }
  </script>
<form name="cart" action="<portlet:actionURL/>" method="post">
	<%
	if (request.getSession().getAttribute("CartBean") != null) {
	%>
	The following items have been placed in the cart...
	<br />
	<table cellpadding="4" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<%
				            java.util.HashMap cartBean = (HashMap) request.getSession()
				            .getAttribute("CartBean");

				        Iterator quoteProductIterator = cartBean.keySet().iterator();
				%>
				<table border="0" cellpadding="0" cellspacing="5">
					<tr>
						<TH>
							Remove
						</TH>
						<TH>
							Thumbnail
						</TH>
						<TH>
							Product Name
						</TH>
						<TH>
							Quantity
						</TH>
					</tr>
					<%
					          while (quoteProductIterator.hasNext()) {
					          String productId = (String) quoteProductIterator.next();
					          QuoteProductBean quoteProductBean = (QuoteProductBean) cartBean
					              .get(productId);
					%>
					<tr>
						<td width="20">
							<INPUT type="checkbox" name="quoteProductId"
								value="<%=productId%>">
						</td>
						<td width="140">
							<dhv:evaluate
								if="<%=quoteProductBean.getProduct().getThumbnailImageId() > -1%>">
								<dhv:fileItemImage
									id="<%=quoteProductBean.getProduct().getThumbnailImageId()%>"
									path="products" thumbnail="true"
									name="<%=quoteProductBean.getProduct().getName()%>" />
							</dhv:evaluate>
							<dhv:evaluate
								if="<%=quoteProductBean.getProduct().getThumbnailImageId() == -1%>">
								<dhv:fileItemImage
									id="<%=quoteProductBean.getProduct().getLargestImageId()%>"
									path="products" thumbnail="true"
									name="<%=quoteProductBean.getProduct().getName()%>" />
							</dhv:evaluate>
						</td>
						<td>
							<div>
								<%=quoteProductBean.getProduct().getName()%>
							</div>
						</td>
						<td width="10">
							<INPUT type="text" name="quantity_<%=productId%>"
								value="<%=quoteProductBean.getQuantity()%>" size="6">
						</td>
					</tr>
					<%
					}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td style="text-align:left;">
				<input type="submit" name="save" value="Update Cart">
				&nbsp;
				<input type="submit" name="clear" value="Remove All">
				&nbsp;
				<dhv:evaluate if="<%="true".equals(INCLUDE_ORDER) %>">
				<portlet:renderURL var="url">
           			<portlet:param value="customerLogin" name="viewType"/>
        		</portlet:renderURL>
				<input type="button" name="checkout" value="Checkout" onclick="javascript:window.location.href='<%= pageContext.getAttribute("url") %>'" >
				&nbsp;
				</dhv:evaluate>
				<input type="submit" name="requestQuote" value="Request a quote">

			</td>
		</tr>
	</table>
	<%
	} else {
	%>
	<div>
		<%=CART_EMPTY_MESSAGE%>
	</div>
	<%
	}
	%>
</form>

