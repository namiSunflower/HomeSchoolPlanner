//HomeworkAdapter
package com.example.homeschoolplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkVh> {
    private ArrayList<Assignment> assignments;
    private Context context;
    private RecyclerView recyclerView;
    private HomeworkAdapter hwkAdapter;
    private User user;
    private String parentId;
    private ChildProfileInterface childProfileInterface;

    public HomeworkAdapter(ArrayList<Assignment> assignments, ChildProfileInterface childProfileInterface) {
        this.assignments = assignments;
        this.childProfileInterface = childProfileInterface;
    }

    @NonNull
    @Override
    public HomeworkVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HomeworkVh(LayoutInflater.from(context).inflate(R.layout.row_task, null));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkAdapter.HomeworkVh holder, int position) {
        Assignment assignmentModel = assignments.get(position);
        String dueDate = assignmentModel.getDue_date();
        String description = assignmentModel.getDescription();
        String title = assignmentModel.getTitle();
        String className = assignmentModel.getClass_name();
        String owner = assignmentModel.getOwner();
        Boolean markComplete = assignmentModel.isMarked_complete();
        Boolean confirmComplete = assignmentModel.isConfirmed_complete();
        Boolean repeating = assignmentModel.isRepeating();

        holder.hwk.setText(className);
        holder.hwkDate.setText(dueDate);
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class HomeworkVh extends RecyclerView.ViewHolder {
        TextView hwk, hwkDate;
        CheckBox checkHwk;
        public HomeworkVh(@NonNull View itemView) {
            super(itemView);
            hwk = itemView.findViewById(R.id.hwk);
            hwkDate = itemView.findViewById(R.id.hwkDate);
            checkHwk = itemView.findViewById(R.id.checkHwk);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                childProfileInterface.onItemClick(getLayoutPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    childProfileInterface.onLongItemClick(getAbsoluteAdapterPosition());
                    return true;
                }
            });
        }
    }
}


