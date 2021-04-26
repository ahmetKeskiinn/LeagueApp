package example.com.newapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import example.com.newapp.DataBase.Fixture.Fixture;
import example.com.newapp.R;

/*
    Bu Class bir adaptör Class'ıdır. Bu adaptörde yapılanlar; verileri recyclerview içinde kullacak şekilde bir kalıba oturtulur.
 */
/*
    This Class is an adapter Class. What is done in this adapter; fit a mold to use the data in recyclerview.
 */

public class FixtureAdapter extends ListAdapter<Fixture, FixtureAdapter.FixtureHolder> {
    private static final String TAG = "Fixture Adapter";
    private FixtureAdapter.OnItemClickListener listener;

    public FixtureAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Fixture> DIFF_CALLBACK = new DiffUtil.ItemCallback<Fixture>() {
        @Override
        public boolean areItemsTheSame(Fixture oldItem, Fixture newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Fixture oldItem, Fixture newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public FixtureAdapter.FixtureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fixture_raw, parent, false);
        return new FixtureAdapter.FixtureHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureAdapter.FixtureHolder holder, int position) {
        Fixture currentFixture = getItem(position);
        String[] match = currentFixture.getTitle().split("vs");
        holder.textViewHomeTeam.setText(match[0]);
        holder.textViewVS.setText("vs");
        holder.textViewAwayTeam.setText(match[1]);

    }

    public Fixture getFixtureAt(int position) {
        return getItem(position);
    }

    class FixtureHolder extends RecyclerView.ViewHolder {
        private TextView textViewHomeTeam;
        private TextView textViewVS;
        private TextView textViewAwayTeam;
        private TextView id;

        public FixtureHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            textViewHomeTeam = itemView.findViewById(R.id.textViewHomeTeam);
            textViewVS = itemView.findViewById(R.id.textViewVSTeam);
            textViewAwayTeam = itemView.findViewById(R.id.textviewAwayTeam);
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
        void onItemClick(Fixture fixture);
    }

    public void setOnItemClickListener(FixtureAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

