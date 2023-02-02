package com.sidroded.todolist;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sidroded.todolist.auth.login.LoginLayout;
import com.sidroded.todolist.calendar.CalendarFragment;
import com.sidroded.todolist.friends.FriendsFragment;
import com.sidroded.todolist.settings.SettingsFragment;
import com.sidroded.todolist.user.User;

public class MainActivity extends AppCompatActivity{
private FirebaseAuth mAuth;
User user;
BottomNavigationView bottomNavigationView;
CalendarFragment calendarFragment = new CalendarFragment();
SettingsFragment settingFragment = new SettingsFragment();
FriendsFragment friendsFragment = new FriendsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        NavController navCo = navHostFragment.getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.calendar_item:
                    navCo.navigate(R.id.calendar);
                    return true;
                case R.id.friends_item:
                    navCo.navigate(R.id.friends);
                    return true;
                case R.id.settings_item:
                   navCo.navigate(R.id.settings);

                    return true;

            }
            return false;
        });

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            user=new User(currentUser.getEmail(),"",currentUser);
            currentUser.reload();

            //Toast.makeText(MainActivity.this, "Авторизація успішна", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginLayout.class);
            startActivity(intent);
        }
    }
}