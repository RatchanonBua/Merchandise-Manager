// Ratchanon Bualeesonsakun 5910401149 Section 2
// ratchanon.bua@ku.th
package com.example.merchandise_manager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

class CRUDOperator {

    // Activity Actions
    void createDataToDatabase(Merchandise merchandise) {
        DatabaseReference reference = readDataFromDatabase();
        reference.push().setValue(merchandise);
    }

    DatabaseReference readDataFromDatabase() {
        return FirebaseDatabase.getInstance().getReference("merchandises");
    }

    // Button Actions
    void updateDataToDatabase(Map<String, Object> object) {
        DatabaseReference reference = readDataFromDatabase();
        reference.updateChildren(object);
    }

    void deleteDataFromDatabase(String itemKey) {
        DatabaseReference reference = readDataFromDatabase();
        reference.child(itemKey).removeValue();
    }
}
