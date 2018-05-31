package com.example.erik.spellcasters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SpellBookActivity extends AppCompatActivity {
    ListView listView ;
    String[] list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_book);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.pickSpellToolBar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Pick Spell");
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        String spellbook = SharedData.player.availableSpells;
        String[] spells = spellbook.split(",");
        if(!SharedData.player.forcedSpell.equals(""))
        {
            spells = new String[]{SharedData.player.forcedSpell};
        }
       list = spells;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };;


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Intent myIntent = new Intent(SpellBookActivity.this, SpellPageActivity.class);
               // for(String string:list)
                //{System.out.println(string);}
                myIntent.putExtra("list",list); //Optional parameters
                myIntent.putExtra("spell",itemPosition); //Optional parameters
                myIntent.putExtra("spellName",list[itemPosition]); //Optional parameters
                SpellBookActivity.this.startActivityForResult(myIntent,2);
                finish();

        }});
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                Intent intent = new Intent();
                // intent.putExtra("edittextvalue","value_here");
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}