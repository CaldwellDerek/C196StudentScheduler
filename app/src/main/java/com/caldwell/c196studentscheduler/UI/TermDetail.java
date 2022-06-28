package com.caldwell.c196studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.caldwell.c196studentscheduler.Database.Repository;
import com.caldwell.c196studentscheduler.Entity.Term;
import com.caldwell.c196studentscheduler.R;

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

        repository = new Repository(getApplication());
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