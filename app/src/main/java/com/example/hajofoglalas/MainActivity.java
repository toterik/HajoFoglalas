package com.example.hajofoglalas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static boolean isLoggedin = false;
    private MenuItem loginMenuItem;
    private MenuItem logoutMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if(isLoggedin){
            MenuItem item = menu.findItem(R.id.menu_item4);
            item.setVisible(true);
            MenuItem items = menu.findItem(R.id.menu_item3);
            items.setVisible(false);

        }
        else {
            MenuItem item = menu.findItem(R.id.menu_item3);
            item.setVisible(true);
            MenuItem items = menu.findItem(R.id.menu_item4);
            items.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.menu_item1)
        {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            return true;
        } else if (id == R.id.menu_item2)
        {
            Intent intent2 = new Intent(this, HajoFoglalasActivity.class);
            startActivity(intent2);
            return true;
        }
        else if(id == R.id.menu_item3)
        {
            Intent intent2 = new Intent(this, LoginActivity.class);
            startActivity(intent2);
            return true;
        }
        else if(id == R.id.menu_item4)
        {
            logout();
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        isLoggedin = false;
    }
}
