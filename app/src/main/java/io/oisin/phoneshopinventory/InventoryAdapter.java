package io.oisin.phoneshopinventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import io.oisin.phoneshopinventory.data.InventoryContract.InventoryEntry;

public class InventoryAdapter extends CursorAdapter {

    public InventoryAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameView = view.findViewById(R.id.name_text_view);
        TextView priceView = view.findViewById(R.id.price_text_view);
        TextView quantityView = view.findViewById(R.id.quantityAmount);
        Button button = view.findViewById(R.id.sale_button);
        final long productId = cursor.getLong(cursor.getColumnIndexOrThrow(InventoryEntry._ID));

        int productNameIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
        int quantityIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);

        String productName = cursor.getString(productNameIndex);
        String price = cursor.getString(priceColumnIndex);
        final int quantity = cursor.getInt(quantityIndex);

        nameView.setText(productName);
        priceView.setText(price);
        quantityView.setText(Integer.toString(quantity));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    int newQuantity = quantity - 1;
                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, newQuantity);
                    Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, productId);

                    context.getContentResolver().update(currentProductUri, values, null, null);
                }
            }
        });
    }
}