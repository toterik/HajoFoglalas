package com.example.hajofoglalas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        if(MainActivity.isLoggedin){
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
            logout(item.getActionView());
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        MainActivity.isLoggedin = false;
    }

    public void regisztracio(View view)
    {
        EditText emailET = findViewById(R.id.email);
        EditText passwordET = findViewById(R.id.password);
        EditText passwordagainET = findViewById(R.id.passwordagain);

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordagain = passwordagainET.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        if(!passwordagain.equals(password))
        {
            Toast.makeText(this, "A két jelszó nem egyezik!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {

                mAuth.signInWithEmailAndPassword(email,password);
                MainActivity.isLoggedin = true;

                Intent intent = new Intent(getApplicationContext(), HajoFoglalasActivity.class);
                startActivity(intent);
                finish();
            } else {
                Exception e = task.getException();
                if (e instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(this, "Ezzel az emaillel már regisztáltak!", Toast.LENGTH_SHORT).show();

                } else if (e instanceof FirebaseAuthWeakPasswordException) {
                    Toast.makeText(this, "A jelszó túl gyenge!", Toast.LENGTH_SHORT).show();

                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this, "Nem megfelelő email!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Valami hiba történt!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}