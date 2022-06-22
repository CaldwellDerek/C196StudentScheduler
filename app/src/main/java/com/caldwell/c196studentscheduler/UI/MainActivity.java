package com.caldwell.c196studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.caldwell.c196studentscheduler.Database.Repository;
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
        Term test1 = new Term(1, "test1");
        Term test2 = new Term(2, "test1");
        Repository repo = new Repository(getApplication());
        repo.insert(test1);
        repo.insert(test2);
    }
}