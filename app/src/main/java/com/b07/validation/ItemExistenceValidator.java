package com.b07.validation;


import android.database.SQLException;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseSelectHelper;
import com.b07.domain.Item;
import com.b07.exceptions.ValidationFailedException;

public class ItemExistenceValidator extends AbstractValidator implements Validator {

  private int itemId;

  /**
   * Initializes itemId for ItemExistenceValidator.
   */
  public ItemExistenceValidator(DatabaseDriverAndroid dbDriver, int itemId) {
    super((dbDriver));
    this.itemId = itemId;
  }

  /**
   * Validates itemId is from database.
   */
  @Override
  public void validate() throws ValidationFailedException {
    try {
      Item item = DatabaseSelectHelper.getInstance(getDbDriver()).getItem(itemId);
      if (item == null) {
        throw new ValidationFailedException("Item id, " + itemId + " not found. ");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ValidationFailedException(e.getMessage());
    }

  }

}
