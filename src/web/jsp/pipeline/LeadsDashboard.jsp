<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ShortChildList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="OppList" class="org.aspcfs.modules.OpportunityList" scope="request"/>
<jsp:useBean id="GraphTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="Dashboard" action="Leads.do?command=Dashboard" method=POST>
<a href="Leads.do">Pipeline Management</a> > 
Dashboard<br>
<hr color="#BFBFBB" noshade>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <%-- Left Column --%>
    <td width="275" valign="top">
      <%-- Graphic --%>
      <table width="275" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign="center" align="center">
    <% if (((String)request.getSession().getAttribute("leadsoverride")) == null) {%>
      My Dashboard
		<%} else {%>
      Dashboard: <%=toHtml((String)request.getSession().getAttribute("leadsothername"))%>
		<%}%>
          </td>
        </tr>
        <tr>
          <td>
            <img border="0" width="275" height="200" src="graphs/<%= request.getAttribute("GraphFileName") %>">
          </td>
        </tr>
        <tr>
          <td align="center">
            <%= GraphTypeList.getHtml() %>&nbsp;
          </td>
        </tr>
      </table>
      <%-- Up a level --%>
      <table width="285" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td align="center" width="100%">
	<% if (!(((String)request.getSession().getAttribute("leadsoverride")) == null)) {%>
	<input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("leadsoverride"))%>">
	<a href="Leads.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("leadspreviousId"))%>">Up One Level</a> |
	<a href="Leads.do?command=Dashboard&reset=1">Back to My Dashboard</a>
	<%} else {%>
      &nbsp;
  <%}%>
          </td>
        </tr>
      </table>
      <%-- User List --%>
      <table width="285" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td valign="center" nowrap>
            Reports ($Gr. Pipe.)
          </td>
          <td width=125 valign=center>
            Title
          </td>
        </tr>
<%
    Iterator x = ShortChildList.iterator();
      if ( x.hasNext() ) {
        int rowid = 0;
        while (x.hasNext()) {
          if (rowid != 1) {
            rowid = 1;
          } else {
            rowid = 2;
          }
          User thisRec = (User)x.next();
%>
        <tr>
          <td class="row<%= rowid %>" valign=center nowrap>
            <a href="Leads.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLastFirst()) %></a>
            ($<%=thisRec.getGrossPipelineCurrency(1000)%>K)
            <dhv:evaluate exp="<%=!(thisRec.getEnabled())%>"><font color="red">*</font></dhv:evaluate>
          </td>
          <td width=125 class="row<%= rowid %>" valign=center>
            <%= toHtml(thisRec.getContact().getTitle()) %>
          </td>
        </tr>
<%      }
      } else {
%>
        <tr>
          <td valign=center colspan=3>No Reporting staff.</td>
        </tr>
      <%}%>
      </table>
    </td>
    <%-- Right Column --%>
    <td valign=top width="100%">
      <%-- Opportunity List --%>
      <table width="100%" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr bgcolor="#DEE0FA">
          <td>Opportunity</td>
          <td align="left">Amnt</td>
        </tr>
<%
	Iterator n = OppList.iterator();
  FileItem thisFile = new FileItem();
  if ( n.hasNext() ) {
    int rowid = 0;
    int previousId = -1;
    while (n.hasNext()) {
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
      Opportunity thisOpp = (Opportunity)n.next();
      if (thisOpp.getId() != previousId) {
%>    
				<tr>
          <td width="100%" class="row<%= rowid %>" valign=center><a href="Leads.do?command=DetailsOpp&oppId=<%=thisOpp.getId()%>&return=dashboard"><%= toHtml(thisOpp.getAccountName()) %>:&nbsp;<%= toHtml(thisOpp.getDescription()) %></a>
          (<%=thisOpp.getComponentCount()%>)
        <% if (thisOpp.hasFiles()) {%>
        <%= thisFile.getImageTag() %>
        <%}%>             
          </td>
          <td width="55" class="row<%= rowid %>">$<%=thisOpp.getTotalValue(1000)%>K</td>
        </tr>
<%
        }
        previousId = thisOpp.getId();
      }
	  } else {
%>
        <tr>
          <td valign="center" colspan="7">No opportunities found.</td>
        </tr>
<%}%>
      </table>
      
      <table width="100%" border="0" cellpadding="3">
        <tr>
          <td align="center">
            <dhv:pagedListStatus object="DashboardListInfo" showRefresh="false" showControlOnly="true"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
