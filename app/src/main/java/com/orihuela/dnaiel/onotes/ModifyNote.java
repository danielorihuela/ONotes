package com.orihuela.dnaiel.onotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.orihuela.dnaiel.onotes.DataBase.SQLController;
import com.orihuela.dnaiel.onotes.notifications.Notification;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ModifyNote extends AppCompatActivity {
    EditText ettitle, etnote;
    int note_id, notif_id;
    SQLController dbconnection;
    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nota);

        ButterKnife.bind(this);

        dbconnection = new SQLController(this);
        notification = new Notification(this);

        infoReceived();
    }

    @OnClick(R.id.bmodify)
    public void onClickModify(){
        if(notif_id < 100){
            String title_update = ettitle.getText().toString();
            String note_update = etnote.getText().toString();
            dbconnection.updateData(note_id, title_update, note_update);
            notification.deleteNotify(notif_id);
            notification.makeNotify(notif_id, title_update, note_update);
            this.returnHome();
        } else {
            String title_update = ettitle.getText().toString();
            String note_update = etnote.getText().toString();
            dbconnection.updateData(note_id, title_update, note_update);
            this.returnHome();
        }
        dbconnection.closeDatabases();
    }

    @OnClick(R.id.bdelete)
    public void onClickDelete(){
        notification.deleteNotify(notif_id);
        dbconnection.deleteData(note_id);
        dbconnection.closeDatabases();
        this.returnHome();
    }

    public void returnHome() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void infoReceived(){
        dbconnection.openDatabases();

        ettitle = (EditText) findViewById(R.id.ettitlever);
        etnote = (EditText) findViewById(R.id.etnotever);

        Intent i = getIntent();
        String noteID = i.getStringExtra("noteID");
        String notetitle = i.getStringExtra("notetitle");
        String note = i.getStringExtra("note");
        String notif = i.getStringExtra("notif");

        note_id = Integer.parseInt(noteID);
        notif_id = Integer.parseInt(notif);

        ettitle.setText(notetitle);
        etnote.setText(note);
    }
}
