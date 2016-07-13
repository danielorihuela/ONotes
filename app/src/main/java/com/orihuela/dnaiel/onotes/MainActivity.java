package com.orihuela.dnaiel.onotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.orihuela.dnaiel.onotes.DataBase.DBhelper;
import com.orihuela.dnaiel.onotes.DataBase.SQLController;
import com.orihuela.dnaiel.onotes.notifications.Notification;
import com.orihuela.dnaiel.onotes.notifications.ServiceClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listViewNotes)
    ListView listViewNotes;

    Notification notification;
    SQLController dbconnection;
    TextView tvID, tvTitle, tvNote, tvNoteNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        stopService(new Intent(this, ServiceClass.class));

        notification = new Notification(this);
        dbconnection = new SQLController(this);
        info();
    }

    public void info(){
        dbconnection.openDatabases();
        Cursor cursor = dbconnection.readData();

        String[] from = new String[]{
                DBhelper.NOTE_ID,
                DBhelper.NOTE_TITLE,
                DBhelper.NOTE_NOTE,
                DBhelper.NOTE_NOTIF};

        int[] to = new int[]{
                R.id.tvnoteid,
                R.id.tvnotetitle,
                R.id.tvnote,
                R.id.tvnotenotif};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.formato_fila, cursor, from, to);

        adapter.notifyDataSetChanged();
        listViewNotes.setAdapter(adapter);

        listViewNotes.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                tvID = (TextView) v.findViewById(R.id.tvnoteid);
                tvTitle = (TextView) v.findViewById(R.id.tvnotetitle);
                tvNote = (TextView) v.findViewById(R.id.tvnote);
                tvNoteNotif = (TextView) v.findViewById(R.id.tvnotenotif);

                String aux_tvID = tvID.getText().toString();
                String aux_tvTitle = tvTitle.getText().toString();
                String aux_note = tvNote.getText().toString();
                String aux_notif = tvNoteNotif.getText().toString();


                Intent i2 = new Intent(getApplicationContext(), ModifyNote.class);
                i2.putExtra("noteID", aux_tvID);
                i2.putExtra("notetitle", aux_tvTitle);
                i2.putExtra("note", aux_note);
                i2.putExtra("notif", aux_notif);
                startActivity(i2);
            }
        });

        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                tvID = (TextView) v.findViewById(R.id.tvnoteid);
                int noteId = Integer.parseInt(tvID.getText().toString());
                tvNoteNotif = (TextView) v.findViewById(R.id.tvnotenotif);
                int notifId = Integer.parseInt(tvNoteNotif.getText().toString());
                notification.deleteNotify(notifId);
                dbconnection.deleteData(noteId);
                dbconnection.closeDatabases();
                update();
                return true;
            }
        });
    }

    @OnClick(R.id.BottonPlus)
    public void onClick() {
        Intent i = new Intent(this, NuevaNota.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            Intent i = new Intent(this, About.class);
            startActivity(i);
        } else if (id == R.id.deleteAll) {
            dbconnection.deleteDatabase();
            dbconnection.closeDatabases();
            notification.deleteAll();
            update();
        }

        return super.onOptionsItemSelected(item);
    }

    public void update(){
        final Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
