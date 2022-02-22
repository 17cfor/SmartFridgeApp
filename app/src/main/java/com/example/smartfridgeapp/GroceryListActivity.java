package com.example.smartfridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GroceryListActivity extends AppCompatActivity {

    private EditText onion;
    private EditText carrot;
    private EditText orange;
    private EditText apple;
    private EditText lettuce;

    private Button updateGrocery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        onion = findViewById(R.id.numOnion);
        carrot = findViewById(R.id.numCarrot);
        orange = findViewById(R.id.numOrange);
        apple = findViewById(R.id.numApple);
        lettuce = findViewById(R.id.numLettuce);

        updateGrocery = findViewById(R.id.updateGrocery);

        DatabaseReference g_reference = FirebaseDatabase.getInstance().getReference().child("Grocery List");
        DatabaseReference f_reference = FirebaseDatabase.getInstance().getReference().child("Fridge");

        int[] groceryNumbers = {0,0,0,0,0}; //there needs to be a zero for each item being tracked

        HashMap<String, String> groceryList = new HashMap<String, String>();
        HashMap<String, String> fridgeList = new HashMap<String, String>();

        g_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // on update button click, get new values for item numbers
                updateGrocery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // get inputs and convert to int
                        groceryNumbers[0] = Integer.parseInt(onion.getText().toString());
                        groceryNumbers[1] = Integer.parseInt(carrot.getText().toString());
                        groceryNumbers[2] = Integer.parseInt(orange.getText().toString());
                        groceryNumbers[3] = Integer.parseInt(apple.getText().toString());
                        groceryNumbers[4] = Integer.parseInt(lettuce.getText().toString());

                        int i = 0;
                        for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                            //String username = dataSnapshot.child("item").getValue(String.class);
                            //for each item in the list, update number
                            g_reference.child(snapshot.getKey()).child("number").setValue(groceryNumbers[i]);
                            groceryList.put(String.valueOf(snapshot.child("item").getValue()),String.valueOf(groceryNumbers[i]));
                           // Log.d("GLIST", String.valueOf(snapshot.child("item").getValue()));
                            //Log.d("GLIST", String.valueOf(groceryNumbers[i]));
                            //reference.child(snapshot.getKey()).child("number").getKey();
                            i += 1;
                        }
                        compareLists(groceryList, fridgeList);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        f_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),"fridge update",Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Log.d("Fridge", String.valueOf(snapshot.child("item").getValue()));
                    fridgeList.put(String.valueOf(snapshot.child("item").getValue()),String.valueOf(snapshot.child("number").getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

      //  DatabaseReference fridge_reference = FirebaseDatabase.getInstance().getReference().child("Fridge");
      //  fridge_reference.addValueEventListener(new ValueEventListener() {
      //      @Override
      //      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
      //          for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                   //maybe call function here

                   // fridge_reference.child(snapshot.getKey()).child();
                }
    public void compareLists(HashMap<String, String> groceryList, HashMap<String, String> fridgeList)
    {
        //Toast.makeText(getApplicationContext(),groceryList.get("apple"),Toast.LENGTH_SHORT).show();

        HashMap<String, Integer> difference = new HashMap<String, Integer>();

        for(HashMap.Entry<String, String> gEntry : groceryList.entrySet())
        {
           // Log.d("G Key", gEntry.getKey());
            //Log.d("G Val", gEntry.getValue());

            for(HashMap.Entry<String, String> fEntry : fridgeList.entrySet())
            {
              //  Log.d("F Key", fEntry.getKey());
               // Log.d("F Val", fEntry.getValue());

                if(gEntry.getKey().equals(fEntry.getKey())) // if there is an item in both fridge and grocery list
                {
                    //Log.d("Tag", gEntry.getValue());
                    difference.put(gEntry.getKey(), (Integer.parseInt(gEntry.getValue()) - Integer.parseInt(fEntry.getValue()) ));

                    //Integer.parseInt(onion.getText().toString());
                }
            }
            //String key = entry.getKey();
            //String value = entry.getValue();
        }

        for (HashMap.Entry<String, Integer> dE : difference.entrySet())
        {
            Log.d("GroceryListActivity", dE.toString());
        }

    }




}
          //  @Override
        //    public void onCancelled(@NonNull DatabaseError error) {
        //    }
     //   });

   // }

