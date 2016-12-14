package r12;

import java.util.HashMap;

public class heatMapCalc {

    private HashMap<String, heatMapObject> heatMapResource = new HashMap();
    private int[][] enemyBoard;
    private int[] ships;

    public heatMapCalc() {
        heatMapResource.put("0-0", new heatMapObject(new int[]{0, 0, 0, 0}));
        heatMapResource.put("0-1", new heatMapObject(new int[]{1, 0, 0, 0}));
        heatMapResource.put("0-2", new heatMapObject(new int[]{1, 1, 0, 0}));
        heatMapResource.put("0-3", new heatMapObject(new int[]{1, 1, 1, 0}));
        heatMapResource.put("0-4", new heatMapObject(new int[]{1, 1, 1, 1}));

        heatMapResource.put("1-1", new heatMapObject(new int[]{2, 1, 0, 0}));
        heatMapResource.put("1-2", new heatMapObject(new int[]{2, 2, 1, 0}));
        heatMapResource.put("1-3", new heatMapObject(new int[]{2, 2, 2, 1}));
        heatMapResource.put("1-4", new heatMapObject(new int[]{2, 2, 2, 2}));

        heatMapResource.put("2-2", new heatMapObject(new int[]{2, 3, 2, 1}));
        heatMapResource.put("2-3", new heatMapObject(new int[]{2, 3, 3, 2}));
        heatMapResource.put("2-4", new heatMapObject(new int[]{2, 3, 3, 3}));

        heatMapResource.put("3-3", new heatMapObject(new int[]{2, 3, 4, 3}));
        heatMapResource.put("3-4", new heatMapObject(new int[]{2, 3, 4, 4}));

        heatMapResource.put("4-4", new heatMapObject(new int[]{2, 3, 4, 5}));
    }

    public int[][] getHeatMap(int[][] board, int[] ships) {
        int[][] boardHeatMap = new int[10][10];
        enemyBoard = board;
        this.ships = ships;
        int n;
        int s;
        int e;
        int w;
        int heatPoints;
        System.out.println("Down");
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (enemyBoard[x][y] == 1) {
                    boardHeatMap[x][y] = 0;
                    continue;
                }
                s = checkDown(x, y);
                n = checkUp(x, y);
                e = checkRight(x, y);
                w = checkLeft(x, y);
                heatPoints = getHeatPoints(n, s, e, w);
                boardHeatMap[x][y] = heatPoints;
            }
        }
        return boardHeatMap;

    }

    private int getHeatPoints(int n, int s, int e, int w) {
        int points = 0;
        int[] tempNS = new int[4];
        int[] tempEW = new int[4];
        if (n > s) {
            tempNS = heatMapResource.get(s + "-" + n).getArray();
        } else {
            tempNS = heatMapResource.get(n + "-" + s).getArray();
        }
        if (w > e) {
            tempEW = heatMapResource.get(e + "-" + w).getArray();
        } else {
            tempEW = heatMapResource.get(w + "-" + e).getArray();
        }
        for (int i = 0; i < 4; i++) {
            points += ships[i] * tempNS[i];
            points += ships[i] * tempEW[i];
        }
        return points;
    }

    private int checkUp(int x, int y) {
        int spots = 0;
        int tempX = x;
        int tempY = y;
        while (true) {
            tempY++;
            if (tempY > 9 || enemyBoard[tempX][tempY] == 1) {
                break;
            }
            spots++;
        }
        if (spots > 4) {
            spots = 4;
        }
        return spots;
    }

    private int checkDown(int x, int y) {
        int spots = 0;
        int tempX = x;
        int tempY = y;
        while (true) {
            tempY--;
            if (tempY < 0 || enemyBoard[tempX][tempY] == 1) {
                break;
            }
            spots++;
        }
        if (spots > 4) {
            spots = 4;
        }
        return spots;
    }

    private int checkLeft(int x, int y) {
        int spots = 0;
        int tempX = x;
        int tempY = y;
        while (true) {
            tempX--;
            if (tempX < 0 || enemyBoard[tempX][tempY] == 1) {
                break;
            }
            spots++;
        }
        if (spots > 4) {
            spots = 4;
        }
        return spots;
    }

    private int checkRight(int x, int y) {
        int spots = 0;
        int tempX = x;
        int tempY = y;
        while (true) {
            tempX++;
            if (tempX > 9 || enemyBoard[tempX][tempY] == 1) {
                break;
            }
            spots++;
        }
        if (spots > 4) {
            spots = 4;
        }
        return spots;
    }

}
