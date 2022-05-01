package com.github.ilyabelow.notes;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NoteRepository {
    private static final HashMap<Integer, Note> map = new HashMap<>();
    private static ArrayList<Note> list = new ArrayList<>();
    private static int curId;
    private static NoteListAdapter adapter;


    public static void initialize(Context context) {
        try {
            File db = new File(context.getFilesDir().toString() + "/notes.json");
            InputStream stream;
            if (!db.exists()) {
                stream = context.getAssets().open("notes_prepopulate.json");
            } else {
                stream = new FileInputStream(db);
            }
            // Read whole file
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            String str = total.toString();
            // Parse json
            JSONArray array = new JSONObject(str).getJSONArray("notes");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                int id = object.getInt("id");
                map.put(id, new Note(id,
                                object.getString("title"),
                                object.getString("text"),
                                new Date(object.getLong("date")),
                                object.getInt("color")
                        )
                );
                curId = Math.max(curId, id);
            }
        } catch (IOException | JSONException ignored) { }

        // Creating a list for main activity
        list = new ArrayList<>(map.values());
        list.sort((a, b) -> b.getDate().compareTo(a.getDate()));
    }

    // Yes, this is super slow and I should totally use a database
    public static void push(Context context) {
        try {
            // Constructing a json
            JSONArray array = new JSONArray();
            for (int key : map.keySet()) {
                JSONObject object = new JSONObject();
                Note n = getNote(key);
                object.put("id", n.getId());
                object.put("title", n.getTitle());
                object.put("text", n.getText());
                object.put("date", n.getDate().getTime());
                object.put("color", n.getColor());
                array.put(object);
            } // can super slow, should at least put it in async task
            JSONObject master = new JSONObject();
            master.put("notes", array);
            // Writing it to file
            File file = new File(context.getFilesDir(), "notes.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(master.toString());
            bufferedWriter.close();
        } catch (IOException | JSONException ignore) { }
    }

    public static void subscribeAdapter(NoteListAdapter a) {
        adapter = a;
    }

    public static ArrayList<Note> getList() {
        return list;
    }


    private static int findPosInList(int id) {
        // Yes, this is ugly, but how else?)))
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    // Access

    public static void addNote(String title, String text, Date date, int color) {
        curId += 1;
        Note n = new Note(curId, title, text, date, color);
        map.put(curId, n);
        list.add(0, n);
        adapter.notifyItemInserted(0);

    }


    public static void removeNote(int id) {
        map.remove(id);
        int pos = findPosInList(id);
        if (pos != -1) {
            list.remove(pos);
            adapter.notifyItemRemoved(pos);
        }
    }


    public static void editNote(int id, String title, String text, Date date, int color) {
        Note n = map.get(id);
        if (n == null) {
            return;
        }
        n.setTitle(title);
        n.setColor(color);
        n.setDate(date);
        n.setText(text);
        int pos = findPosInList(id);
        if (pos != -1) {
            list.set(pos, n);
            adapter.notifyItemChanged(pos);
        }
    }

    public static Note getNote(int id) {
        return map.get(id);
    }
}