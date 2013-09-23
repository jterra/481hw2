package com.example.connect4;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity {

	boolean game = true;
	boolean turn;
	GridLayout gridLayout;
	
	static int data[][] = new int[6][7];
    static int num_turns = 0;    
	
    public void win(boolean turn){
    	TextView tv = (TextView) findViewById(R.id.text);
    	if(turn)
			tv.setText("Red wins!");
		else
			tv.setText("Black wins!");
    	game = false;
    }
    
    public void quit_method(View v){
    	finish();
    	System.exit(0);
    }
    
    public boolean row_has_win(int row, int col){
    	int color = data[row][col];
    	if(data[row][0] == color && color == data[row][1] 
    				&& color == data[row][2] && color == data[row][3]
    			|| data[row][1] == color && color == data[row][2]
    					&& color == data[row][3] && color == data[row][4]
    			|| data[row][2] == color && color == data[row][3]
    					&& color == data[row][4] && color == data[row][5]
    			|| data[row][3] == color && color == data[row][4]
    					&& color == data[row][5] && color == data[row][6]	)
    		return true;
    	else
    		return false;
    }
    
    public boolean diagonal_has_win(int row, int col){
    	int color = data[row][col];
    	int count = 1;
    	int r = row;
    	int c = col;
    	//check up-left
    	while(r > 0 && c > 0){
    		if(data[r-1][c-1] == color){
    			count++;
    			r--;
    			c--;
    		}
    		else
    			break;
    	}
    	r = row;
    	c = col;
    	
    	//check down-right
    	while(r < 5 && c < 6){
    		if(data[r+1][c+1] == color){
    			count++;
    			r++;
    			c++;
    		}
    		else
    			break;
    	}
    	if(count >= 4)
    		return true;
    	count = 1;
    	r = row;
    	c = col;
    	
    	//check down-left
    	while(r < 5 && c > 0){
    		if(data[r+1][c-1] == color){
    			count++;
    			r++;
    			c--;
    		}
    		else
    			break;
    	}
    	r = row;
    	c = col;
    	//check up-right
    	while(r > 0 && c < 6){
    		if(data[r-1][c+1] == color){
    			count++;
    			r--;
    			c++;
    		}
    		else
    			break;
    	}
    	if(count >= 4)
    		return true;
    	
    	return false;
    }
    
	public void method(View v) {
    // when clicked, checks if square is empty and above another square 
		
			ImageButton buttonView = (ImageButton)v;
			TextView tv = (TextView) findViewById(R.id.text);
			
			
			int row = Character.getNumericValue(buttonView.getTag().toString().charAt(0));
			int col = Character.getNumericValue(buttonView.getTag().toString().charAt(1));
			// i.e. a button with tag 32 will be row 3, column 2
			
			if(data[row][col] == 0 && game){
				if(row == 5 || data[row+1][col] > 0){
					//these two check if square is a valid option for piece placement
					
					//at space, set data to 1 if black, 2 if red
					data[row][col] = (turn ? 1 : 0)  + 1;
					if(turn)
						buttonView.setImageResource(R.drawable.square_red);
					else
						buttonView.setImageResource(R.drawable.square_black);
					
					int move = data[row][col];
					
					//check vertically
					if(row < 3){
						if(move == data[row+1][col] && 
								move == data[row+2][col] && move == data[row+3][col]){
							win(turn);
						}
					}
					
					//check horizontal
					if(row_has_win(row, col))
						win(turn);
					
					if(diagonal_has_win(row, col))
						win(turn);
					
					
					if(game){
						turn = !turn;
						
						if(turn)
							tv.setText("Red's turn");
						else
							tv.setText("Black's turn");
						
						num_turns++;
						
						if(num_turns == 42){
							tv.setText("Draw");
							game = false;
						}
					}
				}
			}
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		gridLayout = (GridLayout)findViewById(R.id.C4Grid);
		
		// init array data
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				data[i][j] = 0;
			}
		}
		TextView tv = (TextView) findViewById(R.id.text);
		tv.setText("Black's turn");
	}
}
