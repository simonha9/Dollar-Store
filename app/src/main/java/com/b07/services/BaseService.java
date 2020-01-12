package com.b07.services;

import android.content.Context;
import com.b07.database.DatabaseDeleteHelper;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;

public class BaseService implements AutoCloseable {

  private Context appContext;
  private DatabaseDriverAndroid dbDriver;

  public BaseService(Context appContext) {
    this.appContext = appContext;
  }

  public Context getAppContext() {
    return appContext;
  }

  public DatabaseDriverAndroid getDbDriver() {
    if (dbDriver == null) {
      dbDriver = new DatabaseDriverAndroid(appContext);
    }
    return dbDriver;
  }

  public DatabaseSelectHelper getDatabaseSelectHelper() {
    return DatabaseSelectHelper.getInstance(getDbDriver());
  }

  public DatabaseInsertHelper getDatabaseInsertHelper() {
    return DatabaseInsertHelper.getInstance(getDbDriver());
  }

  public DatabaseUpdateHelper getDatabaseUpdateHelper() {
    return DatabaseUpdateHelper.getInstance(getDbDriver());
  }

  public DatabaseDeleteHelper getDatabaseDeleteHelper() {
    return DatabaseDeleteHelper.getInstance(getDbDriver());
  }

  @Override
  public void close() {
    if (dbDriver != null) {
      dbDriver.close();
    }
  }
}
