package com.example.litepaltest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.litepaltest.R;
import com.example.litepaltest.activity.ScheduleDetailActivity;
import com.example.litepaltest.entity.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<Schedule> scheduleList;
    private Context scheduleContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View scheduleView;
        TextView scheduleContent;
        TextView scheduleDate;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scheduleView = itemView;
            scheduleContent = (TextView) itemView.findViewById(R.id.schedule_content);
            scheduleDate = (TextView) itemView.findViewById(R.id.schedule_date);
        }
    }

    public ScheduleAdapter(List<Schedule> scheduleList, Context context) {
        this.scheduleList = scheduleList;
        scheduleContext = context;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_adapter,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.scheduleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getPosition();
                Schedule schedule = scheduleList.get(position);
                Intent intent = new Intent(scheduleContext, ScheduleDetailActivity.class);
                intent.putExtra("content",schedule.getContent());
                intent.putExtra("date",schedule.getDate());
                int i = schedule.getId();
                intent.putExtra("id",i);
                scheduleContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        String content = "任务: "+ schedule.getContent()+"        ";

        holder.scheduleContent.setText(content.substring(0,9));
        holder.scheduleDate.setText(schedule.getDate());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
