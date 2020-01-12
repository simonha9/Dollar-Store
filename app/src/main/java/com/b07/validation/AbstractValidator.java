package com.b07.validation;

import com.b07.database.DatabaseDriverAndroid;

public abstract class AbstractValidator {

  private DatabaseDriverAndroid dbDriver;

  public AbstractValidator(DatabaseDriverAndroid dbDriver) {
    this.dbDriver = dbDriver;
  }

  public DatabaseDriverAndroid getDbDriver() {
    return dbDriver;
  }

  public void setDbDriver(DatabaseDriverAndroid dbDriver) {
    this.dbDriver = dbDriver;
  }
}
