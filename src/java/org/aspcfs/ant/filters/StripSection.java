package org.aspcfs.ant.filters;

import java.io.IOException;
import java.io.Reader;
import org.apache.tools.ant.util.LineTokenizer;
import org.apache.tools.ant.types.Parameter;
import org.apache.tools.ant.filters.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 29, 2004
 *@version    $Id$
 */
public final class StripSection extends BaseParamFilterReader
     implements ChainableReader {
  private final static String BEGIN_KEY = "beginBlock";
  private final static String END_KEY = "endBlock";
  private String beginBlock = null;
  private String endBlock = null;
  private LineTokenizer lineTokenizer = null;
  private String line = null;
  private int linePos = 0;
  private boolean inSection = false;


  /**
   *  Constructor for "dummy" instances.
   *
   *@see    BaseFilterReader#BaseFilterReader()
   */
  public StripSection() {
    super();
  }


  /**
   *  Creates a new filtered reader.
   *
   *@param  in  A Reader object providing the underlying stream. Must not be
   *      <code>null</code>.
   */
  public StripSection(final Reader in) {
    super(in);
    lineTokenizer = new LineTokenizer();
    lineTokenizer.setIncludeDelims(true);
  }


  /**
   *  Returns the next character in the filtered stream. If the desired number
   *  of lines have already been read, the resulting stream is effectively at an
   *  end. Otherwise, the next character from the underlying stream is read and
   *  returned.
   *
   *@return                  the next character in the resulting stream, or -1
   *      if the end of the resulting stream has been reached
   *@exception  IOException  if the underlying stream throws an IOException
   *      during reading
   */
  public final int read() throws IOException {
    if (!getInitialized()) {
      initialize();
      setInitialized(true);
    }

    while (line == null || line.length() == 0) {
      line = lineTokenizer.getToken(in);
      if (line == null) {
        return -1;
      }
      line = stripSection(line);
      linePos = 0;
    }

    int ch = line.charAt(linePos);
    linePos++;
    if (linePos == line.length()) {
      line = null;
    }
    return ch;
  }


  /**
   *  Sets the number of lines to be returned in the filtered stream.
   *
   *@param  tmp    The new beginBlock value
   */
  public final void setBeginBlock(String tmp) {
    beginBlock = tmp;
  }


  /**
   *  Sets the endBlock attribute of the StripSection object
   *
   *@param  tmp  The new endBlock value
   */
  public final void setEndBlock(String tmp) {
    endBlock = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  rdr  Description of the Parameter
   *@return      Description of the Return Value
   */
  public final Reader chain(final Reader rdr) {
    StripSection newFilter = new StripSection(rdr);
    newFilter.setBeginBlock(beginBlock);
    newFilter.setEndBlock(endBlock);
    newFilter.setInitialized(true);
    return newFilter;
  }


  /**
   *  Scans the parameters list for the "lines" parameter and uses it to set the
   *  number of lines to be returned in the filtered stream. also scan for skip
   *  parameter.
   */
  private final void initialize() {
    Parameter[] params = getParameters();
    if (params != null) {
      for (int i = 0; i < params.length; i++) {
        if (BEGIN_KEY.equals(params[i].getName())) {
          beginBlock = params[i].getValue();
          continue;
        }
        if (END_KEY.equals(params[i].getName())) {
          endBlock = params[i].getValue();
          continue;
        }
      }
    }
  }


  /**
   *  implements a head filter on the input stream
   *
   *@param  line  Description of the Parameter
   *@return       Description of the Return Value
   */
  private String stripSection(String line) {
    if (line.indexOf(beginBlock) > -1) {
      inSection = true;
    }
    if (inSection) {
      if (line.indexOf(endBlock) > -1) {
        inSection = false;
      }
      return null;
    }
    return line;
  }
}

