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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class WorkOrderDetailsActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private TextView welcomeDetailsId;
    private EditText detailsDescriptionId, editableDescriptionId, chargedId;
    private Button saveId, returnId;
    private Spinner spinnerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_details);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        String firstname = getIntent().getStringExtra("firstname");
        String lastname = getIntent().getStringExtra("lastname");
        int id = getIntent().getIntExtra("id", 0);
        editableDescriptionId = (EditText) findViewById(R.id.editableDescriptionId);
        detailsDescriptionId = (EditText) findViewById(R.id.detailsDescriptionId);
        chargedId = (EditText) findViewById(R.id.chargedId);
        welcomeDetailsId = (TextView) findViewById(R.id.welcomeDetailsId);
        saveId = (Button) findViewById(R.id.saveId);
        returnId = (Button) findViewById(R.id.returnId);
        spinnerId = (Spinner) findViewById(R.id.spinnerId);
        viewModel.getDetails(id).observe(this, new Observer<WorkOrderEntity>() {
            @Override
            public void onChanged(WorkOrderEntity workOrderEntity) {
                welcomeDetailsId.setText("ID: " + workOrderEntity.getId() + ", Customer: " + workOrderEntity.getCustomerName());
                detailsDescriptionId.setText(workOrderEntity.getDetailedProblemDescription());
                editableDescriptionId.setText(workOrderEntity.getRepairInformation());
                String amount = chargedId.getText().toString();
                if (!amount.matches("[0-9]+")) {
                    Toast.makeText(getApplicationContext(), "Please fill in a round number", Toast.LENGTH_SHORT).show();
                    if (amount.matches("[0-9]+")) {
                        chargedId.setText(amount);
                    }

                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.planets_array, android.R.layout.simple_spinner_item);
                    spinnerId.setAdapter(adapter);


                    if (spinnerId.getSelectedItemPosition() == 0)
                        workOrderEntity.setPaymentMethod("Cash");
                    if (spinnerId.getSelectedItemPosition() == 1)
                        workOrderEntity.setPaymentMethod("Payconiq");
                    if (spinnerId.getSelectedItemPosition() == 2)
                        workOrderEntity.setPaymentMethod("Bancontact");

                    if (workOrderEntity.getProcessed() == true) {
                        editableDescriptionId.setEnabled(false);
                        chargedId.setText(workOrderEntity.getCharged());
                        chargedId.setEnabled(false);
                        spinnerId.setEnabled(false);
                        saveId.setVisibility(View.INVISIBLE);

                        if (workOrderEntity.getPaymentMethod() == "Cash") {
                            spinnerId.setSelection((int) adapter.getItemId(0));
                        }
                        if (workOrderEntity.getPaymentMethod() == "Payconiq") {
                            spinnerId.setSelection((int) adapter.getItemId(1));
                        }
                        if (workOrderEntity.getPaymentMethod() == "Bancontact") {
                            spinnerId.setSelection((int) adapter.getItemId(2));
                        }
                        //spinnerId.setSelection((int) adapter.getItemId(0));
                    }
                    saveId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!editableDescriptionId.getText().toString().isEmpty() || !chargedId.getText().toString().isEmpty()) {
                                String problemDesription = editableDescriptionId.getText().toString();
                                String chargedAmount = chargedId.getText().toString();
                                if (workOrderEntity.getPaymentMethod() == "Cash") {
                                    spinnerId.setSelection((int) adapter.getItemId(0));
                                    spinnerId.setEnabled(false);
                                }
                                if (workOrderEntity.getPaymentMethod() == "Payconiq") {
                                    spinnerId.setSelection((int) adapter.getItemId(1));
                                    spinnerId.setEnabled(false);
                                }
                                if (workOrderEntity.getPaymentMethod() == "Bancontact") {
                                    spinnerId.setSelection((int) adapter.getItemId(2));
                                    spinnerId.setEnabled(false);
                                }
                                workOrderEntity.setRepairInformation(problemDesription);
                                workOrderEntity.setCharged(chargedAmount);
                                workOrderEntity.setProcessed(true);

                                viewModel.updateWorkOrder(workOrderEntity);
                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                Intent saveIntent = new Intent(WorkOrderDetailsActivity.this, HomescreenAcitivity.class);
                                saveIntent.putExtra("name", firstname);
                                saveIntent.putExtra("lastname", lastname);
                                startActivity(saveIntent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Fill in all values before saving!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                    returnId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent returnIntent = new Intent(WorkOrderDetailsActivity.this, HomescreenAcitivity.class);
                            returnIntent.putExtra("name", firstname);
                            returnIntent.putExtra("lastname", lastname);
                            startActivity(returnIntent);
                        }
                    });
                }
            }
        });

    }
}
