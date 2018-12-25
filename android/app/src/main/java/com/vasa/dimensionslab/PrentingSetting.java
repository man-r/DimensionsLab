package com.vasa.dimensionslab;

import java.net.URI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.vasa.dimensionslab.parser.STLParser;
import com.vasa.dimensionslab.parser.Triangle;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class PrentingSetting extends Activity {

    String path = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenting_setting);

         Bundle extras = getIntent().getExtras();
         if (extras != null) {
             path = extras.getString("stlUri");
         }

         Log.i(Constants.TAGS.STLVIEWACT_TAG, path);
         getPermissions();
    }

    void getPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION.READ_EXTERNAL_STORAGE);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.PERMISSION.READ_EXTERNAL_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.PERMISSION.READ_EXTERNAL_STORAGE) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            // Check if the only required permission has been granted
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                getStlDetails();
                //Intent intent = getIntent();
                //finish();
                //startActivity(intent);
            } else {
                Log.i(Constants.TAGS.STLVIEWACT_TAG, "CAMERA permission was NOT granted.");
                finish();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getStlDetails() {

        Log.i(Constants.TAGS.STLVIEWACT_TAG, "getStlDetails");
        try {
            File f = new File(path);
            Log.i(Constants.TAGS.STLVIEWACT_TAG, "getStlDetails");
            List<Triangle> mesh = STLParser.parseSTLFile(f.toPath());

            for (int i = 0; i < mesh.size(); i++) {
                Log.i(Constants.TAGS.STLVIEWACT_TAG, mesh.get(i).toString());
            }
        } catch (IOException ex) {
            Log.e(Constants.TAGS.STLVIEWACT_TAG, "IOException",ex);
        } 
    }
}
