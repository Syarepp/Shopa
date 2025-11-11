package com.example.shoppa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchNotification, switchPromo;
    private Spinner spinnerPrivacy, spinnerLanguage, spinnerPayment;
    private RadioGroup radioGroupTheme;
    private ImageView buttonBack;
    private Button btnLogout;

    private SharedPreferences preferences;
    private static final String PREFS_NAME = "settings_prefs";
    private static final String KEY_THEME = "app_theme";

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        applySavedTheme();
        setContentView(R.layout.activity_settings);

        session = new SessionManager(getApplicationContext());

        // Initialize all views
        initializeViews();

        // Back button click handler
        buttonBack.setOnClickListener(v -> finish());

        // Logout button click handler
        btnLogout.setOnClickListener(v -> {
            // Logout user
            session.logout();
            Toast.makeText(SettingsActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Create intent to go back to LoginActivity
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            // Clear the back stack and start LoginActivity as a new task
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Finish SettingsActivity
        });

        // Restore saved theme selection
        restoreThemeSelection();

        // Switch listeners
        setupSwitchListeners();

        // Spinner listeners
        setupSpinnerListeners();

        // Theme mode listener
        setupThemeListener();
    }

    private void initializeViews() {
        switchNotification = findViewById(R.id.switchNotification);
        switchPromo = findViewById(R.id.switchPromo);
        spinnerPrivacy = findViewById(R.id.spinnerPrivacy);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        spinnerPayment = findViewById(R.id.spinnerPayment);
        radioGroupTheme = findViewById(R.id.radioGroupTheme);
        buttonBack = findViewById(R.id.buttonBack);
        btnLogout = findViewById(R.id.btnLogout); // This was missing!
    }

    private void restoreThemeSelection() {
        String currentTheme = preferences.getString(KEY_THEME, "light");
        if (currentTheme.equals("light")) {
            ((RadioButton) findViewById(R.id.radioLight)).setChecked(true);
        } else {
            ((RadioButton) findViewById(R.id.radioDark)).setChecked(true);
        }
    }

    private void setupSwitchListeners() {
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this, "Notifications " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show());

        switchPromo.setOnCheckedChangeListener((buttonView, isChecked) ->
                Toast.makeText(this, "Promo alerts " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show());
    }

    private void setupSpinnerListeners() {
        spinnerPrivacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                // Toast.makeText(SettingsActivity.this, "Privacy: " + selected, Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                // Toast.makeText(SettingsActivity.this, "Language: " + selected, Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                // Toast.makeText(SettingsActivity.this, "Payment: " + selected, Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupThemeListener() {
        radioGroupTheme.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedTheme = findViewById(checkedId);
            String theme = selectedTheme.getText().toString();

            if (theme.equalsIgnoreCase("Light")) {
                setAppTheme("light");
            } else {
                setAppTheme("dark");
            }

            Toast.makeText(this, "Theme set to: " + theme, Toast.LENGTH_SHORT).show();
        });
    }

    private void setAppTheme(String theme) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_THEME, theme);
        editor.apply();

        if (theme.equals("light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }

    private void applySavedTheme() {
        String theme = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getString(KEY_THEME, "light");

        if (theme.equals("light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}