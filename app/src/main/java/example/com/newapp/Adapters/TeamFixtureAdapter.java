package example.com.newapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import example.com.newapp.DataBase.Fixture.Fixture;
import example.com.newapp.DataBase.Team.Team;
import example.com.newapp.R;
import example.com.newapp.Utils.SharedPref;

/*
    Bu class bir Adaptör'dür. Bu adaptörde; gelen veriler recyclerview'de mevcut bulunan row'a uygun işleniyor. Eğer ki seçilen takım
    maçı kendi sahasında oynyacaksa "Ev", deplasmana gidecekse "Uçak" sembolü koyulacaktır.
 */

/*
    This class is an Adapter. In this adapter; Incoming data are processed in accordance with the current row in recyclerview.
    If the chosen teamIf the match will be played at home, the symbol "Home" will be placed, if it will go away, the "Airplane" symbol will be placed.
 */

public class TeamFixtureAdapter extends ListAdapter<Fixture, TeamFixtureAdapter.TeamHolder> {
    private static final String TAG = "Team Fixture Adapter";
    private TeamFixtureAdapter.OnItemClickListener listener;
    private SharedPref sharedPref;
    private Context context;
    public TeamFixtureAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Fixture> DIFF_CALLBACK = new DiffUtil.ItemCallback<Fixture>() {
        @Override
        public boolean areItemsTheSame(Fixture oldItem, Fixture newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Fixture oldItem, Fixture newItem) {
            return true;
        }
    };

    @NonNull
    @Override
    public TeamFixtureAdapter.TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPref = SharedPref.getInstance(TeamFixtureAdapter.this.context);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_fixture_raw, parent, false);
        return new TeamFixtureAdapter.TeamHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamFixtureAdapter.TeamHolder holder, int position) {
        Fixture currentTeam = getItem(position);
        String[] currentTeamSplit= currentTeam.getTitle().split("vs");
        Log.d(TAG, "onBindViewHolder: ");
        if(currentTeamSplit[0].contains(sharedPref.getSELECETED_TEAM())){
            holder.whereIn.setImageResource(R.drawable.home_team);
        }
        else{
            holder.whereIn.setImageResource(R.drawable.away_team);
        }
        holder.homeTeamTW.setText(currentTeamSplit[0]);
        holder.vsTW.setText("vs");
        holder.awayTeamTW.setText(currentTeamSplit[1]);
        //holder.weekTW.setText("1" + Resources.getSystem().getString(R.string.weekNo));
        String placeholder = holder.itemView.getContext().getResources().getString(R.string.weekNo);
        holder.weekTW.setText(currentTeam.getWeek()+ placeholder);
    }

    public Fixture getTeamAt(int position) {
        return getItem(position);
    }

    class TeamHolder extends RecyclerView.ViewHolder {

        private TextView homeTeamTW;
        private TextView vsTW;
        private TextView awayTeamTW;
        private TextView weekTW;
        private ImageView whereIn;
        public TeamHolder(View itemView) {
            super(itemView);
            homeTeamTW = itemView.findViewById(R.id.textViewHomeTeam);
            vsTW = itemView.findViewById(R.id.textViewVSTeam);
            awayTeamTW = itemView.findViewById(R.id.textviewAwayTeam);
            weekTW = itemView.findViewById(R.id.teamWeek);
            whereIn = itemView.findViewById(R.id.teamFixtureButton);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Team team);
    }

}