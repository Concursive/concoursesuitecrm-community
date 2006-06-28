// helper functions
function strtrim(value)
{
  return value.replace(/^\s+/,'').replace(/\s+$/,'');
}

function highlight(name)
{
  elt = document.getElementById(name);
  var sClass = elt.className;
  elt.className = (sClass=="SELECTED")?"":"SELECTED";
}


function expand(node, a)
{
	var good = false; 
	ul = document.getElementById("ul_" + node);
	img = document.getElementById("img_" + node);
	if (good = (ul != null && img != null && ul.className != null && ul.innerHTML != null)) 
	{
		str = img.src;
		if ((ul.className == "Shown") || (ul.className == ""))
		{
			ul.className = "Hidden";
			img.src = "images/tree7c.gif";
			if (a != null) a.href = "javascript:;";
		}
		else
			if (ul.className == "Hidden")
			{
				img.src= "images/tree7o.gif";
				ul.className = "Shown";
			}

		if (strtrim(ul.innerHTML) == "")
		{
			ul.innerHTML = "<img src='images/hourglass.gif' onclick=\"cancelLoad('" + node + "');\"/>";
			ul.className="";
		}
		if ((a != null) && ("object" == typeof(a)) && (window.length > 0))
			a.target = 'frame_' + node;
	}
	return true;
}

function cancelLoad(id)
{
	fra = document.getElementById("frame_" + id);
	if (fra != null)
		fra.src = "about:blank";

	expand(id);
}

function loadNode(id, node)
{
    ul = document.getElementById("ul_"+id);
    
    if (ul != null && "object" == typeof(ul) && node && "object" == typeof(node)) 
    {
        ul.className = "Hidden";
        ul.innerHTML = node.innerHTML;
        expand(id);
    }
    return;
}

