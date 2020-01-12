package com.b07.ui.db;

public interface DbPresenter {

  boolean initializeDbRequired();

  void initializeDb();

  void importDb(String filename);
}
