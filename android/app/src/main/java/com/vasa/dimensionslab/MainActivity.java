package com.vasa.dimensionslab;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.provider.MediaStore;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

        Button b = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBrowse(v);
            }
        });
    }

    public void onBrowse(View view) {
        Intent chooseFile;
        Intent intent;

        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        //chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("file/*");

        intent = Intent.createChooser(chooseFile, "choose a file");

        startActivityForResult(intent, Constants.ACTION.ACTION_GET_CONTENT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        String path = "";
        if (requestCode == Constants.ACTION.ACTION_GET_CONTENT) {
            Uri uri = data.getData();
            String FilePath = getRealPathFromUri(uri);
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
