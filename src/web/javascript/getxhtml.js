/*----------------------------------------------------------------------------\
|                           Get XHTML for IE 1.02                             |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
|         Serilizes an IE HTML DOM tree to a well formed XHTML string         |
|-----------------------------------------------------------------------------|
|                  Copyright (c) 1999 - 2002 Erik Arvidsson                   |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| 2001-12-02 | Rich Edit: Added getXHTML, supportsXHTML, usebr, oneditinit,   |
|            | fix for no selection, bUI flag to execCommand method           |
| 2001-12-03 | Rich Edit: Fixed case of HTML node names to lowercase          |
| 2002-10-03 | Fixed case for HTML attribute                                  |
|-----------------------------------------------------------------------------|
| Created 2002-12-02 | All changes are in the log above. | Updated 2002-10-03 |
\----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------\
| This file requires that StringBuilder is first defined. This class can be   |
| found in the file stringbuilder.js at WebFX                                 |
\----------------------------------------------------------------------------*/

function getXhtml(oNode) {
		
	var sb = new StringBuilder;
	// IE5 and IE55 has trouble with the document node so beware
	_appendNodeXHTML(oNode, sb);

	return sb.toString();
}

function _fixAttribute(s) {
	return String(s).replace(/\&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/\"/g, "&quot;");
}

function _fixText(s) {
	return String(s).replace(/\&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;");
}

function _appendNodeXHTML(node, sb) {

	switch (node.nodeType) {
		case 1:	// ELEMENT
		
			if (node.nodeName == "!") {	// IE5.0 and IE5.5 are weird
				sb.append(node.text);
				break;
			}
		
			var name = node.nodeName;
			if (node.scopeName == "HTML")
				name = name.toLowerCase();
		
			sb.append("<" + name);
			
			// attributes
			var attrs = node.attributes;
			var l = attrs.length;
			for (var i = 0; i < l; i++) {
				if (attrs[i].specified) {
					if (attrs[i].nodeName != "style") {
						sb.append(" " + 
							(attrs[i].expando ? attrs[i].nodeName : attrs[i].nodeName.toLowerCase()) +
							"=\"" + _fixAttribute(attrs[i].nodeValue) + "\"");
					}
					else
						sb.append(" style=\"" + _fixAttribute(node.style.cssText) + "\"");
				}
			}
				
			if (node.canHaveChildren || node.hasChildNodes()) {
			
				sb.append(">");
				
				// childNodes
				var cs = node.childNodes;
				l = cs.length;
				for (var i = 0; i < l; i++)
					_appendNodeXHTML(cs[i], sb);
				
				sb.append("</" + name + ">");
			}
			else if (name == "script")
				sb.append(">" + node.text + "</" + name + ">");
			else if (name == "title" || name == "style" || name == "comment")
				sb.append(">" + node.innerHTML + "</" + name + ">");
			else 
				sb.append(" />");
				
			break;
			
		case 3:	// TEXT
			sb.append( _fixText(node.nodeValue) );
			break;
				
		case 4:
			sb.append("<![CDA" + "TA[\n" + node.nodeValue + "\n]" + "]>");
			break;
				
		case 8:
			//sb.append("<!--" + node.nodeValue + "-->");
			sb.append(node.text);
			if (/(^<\?xml)|(^<\!DOCTYPE)/.test(node.text) )
				sb.append("\n");
			break;
			
		case 9:	// DOCUMENT
			// childNodes
			var cs = node.childNodes;
			l = cs.length;
			for (var i = 0; i < l; i++)
				_appendNodeXHTML(cs[i], sb);
			break;
			
		default:
			sb.append("<!--\nNot Supported:\n\n" + "nodeType: " + node.nodeType + "\nnodeName: " + node.nodeName + "\n-->");
	}
}
