package com.caldwell.c196studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.caldwell.c196studentscheduler.Database.Repository;
import com.caldwell.c196studentscheduler.Entity.Course;
import com.caldwell.c196studentscheduler.Entity.Term;
import com.caldwell.c196studentscheduler.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewSchedule(View view) {
        Intent i = new Intent(MainActivity.this, TermList.class);
        startActivity(i);
        Term test1 = new Term(1, "test1", "06/28/2022", "07/01/2022");
        Term test2 = new Term(2, "test2", "06/28/2022", "07/01/2022");
        Course course1 = new Course(1,
                "C196",
                "07/01/2022",
                "07/22/2022",
                "Completed",
                "Derek Caldwell",
                "1112223333",
                "testemail1@testmail.com",
                1);
        Course course2 = new Course(2,
                "C197",
                "07/01/2022",
                "07/22/2022",
                "Completed",
                "Derek Caldwell",
                "1112223333",
                "testemail1@testmail.com",
                1);
        Course course3 = new Course(3,
                "C198",
                "07/01/2022",
                "07/22/2022",
                "Completed",
                "Derek Caldwell",
                "1112223333",
                "testemail1@testmail.com",
                2);
        Repository repo = new Repository(getApplication());
        repo.insert(test1);
        repo.insert(test2);
        repo.insert(course1);
    }
}