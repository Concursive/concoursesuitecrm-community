<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<script language="javascript" type="text/javascript">
  alert("This user role cannot be changed.\r\n" +
        "Possible reasons:\r\n" +
        "- There must be at least one (1) other user with a project lead role\r\n" +
        "- You must be in a role that can change user roles");
  parent.scrollReload('ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= request.getParameter("pid") %>');
</script>

