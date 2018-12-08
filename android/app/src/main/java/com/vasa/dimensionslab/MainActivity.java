package com.vasa.dimensionslab;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                Intent intent = new Intent(getApplicationContext(), STLViewActivity.class);
                intent.putExtra("stlUri", uri.toString());
                startActivity(intent);
            }
        });

        filePathTextView = (TextView)findViewById(R.id.filepathtextview);
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
            filePathTextView.setText(uri.toString());
        }
    }

    public String getRealPathFromUri(Uri contentUri) {
        String [] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor == null) {
            return null;
        }

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
