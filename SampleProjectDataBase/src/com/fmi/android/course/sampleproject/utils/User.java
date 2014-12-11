package com.fmi.android.course.sampleproject.utils;

import java.io.Serializable;

import android.content.ContentValues;
import android.database.Cursor;

public class User implements Serializable {

	/**
	 * Class fields
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String password;
	private String imagePath;

	/**
	 * Enum representing the name of columns in the database
	 */
	public enum CURSOR_COLUMNS {
		ID("id"), NAME("name"), EMAIL("email"), IMAGE("image"), PASSWORD(
				"password");

		private String name;

		private CURSOR_COLUMNS(String val) {
			name = val;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @param imagePath
	 */
	public User(String name, String email, String password, String imagePath) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.imagePath = imagePath;
	}

	/**
	 * SQL query for creating the ATMs cities table - BG
	 */
	public static final String SQL_TABLE_NAME = "users";
	public static final String SQL_CREATETE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ SQL_TABLE_NAME
			+ "( "
			+ CURSOR_COLUMNS.ID.getName()
			+ " LONG PRIMARY KEY,"
			+ CURSOR_COLUMNS.NAME.getName()
			+ " nvarchar(64),"
			+ CURSOR_COLUMNS.EMAIL.getName()
			+ " nvarchar(64),"
			+ CURSOR_COLUMNS.IMAGE.getName()
			+ " nvarchar(128),"
			+ CURSOR_COLUMNS.PASSWORD.getName()
			+ " nvarchar(16));";
	
	/**
	 * 
	 * @return ContentValues object containing data from the calling object
	 */
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();
		values.put(CURSOR_COLUMNS.NAME.getName(), this.name);
		values.put(CURSOR_COLUMNS.EMAIL.getName(), this.email);
		values.put(CURSOR_COLUMNS.PASSWORD.getName(), this.password);
		values.put(CURSOR_COLUMNS.IMAGE.getName(), this.imagePath);
		return values;
	}

	
	/**
	 * Create User from DB Cursor
	 * @param c
	 */
	public User(Cursor c) {

		this.name = c
				.getString(c.getColumnIndex(CURSOR_COLUMNS.NAME.getName()));
		this.email = c
				.getString(c.getColumnIndex(CURSOR_COLUMNS.EMAIL.getName()));
		this.imagePath = c
				.getString(c.getColumnIndex(CURSOR_COLUMNS.IMAGE.getName()));
		this.password = c
				.getString(c.getColumnIndex(CURSOR_COLUMNS.PASSWORD.getName()));
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
