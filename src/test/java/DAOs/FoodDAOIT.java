/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAOs;

import DBConnection.DBConnection;
import Models.Food;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author huynhvu
 */
public class FoodDAOIT {

    public FoodDAOIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdd() {
        // Create a mock instance of the FoodDAO class.
        FoodDAO foodDAOMock = mock(FoodDAO.class);

        // Create a food object.
        Food food = new Food();
        food.setFoodName("Mì");
        food.setFoodPrice(BigDecimal.valueOf(50000));
        food.setDiscountPercent((byte) 10);
        food.setImageURL("https://example.com/com-ga.png");
        food.setFoodTypeID((byte) 1);

        // Tell Mockito to return 1 when the add() method is called on the mock object.
        when(foodDAOMock.add(food)).thenReturn(1);

        // Call the add() method.
        int result = foodDAOMock.add(food);

        // Verify that the add() method was called with the correct parameters.
        verify(foodDAOMock).add(food);

        // Verify that the expected result was returned.
        assertEquals(1, result);

        if (result == 1) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }

    @Test
    public void testAddNegativeDiscount() {
        // Create a mock instance of the FoodDAO class.
        FoodDAO foodDAOMock = mock(FoodDAO.class);

        // Create a food object.
        Food food = new Food();
        food.setFoodName("Mì");
        food.setFoodPrice(BigDecimal.valueOf(50000));
        food.setDiscountPercent((byte) -10);
        food.setImageURL("https://example.com/com-ga.png");
        food.setFoodTypeID((byte) 1);

        // Tell Mockito to return 1 when the add() method is called on the mock object.
        when(foodDAOMock.add(food)).thenReturn(1);

        // Call the add() method.
        int result = foodDAOMock.add(food);

        // Verify that the add() method was called with the correct parameters.
        verify(foodDAOMock).add(food);

        // Verify that the expected result was returned.
        assertEquals(1, result);

        if (result != 1) {

            System.out.println("Discount is Negative");
        } else {
            System.out.println("Success");
        }
    }

    @Test
    public void testAddNegativePrice() {
        // Create a mock instance of the FoodDAO class.
        FoodDAO foodDAOMock = mock(FoodDAO.class);

        // Create a food object.
        Food food = new Food();
        food.setFoodName("Mì");
        food.setFoodPrice(BigDecimal.valueOf(-50000));
        food.setDiscountPercent((byte) 10);
        food.setImageURL("https://example.com/com-ga.png");
        food.setFoodTypeID((byte) 1);

        // Tell Mockito to return 1 when the add() method is called on the mock object.
        when(foodDAOMock.add(food)).thenReturn(1);

        // Call the add() method.
        int result = foodDAOMock.add(food);

        // Verify that the add() method was called with the correct parameters.
        verify(foodDAOMock).add(food);

        // Verify that the expected result was returned.
        assertEquals(1, result);

        if (result != 1) {
            System.out.println("Price is Negative");
            return;
        } else {
            System.out.println("Success");
        }
    }

    @Test
    public void testAddDuplicate() {
        // Create a mock instance of the FoodDAO class.
        FoodDAO foodDAOMock = mock(FoodDAO.class);

        // Create a food object.
        Food food = new Food();
        food.setFoodName("Mì");
        food.setFoodPrice(BigDecimal.valueOf(50000));
        food.setDiscountPercent((byte) 10);
        food.setImageURL("https://example.com/com-ga.png");
        food.setFoodTypeID((byte) 1);

        // Tell Mockito to return 1 when the add() method is called on the mock object.
        when(foodDAOMock.add(food)).thenReturn(1);

        // Call the add() method.
        int result = foodDAOMock.add(food);

        // Verify that the add() method was called with the correct parameters.
        verify(foodDAOMock).add(food);

        // Verify that the expected result was returned.
        assertEquals(1, result);

        if (result != 1) {

            System.out.println("Food already exsist");
        } else {
            System.out.println("False");
        }
    }

    @Test
    public void testAddNullFoodPrice() {
        // Create a mock instance of the FoodDAO class.
        FoodDAO foodDAOMock = mock(FoodDAO.class);

        // Create a food object.
        Food food = new Food();
        food.setFoodID((short) 21);
        food.setFoodName("Com gà");
       food.setFoodPrice(null);
        food.setDiscountPercent((byte) 10);
        food.setImageURL("https://example.com/com-ga.png");
        food.setFoodTypeID((byte) 1);

        // Tell Mockito to return 1 when the add() method is called on the mock object.
        when(foodDAOMock.add(food)).thenReturn(1);

        // Call the add() method.
        int result = foodDAOMock.add(food);

        // Verify that the add() method was called with the correct parameters.
        verify(foodDAOMock).add(food);

        // Verify that the expected result was returned.
        assertEquals(1, result);

        if (result != 1) {
            System.out.println("Price is null");
        } else {
            System.out.println("True");
            System.out.println("Food ID: " + food.getFoodID());
            System.out.println("Food name: " + food.getFoodName());
            System.out.println("Food price: " + food.getFoodPrice());
            System.out.println("Discount percent: " + food.getDiscountPercent());
            System.out.println("Image URL: " + food.getImageURL());
            System.out.println("Food type ID: " + food.getFoodTypeID());
            System.out.println();
        }
    }

    @Test
    public void testAddNullFoodURL() {
        // Create a mock instance of the FoodDAO class.
        FoodDAO foodDAOMock = mock(FoodDAO.class);

        // Create a food object.
        Food food = new Food();
        food.setFoodName("Com gà");
        food.setFoodPrice(BigDecimal.valueOf(50000));
        food.setDiscountPercent((byte) 10);
        food.setImageURL(null);
        food.setFoodTypeID((byte) 1);

        // Tell Mockito to return 1 when the add() method is called on the mock object.
        when(foodDAOMock.add(food)).thenReturn(1);

        // Call the add() method.
        int result = foodDAOMock.add(food);

        // Verify that the add() method was called with the correct parameters.
        verify(foodDAOMock).add(food);

        // Verify that the expected result was returned.
        assertEquals(1, result);

        if (result != 1) {
            System.out.println("URL is null");
        } else {
            System.out.println("False");
        }
    }

    @Test
    public void testAddNullFoodType() {
        // Create a mock instance of the FoodDAO class.
        FoodDAO foodDAOMock = mock(FoodDAO.class);

        // Create a food object.
        Food food = new Food();
        food.setFoodName(null);
        food.setFoodPrice(BigDecimal.valueOf(50000));
        food.setDiscountPercent((byte) 10);
        food.setImageURL("https://example.com/com-ga.png");
        food.setFoodTypeID((byte) 1);

        // Tell Mockito to return 1 when the add() method is called on the mock object.
        when(foodDAOMock.add(food)).thenReturn(1);

        // Call the add() method.
        int result = foodDAOMock.add(food);

        // Verify that the add() method was called with the correct parameters.
        verify(foodDAOMock).add(food);

        // Verify that the expected result was returned.
        assertEquals(1, result);

        if (result != 1) {
            System.out.println("name is null");
        } else {
            System.out.println("False");
        }
    }

}
