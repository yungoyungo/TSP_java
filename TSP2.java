import java.awt.Point;
import java.math.BigInteger;
import java.util.LinkedList;

public class TSP2 {

    static double minMileage = Double.POSITIVE_INFINITY;
    static int progress = 0;
    /*
     * 都市[0]と都市[1]の距離をcityDistance[0][1]に格納，みたいな cityDistance[0][1] ==
     * cityDistance[1][0]とか cityDistance[0][0] == 0とか不必要な部分がちらほら
     */
    static double[][] cityDistances = new double[32][32];

    public static void main(String[] args) {
        int num_cities = Integer.parseInt(args[0]);
        // int num_cities = 14;
        Point[] cityLocations = new Point[num_cities];
        for (int i = 0; i < num_cities; i++) {
            // 10000 * 10000 平面内のランダムな1点
            cityLocations[i] = new Point((int) (Math.random() * 10000), (int) (Math.random() * 10000));
        }

        for (int i = 0; i < cityLocations.length; i++) {
            // System.out.println(cityLocations[i].toString());
        }

        for (int i = 0; i < num_cities; i++) {
            for (int j = 0; j < num_cities; j++) {
                cityDistances[i][j] = cityLocations[i].distance(cityLocations[j]);
            }
        }

        // 14都市以下の場合，パス数を計算（14以上は無理なので）
        if (num_cities < 14) {
            String num_paths_str = getPathsNum(new BigInteger(String.valueOf(num_cities - 1))).toString();
            int num_paths = Integer.parseInt(num_paths_str);
            // 都市数が1以下の場合,パスが0になってしまうため（割とどうでも良い）
            if (num_cities > 1) {
                if (num_cities > 2) {
                    num_paths /= 2;
                }
                System.out.println("all paths: " + num_paths);
            } else {
                System.out.println("都市数が2以上のときに計算します");
            }
        }
        startRoundRobin(num_cities);
        System.out.println("minimal : " + TSP2.minMileage);
    }

    public static void startRoundRobin(int num_cities) {
        int x = 2;
        LinkedList<Integer> routeAry = new LinkedList<Integer>();
        for (; x <= num_cities; x++) {
            routeAry.add(x);
            routeAry.add(1);
            startRoundRobin_subloop(num_cities, 2, routeAry);
            routeAry.clear();
        }
        System.out.println("");
    }

    private static void startRoundRobin_subloop(int num_cities, int n, LinkedList<Integer> routeAry) {
        if (n == num_cities) {
            // System.out.println(routeAry);
            // 生成したルートでの総距離を取得
            double minimal_tmp = getMileage(routeAry);
            if (TSP2.minMileage > minimal_tmp) {
                TSP2.minMileage = minimal_tmp;
            }
            // System.out.println("minimal : "+TSP2.minMileage);
            TSP2.progress++;
            if (TSP2.progress == 4000000) {
                System.out.print("#");
                TSP2.progress = 0;
            }
        } else {
            for (int x = 2; x <= num_cities; x++) {
                if (!(routeAry.contains(x))) {
                    if (n != 2 || (int) routeAry.getFirst() > x) {
                        routeAry.add(x);
                        startRoundRobin_subloop(num_cities, n + 1, routeAry);
                        routeAry.removeLast();
                    }
                }
            }
        }
    }

    public static double getMileage(LinkedList<Integer> routeAry) {
        double mileage = 0;
        int i = 0;
        for (; i + 1 < routeAry.size(); i++) {
            mileage += cityDistances[routeAry.get(i) - 1][routeAry.get(i + 1) - 1];
            // System.out.println("mil:" + mileage);
        }
        // マサラタウンに帰る
        // System.out.println(routeAry.get(i)+" - "+routeAry.get(0)+" : "+tmp);
        mileage += cityDistances[routeAry.get(i) - 1][routeAry.get(0) - 1];
        // このルートでの総走行距離
        // System.out.println("mile: " + mileage);
        return mileage;
    }

    public static BigInteger getPathsNum(BigInteger bi) {
        if (bi.longValue() == 0) {
            return new BigInteger("1");
        }
        return bi.multiply(getPathsNum(bi.subtract(new BigInteger("1"))));
    }
}