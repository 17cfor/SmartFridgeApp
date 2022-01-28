package com.example.smartfridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity {
    private Button addItem;
    private Spinner dropDown;
    private ListView listView;
    List<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        addItem = (Button) findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        dropDown = findViewById(R.id.spinner);

        myList = new ArrayList<>();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(GroceryListActivity.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.groceries));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(myAdapter);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = dropDown.getSelectedItem().toString();
                if(txt_name.isEmpty()){
                    Toast.makeText(GroceryListActivity.this,"No Item Entered", Toast.LENGTH_SHORT).show();
                }else{
                    myList.add(txt_name);
                    myList.add(String.valueOf(1));
                    FirebaseDatabase.getInstance().getReference().child("Grocery List").push().setValue(myList);
                    myList.clear();
                }
            }
        });

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Grocery List");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }
}