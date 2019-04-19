package rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Start {

	private static Random r = new Random();
	private static char[][] mars;
	private static int zeilen  = 20;
	private static int spalten = 80;


	public static void main(String[] args) {

		if (args.length > 1) {
			long seed = Long.parseLong(args[1]);
			r.setSeed(seed);
		}

		initField();
		String eingabe = args[0];
		drawOutput();

		for (int i = 0; i < eingabe.length(); i++) {
			directTheRover(eingabe.charAt(i));
			makeMovement(eingabe.charAt(i));
			drawOutput();
		}

	}

	private static void initField() {

		int mitteZeilen  = zeilen/2;
		int mitteSpalten = spalten/2;
        mars = new char[zeilen][spalten];


        for (int i = 0; i < zeilen; i++) {
			for (int j = 0; j < spalten; j++) {
				if (r.nextDouble() < 0.25 && !(mitteZeilen == i && mitteSpalten == j)) {
					mars[i][j]= '#';
				}else{
					mars[i][j]= ' ';
				}
			}
		}
		mars[mitteZeilen][mitteSpalten]= '^';
	}

	private static void drawOutput() {

		for (int j = 0; j < zeilen; j++) {
			for (int i = 0; i < spalten; i++) {
				System.out.print(mars[j][i]);
			}
			System.out.println();
		}
		for(int i = 0; i < spalten; i++) {
			System.out.print("=");
		}
		System.out.println();
	}

	private static boolean isGoodPosition( int zeile , int spalte ){

		if(mars[zeile][spalte]=='#')
			return false;
		return true;
	}


	private static void makeMovement( char eingabe){

        int[] roverPosition = findeRover();
        int zeile = roverPosition[0];
        int spalte = roverPosition[1];

        char direction = mars[zeile ][spalte];

		if( eingabe == 'f') {
            if(direction =='<' && isGoodPosition(zeile  , spalte-1) ) {
                mars[zeile ][spalte-1]= direction;
                mars[zeile ][spalte] = ' ';
            }
            else if(direction =='>' && isGoodPosition(zeile  , spalte+1) ) {
                mars[zeile ][spalte+1]= direction ;
                mars[zeile ][spalte] = ' ';
            }
            else if(direction =='^'  && isGoodPosition(zeile -1 , spalte)){
                mars[zeile-1 ][spalte]= direction;
                mars[zeile ][spalte] = ' ';
            }
            else if(direction =='v' && isGoodPosition(zeile +1 , spalte) ) {
                mars[zeile+1 ][spalte]= direction;
                mars[zeile ][spalte] = ' ';
            }
        }
		else if ( eingabe == 'b'){

            if(direction =='<' && isGoodPosition(zeile  , spalte+1) ) {
                mars[zeile ][spalte+1]= direction;
                mars[zeile ][spalte] = ' ';
            }
            else if(direction =='>' && isGoodPosition(zeile  , spalte-1) ) {
                mars[zeile ][spalte+1]= direction ;
                mars[zeile ][spalte] = ' ';
            }
            else if(direction =='^'  && isGoodPosition(zeile +1 , spalte)){
                mars[zeile+1 ][spalte]= direction;
                mars[zeile ][spalte] = ' ';
            }
            else if(direction =='v' && isGoodPosition(zeile -1 , spalte) ) {
                mars[zeile-1 ][spalte]= direction;
                mars[zeile ][spalte] = ' ';
            }

        }

    }

	private static void directTheRover(char eingabe){
		int[] roverPosition = findeRover();
		int zeile = roverPosition[0];
		int spalte = roverPosition[1];

		if(eingabe =='l'){
			if( mars[ zeile][spalte]=='^') mars[zeile][spalte]='<';
			else if( mars[ zeile][spalte]=='<') mars[zeile][spalte]='v';
			else if( mars[ zeile][spalte]=='v') mars[zeile][spalte]='>';
			else if( mars[ zeile][spalte]=='>') mars[zeile][spalte]='^';
		}
		else if(eingabe =='r'){
			if( mars[ zeile][spalte]=='^') mars[zeile][spalte]='>';
			else if( mars[ zeile][spalte]=='<') mars[zeile][spalte]='^';
			else if( mars[ zeile][spalte]=='v') mars[zeile][spalte]='<';
			else if( mars[ zeile][spalte]=='>') mars[zeile][spalte]='v';
		}

	}


	private static int[] findeRover() {

		for (int j = 0; j < zeilen; j++) {
			for (int i = 0; i < spalten; i++) {
				if (mars[j][i] != ' ' && mars[j][i] != '#')
					return new int[]{j, i};
			}
		}
		throw new IllegalStateException("Rover missing in action");
	}
}