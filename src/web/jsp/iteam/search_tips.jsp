<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<b><dhv:label name="project.simpleQuerySyntax">Simple Query Syntax</dhv:label></b>
<br />
<br />
<table bgcolor="#DEDEDE" border="0" cellspacing="1" cellpadding="4" width="100%">
  <tr>
    <td><b><dhv:label name="project.searchProjectDataBy.text">Search for project data by typing in words</dhv:label></b></td>
  </tr>
  <tr>
    <td><dhv:label name="project.fallCampaign.lowercase">fall campaign</dhv:label></td>
  </tr>
  <tr>
    <td><hr color="#BFBFBB" noshade></td>
  </tr>
  <tr>
    <td><b><dhv:label name="project.searchForExactPhrases.text">Search for exact phrases by using quotations</dhv:label></b></td>
  </tr>
  <tr>
    <td><dhv:label name="project.fallCampaign.quotes">"fall campaign"</dhv:label></td>
  </tr>
  <tr>
    <td><hr color="#BFBFBB" noshade></td>
  </tr>
  <tr>
    <td><b><dhv:label name="project.andOrNotOperators.text">Use AND, OR, NOT operators when searching</dhv:label></b></td>
  </tr>
  <tr>
    <td><dhv:label name="project.campaignNOTfall">campaign NOT fall</dhv:label><br />
	    <dhv:label name="project.andOrNot.example" param="quot=&quot;|break=<br />">&quot;fall campaign&quot; AND &quot;campaign ideas&quot;<br />&quot;fall campaign&quot; OR ideas<br /><br />Note: AND, OR, NOT can be uppercase or lowercase.</dhv:label>
    </td>
  </tr>
  <tr>
    <td><hr color="#BFBFBB" noshade></td>
  </tr>
  <tr>
    <td><b><dhv:label name="project.singleMultipleWildcard.text">Search using single and multiple character wildcard searches</dhv:label></b></td>
  </tr>
  <tr>
    <td>
      <dhv:label name="project.singleMultipleWildcard.example" param="quot=&quot;|break=<br />">To perform a single character wildcard search use the &quot;?&quot; symbol:<br /><br />cam?aign<br /><br />To perform a multiple character wildcard search use the &quot;*&quot; symbol:<br /><br />ca*gn<br />campaign*<br /><br />Note: You cannot use a * or ? symbol as the first character of a search.</dhv:label>
    </td>
  </tr>
  <tr>
</table>
<br />
<br />
<b><dhv:label name="project.advancedQuerySyntax">Advanced Query Syntax</dhv:label></b>
<br />
<br />
<table bgcolor="#DEDEDE" border="0" cellspacing="1" cellpadding="4" width="100%">
  <tr>
    <td><b><dhv:label name="project.fuzzySearch">Fuzzy Search</dhv:label></b></td>
  </tr>
  <tr>
    <td><dhv:label name="project.fuzzySearch.example" param="quot=&quot;|break=<br />">To do a fuzzy search, use the tilde &quot;~&quot; symbol at the end of a single word term. For example, to search for a term similar in spelling to &quot;roam&quot;, use the search:<br /><br />roam~<br /><br />This search will find terms like foam and roams.</dhv:label>
    </td>
  </tr>
  <tr>
    <td><hr color="#BFBFBB" noshade></td>
  </tr>
  <tr>
    <td><b><dhv:label name="project.proximitySearch">Proximity Search</dhv:label></b></td>
  </tr>
  <tr>
    <td><dhv:label name="project.proximitySearch.text">To do a proximity search, use the tilde &quot;~&quot; symbol at the end of a phrase. For example, to search for &quot;fall&quot; and &quot;campaign&quot; within 10 words of each other in a document, use the search:<br /><br />&quot;fall campaign&quot;~10</dhv:label></td>
  </tr>
</table>
<br />
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="window.close()" />
