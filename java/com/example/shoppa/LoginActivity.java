package com.example.shoppa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etFullName, etConfirmPassword;
    private Button btnLoginSignup;
    private TextView tvSwitchMode, tvTitle;

    private boolean isLoginMode = true;
    private UserDBHelper userDBHelper;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize DB Helper and Session Manager
        userDBHelper = new UserDBHelper(this);
        session = new SessionManager(getApplicationContext());

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etFullName = findViewById(R.id.etFullName);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnLoginSignup = findViewById(R.id.btnLoginSignup);
        tvSwitchMode = findViewById(R.id.tvSwitchMode);
        tvTitle = findViewById(R.id.tvTitle);

        // Set initial UI state
        updateUI();

        // Set click listener for the main button
        btnLoginSignup.setOnClickListener(v -> handleLoginSignup());

        // Set click listener for switching mode
        tvSwitchMode.setOnClickListener(v -> {
            isLoginMode = !isLoginMode;
            updateUI();
        });
    }

    /**
     * Updates the UI elements based on whether the user is logging in or signing up.
     */
    private void updateUI() {
        if (isLoginMode) {
            tvTitle.setText(getString(R.string.login));
            etFullName.setVisibility(View.GONE);
            etConfirmPassword.setVisibility(View.GONE);
            btnLoginSignup.setText(getString(R.string.login));
            tvSwitchMode.setText(R.string.prompt_signup);
        } else {
            tvTitle.setText(getString(R.string.signup));
            etFullName.setVisibility(View.VISIBLE);
            etConfirmPassword.setVisibility(View.VISIBLE);
            btnLoginSignup.setText(getString(R.string.signup));
            tvSwitchMode.setText(R.string.prompt_login);
        }
    }

    /**
     * Handles the click on the main button, routing to login or signup.
     */
    private void handleLoginSignup() {
        if (isLoginMode) {
            loginUser();
        } else {
            signupUser();
        }
    }

    /**
     * Validates input and attempts to log in the user.
     */
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_email_required));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_password_required));
            return;
        }

        // Check user in SQLite
        if (userDBHelper.checkUser(email, password)) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

            // Create login session
            session.setLogin(email);

            // Navigate to the main activity
            goToMainActivity();
        } else {
            Toast.makeText(this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Validates input and attempts to sign up the user.
     */
    private void signupUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError(getString(R.string.error_name_required));
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_email_required));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_password_required));
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.error_confirm_password));
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.error_password_mismatch));
            return;
        }

        // Check if email already exists
        if (userDBHelper.checkEmailExists(email)) {
            Toast.makeText(this, getString(R.string.error_email_exists), Toast.LENGTH_SHORT).show();
        } else {
            // Add user to database
            boolean isAdded = userDBHelper.addUser(fullName, email, password);
            if (isAdded) {
                Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show();

                // Create login session
                session.setLogin(email);

                // Navigate to the main activity
                goToMainActivity();
            } else {
                Toast.makeText(this, "Error during signup. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Navigates to the MainActivity and clears the back stack.
     */
    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // Clear the back stack so the user can't go back to the login page
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the LoginActivity
    }
}