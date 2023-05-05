package com.example.hajofoglalas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;


public class HajoFoglalasActivity extends AppCompatActivity {

    private RecyclerView mrRecyclerView;
    private ArrayList<Ship> mItemList;
    private ShipAdapter mAdapter;
    private int gridNumber = 1;
    private FirebaseFirestore mFireStore;
    private CollectionReference mItems;
    private ImageView imageViewAsc;
    private ImageView imageViewDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajofoglalas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle("");
        }


        mrRecyclerView = findViewById(R.id.recyclerView);
        mrRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));

        mItemList = new ArrayList<>();
        mAdapter = new ShipAdapter(this, mItemList);
        mrRecyclerView.setAdapter(mAdapter);


        mFireStore = FirebaseFirestore.getInstance();
        mItems = mFireStore.collection("Ships");

        imageViewAsc = findViewById(R.id.sortAsc);
        imageViewDesc = findViewById(R.id.sortDesc);
        imageViewAsc.setVisibility(View.INVISIBLE);


        queryData();

    }

    public void queryData() {
        mItemList.clear();

        mItems.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots)
            {
                Ship item = document.toObject(Ship.class);
                item.setId(document.getId());
                mItemList.add(item);
            }

            if (mItemList.size() == 0)
            {
                initalize();
                queryData();
            }
            mAdapter.notifyDataSetChanged();

        });

    }

    public void delete(Ship item) {
        DocumentReference ref = mItems.document(item._getId());
        ref.delete()
                .addOnSuccessListener(success -> {
                    Toast.makeText(this, "Sikeresen törölve", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, "Hiba történt a törlés közben", Toast.LENGTH_SHORT).show();
                });
        queryData();
    }



    private void initalize() {
        String[] itemsList = getResources().getStringArray(R.array.Ship_names);
        String[] itemsDescription = getResources().getStringArray(R.array.Ship_description);
        String[] itemsPrice = getResources().getStringArray(R.array.Ship_price);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.ship_images);

        for (int i = 0; i < itemsList.length; i++) {
            mItems.add(new Ship(itemsDescription[i], itemsImageResource.getResourceId(i, 0), itemsList[i], itemsPrice[i],false));
        }

        itemsImageResource.recycle();

    }
    public void sortAsc(View view)
    {
        mItemList.clear();
        mItems.orderBy("price", Query.Direction.ASCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots)
            {
                Ship item = document.toObject(Ship.class);
                item.setId(document.getId());
                mItemList.add(item);
            }
            mAdapter.notifyDataSetChanged();
        });

        imageViewAsc.setVisibility(View.INVISIBLE);
        imageViewDesc.setVisibility(View.VISIBLE);
    }
    public void sortDesc(View view)
    {
        mItemList.clear();
        mItems.orderBy("price", Query.Direction.DESCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots)
            {
                Ship item = document.toObject(Ship.class);
                item.setId(document.getId());
                mItemList.add(item);
            }
            mAdapter.notifyDataSetChanged();
        });
        imageViewDesc.setVisibility(View.INVISIBLE);
        imageViewAsc.setVisibility(View.VISIBLE);
    }


    public void booking(Ship item, int position) {
        if (!MainActivity.isLoggedin) {
            Toast.makeText(this, "Ehhez a funkcióhoz be kell, hogy jelentkezz!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            View view = mrRecyclerView.getChildAt(position);
            Button button = view.findViewById(R.id.booking);
            if (item.isFoglalt()) {
                button.setText("Lefoglal");
                item.setFoglalt(false);
            } else {
                button.setText("Foglalt");
                item.setFoglalt(true);
            }
        }

        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle("title").setContentText("body")
                .setSmallIcon(R.drawable.hajo).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (MainActivity.isLoggedin) {
            MenuItem item = menu.findItem(R.id.menu_item4);
            item.setVisible(true);
            MenuItem items = menu.findItem(R.id.menu_item3);
            items.setVisible(false);

        } else {
            MenuItem item = menu.findItem(R.id.menu_item3);
            item.setVisible(true);
            MenuItem items = menu.findItem(R.id.menu_item4);
            items.setVisible(false);

        }
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
        } else if (id == R.id.menu_item3) {
            Intent intent2 = new Intent(this, LoginActivity.class);
            startActivity(intent2);
            return true;
        } else if (id == R.id.menu_item4) {
            logout();
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        MainActivity.isLoggedin = false;
    }

}
