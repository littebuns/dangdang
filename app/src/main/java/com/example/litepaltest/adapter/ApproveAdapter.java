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
import com.example.litepaltest.activity.AdminApproveActivity;
import com.example.litepaltest.activity.AdminApproveDetailActivity;
import com.example.litepaltest.activity.ScheduleDetailActivity;
import com.example.litepaltest.entity.Approve;
import com.example.litepaltest.entity.Schedule;
import com.example.litepaltest.entity.User;

import java.util.List;

public class ApproveAdapter extends RecyclerView.Adapter<ApproveAdapter.ViewHolder> {

    private Context approveContext;
    private List<Approve> approveList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_adpater,parent,false);

        final ApproveAdapter.ViewHolder holder = new ApproveAdapter.ViewHolder(view);

        if(approveContext.getClass().equals(AdminApproveActivity.class)){
            holder.approveView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Approve approve = approveList.get(position);
                    Intent intent = new Intent(approveContext, AdminApproveDetailActivity.class);
                    intent.putExtra("name",approve.getName());
                    intent.putExtra("content",approve.getContent());
                    intent.putExtra("date",approve.getDate());
                    intent.putExtra("id",approve.getId());
                    approveContext.startActivity(intent);
                }
            });
        }
//        holder.approveView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Approve approve = approveList.get(position);
////               approveContext.startActivity(intent);
//            }
//        });
        return holder;


    }


    public ApproveAdapter(Context approveContext, List<Approve> approveList) {
        this.approveContext = approveContext;
        this.approveList = approveList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Approve approve = approveList.get(position);
        String content = approve.getContent();
        content = content+"             ";
        content = content.substring(0,8);
        holder.approveContent.setText(content);
        holder.approveDate.setText(approve.getDate());
        holder.approveName.setText(approve.getName());

    }

    @Override
    public int getItemCount() {
        return approveList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View approveView;
        TextView approveName;
        TextView approveDate;
        TextView approveContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            approveView = itemView;
            approveName = (TextView) itemView.findViewById(R.id.sign_name);
            approveDate = (TextView) itemView.findViewById(R.id.sign_date);
            approveContent = (TextView) itemView.findViewById(R.id.sign_location);
        }
    }





}
