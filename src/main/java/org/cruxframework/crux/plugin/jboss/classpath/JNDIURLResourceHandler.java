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
package org.cruxframework.crux.plugin.jboss.classpath;

import java.io.IOException;
import java.net.URL;

import org.apache.naming.resources.DirContextURLConnection;
import org.cruxframework.crux.classpath.AbstractURLResourceHandler;
import org.cruxframework.crux.classpath.URLResourceException;
import org.cruxframework.crux.classpath.URLResourceHandler;
import org.cruxframework.crux.plugin.jboss.scanner.archiveiterator.JNDIProtocolIteratorFactory;
import org.cruxframework.crux.scanner.archiveiterator.DirectoryIteratorFactory;

/**
 * 
 * @author Samuel Almeida Cardoso
 *
 */
public class JNDIURLResourceHandler extends AbstractURLResourceHandler implements URLResourceHandler
{
	public static final String PROTOCOL = "jndi"; 

	@Override
	public boolean exists(URL url)
	{
		try
        {
	        ((DirContextURLConnection)url.openConnection()).list();
        }
        catch (IOException e)
        {
        	return false;
        }
		return true;
	}
	
	@Override
	public String getProtocol()
	{
		return PROTOCOL;
	}
	
	@Override
	public URL getParentDir(URL url)
	{
		try
		{
			String path = url.toString();
			
			if (path.endsWith("/"))
			{
				path = path.substring(0, path.length() - 1);
			}
			
			int lastSlash = path.lastIndexOf("/");
			if (lastSlash > 0)
			{
				path = path.substring(0, lastSlash+1);
				return new URL(path);
			}
			return null;
		}
		catch (Exception e)
		{
			throw new URLResourceException(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 
	 */
	@Override
	public DirectoryIteratorFactory getDirectoryIteratorFactory()
	{
		return new JNDIProtocolIteratorFactory();
	}
}
