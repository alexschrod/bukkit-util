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

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A {@link Storage} implementation that uses Bukkit's {@link FileConfiguration}
 * to store objects.
 */
public class FileConfigurationStorage implements Storage {

	private String storageDirectoryName = "storage";

	private File dataFolder;
	
	private boolean initialized = false;

	/**
	 * Gets the name of the directory where the configuration files are saved.
	 * 
	 * @return the name of the storage directory
	 */
	public String getStorageDirectoryName() {
		return storageDirectoryName;
	}

	/**
	 * Sets the name of the directory where the configuration files are saved.
	 * This directory will be created as a sub-directory of
	 * {@link JavaPlugin#getDataFolder()}.
	 * <p>
	 * The default name for the directory is "storage".
	 * 
	 * @param storageDirectoryName
	 *            the name of the storage directory
	 */
	public void setStorageDirectoryName(String storageDirectoryName) {
		this.storageDirectoryName = storageDirectoryName;
	}

	private FileConfiguration getContextFile(String context) {
		final File file = new File(dataFolder, context + ".yml");
		if (!file.exists()) {
			return null;
		}

		final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config;
	}
	
	private void throwExceptionIfNotInitialized() throws StorageException {
		if (!initialized) {
			throw new StorageException("Cannot use the FileConfigurationStorage without calling initialize() first");
		}
	}

	public void initialize(JavaPlugin plugin) throws StorageException {
		try {
			dataFolder = new File(plugin.getDataFolder(), storageDirectoryName);
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			initialized = true;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public void save(String context, String key, Object value) throws StorageException {
		throwExceptionIfNotInitialized();
		try {
			final File file = new File(dataFolder, context + ".yml");
			final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set(key, value);
			config.save(file);
		} catch (final Exception e) {
			throw new StorageException(e);
		}
	}

	public Object load(String context, String key) throws StorageException {
		throwExceptionIfNotInitialized();
		try {
			final FileConfiguration config = getContextFile(context);
			if (config == null) {
				return null;
			}
			return config.get(key);
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public boolean exists(String context, String key) throws StorageException {
		throwExceptionIfNotInitialized();
		try {
			final FileConfiguration config = getContextFile(context);
			if (config == null) {
				return false;
			}
			return config.isSet(key);
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public void delete(String context) throws StorageException {
		throwExceptionIfNotInitialized();
		try {
			final File file = new File(dataFolder, context + ".yml");
			file.delete();
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public void deleteKey(String context, String key) throws StorageException {
		throwExceptionIfNotInitialized();
		try {
			final File file = new File(dataFolder, context + ".yml");
			if (!file.exists()) {
				return;
			}
		} catch (Exception e) {
			throw new StorageException(e);
		}
		save(context, key, null);
	}

	public Set<String> getContexts() throws StorageException {
		throwExceptionIfNotInitialized();
		try {
			final String[] fileNames = dataFolder.list(new FilenameFilter() {
				public boolean accept(final File folder, final String fileName) {
					return fileName.endsWith(".yml");
				}
			});

			final HashSet<String> fileSet = new HashSet<String>();
			for (String fileName : fileNames) {
				fileName = fileName.substring(0, fileName.lastIndexOf(".yml"));
				fileSet.add(fileName);
			}
			return fileSet;
		} catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public Set<String> getKeys(String context) throws StorageException {
		throwExceptionIfNotInitialized();
		try {
			final FileConfiguration config = getContextFile(context);
			if (config == null) {
				return Collections.emptySet();
			}

			return config.getKeys(false);
		} catch (Exception e) {
			throw new StorageException(e);
		}

	}

}
