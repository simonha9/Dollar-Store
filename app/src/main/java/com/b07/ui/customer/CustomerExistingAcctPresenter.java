package com.b07.ui.customer;

import com.b07.domain.Item;
import java.util.List;
import java.util.Map;

public interface CustomerExistingAcctPresenter {

  List<Integer> getActiveAccounts(int userId);

  Map<Item, Integer> getSelectedAccount(int accountId);

}
