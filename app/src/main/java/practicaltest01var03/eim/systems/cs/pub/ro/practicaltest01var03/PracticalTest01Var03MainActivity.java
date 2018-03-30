package practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter = new IntentFilter();
    private EditText firstEditText = null;
    private EditText secondEditText = null;
    private EditText showEditText = null;
    private CheckBox firstCheckbox = null, secondCheckbox = null;
    private Button displayButton = null, navigateButton = null;
    private int serviceStatus;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String data = "";

            if (firstCheckbox.isChecked()) {
                data += firstEditText.getText().toString();
            }

            if (secondCheckbox.isChecked()) {
                data += secondEditText.getText().toString();
            }

            showEditText.setText(data);

            if (firstCheckbox.isChecked() && secondCheckbox.isChecked()
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                intent.putExtra(Constants.SHOW_DETAILS, data);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }

        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private NavigateButtonClickListener navigatebuttonClickListener = new NavigateButtonClickListener();
    private class NavigateButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);

            String details = showEditText.getText().toString();
            intent.putExtra(Constants.SHOW_DETAILS, details);
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        firstEditText = (EditText) findViewById(R.id.first_edit_text);
        secondEditText = (EditText) findViewById(R.id.second_edit_text);
        showEditText = (EditText) findViewById(R.id.show_edit_text);

        firstCheckbox = (CheckBox) findViewById(R.id.first_checkbox);
        secondCheckbox = (CheckBox) findViewById(R.id.second_checkbox);

        displayButton = (Button)findViewById(R.id.display_information_button);
        displayButton.setOnClickListener(buttonClickListener);

        navigateButton = (Button)findViewById(R.id.navigate_to_secondary_activity_button);
        navigateButton.setOnClickListener(navigatebuttonClickListener);

        intentFilter.addAction(Constants.actionType);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.FIRST_TEXT)) {
                firstEditText.setText(savedInstanceState.getString(Constants.FIRST_TEXT));
            } else {
                firstEditText.setText("");
            }

            if (savedInstanceState.containsKey(Constants.SECOND_TEXT)) {
                secondEditText.setText(savedInstanceState.getString(Constants.SECOND_TEXT));
            } else {
                secondEditText.setText("");
            }

            if (savedInstanceState.containsKey(Constants.SHOW_TEXT)) {
                showEditText.setText(savedInstanceState.getString(Constants.SHOW_TEXT));
            } else {
                showEditText.setText("");
            }

            if (savedInstanceState.containsKey(Constants.FIRST_CHECKBOX)) {
                firstCheckbox.setChecked(savedInstanceState.getBoolean(Constants.FIRST_CHECKBOX));
            } else {
                firstCheckbox.setChecked(false);
            }
            if (savedInstanceState.containsKey(Constants.SECOND_CHECKBOX)) {
                secondCheckbox.setChecked(savedInstanceState.getBoolean(Constants.SECOND_CHECKBOX));
            } else {
                secondCheckbox.setChecked(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(Constants.FIRST_TEXT, firstEditText.getText().toString());
        savedInstanceState.putString(Constants.SECOND_TEXT, secondEditText.getText().toString());
        savedInstanceState.putString(Constants.SHOW_TEXT, showEditText.getText().toString());

        savedInstanceState.putBoolean(Constants.FIRST_CHECKBOX, firstCheckbox.isChecked());
        savedInstanceState.putBoolean(Constants.SECOND_CHECKBOX, secondCheckbox.isChecked());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState.containsKey(Constants.FIRST_TEXT)) {
            firstEditText.setText(savedInstanceState.getString(Constants.FIRST_TEXT));
        } else {
            firstEditText.setText("");
        }

        if (savedInstanceState.containsKey(Constants.SECOND_TEXT)) {
            secondEditText.setText(savedInstanceState.getString(Constants.SECOND_TEXT));
        } else {
            secondEditText.setText("");
        }

        if (savedInstanceState.containsKey(Constants.SHOW_TEXT)) {
            showEditText.setText(savedInstanceState.getString(Constants.SHOW_TEXT));
        } else {
            showEditText.setText("");
        }

        if (savedInstanceState.containsKey(Constants.FIRST_CHECKBOX)) {
            firstCheckbox.setChecked(savedInstanceState.getBoolean(Constants.FIRST_CHECKBOX));
        } else {
            firstCheckbox.setChecked(false);
        }
        if (savedInstanceState.containsKey(Constants.SECOND_CHECKBOX)) {
            secondCheckbox.setChecked(savedInstanceState.getBoolean(Constants.SECOND_CHECKBOX));
        } else {
            secondCheckbox.setChecked(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "Rezultat " + intent.getStringExtra(Constants.RESULT), Toast.LENGTH_LONG).show();
        }
    }

}
