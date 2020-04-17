package andrew.DDC.game.front;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import andrew.DDC.R;

public class TdMenuActivity extends AppCompatActivity {
    private int size = 16;
    private boolean hardmode = false;
    private boolean random = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_td_menu);

        final Intent i = new Intent(TdMenuActivity.this, TdGameActivity.class);

        Switch onOffSwitch = findViewById(R.id.sw_hardmode);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hardmode = isChecked;
            }
        });

        onOffSwitch = findViewById(R.id.sw_random);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                random = isChecked;
            }
        });

        findViewById(R.id.bt_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ((EditText) findViewById(R.id.et_size)).getText().toString();
                size = Integer.parseInt(str);

                i.putExtra("size",size);
                i.putExtra("hardmode",hardmode);
                i.putExtra("random",random);
                startActivity(i);
                finish();
            }
        });
    }
}
