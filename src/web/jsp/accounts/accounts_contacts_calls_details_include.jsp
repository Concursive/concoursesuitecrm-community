<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Activity Details</strong>  
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Type
          </td>
          <td>
            <%= toHtml(CallDetails.getCallType()) %>
            <dhv:evaluate if="<%= CallDetails.hasLength() %>">
            Length:
            <%= toHtml(CallDetails.getLengthText()) %>
            </dhv:evaluate>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Subject
          </td>
          <td>
            <%= toHtml(CallDetails.getSubject()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel" valign="top">
            Notes
          </td>
          <td>
            <%= toHtml(CallDetails.getNotes()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" nowrap>
            Result
          </td>
          <td>
            <%= toHtml(CallResult.getDescription()) %>
          </td>
        </tr>
      </table>
