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
  - Version: $Id: range_select_start.jsp dharmas$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.aspcfs.modules.base.CustomFieldData"%>

<jsp:useBean id="dataList" class="org.aspcfs.modules.base.CustomFieldDataList" scope="request"/>
<jsp:useBean id="rangeSelectInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>

<html>
<head><title>Folder Range Start Select Page</title></head>

<script language="JavaScript">

    function setParentLabel(textToDisplay,textToStore) {
        var hiddenFieldId = document.getElementById("hiddenFieldId").value;
        window.opener.setParentLabelValue(hiddenFieldId, textToDisplay, textToStore);
    }

    function setSelectedRecordId(id) {
        document.dataStartDisplay.rangeStartValue.value = id;
    }

    function popFolderGraphRecordRangeSelect(folderId, majorAxisField) {
        startValue = document.dataStartDisplay.rangeStartValue.value;
        if(parseInt(startValue) > 0) {
            title  = 'RecordRangeEndSelect';
            width  =  '450';
            height =  '400';
            resize =  'yes';
            bars   =  'yes';
            var posx = (screen.width - width)/2;
            var posy = (screen.height - height)/2;
            var windowParams = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;

            var newwin=window.open('FolderAndFieldSelector.do?command=RangeSelect&listType=single&flushtemplist=true&folderId='+folderId+'&majorAxisField='+majorAxisField+'&isNextPopup=true&rangeStartValue='+startValue, title, windowParams);

            newwin.focus();
            if (newwin != null) {
                if (newwin.opener == null)
                    newwin.opener = self;
            }
            return true;
        }
        else {
            alert('Invalid Start Range Value! Enter value > 0');
            return false;
        }
    }

</script>
<link type="text/css" rel="stylesheet" >
<body onLoad="document.dataStartDisplay.rangeStartValue.focus();">

<form name="dataStartDisplay" action="">
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<dhv:label name="folder.range.start">Range Start </dhv:label>&nbsp;
    <input type="text" align="center" name="rangeStartValue" value="12" size="8" maxlength="10">
    &nbsp;<dhv:label name="folder.range.enter">Enter 0 for Current</dhv:label><br>

    <dhv:pagedListStatus object="rangeSelectInfo" />
    <br>

    <table width="100%" cellspacing="0" cellpadding="4" border="1" class="details">
        <dhv:evaluate if='<%= dataList!=null && dataList.size() > 0 %>'>

            <tr>
                <th align="center" width="8">
                    &nbsp;
                </th>
                <th>
                    <strong><dhv:label name="calendar.dateEntered">Date Entered</dhv:label></strong>
                </th>
                <th>
                    <strong><dhv:label name="folder.range.majorAxisField">Major Axis Field</dhv:label></strong>
                </th>
            </tr>

            <%
                Iterator dataIter = dataList.iterator();
                int count = 1;
                if(dataIter.hasNext()){
                    int rowid = 0;
                    while(dataIter.hasNext()){
                        rowid = (rowid != 1?1:2);
                        CustomFieldData record = (CustomFieldData)dataIter.next();
            %>
            <tr class="row<%= rowid %>">
                <td><a href="javascript:setSelectedRecordId(<%= record.getRecordId() %>);">Select</a></td>
                <td> <%= record.getEntered() %> </td>
                <td> <%= record.getEnteredValue() %> </td>
            </tr>
            <% count++;
            } } %>

        </dhv:evaluate>
    </table>
    <br>
    <input type="hidden" name="folderId" id="folderId" value="<%= toHtmlValue(request.getParameter("folderId")) %>">
    <input type="hidden" name="majorAxisField" id="majorAxisField" value="<%= toHtmlValue(request.getParameter("majorAxisField")) %>">
    <input type="hidden" name="displayFieldId" id="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
    <input type="hidden" name="hiddenFieldId" id="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
    <div align="right">
        <br><br>
        <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
        <input type="button" name="chooseTo" value="<dhv:label name="folder.range.chooseto">Choose To</dhv:label>" onClick="popFolderGraphRecordRangeSelect(document.forms[0].folderId.value, document.forms[0].majorAxisField.value);" >
    </div>

</form>
</body>
</html>