package com.darkhorseventures.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.darkhorseventures.cfsbase.*;

public class BrowserHandler extends TagSupport {
  private String browserId = null;
	private double minVersion = -1;
	private double maxVersion = -1;
  private String os = null;
	private boolean include = true;

  public final void setId(String tmp) {
    browserId = tmp;
  }
	
	public final void setMinVersion(String tmp) {
    minVersion = Double.parseDouble(tmp);
  }
	
	public final void setMaxVersion(String tmp) {
    maxVersion = Double.parseDouble(tmp);
  }
	
  public void setOs(String tmp) { this.os = tmp; }

	public final void setInclude(String tmp) {
    include = tmp.equalsIgnoreCase("true");
  }

  public final int doStartTag() throws JspException {
		UserBean thisUser = (UserBean)pageContext.getSession().getAttribute("User");
		
		if (thisUser.getBrowserId().equalsIgnoreCase(browserId)) {
			if (include) {
				if (versionPasses(thisUser.getBrowserVersion()) && osPasses(thisUser.getClientType().getOsString())) {
					return EVAL_BODY_INCLUDE;
				} else {
					return SKIP_BODY;
				}
			} else {
				if (versionPasses(thisUser.getBrowserVersion()) && osPasses(thisUser.getClientType().getOsString())) {
					return SKIP_BODY;
				} else {
					return EVAL_BODY_INCLUDE;
				}
			}
		} else {
			if (include) {
				return SKIP_BODY;
			} else {
				return EVAL_BODY_INCLUDE;
			}
		}
  }

	private boolean versionPasses(double userVersion) {
		if ((minVersion == -1 || userVersion >= minVersion) &&
			(maxVersion == -1 || userVersion <= maxVersion)) {
			return true;
		} else {
			return false;
		}
	}
  
  private boolean osPasses(String userOs) {
    if (os != null) {
      return (os.equals(userOs));
    } else {
      return true;
    }
  }
}

