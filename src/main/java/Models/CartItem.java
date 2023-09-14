/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;

/**
 *
 * @author Hung
 */
public class CartItem {

  private int cartItemID;
  private int cartID;
  private Food food;
  private int foodQuantity;

    public CartItem() {
    }

    public CartItem(int cartItemID, int cartID, Food food, int foodQuantity) {
        this.cartItemID = cartItemID;
        this.cartID = cartID;
        this.food = food;
        this.foodQuantity = foodQuantity;
    }

    public CartItem(Food food, int foodQuantity) {
        this.food = food;
        this.foodQuantity = foodQuantity;
    }

   
    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    @Override
    public String toString() {
        return "CartItem{" + "cartItemID=" + cartItemID + ", cartID=" + cartID + ", food=" + food + ", foodQuantity=" + foodQuantity + '}';
    }

  
}
