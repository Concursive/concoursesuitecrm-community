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
<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupDirectory.do?command=ConfigureDirectoryCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>Status</th>
  </tr>
  <tr>
    <td>
      License accepted!<br>
      <br>
      Now it's time to configure some of the system settings before you
      can begin using Centric CRM.<br>
      <br>
      Configuration includes the following steps:<br>
      <br>
      <table border="0" class="empty">
        <tr>
          <td class="formLabel">
            Step 1:
          </td>
          <td>
            Setup the file library
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Step 2:
          </td>
          <td>
            Setup external servers (mail, fax)
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Step 3:
          </td>
          <td>
            Setup the database
          </td>
        </tr>
        <tr>
          <td class="formLabel">
            Step 4:
          </td>
          <td>
            Setup the Centric CRM administrative user account
          </td>
        </tr>
      </table>
      <br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
