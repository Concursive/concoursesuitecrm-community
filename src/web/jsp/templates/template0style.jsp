<%@ page  import="java.util.*,com.darkhorseventures.cfsmodule.*,com.darkhorseventures.controller.*" %>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="com.darkhorseventures.cfsmodule.ModuleBean" scope="request"/>
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
  .highlight { color: #006699; font-weight: bold; }
  .pagedlist { background: #D4D4D4; }
  
  .title { background: #DEE0FA; }
  
  .containerHeader { background: #FFFF95; }
  .containerMenu { background: #F1F0E0; }
  .containerBack { background: #FFFDED; }
  .containerBody { background: #FFFFFF; }
  
  .column { color: #000080; font-weight: bold; }
  .row1old { background: #E5E5E5; }
  .row1 { background: #EDEDED; }
  .row2 { background: #FFFFFF; }
  .columntext1 { color: #000080; }
  .error { color: #006699; }
  
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
  
TABLE, TD, TH, LI, P, UL, OL, TEXTAREA, INPUT, SELECT  { font-family: verdana, arial, sans-serif; font-size: 8pt }
.list { background-color: #D4D4D4 }
.formLabel { text-align: right; width: 100 }

A            {color:#000000;text-decoration:underline;} 
A:active     {color:#000000;} 
A:visited    {color:#000000;}
A:hover      {color:#FF3300;} 
A:visited:hover{color:#FF3300;} 

.r{color:#FFFFFF;text-decoration:none;} 
.r:active{color:#FFFFFF;} 
.r:visited{color:#FFFFFF;} 
.r:hover{color:#ccccff;text-decoration:underline;} 
.r:visited:hover{color:#ccccff;text-decoration:underline;}

.rs{color:#FFFF00;text-decoration:none;} 
.rs:active{color:#FFFF00;} 
.rs:visited{color:#FFFF00;} 
.rs:hover{color:#ccccff;text-decoration:underline;} 
.rs:visited:hover{color:#ccccff;text-decoration:underline;}

.s{color:#000000;text-decoration:underline;} 
.s:active{color:#000000;} 
.s:visited{color:#000000;} 
.s:hover{color:#FF3300;text-decoration:underline;} 
.s:visited:hover{color:#FF3300;text-decoration:underline;}
-->
</style>
</head>

<body leftmargin=0 rightmargin=0 margin=0 marginwidth=0 topmargin=0 marginheight=0>
<table border="0" width="100%" height="100%">
  <tr>
    <td valign="middle">
<% String includeModule = (String) request.getAttribute("IncludeModule"); %>          
<jsp:include page="<%= includeModule %>" flush="true"/>
    </td>
  </tr>
</table>
<!-- (C) 2001-2002 Dark Horse Ventures -->
</body>

</html>

