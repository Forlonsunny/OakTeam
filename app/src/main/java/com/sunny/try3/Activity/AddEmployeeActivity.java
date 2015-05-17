package com.sunny.try3.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunny.try3.Module.Employee;
import com.sunny.try3.R;
import com.sunny.try3.SQLiteDatabase.EmployeeDAO;


public class AddEmployeeActivity extends Activity implements OnClickListener {

	public static final String TAG = "AddEmployeeActivity";

	private EditText mTxtCompanyName;
	private EditText mTxtAddress;
	private EditText mTxtPhoneNumber;
	private EditText mTxtWebsite;
	private Button mBtnAdd;

	private EmployeeDAO mEmployeeDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_employee);

		initViews();

		this.mEmployeeDao = new EmployeeDAO(this);
	}

	private void initViews() {
		this.mTxtCompanyName = (EditText) findViewById(R.id.txt_company_name);
		this.mTxtAddress = (EditText) findViewById(R.id.txt_address);
		this.mTxtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
		this.mTxtWebsite = (EditText) findViewById(R.id.txt_website);
		this.mBtnAdd = (Button) findViewById(R.id.btn_add);

		this.mBtnAdd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			Editable companyName = mTxtCompanyName.getText();
			Editable address = mTxtAddress.getText();
			Editable phoneNumber = mTxtPhoneNumber.getText();
			Editable website = mTxtWebsite.getText();
			if (!TextUtils.isEmpty(companyName) && !TextUtils.isEmpty(address)
					&& !TextUtils.isEmpty(website)
					&& !TextUtils.isEmpty(phoneNumber)) {
				// add the employee to database
				Employee createdEmployee = mEmployeeDao.createEmployee(
						companyName.toString(), address.toString(),
						website.toString(), phoneNumber.toString());
				
				Log.d(TAG, "added Employee : "+ createdEmployee.getName());
				Intent intent = new Intent();
				intent.putExtra(ListEmployeesActivity.EXTRA_ADDED_EMPLOYEE, createdEmployee);
				setResult(RESULT_OK, intent);
				Toast.makeText(this, R.string.employee_created_successfully, Toast.LENGTH_LONG).show();
				finish();
			}
			else {
				Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mEmployeeDao.close();
	}
}
