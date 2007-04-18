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
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.utils.StringUtils,org.aspcfs.utils.web.ClientType" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.website.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="rowColumn" class="org.aspcfs.modules.website.base.RowColumn" scope="request"/>
<jsp:useBean id="propertyMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="icelet" class="org.aspcfs.modules.website.base.Icelet" scope="request"/>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="leadSourceSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="rolelistSelect" class="org.aspcfs.modules.admin.base.RoleList" scope="request"/>
<jsp:useBean id="folderList" class="org.aspcfs.modules.base.CustomFieldCategoryList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%
  if (clientType.getType() == -1) {
    clientType.setParameters(request);
  }
  String noOfRecords = "";
%>
<dhv:evaluate if="<%= !clientType.showApplet() %>">
  <jsp:include page="../tinymce_include.jsp" flush="true"/>
</dhv:evaluate>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/folderListCheck.js"></script>
<script language="JavaScript">
    function setParentLabelValue(hiddenFieldId, textToDisplay,textToStore) {
        divToChange = document.getElementById('toChange');
        divToChange.innerHTML = textToDisplay;
       // document.icelet.textToStore.value = textToStore;
        var rangeHiddenField =  document.getElementById(hiddenFieldId);
        rangeHiddenField.value= textToStore;
      //  document.icelet.textToStore.value = textToStore;
    }

  function changeDivContent(divName, divContents) {
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}
</script>
<form name="icelet" action="RowColumns.do?command=SaveIcelet&rowColumnId=<%= rowColumn.getId() %>&popup=true&auto-populate=true" onSubmit="return checkForm(this);" method="post">
<dhv:formMessage showSpace="false"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><strong><dhv:label name="">Configure the portlet properties for <%= toHtml(icelet.getName()) %></dhv:label></strong></th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel1">
      <dhv:label name="">Column Width</dhv:label>
    </td>
		<td><input type="text" name="width" value="<%= (rowColumn.getWidth() > -1? rowColumn.getWidth(): 50) %>"/></td>
  </tr>
<%
  String previousLabel = "";
  String selectedFolderName = "";
  String selectedMajorAxisField = "";
  boolean htmlEditorIncluded = false;
  Iterator iter = (Iterator) propertyMap.keySet().iterator();
  while (iter.hasNext()) {
    Integer key = (Integer) iter.next();
    IceletProperty property = (IceletProperty) propertyMap.get(key);
%>
	<tr class="containerBody">
    <td nowrap class="formLabel1" valign="top">
      <dhv:evaluate if="<%= !previousLabel.equals(property.getLabel()) %>">
        <%= toHtml(property.getLabel()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= previousLabel.equals(property.getLabel()) %>">&nbsp;</dhv:evaluate>
    </td>
    <td>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.HTML_EDITOR) %>">
        <dhv:evaluate if="<%= !htmlEditorIncluded %>">
          <% htmlEditorIncluded = true; %>
          <script language="JavaScript">
            var imageLibraryURL = "/../WebsiteMedia.do?command=View&row=<%= rowColumn.getPageRowId() %>&popup=true";
            var documentLibraryURL = "/../WebsiteDocuments.do?command=View&row=<%= rowColumn.getPageRowId() %>&popup=true";
          </script>
        </dhv:evaluate>
        <%= toHtml(property.getDescription()) %><br />
        <%@ include file="website_html_editor_include.jsp" %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.TEXT_AREA) %>">
        <%= toHtml(property.getDescription()) %><br />
        <textarea rows="3" cols="50" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>"><%= toString(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %></textarea>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.PRODUCT_CATEGORY) %>">
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr><td colspan="2"><%= toHtml(property.getDescription()) %></td></tr>
          <tr>
            <td>
            <%if (1==1) { %>
              <div id="<%= "changeproduct_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>  
                <dhv:evaluate if='<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>'>
                  <%= toHtml(iceletProperty.getValueString()) %>
                </dhv:evaluate>
                <dhv:evaluate if='<%= iceletProperty == null || iceletProperty.getValueString() == null || "".equals(iceletProperty.getValueString()) %>'>
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
<input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= (iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString())?iceletProperty.getValue():"-1") %>"/> &nbsp;
[<a href="javascript:popProductCategoriesListSingle('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>','<%= "changeproduct_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', 'setParentList=true&listType=single');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
[<a href="javascript:document.getElementById('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>').value='-1';javascript:changeDivContent('<%= "changeproduct_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
            <%} %>
              &nbsp;
            </td>
          </tr>
        </table>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.PORTFOLIO_CATEGORY) %>">
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr><td colspan="2"><%= toHtml(property.getDescription()) %></td></tr>
          <tr>
            <td>
            <%if (1==1) { %>
              <div id="<%= "changeportfolio_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>
                <dhv:evaluate if='<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>'>
                  <%= toHtml(iceletProperty.getValueString()) %>
                </dhv:evaluate>
                <dhv:evaluate if='<%= iceletProperty == null || iceletProperty.getValueString() == null || "".equals(iceletProperty.getValueString()) %>'>
                  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
<input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= (iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString())?iceletProperty.getValue():"-1") %>"/> &nbsp;
[<a href="javascript:popPortfolioCategoryListSingle('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>','<%= "changeportfolio_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', '');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
[<a href="javascript:document.getElementById('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>').value='-1';javascript:changeDivContent('<%= "changeportfolio_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
            <%}%>
              &nbsp;
            </td>
          </tr>
        </table>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.LEAD_SOURCE) %>">
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr><td colspan="2"><%= toHtml(property.getDescription()) %></td></tr>
          <tr>
            <td>
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>  
            <%= leadSourceSelect.getHtmlSelect("property_"+rowColumn.getId()+"_"+property.getTypeConstant(),(iceletProperty != null?Integer.parseInt(iceletProperty.getValue()):-1)) %></td>
          </tr>
        </table>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.PORTAL_ROLELIST) %>">
        <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr><td colspan="2"><%= toHtml(property.getDescription()) %></td></tr>
          <tr>
            <td>
            <%IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>  
            <%= rolelistSelect.getHtmlSelect("property_"+rowColumn.getId()+"_"+property.getTypeConstant(),(iceletProperty != null?Integer.parseInt(iceletProperty.getValue()):-1)) %></td>
          </tr>
        </table>
      </dhv:evaluate>

        <dhv:evaluate if="<%= property.getType().equals(IceletProperty.FOLDER_DROPLIST) %>">
                <table cellspacing="0" cellpadding="0" border="0" class="empty">

             <%if (1==1) { %>
                  <tr>
                    <td>

            <%IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>
           <%= folderList.getHtmlSelect("property_"+rowColumn.getId()+"_"+property.getTypeConstant(),(iceletProperty != null?Integer.parseInt(iceletProperty.getValue()):-1),true) %></td>
           <% selectedFolderName="property_"+rowColumn.getId()+"_"+property.getTypeConstant(); %>

             <%--</td>--%>
              <%}%>
                  </tr>
                </table>
              </dhv:evaluate>
        <dhv:evaluate if="<%= property.getType().equals(IceletProperty.CUSTOM_FIELDLIST) %>">
          <table cellspacing="0" cellpadding="0" border="0" class="empty">
          <tr>
          <td>
          <textarea rows="6" cols="15" id ="displayArea" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" readonly="readonly"><%= toString(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %></textarea>
          </td>
          <td valign="top">
           [<a href='javascript:popFieldsList(document.forms[0].<%=selectedFolderName%>.options[document.forms[0].<%=selectedFolderName%>.selectedIndex].value,document.forms[0].<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>.name);'><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
           [<a href="javascript:document.getElementById('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>').value='';javascript:changeDivContent('<%= "changeproduct_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
         </td>
          </tr>
          </table>
        </dhv:evaluate>



             <dhv:evaluate if="<%= property.getType().equals(IceletProperty.MAJORAXIS_SELECT) %>">
                <table cellspacing="0" cellpadding="0" border="0" class="empty">
                  <tr>
                    <td>
            <%if (1==1) { %>
                      <div id="<%= "changemajoraxis_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>
                        <dhv:evaluate if='<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>'>
                  <%= toHtml(iceletProperty.getValueString()) %>
                        </dhv:evaluate>
                         <input type="radio" name="majoraxisselect" id="majoraxisselect" value="X" checked onClick="setMajorAxis('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>')"><dhv:label name="">X-Axis</dhv:label>
                         <input type="radio" name="majoraxisselect" id="majoraxisselect" value="Y" onClick="setMajorAxis('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>')"><dhv:label name="">Y-Axis</dhv:label>
                          <input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="X"/> &nbsp;
                      </div>
                    </td>
                   <td>
            <%}%>
                      &nbsp;
                    </td>
                  </tr>
                </table>
              </dhv:evaluate>

              <dhv:evaluate if="<%= property.getType().equals(IceletProperty.MAJORAXIS_FIELD) %>">
                <table cellspacing="0" cellpadding="0" border="0" class="empty">
                  <tr>
                    <td>
            <%if (1==1) { %>
                      <div id="<%= "changemajoraxisfield_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>
                        <dhv:evaluate if='<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>'>
                  <%= toHtml(iceletProperty.getValueString()) %>
                        </dhv:evaluate>
                        <dhv:evaluate if='<%= iceletProperty == null || iceletProperty.getValueString() == null || "".equals(iceletProperty.getValueString()) %>'>
                          <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                        </dhv:evaluate>
                      </div>
                    </td>
                    <td>
        <input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= (iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString())?iceletProperty.getValue():"-1") %>"/> &nbsp;
        <input type="hidden" name="hiddenMajorAxis" id="hiddenMajorAxis" value="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>"/> &nbsp;
        <input type="hidden" name="hiddenMajorAxisDiv" id="hiddenMajorAxisDiv" value="<%= "changemajoraxisfield_"+rowColumn.getId()+"_"+property.getTypeConstant() %>"/> &nbsp;
        <%selectedMajorAxisField = "property_"+rowColumn.getId()+"_"+property.getTypeConstant();%>

        [<a href="javascript:popFolderGraphMajorAxisSelect(document.forms[0].<%=selectedFolderName%>.options[document.forms[0].<%=selectedFolderName%>.selectedIndex].value, '<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>','<%= "changemajoraxisfield_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', '');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
        [<a href="javascript:document.getElementById('<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>').value='-1';javascript:changeDivContent('<%= "changemajoraxisfield_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', label('none.selected','None Selected'));"><dhv:label name="button.clear">Clear</dhv:label></a>]
            <%}%>
                      &nbsp;
                    </td>
                  </tr>
                </table>
              </dhv:evaluate>

             <dhv:evaluate if="<%= property.getType().equals(IceletProperty.MINORAXIS_PARAMETER) %>">
                <table cellspacing="0" cellpadding="0" border="0" class="empty">
                 <tr>
                    <td>
            <%if (1==1) { %>
                      <div id="<%= "changeminoraxis_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>
                        <dhv:evaluate if='<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>'>
                  <%= toHtml(iceletProperty.getValueString()) %>
                        </dhv:evaluate>
                         <select name="minoraxisparam" id="minoraxisparam" size="10"  style="width: 250px">
                         </select>
                      </div>
                    </td>
                    <td>
        <input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= (iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString())?iceletProperty.getValue():"-1") %>"/> &nbsp;
        <input type="hidden" name="hiddenMinorAxis" id="hiddenMinorAxis" value="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>"/> &nbsp;
        [<a href="javascript:clearValues();javascript:popFolderGraphMinorAxisSelect(document.forms[0].<%=selectedFolderName%>.options[document.forms[0].<%=selectedFolderName%>.selectedIndex].value,'<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>','<%= "changeminoraxis_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', '');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;
        [<a href="javascript:clearValues();"><dhv:label name="button.clear">Clear</dhv:label></a>]
            <%}%>
                      &nbsp;
                    </td>
                  </tr>
                </table>
              </dhv:evaluate>

             <dhv:evaluate if="<%= property.getType().equals(IceletProperty.RECORDRANGE_SELECT) %>">
                <table cellspacing="0" cellpadding="0" border="0" class="empty">
                  <tr>
                    <td>
            <%if (1==1) { %>
                      <div id="<%= "changerecordrange_"+rowColumn.getId()+"_"+property.getTypeConstant() %>">
            <%  IceletProperty iceletProperty = (rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null ? (IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) : null); %>
                        <dhv:evaluate if='<%= iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString()) %>'>
                  <%= toHtml(iceletProperty.getValueString()) %>
                        </dhv:evaluate>
                        <dhv:evaluate if='<%= iceletProperty == null || iceletProperty.getValueString() == null || "".equals(iceletProperty.getValueString()) %>'>
                          <div id="toChange">12 Records Ago to Current</div>
                        </dhv:evaluate>
                      </div>
                    </td>
                    <td>
        <input type="hidden" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" id="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= (iceletProperty != null && iceletProperty.getValueString() != null && !"".equals(iceletProperty.getValueString())?iceletProperty.getValue():"Start:Relative,12;End:Relative,0") %>"/> &nbsp;
        [<a href="javascript:popFolderGraphRecordRangeSelect(document.forms[0].<%=selectedFolderName%>.options[document.forms[0].<%=selectedFolderName%>.selectedIndex].value, document.forms[0].<%=selectedMajorAxisField%>.value, '<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>','<%= "changerecordrange_"+rowColumn.getId()+"_"+property.getTypeConstant() %>', '');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>] &nbsp;

            <%}%>
                      &nbsp;
                    </td>
                  </tr>
                </table>
              </dhv:evaluate>


      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.TEXT) %>">
        <br />
        <input type="text" size="20" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= toHtmlValue(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %>"/>
        <% noOfRecords = "property_"+rowColumn.getId()+"_"+property.getTypeConstant(); %>
      </dhv:evaluate>

      <dhv:evaluate if="<%= property.getType().equals(IceletProperty.INTEGER) %>">
        <%= toHtml(property.getDescription()) %><br />
        <input type="text" name="<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>" value="<%= toHtmlValue(rowColumn.getIceletPropertyMap() != null && rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null? ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() : property.getDefaultValue()) %>"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= property.getType().equals(property.CHECKBOX) %>">
            <dhv:checkbox name='<%= "property_"+rowColumn.getId()+"_"+property.getTypeConstant() %>' value="true" checked='<%= (rowColumn.getIceletPropertyMap() != null ? (rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant())) != null && ((IceletProperty) rowColumn.getIceletPropertyMap().get(new Integer(property.getTypeConstant()))).getValue() != null): "true".equals(property.getDefaultValue())) %>' />
            <dhv:evaluate if='<%= icelet.getName() != null && !("Folder Graph".equals(icelet.getName().trim()) ||"Folder Portlet".equals(icelet.getName().trim())) %>'>
              <%= toHtml(property.getDescription()) %>
            </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if='<%= property.getAdditionalText() != null && !"".equals(property.getAdditionalText().trim()) %>'>
        <%= toHtml(property.getAdditionalText()) %>
      </dhv:evaluate>      
    <%  previousLabel = property.getLabel();
            } %>
    </td>
  </tr>
</table>
<br />
<input type="hidden" name="id" value="<%= rowColumn.getId() %>"/>
<input type="hidden" name="position" value="<%= rowColumn.getPosition() %>"/>
<input type="hidden" name="pageRowId" value="<%= rowColumn.getPageRowId() %>"/>
<input type="hidden" name="enabled" value="<%= rowColumn.getEnabled() %>"/>
<input type="hidden" name="modified" value="<%= rowColumn.getModified() %>"/>
<input type="hidden" name="nextRowColumnId" value="<%= rowColumn.getNextRowColumnId() %>"/>
<input type="hidden" name="previousRowColumnId" value="<%= rowColumn.getPreviousRowColumnId() %>"/>
<input type="hidden" name="iceletId" value="<%= rowColumn.getIceletId() %>"/>
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>" name="Save"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();">
</form>
<script language="JavaScript">
     function checkForm(form) {
          try { tinyMCE.triggerSave(false); } catch(e) { }
                            var flag = true;
                            message = "";
             if (flag == false) {
                alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
                  return false;
             } else {
                    var messageText = "";
                    var formTest = true;
                    if(document.getElementById("hiddenMajorAxis")!=null && document.getElementById("hiddenMinorAxis")!=null ){
                        var majorAxis = document.getElementById("hiddenMajorAxis").value;
                        var minorAxis = document.getElementById("hiddenMinorAxis").value;
                        if(document.getElementById(majorAxis).value == -1){
                            messageText += label("majoraxis.field.required","- Major Axis Field is required\r\n");
                            formTest = false;
                        }
                        if(document.getElementById(minorAxis).value == -1){
                            messageText += label("minoraxis.parameter.required","- Minor Axis Parameter is required\r\n");
                            formTest = false;
                        }

                    }
                 if(document.icelet.<%=noOfRecords%>.value ==0){
                            messageText += label("noofrecords.parameter.notzero","- Number of Records to Display in List should not be zero\r\n");
                            formTest = false;
                   }
                 if (formTest == false) {
                          messageText = label("check.form","The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
                          alert(messageText);
                          return false;
                        } else {
                           return true;
                        }
            }
      }
</script>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
