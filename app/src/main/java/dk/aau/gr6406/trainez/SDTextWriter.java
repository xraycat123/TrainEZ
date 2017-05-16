package dk.aau.gr6406.trainez;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Group 6404, Aalborg University, Sundhedsteknologi, 6th semester
 * @version 1.0
 */

public class SDTextWriter extends AppCompatActivity{

    private static final String TAG = "MEDIA";
    public String dataTosave;
    public String txtName;
    File root = null;

    public SDTextWriter(String txtName, String dataTosave, File root){
        this.dataTosave = dataTosave;
        this.txtName = txtName;
        this.root = root;
    }


    /**
     * Method to check whether external media available and writable. This is adapted from
     * http://developer.android.com/guide/topics/data/data-storage.html#filesExternal
     */
    public void checkExternalMedia() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    /**
     * Method to write ascii text characters to file on SD card. In earlier versions of Android a
     * WRITE_EXTERNAL_STORAGE permission must be added to the manifest file or this method will throw
     * a FileNotFound Exception because you won't have write permission. But not true after
     * API 18 for files in storage area of app (then no write permission required).
     */
    public void writeToSDFile() {
        root = root;
        File dir = new File(root.getAbsolutePath() + "/datafiles");
        dir.mkdirs();
        File file = new File(dir, txtName);
        // Must catch FileNotFoundException and IOException
        try {
            // change from true to one arugment if no appending is wanted
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            // Time stamp
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HHmm");
            sdf.setTimeZone(TimeZone.getDefault());
            String currentDateandTime = sdf.format(new Date());
            // Save the text from the EditText field in the txt
            pw.println(dataTosave + '\n');
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "File not found");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "I/O exception");
        }
        // tv.append("\n\nFILE WRITTEN TO:\n" + file);
    }

    private void readRaw() {
        InputStream is = this.getResources().openRawResource(R.raw.textfile);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);    // 2nd arg is buffer size

        // More efficient (less readable) implementation of above is the composite expression
        //    BufferedReader br = new BufferedReader(new InputStreamReader(
        //             this.getResources().openRawResource(R.raw.textfile)), 8192);
        try {
            String test;
            while (true) {
                test = br.readLine();
                // readLine() returns null if no more lines in the file
                if (test == null) break;
            }
            isr.close();
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
