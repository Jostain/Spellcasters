package com.example.erik.spellcasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SpellPageActivity extends AppCompatActivity {
    String[] spells;
    int position;
    TextView spellHeader;
    TextView textDescription;
    TextView deckCounter;
    TextView manaCostOctarine;
    TextView manaCostPurple;
    TextView manaCostRed;
    TextView manaCostOrange;
    TextView manaCostYellow;
    TextView manaCostGreen;
    TextView manaCostBlue;
    TextView manaCostCyan;
    Button nextButton;
    Button prevButton;
    Spell spell = new Spell();
    Button castSpellButton;
   // private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpellLibrary.initiateLibrary();
        setContentView(R.layout.activity_spell_page);
        final SpellPageActivity activity = this;
        spells = SharedData.player.availableSpells.split(",");
        if(!SharedData.player.forcedSpell.equals(""))
        {
            spells = new String[]{SharedData.player.forcedSpell};
        }
        nextButton = (Button) findViewById(R.id.nextSpell);
        prevButton = (Button) findViewById(R.id.prevSpell);
        textDescription = (TextView) findViewById(R.id.textDescription);
        deckCounter = (TextView) findViewById(R.id.deckCounter);

        manaCostOctarine = (TextView) findViewById(R.id.OctarineCost);
        manaCostPurple = (TextView) findViewById(R.id.PurpleCost);
        manaCostRed = (TextView) findViewById(R.id.RedCost);
        manaCostOrange = (TextView) findViewById(R.id.OrangeCost);
        manaCostYellow = (TextView) findViewById(R.id.YellowCost);
        manaCostGreen = (TextView) findViewById(R.id.GreenCost);
        manaCostBlue = (TextView) findViewById(R.id.BlueCost);
        manaCostCyan = (TextView) findViewById(R.id.CyanCost);

        position = getIntent().getIntExtra("spell",0);
        spellHeader = (TextView) findViewById(R.id.spellHeader);
        castSpellButton = (Button) findViewById(R.id.cast);
        spellHeader.setText(spells[position]);
        textDescription.setText(SpellLibrary.library.get(spells[position]).description);
        deckCounter.setText(position+1+"/"+spells.length);
        spell = SpellLibrary.library.get(spells[position]);
        String[] manaCostList = spell.manaCost.split(":");
        manaCostOctarine.setText( manaCostList[0]);
        manaCostPurple.setText( manaCostList[1]);
        manaCostRed.setText( manaCostList[2]);
        manaCostOrange.setText( manaCostList[3]);
        manaCostYellow.setText( manaCostList[4]);
        manaCostGreen.setText( manaCostList[5]);
        manaCostBlue.setText( manaCostList[6]);
        manaCostCyan.setText( manaCostList[7]);
        SharedData.storage = spell;
        System.out.println(SharedData.storage.spellName);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position++;
                if(spells.length-1<position)
                {position=0;}
                deckCounter.setText(position+1+"/"+spells.length);
                spell = SpellLibrary.library.get(spells[position]);
                String[] manaCostList = spell.manaCost.split(":");
                textDescription.setText(spell.description);
                spellHeader.setText(spells[position]);
                manaCostOctarine.setText( manaCostList[0]);
                manaCostPurple.setText( manaCostList[1]);
                manaCostRed.setText( manaCostList[2]);
                manaCostOrange.setText( manaCostList[3]);
                manaCostYellow.setText( manaCostList[4]);
                manaCostGreen.setText( manaCostList[5]);
                manaCostBlue.setText( manaCostList[6]);
                manaCostCyan.setText( manaCostList[7]);
                SharedData.storage = spell;


            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position==-1)
                {position=spells.length-1;}
                deckCounter.setText(position+1+"/"+spells.length);
                spell = SpellLibrary.library.get(spells[position]);
                String[] manaCostList = spell.manaCost.split(":");
                textDescription.setText(spell.description);
                spellHeader.setText(spells[position]);
                manaCostOctarine.setText( manaCostList[0]);
                manaCostPurple.setText( manaCostList[1]);
                manaCostRed.setText( manaCostList[2]);
                manaCostOrange.setText( manaCostList[3]);
                manaCostYellow.setText( manaCostList[4]);
                manaCostGreen.setText( manaCostList[5]);
                manaCostBlue.setText( manaCostList[6]);
                manaCostCyan.setText( manaCostList[7]);
                if(spells.length<position)
                {position=0;}
                SharedData.storage = spell;
            }
        });
        castSpellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String[] manaCost = spell.manaCost.split(":");
                User player = SharedData.player;
                if(Integer.parseInt(manaCost[0])>player.octarineMana||Integer.parseInt(manaCost[1])>player.purpleMana||Integer.parseInt(manaCost[2])>player.redMana||Integer.parseInt(manaCost[3])>player.orangeMana||Integer.parseInt(manaCost[4])>player.yellowMana||Integer.parseInt(manaCost[5])>player.greenMana||Integer.parseInt(manaCost[6])>player.blueMana||Integer.parseInt(manaCost[7])>player.cyanMana)
                {
                    if(SharedData.random.nextInt(1000)==1)
                    {
                        Toast.makeText(activity, "You Must Construct Additional Pylons!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(activity, "Not enough mana!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    SharedData.storage = spell;
                    Intent myIntent = new Intent(SpellPageActivity.this, targetPlayerActivity.class);
                    //  myIntent.putExtra("spell",spell); //Optional parameters

                    SpellPageActivity.this.startActivity(myIntent);
                    finish();
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

}
