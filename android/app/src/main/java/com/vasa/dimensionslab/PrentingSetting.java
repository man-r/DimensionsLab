package com.vasa.dimensionslab;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.vasa.dimensionslab.parser.STLParser;
import com.vasa.dimensionslab.parser.Triangle;
import com.vasa.dimensionslab.parser.Vec3d;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PrentingSetting extends Activity {

    String path = null;
    Uri uri = null;


    TextView minx = null;
    TextView miny = null;
    TextView minz = null;
    TextView maxx = null;
    TextView maxy = null;
    TextView maxz = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenting_setting);

        minx = (TextView)findViewById(R.id.minx);
        miny = (TextView)findViewById(R.id.miny);
        minz = (TextView)findViewById(R.id.minz);
        maxx = (TextView)findViewById(R.id.maxx);
        maxy = (TextView)findViewById(R.id.maxy);
        maxz = (TextView)findViewById(R.id.maxz);
        
         Bundle extras = getIntent().getExtras();
         if (extras != null) {
            path = extras.getString("stlPath");
            uri = Uri.parse(extras.getString("stlUri"));
         }

        Log.i(Constants.TAGS.STLPARSER_TAG, "Parsing STL file");
        getStlDetails(path);
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
                getStlDetails(path);
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
    public void getStlDetails(String path) {
        Log.i(Constants.TAGS.STLPARSER_TAG, "Parsing STL file");
        try {
            File f = new File(path);
            Log.i(Constants.TAGS.STLPARSER_TAG, "Parsing STL file");
            List<Triangle> mesh = STLParser.parseSTLFile(f.toPath());

            double minX = 999999999;
            double minY = 999999999;
            double minZ = 999999999;
            
            double maxX = -999999999;
            double maxY = -999999999;
            double maxZ = -999999999;

            for (int i = 0; i < mesh.size(); i++) {

                Vec3d[] vec =  mesh.get(i).getVertices();
                
                for (int v = 0; v < vec.length; v++) {
                    if (vec[v].x < minX) {
                        minX = vec[v].x;
                    }
                    
                    if (vec[v].y < minY) {
                        minY = vec[v].y;
                    }

                    if (vec[v].z < minZ) {
                        minZ = vec[v].z;
                    }
                ///////////////////////////////////////////////

                    if (vec[v].x > maxX) {
                        maxX = vec[v].x;
                    }
                    
                    if (vec[v].y > maxY) {
                        maxY = vec[v].y;
                    }

                    if (vec[v].z > maxZ) {
                        maxZ = vec[v].z;
                    }
                }
                Log.i(Constants.TAGS.STLPARSER_TAG, minX + " - " + maxX);
                Log.i(Constants.TAGS.STLPARSER_TAG, minY + " - " + maxY);
                Log.i(Constants.TAGS.STLPARSER_TAG, minZ + " - " + maxZ);
            }

            minx.setText("minX: " + minX);
            miny.setText("minY: " + minY);
            minz.setText("minZ: " + minZ);
            maxx.setText("maxX: " + maxX);
            maxy.setText("maxY: " + maxY);
            maxz.setText("maxZ: " + maxZ);

        } catch (IOException ex) {
            Log.e(Constants.TAGS.STLPARSER_TAG, "IOException",ex);
        } 
    }
}
