package br.com.victormelo.cleanagenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditContactActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);

        Button confirm = (Button) findViewById(R.id.confirmButton);
        confirm.setOnClickListener(this);

        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.confirmButton:
                Intent resultIntent = new Intent();
                // TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("name", editName.getText().toString());
                resultIntent.putExtra("phone", editPhone.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;

            case R.id.cancelButton:
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
                break;
        }

    }
}
