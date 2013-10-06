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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The purpose of the <code>Storage</code> interface and its implementations are
 * to provide a back-end-agnostic way for a plug-in to save and load objects.
 * That is to say, the plug-in should just be able to do
 * 
 * <pre>
 * Storage s = getStorage();
 * SomeClass value = ...;
 * s.save("context", "key", value);
 * </pre>
 * 
 * without having to worry about the actual details about how that object is
 * stored and retrieved. However, because we want to be compatible with Bukkit's
 * file configuration system, some restrictions on what can be stored are placed
 * on us:
 * 
 * <p>
 * Values stored with this storage system are expected to either implement
 * {@link ConfigurationSerializable} or otherwise be a type that is compatible
 * with the Bukkit configuration system, e.g. Java primitive types and certain
 * collection types, like {@link List lists} or {@link Map maps} containing only
 * objects that satisfy these very restrictions.
 * 
 * <p>
 * This storage system is based on a two-level key system (level 1 is called
 * <code>context</code>, level 2 is called <code>key</code>), where how these
 * are abstracted are up to the implementation. As an example, in the
 * {@link FileConfigurationStorage} implementation, the <code>context</code>
 * decides the file name, while <code>key</code> is used as a key in the
 * {@link Configuration} that is stored in the file. In a database storage
 * implementation, context could for instance be a table, while key could be
 * used as the table's primary key column.
 */
public interface Storage {

	/**
	 * Initializes the storage system. Should be called immediately after
	 * instantiating the storage implementation.
	 * 
	 * @param plugin
	 *            an instance of your plug-in
	 * @throws StorageException
	 *             if there is a problem initializing the storage system
	 */
	void initialize(final JavaPlugin plugin) throws StorageException;

	/**
	 * Save an object in the storage system.
	 * 
	 * @param context
	 *            the context to save the object in
	 * @param key
	 *            the key to save the object with
	 * @param value
	 *            the object to store
	 * @throws StorageException
	 *             if there is a problem saving the object in the storage system
	 */
	void save(String context, String key, Object value) throws StorageException;

	/**
	 * Load an object from the storage system.
	 * 
	 * @param context
	 *            the context to load the object from
	 * @param key
	 *            the key the object was saved with
	 * @return the object that was loaded, or null if no object was saved with
	 *         the given <code>context</code> and <code>key</code>.
	 * @throws StorageException
	 *             if there is a problem loading the object from the storage
	 *             system
	 */
	Object load(String context, String key) throws StorageException;

	/**
	 * Checks whether an object is stored in the given <code>context</code> with
	 * the given <code>key</code>.
	 * 
	 * @param context
	 *            the context to check for the existence of the object in
	 * @param key
	 *            the key to check for the existence of
	 * @return <code>true</code> if an object exists, <code>false</code>
	 *         otherwise
	 * @throws StorageException
	 *             if there is a problem checking for the object's existence in
	 *             the storage system
	 */
	boolean exists(String context, String key) throws StorageException;

	/**
	 * Deletes a context and all its stored objects.
	 * 
	 * @param context
	 *            the context to delete
	 * @throws StorageException
	 *             if there is a problem deleting the context from the storage
	 *             system
	 */
	void delete(String context) throws StorageException;

	/**
	 * Deletes an object from a context.
	 * 
	 * @param context
	 *            the context to delete the object from
	 * @param key
	 *            the key the object is stored with
	 * @throws StorageException
	 *             if there is a problem deleting the object from the storage
	 *             system
	 */
	void deleteKey(String context, String key) throws StorageException;

	/**
	 * Gets the contexts in the storage system.
	 * 
	 * @return the contexts in the storage system
	 * @throws StorageException
	 *             if there is a problem enumerating the contexts in the storage
	 *             system
	 */
	Set<String> getContexts() throws StorageException;

	/**
	 * Gets the keys of the objects within a context in the storage system.
	 * 
	 * @param context
	 *            the context to get the keys of
	 * @return the keys of the objects stored in the given context
	 * @throws StorageException
	 *             if there is a problem enumerating the keys in the given
	 *             context in the storage system
	 */
	Set<String> getKeys(String context) throws StorageException;
}
