package com.sunny.try3.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sunny.try3.Adaptar.ListEmployeesAdapter;
import com.sunny.try3.Module.Employee;
import com.sunny.try3.R;
import com.sunny.try3.SQLiteDatabase.EmployeeDAO;

import java.util.ArrayList;
import java.util.List;
//Project upter compleation

public class ListEmployeesActivity extends ActionBarActivity {
	
	public static final String TAG = "ListEmployeesActivity";
	
	public static final int REQUEST_CODE_ADD_EMPLOYEE= 40;
	public static final int REQUEST_CODE_DELETE_EMPLOYEE= 20;
	public static final int REQUEST_CODE_UPDATED_EMPLOYEE= 20;
	public static final String EXTRA_ADDED_EMPLOYEE = "extra_key_added_employee";
	public static final String EXTRA_DELETED_EMPLOYEE = "extra_key_delete_employee";
	public static final String EXTRA_UPDATED_EMPLOYEE = "extra_key_updated_employee";

	private ListView mListviewEmployees;

	
	private ListEmployeesAdapter mAdapter;
	private List<Employee> mListEmployess;
	private EmployeeDAO mEmployeeDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_employees);
		
		// initialize views
		initilicationOfViews();
		
		// fill the listView
		mEmployeeDao = new EmployeeDAO(this);
		mListEmployess = mEmployeeDao.getAllEmpoyees();
		if(mListEmployess != null && !mListEmployess.isEmpty()) {
			mAdapter = new ListEmployeesAdapter(this, mListEmployess);
			mListviewEmployees.setAdapter(mAdapter);
		}
		else {

			mListviewEmployees.setVisibility(View.GONE);
		}
		mListviewEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent btedeInten=new Intent(getApplicationContext(),EmployeeDetailsActivity.class);
				btedeInten.putExtra("id",String.valueOf(id));
				startActivity(btedeInten);
			}
		});
		
	}
	
	private void initilicationOfViews() {
		this.mListviewEmployees = (ListView) findViewById(R.id.list_empoyees);

	}


	//for menu

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_Add) {


			Intent intent=new Intent(this,AddEmployeeActivity.class);
			startActivityForResult(intent,REQUEST_CODE_ADD_EMPLOYEE);

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE_ADD_EMPLOYEE) {
			if (data != null) {
				Employee createdEmployee = (Employee) data.getSerializableExtra(EXTRA_ADDED_EMPLOYEE);
				if (createdEmployee != null) {
					if (mListEmployess == null)
						mListEmployess = new ArrayList<Employee>();
					mListEmployess.add(createdEmployee);

					if (mListviewEmployees.getVisibility() != View.VISIBLE) {
						mListviewEmployees.setVisibility(View.VISIBLE);

					}

					if (mAdapter == null) {
						mAdapter = new ListEmployeesAdapter(this, mListEmployess);
						mListviewEmployees.setAdapter(mAdapter);
					} else {
						mAdapter.setItems(mListEmployess);
						mAdapter.notifyDataSetChanged();
					}
				}
			}

		}  else {
			System.out.println("request code" + requestCode);
			System.out.println("result code" + resultCode);
			super.onActivityResult(requestCode, resultCode, data);
		}
		}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mEmployeeDao.close();

	}


	@Override
	protected void onPostResume() {
		super.onPostResume();
		mEmployeeDao = new EmployeeDAO(this);
		mListEmployess = mEmployeeDao.getAllEmpoyees();
		if(mListEmployess != null && !mListEmployess.isEmpty()) {
			mAdapter = new ListEmployeesAdapter(this, mListEmployess);
			mListviewEmployees.setAdapter(mAdapter);
		}
		else {

			mListviewEmployees.setVisibility(View.GONE);
		}
	}


}
