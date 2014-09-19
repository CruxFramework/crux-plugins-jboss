/*
 * Copyright 2014 cruxframework.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cruxframework.crux.plugin.jboss.scanner.archiveiterator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;

import org.apache.naming.resources.DirContextURLConnection;
import org.apache.naming.resources.FileDirContext;
import org.cruxframework.crux.scanner.ScannerException;
import org.cruxframework.crux.scanner.ScannerRegistration;
import org.cruxframework.crux.scanner.Scanners;
import org.cruxframework.crux.scanner.archiveiterator.URLIterator;

/**
 * @author Thiago da Rosa de Bustamante 
 *
 */
public class JNDIProtocolIterator extends URLIterator
{
	protected URL url;
	protected URL parentURL;
	
	public JNDIProtocolIterator(URL url, List<ScannerRegistration> scanners, String pathInZip) throws IOException, URISyntaxException
	{
		super(scanners);
		this.url = url;
		this.parentURL = url;
	}
	
	protected JNDIProtocolIterator(List<ScannerRegistration> scanners)
	{
		super(scanners);
	}

	@SuppressWarnings("unchecked")
	protected void search(URL url) throws IOException 
	{
        URLConnection con = url.openConnection();
        if (con instanceof DirContextURLConnection)
        {
        	DirContextURLConnection dirContextConnection = (DirContextURLConnection)con;
        	Object content = dirContextConnection.getContent();
        	if (content instanceof FileDirContext)
        	{
        		Enumeration<String> files = dirContextConnection.list();
        		
        		if (files != null)
        		{
        			while (files.hasMoreElements())
        			{
        				String childPath = files.nextElement();
        				String urlPath = url.toString();
        				if (!urlPath.endsWith("/"))
        				{
        					urlPath += "/";
        				}
        				
        				URL childURL = new URL(urlPath+childPath);
        				search(childURL);
        			}
        		}
        	}
			else if (!Scanners.ignoreScan(parentURL, url.toString()))
			{
				consumeWhenAccepted(parentURL, url, url.toString());
			}
        }
	}
	
	@Override
	public void search()
	{
		try
        {
			search(url);
        }
        catch (IOException e)
        {
			throw new ScannerException("Error running scanner.", e);
        }
	}
}
