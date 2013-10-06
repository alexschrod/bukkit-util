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

package net.alexanderschroeder.bukkitutil.storage;

/**
 * The <code>StorageException</code> class is used to indicate that an
 * exceptional condition has occurred in the storage system.
 */
public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new <code>StorageException</code> object without a detail
	 * message.
	 */
	public StorageException() {
		super();
	}

	/**
	 * Constructs a new <code>StorageException</code> object with the specified
	 * detail message.
	 * 
	 * @param message
	 *            the message to generate when a <code>StorageException</code>
	 *            is thrown
	 */
	public StorageException(final String message) {
		super(message);
	}

	/**
	 * Construct a new <code>StorageException</code> object with the specified detail
	 * message and chaining the supplied exception.
	 * 
	 * @param message
	 *            the message to generate when a <code>StorageException</code>
	 *            is thrown
	 * @param exception
	 *            the cause of this exception. A <code>null</code> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.
	 */
	public StorageException(final String message, final Exception exception) {
		super(message, exception);
	}

	/**
	 * Construct a new <code>StorageException</code> object chaining the
	 * supplied exception.
	 * 
	 * @param exception
	 *            the cause of this exception. A <code>null</code> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.
	 */
	public StorageException(final Exception exception) {
		super(exception);
	}
}
