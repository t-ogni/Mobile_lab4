package yakovskij.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    List<Checks> positions;
    EditText adder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adder = findViewById(R.id.adder);
        positions = new ArrayList<Checks>();
        positions.add(new Checks(findViewById(R.id.Position1Check), findViewById(R.id.Position1Count), findViewById(R.id.Position1Cost)));
        positions.add(new Checks(findViewById(R.id.Position2Check), findViewById(R.id.Position2Count), findViewById(R.id.Position2Cost)));
        positions.add(new Checks(findViewById(R.id.Position3Check), findViewById(R.id.Position3Count), findViewById(R.id.Position3Cost)));
    }

    public void addPos(View view){
        String name = adder.getText().toString();

        CheckBox chk = new CheckBox(this);
        chk.setText(name);
        EditText count = new EditText(this);
        EditText cost = new EditText(this);
        count.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        cost.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        count.setEms(10);
        cost.setEms(10);
        chk.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        count.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        cost.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));


        LinearLayout position = new LinearLayout(this);
        position.setOrientation(LinearLayout.HORIZONTAL);
        position.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        position.addView(chk);
        position.addView(count);
        position.addView(cost);

        LinearLayout layout = (LinearLayout) findViewById(R.id.Posts);
        layout.addView(position);
        positions.add(new Checks(chk, count, cost));
    }

    @SuppressLint({"DefaultLocale"})
    public void Calculate(View view) {
        StringBuilder lenta = new StringBuilder("Кассовый чек: ");
        double sum = 0;
        for(Checks position : positions){
            String name = position.chk.getText().toString();
            if (!position.chk.isChecked())
                continue;
            double col, cost;
            try {
                col = Double.parseDouble(position.count.getText().toString());
                cost = Double.parseDouble(position.cost.getText().toString());
            } catch (Exception err){
                Toast.makeText(this, "Error with " + name, Toast.LENGTH_SHORT).show();
                continue;
            }
            sum += col*cost;
            lenta.append(String.format("%s [%d]*%.2fр=%dр. ", name, (int) col,cost, (int) (cost * col)));
        }
        lenta.insert(0, String.format("Итого: %.2fр. ", sum));

        Toast.makeText(this, lenta.toString(), Toast.LENGTH_LONG).show();


    }

}