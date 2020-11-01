package com.example.pocketbook.activity;


import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.pocketbook.fragment.HomeFragment;
import com.example.pocketbook.fragment.AddFragment;
import com.example.pocketbook.fragment.ProfileFragment;
import com.example.pocketbook.fragment.ScanFragment;
import com.example.pocketbook.R;
import com.example.pocketbook.fragment.SearchFragment;
import com.example.pocketbook.fragment.ViewBookFragment;
import com.example.pocketbook.model.BookList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.example.pocketbook.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mStorageRef;
    private Button buttonUploadImg;
    private String name;
    private ImageView image;
    private ArrayList<String> pathArray;
    private int array_position;
    private FirebaseUser currentUser;
    String email;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("CURRENT_USER");
//        Toast.makeText(this,user.getFirstName(),Toast.LENGTH_SHORT).show();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(NavListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                HomeFragment.newInstance(user, new BookList())).commit();
    }

    @Override //temporary until we find a way to make the back button work properly
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                HomeFragment.newInstance(user, new BookList())).commit();
    }
  
    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.bottom_nav_home:
                            selectedFragment = HomeFragment.newInstance(user, new BookList());
                            break;
                        case R.id.bottom_nav_search:
                            selectedFragment = SearchFragment.newInstance(user, new BookList());
                            break;
                        case R.id.bottom_nav_add:
                            selectedFragment = AddFragment.newInstance(user, new BookList());
                            break;
                        case R.id.bottom_nav_scan:
                            selectedFragment = new ScanFragment();
                            break;
                        case R.id.bottom_nav_profile:
                            selectedFragment = ProfileFragment.newInstance(user);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
                    return true;
                }
            };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}


