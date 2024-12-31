import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Measurable obj;
        char response;

}

interface Measurable{
    float PI = 3.14f;
    float getArea();
    void show();
}

class Rectangle implements Measurable{
    int breadth, length;
    public Rectangle(int breadth, int length){
        this.breadth = breadth;
        this.length = length;
    }

    public float getArea(){
        return (breadth * length);
    }
    public void show(){
        System.out.println("Area of the rectangle is: " + getArea());
    }
}

class Circle implements Measurable{
    int radius;
    public Circle(int radius){
        this.radius = radius;
    }

    public float getArea(){
        return (PI * (radius*radius));
    }
    public void show(){
        System.out.println("Area of the circle is: " + getArea());
    }
}
