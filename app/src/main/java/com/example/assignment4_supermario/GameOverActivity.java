package com.example.assignment4_supermario;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {

    private GameView game;
    private Canvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        displayGameOver(canvas);

        Button RestartBtn = (Button)findViewById(R.id.restartBtn);
        RestartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),SuperMario.class);
                //how to pass info to second screen
                startActivity(startIntent);
            }
        });

    }
    public void displayGameOver(Canvas canvas){
//            canvas = holder.lockCanvas();
//            canvas.drawARGB(255, 0, 0, 0
//            surfaceDestroyed(getHolder());
        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(140);
        canvas.drawText("Game Over!", 500, 300, paint2);
        paint2.setTextSize(100);
        canvas.drawText("Score "+game.score, 500, 800, paint2);
//            gameOver = 0;
//            score = 0;

//            holder.unlockCanvasAndPost(grid);

    }
}
