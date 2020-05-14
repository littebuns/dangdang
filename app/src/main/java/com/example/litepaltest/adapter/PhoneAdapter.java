package com.example.litepaltest.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.litepaltest.R;
import com.example.litepaltest.entity.Sign;
import com.example.litepaltest.entity.User;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {


    private Context phoneContext;
    private List<User> userList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View phoneView;
        TextView phoneName;
        TextView phonePhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneView = itemView;
            phoneName = (TextView) itemView.findViewById(R.id.phone_name);
            phonePhone = (TextView) itemView.findViewById(R.id.phone_phone);
        }
    }

    @NonNull
    @Override
    public PhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_adapter,parent,false);

        //通讯录子项点击事件，跳转至拨号
        final ViewHolder holderClick = new ViewHolder(view);
        holderClick.phoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holderClick.getAdapterPosition();
                User user = userList.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:"+ user.getPhone());
                intent.setData(data);
                phoneContext.startActivity(intent);
            }
        });
        return holderClick;
    }

    public PhoneAdapter(List<User> userList, Context phoneContext) {
        this.phoneContext = phoneContext;
        this.userList = userList;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.phoneName.setText(user.getName());
        holder.phonePhone.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }




}
