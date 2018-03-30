package practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private TextView showSecondEditText;
    private Button okButton, cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();

            switch (view.getId()) {
                case R.id.ok_button:
                    intent.putExtra(Constants.RESULT, Constants.RESULT_OK);
                    setResult(RESULT_OK, intent);
                    break;
                case R.id.cancel_button:
                    intent.putExtra(Constants.RESULT, Constants.RESULT_CANCEL);
                    setResult(RESULT_CANCELED, intent);
                    break;
            }
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        showSecondEditText = (TextView)findViewById(R.id.second_show_text_view);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.SHOW_DETAILS)) {
            String data = intent.getStringExtra(Constants.SHOW_DETAILS);
            showSecondEditText.setText(String.valueOf(data));
        }

        okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}
