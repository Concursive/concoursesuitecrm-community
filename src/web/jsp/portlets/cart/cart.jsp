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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<%@ page import="java.io.*, 
								java.util.*, 
								org.aspcfs.modules.products.base.*,
								org.aspcfs.modules.quotes.base.QuoteProductBean" %>
<%@ include file="../../initPage.jsp" %>
<%@ include file="../../initPopupMenu.jsp" %>
<portlet:defineObjects/>
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
  <% if (request.getSession().getAttribute("CartBean") != null){ %>
The following items have been placed in the cart...<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
    
<%
		java.util.HashMap cartBean = (HashMap) request.getSession().getAttribute("CartBean");

    Iterator quoteProductIterator = cartBean.keySet().iterator(); 
%>
		<table border="0" cellpadding="0" cellspacing="5">
    <tr>
      <TH nowrap>Remove</TH>
      <TH nowrap>Thumbnail</TH>
      <TH nowrap>Product Name</TH>
      <TH nowrap colspan="2">Quantity</TH>
    </tr>
    <%
    while (quoteProductIterator.hasNext()) {
    	String productId = (String)quoteProductIterator.next();
    	QuoteProductBean quoteProductBean = (QuoteProductBean)cartBean.get(productId);
  %>
    <tr>
      <td>
      	<INPUT type="checkbox" name="quoteProductId" value="<%=productId%>">
      </td>
      <td width="140" nowrap>
        <dhv:evaluate if="<%= quoteProductBean.getProduct().getThumbnailImageId() > -1 %>"><dhv:fileItemImage id="<%= quoteProductBean.getProduct().getThumbnailImageId() %>" path="products" thumbnail="true" name="<%=  quoteProductBean.getProduct().getName() %>" /></dhv:evaluate>
        <dhv:evaluate if="<%= quoteProductBean.getProduct().getThumbnailImageId() == -1 && quoteProductBean.getProduct().getLargestImageId() > -1 %>"><dhv:fileItemImage id="<%= quoteProductBean.getProduct().getLargestImageId() %>" path="products" thumbnail="true" name="<%=  quoteProductBean.getProduct().getName() %>" /></dhv:evaluate>
        &nbsp;
      </td>
      <td>
      	<div><%= StringUtils.toHtml(quoteProductBean.getProduct().getName()) %></div>
      </td>
      <td width="10">
      	<%= quoteProductBean.getQuantity() %>
      </td>
      <td width="10">
      	<INPUT type="text" name="quantity_<%=productId%>" value="<%= quoteProductBean.getQuantity() %>" size="6">
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
    <input type="submit" name="save" value="Update Cart">&nbsp;<input type="submit" name="clear" value="Remove All">
    
    </td>
  </tr>
</table>
<hr>
    If you would like to receive a quote from a sales representative, please
    fill out the following information and choose &quot;Request Quote.&quot;<br />
    <br />
    A sales representative will review your information and contact you
    with further information.<br />
    <br />
<table cellpadding="4" cellspacing="0" border="0" >
  <tr>
    <td>Your Name</td>
    <td><INPUT type="text" name="userName" value="" size="20"></td>
  </tr>
    <tr>
    <td>Your email address</td>
    <td><INPUT type="text" name="userEmail" value="" size="30"></td>
  </tr>
  <tr>
    <td>Comments to representative</td>
    <td><textarea rows="5" cols="40" name="comments"></textarea></td>
  </tr>
  <tr>
    <td> <input type="submit" name="email" value="Request Quote" onClick="return formValidate();"></td>
    <td>&nbsp;</td>
  </tr>
</table>

 <% 
  } else {
%>There are currently no items in the cart.<br />
    <br />
    Items can be added by searching the product catalog and selecting the option to add the item to your cart.<%
  }
 %>
</form>

