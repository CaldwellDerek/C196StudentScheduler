package com.caldwell.c196studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.caldwell.c196studentscheduler.Database.Repository;
import com.caldwell.c196studentscheduler.Entity.Course;
import com.caldwell.c196studentscheduler.Entity.Term;
import com.caldwell.c196studentscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class TermDetail extends AppCompatActivity {

    int termID;
    String termName;
    String termStartDate;
    String termEndDate;
    EditText etTermName;
    EditText etTermStartDate;
    EditText etTermEndDate;
    Repository repository;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        RecyclerView rv = findViewById(R.id.rvTermDetailCourses);
        repository = new Repository(getApplication());

        etTermName = findViewById(R.id.etTermName);
        etTermStartDate = findViewById(R.id.etTermStartDate);
        etTermEndDate = findViewById(R.id.etTermEndDate);

        termID = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        termStartDate = getIntent().getStringExtra("termStartDate");
        termEndDate = getIntent().getStringExtra("termEndDate");

        etTermName.setText(termName);
        etTermStartDate.setText(termStartDate);
        etTermEndDate.setText(termEndDate);


        final CourseAdapter adapter = new CourseAdapter(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<Course> allCourses = repository.getAllCourses();
        List<Course> courses = new ArrayList<>();
        if (termID != -1) {
            for (Course course : allCourses){
                int x = 0;
                if (course.getTermID() == termID) {
                    courses.add(x, course);
                    x ++;
                }
            }
            adapter.setCourses(courses);
        } else {
            adapter.setCourses(null);
        }

    }


    public void termSaveButton(View view) {
        Term term;
        if (termID == -1) {
            int newTermID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
            term = new Term(newTermID, etTermName.getText().toString(), etTermStartDate.getText().toString(), etTermEndDate.getText().toString());
            repository.insert(term);
        } else {
            term = new Term(termID,etTermName.getText().toString(), etTermStartDate.getText().toString(), etTermEndDate.getText().toString());
            repository.insert(term);
        }
    }
}