package com.caldwell.c196studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caldwell.c196studentscheduler.Entity.Term;
import com.caldwell.c196studentscheduler.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public class TermViewHolder extends RecyclerView.ViewHolder{

        private final TextView termItemView;
        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.term_list_item_textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent i = new Intent(context, TermDetail.class);
                    i.putExtra("termID", current.getTermID());
                    i.putExtra("termName", current.getTermName());
                    i.putExtra("termStartDate", current.getStartDate());
                    i.putExtra("termEndDate", current.getEndDate());
                    context.startActivity(i);
                }
            });
        }
    }

    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms != null){
            Term current = mTerms.get(position);
            String name = current.getTermName();
            holder.termItemView.setText(name);
        } else {
            holder.termItemView.setText("No product name");
        }
    }

    @Override
    public int getItemCount() {
        if (mTerms != null) {
            return mTerms.size();
        } else {
            return 0;
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }
}
