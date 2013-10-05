/*
 *  BukkitUtil: Utility classes for Bukkit plugin development
 *  Copyright © 2013  Alexander Krivács Schrøder
 *  Copyright (C) 2013  M.Y.Azad
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

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

/**
 * The MessageSender class contains a bunch of helpful methods for sending
 * messages.
 */
public class MessageSender {

	/**
	 * Sends a message to the sender, where color codes using the '&' character
	 * are automatically converted to the color codes Minecraft expects.
	 * 
	 * @param sender
	 *            whom to send the message
	 * @param message
	 *            message to be displayed
	 */
	public static void send(final CommandSender sender, final String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	private String prefix = "";

	/**
	 * Get the prefix of this MessageSender used in the
	 * {@link #sendWithPrefix(CommandSender, String) sendWidthPrefix()} method.
	 * 
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Get the prefix of this MessageSender. Supports the use of color codes
	 * using the '&' character. The prefix is used in the
	 * {@link #sendWithPrefix(CommandSender, String) sendWidthPrefix()} method.
	 * 
	 * @param prefix
	 *            the prefix
	 */
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Sends a message to the sender with a configurable prefix, as set by
	 * {@link #setPrefix(String)}, where color codes using the '&' character are
	 * automatically converted to the color codes Minecraft expects.
	 * 
	 * @param sender
	 *            whom to send the message
	 * @param message
	 *            message to be displayed
	 */
	public void sendWithPrefix(final CommandSender sender, final String message) {
		final String messageWithPrefix = prefix + ChatColor.RESET + message;
		send(sender, messageWithPrefix);
	}

	private ChatColor headerColor = ChatColor.GREEN;
	private ChatColor titleColor = ChatColor.GOLD;
	private String titlePrefix = "[ ";
	private String titleSuffix = " ]";
	private char headerCharacter = '_';

	/**
	 * Get the color used in the header generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @return the header color
	 */
	public ChatColor getHeaderColor() {
		return headerColor;
	}

	/**
	 * Set the color used in the header generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @param headerColor
	 *            the header color
	 */
	public void setHeaderColor(final ChatColor headerColor) {
		this.headerColor = headerColor;
	}

	/**
	 * Get the color used in the title generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @return the title color
	 */
	public ChatColor getTitleColor() {
		return titleColor;
	}

	/**
	 * Set the color used in the title generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @param titleColor
	 *            the title color
	 */
	public void setTitleColor(final ChatColor titleColor) {
		this.titleColor = titleColor;
	}

	/**
	 * Get the prefix used in the title generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @return the title prefix
	 */
	public String getTitlePrefix() {
		return titlePrefix;
	}

	/**
	 * Set the prefix used in the title generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @param titlePrefix
	 *            the title prefix
	 */
	public void setTitlePrefix(final String titlePrefix) {
		this.titlePrefix = titlePrefix;
	}

	/**
	 * Get the suffix used in the title generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @return the title suffix
	 */
	public String getTitleSuffix() {
		return titleSuffix;
	}

	/**
	 * Set the suffix used in the title generated in the
	 * {@link #sendTitle(CommandSender, String, int) sendTitle()} method.
	 * 
	 * @param titleSuffix
	 *            the title suffix
	 */
	public void setTitleSuffix(final String titleSuffix) {
		this.titleSuffix = titleSuffix;
	}

	/**
	 * Get the character used to fill in the left-over width in the title
	 * generated in the {@link #sendTitle(CommandSender, String, int)
	 * sendTitle()} method.
	 * 
	 * @return the header character
	 */
	public char getHeaderCharacter() {
		return headerCharacter;
	}

	/**
	 * Set the character used to fill in the left-over width in the title
	 * generated in the {@link #sendTitle(CommandSender, String, int)
	 * sendTitle()} method.
	 * 
	 * @param headerCharacter
	 *            the header character
	 */
	public void setHeaderCharacter(final char headerCharacter) {
		this.headerCharacter = headerCharacter;
	}

	/**
	 * Sends a message to the sender with a formatted header style, like so:
	 * 
	 * <pre>
	 * __________[ TITLE ]__________
	 * </pre>
	 * 
	 * The title text will be centered in the middle, with an equal amount of
	 * characters on each side. You can configure the format of this title by
	 * using the various <code>setTitle*()</code> and <code>setHeader*()</code>
	 * methods.
	 * 
	 * Color codes using the '&' character are automatically converted to the
	 * color codes Minecraft expects.
	 * 
	 * @param sender
	 *            whom to send the message
	 * @param title
	 *            the text to place in the generated header
	 * @param totalCharacters
	 *            how wide to make the header in total
	 */
	public void sendTitle(final CommandSender sender, final String title, final int totalCharacters) {
		final int headerLength = totalCharacters - title.length() - titlePrefix.length() - titleSuffix.length();

		final char[] headerChars = new char[headerLength / 2];
		Arrays.fill(headerChars, headerCharacter);
		final String headerString = new String(headerChars);

		final String header = headerColor + headerString + titlePrefix + titleColor + title + headerColor + titleSuffix + headerString;
		send(sender, header);
	}

	/**
	 * Returns a nice text representation of the {@link Material materials}, by
	 * making them lower case and replacing underscore characters '_' with
	 * spaces ' '.
	 * 
	 * @param material
	 *            the material to return a nice text representation of
	 * @return the nice text representation of the provided material
	 */
	public static String getNiceNameOf(final Material material) {
		return material.toString().toLowerCase().replace('_', ' ');
	}
}
