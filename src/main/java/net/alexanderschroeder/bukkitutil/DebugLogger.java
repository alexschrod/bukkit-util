/*
 *  BukkitUtil: Utility classes for Bukkit plugin development
 *  Copyright © 2013  Alexander Krivács Schrøder
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *   
 */

package net.alexanderschroeder.bukkitutil;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The DebugLogger class is a modified version of Bukkit's {@link PluginLogger}
 * that prepends all logging calls with the name of the plugin doing all the
 * logging, while also adding some relevant debugging information, such as the
 * name of the class and method that made the logging call. The API for
 * DebugLogger is exactly the same as {@link Logger}.
 * 
 * @see PluginLogger
 * @see Logger
 */
public class DebugLogger extends Logger {
	private static final String DEBUG_PREFIX = "DEBUG";

	private final String pluginName;

	/**
	 * Creates a new DebugLogger that extracts the name from a plugin.
	 * 
	 * @param context
	 *            A reference to the plugin
	 * @param isEnabled
	 *            Whether to pass logging messages on to the server log or not.
	 *            Direct logging handlers (added with
	 *            {@link DebugLogger#addHandler(java.util.logging.Handler)
	 *            addHandler()}) will still receive messages, regardless of this
	 *            paramter's value.
	 */
	public DebugLogger(final JavaPlugin context, final boolean isEnabled) {
		super(context.getLogger().getName() + ".DebugLogger", null);
		final String prefix = context.getDescription().getPrefix();
		pluginName = prefix != null ? prefix : context.getDescription().getName();

		setParent(context.getLogger());
		setLevel(Level.ALL);
		setUseParentHandlers(isEnabled);
	}

	@Override
	public void log(final LogRecord logRecord) {
		final String sourceClassName = logRecord.getSourceClassName();
		final String className = sourceClassName.substring(sourceClassName.lastIndexOf('.') + 1);
		logRecord.setMessage(String.format("[%s] [%s] <%s:%s> %s", pluginName, DEBUG_PREFIX, className, logRecord.getSourceMethodName(), logRecord.getMessage()));
		super.log(logRecord);
	}

	/**
	 * Specify whether to pass logging messages on to the server log or not.
	 * Direct logging handlers (added with
	 * {@link DebugLogger#addHandler(java.util.logging.Handler) addHandler()})
	 * will still receive messages, regardless of this setting's value.
	 * 
	 * @param enabled
	 *            true if log messages are to be passed on to the server log.
	 * 
	 */
	public void setEnabled(final boolean enabled) {
		setUseParentHandlers(enabled);
	}
}
