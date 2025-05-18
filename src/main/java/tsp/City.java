package tsp;

public class City {
    private double x, y;
    private String name;

    public City(String name, double x, double y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public double distanceTo(City city){
        double dx = this.x - city.x;
        double dy = this.y - city.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public String getName(){
        return name;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
