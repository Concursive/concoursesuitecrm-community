/**
 *  This file is part of the jcrontab package
 *  Copyright (C) 2001-2002 Israel Olalla
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free
 *  Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *  MA 02111-1307, USA
 *
 *  For questions, suggestions:
 *
 *  iolalla@yahoo.com
 *
 */
package org.jcrontab.log;

/**
 * This is the Null Logger basically is here to avoid logging,
 * usually this shouldn't be used
 *
 * @author Israel Olalla
 * @version $Revision$
 */
public class NullLogger implements Logger {
  /**
   * This method basically does nothing
   */
  public void init() {
  }

  /**
   * This method basically does nothing
   */
  public void info(String message) {
  }

  /**
   * This method prints the trace of a Exception to
   * avoid losing the info
   */
  public void error(String message, Throwable t) {
    t.printStackTrace();
  }

  /**
   * This method basically does nothing
   */
  public void debug(String message) {
  }
}
