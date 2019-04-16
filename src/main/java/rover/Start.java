package rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Start {

	private static Random r = new Random();
	private static LinkedHashMap<int[], String> mars;


	public static void main(String[] args) {

		if (args.length > 1) {
			long seed = Long.parseLong(args[1]);
			r.setSeed(seed);
		}
		initField();
		String pg = args[0];
		drawOutput();
		for (int i = 0; i < pg.length(); i++) {
			makeMovement(pg.charAt(i));
			drawOutput();
		}
	}

    private static void initField() {
		mars = new LinkedHashMap<>();
		int x = 80;
		int y = 20;
		int rx = x / 2;
		int ry = y / 2;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int[] p = new int[] { i, j };
				if (r.nextDouble() < 0.25 && !(rx == i && ry == j)) {
					mars.put(p, "#");
				}else{
                    mars.put(p, " ");
                }
			}
		}
		mars.put(new int[] { rx, ry }, "n");
	}

	private static int[] getMaximum(Set<int[]> set) {
		int[] max = new int[2];
		for (int[] e : set) {
			if (e[0] > max[0])
				max[0] = e[0];
			if (e[1] > max[1])
				max[1] = e[1];
		}
		return max;
	}

	private static String get(Map<int[], String> kloetze, int[] p) {
		Set<Entry<int[], String>> entrySet = kloetze.entrySet();
        String value ="";
		for (Entry<int[], String> entry : entrySet) {
			if (entry.getKey()[0] == p[0] && entry.getKey()[1] == p[1]) {
                value = entry.getValue();
            }
		}
        return value;
	}

	private static void drawOutput() {

		int[] max = getMaximum(mars.keySet());
		for (int j = 0; j < max[1]; j++) {
			for (int i = 0; i < max[0]; i++) {

				if (get(mars, new int[] { i, j }).equals(" ")){
					System.out.print(" ");
					continue;
				}
				if (get(mars, new int[] { i, j }).equals("#"))
					System.out.print("#");
				else if (get(mars, new int[] { i, j }).equals("n"))
					System.out.print("^");
				else if (get(mars, new int[] { i, j }).equals("s"))
					System.out.print("V");
				else if (get(mars, new int[] { i, j }).equals("e"))
					System.out.print(">");
				else if (get(mars, new int[] { i, j }).equals("w"))
					System.out.print("<");

			}
			System.out.println();
		}
		for (int i = 0; i < max[0]; i++) {
			System.out.print("=");
		}
		System.out.println();
	}


	private static void makeMovement(char c) {
		int[] p = findeRover();
		int[] tempPos = new int[] { p[0], p[1]};

		if (c == 'f') {

			if (get(mars, p).equals("n") && !get(mars, new int[] { tempPos[0], tempPos[1]-1} ).equals("#"))
				p[1]--;
			else if (get(mars, p).equals("s") && !get(mars, new int[] { tempPos[0], tempPos[1]+1} ).equals("#"))
				p[1]++;
			else if (get(mars, p).equals("e") && !get(mars, new int[] { tempPos[0]+1, tempPos[1]} ).equals("#"))
				p[0]++;
			else if (get(mars, p).equals("w") && !get(mars, new int[] { tempPos[0]-1, tempPos[1]} ).equals("#"))
				p[0]--;
		} else if (c == 'b') {

			if (get(mars, p).equals("s") && !get(mars, new int[] { tempPos[0], tempPos[1]-1} ).equals("#"))
				p[1]--;
			else if (get(mars, p).equals("n") && !get(mars, new int[] { tempPos[0], tempPos[1]+1} ).equals("#"))
				p[1]++;
			else if (get(mars, p).equals("w") && !get(mars, new int[] { tempPos[0]+1, tempPos[1]} ).equals("#"))
				p[0]++;
			else if (get(mars, p).equals("e") && !get(mars, new int[] { tempPos[0]-1, tempPos[1]} ).equals("#"))
				p[0]--;
		} else if (c == 'l') {

			if (get(mars, p).equals("n"))
				mars.put(p, "w");
			else if (get(mars, p).equals("s"))
				mars.put(p, "e");
			else if (get(mars, p).equals("e"))
				mars.put(p, "n");
			else if (get(mars, p).equals("w"))
				mars.put(p, "s");
		} else if (c == 'r') {

			if (get(mars, p).equals("w"))
				mars.put(p, "n");
			else if (get(mars, p).equals("e"))
				mars.put(p, "s");
			else if (get(mars, p).equals("n"))
				mars.put(p, "e");
			else if (get(mars, p).equals("s"))
				mars.put(p, "w");
		}

	}

	private static int[] findeRover() {
		Set<Entry<int[], String>> entrySet = mars.entrySet();
		for (Entry<int[], String> entry : entrySet) {
			if (!entry.getValue().equals(" ") && !entry.getValue().equals("#"))
				return entry.getKey();
		}
		throw new IllegalStateException("Rover missing in action");
	}

}
