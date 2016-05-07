package com.project.coen268.photonotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhotoNote extends AppCompatActivity {

    ImageButton takeImage;
    Button saveImage;
    EditText caption;
    ImageView imgTaken;
    static final int REQUEST_TAKE_PHOTO = 1;
    File mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_note);

        caption = (EditText) findViewById(R.id.captionValue);

        takeImage = (ImageButton) findViewById(R.id.takePhoto);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPhotoPath=null;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();

                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }

        });

        saveImage = (Button) findViewById(R.id.savePhoto);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrentPhotoPath==null)
                {
                    //Toast.makeText(AddPhotoNote.this,"Cannot save empty image",Toast.LENGTH_LONG).show();
                    AlertDialog alertDialog = new AlertDialog.Builder(AddPhotoNote.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Cannot save empty image");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }

                PhotoNote photoNote = new PhotoNote(caption.getText().toString(),mCurrentPhotoPath.getAbsolutePath());
                PhotoNoteDBHelper photoNoteDBHelper = new PhotoNoteDBHelper(AddPhotoNote.this);
                photoNoteDBHelper.insertPhotoNoteObject(photoNote);
                /*Intent intent = new Intent(AddPhotoNote.this, ListPhotoNotes.class);
                startActivity(intent);*/
                finish();


            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image;
        //Toast.makeText(this,mCurrentPhotoPath.getAbsolutePath(),Toast.LENGTH_LONG).show();
        //Log.d("%s", mCurrentPhotoPath.getAbsolutePath());
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgTaken.setImageBitmap(imageBitmap);*/
            //Toast.makeText(this,"After click: "+mCurrentPhotoPath.getAbsolutePath(),Toast.LENGTH_LONG).show();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;  // Experiment with different sizes
            Bitmap b = BitmapFactory.decodeFile(mCurrentPhotoPath.getAbsolutePath(),options);
            imgTaken = (ImageView) findViewById(R.id.imageTaken);
            imgTaken.setImageBitmap(b);
        }
        else
        {
            //Toast.makeText(AddPhotoNote.this,"Result Code:"+resultCode,Toast.LENGTH_LONG).show();
            mCurrentPhotoPath=null;
            imgTaken = (ImageView) findViewById(R.id.imageTaken);
            imgTaken.setImageResource(android.R.color.transparent);
        }
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
