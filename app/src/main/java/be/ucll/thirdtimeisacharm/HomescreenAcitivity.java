package be.ucll.thirdtimeisacharm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class HomescreenAcitivity extends AppCompatActivity {

    private TextView welcomeId, Cityid, DeviceId, Problem_codeId, NameId, ProcessedId;
    private TableLayout tableLayout;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen_acitivity);
        String firstname = getIntent().getStringExtra("name");
        String firstcapital = capitalFirst(firstname, firstname);
        String lastname = getIntent().getStringExtra("lastname");
        String lastcapital = capitalFirst(lastname, lastname);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        welcomeId = (TextView) findViewById(R.id.welcomeId);
        welcomeId.setText("Welcome " + firstcapital + " " + lastcapital);
        Cityid = (TextView) findViewById(R.id.Cityid);
        DeviceId = (TextView) findViewById(R.id.DeviceId);
        Problem_codeId = (TextView) findViewById(R.id.Problem_codeId);
        NameId = (TextView) findViewById(R.id.NameId);
        ProcessedId = (TextView) findViewById(R.id.ProcessedId);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getWorkOrderUser(firstcapital).observe(this, new Observer<List<WorkOrderEntity>>() {
            @Override
            public void onChanged(List<WorkOrderEntity> workOrderEntities) {
                for (WorkOrderEntity workOrderEntity : workOrderEntities) {
                    TableRow tableRow = new TableRow(HomescreenAcitivity.this);
                    TextView customerName = new TextView(HomescreenAcitivity.this);
                    TextView device = new TextView(HomescreenAcitivity.this);
                    TextView city = new TextView(HomescreenAcitivity.this);
                    TextView problem_code = new TextView(HomescreenAcitivity.this);
                    TextView processed = new TextView(HomescreenAcitivity.this);

                    int orderID = workOrderEntity.getId();

                    customerName.setGravity(Gravity.LEFT);
                    device.setGravity(Gravity.CENTER);
                    city.setGravity(Gravity.LEFT);
                    problem_code.setGravity(Gravity.CENTER);
                    processed.setGravity(Gravity.CENTER);


                    device.setText(workOrderEntity.getDevice());
                    customerName.setText(workOrderEntity.getCustomerName());
                    city.setText(workOrderEntity.getCity());
                    problem_code.setText(workOrderEntity.getProblemCode());

                    tableRow.addView(customerName);
                    tableRow.addView(city);
                    tableRow.addView(device);
                    tableRow.addView(problem_code);
                    tableRow.addView(processed);

                    if (workOrderEntity.getProcessed() == false) {
                        processed.setBackgroundColor(Color.RED);
                        processed.setText("NO");
                    } else {
                        processed.setBackgroundColor(Color.GREEN);
                        processed.setText("YES");
                    }
                    customerName.setLayoutParams(layoutParams);
                    device.setLayoutParams(layoutParams);
                    problem_code.setLayoutParams(layoutParams);
                    city.setLayoutParams(layoutParams);
                    processed.setLayoutParams(layoutParams);
                    tableRow.setPadding(0, 10, 5, 10);
                    tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                    processed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(HomescreenAcitivity.this, WorkOrderDetailsActivity.class);
                            intent.putExtra("id", orderID);
                            intent.putExtra("customer", String.valueOf(customerName));
                            intent.putExtra("firstname", firstname);
                            intent.putExtra("lastname", lastname);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }

    public String capitalFirst(String text, String upperstring) {
        if (!text.isEmpty()) {
            upperstring = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        }
        return upperstring;
    }
}