//<script>
/*
 * This script was created by Erik Arvidsson (erik@eae.net)
 * for WebFX (http://webfx.eae.net)
 * Copyright 2001
 * 
 * For usage see license at http://webfx.eae.net/license.html	
 *
 * Created:		2000-??-??
 * Updates:		2001-12-02	Added getXHTML, supportsXHTML, usebr, oneditinit, *                          fix for no selection, bUI flag to execCommand method *				2001-12-03	Fixed case of HTML node names to lowercase
 */
 function initRichEdit(el) {
	if (el.id) {	// needs an id to be accessible in the frames collection
		el.frameWindow = document.frames[el.id];
		if (el.value == null)
			el.value = el.innerHTML;
		
		if ( el.value.replace(/\s/g, "") == "" )
			el.value = "<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
				"\"DTD/xhtml1-transitional.dtd\">\n" +
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" +				"<head>\n<title></title>\n</head>\n<body>\n</body>\n</html>";
							var d = el.frameWindow.document;
		d.designMode = "On";
		d.open();
		d.write(el.value);
		d.close();
				el.supportsXHTML = el.frameWindow.document.documentElement && el.frameWindow.document.childNodes != null;		
		// set up the expandomethods
		
		// first some basic
		
		el.setHTML = function (sHTML) {
			el.value = sHTML;			initRichEdit(el);
		}
		
		el.getHTML = function () {
			// notice that IE4 cannot get the document.documentElement so we'll use the body
			return el.frameWindow.document.body.innerHTML;
			// for IE5 the following is much better. If you don't want IE4 compatibilty modify this
			//return el.frameWindow.document.documentElement.outerHTML;
		}
		el.getXHTML = function () {
			if (!el.supportsXHTML) {				alert("Document root node cannot be accessed in IE4.x");				return;			}
			else if (typeof window.StringBuilder != "function") {				alert("StringBuilder is not defined. Make sure to include stringbuilder.js");				return;			}			
						var sb = new StringBuilder;
			// IE5 and IE55 has trouble with the document node
			var cs = el.frameWindow.document.childNodes;
			var l = cs.length;
			for (var i = 0; i < l; i++)
				_appendNodeXHTML(cs[i], sb);
		
			return sb.toString();		};
		el.setText = function (sText) {
			el.value = sText.replace(/\&/g, "&amp;").replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/\n/g, "<br>");
			initRichEdit(el);
		}
		
		el.getText = function () {
			// notice that IE4 cannot get the document.documentElement so we'll use the body			// not hat it matters when it comes to innerText :-)
			return el.frameWindow.document.body.innerText;
		}

		// and now some text manipulations
		
		el.execCommand = function (execProp, execVal, bUI) {			return execCommand(this, execProp, execVal, bUI);
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

		/* modifies the enter keyup event to generate BRs. */

		/* Enabled by default */				if (el.getAttribute("usebr")) {		
			el.frameWindow.document.onkeydown = function () { 
				if (el.frameWindow.event.keyCode == 13) {	// ENTER					var sel = el.frameWindow.document.selection;					if (sel.type == "Control")						return;										var r = sel.createRange();	
					r.pasteHTML("<BR>");
					el.frameWindow.event.cancelBubble = true; 
					el.frameWindow.event.returnValue = false; 
					r.select();
					r.moveEnd("character", 1);
					r.moveStart("character", 1);
					r.collapse(false);										return false;
				}			};
			el.frameWindow.document.onkeypress = 			el.frameWindow.document.onkeyup = function () { 
				if (el.frameWindow.event.keyCode == 13) {	// ENTER
					el.frameWindow.event.cancelBubble = true;
					el.frameWindow.event.returnValue = false;
					return false;
				}
			};			
		}
		
		// Add your own or use the execCommand method.
		// See msdn.microsoft.com for commands

		// call oneditinit if defined		if (typeof el.oneditinit == "string")
			el.oneditinit = new Function(el.oneditinit);		if (typeof el.oneditinit == "function")
			el.oneditinit();	}
	
	function execCommand(el, execProp, execVal, bUI) {		var doc = el.frameWindow.document;		var type = doc.selection.type;
		var oTarget = type == "None" ? doc : doc.selection.createRange();
		var r = oTarget.execCommand(execProp, bUI, execVal);
		if (type == "Text")			oTarget.select();
		return r;	}
}
function _appendNodeXHTML(node, sb) {

	function fixAttribute(s) {
		return String(s).replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;").replace("\"", "&quot;");
	}

	function fixText(s) {
		return String(s).replace("&", "&amp;").replace(">", "&gt;").replace("<", "&lt;");;
	}

	switch (node.nodeType) {
		case 1:	// ELEMENT
		
			if (node.nodeName == "!") {	// IE5.0 and IE5.5 are weird
				sb.append(node.text);				break;			}
		
			var name = node.nodeName;			if (node.scopeName == "HTML")				name = name.toLowerCase();
					sb.append("<" + name);
						// attributes
			var attrs = node.attributes;
			var l = attrs.length;
			for (var i = 0; i < l; i++) {				if (attrs[i].specified) {
					if (attrs[i].nodeName != "style")						sb.append(" " + attrs[i].nodeName + "=\"" + fixAttribute(attrs[i].nodeValue) + "\"");
					else
						sb.append(" style=\"" + fixAttribute(node.style.cssText) + "\"");
				}
			}				
			if (node.canHaveChildren || node.hasChildNodes()) {
							sb.append(">");				
				// childNodes
				var cs = node.childNodes;
				l = cs.length;
				for (var i = 0; i < l; i++)
					_appendNodeXHTML(cs[i], sb);									sb.append("</" + name + ">");
			}
			else if (name == "script")
				sb.append(">" + node.text + "</" + name + ">");
			else if (name == "title" || name == "style" || name == "comment")
				sb.append(">" + node.innerHTML + "</" + name + ">");
			else
				sb.append(" />");			break;
			
		case 3:	// TEXT			sb.append( fixText(node.nodeValue) );			break;
				
		case 4:			sb.append("<![CDA" + "TA[" + node.nodeValue + "]" + "]>");
			break;
				
		case 8:			//sb.append("<!--" + node.nodeValue + "-->");
			sb.append(node.text);
			break;
					case 9:	// DOCUMENT
			// childNodes
			var cs = node.childNodes;
			l = cs.length;
			for (var i = 0; i < l; i++)
				_appendNodeXHTML(cs[i], sb);
			break;			
		default:
			sb.append("<!--\nNot Supported:\n\n" + "nodeType: " + node.nodeType + "\nnodeName: " + node.nodeName + "\n-->");	}
}function initAllRichEdits() {
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