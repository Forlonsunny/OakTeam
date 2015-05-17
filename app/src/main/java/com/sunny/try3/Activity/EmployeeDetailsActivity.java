package com.sunny.try3.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sunny.try3.Module.Employee;
import com.sunny.try3.R;
import com.sunny.try3.SQLiteDatabase.EmployeeDAO;

/**
 * Created by Mobile App Develop on 16-5-15.
 */
public class EmployeeDetailsActivity extends Activity {
    TextView emName;
    TextView emAddress;
    TextView emPhone;
    TextView emWebA;
    Button btUpdate;
    Button btDelete;
    EmployeeDAO aEmployeeDAO;
    Employee aEmployee=null;
    String flag;
    long eMid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_details_xml);

intitView();
        Intent mEIntent=getIntent();
        flag=mEIntent.getStringExtra("id");
        if(flag!=null)
        {
            eMid=Long.parseLong(flag);
            aEmployeeDAO=new EmployeeDAO(this);

            aEmployee=aEmployeeDAO.getEmployeeById(eMid);

            String eName=aEmployee.getName();
            String eAddress=aEmployee.getAddress();
            String eWebAddress=aEmployee.getWebsite();
            String ephone=aEmployee.getPhoneNumber();
            emName.setText(eName);
            emAddress.setText(eAddress);
            emWebA.setText(eWebAddress);
            emPhone.setText(ephone);
        }
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bteditInten = new Intent(getApplicationContext(), EmployeeDetailsEditActivity.class);
                bteditInten.putExtra("id", String.valueOf(eMid));
                startActivity(bteditInten);
                finish();

            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aEmployeeDAO.deleteEmployee(eMid);
                Log.d("EmployeeDetailsActivity", "Delated Employee : " + eMid);
                Intent intent = new Intent();
                intent.putExtra(ListEmployeesActivity.EXTRA_DELETED_EMPLOYEE,20);
                setResult(RESULT_OK, intent);
                Toast.makeText(EmployeeDetailsActivity.this, "Deleted Employee successfully", Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }



    private void intitView() {
        emName=(TextView)findViewById(R.id.txt_employe_name);
        emAddress=(TextView)findViewById(R.id.txt_address);
        emPhone=(TextView)findViewById(R.id.txt_phone_number);
        emWebA=(TextView)findViewById(R.id.txt_website);
        btUpdate=(Button)findViewById(R.id.btupdate);
        btDelete=(Button)findViewById(R.id.btdelete);

    }
}
