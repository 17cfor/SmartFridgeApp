package com.example.smartfridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FridgeActivity extends AppCompatActivity {
    private Button back;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        back = findViewById(R.id.back);
        listView  = findViewById(R.id.listView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FridgeActivity.this, MainActivity.class));
            }
        });


        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.fridge_item, list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Fridge");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot :dataSnapshot.child("food").getChildren()){
                    list.add(snapshot.getKey().substring(0, 1).toUpperCase() + snapshot.getKey().substring(1)  + " : " + snapshot.getValue().toString());
                    for (DataSnapshot snapshot2 :dataSnapshot.child("non_food").getChildren()){
                        Toast.makeText(FridgeActivity.this, "Warning: there is a phone in the fridge", Toast.LENGTH_SHORT).show();
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

           }

        });

    }
}