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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="helpTOC" class="org.aspcfs.modules.help.base.HelpTOC" scope="request"/>
<link rel="stylesheet" href="css/template-help.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function updateWindows(pageId,moduleId) {
    pageURL = "Help.do?command=ViewContext&helpId=" + pageId;
    moduleURL = "Help.do?command=ViewModule&moduleId=" + moduleId
    window.parent.frames['context'].location.href=pageURL;
    window.parent.frames['module'].location.href=moduleURL;
  }
</script>
<%!
  public String getIndents(int level){
    String indents = "";
    for (int noOfIndents = 0 ; noOfIndents < level ; noOfIndents++){
         indents = indents + " &nbsp";
    }
    return indents;
  }
%> 
<html>
<body>
<table>
  <tr><td><h3> Table of Contents </h3></td></tr>
   <%
    Iterator i0 = helpTOC.getTopLevelList().iterator();
    while(i0.hasNext()){
        HelpTableOfContentItem thisTOCItem = (HelpTableOfContentItem) i0.next();
        %>
          <tr>
            <td nowrap>
              <%=toHtml(getIndents(thisTOCItem.getContentLevel()))%>
              <%
                Iterator il0 = thisTOCItem.getHTOCLinks().iterator();
                if (il0.hasNext()){
                  HelpTableOfContentItemLink link = (HelpTableOfContentItemLink)il0.next();
               %>
               <a href="javascript:updateWindows(<%=link.getLinkToHelpItem()%>,'<%=link.getLinkModule()%>');">
               <%}%>
               <%=toHtml(thisTOCItem.getDisplayText())%>
             </td>
            </tr>
        <%
        Iterator i1 = helpTOC.getChildList(thisTOCItem.getId()).iterator();
        while(i1.hasNext()){
            HelpTableOfContentItem thisTOCItem1 = (HelpTableOfContentItem) i1.next();
         %>
            <tr>
              <td nowrap>
                <%=toHtml(getIndents(thisTOCItem1.getContentLevel()))%> 
                <%
                  Iterator il1 = thisTOCItem1.getHTOCLinks().iterator();
                  if (il1.hasNext()){
                    HelpTableOfContentItemLink link = (HelpTableOfContentItemLink)il1.next();
                 %>
                 <a href="javascript:updateWindows(<%=link.getLinkToHelpItem() %>,'<%=link.getLinkModule() %>');">
                 <%}%>
                 <%=toHtml(thisTOCItem1.getDisplayText())%></a>
              </td>
            </tr>
            <%
              Iterator i2 = helpTOC.getChildList(thisTOCItem1.getId()).iterator();
              while(i2.hasNext()){
                  HelpTableOfContentItem thisTOCItem2 = (HelpTableOfContentItem) i2.next();
             %>
              <tr>
                <td nowrap>
                <%=toHtml(getIndents(thisTOCItem2.getContentLevel()))%> 
                <%
                  Iterator il2 = thisTOCItem2.getHTOCLinks().iterator();
                  if (il2.hasNext()){
                  HelpTableOfContentItemLink link = (HelpTableOfContentItemLink)il2.next();
                 %>
                 <a href="javascript:updateWindows(<%=link.getLinkToHelpItem() %>,'<%=link.getLinkModule() %>');">
                 <%}%>
                 <%=toHtml(thisTOCItem2.getDisplayText())%></a>
                 </td>
              </tr>
              <%
                Iterator i3 = helpTOC.getChildList(thisTOCItem2.getId()).iterator();
                while(i3.hasNext()){
                    HelpTableOfContentItem thisTOCItem3 = (HelpTableOfContentItem) i3.next();
               %>
               <tr>
                 <td nowrap>
                 <%=toHtml(getIndents(thisTOCItem3.getContentLevel()))%> 
                 <%
                  Iterator il3 = thisTOCItem3.getHTOCLinks().iterator();
                  if (il3.hasNext()){
                    HelpTableOfContentItemLink link = (HelpTableOfContentItemLink)il3.next();
                 %>
                 <a href="javascript:updateWindows(<%=link.getLinkToHelpItem() %>,'<%=link.getLinkModule() %>');">
                 <%}%>
                 <%=toHtml(thisTOCItem3.getDisplayText())%></a>
                 </td>
                </tr>
                <%
                  Iterator i4 = helpTOC.getChildList(thisTOCItem3.getId()).iterator();
                  while(i4.hasNext()){
                      HelpTableOfContentItem thisTOCItem4 = (HelpTableOfContentItem) i4.next();
                %>
                <tr>
                  <td nowrap>
                  <%=toHtml(getIndents(thisTOCItem4.getContentLevel()))%> 
                  <%
                    Iterator il4 = thisTOCItem4.getHTOCLinks().iterator();
                    if (il4.hasNext()){
                      HelpTableOfContentItemLink link = (HelpTableOfContentItemLink)il4.next();
                      %>
                      <a href="javascript:updateWindows(<%=link.getLinkToHelpItem() %>,'<%=link.getLinkModule() %>');">
                    <%}%>
                    <%=toHtml(thisTOCItem4.getDisplayText())%></a>
                 </td>
                </tr>
               <%}
               }
              }
            }
        }%>
</table>
</body>
</html>
