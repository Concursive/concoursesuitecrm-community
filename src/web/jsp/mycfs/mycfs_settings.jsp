<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="EmployeeBean" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<jsp:useBean id="Locale" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZone" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<form action='/MyCFSSettings.do?command=UpdateSettings' method='post'>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
<br>
&nbsp;
<input type="hidden" name="empid" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="id" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="modified" value="<%= EmployeeBean.getModified() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Locale Settings</strong>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Locale
    </td>
    <td>
      <% Locale.setTypeLocale(); %><%= Locale.getHtml("locale", 0) %>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      Time Zone
    </td>
    <td>
      <% TimeZone.setTypeTimeZone(); %><%= TimeZone.getHtml("timeZone", 0) %>
    </td>
  </tr>
</table>
<br>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
</form>
