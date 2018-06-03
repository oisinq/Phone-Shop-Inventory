package io.oisin.phoneshopinventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.oisin.phoneshopinventory.data.InventoryContract.InventoryEntry;
import io.oisin.phoneshopinventory.data.InventoryDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    InventoryDbHelper mDbHelper;
    static final int URL_LOADER = 0;

    InventoryAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton actionButton = findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new InventoryDbHelper(this);

        ListView productListView = findViewById(R.id.list);

        productAdapter = new InventoryAdapter(this ,null);
        productListView.setAdapter(productAdapter);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri content = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                intent.setData(content);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(URL_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertProduct();
                return true;
            case R.id.action_delete_all_entries:
                getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertProduct() {
        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "iPhone 7");
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, 5);
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, 599.99);
        values.put(InventoryEntry.COLUMN_SUPPLIER_NAME, "Apple Inc");
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE, "+1 45345 313 5353");

        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle bundle) {
        String [] projection = { InventoryEntry._ID, InventoryEntry.COLUMN_PRODUCT_NAME, InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_PRODUCT_PRICE, InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryEntry.COLUMN_SUPPLIER_PHONE };

        return new CursorLoader(this, InventoryEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        productAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productAdapter.swapCursor(null);
    }
}
