package com.sunny.try3.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunny.try3.Module.Employee;
import com.sunny.try3.R;

import java.util.List;


public class ListEmployeesAdapter extends BaseAdapter {
	
	public static final String TAG = "ListEmployeesAdapter";
	
	private List<Employee> mItems;
	private LayoutInflater mInflater;
	
	public ListEmployeesAdapter(Context context, List<Employee> listEmployess) {
		this.setItems(listEmployess);
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
	}

	@Override
	public Employee getItem(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
	}

	@Override
	public long getItemId(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if(v == null) {
			v = mInflater.inflate(R.layout.list_item_employee, parent, false);
			holder = new ViewHolder();
			holder.txtEmployessName = (TextView) v.findViewById(R.id.txt_employee_name);

			v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		// fill row data
		Employee currentItem = getItem(position);
		if(currentItem != null) {
			holder.txtEmployessName.setText(currentItem.getName());

		}
		
		return v;
	}
	
	public List<Employee> getItems() {
		return mItems;
	}

	public void setItems(List<Employee> mItems) {
		this.mItems = mItems;
	}

	class ViewHolder {
		TextView txtEmployessName;

			}

}
