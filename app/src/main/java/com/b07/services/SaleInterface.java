package com.b07.services;

import android.content.Context;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import java.math.BigDecimal;

public class SaleInterface extends BaseService {


  public SaleInterface(Context appContext) {
    super(appContext);
  }

  /**
   * Creates itemized sale.
   */
  public void createItemizedSale(int saleId, int itemId, int quantity)
      throws DatabaseInsertException, ValidationFailedException {
    getDatabaseInsertHelper().insertItemizedSale(saleId, itemId, quantity);
  }

  /**
   * Creates sale.
   *
   * @return saleId
   */
  public int createSale(int custId, BigDecimal totalPriceAfterTax)
      throws DatabaseInsertException, ValidationFailedException {
    return getDatabaseInsertHelper().insertSale(custId, totalPriceAfterTax);
  }

}
