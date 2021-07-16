//HomeworkAdapter
package com.example.homeschoolplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkVh> {
    private ArrayList<Assignment> assignments;
    private ArrayList<User> users;
    private Context context;
    private RecyclerView recyclerView;
    private HomeworkAdapter hwkAdapter;
    private User user;
    private String parentId;
    private ChildProfileInterface childProfileInterface;
    Intent intent;

    public HomeworkAdapter(User user, String parentId, Intent intent, ArrayList<Assignment> assignments, ChildProfileInterface childProfileInterface) {
        this.user = user;
        this.parentId = parentId;
        this.assignments = assignments;
        this.childProfileInterface = childProfileInterface;
        this.intent = intent;
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
        holder.index = position;
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class HomeworkVh extends RecyclerView.ViewHolder {
        TextView hwk, hwkDate;
        Button completeAssignment;
        public int index;
        public HomeworkVh(@NonNull View itemView) {
            super(itemView);
            hwk = itemView.findViewById(R.id.hwk);
            hwkDate = itemView.findViewById(R.id.hwkDate);
            completeAssignment = itemView.findViewById(R.id.button2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent.putExtra("childId", user.userId);
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("description", user.assignments.get(index).description);
                    intent.putExtra("title", user.assignments.get(index).title);
                    intent.putExtra("class_name", user.assignments.get(index).class_name);
                    intent.putExtra("repeating", user.assignments.get(index).repeating);
                    intent.putExtra("assignmentIndex", index);
                    context.startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    childProfileInterface.onLongItemClick(getAbsoluteAdapterPosition());
                    return true;
                }
            });

            completeAssignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean is_parent = (parentId != null);
                    user.markAssignmentComplete(index, is_parent);
                }
            });
        }
    }
}


