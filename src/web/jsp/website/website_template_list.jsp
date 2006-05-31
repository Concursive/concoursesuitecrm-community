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
- Version: $Id: $
- Description:
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.utils.StringUtils"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.aspcfs.modules.website.base.Template"%>
<jsp:useBean id="fromList" class="java.lang.String" scope="request"/>
<jsp:useBean id="templateList" class="org.aspcfs.modules.website.base.TemplateList" scope="request"/>
<script type="text/javascript">
function addWebsite(template) {
  var url = 'Sites.do?command=Add&template=' + template + '&auto-populate=true&popup=true';
  window.location.href= url;
}
</script>
<!-- Trails -->
<table class="trails" cellspacing="0"><tr><td>
  <a href="Sites.do?command=List&popup=true"><dhv:label name="">Sites</dhv:label></a> >
  Choose a Template
</td></tr></table>
<!-- End Trails -->
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="">To get started, select one of the following website templates. These templates
    have content, images, and forms that you can further tailor to your organization.</dhv:label></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
		<td>
<%
	Iterator itemIterator = templateList.iterator();
	if (itemIterator.hasNext()){
		while (itemIterator.hasNext()) {
			Template template = (Template) itemIterator.next();
%>
			<div style="float: left;width: 290px;height: 160px;padding-right: 3px;">
          <table border="0" width="290" height="160" cellpadding="0" cellspacing="0">
            <tr>
              <td height="160" valign="top" nowrap>
                <a href="javascript:addWebsite('<%= StringUtils.toHtml(template.getFilename()) %>');">
                <img src="Sites.do?command=TemplateThumbnail&template=<%= StringUtils.toHtml(template.getFilename()) %> " />
                <div><%=  StringUtils.toHtml(template.getName()) %></div></a>
              </td>
              <td height="160" valign="top" width="100%" style="padding-left: 4px;">
                <%=  StringUtils.toHtml(template.getDescription()) %>
              </td>
            </tr>
          </table>
			</div>
<%
		}
	} else {
%>
			No templates found.
<%
		}
%>
		</td>
	</tr>
</table>
<br />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="<%= (fromList != null && "true".equals(fromList.trim())? "javascript:window.location.href='Sites.do?command=List&popup=true'":"javascript:self.close();") %>"/>

