<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<script language="javascript" type="text/javascript">
  alert("This user role cannot be changed.\r\n" +
        "Possible reasons:\r\n" +
        "- There must be at least one (1) other user with a project lead role\r\n" +
        "- You must be in a role that can change user roles");
  parent.scrollReload('ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= request.getParameter("pid") %>');
</script>

