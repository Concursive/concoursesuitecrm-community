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

package org.jcrontab;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import org.jcrontab.log.Log;

/**
 *	This class is the one that captures the output from the 
 *  native progam and writes it to the System.out
 * @author iolalla
 * @version $Revision$
 */
public class StreamGobbler extends Thread {
	//This variable represents the message
    InputStream is;
	//This variable represents the type(ERROR and OUTPUT)
    String type;
    //Constructor 
    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }
    //This is the thread called when the process is ended
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);    
            } catch (IOException e) {
                Log.error(e.toString(), e);
            }
    }
}
