function validate_field(field, type, doalert, autoplace)
{
    if (field.value == null || field.value.length == 0)
    {
        window.isvalid = true;
        return true;
    }
    var validflag = true;
    if (type == "currency" || type == "currency2")
    {
        var val = field.value.replace(/\$/g,"");
        val = val.replace(/,/g,"");
        val = val.toLowerCase();
        if(val.charAt(0) == '=') val = val.substr(1);

        

        if (val.substr(1).search(/[\+\-\*\/]/g) != -1)
        {
            
            var c = val.charAt(0);
            if(val.charAt(0) >='a' && val.charAt(0) <='z')
            {
                value = "error";
            }
            else
            {

			    
				
				try {
				
	                val = eval(val);
				
				} catch (e) { val = "error"; }
				


                autoplace = false;
            }
        }
        numval = parseFloat(val);
        if (isNaN(numval) || Math.abs(numval)>1.0e+10)
        {
            if (doalert) alert("Invalid currency value. Values must be numbers up to 9,999,999,999.99");
            validflag = false;
        }
        else
        {
            

            if(autoplace && val.indexOf(".") == -1) numval/=100;
            if(type == "currency")
                field.value = format_currency(numval);
            else
                field.value = format_currency(numval, true);
           validflag = true;
        }
    }
    else if (type == "date")
    {
        validflag = true;
        var m=0,d=0,y=0,val=field.value;
        var fmterr;

        if(!window.dateformat)
            window.dateformat = "MM/DD/YYYY";   // Compatibility with calendar.jsp

        if(window.dateformat == "MM/DD/YYYY")
        {
            if (val.indexOf("/") != -1)
            {
                var c = val.split("/");
                if(onlydigits(c[0])) m = parseInt(c[0],10);
                if(onlydigits(c[1])) d = parseInt(c[1],10);
                if(onlydigits(c[2])) y = parseInt(c[2],10);
            }
            else
            {
                var l = val.length, str;
                str = val.substr(0,2-l%2); if(onlydigits(str)) m = parseInt(str,10);
                str = val.substr(2-l%2,2); if(onlydigits(str)) d = parseInt(str,10);
                str = val.substr(4-l%2);   if(onlydigits(str)) y = parseInt(str,10);
            }
            fmterr = "MM/DD/YY, MM/DD/YYYY, MMDDYY or MMDDYYYY";
        }
        else if(window.dateformat == "DD.MM.YYYY")
        {
            if (val.indexOf(".") != -1)
            {
                var c = val.split(".");
                if(onlydigits(c[0])) d = parseInt(c[0],10);
                if(onlydigits(c[1])) m = parseInt(c[1],10);
                if(onlydigits(c[2])) y = parseInt(c[2],10);
            }
            else
            {
                var l = val.length, str;
                str = val.substr(0,2-l%2); if(onlydigits(str)) d = parseInt(str,10);
                str = val.substr(2-l%2,2); if(onlydigits(str)) m = parseInt(str,10);
                str = val.substr(4-l%2);   if(onlydigits(str)) y = parseInt(str,10);
            }
            fmterr = "DD.MM.YY, DD.MM.YYYY, DDMMYY or DDMMYYYY";
        }
        else if(window.dateformat == "DD-Mon-YYYY")
        {
            var ms = "JANFEBMARAPRMAYJUNJULAUGSEPOCTNOVDEC";
            if (val.indexOf("-") != -1)
            {
                var c = val.split("-");
                if(onlydigits(c[0])) d = parseInt(c[0],10);
                m = (ms.indexOf(c[1].toUpperCase())+3)/3;
                if(onlydigits(c[2])) y = parseInt(c[2],10);
            }
            else
            {
                var l = val.length, str;
                str = val.substr(0,1+l%2); if(onlydigits(str)) d = parseInt(str,10);
                str = val.substr(1+l%2,3); m = (ms.indexOf(str.toUpperCase())+3)/3;
                str = val.substr(4+l%2);   if(onlydigits(str)) y = parseInt(str,10);
            }
            fmterr = "DD-Mon-YY, DD-Mon-YYYY, DDMonYY or DDMonYYYY";
        }

        if(m==0 || d==0)
        {
            if (doalert) alert("Invalid date value (must be "+fmterr+")");
            validflag = false;
        }
        else
        {
            if(m<1) m=1; else if(m>12) m=12;
            if(d<1) d=1; else if(d>31) d=31;
            if(y<100) y+=((y>=70)?1900:2000);
            if(y<1000) y*=10;
            if (y > 9999) y = (new Date()).getYear();

            field.value = getdatestring(new Date(y, m-1, d));
        }
    }
    else if (type == "mmyydate")
    {
        var month;
        var day = 0;
        var year;

        if (field.value.indexOf("/") == -1)
        {
            var l = field.value.length;
            month = parseInt(field.value.substr(0,2-l%2),10);
            year = parseInt(field.value.substr(2-l%2),10);
        }
        else
        {
            var comps = field.value.split("/");
            month = parseInt(comps[0],10);
            if (comps[2] != null)
            {
                day  = parseInt(comps[1],10);
                year = parseInt(comps[2],10);
            }
            else
                year = parseInt(comps[1],10);
        }
        if (month >= 1 && month <= 12 && ((year >= 0 && year < 100) || (year > 1900 && year <2100)))
        {
            if (year < 50)
                year += 2000;
            else if (year < 100)
                year += 1900;
            if (day == 0 || day > 31)
            {
                if (month == 2)
                {
                    if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
                        day = 29;
                    else
                        day = 28;
                }
                else if (month == 4 || month == 6 || month == 9 || month == 11)
                    day = 30;
                else
                    day = 31;
            }
            field.value = getmmyydatestring(new Date(year, month-1, day));
            validflag = true;
        }
        else
        {
            if (doalert) alert("Invalid date value (must be MMYY, MMYYYY, MM/DD/YY, MM/DD/YYYY)");
            validflag = false;
        }
    }
    else if (type == "ccexpdate")
    {
        

        validflag = true;
        var m=0, y=0;
        if(field.value.indexOf('/') != -1)
        {
            var dToday = new Date();
            var Y = dToday.getYear();
            var M = dToday.getMonth() + 1;
            if(Y <= 999) Y += 1900;         

            var c = field.value.split('/');
            if(onlydigits(c[0])) m = parseInt(c[0],10);
            if(onlydigits(c[1])) y = parseInt(c[1],10);

            if(m<1) m=1; else if(m>12) m=12;
            if(y<100) y+=((y>=70)?1900:2000);

            if(y<Y || (y==Y && m<M))
            {
                if (doalert) alert("Notice: The credit card appears to be incorrect");
            }
            field.value = (m<10?'0':'')+m+'/'+y;
        }
        else
        {
            if (doalert) alert("Please enter an expiration date int MM/YYYY format");
            validflag = false;
        }

    }
    else if (type == "ccnumber") 
    {
        validflag = checkccnumber(field);
    }
    else if (type == "rate")
    {
        var numval;
        var minclip=-10000000;
        var maxclip=10000000;

        var val = field.value;
        val = val.replace(/,/g,"");
        var pctidx = val.lastIndexOf("%");
        if (pctidx!=-1)
            val = val.substr(0,pctidx);

        numval = parseFloat(val);
        if (isNaN(numval) || numval >= maxclip || numval <= minclip)
        {
            if (doalert) alert("Invalid number or percentage");
            validflag = false;
        }
        else
        {
            field.value = format_rate(numval,pctidx!=-1);
            validflag = true;
        }
    }
    else if (type == "integer" || type == "posinteger" || type == "float" || type == "posfloat")
    {
        var numval;
        var minclip=-Math.pow(2,32);    // any reason for this value? YF/25Aug00
        var maxclip=Math.pow(2,64); // Sized by our max allowable check size. YF/25Aug00
        var val = field.value;
        val = val.replace(/,/g,"");

        if (type == "integer")
            numval = parseInt(val,10);
        else if (type == "posinteger")
        {
            numval = parseInt(val,10);
            minclip=0;
        }
        else if (type == "posfloat")
        {
            numval = parseFloat(val);
            minclip=0;
        }
        else
            numval = parseFloat(val);
        if (isNaN(numval) || numval >= maxclip || numval <= minclip)
        {
			if (doalert)
			{
				if (type=="posinteger" || type=="posfloat")
				    alert("Invalid number (must be positive and less than 1.845E19)");
				else if (type=="integer" || type=="float")
				{
				    if (isNaN(numval))
				        alert('You may only enter numbers into this field');
				    else
				        alert("Illegal number: " + numval);
				}
				else
				    alert("Invalid number (must be greater than -4.29B");
            }
            validflag = false;
        }
        else
        {
            field.value = numval;
            validflag = true;
        }
    }
    else if (type == "address")
    {
        var err = '';
        if (field.value.length>999)
        {
            err = "Address too long (truncated at 1000 characters)";
            newval = field.value.substr(0,999);
        }
        if (err != '')
        {
            if (doalert) alert(err);
            field.value = newval;
        }
    }
    else if (type == "time" || type == "timetrack")
    {
        var hours;
        var minutes;

        var re = /([0-9][0-9]?)?(:[0-5][0-9])?/
        var result = re.exec(field.value)
        if (result==null || result.index > 0 || result[0].length != field.value.length)
        {
            timeval = parseFloat(field.value);
            if (isNaN(timeval))
                hours = -1;
            else
            {
                hours = Math.floor(timeval);
                minutes = Math.floor((timeval-hours)*60+0.5);
            }
        }
        else
        {
            if (RegExp.$1.length > 0)
                hours = parseInt(RegExp.$1,10);
            else
                hours = 0;
            if (typeof(RegExp.$2) != "undefined" && RegExp.$2.length > 0)
                minutes = parseInt(RegExp.$2.substr(1),10);
            else
                minutes = 0;
        }
        if (hours >= 0 && minutes >= 0 && minutes < 60)
        {
            field.value = hours + ":" + (minutes < 10 ? "0" : "") + minutes;
            validflag = true;
        }
        else
        {
            if (doalert) alert("Invalid time value (must be HH:MI)");
            validflag = false;
        }
    }
    else if (type == "timeofday")
    {
        var hours;
        var minutes;
        var amorpm;

        var re;
        var re = /([0-9][0-9]?)(:[0-5][0-9])\s?([AaPp])?[Mm]?/
        var result = re.exec(field.value)
        if (result==null || result.index > 0 || result[0].length != field.value.length)
            hours = -1;
        else
        {
            if (RegExp.$1.length > 0)
                hours = parseInt(RegExp.$1,10);
            else
                hours = -1;
            if (typeof(RegExp.$2) != "undefined" && RegExp.$2.length > 0)
                minutes = parseInt(RegExp.$2.substr(1),10);
            else
                minutes = -1;
            amorpm = (RegExp.$3.length == 0 || RegExp.$3 == 'a' || RegExp.$3 == 'A') ? "" : "PM";
        }
        if (hours > 0 && hours <=12 && minutes >= 0 && minutes < 60)
        {
			if (amorpm == "") amorpm = "AM";
            field.value = hours + ":" + (minutes < 10 ? "0" : "") + minutes + " " + amorpm;
            validflag = true;
        }
        else if (hours > 12 && hours <= 25 && minutes >= 0 && minutes < 60 && amorpm == "")
        {
			amorpm = "PM";
			hours -= 12;
            field.value = hours + ":" + (minutes < 10 ? "0" : "") + minutes + " " + amorpm;
        }
        else
        {
            if (doalert) alert("Invalid time of day value (must be HH24:MI or HH:MI AM/PM)");
            validflag = false;
        }
    }
    else if (type == "visiblepassword")
    {
        if (checkpassword(field, field, doalert))
            validflag = true;
        else
            validflag = false;
    }
    else if (type == "email")
    {
        if (checkemail(field, true, doalert))
            validflag = true;
        else
            validflag = false;
    }
    else if (type == "emails")
    {
        var bademail;
        var emails1 = field.value.split(',');
        for (var i=0; i<emails1.length; i++)
        {
            if (validflag && !isValEmpty(emails1[i]))
            {
				var emails = emails1[i].split(';');
    		    for (var j=0; j<emails.length; j++)
    		    {
           			if (validflag && !isValEmpty(emails[j]))
           			{
                		validflag = checkemailvalue(emails[j], false);
                		if (!validflag)
                   			 bademail = emails[j];
					}
				}
            }
        }
        if (!validflag)
            if (doalert) alert("Invalid email found:"+bademail);
    }
    else if (type == "printerOffset")
    {
        var maxclip =  2.0;
        var minclip = -2.0;
        var val = field.value;
        val = val.replace(/,/g,"");

        numval = parseFloat(val);
        if (isNaN(numval) || numval >= maxclip || numval <= minclip)
        {
			if (doalert)
			{
				if (numval >= maxclip)
				    alert("Invalid number (must be lower than " + maxclip + ").");
				else if (numval <= minclip)
				    alert("Invalid number (must be greater than " + minclip + ").");
				else
				    alert("Illegal number: " + numval);
            }
            validflag = false;
        }
        else
        {
            //field.value = numval;
            validflag = true;
        }
    }
    else if (type == "metricPrinterOffset")
    {
        var maxclip =  50.0;
        var minclip = -50.0;
        var val = field.value;
        val = val.replace(/,/g,"");

        numval = parseFloat(val);
        if (isNaN(numval) || numval >= maxclip || numval <= minclip)
        {
			if (doalert)
			{
				if (numval >= maxclip)
				    alert("Invalid number (must be lower than " + maxclip + ").");
				else if (numval <= minclip)
				    alert("Invalid number (must be greater than " + minclip + ").");
				else
				    alert("Illegal number: " + numval);
            }
            validflag = false;
        }
        else
        {
            //field.value = numval;
            validflag = true;
        }
    }
    else if (type == "phone"  || type == "fullphone")
    {
        var val = field.value;
        
        var re = /^[0-9()\- .\/]{7,}$/;
        if (!re.test(val))
        {
            if (doalert) alert("Invalid phone number: " + val);
            validflag = false;
        }

        if (validflag && type == "fullphone")
        {
            var val = field.value;
            
            var re = /^[0-9()\- .\/]{10,}$/;
            if (!re.test(val))
            {
                if (doalert) alert("Please include the area code for phone number: " + val);
                validflag = false;
            }
        }
    }
    else if (type == "color")
    {
        var val = field.value;
        if (val.substring(0,1) == "#")
            val = val.substring(1);
        
        var re = /^[0-9ABCDEFabcdef]{6,}$/;
        if (val.length > 6 || !re.test(val))
        {
            if (doalert) alert("Color value must be 6 hexadecimal digits of the form: #RRGGBB.  Example: #FF0000 for red.");
            //validflag = false;

            
            while (val.length < 6)
                val = "0" + val;
            val = val.substring(0,6);
            var newval = "";
            for (var i = 0; i < 6; i++)
            {
                var c = val.charAt(i);
                if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f'))
                    newval += val.substring(i,i+1);
                else
                    newval += "0";
            }
            val = newval;
            validflag = true;
        }
        field.value = "#"+val;
    }
    if (!validflag)
    {
        field.focus();
        field.select();
    }
    window.isvalid = validflag;
    return validflag;
}





function nlGetFullYear(d)
{
    if (navigator.appName == "Netscape")
    {
        if (d.getFullYear=="undefined")
            return d.getYear();
    }
    return d.getFullYear();
}
function nlSetFullYear(d,val)
{
    if (navigator.appName == "Netscape")
    {
        if (d.setFullYear=="undefined")
            d.setYear(val);
    }
    d.setFullYear(val);
}

function getdatestring(d)
{
    if (window.dateformat == "DD-Mon-YYYY")
    {
        var m = "JanFebMarAprMayJunJulAugSepOctNovDec";
        return d.getDate()+"-"+m.substring(d.getMonth()*3,d.getMonth()*3+3)+"-"+nlGetFullYear(d);
    }
    else if (window.dateformat == "DD.MM.YYYY")
        return d.getDate()+"."+(d.getMonth()+1)+"."+nlGetFullYear(d);
    else
        return (d.getMonth()+1)+"/"+d.getDate()+"/"+nlGetFullYear(d);
}
function getmmyydatestring(d)
{
    return ((d.getMonth()+1) < 10 ? "0" : "") + (d.getMonth()+1) + "/" + nlGetFullYear(d);
}
function stringtodate(s)
{
    var comps;
    if (s.indexOf("/") != -1)
    {
        comps = s.split("/");
        var month = parseInt(comps[0],10)-1;
        var day = parseInt(comps[1],10);
        var year = parseInt(comps[2],10);
    }
    else if (s.indexOf(".") != -1)
    {
        comps = s.split(".");
        var day = parseInt(comps[0],10);
        var month = parseInt(comps[1],10)-1;
        var year = parseInt(comps[2],10);
    }
    else if (s.indexOf("-") != -1)
    {
        comps = s.split("-");
        var ms = "JANFEBMARAPRMAYJUNJULAUGSEPOCTNOVDEC";
        var day = parseInt(comps[0],10);
        var month = ms.indexOf(comps[1].toUpperCase())/3;
        var year = parseInt(comps[2],10);
    }
    var d = new Date(year, month,day);
    if (year < 50)
        nlSetFullYear(d, year+2000);
    else if (year < 100)
        nlSetFullYear(d, year+1900);
    return d;
}
function adddays(d, daystoadd)
{
    var d2 = new Date(d.getTime() + 86400 * daystoadd * 1000);
    
    if (d2.getHours() != d.getHours())
    {
		if ((d.getHours() > 0 && d2.getHours() < d.getHours()) || (d.getHours() == 0 && d2.getHours() == 23))
			d2.setTime(d2.getTime() + 3600*1000);
		else
			d2.setTime(d2.getTime() - 3600*1000); 
	}
	d.setTime(d2.getTime());
	return d;
}
function addmonths(d, mtoadd)
{
    var curmonth = d.getMonth()+mtoadd;
    while (curmonth < 0)
    {
        curmonth += 12;
        nlSetFullYear(d, nlGetFullYear(d)-1);
    }
    while (curmonth > 11)
    {
        curmonth -= 12;
        nlSetFullYear(d,nlGetFullYear(d)+1);
    }
    d.setMonth(curmonth);
    return d;
}
function modifydate(d, val, mmyyyy)
{
    timenow = d;
    
    var v = val.split(",");
    var num = 0;
    for (var i=0; i<3; i++)
    {
        if (v[i] == "-")
        {
            continue;
        }
        else if (v[i].charAt(0) == "-" || v[i].charAt(0) == "+")
        {
            num = parseInt(v[i],10);
            if (i==0) // days
                adddays(timenow, num);
            else if (i==1)
                addmonths(timenow, num);
            else if (i==2)
                nlSetFullYear(timenow,nlGetFullYear(timenow)+num);
        }
        else
        {
            num = parseInt(v[i],10);
            if (i==0)
                timenow.setDate(num);
            else if (i==1)
                timenow.setMonth(num);
            else if (i==2)
                nlSetFullYear(timenow,num);
        }
    }
    if (v[3] == "0"||v[3] == "1"||v[3] == "2")
    {
        var qstart = parseInt(v[3],10);
        num = timenow.getMonth();
        addmonths(timenow, 0-((num-qstart+3)%3));
    }
    if (v[4] == "Y")
    {
        adddays(timenow,-1);
    }
    if (v[5] == "E")
    {
        
        num = timenow.getDay(); // 0 -sun, 1-mon, etc
        adddays(timenow, ((5+window.weekstart-num)%7));
    }
    else if (v[5] == "B")
    {
        
        num = timenow.getDay(); // 0 -sun, 1-mon, etc
        adddays(timenow, -((num+1-window.weekstart) % 7));
    }
    return (mmyyyy ? getmmyydatestring(timenow) : getdatestring(timenow));
}

function nlutil_getmodifieddate(type,which,fm,mmyyyy)
{
    var curdate = new Date();
    if (type=="ALL")
        return "";

    timenow = new Date();
    if (type=="TODAY")
        modifier =  (which==1 ? "-,-,-,N,N,-" : "-,-,-,N,N,-");
    else if (type=="TMTD")
        modifier =  (which==1 ? "1,-,-,N,N,-" : "-,-,-,N,N,-");
    else if (type=="OY")
        modifier =  (which==1 ? "+1,-,-1,N,N,-" : "-,-,-,N,N,-");
    else if (type=="YESTERDAY")
        modifier =  (which==1 ? "-1,-,-,N,N,-" : "-1,-,-,N,N,-");
    else if (type=="OW")
        modifier =  (which==1 ? "-6,-,-,N,N,-" : "-,-,-,N,N,-");
    else if (type=="TW")
        modifier =  (which==1 ? "-,-,-,N,N,B" : "-,-,-,N,N,E");
    else if (type=="LW")
        modifier =  (which==1 ? "-7,-,-,N,N,B" : "-7,-,-,N,N,E");
    else if (type=="LWTD")
        modifier =  (which==1 ? "-7,-,-,N,N,B" : "-7,-,-,N,N,-");
    else if (type=="TWTD")
        modifier =  (which==1 ? "-,-,-,N,N,B" : "-,-,-,N,N,-");
    else if (type=="TM")
        modifier =  (which==1 ? "1,-,-,N,N,-" : "1,+1,-,N,Y,-");
    else if (type=="OM")
        modifier =  (which==1 ? "+1,-1,-,N,N,-" : "-,-,-,N,N,-");
    else if (type=="LM")
        modifier =  (which==1 ? "1,-1,-,N,N,-" : "1,-,-,N,Y,-");
    else if (type=="LMTD")
        modifier =  (which==1 ? "1,-1,-,N,N,-" : "-,-1,-,N,N,-");
    else if (type=="NW")
        modifier =  (which==1 ? "+7,-,-,N,N,B" : "+7,-,-,N,N,E");
    else if (type=="N4W")
        modifier =  (which==1 ? "+7,-,-,N,N,B" : "+29,-,-,N,N,E");
    else if (type=="NM")
        modifier =  (which==1 ? "1,+1,-,N,N,-" : "1,+2,-,N,Y,-");
    else if (type=="CUSTOM")
        modifier =  (which==1 ? "-,-,-,-,N,-" : "-,-,-,-,N,-");
    else if (type=="")
        modifier =  (which==1 ? "" : "");
    else if (type.search("FY")!=-1)
    {
        var curmonth = curdate.getMonth();
        var yearmod = (curmonth< fm) ? -1 : 0;
        var endmodifier;
        if (which==1)
        {
            modifier = "1,"+fm+",";
            endmodifier = "N,N,-";
            if (type=="LFYTD" || type=="LFY")
                yearmod +=  -1;
            else if (type=="TFYTD" || type=="TFY")
                yearmod += 0;
            else if (type=="NFY")
                yearmod +=  1;
        }
        else
        {
            if (type=="LFYTD" || type=="TFYTD")
            {
                modifier = "-,-,";
                endmodifier = "N,N,-";
            }
            else
            {
                modifier = "1,"+fm+",";
                endmodifier = "N,Y,-";
            }
            if (type=="TFYTD")
                yearmod =  0; // !! This is always 0 not +=0 !!
            else if (type=="LFY")
                yearmod +=  0;
            else if (type=="LFYTD")
                yearmod += -1;
            else if (type=="TFY")
                yearmod += 1;
            else if (type=="NFY")
                yearmod +=  2;
        }

        if (yearmod < 0)
            modifier += yearmod+",";
        else if (yearmod == 0)
            modifier += "-,";
        else if (yearmod > 0)
            modifier += "+"+yearmod+",";

        modifier += endmodifier;
    }
    else if (type.search("FQ")!=-1)
    {
        var curmonth = curdate.getMonth();
        var monthmod=0;
        var endmodifier;
        if (which==1)
        {
            modifier = "1,";
            endmodifier = "-,"+(fm%3)+",N,-";
            if (type=="LFQTD" || type=="LFQ")
                monthmod +=  -3;
            else if (type=="TFQTD")
                monthmod += 0;
            else if (type=="TFQ")
                monthmod += 0;
            else if (type=="NFQ")
                monthmod += 3;
        }
        else
        {
            if (type=="LFQTD" || type=="TFQTD")
            {
                modifier = "-,";
                endmodifier = "-,N,N,-";
            }
            else
            {
                modifier = "1,";
                endmodifier = "-,"+(fm%3)+",Y,-";
            }
            if (type=="TFQTD" || type=="LFQ")
                monthmod =  0; // !! This is always 0 not +=0 !!
            else if (type=="LFQTD")
                monthmod += -3;
            else if (type=="TFQ")
                monthmod += 3;
            else if (type=="NFQ")
                monthmod +=  6;
        }

        if (monthmod < 0)
            modifier += monthmod+",";
        else if (monthmod == 0)
            modifier += "-,";
        else if (monthmod > 0)
            modifier += "+"+monthmod+",";

        modifier += endmodifier;
    }
    else
        return "";
    return modifydate(curdate, modifier,mmyyyy);
}

function nlPopupHelp(db,s,g,p,usertype) {

    var dest;
    if (usertype == "Employee")
    {
        dest = "/pages/support/help/text/employee.html";
    }
    else if (usertype == "CustJob")
    {
        dest = "/pages/support/help/text/visitor.html";
    }
    else if (usertype == "Vendor")
    {
        dest = "/pages/support/help/text/vendor.html";
    }
    else
    {
        var str = 'section='+s+'&group='+g;
        if (p != null)
        {
            str += '&perm='+p;
        }
        str += '&db='+db+'&usertype='+usertype;
        dest = '/pages/help/popuphelp.nl?'+str;
    }
    window.open(dest,'popuphelp','scrollbars=yes,width=750,height=425');
}
function nlFieldHelp(db,p,f)
{
    var fieldwindow = window.open('/pages/help/fieldhelp.nl?fld='+f+'&perm='+p,'fieldhelp','scrollbars=no,width=350,height=150');
    fieldwindow.focus();
    return false;
}
function DoFieldFocus(form)
{
    if (form == null)
        return;
    var i;
    for (i=0;i< form.elements.length;i++)
    {
        var el = form.elements[i];
        if (el.type == "text" || el.type == "select-one" || el.type == "checkbox")
        {
            el.focus();
            return;
        }
    }
}
function clearMultiSelect(sel)
{
    for (i=sel.length-1; i >=0 ; i--)
    {
        sel.options[i].selected = false;
    }
}

function getnamevaluelisttext(val,delim)
{
	if (val.length == 0)
		return "";
    var nvarray = val.split(String.fromCharCode(4));
    var result = "";
	for (i=0; i < nvarray.length; i++)
	{
		if (i>0) result += delim;
		var nv = nvarray[i].split(String.fromCharCode(3));
		result += nv[1]+": "+nv[2];
	}
	return result;
}
function syncnamevaluelist(list)
{
	list.form.elements[list.name+"_display"].value = getnamevaluelisttext(list.value,"\n");
}

function synclist(list,val,makedefault)
{
    var i;
    for (i=0; i < list.length; i++)
        if (list.options[i].value == val)
        {
            list.selectedIndex=i
            if (makedefault)
                list.options[i].defaultSelected = true;
            break;
        }
}
function syncmultiselectlist(list,val)
{
    clearMultiSelect(list);
    var selvals = val.split(String.fromCharCode(3));
    for (i=0; i < selvals.length; i++)
    {
        for (j=0; j < list.length; j++)
        {
            if (list.options[j].value == selvals[i])
                list.options[j].selected = true;
        }
    }
}
function syncradio(radio,val,makedefault)
{
    var i;
    for (i=0; i < radio.length; i++)
        if (radio[i].value == val)
        {
            radio[i].checked=true;
            if (makedefault)
                radio[i].defaultChecked = true;
            break;
        }
}
function getlisttext(list, val, multisel)
{
    var i;
    if (list.type != "select-one" && !multisel)
        return list.form.elements[list.name+"_display"].value;
    for (i=0; i < list.length; i++)
        if (list.options[i].value == val)
            return list.options[i].text;
    return "";
}
function getmultiselectlisttext(list, val)
{
    var selvals = val.split(String.fromCharCode(3));
    var label = '', numParams = 0;

    for (i=0; i < selvals.length; i++)
    {
        if (numParams > 0) label += '<br>';
        label += getlisttext(list, selvals[i], true);
        numParams++;
    }
    return label;
}
