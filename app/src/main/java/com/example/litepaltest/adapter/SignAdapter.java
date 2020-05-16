package com.example.litepaltest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.litepaltest.R;
import com.example.litepaltest.entity.Sign;

import java.util.List;

public class SignAdapter extends  RecyclerView.Adapter<SignAdapter.ViewHodler> {

    private List<Sign> signList;

    static class ViewHodler extends RecyclerView.ViewHolder{

        TextView signName;
        TextView signLocation;
        TextView signDate;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            signName = (TextView) itemView.findViewById(R.id.sign_name);
            signLocation = (TextView) itemView.findViewById(R.id.sign_location);
            signDate = (TextView) itemView.findViewById(R.id.sign_date);
        }
    }

    public SignAdapter(List<Sign> signList) {
        this.signList = signList;
    }

    @NonNull
    @Override
    public SignAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_adpater,parent,false);
        ViewHodler holder = new ViewHodler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SignAdapter.ViewHodler holder, int position) {
        Sign sign = signList.get(position);
        String content = sign.getLocation();
        content = content+"             ";
        content = content.substring(0,10);
        holder.signName.setText(sign.getName());
        holder.signLocation.setText(content);
        holder.signDate.setText(sign.getDate());

    }

    @Override
    public int getItemCount() {
        return signList.size();
    }
}
