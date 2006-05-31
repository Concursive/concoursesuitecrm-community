package org.aspcfs.modules.dialer.beans;

import java.util.Date;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Oct 19, 2005
 */

public class CallClient {
  private Date validationDate = null;
  private String extension = null;
  private String number = null;
  private String lastResponse = null;

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public Date getValidationDate() {
    return validationDate;
  }

  public void setValidationDate(Date validated) {
    this.validationDate = validated;
  }

  public void setValidationDate(String tmp) {
    validationDate = new java.util.Date(Long.parseLong(tmp));
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getLastResponse() {
    return lastResponse;
  }

  public void setLastResponse(String lastResponse) {
    this.lastResponse = lastResponse;
  }

  public boolean isValid() {
    return !(validationDate == null || extension == null || "".equals(extension.trim()));
  }
}

