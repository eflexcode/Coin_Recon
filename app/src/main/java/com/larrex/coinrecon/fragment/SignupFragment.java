package com.larrex.coinrecon.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.larrex.coinrecon.MainActivity;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.FragmentSignupBinding;
import com.larrex.coinrecon.viewmodel.LoginViewModel;

import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class SignupFragment extends Fragment {

    FragmentSignupBinding binding;

    LoginViewModel loginViewModel;

    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;

    private static final String TAG = "SignupFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        init();

        return binding.getRoot();
    }

    private void init() {

        //google stuff

        googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestId()
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);

        binding.loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "App is busy", Toast.LENGTH_SHORT).show();
            }
        });

        binding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
                String name = binding.name.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();

                if (name.trim().isEmpty()) {
                    binding.nameLayout.setError("Name is Required");
                } else if (email.trim().isEmpty()) {
                    binding.emailLayout.setError("Email is Required");
                } else if (!email.contains("@") || !email.contains(".com")) {
                    binding.emailLayout.setError("Invalid email Address");
                } else if (password.trim().isEmpty()) {
                    binding.passwordLayout.setError("Password is Required");
                } else if (calculatePasswordStrength1(password) <= 1) {
                    binding.passwordLayout.setError("Weak is Required");
                } else if (!confirmPassword.equals(password)) {
                    binding.passwordLayout.setError("Not the same");
                    binding.confirmPasswordLayout.setError("Not the same");
                } else {
                    binding.loginContinue.startAnimation();
                    binding.loading.setVisibility(View.VISIBLE);
                    loginViewModel.doRegister(email, password, name);
                }

            }
        });

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.emailLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.passwordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.nameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.confirmPasswordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d(TAG, "onActivityResult: google retuned");
//                        if (result.getResultCode() == RESULT_OK) {

                        if (result.getData() == null) {
                            Log.d(TAG, "onActivityResult: data is null");
                        } else {
                            Log.d(TAG, "onActivityResult: data is not null");
                        }


                        Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());


                        try {
                            GoogleSignInAccount googleSignInAccount = googleSignInAccountTask.getResult(ApiException.class);
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                            loginViewModel.doGoogleSignIn(authCredential);

                        } catch (ApiException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onActivityResult: " + e.getLocalizedMessage());
                            Toast.makeText(getContext(), "Error from google", Toast.LENGTH_SHORT).show();
                            binding.googleSignIn.revertAnimation();
                            binding.loading.setVisibility(View.GONE);
                        }


                    }

//                    }
                });


        binding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.googleSignIn.startAnimation();
                binding.loading.setVisibility(View.VISIBLE);

                Intent intent = googleSignInClient.getSignInIntent();
                resultLauncher.launch(intent);

            }
        });

        loginViewModel.observeIsRegisterSuccessful().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {

                    //go to main activity

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    binding.loginContinue.revertAnimation();
                    binding.loading.setVisibility(View.GONE);
                }
            }
        });

        loginViewModel.observeIsGoogleSuccessful().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {

                    //go to main activity

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    binding.googleSignIn.revertAnimation();
                    binding.loading.setVisibility(View.GONE);
                }
            }
        });

    }

    public int calculatePasswordStrength1(String password) {

        int count = 0;

        boolean isCharacter = false;

        boolean isNumber = false;

        boolean isUpper = false;

        boolean isLower = false;


        for (int i = 0; i < password.length(); i++) {

            char c = password.charAt(i);

            if (!isNumber && Character.isDigit(c)) {
                isNumber = true;
                count++;
            } else if (!isCharacter && Character.isLetter(c)) {
                count++;
                isCharacter = true;
            } else if (!isUpper && Character.isUpperCase(c)) {
                count++;
                isUpper = true;
            } else if (!isLower && Character.isLowerCase(c)) {
                count++;
                isLower = true;
            }

        }

        return count;

    }

}