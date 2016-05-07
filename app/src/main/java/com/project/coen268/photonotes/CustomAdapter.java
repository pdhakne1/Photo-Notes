package com.project.coen268.photonotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by pallavi on 2/12/16.
 */
public class CustomAdapter extends ArrayAdapter<PhotoNote>{

    private final List<PhotoNote> photoNoteList;
    static class ViewHolder {
        TextView textView;
        ImageView imageView;

    }

    public CustomAdapter(Context context,int resource,List<PhotoNote> photoNoteList) {
        super(context,resource,photoNoteList);
        this.photoNoteList= photoNoteList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhotoNote photoList = photoNoteList.get(position);

        View myView;
        ViewHolder holder;

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.card_view,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) myView.findViewById(R.id.rowImage);
            holder.textView = (TextView) myView.findViewById(R.id.rowText);
            myView.setTag(holder);

        }
        else {
            myView = convertView;
            holder = (ViewHolder) myView.getTag();
        }

        holder.textView.setText(photoList.getCaption());
        try{
            File imageFile = new File(photoList.getImagePath());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;  // Experiment with different sizes
            Bitmap b = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
            holder.imageView.setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myView;
    }

}
