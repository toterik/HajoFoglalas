package com.example.hajofoglalas;

        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;


public class HajoFoglalasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajofoglalas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Enable the Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d("MainActivity", "onCreateOptionsMenu called");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item1) {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            return true;
        } else if (id == R.id.menu_item2) {
            Intent intent2 = new Intent(this, HajoFoglalasActivity.class);
            startActivity(intent2);
            return true;
        }
        else if(id == R.id.menu_item3)
        {
            Intent intent2 = new Intent(this, LoginActivity.class);
            startActivity(intent2);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

}
