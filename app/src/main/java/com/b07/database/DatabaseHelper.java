package com.b07.database;

public class DatabaseHelper {

  public static final String DATABASE_NAME = "inventorymgmt.db";
  public static final String SERIALIZED_DATABASE_NAME = "database_copy.ser";

  protected DatabaseDriverAndroid dbDriver;

  public DatabaseHelper(DatabaseDriverAndroid dbDriver) {
    this.dbDriver = dbDriver;
  }
}
