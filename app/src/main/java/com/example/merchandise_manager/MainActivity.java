// Ratchanon Bualeesonsakun 5910401149 Section 2
// ratchanon.bua@ku.th
package com.example.merchandise_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CRUDOperator operator;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    private ArrayList<Merchandise> merchandises = new ArrayList<>();
    private ArrayList<String> merchKey = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.operator = new CRUDOperator();
        setRecyclerView();
        readMerchandiseDataList();
    }

    public void createMerchandiseData(View view) {
        EditText nameInput = findViewById(R.id.merchandise_name);
        EditText priceInput = findViewById(R.id.merchandise_price);
        String nameText = nameInput.getText().toString();
        String priceText = priceInput.getText().toString();
        if (isInputValid(nameText, priceText)) {
            String validDouble = df.format(Double.parseDouble(priceText));
            operator.createDataToDatabase(new Merchandise(nameText, Double.parseDouble(validDouble)));
            nameInput.getText().clear();
            priceInput.getText().clear();
        } else {
            Toast.makeText(MainActivity.this, "Input error. Try again.", Toast.LENGTH_LONG).show();
        }
    }

    private void readMerchandiseDataList() {
        DatabaseReference reference = operator.readDataFromDatabase();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                merchandises.clear();
                merchKey.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Merchandise object = snapshot.getValue(Merchandise.class);
                    String objKey = snapshot.getKey();
                    merchandises.add(object);
                    merchKey.add(objKey);
                }
                adapter = new RecyclerAdapter(MainActivity.this, merchandises, merchKey, operator);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("errorGetData", String.valueOf(databaseError.toException()));
            }
        });
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.datalist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private boolean isInputValid(String name, String price) {
        if (name.equals("") || price.equals("")) {
            return false;
        } else {
            try {
                Double.parseDouble(price);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
