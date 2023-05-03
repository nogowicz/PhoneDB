package com.example.phonedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private PhoneViewModel mPhoneViewModel;
    private PhoneListAdapter mAdapter;
    private FloatingActionButton floatingActionButton;
    private static final int REQUEST_CODE = 1;

    public static final int UPDATE_PHONE_ACTIVITY_REQUEST_CODE = 2;

    private ItemTouchHelper mItemTouchHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.phoneRecyclerView);
        mAdapter = new PhoneListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mPhoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);


        mPhoneViewModel.getAllPhones().observe(this, elements -> {
                    mAdapter.setPhoneList(elements);
        });

        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewPhoneActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        // Przygotowanie ItemTouchHelper
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // Obsługa przeciągania elementów
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Obsługa przesunięcia elementu
                int position = viewHolder.getAdapterPosition();
                Phone phone = mAdapter.getPhoneAtPosition(position);
                mPhoneViewModel.delete(phone);
            }

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        });

        // Przekazanie ItemTouchHelper do RecyclerView
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        mAdapter.setOnItemClickListener(new PhoneListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Phone phone) {
                Intent intent = new Intent(MainActivity.this, NewPhoneActivity.class);
                intent.putExtra("phoneId", phone.getId());
                intent.putExtra("producer", phone.getProducer());
                intent.putExtra("model", phone.getModel());
                intent.putExtra("androidVersion", phone.getAndroidVersion());
                intent.putExtra("website", phone.getWebsite());
                startActivityForResult(intent, UPDATE_PHONE_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pierwsza_opcja) {
            mPhoneViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String producer = data.getStringExtra("producer");
            String model = data.getStringExtra("model");
            String androidVersion = data.getStringExtra("androidVersion");
            String website = data.getStringExtra("website");

            Phone newPhone = new Phone(producer, model, androidVersion, website);
            Log.d("new data", "inActivityResult");
            mPhoneViewModel.insert(newPhone);
        }

        if(resultCode == RESULT_OK && requestCode == UPDATE_PHONE_ACTIVITY_REQUEST_CODE) {
            int phoneId = data.getIntExtra("phoneId", -1);
            String producer = data.getStringExtra("producer");
            String model = data.getStringExtra("model");
            String androidVersion = data.getStringExtra("androidVersion");
            String website = data.getStringExtra("website");
            if (phoneId != -1) {
                mPhoneViewModel.updatePhone(phoneId, producer, model, androidVersion, website);
            } else {
                Toast.makeText(getApplicationContext(), "Update error", Toast.LENGTH_LONG).show();
            }

        }
    }

}
