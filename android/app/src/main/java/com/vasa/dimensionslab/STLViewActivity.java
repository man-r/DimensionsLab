package com.vasa.dimensionslab;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.vasa.dimensionslab.renderer.STLRenderer;
import com.vasa.dimensionslab.util.Log;
import com.vasa.dimensionslab.view.STLView;

public class STLViewActivity extends Activity {

	private STLView stlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stlview);

        Uri uri = null;
        
        Bundle extras = getIntent().getExtras();
		uri = Uri.parse(extras.getString("stlUri"));
		setUpViews(uri);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stlView != null) {
            Log.i("onResume");
            STLRenderer.requestRedraw();
            stlView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stlView != null) {
            Log.i("onPause");
            stlView.onPause();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("onRestoreInstanceState");
        Parcelable stlFileName = savedInstanceState.getParcelable("STLFileName");
        if (stlFileName != null) {
            setUpViews((Uri) stlFileName);
        }
        boolean isRotate = savedInstanceState.getBoolean("isRotate");
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.rotateOrMoveToggleButton);
        toggleButton.setChecked(isRotate);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (stlView != null) {
            Log.i("onSaveInstanceState");
            outState.putParcelable("STLFileName", stlView.getUri());
            outState.putBoolean("isRotate", stlView.isRotate());
        }
    }

    private void setUpViews(Uri uri) {
        setContentView(R.layout.activity_stlview);
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.rotateOrMoveToggleButton);
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (stlView != null) {
                    stlView.setRotate(isChecked);
                }
            }
        });

        final ImageButton preferencesButton = (ImageButton) findViewById(R.id.preferncesButton);
        preferencesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(STLViewActivity.this, PreferencesActivity.class);
                startActivity(intent);
            }
        });

        if (uri != null) {
            setTitle(uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1));

            FrameLayout relativeLayout = (FrameLayout) findViewById(R.id.stlFrameLayout);
            stlView = new STLView(this, uri);
            relativeLayout.addView(stlView);

            toggleButton.setVisibility(View.VISIBLE);

            stlView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (preferencesButton.getVisibility() == View.INVISIBLE) {
                        ;
                    }
                }
            });
        }
    }
}
