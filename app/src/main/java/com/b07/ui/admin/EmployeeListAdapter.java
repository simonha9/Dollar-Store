package com.b07.ui.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.b07.users.User;
import java.util.List;

public class EmployeeListAdapter extends ArrayAdapter {

  private int layoutResource;

  public EmployeeListAdapter(Context context, int layoutResource, List<User> employeeList) {
    super(context, layoutResource, employeeList);
    this.layoutResource = layoutResource;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;

    if (view == null) {
      LayoutInflater layoutInflater = LayoutInflater.from(getContext());
      view = layoutInflater.inflate(layoutResource, null);
    }

    User employee = (User) getItem(position);

    if (employee != null) {
      TextView employeeName = view.findViewById(android.R.id.text1);

      if (employeeName != null) {
        employeeName.setText(employee.getName());
      }
    }

    return view;
  }
}
