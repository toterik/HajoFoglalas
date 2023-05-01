package com.example.hajofoglalas;

        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.content.res.TypedArray;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;

        import com.google.firebase.auth.FirebaseAuth;

        import java.util.ArrayList;


public class HajoFoglalasActivity extends AppCompatActivity {

    private RecyclerView mrRecyclerView;
    private ArrayList<Ship> mItemList;
    private ShipAdapter mAdapter;
    private int gridNumber = 1;
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


        mrRecyclerView=findViewById(R.id.recyclerView);
        mrRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));

        mItemList = new ArrayList<>();
        mAdapter = new ShipAdapter(this, mItemList);
        mrRecyclerView.setAdapter(mAdapter);

        initalizeData();
    }
    public void initalizeData()
    {
        String [] itemsList = getResources().getStringArray(R.array.Ship_names);
        String [] itemsDescription = getResources().getStringArray(R.array.Ship_description);
        String [] itemsPrice = getResources().getStringArray(R.array.Ship_price);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.ship_images);

        mItemList.clear();

        for(int i = 0; i<itemsList.length;i++)
        {
            mItemList.add(new Ship(itemsDescription[i], itemsImageResource.getResourceId(i,0),itemsList[i],itemsPrice[i]));
        }

        itemsImageResource.recycle();

        mAdapter.notifyDataSetChanged();
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
        MainActivity.isLoggedin = false;
    }

}
