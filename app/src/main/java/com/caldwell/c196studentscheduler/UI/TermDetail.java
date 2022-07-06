package com.caldwell.c196studentscheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caldwell.c196studentscheduler.Database.Repository;
import com.caldwell.c196studentscheduler.Entity.Course;
import com.caldwell.c196studentscheduler.Entity.Term;
import com.caldwell.c196studentscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetail extends AppCompatActivity {

    int termID;
    String termName;
    String termStartDate;
    String termEndDate;
    EditText etTermName;
    EditText etTermStartDate;
    EditText etTermEndDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();
    String format;
    SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Filters Courses assigned to selected Term and populates them in the Associated Courses recycler view
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

        // Assigns Views to variables and populates fields with selected Term info
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

        // Create OnClick and DateSet listeners for the Term Start Date
        format = "MM/dd/yy";
        formatter = new SimpleDateFormat(format, Locale.US);
        etTermStartDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String entry = etTermStartDate.getText().toString();
                if (entry.equals("")) {
                    entry = "07/10/2022";
                }
                try {
                    calStart.setTime(formatter.parse(entry));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, startDate, calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calStart.set(Calendar.YEAR, year);
                calStart.set(Calendar.MONTH, month);
                calStart.set(Calendar.DAY_OF_MONTH, day);
                updateTermStartDate();
            }
        };

        // Create OnClick and DateSet listeners for End Date
        etTermEndDate.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String entry = etTermEndDate.getText().toString();
                if (entry.equals("")) {
                    entry = "10/10/2022";
                }
                try {
                    calEnd.setTime(formatter.parse(entry));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, endDate, calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calEnd.set(Calendar.YEAR, year);
                calEnd.set(Calendar.MONTH, month);
                calEnd.set(Calendar.DAY_OF_MONTH, day);
                updateTermEndDate();
            }
        };
    }

    private void updateTermStartDate() {
        etTermStartDate.setText(formatter.format(calStart.getTime()));
    }

    private void updateTermEndDate() {
        etTermEndDate.setText(formatter.format(calEnd.getTime()));
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
            repository.update(term);
        }
        Intent i = new Intent(TermDetail.this, TermList.class);
        startActivity(i);
    }

    public void termDeleteButton(View view) {
        int x = 0;
        List<Course> allCourses = repository.getAllCourses();
        for (Course course : allCourses) {
            if (course.getTermID() == getIntent().getIntExtra("termID", -1)) {
                x++;
            }
        }
        if (x >= 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "This term cannot be deleted, it has courses assigned to it.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Term termToDelete = new Term(termID,etTermName.getText().toString(), etTermStartDate.getText().toString(), etTermEndDate.getText().toString());
            repository.delete(termToDelete);
            Intent i = new Intent(TermDetail.this, TermList.class);
            startActivity(i);
        }
    }
}