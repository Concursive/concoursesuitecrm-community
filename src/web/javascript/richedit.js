/*----------------------------------------------------------------------------\
|                           Rich Text Editor 1.14                             |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| A rich text editor (WYSIWYG) for Internet Explorer 4.0 (Win32/Unix) and up  |
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
| 2000-??-?? |
| 2001-12-02 | (1.1) Added getXHTML, supportsXHTML, usebr, oneditinit, fix    |
|            | for no selection, bUI flag to execCommand method               |
| 2001-12-03 | Fixed case of HTML node names to lowercase                     |
| 2002-07-13 | Fixed security error when reloading page in IE. Also updated   |
|            | empty doc template to use IE CSS1 rendering mode.              |
| 2002-09-03 | Added getRange and surroundSelection                           |
| 2002-10-03 | Separated Get XHTML functionality into separate file. XHTML    |
|            | changes can be found in the getxhtml.js file.                  |
|-----------------------------------------------------------------------------|
| Created 2000-??-?? | All changes are in the log above. | Updated 2002-10-03 |
\----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------\
| To use getXHTML you need to include the files stringbuilder as well as the  |
| file getxhtml.js before this file. These can be found at WebFX.             |
\----------------------------------------------------------------------------*/
 
function initRichEdit(el) {
	if (el.id) {	// needs an id to be accessible in the frames collection
		el.frameWindow = document.frames[el.id];
		if (el.value == null)
			el.value = el.innerHTML;
		
		if ( el.value.replace(/\s/g, "") == "" )
			el.value = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
				"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
				"<html>\n" +
				"<head>\n<title></title>\n</head>\n<body>\n</body>\n</html>";
					
		el.src = "about:blank";
		var d = el.frameWindow.document;
		d.designMode = "On";
		d.open();
		d.write(el.value);
		d.close();
		
		el.supportsXHTML = el.frameWindow.document.documentElement && el.frameWindow.document.childNodes != null;
		
		// set up the expandomethods
		
		// first some basic
		
		el.setHTML = function (sHTML) {
			el.value = sHTML;
			initRichEdit(el);
		}
		
		el.getHTML = function () {
			// notice that IE4 cannot get the document.documentElement so we'll use the body
			return el.frameWindow.document.body.innerHTML;
			// for IE5 the following is much better. If you don't want IE4 compatibilty modify this
			//return el.frameWindow.document.documentElement.outerHTML;
		}

		el.getXHTML = function () {
			if (!el.supportsXHTML) {
				alert("Document root node cannot be accessed in IE4.x");
				return;
			}
			else if (typeof window.StringBuilder != "function") {
				alert("StringBuilder is not defined. Make sure to include stringbuilder.js");
				return;
			}
			
			
			var sb = new StringBuilder;
			// IE5 and IE55 has trouble with the document node
			var cs = el.frameWindow.document.childNodes;
			var l = cs.length;
			for (var i = 0; i < l; i++)
				_appendNodeXHTML(cs[i], sb);
		
			return sb.toString();
		};
		el.setText = function (sText) {
			el.value = sText.replace(/\&/g, "&amp;").replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/\n/g, "<br>");
			initRichEdit(el);
		}
		
		el.getText = function () {
			// notice that IE4 cannot get the document.documentElement so we'll use the body
			// not hat it matters when it comes to innerText :-)
			return el.frameWindow.document.body.innerText;
		}

		// and now some text manipulations
		
		el.execCommand = function (execProp, execVal, bUI) {
			return execCommand(this, execProp, execVal, bUI);
		}	
		el.setBold = function () {
			return this.execCommand("bold");
		}
		el.setItalic = function () {
			return this.execCommand("italic");
		}
		el.setUnderline = function () {
			return this.execCommand("underline");
		}
		el.setBackgroundColor = function(sColor) {
			return this.execCommand("backcolor", sColor);
		}
		el.setColor = function(sColor) {
			return this.execCommand("forecolor", sColor);
		}
		el.surroundSelection = function(sBefore, sAfter) {
			var r = this.getRange();
			if (r != null)
				r.pasteHTML(sBefore + r.htmlText + sAfter);
		};
		el.getRange = function () {
			var doc = this.frameWindow.document;
			var r = doc.selection.createRange();
			if (doc.body.contains(r.parentElement()))
				return r;
			// can happen in IE55+
			return null;
		};

		/* modifies the enter keyup event to generate BRs. */

		/* Enabled by default */
		
		if (el.getAttribute("usebr")) {
		
			el.frameWindow.document.onkeydown = function () { 
				if (el.frameWindow.event.keyCode == 13) {	// ENTER
					var sel = el.frameWindow.document.selection;
					if (sel.type == "Control")
						return;
					
					var r = sel.createRange();	
					r.pasteHTML("<BR>");
					el.frameWindow.event.cancelBubble = true; 
					el.frameWindow.event.returnValue = false; 

					r.select();
					r.moveEnd("character", 1);
					r.moveStart("character", 1);
					r.collapse(false);
					
					return false;
				}
			};
			el.frameWindow.document.onkeypress = 
			el.frameWindow.document.onkeyup = function () { 
				if (el.frameWindow.event.keyCode == 13) {	// ENTER
					el.frameWindow.event.cancelBubble = true;
					el.frameWindow.event.returnValue = false;
					return false;
				}
			};			
		}
		
		// Add your own or use the execCommand method.
		// See msdn.microsoft.com for commands

		// call oneditinit if defined
		if (typeof el.oneditinit == "string")
			el.oneditinit = new Function(el.oneditinit);
		if (typeof el.oneditinit == "function")
			el.oneditinit();
	}
	
	function execCommand(el, execProp, execVal, bUI) {
		var doc = el.frameWindow.document;
		var type = doc.selection.type;
		var oTarget = type == "None" ? doc : doc.selection.createRange();
		var r = oTarget.execCommand(execProp, bUI, execVal);
		if (type == "Text")
			oTarget.select();
		return r;
	}
}

function initAllRichEdits() {
	var iframes = document.all.tags("IFRAME");
	
	for (var i=0; i<iframes.length; i++) {
		if (iframes[i].className == "richEdit")
			initRichEdit(iframes[i]);
	}
}

if (window.attachEvent)	// IE5
	window.attachEvent("onload", initAllRichEdits)
else if (document.all)	// IE4
	window.onload = initAllRichEdits;