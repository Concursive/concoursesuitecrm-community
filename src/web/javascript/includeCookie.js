
function getCookieVal(offset)
{
  var endstr = document.cookie.indexOf(";", offset);
  if(endstr == -1)
    endstr = document.cookie.length;

  return unescape(document.cookie.substring(offset, endstr));
}

function getCookie(name)
{
  var arg = name + "=";
  var alen = arg.length;
  var clen = document.cookie.length;
  var i = 0;
  var j;
  while (i < clen)
  {
    j = i + alen;
    if(document.cookie.substring(i, j) == arg)
      return getCookieVal(j);
        i = document.cookie.indexOf(" ", i) + 1;
    
    if(i == 0)
      break;
  }
  return null;
}

function setCookie(name, value, expires, path, domain, secure)
{
  document.cookie = name + "=" + escape(value) + 
    ((expires) ? " ; expires=" + expires.toGMTString() : "") + 
    ((path) ? "; path=" + path : "") + 
    ((domain) ? "; domain=" + domain : "") +
    ((secure) ? "; secure" : "");
}

function deleteCookie(name, path, domain)
{
  if(getCookie(name))
  {
    document.cookie = name + "=" + 
      ((path) ? "; path=" + path : "") + 
      ((domain) ? "; domain=" + domain : "") + 
       "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}

function gotoCenter(projectID)
{
  if(projectID != "" && projectID != null)
  {
    //deleteCookie("projectCenterAccess");
    //setCookie("projectCenterAccess", projectID, null, null, null, null);
	//location.href = "projectCenter.jsp";
	//window.open("projectCenter.jsp");
	//window.open().location.href = "projectCenter.jsp";
	//window.location = "projectCenter.jsp";
	location.href = "projectCenter.jsp?pid=" + projectID;
  }
}

function popPrjUpdateWindow(url)
{
  var popActUpdateWindow
  popReqWindow=window.open(url,"actUpdateWindow","menubar=no,scrollbars=no,status=no,width=440,height=400")
}

function popActUpdateWindow(url)
{
  var popActUpdateWindow
  popReqWindow=window.open(url,"actUpdateWindow","menubar=no,scrollbars=yes,status=no,width=550,height=360")
}

function popIss(url)
{
  var popIssWindow
  popIssWindow=window.open(url,"issWindow","menubar=no,scrollbars=yes,status=no,width=440,height=420")
}

function checkDate(datein){
    if (datein != "") {
    	var indate = datein;
    	if (indate.indexOf("-") != -1){
    		var sdate = indate.split("-")
    	}
    	else {
    		var sdate = indate.split("/")
    	}
    	var chkDate = new Date(Date.parse(indate))
    	var cmpDate = (chkDate.getMonth() + 1) + "/" + (chkDate.getDate()) + "/" + (chkDate.getYear())
    	var indate2 = (Math.abs(sdate[0])) + "/" + (Math.abs(sdate[1])) + "/" + (Math.abs(sdate[2]))
    	if (indate2 != cmpDate){
    		//alert("You've entered an invalid date or date format.  Please use the MM/DD/YY format.");
        return false;
    	}
    	else {
    		if (cmpDate == "NaN/NaN/NaN"){
    			//alert("You've entered an invalid date or date format.  Please use the MM/DD/YY format.");
				  return false;
    		}	else {
				  return true;
    		}	
    	}
	}
}
