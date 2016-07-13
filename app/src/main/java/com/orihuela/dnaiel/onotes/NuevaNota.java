package com.orihuela.dnaiel.onotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.orihuela.dnaiel.onotes.DataBase.SQLController;
import com.orihuela.dnaiel.onotes.notifications.Notification;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuevaNota extends AppCompatActivity {
    @BindView(R.id.editTxtTitle)
    EditText editTxtTitle;
    @BindView(R.id.editTxtNote)
    EditText editTxtNote;
    @BindView(R.id.radbtn1)
    RadioButton radbtn1;
    @BindView(R.id.radbtn2)
    RadioButton radbtn2;

    SQLController dbconnection;
    Notification notification;
    int id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_nota);

        ButterKnife.bind(this);

        dbconnection = new SQLController(this);
        dbconnection.openDatabases();

        notification = new Notification(this);
    }

    @OnClick(R.id.bsave)
    public void onClick() {
        String title = editTxtTitle.getText().toString();
        String note = editTxtNote.getText().toString();
        if (radbtn1.isChecked() && !title.equals("") && !note.equals("")) {
            id = dbconnection.readData().getCount() + 1;
            notification.makeNotify(id, title, note);
            dbconnection.insertData(title, note, id);
            dbconnection.closeDatabases();
            Intent i = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (radbtn2.isChecked() && !title.equals("") && !note.equals("")) {
            id = dbconnection.readData().getCount() + 100;
            dbconnection.insertData(title, note, id);
            dbconnection.closeDatabases();
            Intent i = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.chose, Toast.LENGTH_SHORT).show();
        }
    }
}
