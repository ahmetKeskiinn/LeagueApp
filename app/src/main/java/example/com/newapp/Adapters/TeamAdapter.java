package example.com.newapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import example.com.newapp.DataBase.Team.Team;
import example.com.newapp.R;

/*
    Bu class bir Adaptördür. Bu Adaptörde yapılanlar; gelen verileri recyclerview de belirtilen row'a uygun olarak işlenir.
 */
/*
    This class is an Adapter. What is done in this Adapter; Incoming data is processed in accordance with the row specified in recyclerview.
 */

public class TeamAdapter extends ListAdapter<Team, TeamAdapter.TeamHolder> {
    private static final String TAG = "Team Adapter";
    private OnItemClickListener listener;

    public TeamAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Team> DIFF_CALLBACK = new DiffUtil.ItemCallback<Team>() {
        @Override
        public boolean areItemsTheSame(Team oldItem, Team newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Team oldItem, Team newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getId() == newItem.getId();
        }
    };

    @NonNull
    @Override
    public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new TeamHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamHolder holder, int position) {
        Team currentTeam = getItem(position);
        holder.textViewTitle.setText(currentTeam.getTitle());
    }

    public Team getTeamAt(int position) {
        return getItem(position);
    }

    class TeamHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;

        public TeamHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Team team);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}