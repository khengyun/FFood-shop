/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
import java.math.BigInteger;
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
        if (checkExist(newItem.getFood().getFoodID())) {
            CartItem olditem = getItemById(newItem.getFood().getFoodID());
            olditem.setFoodQuantity(olditem.getFoodQuantity() + newItem.getFoodQuantity());
        } else {
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
