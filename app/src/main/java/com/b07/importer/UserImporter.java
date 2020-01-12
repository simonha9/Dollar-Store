package com.b07.importer;

import android.content.Context;
import android.util.Log;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.exceptions.DataImportException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.users.User;

import java.util.List;

public class UserImporter implements DbRecordImporter {

    private Context appContext;

    public UserImporter(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public void importRecords(List<Object> data) throws DataImportException {
        Log.d("UserImporter", "Starting");
        int count = 0;
        try (DatabaseDriverAndroid dbDriver = new DatabaseDriverAndroid(appContext)) {
            DatabaseSelectHelper selectHelper = DatabaseSelectHelper.getInstance(dbDriver);
            DatabaseInsertHelper insertHelper = DatabaseInsertHelper.getInstance(dbDriver);
            DatabaseUpdateHelper updateHelper = DatabaseUpdateHelper.getInstance(dbDriver);
            for (Object entity : data) {
                if (entity instanceof User) {
                    User inUser = (User) entity;
                    count++;
//          User user = selectHelper.getUserDetails(inUser.getId());
//          if (user == null) {
                    int userId = insertHelper.insertNewUserWithHashedPassword(inUser.getName(), inUser.getAge(),
                            inUser.getAddress(), inUser.getHashedPassword());
                    int roleId = inUser.getRoleId();
                    insertHelper.insertUserRole_initialize(userId, roleId);
//          } else {
//            updateHelper.updateUserAddress(inUser.getAddress(), inUser.getId());
//            updateHelper.updateUserAge(inUser.getAge(), inUser.getId());
//            updateHelper.updateUserName(inUser.getName(), inUser.getId());
//            updateHelper.updateUserRole(inUser.getRoleId(), inUser.getId());
//          }
                }
            }
        } catch (ValidationFailedException | DatabaseInsertException e) {
            throw new DataImportException("Cannot import user: " + e.getMessage());
        }
        Log.d("UserImporter", "Finished import " + count + " records");
    }
}
