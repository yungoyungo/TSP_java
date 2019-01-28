import java.awt.Point;
import java.math.BigInteger;
import java.util.LinkedList;

public class TSP {
    public static void main(String[] args) {
        //int num_cities = Integer.parseInt(args[0]);
        int num_cities = 5;
        Point[] points = new Point[num_cities];
        for (int i = 0; i < num_cities; i++) {
            points[i] = new Point((int) (Math.random() * 100), (int) (Math.random() * 100));
        }

        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i].toString());
        }
        //System.out.println("total : " + AtoZDistance(points));

        String num_paths = getPathsNum(new BigInteger(String.valueOf(num_cities - 1))).toString();
        System.out.println("all paths: " + num_paths);
        gen_path_all(num_cities);
    }

    // distance標準であるから使わないぞ（作った後に気付く）
    public static double p2p_distance(Point from, Point to) {
        double distance = Math.sqrt(((from.x - to.x) * (from.x - to.x)) + ((from.y - to.y) * (from.y - to.y)));
        return distance;
    }

    public static double AtoZDistance(Point[] points) {
        double total = 0;
        double tmp;
        int i = 0;
        for (; i + 1 < points.length; i++) {
            // total = total + p2p_distance(points[i], points[i+1]);
            tmp = points[i].distance(points[i + 1]);
            // System.out.println("dist: "+tmp);
            total = total + tmp;
        }
        // マサラタウンに帰る
        tmp = points[i].distance(points[0]);
        // System.out.println("dist: "+tmp);
        total = total + tmp;
        return total;
    }

    public static void gen_path_all(int num_cities){
        int x = 2;
        LinkedList<Integer> route_ary = new LinkedList<Integer>();
        for(; x <= num_cities; x++){
            route_ary.add(x);
            route_ary.add(1);
            gen_path_subloop_all(num_cities, 2, route_ary);
            route_ary.clear();
        }
    }
    private static void gen_path_subloop_all(int num_cities,int n,LinkedList<Integer> route_ary){
        if(n == num_cities){
            System.out.println(route_ary);
            getMileage(route_ary);
        }
        else{
            for(int x=2; x <= num_cities; x++){
                if(!(route_ary.contains(x))){
                    if( n != 2 || (int)route_ary.getFirst() > x ){
                        route_ary.add(x);
                        gen_path_subloop_all(num_cities, n+1, route_ary);
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

    public static double getMileage(LinkedList<Integer> route_ary){
        double mileage = 0;
        return mileage;
    }
}