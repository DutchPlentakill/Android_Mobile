package be.ucll.thirdtimeisacharm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class WorkOrderDetailsActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private TextView welcomeDetailsId;
    private EditText detailsDescriptionId, editableDescriptionId, chargedId;
    private Button saveId, returnId;
    private Spinner spinnerId;

    private boolean textFieldsValid() {
        boolean validTextFields = true;

        if (chargedId.getText().toString().isEmpty() || editableDescriptionId.getText().toString().isEmpty()) {
            validTextFields = false;
            Toast.makeText(getApplicationContext(), "Please Fill in all values before saving!", Toast.LENGTH_LONG).show();
        }
        //Had to use regex here since parsing to an int or double crashed gave fatal errors.
        if (!chargedId.getText().toString().matches("[0-9]+")) {
            validTextFields = false;
            Toast.makeText(getApplicationContext(), "Please fill in a round number", Toast.LENGTH_SHORT).show();
        }
        if (!editableDescriptionId.getText().toString().matches("[a-zA-Z0-9., ]*(\\s)*")) {
            Toast.makeText(getApplicationContext(), "Only use allowed characters", Toast.LENGTH_SHORT).show();
            validTextFields = false;
        }
        return validTextFields;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_details);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        int id = getIntent().getIntExtra("id", 0);
        editableDescriptionId = findViewById(R.id.editableDescriptionId);
        detailsDescriptionId = findViewById(R.id.detailsDescriptionId);
        chargedId = findViewById(R.id.chargedId);
        welcomeDetailsId = findViewById(R.id.welcomeDetailsId);
        saveId = findViewById(R.id.saveId);
        returnId = findViewById(R.id.returnId);
        spinnerId = findViewById(R.id.spinnerId);
        viewModel.getDetails(id).observe(this, workOrderEntity -> {
            welcomeDetailsId.setText("ID: " + workOrderEntity.getId() + ", Customer: " + workOrderEntity.getCustomerName());
            detailsDescriptionId.setText(workOrderEntity.getDetailedProblemDescription());
            editableDescriptionId.setText(workOrderEntity.getRepairInformation());
            chargedId.setText(workOrderEntity.getCharged());

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.array,
                    android.R.layout.simple_spinner_item);
            spinnerId.setAdapter(adapter);

            if (workOrderEntity.getProcessed()) {
                editableDescriptionId.setEnabled(false);
                chargedId.setEnabled(false);
                spinnerId.setEnabled(false);
                saveId.setVisibility(View.INVISIBLE);
            }
            saveId.setOnClickListener(v -> {
                //This part does not work for some weird reason...
                if (spinnerId.getSelectedItemId() == 0)
                    workOrderEntity.setPaymentMethod("Cash");
                if (workOrderEntity.getPaymentMethod() == "Cash") {
                    spinnerId.setSelection(0);
                }
                if (spinnerId.getSelectedItemId() == 1)
                    workOrderEntity.setPaymentMethod("Payconiq");
                if (workOrderEntity.getPaymentMethod()== "Payconiq") {
                    spinnerId.setSelection(1);
                }
                if (spinnerId.getSelectedItemId() == 2)
                    workOrderEntity.setPaymentMethod("Bancontact");
                if (workOrderEntity.getPaymentMethod() == "Bancontact") {
                    spinnerId.setSelection(2);
                }

                if (textFieldsValid()) {
                    String problemDescription;
                    problemDescription = editableDescriptionId.getText().toString();
                    String amount = chargedId.getText().toString();

                    workOrderEntity.setCharged(amount);
                    workOrderEntity.setRepairInformation(problemDescription);
                    workOrderEntity.setProcessed(true);

                    viewModel.updateWorkOrder(workOrderEntity);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    Intent saveIntent = new Intent(WorkOrderDetailsActivity.this, HomescreenAcitivity.class);
                    saveIntent.putExtra("name", firstname);
                    saveIntent.putExtra("lastname", lastname);
                    startActivity(saveIntent);
                }
            });
            returnId.setOnClickListener(v -> {
                Intent returnIntent = new Intent(WorkOrderDetailsActivity.this, HomescreenAcitivity.class);
                returnIntent.putExtra("name", firstname);
                returnIntent.putExtra("lastname", lastname);
                startActivity(returnIntent);
            });
        });
    }
}

