package com.vasa.dimensionslab;

import java.net.URI;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.vasa.dimensionslab.parser.STLParser;
import com.vasa.dimensionslab.parser.Triangle;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PrentingSetting extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenting_setting);

        Uri uri = null;
        
        Bundle extras = getIntent().getExtras();
		uri = Uri.parse(extras.getString("stlUri"));

		getStlDetails(uri);
    }


    public void getStlDetails(Uri uri) {
    	File f = new File(uri.toString());

    	try {
    		List<Triangle> mesh = STLParser.parseSTLFile(f.toPath());

    		for (int i = 0; i < mesh.size(); i++) {
    			Log.i(Constants.TAGS.STLVIEWACT_TAG, mesh.get(i).toString());
    		}
    	} catch (IOException ex) {
    		Log.i(Constants.TAGS.STLVIEWACT_TAG, "IOException");
    	}
    }

}
