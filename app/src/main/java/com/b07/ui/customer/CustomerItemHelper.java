package com.b07.ui.customer;

import android.content.Context;

import com.b07.domain.Item;
import com.b07.exceptions.DataExportException;
import com.b07.exporter.ItemExporter;
import com.b07.exporter.UserExporter;
import com.b07.users.Customer;
import com.b07.users.User;

import java.util.List;

public class CustomerItemHelper {
  private Context context;

  public CustomerItemHelper(Context context) {
    this.context = context;
  }

  public Customer getUserById(int id) throws DataExportException {
    UserExporter userExport = new UserExporter(this.context);
    List<User> userList = userExport.exportRecords();

    for (int i=0; i<userList.size(); i++) {
      if (id == userList.get(i).getId()) {
        return (Customer) userList.get(i);
      }
    }
    return null;
  }

  public Item getItemByName(String name) throws DataExportException {
    ItemExporter itemExport = new ItemExporter(context);
    List<Item> itemList =  itemExport.exportRecords();

    for (int i=0; i<itemList.size(); i++) {
      if (name.equalsIgnoreCase(itemList.get(i).getName())){
        return itemList.get(i);
      }
    }
    return null;
  }

  public String convertName(String name) {
    if (name.equalsIgnoreCase("Fishing Rod")) {
      return "FISHING_ROD";
    } else if (name.equalsIgnoreCase("Hockey Stick")) {
      return "HOCKEY_STICK";
    } else if (name.equalsIgnoreCase("Skates")) {
      return "SKATES";
    } else  if (name.equalsIgnoreCase("Running Shoes")) {
      return "RUNNING_SHOES";
    } else {
      return "PROTEIN_BAR";
    }
  }
}
