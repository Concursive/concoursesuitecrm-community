    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">
        &nbsp;
      </td>
      <td width="743" valign="top" colspan="4" class="title">
        &nbsp;<img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle" />
        <a href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisProject.getId() %>"><%= toHtml(thisProject.getTitle()) %></a>
        <%-- <%= toHtml(thisProject.getShortDescription()) %> --%>
        <dhv:evaluate if="<%= thisProject.getPortal() %>">
          <img src="images/portal.gif" border="0" alt="" align="absmiddle" />
        </dhv:evaluate>
        <%--
        <dhv:evaluate if="<%= thisProject.getAllowGuests() %>">
          <img src="images/public.gif" border="0" alt="" align="absmiddle" />
        </dhv:evaluate>
        --%>
        <dhv:evaluate if="<%= thisProject.getApprovalDate() == null %>">
          <img src="images/unapproved.gif" border="0" alt="" align="absmiddle" />
        </dhv:evaluate>
      </td>
    </tr>
<%-- Show news articles --%>
  <% boolean isHideListFilter3 = "hide".equals(projectEnterpriseInfo.getFilterValue("listFilter3")); %>
  <dhv:evaluate if="<%= !isHideListFilter3 %>">
    <tr bgcolor="#EFF0EA">
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="2"><b>&nbsp;News</b></td>
      <td width="88" align="left"><b>Posted</b></td>
      <td width="109"><b>From</b></td>
    </tr>
    <dhv:evaluate if="<%= thisProject.getNews().isEmpty() %>">
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="4">
        &nbsp;&nbsp;&nbsp;No News found.
      </td>
    </tr>
    </dhv:evaluate>
  </dhv:evaluate>
<%
      if (thisProject.getNews().size() > 0) {
        Iterator newsList = thisProject.getNews().iterator();
        while (newsList.hasNext()) {
          NewsArticle thisArticle = (NewsArticle) newsList.next();
%>
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430" colspan="2">
        &nbsp;&nbsp;&nbsp;<img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle" />&nbsp;
        <%= toHtml(thisArticle.getSubject()) %>
        <%--<a href="javascript:popURL('ProjectManagementIssues.do?command=Details&pid=<%= thisProject.getId() %>&iid=<%= thisIssue.getId() %>&popup=true','CFS_Issue','600','300','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Review this issue';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisIssue.getSubject()) %></a>--%>
      </td>
      <td width="88" align="left">&nbsp;<zeroio:tz timestamp="<%= thisArticle.getStartDate() %>" dateOnly="true" default="&nbsp;"/></td>
      <td width="109">&nbsp;<dhv:username id="<%= thisArticle.getEnteredBy() %>"/></td>
    </tr>
<%
        }
      }
%>
<%-- Show the assignments --%>
  <% boolean isHideListFilter1 = "hide".equals(projectEnterpriseInfo.getFilterValue("listFilter1")); %>
  <dhv:evaluate if="<%= !isHideListFilter1 %>">
    <tr bgcolor="#EFF0EA">
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430"><b>&nbsp;Assignments</b></td>
      <td width="116" align="left"><b>Status</b></td>
      <td width="88"><b>Due Date</b></td>
      <td width="109" nowrap><b>Assigned To</b></td>
    </tr>
    <dhv:evaluate if="<%= thisProject.getAssignments().isEmpty() %>">
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="4">
        &nbsp;&nbsp;&nbsp;No Assignments found.
      </td>
    </tr>
    </dhv:evaluate>
  </dhv:evaluate>
<%
      if (thisProject.getAssignments().size() > 0) {
        Iterator assignmentList = thisProject.getAssignments().iterator();
        while (assignmentList.hasNext()) {
          Assignment thisAssignment = (Assignment) assignmentList.next();
%>
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430">
        &nbsp;&nbsp;&nbsp;<%= thisAssignment.getStatusGraphicTag() %>&nbsp;
        <a href="javascript:popURL('ProjectManagementAssignments.do?command=Modify&pid=<%= thisProject.getId() %>&aid=<%= thisAssignment.getId() %>&popup=true&return=ProjectEnterpriseView','Assignment','650','475','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Update this assignment';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisAssignment.getRole()) %></a>
      </td>
      <td width="116" align="left">&nbsp;<%= toHtml(thisAssignment.getStatus()) %></td>
      <td width="88">&nbsp;<%= thisAssignment.getRelativeDueDateString(User.getTimeZone(), User.getLocale()) %></td>
      <td width="109" nowrap>&nbsp;<dhv:username id="<%= thisAssignment.getUserAssignedId() %>"/></td>
    </tr>
<%
        }
      }
%>
<%-- Show discussion topics --%>
  <% boolean isHideListFilter2 = "hide".equals(projectEnterpriseInfo.getFilterValue("listFilter2")); %>
  <dhv:evaluate if="<%= !isHideListFilter2 %>">
    <tr bgcolor="#EFF0EA">
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="2"><b>&nbsp;Topics</b></td>
      <td width="88" align="left"><b>Posted</b></td>
      <td width="109"><b>From</b></td>
    </tr>
    <dhv:evaluate if="<%= thisProject.getIssues().isEmpty() %>">
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="4">
        &nbsp;&nbsp;&nbsp;No Topics found.
      </td>
    </tr>
    </dhv:evaluate>
  </dhv:evaluate>
<%
      if (thisProject.getIssues().size() > 0) {
        Iterator issueList = thisProject.getIssues().iterator();
        while (issueList.hasNext()) {
          Issue thisIssue = (Issue)issueList.next();
%>      
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430" colspan="2">
        &nbsp;&nbsp;&nbsp;<img src="images/icons/stock_draw-callouts-16.gif" border="0" align="absmiddle" />&nbsp;
        <%= toHtml(thisIssue.getSubject()) %>
        <%--<a href="javascript:popURL('ProjectManagementIssues.do?command=Details&pid=<%= thisProject.getId() %>&iid=<%= thisIssue.getId() %>&popup=true','CFS_Issue','600','300','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Review this issue';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisIssue.getSubject()) %></a>--%>
      </td>
      <td width="88" align="left">&nbsp;<zeroio:tz timestamp="<%= thisIssue.getReplyDate() %>" dateOnly="true" default="&nbsp;"/></td>
      <td width="109">&nbsp;<dhv:username id="<%= thisIssue.getModifiedBy() %>" /></td>
    </tr>
<%
        }
      }
      if (i.hasNext()) {
%>      
  <dhv:evaluate if="<%= !isHideListFilter3 || !isHideListFilter2 || !isHideListFilter1 %>">
  <tr>
    <td colspan="5">
      &nbsp;
    </td>
  </tr>
  </dhv:evaluate>
<%
      }
%>
