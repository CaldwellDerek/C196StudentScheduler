package com.caldwell.c196studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caldwell.c196studentscheduler.Entity.Course;
import com.caldwell.c196studentscheduler.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseItemView;
        private CourseViewHolder(View itemView) {
             super(itemView);
             courseItemView = itemView.findViewById(R.id.course_list_item_textview);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     int position = getAdapterPosition();
                     final Course current = mCourses.get(position);
                     Intent i = new Intent(context, CourseDetail.class);
                     i.putExtra("courseID", current.getCourseID());
                     i.putExtra("courseName", current.getCourseName());
                     i.putExtra("courseStartDate", current.getStartDate());
                     i.putExtra("courseEndDate", current.getEndDate());
                     i.putExtra("courseInstructorName", current.getInstructorName());
                     i.putExtra("courseInstructorEmail", current.getInstructorEmail());
                     i.putExtra("courseInstructorPhone", current.getInstructorPhone());
                     context.startActivity(i);
                 }
             });
        }

    }

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        } else {
            holder.courseItemView.setText("No course name");
        }
    }

    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }

}
