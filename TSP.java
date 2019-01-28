import java.awt.Point;
import java.math.BigInteger;
import java.util.LinkedList;

public class TSP {

    static double minMileage = Double.POSITIVE_INFINITY;
    public static void main(String[] args) {
        int num_cities = Integer.parseInt(args[0]);
        //int num_cities = 5;
        Point[] points = new Point[num_cities];
        for (int i = 0; i < num_cities; i++) {
            points[i] = new Point((int) (Math.random() * 100), (int) (Math.random() * 100));
        }

        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i].toString());
        }
        //System.out.println("total : " + AtoZDistance(points));

        String num_paths = getPathsNum(new BigInteger(String.valueOf(num_cities - 1))).toString();
        startRoundRobin(points, num_cities);
        System.out.println("all paths: " + Integer.parseInt(num_paths)/2);
        System.out.println("minimal : "+TSP.minMileage);
    }

    // distance標準であるから使わないぞ（作った後に気付く）
    public static double p2p_distance(Point from, Point to) {
        double distance = Math.sqrt(((from.x - to.x) * (from.x - to.x)) + ((from.y - to.y) * (from.y - to.y)));
        return distance;
    }

    public static void startRoundRobin(Point[] points,int num_cities){
        int x = 2;
        LinkedList<Integer> route_ary = new LinkedList<Integer>();
        for(; x <= num_cities; x++){
            route_ary.add(x);
            route_ary.add(1);
            startRoundRobin_subloop(points, num_cities, 2, route_ary);
            route_ary.clear();
        }
    }
    private static void startRoundRobin_subloop(Point[] points, int num_cities,int n,LinkedList<Integer> route_ary){
        if(n == num_cities){
            double minimal_tmp = getMileage(points, route_ary);
            if(TSP.minMileage > minimal_tmp){
                TSP.minMileage = minimal_tmp;
            }
            //System.out.println("minimal : "+TSP.minMileage);
        }
        else{
            for(int x=2; x <= num_cities; x++){
                if(!(route_ary.contains(x))){
                    if( n != 2 || (int)route_ary.getFirst() > x ){
                        route_ary.add(x);
                        startRoundRobin_subloop(points, num_cities, n+1, route_ary);
                        route_ary.removeLast();
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

    public static double getMileage(Point[] points, LinkedList<Integer> route_ary){
        double mileage = 0;
        int i = 0;
        for (; i+1 < points.length; i++) {
            //System.out.println(route_ary.get(i)+" - "+route_ary.get(i+1)+" : "+tmp);
            mileage += points[route_ary.get(i)-1].distance(points[route_ary.get(i+1)-1]);
        }
        // マサラタウンに帰る
        //System.out.println(route_ary.get(i)+" - "+route_ary.get(0)+" : "+tmp);
        mileage += points[route_ary.get(i)-1].distance(points[route_ary.get(0)-1]);
        System.out.println("mile: "+mileage);
        return mileage;
    }
}