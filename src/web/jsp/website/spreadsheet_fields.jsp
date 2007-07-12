<%--
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: spreadsheet_fields.jsp 4.1 2007-06-14 12:20:00 +0530 (Thu, 14 Jun 2007) nagarajay $
  - Description:
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.aspcfs.modules.base.CustomField" %>
<%@ page import="org.aspcfs.modules.base.CustomFieldGroup" %>
<%@ page import="org.aspcfs.modules.base.CustomFieldCategory" %>
<%@ page import="org.aspcfs.utils.StringUtils" %>
<%@ page import="org.aspcfs.utils.FolderUtils" %>
<jsp:useBean id="rows" class="java.lang.String" scope="request"/>
<jsp:useBean id="recordRange" class="java.lang.String" scope="request"/>
<jsp:useBean id="cols" class="java.lang.String" scope="request"/>
<jsp:useBean id="previousRows" class="java.lang.String" scope="request"/>
<jsp:useBean id="previousColumns" class="java.lang.String" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String" scope="request"/>
<jsp:useBean id="htmlContent" class="java.lang.String" scope="request"/>
<jsp:useBean id="finalPropertyStr" class="java.lang.String" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="records" class="org.aspcfs.modules.base.CustomFieldRecordList" scope="request"/>
<jsp:useBean id="recordCategories" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="configurePropertyName" class="java.lang.String" scope="request"/>
<jsp:useBean id="previewPropertyName" class="java.lang.String" scope="request"/>

<html>
<script language="JavaScript" type="text/javascript">
  var cellNames = new Array("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
  var specialChars = new Array("<", ".", ">", "?", ";", "'", "\"", "[", "{", "]", "}", "|", "&", "%", "$", "#", "@", "!", "`", "~");

</script>
<script language="JavaScript" type="text/javascript">
function loadRowsCols() {
  window.opener.document.forms[0].prevRows.value = '<%=rows%>';
  window.opener.document.forms[0].prevCols.value = '<%=cols%>';
}
function apply() {
  var cellValue = "";
  var queryString = "";
  var subString = "";
  var finalString = "";
  var elem;
  var colsCount = "<%=cols%>";
  var m = 0;
  var q = -1;
  var charsCellNames = "";

  for (var row = 0; row <<%=rows%>; row++)
  {
    m = 0;
    q = -1;
    rowString = (parseInt(row) + 1) + "<%=FolderUtils.ROW_NUMBER_SEPERATOR%>" + "{";
    var formulaName = "";
    for (var col = 0; col <<%=cols%>; col++) {
      if (cellNames.length == m && colsCount > cellNames.length) {
        m = 0;
        q = parseInt(q) + 1;
      }
      if (q == "-1") {
        elem = document.getElementById(cellNames[m] + (parseInt(row) + 1));
      } else {
        elem = document.getElementById(cellNames[q] + cellNames[m] + (parseInt(row) + 1));
      }
      m = parseInt(m) + 1;
      cellValue = elem.value;
      if (cellValue == "") {
        subString = subString + "EMPTY";
      } else {
        // Storing cellnames in the variable if cell contains special chars.
        if (cellValue.length > 1 && cellValue.substring(0, 1) == "=") {
          for (var char = 0; char < specialChars.length; char++) {
            for (l = 0; l < cellValue.length; l++) {
              if (cellValue.charAt(l) == specialChars[char]) {
                charsCellNames = charsCellNames + elem.name + ", ";
                char = specialChars.length;
                break;
              }
            }
          }
        }
        formulaName = cellValue.substring(1, cellValue.indexOf("("));
        if (formulaName.toUpperCase() == 'AVG')
          cellValue = cellValue.replace(formulaName, "AVERAGE");
      }
      subString = subString + cellValue + "<%=FolderUtils.COLUMN_SEPERATOR%>";
    }
    subString = subString.substring(0, subString.length - 1);
    finalString = finalString + rowString + subString + "}" + "<%=FolderUtils.ROW_SEPERATOR%>"
    subString = "";
  }

  // Fires an alert and returns if work area formula cells contains any special chars.
  if (charsCellNames != "") {
    charsCellNames = charsCellNames.substring(0, charsCellNames.length - 2);
    alert("Cells  " + charsCellNames + " contains special characters. Please remove them and try again.");
    return;
  }

  finalString = finalString.substring(0, finalString.length - 2);
  var configurePropertyName = "<%=configurePropertyName%>";
  var previewPropertyName = "<%=previewPropertyName%>";
  window.location.href = 'RowColumns.do?command=SpreadSheetFieldsLists&rows='+<%=rows%>+
  '&cols='+<%=cols%>+
  '&finalString=' + escape(finalString) + '&folderId='+<%=folderId%>+
  '&recordRange='+<%=recordRange%>+
  '&configurePropertyName=' + configurePropertyName + '&previewPropertyName=' + previewPropertyName + '';
}

function process() {
  var configureContent = "<%=finalPropertyStr%>";
  var previewContent = "<%=htmlContent%>";
  window.opener.document.forms[0].<%=configurePropertyName%>.value = configureContent;
  window.opener.document.forms[0].<%=previewPropertyName%>.value = previewContent;
  formulatableGen();
  previewGen();
  window.close();
}

function formulatableGen() {
  var tableID = window.opener.document.getElementById('ConfigureTable');
<%if(finalPropertyStr!=null && !finalPropertyStr.equals("") && !finalPropertyStr.equals("null")){%>
  var propertyStr = "<%=finalPropertyStr%>";
  var commaStr = "";
  var splitArray = propertyStr.split("<%=FolderUtils.ROW_NUMBER_SEPERATOR%>");
  for (var k = 0; k < splitArray.length; k++) {
    tokenStr = splitArray[k];
    var token = tokenStr.split("<%=FolderUtils.COLUMN_SEPERATOR%>")
    for (var l = 0; l < token.length; l++) {
      value = token[l];
      if (value.indexOf("}") != -1 && value.indexOf("{") != -1) {
        commaStr = commaStr + value.substring(1, value.indexOf("}")) + "<%=FolderUtils.COLUMN_SEPERATOR%>";
      } else if (value.indexOf("{") != -1) {
        commaStr = commaStr + value.substring(value.indexOf("{") + 1, value.length) + "<%=FolderUtils.COLUMN_SEPERATOR%>";
      } else if (value.indexOf("}") != -1) {
        commaStr = commaStr + value.substring(0, value.indexOf("}")) + "<%=FolderUtils.COLUMN_SEPERATOR%>";
      } else {
        commaStr = commaStr + value + "<%=FolderUtils.COLUMN_SEPERATOR%>";
      }
    }
  }
  while (tableID.rows.length > 0) {
    tableID.deleteRow(0);
  }

  var splits = commaStr.split("<%=FolderUtils.COLUMN_SEPERATOR%>");
  var count = 0;
  for (var row = 0; row <<%=rows%>; row++) {
    tableRow = tableID.insertRow(row);
  {
    for (var col = 0; col <<%=cols%>; col++) {
      count++;
      cell = tableRow.insertCell(col);
      if (isNaN(splits[count])) {
        cell.setAttribute('align', 'left');
      } else {
        cell.setAttribute('align', 'right');
      }
      if (splits[count] == "EMPTY")
        cell.innerHTML = "&nbsp;";
      else
        cell.innerHTML = splits[count];
    }
  }
  }
<%}%>
}

function previewGen() {
  var previewContent = "<%=htmlContent%>";
  var tableId = window.opener.document.getElementById('previewTable');
<%if(finalPropertyStr!=null && !finalPropertyStr.equals("") && !finalPropertyStr.equals("null")){%>
  while (tableId.rows.length > 0) {
    tableId.deleteRow(0);
  }
<%}%>
  var splits = previewContent.split("0xfff");
  var count = 0;
  for (var row = 0; row <<%=rows%>; row++) {
    tableRow = tableId.insertRow(row);
  {
    for (var col = 0; col <<%=cols%>; col++) {
      cell = tableRow.insertCell(col);
      if (splits[count] == "EMPTY") {
        cell.innerHTML = "&nbsp;";
      } else {
        if (isNaN(splits[count])) {
          cell.innerHTML = splits[count];
        } else
        {
          cell.setAttribute('align', 'right');
          if (splits[count].substring(splits[count].indexOf(".") + 1) == "0") {
            cell.innerHTML = splits[count].substring(0, splits[count].indexOf("."))
          } else {
            cell.innerHTML = splits[count].substring(0, parseInt(splits[count].indexOf(".")) + 3);
          }
        }
      }
      count++;
    }
  }
  }
}
</script>

<body onLoad="javascript:loadRowsCols();">

<table><tr><td><strong>Selected Folder Data</strong></td></tr>
  <tr><td>&nbsp;</td></tr>
</table>

<table class="details" border="0" cellpadding="4" cellspacing="0" width="100%">
<%
  int k = 0;
  int recordsCount = 0;
  CustomField thisField = null;
  CustomFieldGroup thisGroup = null;
  CustomFieldCategory thisCategory = null;
  Iterator groups = Category.iterator();
  Iterator recordList = recordCategories.iterator();
  char[] alphabets = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
          'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
          'U', 'V', 'W', 'X', 'Y', 'Z'};
  int colsCount = 0;
  while (groups.hasNext()) {
    thisGroup = (CustomFieldGroup) groups.next();
    Iterator fields = thisGroup.iterator();
    while (fields.hasNext()) {
      colsCount++;
      thisField = (CustomField) fields.next();
    }
  }
  groups = Category.iterator();
  if (groups.hasNext()) { %>

<tr>
  <th width="2%"></th>
  <% int j = -1;
    int m = 0;
    for (int i = 0; i < colsCount; i++) {
      if (alphabets.length == m && colsCount > alphabets.length) {
        m = 0;
        j++;
      }
      if (j == -1) {
  %>
  <th width="20%"><%=alphabets[m]%></th>
  <%} else { %>
  <th width="20%"><%=alphabets[j]%><%=alphabets[m]%></th>
  <% }
    m++;
  } %>
</tr>

<tr>
  <th width="2%"><%=++recordsCount%></th>
  <%
    groups = Category.iterator();
    while (groups.hasNext()) {
      thisGroup = (CustomFieldGroup) groups.next();
      Iterator fields1 = thisGroup.iterator();
      while (fields1.hasNext()) {
        thisField = (CustomField) fields1.next();

  %>
  <td nowrap><strong><%=thisField.getName()%></strong></td>
  <%
      }
    }
  %>
</tr>
<%
  int row = 0;
  Iterator fields = null;
  if (recordList.hasNext()) {
    while (recordList.hasNext()) {
      k++;
%>
<tr class="containerBody">
  <%
    thisCategory = (CustomFieldCategory) recordList.next();
    groups = thisCategory.iterator();
    while (groups.hasNext()) {
      thisGroup = (CustomFieldGroup) groups.next();
      fields = thisGroup.iterator();
      while (fields.hasNext()) {
        thisField = (CustomField) fields.next();
        if (row == 0) {

  %>
  <th><%=++recordsCount%></th>
  <% }
    if (thisField.getTypeString().equals("Number") || thisField.getTypeString().equals("Decimal Number") || thisField.getTypeString().equals("Percent") || thisField.getTypeString().equals("Currency")) {%>
  <td align="right"><%=thisField.getValueHtml()%></td>
  <% } else { %>
  <td><%=thisField.getValueHtml()%></td>
  <%}%>
  <%
        row++;
      }
    }
  %>
</tr>
<%
    row = 0;
  }
} else {
%>
<tr>
  <td align="middle" wrap colspan="<%=colsCount+1%>">
    <dhv:label name="folder.noRecords">No records in this folder.</dhv:label>
  </td>
</tr>
<%
  }
} else {
%>
<tr>
  <td align="middle">
    <dhv:label name="folder.noDataSelected">No folder selected.</dhv:label>
  </td>
</tr>
<% } %>
</table>
<br>

<table><tr><td><strong>Work Area / Formula</strong></td></tr>
  <tr>
    <td nowrap>
      <dhv:label name="workArea.note">Note: Following special characters <.>?;'"[{]}&%$#@!`~ are not supported and
        should not contain more than 30 parameters in the work area formula.</dhv:label>
    </td>
  </tr>
  <tr><td>&nbsp;</td></tr>
</table>

<table class="details" border="0" cellpadding="4" cellspacing="0" width="100%">
  <%
    int prevRows = 0;
    int prevCols = 0;
    if (previousRows != null && !"".equals(previousRows) &&  Integer.parseInt(previousRows) > 0 && previousColumns != null && !"".equals(previousColumns) && Integer.parseInt(previousColumns) > 0) {
      prevRows = Integer.parseInt(previousRows);
      prevCols = Integer.parseInt(previousColumns);
    } else {
      prevRows = Integer.parseInt(rows);
      prevCols = Integer.parseInt(cols);
    }
  %>
  <tr>
    <dhv:spreadsheet rows='<%=Integer.parseInt(rows)%>' cols='<%=Integer.parseInt(cols)%>' prevRows='<%=prevRows%>'
                     prevCols='<%=prevCols%>'
                     configString='<%=finalPropertyStr%>'>
    </dhv:spreadsheet>
  </tr>

  <tr>
    <td><input type="button" name="Apply" value="<dhv:label name="button.apply">Apply</dhv:label>"
               onclick="javascript:apply();"></td>
  </tr>
</table>

<% if (htmlContent != null && !htmlContent.equals("") && !htmlContent.equals("null")) { %>
<br>
<table border="0" cellpadding="0" cellspacing="0">
  <tr><td><strong>Preview</strong></td></tr>
</table>
<br>

<table class="details" border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <%

     int count = 0;
     String value = null;
        String content[] = htmlContent.split("0xfff");
    for (int row = 0; row <Integer.parseInt(rows); row++) {

    %>
  <tr>
    <%
      for (int col = 0; col < Integer.parseInt(cols); col++) {
        if (prevCols != 0 && prevRows != 0) {
          if (Integer.parseInt(cols) == prevCols || Integer.parseInt(cols) > prevCols || Integer.parseInt(cols) < prevCols)
          {
            if (col >= prevCols || row >= prevRows || count >= content.length) {
              value = "&nbsp;";
            } else {
              value = (!"EMPTY".equals(content[count]) && !"".equals(content[count])) ? content[count] : "&nbsp;";
            }
          }
        } else {
          value = (!"EMPTY".equals(content[count]) && !"".equals(content[count])) ? content[count] : "&nbsp;";
        }
        if (StringUtils.isNumeric(value)) {
    %>
    <td align='right'
        width="<%=100/Integer.parseInt(cols)%>%"><%= StringUtils.parseStringForNumber(value, 2, true)%></td>
    <%
    } else {%>
    <td width='<%=100/Integer.parseInt(cols)%>%'><%=value%></td>
    <%
        }
        count++;
      }%>
  </tr>
  <%
    }
  %>
</table>
<br>
<input type="button" name="done" value="<dhv:label name="button.done">Done</dhv:label>" onClick=javascript:process();>
<%
  }
%>
</body>
</html>