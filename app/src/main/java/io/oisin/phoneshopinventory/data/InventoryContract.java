package io.oisin.phoneshopinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class InventoryContract {

    public static final String CONTENT_AUTHORITY = "io.oisin.phoneshopinventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    public static abstract class InventoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_PRODUCT_PRICE = "productPrice";
        public static final String COLUMN_PRODUCT_QUANTITY = "productQuantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplierName";
        public static final String COLUMN_SUPPLIER_PHONE = "supplierPhone";


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
    }
}
