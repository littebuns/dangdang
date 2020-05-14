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
import com.example.litepaltest.entity.User;

import java.util.List;

public class UserManageAdapter extends RecyclerView.Adapter<UserManageAdapter.ViewHolder> {


    private Context userMangeContext;
    private List<User> userList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View userMangeView;
        TextView userName;
        TextView userPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userMangeView = itemView;
            userName = (TextView) itemView.findViewById(R.id.phone_name);
            userPhone = (TextView) itemView.findViewById(R.id.phone_phone);
        }
    }

    public UserManageAdapter(Context userMangeContext, List<User> userList) {
        this.userMangeContext = userMangeContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_adapter,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.userMangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                User user = userList.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:"+ user.getPhone());
                intent.setData(data);
                userMangeContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getName());
        holder.userPhone.setText(user.getPhone());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }




}
