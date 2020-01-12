package com.b07.exporter;

import android.content.Context;
import android.util.Log;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.DataExportException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.users.User;
import java.util.List;

public class UserExporter implements DbRecordExporter<User> {

  private Context appContext;

  public UserExporter(Context appContext) {
    this.appContext = appContext;
  }

  @Override
  public List<User> exportRecords() throws DataExportException {
    try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
      DatabaseSelectHelper helper = DatabaseSelectHelper.getInstance(dbDriver);
      List<User> users = helper.getUsersDetails();
      for (int i = 0; i < users.size(); i++) {
        User user = users.get(i);
        users.get(i).setHashedPassword(helper.getPassword(user.getId()));
        users.get(i).setRoleId(helper.getUserRoleId(user.getId()));
      }
      Log.d("UserExporter", "exported:" + users.size());
      return users;
    }
  }
}
