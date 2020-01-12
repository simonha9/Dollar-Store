package com.b07.validation;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.exceptions.ValidationFailedException;

public class InventoryValidator extends AbstractValidator implements Validator {

  private int itemId;
  private int quantity;

  /**
   * Initializes itemId and quantity for InventoryValidator.
   */
  public InventoryValidator(DatabaseDriverAndroid dbDriver, int itemId, int quantity) {
    super(dbDriver);
    this.itemId = itemId;
    this.quantity = quantity;
  }

  /**
   * Validates inventory fields as specified in handout.
   */
  @Override
  public void validate() throws ValidationFailedException {
    StringBuilder sb = new StringBuilder();
    try {
      if (quantity < 0) {
        throw new ValidationFailedException("Inventory quantity must be >= 0.");
      }
      Validator itemValidator = new ItemExistenceValidator(getDbDriver(), itemId);
      itemValidator.validate();
    } catch (ValidationFailedException e) {
      sb.append(e.getMessage());
    }
    if (sb.length() > 0) {
      throw new ValidationFailedException(sb.toString());
    }
  }

}
