package com.project.coen268.photonotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ViewPhotoNote extends AppCompatActivity {

    ImageView photoImg;
    TextView caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo_note);

        Intent intent = getIntent();
        PhotoNote selectedPhotoNote = (PhotoNote) intent.getSerializableExtra("selectedPhotoNoteObj");

        photoImg = (ImageView) findViewById(R.id.photoImage);
        caption = (TextView) findViewById(R.id.imgCaption);

        caption.setText(selectedPhotoNote.getCaption());
        //Toast.makeText(ViewPhotoNote.this, selectedPhotoNote.getCaption(), Toast.LENGTH_LONG).show();
        File imageFile = new File(selectedPhotoNote.getImagePath());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;  // Experiment with different sizes
        Bitmap b = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        photoImg.setImageBitmap(b);


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
