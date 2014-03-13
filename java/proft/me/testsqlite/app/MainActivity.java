package proft.me.testsqlite.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {
    ItemDBHelper db;
    ItemAdapter itemAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ItemDBHelper(this);
        itemAdapter = new ItemAdapter(this);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemAdapter = new ItemAdapter(this);
        lvItems.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Item _item = new Item();
        switch (item.getItemId()) {
            case R.id.menu_create:
                Random ran = new Random();
                int num = ran.nextInt(100);

                _item = new Item("item" + String.valueOf(num));
                db.insertItem(_item);

                itemAdapter.addItem(_item);
                itemAdapter.notifyDataSetChanged();
                break;

            case R.id.menu_delete:
                if (itemAdapter.getCount() > 0) {
                    int pos = itemAdapter.getCount() - 1;
                    _item = itemAdapter.getItem(pos);
                    db.deleteItem(_item);
                    itemAdapter.removeItem(pos);
                    itemAdapter.notifyDataSetChanged();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
