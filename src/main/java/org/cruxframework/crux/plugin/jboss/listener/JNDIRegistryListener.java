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
package org.cruxframework.crux.plugin.jboss.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cruxframework.crux.classpath.URLResourceHandlersRegistry;
import org.cruxframework.crux.plugin.jboss.classpath.JNDIURLResourceHandler;

/**
 * @author Thiago da Rosa de Bustamante
 *
 */
public class JNDIRegistryListener implements ServletContextListener
{
	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent contextEvent) 
	{
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent contextEvent) 
	{
		URLResourceHandlersRegistry.registerURLResourceHandler(new JNDIURLResourceHandler());
		
	}
}
