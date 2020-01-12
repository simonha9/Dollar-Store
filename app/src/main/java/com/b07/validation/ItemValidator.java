package com.b07.validation;

import com.b07.exceptions.ValidationFailedException;
import java.math.BigDecimal;

public class ItemValidator implements Validator {

  private BigDecimal price;
  private String name;

  /**
   * Initializes with name and price for ItemValidator.
   */
  public ItemValidator(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  /**
   * Validates name and price of item.
   */
  @Override
  public void validate() throws ValidationFailedException {
    StringBuilder sb = new StringBuilder();
    try {
      Validator priceValidator = new ItemPriceValidator(price);
      priceValidator.validate();
    } catch (ValidationFailedException e) {
      sb.append(e.getMessage());
    }
    try {
      Validator nameValidator = new ItemNameValidator(name);
      nameValidator.validate();
    } catch (ValidationFailedException e) {
      sb.append(e.getMessage());
    }
    if (sb.length() > 0) {
      throw new ValidationFailedException(sb.toString());

    }
  }

}
