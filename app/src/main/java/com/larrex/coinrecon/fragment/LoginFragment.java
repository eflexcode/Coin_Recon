package com.larrex.coinrecon.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.larrex.coinrecon.MainActivity;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.FragmentLoginBinding;
import com.larrex.coinrecon.viewmodel.LoginViewModel;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

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

                if (email.trim().isEmpty()) {
                    binding.emailLayout.setError("Email is Required");
                } else if (password.trim().isEmpty()) {
                    binding.passwordLayout.setError("password is Required");
                } else {
                    binding.loginContinue.startAnimation();
                    binding.loading.setVisibility(View.VISIBLE);
                    loginViewModel.doLogin(email,password);
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


        loginViewModel.observeIsLoginSuccessful().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {

                    //go to main activity

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    binding.loginContinue.revertAnimation();
                    binding.loading.setVisibility(View.GONE);
                }
            }
        });

    }
}