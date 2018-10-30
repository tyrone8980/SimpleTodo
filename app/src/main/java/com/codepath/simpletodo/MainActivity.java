package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setTitle("                             Inscribir");
        //myToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        readItems();
        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemAdapter);

        //items.add("First item");
        //items.add("Second item");

        setupListViewListener();


    }
    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(),"Item Added to List", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener()
    {
        Log.i("MainActivity", "Setting Up Listener on List view");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("MainActivity", "Item Removed from list: " + position);
                items.remove(position);
                itemAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
    private File getDataFile()
    {
        return new File(getFilesDir(), "todo.txt");
    }
    private void readItems()
    {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error Reading File", e);
            items = new ArrayList<>();


        }

    }
    private void writeItems()
    {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error Writing File", e);
        }
    }
}
