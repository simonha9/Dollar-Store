package com.b07.salesapplication;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseInsertHelper;
import com.b07.domain.Item;
import com.b07.domain.impl.ItemImpl;
import com.b07.enums.Roles;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValidationFailedException;
import com.b07.services.AccountInterface;
import com.b07.services.ShoppingCart;
import com.b07.users.Customer;
import com.b07.users.UserFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ShoppingCartTest {

    private Customer customer = null;
    private ShoppingCart cart = null;
    private Context appContext;
    private DatabaseInsertHelper insertHelper = null;

    @Before
    public void init() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        insertHelper = DatabaseInsertHelper.getInstance(new DatabaseDriverAndroid(appContext));
        customer = (Customer) UserFactory.buildUser(Roles.CUSTOMER.toString());
        int custId = insertHelper.insertNewUser("name", 123, "address", "password");
        customer.setAuthenticated(true);
        customer.setId(custId);
        cart = new ShoppingCart(appContext, customer);
        AccountInterface accountInterface = new AccountInterface(appContext, customer);
        int accountId = accountInterface.createAccount();
        customer.setAccountId(accountId);
    }

    @Test
    public void testSave() throws ValidationFailedException, SQLException, DatabaseInsertException {
        Item item2 = new ItemImpl();
        item2.setPrice(new BigDecimal("9"));
        item2.setId(1);
        cart.addItem(item2, 6);
        cart.save();
    }

}
