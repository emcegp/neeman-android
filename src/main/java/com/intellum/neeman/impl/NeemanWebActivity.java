package com.intellum.neeman.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.intellum.neeman.R;

public class NeemanWebActivity extends AppCompatActivity {

    Toolbar vToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_web_activity);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        vToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        if (vToolbar != null) {
            setupToolbar();
        }

        String url = getIntent().getStringExtra("url");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.web_fragment_container, DefaultWebFragment.newInstance(url))
                .commit();
    }

    protected void setupToolbar() {
        setSupportActionBar(vToolbar);
        showToolbarUpButton(true);
    }

    protected void showToolbarUpButton(boolean isUp) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isUp);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
