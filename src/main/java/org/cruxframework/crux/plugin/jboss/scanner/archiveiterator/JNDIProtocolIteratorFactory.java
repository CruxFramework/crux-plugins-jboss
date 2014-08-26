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
import java.net.URL;
import java.util.List;

import org.cruxframework.crux.scanner.ScannerRegistration;
import org.cruxframework.crux.scanner.archiveiterator.DirectoryIteratorFactory;
import org.cruxframework.crux.scanner.archiveiterator.URLIterator;

/**
 * 
 * @author Samuel Almeida Cardoso
 */
public class JNDIProtocolIteratorFactory implements DirectoryIteratorFactory
{

	public URLIterator create(URL url, List<ScannerRegistration> scanners) throws IOException
	{
		try
		{
			String urlPath = url.toString();
			
			return new JNDIProtocolIterator(url, scanners, urlPath);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error trying invoke .toURI() over the URL object: [" + url + "]", e);
		}
	}
}
