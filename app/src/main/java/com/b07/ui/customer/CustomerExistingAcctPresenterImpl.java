package com.b07.ui.customer;

import com.b07.domain.Item;
import com.b07.ui.BaseView;
import java.util.List;
import java.util.Map;

public class CustomerExistingAcctPresenterImpl implements CustomerExistingAcctPresenter {

  private CustomerExistingAcctModel model;
  private BaseView view;

  public CustomerExistingAcctPresenterImpl(BaseView view) {
    this.view = view;
    model = new CustomerExistingAcctModel(view.getApplicationContext());
  }

  @Override
  public List<Integer> getActiveAccounts(int userId) {
    return model.getActiveAccounts(userId);
  }

  @Override
  public Map<Item, Integer> getSelectedAccount(int accountId) {
    return model.getSelectedAccount(accountId);
  }
}
