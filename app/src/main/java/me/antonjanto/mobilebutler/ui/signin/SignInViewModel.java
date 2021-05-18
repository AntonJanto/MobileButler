package me.antonjanto.mobilebutler.ui.signin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SignInViewModel extends AndroidViewModel
{
     private FirebaseAuth mAuth;

     private MutableLiveData<Boolean> showError;
     private MutableLiveData<Boolean> signedIn;

     public SignInViewModel(@NonNull @NotNull Application application)
     {
          super(application);
     }

     public void init()
     {
          mAuth = FirebaseAuth.getInstance();
          mAuth.signOut();
          showError = new MutableLiveData<>(false);
          signedIn = new MutableLiveData<>(false);
     }

     public void signin(String emailStr, String passwordStr)
     {
          if (emailStr != null && passwordStr != null && !emailStr.isEmpty() && !passwordStr.isEmpty()) {
               showError.postValue(false);
               mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(task ->
               {
                    if (task.isSuccessful()) {
                         signedIn.postValue(true);
                    }
                    else {
                         showError.postValue(true);
                    }
               });
          }
          else {
               showError.postValue(true);
          }
     }

     public MutableLiveData<Boolean> getSignedIn()
     {
          return signedIn;
     }

     public MutableLiveData<Boolean> getShowError()
     {
          return showError;
     }
}
