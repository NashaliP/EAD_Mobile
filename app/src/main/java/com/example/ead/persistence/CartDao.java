package com.example.ead.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCartItem(CartItem cartItem);

    @Query("SELECT * FROM cart_items")
    List<CartItem> getCartItems();

    @Query("DELETE FROM cart_items")
    void clearCart();

    @Update
    void updateCartItem(CartItem cartItem); // Method to update a cart item

    @Delete
    void deleteCartItem(CartItem cartItem); // Method to delete a cart item
}
