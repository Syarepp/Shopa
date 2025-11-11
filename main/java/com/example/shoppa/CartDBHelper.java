package com.example.shoppa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class CartDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shoppa_cart.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_CART = "cart";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WATCH_NAME = "watch_name";
    public static final String COLUMN_WATCH_PRICE = "watch_price";
    public static final String COLUMN_WATCH_DESCRIPTION = "watch_description";
    public static final String COLUMN_WATCH_IMAGE = "watch_image";
    public static final String COLUMN_QUANTITY = "quantity";

    public CartDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WATCH_NAME + " TEXT NOT NULL, " +
                COLUMN_WATCH_PRICE + " REAL NOT NULL, " +
                COLUMN_WATCH_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_WATCH_IMAGE + " INTEGER NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER DEFAULT 1)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public long addToCart(String name, double price, String description, int imageRes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WATCH_NAME, name);
        values.put(COLUMN_WATCH_PRICE, price);
        values.put(COLUMN_WATCH_DESCRIPTION, description);
        values.put(COLUMN_WATCH_IMAGE, imageRes);
        values.put(COLUMN_QUANTITY, 1);

        return db.insert(TABLE_CART, null, values);
    }

    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WATCH_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WATCH_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WATCH_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WATCH_IMAGE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))
                );
                cartItems.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return cartItems;
    }

    public void removeFromCart(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_ID + " = ?", new String[]{String.valueOf(itemId)});
    }

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
    }
}