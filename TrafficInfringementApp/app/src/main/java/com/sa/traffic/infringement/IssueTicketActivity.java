package com.sa.traffic.infringement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IssueTicketActivity extends AppCompatActivity {

    private EditText editTextOffenseType;
    private EditText editTextLocation;
    private EditText editTextDateTime;
    private EditText editTextNotes;
    private EditText editTextAmount;
    private Button buttonSubmitTicket;

    private static final String TAG = "TicketIssuance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_ticket);

        editTextOffenseType = findViewById(R.id.editTextOffenseType);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDateTime = findViewById(R.id.editTextDateTime);
        editTextNotes = findViewById(R.id.editTextNotes);
        editTextAmount = findViewById(R.id.editTextAmount);
        buttonSubmitTicket = findViewById(R.id.buttonSubmitTicket);

        buttonSubmitTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String offenseType = editTextOffenseType.getText().toString().trim();
                String location = editTextLocation.getText().toString().trim();
                String dateTime = editTextDateTime.getText().toString().trim();
                String notes = editTextNotes.getText().toString().trim();
                String amount = editTextAmount.getText().toString().trim();

                // Basic validation (optional for placeholder, but good practice)
                if (offenseType.isEmpty() || location.isEmpty() || dateTime.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(IssueTicketActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String ticketDetails = "Offense: " + offenseType +
                                     "\nLocation: " + location +
                                     "\nDate/Time: " + dateTime +
                                     "\nAmount: R" + amount +
                                     "\nNotes: " + notes;

                // Log details
                Log.d(TAG, "Ticket Issued: \n" + ticketDetails);

                // Show Toast
                Toast.makeText(IssueTicketActivity.this, "Ticket Issued:\n" + offenseType + " at " + location, Toast.LENGTH_LONG).show();

                // Optional: Clear fields after submission
                clearForm();

                // Potentially navigate away or show success message
                // For now, just a toast and log.
            }
        });
    }

    private void clearForm() {
        editTextOffenseType.setText("");
        editTextLocation.setText("");
        editTextDateTime.setText("");
        editTextNotes.setText("");
        editTextAmount.setText("");
        editTextOffenseType.requestFocus(); // Set focus to the first field
    }
}
