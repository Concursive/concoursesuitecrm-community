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
<jsp:useBean id="searchName" class="java.lang.String" scope="request" />
<jsp:useBean id="searchAbbreviation" class="java.lang.String" scope="request" />
<jsp:useBean id="searchSku" class="java.lang.String" scope="request" />
<jsp:useBean id="searchcodePriceRangeMin" class="java.lang.String" scope="request" />
<jsp:useBean id="searchcodePriceRangeMax" class="java.lang.String" scope="request" />
<jsp:useBean id="searchtimestampStartDate" class="java.lang.String" scope="request" />
<jsp:useBean id="searchtimestampEndDate" class="java.lang.String" scope="request" />
<jsp:useBean id="searchcodeCategoryId" class="java.lang.String" scope="request" />
<jsp:useBean id="searchCategoryNames" class="java.lang.String" scope="request" />
<jsp:useBean id="searchCategoryListIds" class="java.lang.String" scope="request" />
<jsp:useBean id="timeZone" class="java.lang.String" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<%@ include file="../../initPopupMenu.jsp" %>
<portlet:defineObjects/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="Javascript">
  function clearForm() {
    document.forms['searchCondition'].searchName.value = "";
    document.forms['searchCondition'].searchAbbreviation.value="";
    document.forms['searchCondition'].searchSku.value="";
    document.forms['searchCondition'].searchcodePriceRangeMin.value="";
    document.forms['searchCondition'].searchcodePriceRangeMax.value="";    
    document.forms['searchCondition'].searchtimestampStartDate.value="";
    document.forms['searchCondition'].searchtimestampEndDate.value="";        
    document.forms['searchCondition'].searchCategoryListIds.value = "";
    document.forms['searchCondition'].searchCategoryNames.value = "";
    changeDivContent('changecategory', 'All');
    
    document.forms['searchCondition'].searchName.focus();
    //changeDivContent('changecategory', label('label.all','All'));
  }
  
  function clearProductCategory() {
    document.forms['searchCondition'].searchCategoryListIds.value = "";    
    //changeDivContent('changecategory', label('label.all','All'));
     document.forms['searchCondition'].searchCategoryNames.value = "";
     changeDivContent('changecategory', 'All');
  }
  
  function setProductCategory(searchCategoryNames,categoryListIds) {
    //changeDivContent('changecategory', label('label.all','All'));
    document.forms['searchCondition'].searchCategoryNames.value = searchCategoryNames;
    document.forms['searchCondition'].searchCategoryListIds.value = categoryListIds;    
   	changeDivContent('changecategory', searchCategoryNames);
   }
  
</script>
<!--  <body onLoad="javascript:document.searchCondition.searchName.focus();"> -->
<table cellpadding="4" cellspacing="0" border="0" width="100%">
    <td colspan="1" style="text-align:left;" nowrap>
    
			<portlet:renderURL portletMode="view" var="url">
				<portlet:param name="viewType" value="summary"/>
				<portlet:param name="page" value="<%= String.valueOf((String) request.getAttribute("page")) %>"/>
			</portlet:renderURL>
		&nbsp;[<a href="<%= pageContext.getAttribute("url") %>">Back to Products Summary</a>]
		</td>
</tabel>	
<form name="searchCondition" action="<portlet:actionURL />" method="post">
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th colspan="2">
				<strong><dhv:label name="product.searchCondition">Search Product</dhv:label></strong>
			</th>
		</tr>
		<tr>
			<td class="formLabel" width="20%">
				<dhv:label name="products.productName">Product Name</dhv:label>
			</td>
			<td width="20%">
				<input type="text" size="35" name="searchName" value="<%= searchName %>">
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
			</td>
			<td>
				<input type="text" size="35" name="searchAbbreviation" value="<%= searchAbbreviation %>">
			</td>
		</tr>
		<tr>
			<td class="formLabel">
				<dhv:label name="quotes.sku">SKU</dhv:label>
			</td>
			<td>
				<input type="text" size="15" name="searchSku" value="<%= searchSku %>">
			</td>
		</tr>

		<tr>
			<td class="formLabel">
				<dhv:label name="product.priceRange">Price Range</dhv:label>
			</td>
			<td>
				<input type="text" size="10" name="searchcodePriceRangeMin" value="<%= searchcodePriceRangeMin %>">
				&nbsp;
				<input type="text" size="10" name="searchcodePriceRangeMax" value="<%= searchcodePriceRangeMax %>">
			</td>
		</tr>

		<tr>
			<td class="formLabel">
				<dhv:label name="product.startDate">Product Start Date </dhv:label>
			</td>
			<td>
       <zeroio:dateSelect form="searchCondition" field="searchtimestampStartDate" timestamp="<%= searchtimestampStartDate %>"  timeZone="<%= timeZone %>" showTimeZone="false" />
            <%= showError(request, "startDateError") %>			
            &nbsp;to&nbsp;
       <zeroio:dateSelect form="searchCondition" field="searchtimestampEndDate" timestamp="<%= searchtimestampEndDate %>"  timeZone="<%= timeZone %>" showTimeZone="false" />
            <%= showError(request, "startDateError") %>			
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
							<input type="hidden" name="searchCategoryListIds" id="searchCategoryListIds" value="<%= searchCategoryListIds %>">
							&nbsp;[<a href="javascript:popIceProductMultiCategoriesList('Portal.do?command=ListProductMultiCategories&parentId=<%=String.valueOf(productCategory.getId())%>&categoryId=<%=String.valueOf(productCategory.getId())%>',document.forms['searchCondition'].searchCategoryListIds.value);"><dhv:label name="accounts.accounts_add.select">Multi-Select</dhv:label></a>]
							&nbsp;[<a href="javascript:clearProductCategory();"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
						</td>
					</tr>
				</table>
			</td>
		</tr> 

	</table>
	&nbsp;
	<br>
	<input type="hidden" name="viewType" value="searchResult" />
	<input type="hidden" name="searchCategoryNames" id="searchCategoryNames" value="<%=toHtmlValue(searchCategoryNames)%>">
	<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
	<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
</form>

