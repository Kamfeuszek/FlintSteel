package com.example.flintsteel;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button systemNotificationsButton;
    private Button openWebsiteButton;
    private Button selectContactButton;
    private Button showDialogButton;
    private TextView contactHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationHelper.createNotificationChannels(this);
        systemNotificationsButton = findViewById(R.id.systemNotificationsButton);
        openWebsiteButton = findViewById(R.id.openWebsiteButton);
        selectContactButton = findViewById(R.id.selectContactButton);
        showDialogButton = findViewById(R.id.showDialogButton);
        contactHolder = findViewById(R.id.contactHolder);

        systemNotificationsButton.setOnClickListener(v -> {sendSystemNotify();});
        openWebsiteButton.setOnClickListener(v -> {openWebsite();});
        selectContactButton.setOnClickListener(v -> {selectContact();});
        showDialogButton.setOnClickListener(v -> {showDialog();});
    }
    public void sendSystemNotify() {
        NotificationHelper.sendNotification(1, NotificationHelper.CHANNEL_ID_DEFAULT, this, this, "Nowe powiadomienie!", "Kliknij, aby otworzyć aplikację.");
    }
    public void openWebsite() {
        Uri website = Uri.parse("https://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, website);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e("ERROR", "Brak aplikacji do obslugi intentu");
        }
    }
    public void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e("ERROR", "Brak aplikacji do obslugi intentu");
        }
    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy chcesz zamknąć aplikację")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}