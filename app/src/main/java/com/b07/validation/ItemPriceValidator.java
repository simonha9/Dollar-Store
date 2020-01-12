package com.b07.validation;

import com.b07.exceptions.ValidationFailedException;
import java.math.BigDecimal;

public class ItemPriceValidator implements Validator {

  private BigDecimal price;

  /**
   * Initializes price for ItemPriceValidator.
   */
  public ItemPriceValidator(BigDecimal price) {
    this.price = price;
  }

  /**
   * Validates price of item.
   */
  @Override
  public void validate() throws ValidationFailedException {
    StringBuilder sb = new StringBuilder();
    if (price == null || price.compareTo(new BigDecimal("0")) < 0) {
      sb.append("Price cannot be null and must be > 0");
    }
    if (sb.length() > 0) {
      throw new ValidationFailedException(sb.toString());

    }
  }

}
