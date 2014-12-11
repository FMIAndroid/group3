package com.fmi.android.course.sampleproject.database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;

import com.fmi.android.course.sampleproject.utils.User;

public class DBManager {

	private static DBManager instance;

	/**
	 * Singleton
	 * 
	 * @return CategoryManager instance
	 */
	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}

		return instance;
	}

	/**
	 * Save a user object in users table in local database
	 * 
	 * @param media
	 * @return result code
	 * @throws DBException
	 */

	public long saveUser(User user) throws Exception {
		long result = -1l;

		result = DBConnector.getInstance().insert(User.SQL_TABLE_NAME,
				user.getContentValues());

		return result;
	}

	/**
	 * Retrieve a user from users table
	 * 
	 * @param Id
	 * @return Cursor
	 * @throws DBException
	 */
	public String getUser(String Id) throws Exception {
		Cursor cursor = DBConnector.getInstance().query(User.SQL_TABLE_NAME,
				new String[] { User.CURSOR_COLUMNS.NAME.getName() },
				User.CURSOR_COLUMNS.ID.getName() + "=" + Id, null, null);
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		} else {
			return "";
		}

	}

	/**
	 * Get all users
	 * 
	 * @return Cursor
	 * @throws DBException
	 */
	public ArrayList<User> getAllUsers() throws Exception {
		ArrayList<User> users = new ArrayList<User>();
		User user = null;
		if (!isTableEmpty()) {
			Cursor c = DBConnector.getInstance().query(
					User.SQL_TABLE_NAME,
					new String[] { User.CURSOR_COLUMNS.ID.getName(),
							User.CURSOR_COLUMNS.NAME.getName(),
							User.CURSOR_COLUMNS.EMAIL.getName(),
							User.CURSOR_COLUMNS.IMAGE.getName(),
							User.CURSOR_COLUMNS.PASSWORD.getName() }, null,
					null, null);
			if (c == null) {
				return null;
			}
			c.moveToFirst();
			while (!c.isLast()) {
				user = new User(c);
				users.add(user);
				c.moveToNext();
			}
		}
		return users;

	}

	/**
	 * Deletes a user from the local DB
	 * 
	 * @param Id
	 * @throws DBException
	 */
	public void deleteUser(long Id) throws Exception {
		DBConnector.getInstance().delete(User.SQL_TABLE_NAME,
				User.CURSOR_COLUMNS.ID.getName() + "=" + Id, null);
	}

	/**
	 * Delete all categories
	 * 
	 * @throws DBException
	 */
	public void deleteAllCategories() throws Exception {
		DBConnector.getInstance().delete(User.SQL_TABLE_NAME, null, null);
	}

	/**
	 * Method to determine if Categories table is empty
	 * 
	 * @return TRUE if table is EMPTY and FALSE if NOT EMPTY
	 * @throws DBSQLException
	 * @throws DBException
	 */
	public Boolean isTableEmpty() throws SQLException, Exception {
		Cursor cur = DBConnector.getInstance().rawQuery(
				"SELECT COUNT(*) FROM " + User.SQL_TABLE_NAME);

		if (cur != null) {
			cur.moveToFirst(); // Always one row returned.
			if (cur.getInt(0) == 0) { // Zero count means empty table.
				return true;
			} else {
				return false;
			}
		}
		return true;

	}

}
