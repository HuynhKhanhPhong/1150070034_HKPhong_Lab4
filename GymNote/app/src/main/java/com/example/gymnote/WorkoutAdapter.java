package com.example.gymnote;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymnote.R;
import com.example.gymnote.Workout;
import java.util.List;
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.VH> {
    public interface Callback {
        void onEdit(Workout w);
        void onDelete(Workout w);
    }


    private Context ctx;
    private List<Workout> items;
    private Callback cb;


    public WorkoutAdapter(Context ctx, List<Workout> items, Callback cb) {
        this.ctx = ctx;
        this.items = items;
        this.cb = cb;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_workout, parent, false);
        return new VH(v);
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Workout w = items.get(position);
        holder.title.setText(w.getTitle());
        holder.meta.setText(w.getDate() + " • " + w.getType());
        holder.info.setText(w.getDuration() + "p • " + w.getCalories() + " kcal");
        holder.btnEdit.setOnClickListener(view -> cb.onEdit(w));
        holder.btnDelete.setOnClickListener(view -> cb.onDelete(w));
    }


    @Override
    public int getItemCount() { return items.size(); }


    public void update(List<Workout> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }


    static class VH extends RecyclerView.ViewHolder {
        TextView title, meta, info;
        ImageButton btnEdit, btnDelete;
        VH(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.wk_title);
            meta = v.findViewById(R.id.wk_meta);
            info = v.findViewById(R.id.wk_info);
            btnEdit = v.findViewById(R.id.btn_edit);
            btnDelete = v.findViewById(R.id.btn_delete);
        }
    }
}

