package com.larrex.coinrecon.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class LoginRepository {


    Context context;

    public MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRegisterSuccessful = new MutableLiveData<>();

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    public LoginRepository(@ApplicationContext Context context) {
        this.context = context;
    }

    public void doLoginWhitEmailAndPassword(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                isLoginSuccessful.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isLoginSuccessful.setValue(false);
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void doRegisterEmailAndPassword(String email, String password, String name) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                CollectionReference userCollection = firebaseFirestore.collection("Users");

                String userId = authResult.getUser().getUid();

                Map<String, Object> map = new HashMap<>();
                map.put("userName", name);
                map.put("email", email);
                map.put("id", userId);

                userCollection.document(userId).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        isRegisterSuccessful.setValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isRegisterSuccessful.setValue(false);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isRegisterSuccessful.setValue(false);
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
