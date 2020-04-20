package andrew.DDC.game.front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import andrew.DDC.R;
import andrew.DDC.core.GameThread;
import andrew.DDC.core.GameViewInterface;
import andrew.DDC.core.MessageTypes;
import andrew.DDC.game.back.TdGame;
import andrew.DDC.game.back.towers.TowerTypes;

public class TdGameActivity extends AppCompatActivity {
    GameViewInterface a;
    TdGame g;
    GameThread game;

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message m) {
            MessageTypes mt = (MessageTypes) m.getData().get("mType");
            if(mt == null) return;

            switch(mt){
                default:
                    Log.v("Warning","Unhandled message");
                    break;
                case selection:
                    TowerTypes type = (TowerTypes) m.getData().get("type");
                    int hp = m.getData().getInt("hp");
                    if(type == null) return;
                    setInfoText(type, hp);
                    break;
                case update:
                    a.update();
                    break;
                case finished:
                    Intent i = new Intent(TdGameActivity.this, TdOverActivity.class);
                    i.putExtra("score",m.getData().getInt("score"));
                    i.putExtra("wave",m.getData().getInt("wave"));
                    startActivity(i);
                    game = null;
                    finish();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_td_game);
        setupButtons();

        Bundle b = getIntent().getExtras();
        int size = b.getInt("size");

        g = new TdGame(size, b.getBoolean("hardmode"), b.getBoolean("random"));
        GameThread game = new GameThread(g, mHandler);
        game.start();
        a = findViewById(R.id.ArenaView);
        a.setup(size, game);

        setInfoText(TowerTypes.Base); //Auto pre select
    }

    public void setupButtons() {
        findViewById(R.id.ib_base).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Base);
            }
        });
        findViewById(R.id.ib_basic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Basic);
            }
        });
        findViewById(R.id.ib_gauss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Gauss);
            }
        });
        findViewById(R.id.ib_radar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Radar);
            }
        });
        findViewById(R.id.ib_aa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoText(TowerTypes.Aa);
            }
        });
    }

    public void setInfoText(TowerTypes tt) {
        setInfoText(tt, tt.getHp());
    }

    public void setInfoText(TowerTypes tt, int hp) {
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_desc = findViewById(R.id.tv_desc);
        TextView tv_data = findViewById(R.id.tv_data);
        TextView tv_humour = findViewById(R.id.tv_humour);

        g.setSelected(tt);

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
                break;
            case Gauss:
                tv_name.setText(getString(R.string.gauss_name));
                tv_desc.setText(getString(R.string.gauss_desc));
                tv_humour.setText(getString(R.string.gauss_flav));
                break;
            case Radar:
                tv_name.setText(getString(R.string.radar_name));
                tv_desc.setText(getString(R.string.radar_desc));
                tv_humour.setText(getString(R.string.radar_flav));
                break;
            case Aa:
                tv_name.setText(getString(R.string.aa_name));
                tv_desc.setText(getString(R.string.aa_desc));
                tv_humour.setText(getString(R.string.aa_flav));
                break;
        }
        tv_data.setText(String.format(getString(R.string.placeholder_data), hp, tt.getCost()));

    }
}
