package com.b07.database;

import android.database.sqlite.SQLiteDatabase;

public class DatabaseDeleteHelper extends DatabaseHelper {

  public DatabaseDeleteHelper(DatabaseDriverAndroid dbDriver) {
    super(dbDriver);
  }

  public static DatabaseDeleteHelper getInstance(DatabaseDriverAndroid dbDriver) {
    return new DatabaseDeleteHelper(dbDriver);
  }

  public int deleteAccountSummaries(int accountId) {
    SQLiteDatabase sqLiteDatabase = dbDriver.getWritableDatabase();
    int rowsDeleted = sqLiteDatabase.delete("ACCOUNTSUMMARY", "ACCTID=?",
        new String[]{String.valueOf(accountId)});
    sqLiteDatabase.close();
    return rowsDeleted;
  }
}
