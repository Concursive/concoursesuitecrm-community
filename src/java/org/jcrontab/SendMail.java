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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;

/**
 *	This class sends an email to the given address every time a task ends
 *	This class does exactly the same as the directive MAILTO extension in 
 * 	crontab</P>
 *  Sending an email has a strong limitation:</P>
 *    The log you receive by mail can be confused in multithreaded tasks or 
 * 	if you have more than two tasks running at the same time. Why? Cause 
 *  The problem is that the output of the classes goes all to the same 
 *	System.out and there is where jcrontab reads it. Then if you concurrent
 *	executions on you system the output you can receive bu mail can be 
 *	confusing.</P>
 *	But how can i avoid that?</P>
 *	Using a better logging system for example log4j, take a look at 
 *	http://jakarta.apache.org/log4j, this system sends emails and does a lot 
 * of things more... </P>
 *	If you can't change your class or running a native program... well take 
 *	it easy and be concious of the problem</P>
 * @author $Author$
 * @version $Revision$
 */

public class SendMail{
	
	private String to = Crontab.getInstance().getProperty(
									"org.jcrontab.SendMail.to");
	private String from = Crontab.getInstance().getProperty(
									"org.jcrontab.SendMail.from");
	private String host = Crontab.getInstance().getProperty(
									"org.jcrontab.SendMail.smtp.host");	
	private String username = Crontab.getInstance().getProperty(
									"org.jcrontab.SendMail.smtp.username");
	private String password = Crontab.getInstance().getProperty(
									"org.jcrontab.SendMail.smtp.password");

	/**
	 *	This method reads the file to be sended and returns the body of 
	 *  the message
	 *	@param file the temporary file where the info is saved
	 *	@return result the whole file in a String
	 *	@throws Exception 
	 */
	
	private String prepare (File file) throws Exception {
			FileInputStream fis = new FileInputStream(file);
            BufferedReader input = new BufferedReader(
							new InputStreamReader(fis));
			String line, result = new String();
            while((line = input.readLine()) != null)
				result += (line + "\n");
			return result;
	}

	/**
	 *	This method sends the email to the given address by the config
	 *	@param file the temporary file in which the output is stored
	 *	@throws Exception 
	 */
	
	public void send(File file) throws Exception {

			String body = prepare(file);

				// create some properties and get the default Session
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				if (username != null && password != null) {
				props.put("mail.smtp.auth", "true");
				}
			Session session = Session.getDefaultInstance(props, null);
			//session.setDebug(true);
			// create a message
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = {new InternetAddress(to)};
				msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject("jcrontab");
				msg.setSentDate(new Date());
				msg.setText(body);
			//here is sended the message
			if (username != null && password != null) {
				Transport transport = session.getTransport("smtp");
				transport.connect(host, username, password);
				transport.send(msg);
			} else {
				Transport.send(msg);
			}
	}
}
