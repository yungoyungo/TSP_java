import java.awt.Point;
import java.math.BigInteger;
import java.util.LinkedList;

public class TSP {

    static double minMileage = Double.POSITIVE_INFINITY;
    public static void main(String[] args) {
        int num_cities = Integer.parseInt(args[0]);
        //int num_cities = 5;
        Point[] cityLocations = new Point[num_cities];
        for (int i = 0; i < num_cities; i++) {
            //10000 * 10000 平面内のランダムな1点
            cityLocations[i] = new Point((int) (Math.random() * 10000), (int) (Math.random() * 10000));
        }

        for (int i = 0; i < cityLocations.length; i++) {
            System.out.println(cityLocations[i].toString());
        }
        //System.out.println("total : " + AtoZDistance(cityLocations));

        String num_paths_str = getPathsNum(new BigInteger(String.valueOf(num_cities - 1))).toString();
        int num_paths = Integer.parseInt(num_paths_str);
        //都市数が1以下の場合,パスが0になってしまうため（割とどうでも良い）
        if(num_cities>1){
            if(num_cities>2){
                num_paths /= 2;
            }
            startRoundRobin(cityLocations, num_cities);
            System.out.println("all paths: " + num_paths);
            System.out.println("minimal : "+TSP.minMileage);
        }
        else{
            System.out.println("都市数が2以上のときに計算します");
        }
    }

    public static void startRoundRobin(Point[] cityLocations,int num_cities){
        int x = 2;
        LinkedList<Integer> routeAry = new LinkedList<Integer>();
        for(; x <= num_cities; x++){
            routeAry.add(x);
            routeAry.add(1);
            startRoundRobin_subloop(cityLocations, num_cities, 2, routeAry);
            routeAry.clear();
        }
    }
    private static void startRoundRobin_subloop(Point[] cityLocations, int num_cities,int n,LinkedList<Integer> routeAry){
        if(n == num_cities){
            //生成したルートでの総距離を取得
            double minimal_tmp = getMileage(cityLocations, routeAry);
            if(TSP.minMileage > minimal_tmp){
                TSP.minMileage = minimal_tmp;
            }
            //System.out.println("minimal : "+TSP.minMileage);
        }
        else{
            for(int x=2; x <= num_cities; x++){
                if(!(routeAry.contains(x))){
                    if( n != 2 || (int)routeAry.getFirst() > x ){
                        routeAry.add(x);
                        startRoundRobin_subloop(cityLocations, num_cities, n+1, routeAry);
                        routeAry.removeLast();
                    }
                }
            }
        }
    }

    public static BigInteger getPathsNum(BigInteger bi) {
        if (bi.longValue() == 0) {
            return new BigInteger("1");
        }
        return bi.multiply(getPathsNum(bi.subtract(new BigInteger("1"))));
    }

    public static double getMileage(Point[] cityLocations, LinkedList<Integer> routeAry){
        double mileage = 0;
        int i = 0;
        for (; i+1 < cityLocations.length; i++) {
            //System.out.println(routeAry.get(i)+" - "+routeAry.get(i+1)+" : "+tmp);
            mileage += cityLocations[routeAry.get(i)-1].distance(cityLocations[routeAry.get(i+1)-1]);
        }
        // マサラタウンに帰る
        //System.out.println(routeAry.get(i)+" - "+routeAry.get(0)+" : "+tmp);
        mileage += cityLocations[routeAry.get(i)-1].distance(cityLocations[routeAry.get(0)-1]);
        //このルートでの総走行距離
        //System.out.println("mile: "+mileage);
        return mileage;
    }
}