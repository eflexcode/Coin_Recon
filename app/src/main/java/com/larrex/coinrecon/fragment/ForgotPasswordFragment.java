package com.larrex.coinrecon.fragment;

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

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.FragmentForgotPasswordBinding;
import com.larrex.coinrecon.viewmodel.LoginViewModel;


public class ForgotPasswordFragment extends Fragment {

    FragmentForgotPasswordBinding binding;
    LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false);

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

        binding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.email.getText().toString();

                if (email.trim().isEmpty()) {
                    binding.emailLayout.setError("password is Required");
                } else {
                    binding.loginContinue.startAnimation();
                    loginViewModel.doForgotPassword(email);
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

        loginViewModel.observeIsForgotPasswordSuccessful().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    getActivity().onBackPressed();
                    Toast.makeText(getContext(), "An email has be sent to the entered email adress", Toast.LENGTH_SHORT).show();
                    binding.loginContinue.revertAnimation();
                } else {
                    binding.loginContinue.revertAnimation();
                }

            }
        });

    }
}