package com.example.gymnote;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.gymnote.R;
import com.example.gymnote.Workout;
public class AddEditDiaLog {
    public interface Listener {
        void onSaved(Workout w);
    }


    public static void show(Context ctx, Workout initial, Listener listener) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.dialog_add_edit, null);
        EditText etTitle = v.findViewById(R.id.et_title);
        EditText etDate = v.findViewById(R.id.et_date);
        EditText etDuration = v.findViewById(R.id.et_duration);
        EditText etCalories = v.findViewById(R.id.et_calories);
        Spinner spType = v.findViewById(R.id.sp_type);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);


        if (initial != null) {
            etTitle.setText(initial.getTitle());
            etDate.setText(initial.getDate());
            etDuration.setText(String.valueOf(initial.getDuration()));
            etCalories.setText(String.valueOf(initial.getCalories()));
            int pos = adapter.getPosition(initial.getType());
            if (pos >= 0) spType.setSelection(pos);
        }


        AlertDialog dlg = new AlertDialog.Builder(ctx)
                .setTitle(initial == null ? "Thêm buổi tập" : "Sửa buổi tập")
                .setView(v)
                .setNegativeButton("Huỷ", (d, i) -> d.dismiss())
                .setPositiveButton("Lưu", (d, i) -> {
                    Workout w = initial == null ? new Workout() : initial;
                    w.setTitle(etTitle.getText().toString().trim());
                    w.setDate(etDate.getText().toString().trim());
                    try { w.setDuration(Integer.parseInt(etDuration.getText().toString().trim())); } catch (Exception ex) { w.setDuration(30); }
                    try { w.setCalories(Integer.parseInt(etCalories.getText().toString().trim())); } catch (Exception ex) { w.setCalories(200); }
                    w.setType(spType.getSelectedItem().toString());
                    listener.onSaved(w);
                }).create();
        dlg.show();
    }
}
