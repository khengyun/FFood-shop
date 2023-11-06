/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hung
 */
public class Cart {

    private int id;
    private int userId;
    private float totalPrice;
    private List<CartItem> items;

    public Cart() {
    }

    public Cart(int id, int userId, float totalPrice, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Cart(List<CartItem> items) {
        this.items = items;
    }

    public Cart(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    private CartItem getItemById(int id) {
        for (CartItem item : items) {
            if (item.getFood().getFoodID() == id) {
                return item;
            }
        }
        return null;
    }

    private boolean checkExist(int id) {
        for (CartItem item : items) {
            if (item.getFood().getFoodID() == id) {
                return true;
            }
        }
        return false;
    }

    public void addItem(CartItem newItem) {
      int stockQuantity = newItem.getFood().getQuantity();
      int quantity = newItem.getFoodQuantity();
      int maxQuantity = 5;
      int minQuantity = 1;
      
      if (checkExist(newItem.getFood().getFoodID())) {
        CartItem olditem = this.getItemById(newItem.getFood().getFoodID());
        int newQuantity = olditem.getFoodQuantity() + quantity;

        // Same as above
        if (newQuantity >= minQuantity && newQuantity <= maxQuantity && stockQuantity >= maxQuantity) {
            olditem.setFoodQuantity(newQuantity);
        } else if (newQuantity >= minQuantity && newQuantity <= maxQuantity 
              && stockQuantity >= minQuantity && stockQuantity <= maxQuantity) {
            olditem.setFoodQuantity(stockQuantity);
        } else if (newQuantity >= minQuantity && newQuantity > maxQuantity) {
            olditem.setFoodQuantity(maxQuantity);
        } else {
            olditem.setFoodQuantity(minQuantity);
        }
      } else {
        // Make sure the quantity does not exceed 10 and does not exceed the food's stock quantity
        // The range of acceptable quantity is [1, 10]. Note that the stock quantity can limit the upper range
        // There are 4 cases:
        // Case 1: quantity is within the range, and stockQuantity is out of the range
        // Case 2: quantity is within the range, but stockQuantity is within the range
        // Case 3: quantity is out of the upper range
        // Case 4: quantity is out of the lower range
        if (quantity > maxQuantity) {
            quantity = maxQuantity;
        } else if (quantity > stockQuantity) {
            quantity = stockQuantity;
        } else if (quantity < minQuantity) {
            quantity = minQuantity;
        }

        newItem.setFoodQuantity(quantity);
        items.add(newItem);
      }
    }

    public void addItemCheckout(CartItem newItem) {
        if (!checkExist(newItem.getFood().getFoodID())) {
            items.add(newItem);
        } else {
            CartItem oldItem = getItemById(newItem.getFood().getFoodID());
            oldItem.setFoodQuantity(newItem.getFoodQuantity());
        }
    }

    public void removeItem(int id) {
        if (getItemById(id) != null) {
            items.remove(getItemById(id));
        }
    }

    public double getTotalMoney() {
        double t = 0;
        for (CartItem i : items) {
            t += (i.getFoodQuantity() * Double.parseDouble(i.getFood().getFoodPrice().toString()));
        }
        return t;
    }

    @Override
    public String toString() {
        return "Cart{" + "id=" + id + ", userId=" + userId + ", totalPrice=" + totalPrice + ", items=" + items.toString() + '}';
    }

}
