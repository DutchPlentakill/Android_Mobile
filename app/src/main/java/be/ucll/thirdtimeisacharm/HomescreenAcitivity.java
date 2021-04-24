package be.ucll.thirdtimeisacharm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
        tableLayout = findViewById(R.id.tableLayout);
        welcomeId = findViewById(R.id.welcomeId);
        Cityid = findViewById(R.id.Cityid);
        DeviceId = findViewById(R.id.DeviceId);
        Problem_codeId = findViewById(R.id.Problem_codeId);
        NameId = findViewById(R.id.NameId);
        ProcessedId = findViewById(R.id.ProcessedId);
        welcomeId.setText("Welcome " + firstcapital + " " + lastcapital);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getWorkOrderUser(firstcapital).observe(this, workOrderEntities -> {
            for (WorkOrderEntity workOrderEntity : workOrderEntities) {
                TableRow tableRow = new TableRow(HomescreenAcitivity.this);
                TextView customerName = new TextView(HomescreenAcitivity.this);
                TextView device = new TextView(HomescreenAcitivity.this);
                TextView city = new TextView(HomescreenAcitivity.this);
                TextView problem_code = new TextView(HomescreenAcitivity.this);
                TextView processed = new TextView(HomescreenAcitivity.this);

                int orderID = workOrderEntity.getId();

                customerName.setGravity(Gravity.START);
                device.setGravity(Gravity.CENTER);
                city.setGravity(Gravity.START);
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

                if (!workOrderEntity.getProcessed()) {
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

                processed.setOnClickListener(v -> {
                    Intent intent = new Intent(HomescreenAcitivity.this, WorkOrderDetailsActivity.class);
                    intent.putExtra("id", orderID);
                    intent.putExtra("customer", String.valueOf(customerName));
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("lastname", lastname);
                    startActivity(intent);
                });
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