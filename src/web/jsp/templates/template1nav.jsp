<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
%>
<html>

<head>
<title>CFS<%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
<!--link rel="stylesheet" href="defaultStyle.css" type="text/css"-->
<style type=text/css>
<!--
BODY  { font-family: verdana, arial, sans-serif; font-size: 8pt; background: white }
  .date { color: #3366CC; font-size: 8pt; font-weight: bold; }
  .time { color: #000000; font-size: 8pt; }
  .appt { color: #000000; font-size: 8pt; }
  .underline { text-decoration: underline; }
  .smallfooter { color: #000000; font-size: 8pt; text-align: center; }
  
  .calendar { background: #BFBFBB; }
  .monthName { background: #3366CC; color: #FFFFFF; font-size: 12pt; font-weight: bold; text-align: center; }
  .monthArrowPrev { background: #3366CC; color: #FFFFFF; font-size: 12pt; font-weight: bold; text-align: center; }
  .monthArrowNext { background: #3366CC; color: #FFFFFF; font-size: 12pt; font-weight: bold; text-align: center; }
  .weekName { background: #CCCCCC; color: #000000; font-size: 8pt; text-align: center; }
  .day { background: white; color: #000000; font-family: arial, verdana; font-size: 8pt; font-weight: normal; text-align: left; }
  .highlightday { background: #66FF66; color: #000000; font-family: arial, verdana; font-size: 8pt; font-weight: normal; text-align: left; }
  .noday { background: #FEF8DE; color: #000000; font-family: arial, verdana; font-size: 8pt; font-weight: normal; text-align: left; }
  .today { background: #FFFFA6; color: #000000; font-family: arial, verdana; font-size: 8pt; font-weight: bold; text-align: left; }

  .smallcalendar { background: #FFFFFF; }
  .smallmonthName { background: #3366CC; color: #FFFFFF; font-size: 8pt; font-weight: bold; text-align: center; }
  .smallmonthArrowPrev { background: #3366CC; color: #FFFFFF; font-size: 8pt; font-weight: bold; text-align: center; }
  .smallmonthArrowNext { background: #3366CC; color: #FFFFFF; font-size: 8pt; font-weight: bold; text-align: center; }
  .smallweekName { background: #CCCCCC; color: #000000; font-size: 8pt; text-align: center; }  
  .smallday { background: white; color: #000000; font-size: 8pt; font-weight: normal; text-align: right; }
  .smallhighlightday { background: white; color: #FF0000; font-size: 8pt; font-weight: normal; text-align: right; }
  .smallnoday { background: #EEEEEE; color: #000000; font-size: 8pt; font-weight: normal; text-align: right; }
  .smalltoday { background: #FFFFA6; color: #000000; font-size: 8pt; font-weight: bold; text-align: right; }
  
TABLE, TD, TH, LI, P        { font-family: verdana, arial, sans-serif; font-size: 8pt }

A            {color:#000000;text-decoration:none;} 
A:active     {color:#000000;text-decoration:none;} 
A:visited    {color:#000000;text-decoration:none;}
A:hover      {color:#FF3300;text-decoration:underline;} 
A:visited:hover{color:#FF3300;text-decoration:underline;} 

.r{color:#FFFFFF;text-decoration:underline;} 
.r:active{color:#FFFFFF;text-decoration:underline;} 
.r:visited{color:#FFFFFF;text-decoration:underline;} 
.r:hover{color:#FF3300;text-decoration:underline;} 
.r:visited:hover{color:#FF3300;text-decoration:underline;}

.s{color:#000000;text-decoration:underline;} 
.s:active{color:#000000;text-decoration:underline;} 
.s:visited{color:#000000;text-decoration:underline;} 
.s:hover{color:#FF3300;text-decoration:underline;} 
.s:visited:hover{color:#FF3300;text-decoration:underline;}
-->
</style>
</head>

<body leftmargin=0 rightmargin=0 margin =0 marginwidth=0 topmargin=0 marginheight=0>
<table border="0" width="100%">
  <tr>
    <td>
    </td>
    <td>
      <p align="right"><a href="#Profile" class="s">My Profile</a> | <a href="Login.do?command=Logout" class="s"> Logout</a> |
      <a href="#Help" class="s"> Help</a></p>
    </td>
  </tr>
</table>

<!-- Main Menu -->
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <td width=10><img border="0" src="images/samplemenu9right.gif" width="10" height="64"></td>
    <%= request.getAttribute("MainMenu") %>
    <td><img border="0" src="images/samplemenu9right.gif" width="100%" height="64"></td>
  </tr>
</table>
<!-- Sub Menu 1 -->
<table border="0" width="100%" cellspacing="0" bgcolor="#006699">
  <tr>
    <td><b><font color="#FFFFFF" size="1">&nbsp;<%= ModuleBean.getMenu() %></font></b>
    </td>
  </tr>
</table>

<table border="0" width="100%">
  <tr>
    <td valign="top">
      <table border="0" width="100%">
        <!-- The module goes here -->
        <tr>
          <td>
&nbsp;<br>          
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>          
<jsp:include page="<%= includeModule %>" flush="true"/>
          </td>
        </tr>
        <!-- End module -->
      </table>
    </td>
    
    <!-- Global Items -->
    <%= request.getAttribute("GlobalItems") %>
    <!-- End Global Items -->
    
  </tr>
</table>

<!--hr color="#BFBFBB" noshade-->
<br>
<center><%= request.getAttribute("MainMenuSmall") %></center>
<br>
<center>(C) 2001 Dark Horse Ventures</center>
</body>

</html>

