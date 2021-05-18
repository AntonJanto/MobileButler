package me.antonjanto.mobilebutler.ui.signin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import me.antonjanto.mobilebutler.MobileNavigationDirections;
import me.antonjanto.mobilebutler.R;

public class SignInFragment extends Fragment
{
     private SignInViewModel signInViewModel;

     private Button signinButton;
     private EditText emailEditText;
     private EditText passwordEditText;
     private TextView errorTextView;

     @Nullable
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState)
     {
          View root = inflater.inflate(R.layout.fragment_signin_welcome, container, false);
          signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
          signInViewModel.init();
          return root;
     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
     {
          super.onViewCreated(view, savedInstanceState);
          findViews(view);
          signInViewModel.getShowError().observe(getViewLifecycleOwner(), b -> {
               int visibility = b ? View.VISIBLE : View.INVISIBLE;
               errorTextView.setVisibility(visibility);
          });

          signInViewModel.getSignedIn().observe(getViewLifecycleOwner(), s -> {
               if (s) {
                    navigateToOrders();
               }
          });

          signinButton.setOnClickListener((v) -> signInViewModel
               .signin(emailEditText.getText().toString(), passwordEditText.getText().toString()));
     }


     private void navigateToOrders()
     {
          NavDirections action = MobileNavigationDirections.actionGlobalNavOrders();
          NavController navController = NavHostFragment.findNavController(this);
          navController.navigate(action);
     }

     private void findViews(@NotNull View view)
     {
          signinButton = view.findViewById(R.id.signin_button);
          emailEditText = view.findViewById(R.id.signin_email);
          passwordEditText = view.findViewById(R.id.signin_password);
          errorTextView = view.findViewById(R.id.signin_error_message);
     }
}
