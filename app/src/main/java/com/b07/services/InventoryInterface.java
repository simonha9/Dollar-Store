package com.b07.services;

import android.content.Context;
import android.database.SQLException;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.domain.Inventory;
import com.b07.domain.Item;
import com.b07.domain.impl.ItemImpl;
import com.b07.enums.ItemTypes;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.InsufficientItemException;
import com.b07.exceptions.ValidationFailedException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class InventoryInterface extends BaseService {

  public static final int NUM_FISHING_ROD = 10;
  public static final BigDecimal PRICE_FISHING_ROD = new BigDecimal("5");
  public static final int NUM_HOCKEY_STICK = 10;
  public static final BigDecimal PRICE_HOCKEY_STICK = new BigDecimal("6");
  public static final int NUM_SKATES = 10;
  public static final BigDecimal PRICE_SKATES = new BigDecimal("5");
  public static final int NUM_RUNNING_SHOES = 10;
  public static final BigDecimal PRICE_RUNNING_SHOES = new BigDecimal("9");
  public static final int NUM_PROTEIN_BAR = 10;
  public static final BigDecimal PRICE_PROTEIN_BAR = new BigDecimal("2");

  /**
   * Constructor for InventoryInterface.
   */
  public InventoryInterface(Context appContext) {
    super(appContext);
  }

  /**
   * Gets current inventory.
   *
   * @return inventory
   */
  public Inventory getCurrentInventory() throws SQLException {
    Inventory inventory = getDatabaseSelectHelper().getInventory();
    return inventory;
  }

  /**
   * Initializes interface.
   */
  public void initializeInventory() throws DatabaseInsertException, SQLException,
      ValidationFailedException {
    System.out.println("\nInitializing Inventory");

    List<Item> itemList = getDatabaseSelectHelper().getAllItems();
    if (DatabaseSelectHelper.lookupItemByName(itemList, ItemTypes.FISHING_ROD.toString()) == null) {
      initializeFishingRods();
    }
    if (DatabaseSelectHelper.lookupItemByName(itemList, ItemTypes.HOCKEY_STICK.toString())
        == null) {
      initializeHockeySticks();
    }
    if (DatabaseSelectHelper.lookupItemByName(itemList, ItemTypes.SKATES.toString()) == null) {
      initializeSkates();
    }
    if (DatabaseSelectHelper.lookupItemByName(itemList, ItemTypes.RUNNING_SHOES.toString())
        == null) {
      initializeRunningShoes();
    }
    if (DatabaseSelectHelper.lookupItemByName(itemList, ItemTypes.PROTEIN_BAR.toString()) == null) {
      initializeProteinBars();
    }
  }

  /**
   * Initializes fishing rod item.
   */
  private void initializeFishingRods() throws DatabaseInsertException, SQLException,
      ValidationFailedException {
    Item fishingRod = new ItemImpl(ItemTypes.FISHING_ROD.toString(), PRICE_FISHING_ROD);
    DatabaseInsertHelper helper = getDatabaseInsertHelper();
    int fishingRodId = helper.insertItem(ItemTypes.FISHING_ROD.toString(),
        PRICE_FISHING_ROD);
    fishingRod.setId(fishingRodId);
    helper.insertInventory(fishingRodId, NUM_FISHING_ROD);
    getCurrentInventory().updateMap(fishingRod, NUM_FISHING_ROD);
    System.out.println("Fishing rods, itemId: " + fishingRodId + " quantity: " + NUM_FISHING_ROD);
  }

  /**
   * Initializes hockey stick item.
   */
  private void initializeHockeySticks() throws DatabaseInsertException, SQLException,
      ValidationFailedException {
    Item hockeyStick = new ItemImpl(ItemTypes.HOCKEY_STICK.toString(), PRICE_HOCKEY_STICK);
    DatabaseInsertHelper helper = getDatabaseInsertHelper();
    int hockeyStickId = helper.insertItem(ItemTypes.HOCKEY_STICK.toString(),
        PRICE_HOCKEY_STICK);
    hockeyStick.setId(hockeyStickId);
    helper.insertInventory(hockeyStickId, NUM_HOCKEY_STICK);
    getCurrentInventory().updateMap(hockeyStick, NUM_HOCKEY_STICK);
    System.out.println("Hockey Sticks, itemId: " + hockeyStickId + " quantity: "
        + NUM_HOCKEY_STICK);
  }

  /**
   * Initializes skates item.
   */
  private void initializeSkates() throws DatabaseInsertException, SQLException,
      ValidationFailedException {
    Item skates = new ItemImpl(ItemTypes.SKATES.toString(), PRICE_SKATES);
    DatabaseInsertHelper helper = getDatabaseInsertHelper();
    int skatesId = helper.insertItem(ItemTypes.SKATES.toString(), PRICE_SKATES);
    skates.setId(skatesId);
    helper.insertInventory(skatesId, NUM_SKATES);
    getCurrentInventory().updateMap(skates, NUM_SKATES);
    System.out.println("Skates, itemId: " + skatesId + " quantity: " + NUM_SKATES);
  }

  /**
   * Initializes running shoes item.
   */
  private void initializeRunningShoes() throws DatabaseInsertException, SQLException,
      ValidationFailedException {
    Item runningShoes = new ItemImpl(ItemTypes.RUNNING_SHOES.toString(), PRICE_RUNNING_SHOES);
    DatabaseInsertHelper helper = getDatabaseInsertHelper();
    int runningShoesId = helper.insertItem(ItemTypes.RUNNING_SHOES.toString(),
        PRICE_RUNNING_SHOES);
    runningShoes.setId(runningShoesId);
    helper.insertInventory(runningShoesId, NUM_RUNNING_SHOES);
    getCurrentInventory().updateMap(runningShoes, NUM_RUNNING_SHOES);
    System.out.println("Running Shoes, itemId: " + runningShoesId + " quantity: "
        + NUM_RUNNING_SHOES);
  }

  /**
   * Initializes protein bars item.
   */
  private void initializeProteinBars() throws DatabaseInsertException, SQLException,
      ValidationFailedException {
    Item proteinBar = new ItemImpl(ItemTypes.PROTEIN_BAR.toString(), PRICE_PROTEIN_BAR);
    DatabaseInsertHelper helper = getDatabaseInsertHelper();
    int proteinBarId = helper.insertItem(ItemTypes.PROTEIN_BAR.toString(),
        PRICE_PROTEIN_BAR);
    proteinBar.setId(proteinBarId);
    helper.insertInventory(proteinBarId, NUM_PROTEIN_BAR);
    getCurrentInventory().updateMap(proteinBar, NUM_PROTEIN_BAR);
    System.out.println("Protein Bars, itemId: " + proteinBarId + " quantity: " + NUM_PROTEIN_BAR);
  }

  /**
   * Removes item from inventory.
   *
   * @return true if inventory is removed, else false
   */
  public boolean removeInventory(Map<Item, Integer> itemMap) {
    try {
      DatabaseSelectHelper selectHelper = getDatabaseSelectHelper();
      DatabaseUpdateHelper updateHelper = getDatabaseUpdateHelper();
      for (Item item : itemMap.keySet()) {
        int existingQuantity = selectHelper.getInventoryQuantity(item.getId());
        int net = existingQuantity - itemMap.get(item);
        updateHelper.updateInventoryQuantity(net, item.getId());
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Checks if an item is in stock.
   */
  public void checkItemsInStock(Map<Item, Integer> itemMap)
      throws InsufficientItemException, ValidationFailedException {
    DatabaseSelectHelper selectHelper = getDatabaseSelectHelper();
    for (Item item : itemMap.keySet()) {
      int existingQuantity = selectHelper.getInventoryQuantity(item.getId());
      if (itemMap.get(item) > existingQuantity) {
        throw new InsufficientItemException(
            "The requested quantity for " + item.getName() + " exceeds available amount");
      }
    }
  }

}
