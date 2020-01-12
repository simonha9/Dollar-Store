package com.b07.ui.admin;

import android.content.Context;
import com.b07.domain.SalesLog;
import com.b07.services.AdminInterface;

public class SalesLogModel {

  private Context context;

  public SalesLogModel(Context context) {
    this.context = context;
  }

  public SalesLog getSales() {
    AdminInterface adminInterface = new AdminInterface(context);
    return adminInterface.getSales();
  }
}
