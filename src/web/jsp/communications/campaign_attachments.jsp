<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
Create Attachments
</td>
</tr>
</table>
<%-- End Trails --%>
Customize and configure your Campaigns with the following attachments:<br>
&nbsp;<br>
<dhv:permission name="campaign-campaigns-surveys-view">
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><a href="CampaignManagerSurvey.do?command=View">Surveys</a></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td>
      Create interactive surveys using quantitative, qualitative and open-ended questions.
      Once associated with a campaign, the responses can be reviewed and analyzed.
    </td>
  </tr>
</table>
</dhv:permission>
