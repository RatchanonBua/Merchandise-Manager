// Ratchanon Bualeesonsakun 5910401149 Section 2
// ratchanon.bua@ku.th
package com.example.merchandise_manager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

class ManagerDialog {
    private Context context;
    private String merchandiseKey;
    private Merchandise merchandise;
    private CRUDOperator operator;

    ManagerDialog(Context context, String merchandiseKey, Merchandise merchandise, CRUDOperator operator) {
        this.context = context;
        this.merchandiseKey = merchandiseKey;
        this.merchandise = merchandise;
        this.operator = operator;
    }

    void showUpdateMerchandiseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Merchandise Data");
        final View view = setDataInEdittext(setBuilderView());
        builder.setView(view);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView nameView = view.findViewById(R.id.editdialog_name);
                TextView priceView = view.findViewById(R.id.editdialog_price);
                String newName = nameView.getText().toString();
                String newPrice = priceView.getText().toString();
                if (isInputValid(newName, newPrice)) {
                    String validDouble = new DecimalFormat("#.##").format(Double.parseDouble(newPrice));
                    Object newData = new Merchandise(newName, Double.parseDouble(validDouble));
                    Map<String, Object> newMap = new HashMap<>();
                    newMap.put(merchandiseKey, newData);
                    operator.updateDataToDatabase(newMap);
                } else {
                    Toast.makeText(context, "Input error. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.show();
    }

    void showDeleteMerchandiseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(String.format("Do you want to delete %s?", merchandise.getName()));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                operator.deleteDataFromDatabase(merchandiseKey);
                Toast.makeText(context, "Delete complete.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.show();
    }

    @SuppressLint("InflateParams")
    private View setBuilderView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        return inflater.inflate(R.layout.layout_editdialog, null);
    }

    private View setDataInEdittext(View view) {
        EditText nameEdit = view.findViewById(R.id.editdialog_name);
        TextView priceEdit = view.findViewById(R.id.editdialog_price);
        nameEdit.setText(merchandise.getName());
        priceEdit.setText(new DecimalFormat("0.00##").format(merchandise.getPrice()));
        return view;
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
