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

    private EditText apple;
    private EditText banana;
    private EditText carrot;
    private EditText orange;
    private EditText tomato;


    private Button updateGrocery;

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

        DatabaseReference g_reference = FirebaseDatabase.getInstance().getReference().child("Grocery List");
        DatabaseReference f_reference = FirebaseDatabase.getInstance().getReference().child("Fridge");


        int[] groceryNumbers = {0,0,0,0,0}; //there needs to be a zero for each item being tracked

        HashMap<String, String> groceryList = new HashMap<String, String>();
        HashMap<String, String> fridgeList = new HashMap<String, String>();

        g_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateList(dataSnapshot);

                // on update button click, get new values for item numbers
                updateGrocery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // get inputs and convert to int
                        groceryNumbers[0] = Integer.parseInt(apple.getText().toString());
                        groceryNumbers[1] = Integer.parseInt(banana.getText().toString());
                        groceryNumbers[2] = Integer.parseInt(carrot.getText().toString());
                        groceryNumbers[3] = Integer.parseInt(orange.getText().toString());
                        groceryNumbers[4] = Integer.parseInt(tomato.getText().toString());

                        int i = 0;
                        for (DataSnapshot snapshot :dataSnapshot.getChildren()){

                            g_reference.child(snapshot.getKey()).setValue(String.valueOf(groceryNumbers[i]));


                            i += 1;
                        }
                        updateList(dataSnapshot);

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

    }




