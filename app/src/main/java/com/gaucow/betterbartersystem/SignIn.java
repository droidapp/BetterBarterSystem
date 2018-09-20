package com.gaucow.betterbartersystem;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth auth;
    private int signInCode = 12;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        signOut();
        init();
    }
    private void init() {
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            Toast.makeText(this, "Already signed in.", Toast.LENGTH_LONG).show();
            getUserDataAndRedirect();
        } else {
            startSignInFlow();
        }
    }
    private void startSignInFlow() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setTosAndPrivacyPolicyUrls("https://example.com",
                "https://example.com")
                        .setIsSmartLockEnabled(false)
                        .build(),
                signInCode);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == signInCode) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show();
                getUserDataAndRedirect();
                finish();
            } else {
                if (response == null) {
                    showToast(getString(R.string.signin_cancelled));
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast(getString(R.string.no_internet));
                    return;
                }
                showToast(getString(R.string.unknown_error));
            }
        }
    }
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    private void getUserDataAndRedirect() {
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        String name = user.getDisplayName();
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", name);
        userInfo.put("email", email);
        db.collection("users")
                .add(userInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("SignIn", "User info stored");
                    }
                });
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("username", name);
        i.putExtra("email", email);
        startActivity(i);
        finish();
    }
    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SignIn.this, "Signed out.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
