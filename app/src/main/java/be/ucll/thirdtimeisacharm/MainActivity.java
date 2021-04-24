package be.ucll.thirdtimeisacharm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class MainActivity extends AppCompatActivity {
    private ViewModel viewModel;
    private EditText userId;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        userId = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordText = password.getText().toString();
                String userIdText = userId.getText().toString();

                viewModel.login(userIdText, passwordText).observe(MainActivity.this, new Observer<UserEntity>() {
                    @Override
                    public void onChanged(UserEntity userEntity) {
                        if (passwordText.isEmpty() || userIdText.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                        }
                        if (userEntity != null) {
                            if (!userIdText.matches("[a-zA-Z_]+") && !passwordText.matches("[a-zA-Z_]+")) {
                                Toast.makeText(getApplicationContext(), "Nice sql injection try tho.", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(MainActivity.this, HomescreenAcitivity.class);
                            intent.putExtra("user", userEntity.getUserId());
                            intent.putExtra("name", userEntity.getFirstName());
                            intent.putExtra("lastname", userEntity.getLastName());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Credentials incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}