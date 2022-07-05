package com.caldwell.c196studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView rv = findViewById(R.id.rvTermDetailCourses);
        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : allCourses) {
            if (course.getTermID() == getIntent().getIntExtra("termID", -1)) {
                filteredCourses.add(course);
            }
        }
        final CourseAdapter adapter = new CourseAdapter(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(filteredCourses);

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

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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