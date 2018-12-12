package com.vasa.dimensionslab;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import java.io.File;

public class MainActivity extends Activity {

    Uri uri = null;

    Button selectFileButton = null;
    Button stlViewerButton = null;
    TextView filePathTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectFileButton = (Button)findViewById(R.id.selectfilebutton);
        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrowse(v);
            }
        });

        stlViewerButton = (Button)findViewById(R.id.stlviewerbutton);
        stlViewerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrentingSetting.class);
                intent.putExtra("stlUri", getRealPathFromUri(uri));
                startActivity(intent);
            }
        });

        filePathTextView = (TextView)findViewById(R.id.filepathtextview);
        File f = new File(Environment.getExternalStorageDirectory().getPath());
        filePathTextView.setText(Environment.getExternalStorageState() + " - " + f.getAbsolutePath());


    }

    public void onBrowse(View view) {
        Intent chooseFile;
        Intent intent;

        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        //chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("application/vnd.ms-pkistl");

        intent = Intent.createChooser(chooseFile, "choose a file");

        startActivityForResult(intent, Constants.ACTION.ACTION_GET_FILE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        String path = "";
        if (requestCode == Constants.ACTION.ACTION_GET_FILE) {
            uri = data.getData();
            path = getRealPathFromUri(uri);
            filePathTextView.setText(path);
        }
    }

    public String getRealPathFromUri(Uri contentUri) {
        String path = contentUri.getPath();
        path = path.substring(1+path.indexOf(":"));
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path;
        return path;
    }
}
