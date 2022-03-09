package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] toeButtons=new Button[3][3];
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private Button resetBtn;

    private boolean firstPlayerTurn=true;
    private int round;
    private int firstPlayerPoint=0;
    private int secPlayerPoint=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1=findViewById(R.id.player1);
        textViewPlayer2=findViewById(R.id.player2);
        resetBtn=findViewById(R.id.resetBtn);

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                String buttonId="button_"+i+j;
                int resId=getResources().getIdentifier(buttonId,"id",getPackageName());
                toeButtons[i][j]=findViewById(resId);
                toeButtons[i][j].setOnClickListener(this);
            }
        }

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetEverything();
            }
        });

    }



    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        if (firstPlayerTurn){
            ((Button)v).setText("X");
        }
        else {
            ((Button)v).setText("O");
        }

        round++;
        if (checkWinner()){
            if (firstPlayerTurn){

                firstPlayerWin();
            }
            else {
                secPlayerWin();
            }
        }

        else if(round==9){
            draw();
        }
        else {
            firstPlayerTurn=!firstPlayerTurn;
        }
    }



    private void secPlayerWin() {
        secPlayerPoint++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePlayerPoints();
        resetgame();
    }

    private void firstPlayerWin() {
        firstPlayerPoint++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePlayerPoints();
        resetgame();

    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetgame();
    }




    private boolean checkWinner(){
        String [][]field=new String[3][3];

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                field[i][j]= toeButtons[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++){
            if (field[i][0].equals(field[i][1])
            && field[i][0].equals(field[i][2])
            && ! field[i][0].equals("")){

                return  true;
            }
        }

        for (int i=0;i<3;i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && ! field[0][i].equals("")){

                return  true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && ! field[0][0].equals("")){

            return  true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){

            return  true;
        }

        return false;

    }

    private void resetgame() {
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                toeButtons[i][j].setText("");
            }
        }
        round=0;
        firstPlayerTurn=true;
    }

    private void updatePlayerPoints() {
        textViewPlayer1.setText("Player 1 : "+firstPlayerPoint);
        textViewPlayer2.setText("Player 2 : "+secPlayerPoint);

    }


    private void resetEverything() {
        firstPlayerPoint=0;
        secPlayerPoint=0;
        updatePlayerPoints();
        resetgame();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("firstPlayerPoint",firstPlayerPoint);
        outState.putInt("secPlayerPoint",secPlayerPoint);
        outState.putInt("round",round);
        outState.putBoolean("firstPlayerTurn",firstPlayerTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        firstPlayerPoint=savedInstanceState.getInt("firstPlayerPoint");
        secPlayerPoint=savedInstanceState.getInt("secPlayerPoint");
        round=savedInstanceState.getInt("round");
        firstPlayerTurn=savedInstanceState.getBoolean("firstPlayerTurn");
    }
}