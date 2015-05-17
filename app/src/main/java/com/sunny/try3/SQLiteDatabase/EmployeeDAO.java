package com.sunny.try3.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sunny.try3.Module.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 5/14/2015.
 */
public class EmployeeDAO {

    public static final String TAG = "EmployeeDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_EMPLOYEE_ID,
            DBHelper.COLUMN_EMPLOYEE_NAME, DBHelper.COLUMN_EMPLOYEE_ADDRESS,
            DBHelper.COLUMN_EMPLOYEES_WEBSITE,
            DBHelper.COLUMN_EMPLOYEES_PHONE_NUMBER };

    public EmployeeDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Employee createEmployee(String name, String address, String website,
                                   String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EMPLOYEE_NAME, name);
        values.put(DBHelper.COLUMN_EMPLOYEE_ADDRESS, address);
        values.put(DBHelper.COLUMN_EMPLOYEES_WEBSITE, website);
        values.put(DBHelper.COLUMN_EMPLOYEES_PHONE_NUMBER, phoneNumber);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_EMPLOYEES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                DBHelper.COLUMN_EMPLOYEE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Employee newEmployee = cursorToEmployee(cursor);
        cursor.close();
        return newEmployee;
    }
    public Employee updateEmployee(Long insertId,String name, String address, String website,
                                  String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EMPLOYEE_NAME, name);
        values.put(DBHelper.COLUMN_EMPLOYEE_ADDRESS, address);
        values.put(DBHelper.COLUMN_EMPLOYEES_WEBSITE, website);
        values.put(DBHelper.COLUMN_EMPLOYEES_PHONE_NUMBER, phoneNumber);

        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                DBHelper.COLUMN_EMPLOYEE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Employee newEmployee = cursorToEmployee(cursor);
        cursor.close();
        return newEmployee;
    }


    public List<Employee> getAllEmpoyees() {
        List<Employee> listEmployess = new ArrayList<Employee>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Employee employee = cursorToEmployee(cursor);
                listEmployess.add(employee);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listEmployess;
    }

    public Employee getEmployeeById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                DBHelper.COLUMN_EMPLOYEE_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Employee employee = cursorToEmployee(cursor);
        return employee;
    }

    protected Employee cursorToEmployee(Cursor cursor) {
        Employee employee = new Employee();
        employee.setId(cursor.getLong(0));
        employee.setName(cursor.getString(1));
        employee.setAddress(cursor.getString(2));
        employee.setWebsite(cursor.getString(3));
        employee.setPhoneNumber(cursor.getString(4));
        return employee;
    }

    public void deleteEmployee(Long eMid) {
     open();
         mDatabase.delete(DBHelper.TABLE_EMPLOYEES,
                 DBHelper.COLUMN_EMPLOYEE_ID + " = " + eMid, null);
        close();
    }
}

