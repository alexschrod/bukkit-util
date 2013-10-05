/*
 *  BukkitUtil: Utility classes for Bukkit plugin development
 *  Copyright © 2013  Alexander Krivács Schrøder
 *  Copyright © Tomsik68
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

/**
 * SLAPI = Saving/Loading API or API for Saving and Loading Objects. Originally
 * authored by Tomsik68, this is a modified version that suits my needs better.
 * 
 * <p>
 * This class is used to save and load serializable objects (be they either
 * {@link Serializable} or {@link ConfigurationSerializable}) to streams. There
 * are also convenience methods for saving directly to files (which is very
 * close to how the original SLAPI class worked). Because this class makes use
 * of the {@link BukkitObjectOutputStream} and the
 * {@link BukkitObjectInputStream} classes, a version of Bukkit of 1.6.2-R1.0 or
 * higher is required for using this class.
 */
public class SLAPI {

	/**
	 * Save an object to an {@link OutputStream}. The object (and all its
	 * members, recursively) needs to implement either {@link Serializable} or
	 * {@link ConfigurationSerializable} in order for this method to succeed.
	 * 
	 * @param object
	 *            the object to save
	 * @param stream
	 *            the stream to save the object to
	 * @throws IOException
	 *             if an I/O error occurs during serialization
	 */
	public static void saveToStream(final Object object, final OutputStream stream) throws IOException {
		final BukkitObjectOutputStream oos = new BukkitObjectOutputStream(stream);
		oos.writeObject(object);
		oos.flush();
		oos.close();
	}

	/**
	 * Load an object from an {@link InputStream}. The object (and all its
	 * members, recursively) needs to implement either {@link Serializable} or
	 * {@link ConfigurationSerializable} and have been saved in a manner
	 * compatible with {@link #saveToStream(Object, OutputStream)
	 * saveToStream()} in order for this method to succeed.
	 * 
	 * @param stream
	 *            the stream to read the object from
	 * @return the object that was saved in the stream or null if Java could not
	 *         find the class that was saved
	 * @throws IOException
	 *             if an I/O error occurs during serialization
	 */
	public static Object loadFromStream(final InputStream stream) throws IOException {
		final BukkitObjectInputStream ois = new BukkitObjectInputStream(stream);
		Object result;
		try {
			result = ois.readObject();
		} catch (final ClassNotFoundException e) {
			result = null;
		} finally {
			ois.close();
		}
		return result;
	}

	/**
	 * Save an object to a file. The object (and all its members, recursively)
	 * needs to implement either {@link Serializable} or
	 * {@link ConfigurationSerializable} in order for this method to succeed.
	 * 
	 * <p>
	 * This is just a convenience method for using a {@link FileOutputStream}
	 * with {@link #saveToStream(Object, OutputStream) saveToStream}.
	 * 
	 * @param object
	 *            the object to save
	 * @param path
	 *            the path to the file to save the object in
	 * @throws FileNotFoundException
	 *             if the file exists but is a directory rather than a regular
	 *             file, does not exist but cannot be created, or cannot be
	 *             opened for any other reason
	 * @throws IOException
	 *             if an I/O error occurs during serialization
	 */
	public static void saveToFile(final Object object, final String path) throws FileNotFoundException, IOException {
		saveToStream(object, new FileOutputStream(path));
	}

	/**
	 * Load an object from a file. The object (and all its members, recursively)
	 * needs to implement either {@link Serializable} or
	 * {@link ConfigurationSerializable} and have been saved in a manner
	 * compatible with {@link #saveToStream(Object, OutputStream)
	 * saveToStream()} (which is used in {@link #saveToFile(Object, String)
	 * saveToFile()}) in order for this method to succeed.
	 * 
	 * @param path
	 *            the path to the file the object was saved in
	 * @return the object that was saved in the file or null if Java could not
	 *         find the class that was saved
	 * @throws FileNotFoundException
	 *             if the file does not exist, is a directory rather than a
	 *             regular file, or for some other reason cannot be opened for
	 *             reading.
	 * @throws IOException
	 *             if an I/O error occurs during serialization
	 */
	public static Object loadFromFile(final String path) throws FileNotFoundException, IOException {
		return loadFromStream(new FileInputStream(path));
	}
}