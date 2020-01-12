package com.b07.ui.db;

import com.b07.ui.BaseView;

public interface DbActivityView extends BaseView {

  void showProgressBar(boolean flag);

  void showMessage(String msg);
}
