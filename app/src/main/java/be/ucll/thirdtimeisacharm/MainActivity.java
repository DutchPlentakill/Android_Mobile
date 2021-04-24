package be.ucll.thirdtimeisacharm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


public class MainActivity extends AppCompatActivity {
    private ViewModel viewModel;
    private EditText userId;
    private EditText password;
    private Button login;

    private boolean textFieldsValid() {
        boolean validTextFields = true;

        if (userId.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            validTextFields = false;
            Toast.makeText(getApplicationContext(), "Please fill in all values before proceeding!", Toast.LENGTH_LONG).show();
        }
        if (!userId.getText().toString().matches("[a-zA-Z0-9]*(\\s)*[.,]*")) {
            Toast.makeText(getApplicationContext(), "Only use allowed characters!", Toast.LENGTH_SHORT).show();
            validTextFields = false;
        }
        return validTextFields;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(v -> {
            String passwordText = password.getText().toString();
            String userIdText = userId.getText().toString();

            viewModel.login(userIdText, passwordText).observe(MainActivity.this, userEntity -> {
                if (textFieldsValid() && userEntity != null) {
                    Intent intent = new Intent(MainActivity.this, HomescreenAcitivity.class);
                    intent.putExtra("user", userEntity.getUserId());
                    intent.putExtra("name", userEntity.getFirstName());
                    intent.putExtra("lastname", userEntity.getLastName());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Credentials incorrect!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}