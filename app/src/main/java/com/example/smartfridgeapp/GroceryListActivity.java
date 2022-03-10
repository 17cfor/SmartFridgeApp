package com.example.smartfridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GroceryListActivity extends AppCompatActivity {

    private EditText apple;
    private EditText banana;
    private EditText carrot;
    private EditText orange;
    private EditText tomato;

    private Button updateGrocery;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        apple = (EditText)findViewById(R.id.numApple);
        banana = (EditText)findViewById(R.id.numBanana);
        carrot = (EditText)findViewById(R.id.numCarrot);
        orange = (EditText)findViewById(R.id.numOrange);
        tomato = (EditText)findViewById(R.id.numTomato);

        updateGrocery = findViewById(R.id.updateGrocery);

        listView  = findViewById(R.id.list_diff);

        DatabaseReference g_reference = FirebaseDatabase.getInstance().getReference().child("Grocery List");
        DatabaseReference f_reference = FirebaseDatabase.getInstance().getReference().child("Fridge");
       // DatabaseReference d_reference = FirebaseDatabase.getInstance().getReference().child("Difference");

        int[] groceryNumbers = {0,0,0,0,0}; //there needs to be a zero for each item being tracked

        Map<String, String> groceryList = new TreeMap<String, String>();
        Map<String, String> fridgeList = new TreeMap<String, String>();

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.diff_item, list);
        listView.setAdapter(adapter);

        g_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateList(dataSnapshot);

                // on update button click, get new values for item numbers
                updateGrocery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.clear();
                        // get inputs and convert to int
                        groceryNumbers[0] = Integer.parseInt(apple.getText().toString());
                        groceryNumbers[1] = Integer.parseInt(banana.getText().toString());
                        groceryNumbers[2] = Integer.parseInt(carrot.getText().toString());
                        groceryNumbers[3] = Integer.parseInt(orange.getText().toString());
                        groceryNumbers[4] = Integer.parseInt(tomato.getText().toString());

                        int i = 0;
                        for (DataSnapshot snapshot :dataSnapshot.getChildren()){

                            g_reference.child(snapshot.getKey()).setValue(String.valueOf(groceryNumbers[i]));
                            groceryList.put(String.valueOf(snapshot.getKey()),String.valueOf(groceryNumbers[i]));
                            i += 1;
                        }
                        updateList(dataSnapshot);
                        //compareLists(groceryList, fridgeList);
                        Map<String, String> difference =  compareLists(groceryList, fridgeList);

                        for(Map.Entry<String, String> dEntry : difference.entrySet())
                        {
                            //gEntry.getKey());
                           // Log.d("G Val", String.valueOf(dEntry.getValue()));
                            list.add(dEntry.getKey() + " : " + dEntry.getValue().toString());
                        }

                        adapter.notifyDataSetChanged();
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
                for (DataSnapshot snapshot :dataSnapshot.child("food").getChildren()){
                    fridgeList.put(String.valueOf(snapshot.getKey()), String.valueOf(snapshot.getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }
    public void updateList(DataSnapshot dataSnapshot)
    {
        apple.setText(dataSnapshot.child("apple").getValue(String.class));
        banana.setText(dataSnapshot.child("banana").getValue(String.class));
        carrot.setText(dataSnapshot.child("carrot").getValue(String.class));
        orange.setText(dataSnapshot.child("orange").getValue(String.class));
        tomato.setText(dataSnapshot.child("tomato").getValue(String.class));
    }

    public Map<String, String> compareLists(Map<String, String> groceryList, Map<String, String> fridgeList)
    {
        Map<String, String> difference = new TreeMap<String, String>();
        int temp;
        for(Map.Entry<String, String> gEntry : groceryList.entrySet())
        {
            // Log.d("G Key", gEntry.getKey());
             //Log.d("G Val", gEntry.getValue());

            for(Map.Entry<String, String> fEntry : fridgeList.entrySet())
            {
                if(gEntry.getKey().equals(fEntry.getKey())) // if there is an item in both fridge and grocery list
                {
                    //Log.d("Tag", gEntry.getValue());
                    //return str.substring(0, 1).toUpperCase() + str.substring(1);
                    temp = Integer.parseInt(gEntry.getValue()) - Integer.parseInt(fEntry.getValue());
                   if( temp > 0 )
                    {
                        difference.put(gEntry.getKey().substring(0, 1).toUpperCase() + gEntry.getKey().substring(1), String.valueOf(temp));
                    }
                   else
                       {
                        difference.put(gEntry.getKey().substring(0, 1).toUpperCase() + gEntry.getKey().substring(1), String.valueOf(0));
                   }

                    //list.add(gEntry.getKey(), (Integer.parseInt(gEntry.getValue()) - Integer.parseInt(fEntry.getValue()) ));
                    //Integer.parseInt(onion.getText().toString());
                }
            }
        }

        return difference;
    }

    }




