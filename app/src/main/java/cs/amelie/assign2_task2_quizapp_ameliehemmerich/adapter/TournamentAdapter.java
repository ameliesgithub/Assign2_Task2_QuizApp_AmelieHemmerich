package cs.amelie.assign2_task2_quizapp_ameliehemmerich.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs.amelie.assign2_task2_quizapp_ameliehemmerich.R;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.admin.EditTournamentActivity;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.database.AppDatabase;
import cs.amelie.assign2_task2_quizapp_ameliehemmerich.model.Tournament;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {

    private List<Tournament> tournaments;

    private AppDatabase db;


    public TournamentAdapter(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @NonNull
    @Override
    public TournamentAdapter.TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tournament, parent, false);

        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentAdapter.TournamentViewHolder holder, int position) {
        Tournament tournament = tournaments.get(position);

        holder.tvName.setText(tournament.getName());
        holder.tvCategory.setText("Category: " + tournament.getCategory());
        holder.tvDifficulty.setText("Difficulty: " + tournament.getDifficulty());
        holder.tvStartDate.setText("Start Date: " + tournament.getStartDate());
        holder.tvEndDate.setText("End Date: " + tournament.getEndDate());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EditTournamentActivity.class);

                intent.putExtra("id", tournament.getId());
                intent.putExtra("name", tournament.getName());
                intent.putExtra("startDate", tournament.getStartDate());
                intent.putExtra("endDate", tournament.getEndDate());
                intent.putExtra("category", tournament.getCategory());
                intent.putExtra("difficulty", tournament.getDifficulty());

                holder.itemView.getContext().startActivity(intent);
            }
        });

        db = AppDatabase.getInstance(holder.itemView.getContext());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = holder.getAdapterPosition();

                if(currentPos == RecyclerView.NO_POSITION) {
                    return;
                }

                Tournament selectedTournament = tournaments.get(currentPos);

                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Delete Tournament")
                        .setMessage("Are you sure you want to delete " + selectedTournament.getName() + "?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            AppDatabase db = AppDatabase.getInstance(holder.itemView.getContext());

                            db.tournamentDao().delete(selectedTournament);

                            tournaments.remove(currentPos);
                            notifyItemRemoved(currentPos);

                            Toast.makeText(holder.itemView.getContext(),
                                    "Tournament deleted",
                                    Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    public static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory, tvDifficulty, tvStartDate, tvEndDate;
        Button btnEdit, btnDelete;

        public TournamentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameTourItem);
            tvCategory = itemView.findViewById(R.id.tvCategoryTourItem);
            tvDifficulty = itemView.findViewById(R.id.tvDifficultyTourItem);
            tvStartDate = itemView.findViewById(R.id.tvStartDateTourItem);
            tvEndDate = itemView.findViewById(R.id.tvEndDateTourItem);

            btnEdit = itemView.findViewById(R.id.btnEditTour);
            btnDelete = itemView.findViewById(R.id.btnDeleteTour);
        }
    }
}
