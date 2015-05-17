package com.sunny.try3.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunny.try3.Module.Employee;
import com.sunny.try3.R;
import com.sunny.try3.SQLiteDatabase.EmployeeDAO;

/**
 * Created by Mobile App Develop on 16-5-15.
 */
public class EmployeeDetailsEditActivity extends Activity {
    EditText emName;
    EditText emAddress;
    EditText emPhone;
    EditText emWebA;
    Button btreSave;

    EmployeeDAO aEmployeeDAO;
    Employee aEmployee=null;
    String flag;
    long eMid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_details_edit_xml);

        intitView();
        Intent mEIntent = getIntent();
        flag = mEIntent.getStringExtra("id");
        if (flag != null) {
            eMid = Long.parseLong(flag);
            aEmployeeDAO = new EmployeeDAO(this);

            aEmployee = aEmployeeDAO.getEmployeeById(eMid);

            String eName = aEmployee.getName();
            String eAddress = aEmployee.getAddress();
            String eWebAddress = aEmployee.getWebsite();
            String ephone = aEmployee.getPhoneNumber();
            emName.setText(eName);
            emAddress.setText(eAddress);
            emWebA.setText(eWebAddress);
            emPhone.setText(ephone);
        }
        btreSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable employeeName = emName.getText();
                Editable address = emAddress.getText();
                Editable phoneNumber = emPhone.getText();
                Editable website = emWebA.getText();
                if (!TextUtils.isEmpty(employeeName) && !TextUtils.isEmpty(address)
                        && !TextUtils.isEmpty(website)
                        && !TextUtils.isEmpty(phoneNumber)) {
                    // add the employee to database
                    Employee editedEmployee = aEmployeeDAO.updateEmployee(eMid,
                            employeeName.toString(), address.toString(),
                            website.toString(), phoneNumber.toString());

                    Log.d("EmployeeDetails", "updated Employee : " + employeeName.toString());
                    Intent intentupdat = new Intent();
                    intentupdat.putExtra(ListEmployeesActivity.EXTRA_UPDATED_EMPLOYEE, editedEmployee);
                    setResult(RESULT_OK, intentupdat);
                    Toast.makeText(EmployeeDetailsEditActivity.this, "employee_updaed_successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(EmployeeDetailsEditActivity.this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void intitView() {
        emName=(EditText)findViewById(R.id.txt_employe_name);
        emAddress=(EditText)findViewById(R.id.txt_address);
        emPhone=(EditText)findViewById(R.id.txt_phone_number);
        emWebA=(EditText)findViewById(R.id.txt_website);
        btreSave=(Button)findViewById(R.id.btupsave);


    }

   public void forbtEvent(View v){


   }

}
