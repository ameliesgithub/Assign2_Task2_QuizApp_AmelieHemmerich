package cs.amelie.assign2_task2_quizapp_ameliehemmerich.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.player.QuizActivity;

public class PlayerTournamentAdapter extends RecyclerView.Adapter<PlayerTournamentAdapter.PlayerTournamentViewHolder> {

    private List<Tournament> tournaments;
    private String type;
    private int currentUserId;

    public PlayerTournamentAdapter(List<Tournament> tournaments, String type, int currentUserId) {
        this.tournaments = tournaments;
        this.type = type;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public PlayerTournamentAdapter.PlayerTournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player_tournament, parent, false);
        return new PlayerTournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerTournamentAdapter.PlayerTournamentViewHolder holder, int position) {
        Tournament tournament = tournaments.get(position);

        holder.tvName.setText(tournament.getName());
        holder.tvCategory.setText("Category: " + tournament.getCategory());
        holder.tvDifficulty.setText("Difficulty: " + tournament.getDifficulty());
        holder.tvStartDate.setText("Start Date: " + tournament.getStartDate());
        holder.tvEndDate.setText("End Date: " + tournament.getEndDate());

        if(type.equals("ongoing")) {
            holder.btnParticipate.setVisibility(View.VISIBLE);
        } else {
            holder.btnParticipate.setVisibility(View.GONE);
        }

        holder.btnParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), QuizActivity.class);
                intent.putExtra("tournamentId", tournament.getId());
                intent.putExtra("userId", currentUserId);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    public static class PlayerTournamentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory, tvDifficulty, tvStartDate, tvEndDate;
        Button btnParticipate;

        public PlayerTournamentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNamePlayer);
            tvCategory = itemView.findViewById(R.id.tvCategoryPlayer);
            tvDifficulty = itemView.findViewById(R.id.tvDifficultyPlayer);
            tvStartDate = itemView.findViewById(R.id.tvStartDatePlayer);
            tvEndDate = itemView.findViewById(R.id.tvEndDatePlayer);
            btnParticipate = itemView.findViewById(R.id.btnParticipatePlayer);
        }
    }
}
