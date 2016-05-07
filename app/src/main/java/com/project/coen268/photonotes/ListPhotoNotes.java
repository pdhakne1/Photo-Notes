package com.project.coen268.photonotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListPhotoNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListPhotoNotes.this, AddPhotoNote.class);
                startActivity(intent);
            }
        });
        populateList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this,"Toast called",Toast.LENGTH_LONG).show();
        populateList();

    }

    public void populateList()
    {
        List<PhotoNote> photoNoteList = new ArrayList<>();
        PhotoNoteDBHelper photoNoteDBHelper = new PhotoNoteDBHelper(this);
        photoNoteList = photoNoteDBHelper.getPhotoNoteObject();
        final ListView listView = (ListView) findViewById(R.id.custom_list_view);
        listView.setAdapter(new CustomAdapter(this, R.layout.card_view, photoNoteList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoNote selectedPhotoNote = (PhotoNote) listView.getAdapter().getItem(position);
                Intent intent = new Intent(ListPhotoNotes.this,ViewPhotoNote.class);
                intent.putExtra("selectedPhotoNoteObj", selectedPhotoNote);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_photo_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Uri AppToDelete = Uri.parse("package:com.project.coen268.photonotes");
            Intent RemoveApp = new Intent(Intent.ACTION_DELETE, AppToDelete);
            startActivity(RemoveApp);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
