// Ratchanon Bualeesonsakun 5910401149 Section 2
// ratchanon.bua@ku.th
package com.example.merchandise_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    private Context context;
    private CRUDOperator operator;
    private ArrayList<Merchandise> merchandises;
    private ArrayList<String> merchKeyList;

    RecyclerAdapter(Context context, ArrayList<Merchandise> object, ArrayList<String> merchKeyList, CRUDOperator operator) {
        this.context = context;
        this.merchandises = object;
        this.merchKeyList = merchKeyList;
        this.operator = operator;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_datalist, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.merchItem = merchandises.get(position);
        holder.merchKey = merchKeyList.get(position);
        holder.setMerchandiseItem(position);
    }

    @Override
    public int getItemCount() {
        return merchandises.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView merchandiseName;
        TextView merchandisePrice;
        ImageView merchandiseImage;
        Button editMerchandise;
        Button deleteMerchandise;

        String merchKey;
        Merchandise merchItem;
        ManagerDialog dialog;

        Holder(View itemView) {
            super(itemView);
            // View Initialize
            merchandiseName = itemView.findViewById(R.id.name_textview);
            merchandisePrice = itemView.findViewById(R.id.price_textview);
            merchandiseImage = itemView.findViewById(R.id.imageview);
            // Button Initialize
            editMerchandise = itemView.findViewById(R.id.edit_button);
            deleteMerchandise = itemView.findViewById(R.id.delete_button);
        }

        void setMerchandiseItem(int position) {
            dialog = new ManagerDialog(context, merchKey, merchItem, operator);
            String price = new DecimalFormat("0.00##").format(merchandises.get(position).getPrice());
            // Set View
            merchandiseName.setText(merchandises.get(position).getName());
            merchandisePrice.setText(price);
            merchandiseImage.setImageResource(R.drawable.im_unknown);
            // Set Button
            editMerchandise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.showUpdateMerchandiseDialog();
                }
            });
            deleteMerchandise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.showDeleteMerchandiseDialog();
                }
            });
        }
    }
}
