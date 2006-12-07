<%--
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: product_catalogs_search.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*"%>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request" />
<jsp:useBean id="searchcodeGroupKeywords" class="java.lang.String" scope="request" />
<jsp:useBean id="searchSku" class="java.lang.String" scope="request" />
<jsp:useBean id="searchcodePriceRangeMin" class="java.lang.String" scope="request" />
<jsp:useBean id="searchcodePriceRangeMax" class="java.lang.String" scope="request" />
<jsp:useBean id="searchcodeCategoryId" class="java.lang.String" scope="request" />
<jsp:useBean id="searchCategoryNames" class="java.lang.String" scope="request" />
<jsp:useBean id="searchCategoryListIds" class="java.lang.String" scope="request" />
<jsp:useBean id="searchcodeDateAfter" class="java.lang.String" scope="request" />
<jsp:useBean id="searchItems" class="java.lang.String" scope="request" />
<jsp:useBean id="timeZone" class="java.lang.String" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<%@ include file="../../initPopupMenu.jsp" %>
<portlet:defineObjects/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="Javascript">
  function clearForm() {
    document.forms['searchCondition'].forwardsearchcodeGroupKeywords.value = "";
    document.forms['searchCondition'].forwardsearchSku.value="";
    document.forms['searchCondition'].forwardsearchcodePriceRangeMin.value="";
    document.forms['searchCondition'].forwardsearchcodePriceRangeMax.value="";
		document.forms['searchCondition'].forwardsearchCategoryListIds.value = "";
    document.forms['searchCondition'].forwardsearchCategoryNames.value = "";
    changeDivContent('changecategory', 'All');
		setSearchcodeDateAfter("-1");

    document.forms['searchCondition'].forwardsearchcodeGroupKeywords.focus();
    //changeDivContent('changecategory', label('label.all','All'));
  }
	function setSearchcodeDateAfter(selected){
		for(i=0;i<document.forms['searchCondition'].forwardsearchcodeDateAfter.length;i++){
		 if(document.forms['searchCondition'].forwardsearchcodeDateAfter[i].value== selected){
				document.forms['searchCondition'].forwardsearchcodeDateAfter[i].selected = true;
				break;
			}
		}
	}

  function clearProductCategory() {
    document.forms['searchCondition'].forwardsearchCategoryListIds.value = "";
    //changeDivContent('changecategory', label('label.all','All'));
     document.forms['searchCondition'].forwardsearchCategoryNames.value = "";
     changeDivContent('changecategory', 'All');
  }

  function setProductCategory(searchCategoryNames,categoryListIds) {
    //changeDivContent('changecategory', label('label.all','All'));
    document.forms['searchCondition'].forwardsearchCategoryNames.value = searchCategoryNames;
    document.forms['searchCondition'].forwardsearchCategoryListIds.value = categoryListIds;
   	changeDivContent('changecategory', searchCategoryNames);
   }
</script>
<!--  <body onLoad="javascript:document.searchCondition.searchgroupKeywords.focus();"> -->
<%-- TODO: Hide the following table when the Search is the default --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
    <td colspan="1" style="text-align:left;" nowrap>
			<portlet:renderURL portletMode="view" var="url">
				<portlet:param name="viewType" value="summary"/>
				<portlet:param name="page" value="<%= String.valueOf((String) request.getAttribute("page")) %>"/>
			</portlet:renderURL>
		  [<a href="<%= pageContext.getAttribute("url") %>">Back to Products Summary</a>]
		</td>
</table>
<form name="searchCondition" action="<portlet:actionURL />" method="post">
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th colspan="2">
				<strong><dhv:label name="product.searchCondition">Product Search</dhv:label></strong>
			</th>
		</tr>
		<tr>
			<td class="formLabel" width="20%">
				<dhv:label name="products.keywords">Keyword(s)</dhv:label>
			</td>
			<td width="20%">
				<input type="text" size="35" name="forwardsearchcodeGroupKeywords" value="<%= searchcodeGroupKeywords %>">
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<dhv:label name="products.productSKU">Product SKU</dhv:label>
			</td>
			<td>
				<input type="text" size="15" name="forwardsearchSku" value="<%= searchSku %>">
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap>
				<dhv:label name="products.productCategory">Product Category</dhv:label>
			</td>
			<td>
				<table border="0" cellspacing="0" cellpadding="0" class="empty">
					<tr>
						<td valign="top" nowrap>
							<div id="changecategory">
							<%if ("".equals(searchCategoryNames) || searchCategoryNames == null )
									{%><dhv:label name="quotes.all">All</dhv:label>
							<%} else {%>
								<%=toHtmlValue(searchCategoryNames)%><%}%>
							</div>
						</td>
						<td valign="top" width="100%" nowrap>
							<input type="hidden" name="forwardsearchCategoryListIds" id="forwardsearchCategoryListIds" value="<%= searchCategoryListIds %>">
							[<a href="javascript:popIceProductMultiCategoriesList('Portal.do?command=ListProductMultiCategories&parentId=<%=String.valueOf(productCategory.getId())%>&categoryId=<%=String.valueOf(productCategory.getId())%>',document.forms['searchCondition'].forwardsearchCategoryListIds.value);"><dhv:label name="accounts.accounts_add.select">Multi-Select</dhv:label></a>]
							[<a href="javascript:clearProductCategory();"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<dhv:label name="product.priceRange">Price Range</dhv:label>
			</td>
			<td>
				<input type="text" size="10" name="forwardsearchcodePriceRangeMin" value="<%= searchcodePriceRangeMin %>">
				&nbsp;to&nbsp;
				<input type="text" size="10" name="forwardsearchcodePriceRangeMax" value="<%= searchcodePriceRangeMax %>">
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<dhv:label name="product.dateAfter">Date Added to Catalog</dhv:label>
			</td>
			<td>
				<select size="1" name="forwardsearchcodeDateAfter">
						<option value="-1">&nbsp;</option>
						<option value="168_HOUR">Last Week</option>
						<option value="1_MONTH">Last Month</option>
						<option value="3_MONTH">Last 3 Months</option>
						<option value="6_MONTH">Last 6 Months</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<dhv:label name="pagedListInfo.itemsPerPage">Items per page</dhv:label>
			</td>
			<td>
			
				<select size="1" name="forwarditems">
							<% String[] items = {"6","10","20","30","50","100"};
							for (int i=0; i<items.length; i++){%>
							<option value="<%= items[i] %>"
							<%if(searchItems!=null && items[i].equals(searchItems)){ %> selected
							<%}%> >
							<%= items[i] %></option>
							<% }%>
							<%if(searchItems!=null && "-1".equals(searchItems)){ %> 
							<option value="-1" selected ><dhv:label name="quotes.all">All</dhv:label></option>
							<%} else {%> 
     						<option value="-1"><dhv:label name="quotes.all">All</dhv:label></option>
     						<% }%>
				</select>
			</td>
		</tr>
	</table>
	&nbsp;
	<br>
  <input type="hidden" name="actionType" value="search" />
  <input type="hidden" name="viewType" value="searchResult" />
	<input type="hidden" name="forwardsearchCategoryNames" id="forwardsearchCategoryNames" value="<%=toHtmlValue(searchCategoryNames)%>">
	<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
	<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
</form>
<script language="javascript">
		setSearchcodeDateAfter('<%=searchcodeDateAfter%>');
</script>
