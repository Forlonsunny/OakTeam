package com.sunny.try3.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TAG = "DBHelper";

	// columns of the Employees table
	public static final String TABLE_EMPLOYEES = "employees";
	public static final String COLUMN_EMPLOYEE_ID = "_id";
	public static final String COLUMN_EMPLOYEE_NAME = "employee_name";
	public static final String COLUMN_EMPLOYEE_ADDRESS = "address";
	public static final String COLUMN_EMPLOYEES_WEBSITE = "website";
	public static final String COLUMN_EMPLOYEES_PHONE_NUMBER = "phone_number";




	private static final String DATABASE_NAME = "employess.db";
	private static final int DATABASE_VERSION = 1;



	// SQL statement of the Employees table creation
	private static final String SQL_CREATE_TABLE_EMPLOYEES = "CREATE TABLE " + TABLE_EMPLOYEES + "("
			+ COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_EMPLOYEE_NAME + " TEXT NOT NULL, "
			+ COLUMN_EMPLOYEE_ADDRESS + " TEXT NOT NULL, "
			+ COLUMN_EMPLOYEES_WEBSITE + " TEXT NOT NULL, "
			+ COLUMN_EMPLOYEES_PHONE_NUMBER + " TEXT NOT NULL "
			+");";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE_EMPLOYEES);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG,
				"Upgrading the database from version " + oldVersion + " to "+ newVersion);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
		
		// recreate the tables
		onCreate(db);
	}

	public DBHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}
}
