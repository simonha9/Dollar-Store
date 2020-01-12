package com.b07.validation;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.exceptions.ValidationFailedException;
import java.math.BigDecimal;

public class SalesValidator extends AbstractValidator implements Validator {

  private int userId;
  private BigDecimal totalPrice;

  /**
   * Initializes userId and totalPrice for SalesValidator.
   */
  public SalesValidator(DatabaseDriverAndroid dbDriver, int userId, BigDecimal totalPrice) {
    super(dbDriver);
    this.userId = userId;
    this.totalPrice = totalPrice;
  }

  /**
   * Validates totalPrice is in valid format.
   */
  @Override
  public void validate() throws ValidationFailedException {
    StringBuilder sb = new StringBuilder();
    try {
      if (totalPrice == null || totalPrice.compareTo(new BigDecimal("0")) <= 0) {
        sb.append("totalPrice cannot be null and must be > 0 ");
      }
      Validator validator = new UserExistenceValidator(getDbDriver(), userId);
      validator.validate();

    } catch (ValidationFailedException e) {
      sb.append(e.getMessage());
    }
    if (sb.length() > 0) {
      throw new ValidationFailedException(sb.toString());
    }

  }

}
