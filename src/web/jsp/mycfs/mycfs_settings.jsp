<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="Locale" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZone" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<form action='/MyCFSSettings.do?command=UpdateSettings' method='post'>

<dhv:permission name="myhomepage-profile-settings-edit">
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
</dhv:permission>

<dhv:permission name="myhomepage-profile-settings-edit" none="true">
<a href="/MyCFS.do?command=MyProfile">Back to My Profile</a>
</dhv:permission>

<br>
&nbsp;
<input type="hidden" name="modified" value="<%= User.getModifiedString() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Location Settings</strong>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">Start of Day</td>
    <td><input type="text" name="dayStart" value=""></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">End of Day</td>
    <td><input type="text" name="dayEnd" value=""></td>
  </tr>
<%--  <tr>
    <td nowrap class="formLabel">
      Locale
    </td>
    <td>
      <% Locale.setTypeLocale(); %><%= Locale.getHtml("locale", 0) %>
    </td>
  </tr>--%>
  <tr>
    <td nowrap class="formLabel">
      Time Zone
    </td>
    <td>
      <% TimeZone.setTypeTimeZone(); %><%= TimeZone.getHtml("timeZone", User.getTimeZone()) %>
    </td>
  </tr>
</table>

<dhv:permission name="myhomepage-profile-settings-edit">
<br>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
</form>
</dhv:permission>
