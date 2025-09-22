package com.example.gymnote;

public class Workout {
        private int id;
        private String title;
        private String date; // YYYY-MM-DD
        private int duration; // minutes
        private int calories;
        private String type;


        public Workout() {}


        public Workout(int id, String title, String date, int duration, int calories, String type) {
            this.id = id;
            this.title = title;
            this.date = date;
            this.duration = duration;
            this.calories = calories;
            this.type = type;
        }

//nguyên tắc Encapsulation (Đóng gói dữ liệu) trong OOP
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public int getDuration() { return duration; }
        public void setDuration(int duration) { this.duration = duration; }
        public int getCalories() { return calories; }
        public void setCalories(int calories) { this.calories = calories; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }


