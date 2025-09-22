package com.example.gymnote;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gymnote.DbHelper;
import com.example.gymnote.Workout;
import com.example.gymnote.AddEditDiaLog;
import com.example.gymnote.WorkoutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

public class MainActivity extends AppCompatActivity implements WorkoutAdapter.Callback {
    private DbHelper db;
    private WorkoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DbHelper(this);
        RecyclerView rv = findViewById(R.id.rv_workouts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkoutAdapter(this, db.getAllWorkouts(), this);
        rv.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> AddEditDiaLog.show(this, null, w -> {
            long id = db.addWorkout(w);
            if (id > 0) {
                Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
                refresh();
            }
        }));
    }
    private void refresh() {
        List<Workout> list = db.getAllWorkouts();
        adapter.update(list);
    }


    @Override
    public void onEdit(Workout w) {
        AddEditDiaLog.show(this, w, updated -> {
            int r = db.updateWorkout(updated);
            if (r > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                refresh();
            }
        });
    }
    @Override
    public void onDelete(Workout w) {
        new AlertDialog.Builder(this)
                .setTitle("Xoá")
                .setMessage("Bạn có chắc muốn xoá buổi tập này?")
                .setNegativeButton("Huỷ", null)
                .setPositiveButton("Xoá", (d, i) -> {
                    db.deleteWorkout(w.getId());
                    Toast.makeText(this, "Đã xoá", Toast.LENGTH_SHORT).show();
                    refresh();
                }).show();
    }
}