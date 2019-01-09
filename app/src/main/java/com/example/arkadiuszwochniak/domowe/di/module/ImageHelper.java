package com.example.arkadiuszwochniak.domowe.di.module;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;

import com.example.arkadiuszwochniak.domowe.objects.Photos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ImageHelper {
    public static String saveToInternalStorage(Bitmap bitmapImage, Context c, String fileName){

        ContextWrapper cw = new ContextWrapper(c);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName+".jpg");

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String filename)
    {

        try {
            File f=new File("app_imageDir/", filename+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public static String checkPhotoExist(String title, String url, DatabaseHelper myDb, Context c){

        String path;
        myDb = new DatabaseHelper(c);
        Cursor res = myDb.getOneRow(title);

        if(res.getCount()!=0){
            path = "file:///data/data/com.example.arkadiuszwochniak.domowe/app_imageDir/"+title+".jpg";
        } else {
            path = url;
        }

        return path;
    }

}

