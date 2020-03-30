package andrew.DDC.front;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import andrew.DDC.R;
import andrew.DDC.back.TowerTypes;

public class TdGame extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_td_game);
        setupButtons();

        ArenaView a = (ArenaView) findViewById(R.id.ArenaView);
        a.setup(10, 10);
    }

    public void setupButtons() {
        findViewById(R.id.ib_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Base, 100);
            }
        });
        findViewById(R.id.ib_basic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Basic, 100);
            }
        });
        findViewById(R.id.ib_gauss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Gauss, 100);
            }
        });
        findViewById(R.id.ib_radar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Radar, 100);
            }
        });
        findViewById(R.id.ib_aa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Aa, 100);
            }
        });
    }

    public void setInfoText(TowerTypes tt, int hp) {
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_desc = findViewById(R.id.tv_desc);
        TextView tv_data = findViewById(R.id.tv_data);
        TextView tv_humour = findViewById(R.id.tv_humour);

        int cost = 50;
        switch (tt) {
            default:
                tv_name.setText(getString(R.string.base_name));
                tv_desc.setText(getString(R.string.base_desc));
                tv_humour.setText(getString(R.string.base_flav));
                break;
            case Basic:
                tv_name.setText(getString(R.string.basic_name));
                tv_desc.setText(getString(R.string.basic_desc));
                tv_humour.setText(getString(R.string.basic_flav));
                cost = 70;
                break;
            case Gauss:
                tv_name.setText(getString(R.string.gauss_name));
                tv_desc.setText(getString(R.string.gauss_desc));
                tv_humour.setText(getString(R.string.gauss_flav));
                cost = 100;
                break;
            case Radar:
                tv_name.setText(getString(R.string.radar_name));
                tv_desc.setText(getString(R.string.radar_desc));
                tv_humour.setText(getString(R.string.radar_flav));
                cost = 110;
                break;
            case Aa:
                tv_name.setText(getString(R.string.aa_name));
                tv_desc.setText(getString(R.string.aa_desc));
                tv_humour.setText(getString(R.string.aa_flav));
                cost = 80;
                break;
        }
        tv_data.setText(String.format(getString(R.string.placeholder_data), hp, cost));

    }


}
