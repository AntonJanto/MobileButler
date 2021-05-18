package me.antonjanto.mobilebutler;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import me.antonjanto.mobilebutler.repository.OrderRepositoryImpl;

public class MainActivity extends AppCompatActivity
{

     private AppBarConfiguration mAppBarConfiguration;
     private FirebaseAuth mAuth;
     private NavController navController;

     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          Toolbar toolbar = findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);
          DrawerLayout drawer = findViewById(R.id.drawer_layout);
          NavigationView navigationView = findViewById(R.id.nav_view);

          // Passing each menu ID as a set of Ids because each
          // menu should be considered as top level destinations.
          mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_orders, R.id.nav_signin).setOpenableLayout(drawer).build();
          navController = Navigation.findNavController(this, R.id.nav_host_fragment);
          navController.addOnDestinationChangedListener((controller, destination, arguments) ->
          {
               if (destination.getId() == R.id.nav_signin)
               {
                    toolbar.setVisibility(View.GONE);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
               }
               else {
                    toolbar.setVisibility(View.VISIBLE);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
               }
          });
          NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
          NavigationUI.setupWithNavController(navigationView, navController);

          navigationView.getMenu().findItem(R.id.nav_signout).setOnMenuItemClickListener(item ->
          {
               NavDirections directions = MobileNavigationDirections.actionGlobalNavSignin();
               navController.navigate(directions);
               return false;
          });

          //Authorization
          mAuth = FirebaseAuth.getInstance();

          FirebaseUser currentUser = mAuth.getCurrentUser();

          if (currentUser != null)
          {
               NavDirections directions = MobileNavigationDirections.actionGlobalNavOrders();
               navController.navigate(directions);
          }

          OrderRepositoryImpl.getInstance(getApplication());
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu)
     {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.options_main, menu);
          return true;
     }

     @Override
     public boolean onSupportNavigateUp()
     {
          NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
          return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
     }

}