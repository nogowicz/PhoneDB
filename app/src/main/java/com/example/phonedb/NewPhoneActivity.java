package com.example.phonedb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewPhoneActivity extends AppCompatActivity {
    private EditText producerEditText;
    private EditText modelEditText;
    private EditText androidVersionEditText;
    private EditText websiteEditText;
    private int phoneId;
    boolean isValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone);

        producerEditText = findViewById(R.id.producerEditText);
        modelEditText = findViewById(R.id.modelEditText);
        androidVersionEditText = findViewById(R.id.androidVersionEditText);
        websiteEditText = findViewById(R.id.websiteEditText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phoneId = extras.getInt("phoneId", -1);
            String producer = extras.getString("producer");
            String model = extras.getString("model");
            String androidVersion = extras.getString("androidVersion");
            String website = extras.getString("website");

            if (!TextUtils.isEmpty(producer)) {
                producerEditText.setText(producer);
            }
            if (!TextUtils.isEmpty(model)) {
                modelEditText.setText(model);
            }
            if (!TextUtils.isEmpty(androidVersion)) {
                androidVersionEditText.setText(androidVersion);
            }
            if (!TextUtils.isEmpty(website)) {
                websiteEditText.setText(website);
            }
        }


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataAndFinish();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        Button websiteButton = findViewById(R.id.websiteButton);
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open the website in the browser
                String websiteUrl = websiteEditText.getText().toString();
                if (!TextUtils.isEmpty(websiteUrl)) {
                    if(!websiteUrl.startsWith("http://")) {
                        websiteEditText.setError(getString(R.string.website_error));
                        isValid = false;
                    }else {
                        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl));
                        startActivity(browserIntent);
                    }


                }
            }
        });
    }

    private void saveDataAndFinish() {
        String producer = producerEditText.getText().toString().trim();
        String model = modelEditText.getText().toString().trim();
        String androidVersion = androidVersionEditText.getText().toString().trim();
        String website = websiteEditText.getText().toString().trim();



        if (TextUtils.isEmpty(producer)) {
            producerEditText.setError(getString(R.string.text_input_error));
            isValid = false;
        }

        if (TextUtils.isEmpty(model)) {
            modelEditText.setError(getString(R.string.text_input_error));
            isValid = false;
        }

        if (TextUtils.isEmpty(androidVersion)) {
            androidVersionEditText.setError(getString(R.string.text_input_error));
            isValid = false;
        }

        if (TextUtils.isEmpty(website)) {
            websiteEditText.setError(getString(R.string.text_input_error));
            isValid = false;
        }

        if(!website.startsWith("http://")) {
            websiteEditText.setError(getString(R.string.website_error));
            isValid = false;
        }

        if (isValid) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("producer", producer);
            resultIntent.putExtra("model", model);
            resultIntent.putExtra("androidVersion", androidVersion);
            resultIntent.putExtra("website", website);

            if (phoneId > -1) {
                resultIntent.putExtra("phoneId", phoneId);
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

}
