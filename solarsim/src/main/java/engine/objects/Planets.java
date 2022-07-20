package engine.objects;
import java.lang.Math;
import java.util.*;
public class Planets{
    public ArrayList<String> effectedBy;
    public float[] color={0,0,0};
    public double x;
    public double y;
    public double r;
    public double mass;
    public double[] velocity={0,0};
    public Planets(double x,double y,ArrayList<String> effectedBy,float[] color,double mass,double[] velocity){
        this.x=x;
        this.y=y;
        this.r=radiusFromMass(mass);
        this.effectedBy=effectedBy;
        this.color=color;
        this.mass=mass;
        this.velocity=velocity;
    }
    public float[][] getFilledArcVertexes(int sliceRate) {
        double arc=360;
        float[][] vertexes=new float[sliceRate][2];
        for(int i=0;i<sliceRate;i++){
            double ang=(arc*i)/sliceRate;
            vertexes[i][0]=(float)(Math.cos(Math.toRadians(ang))*r+x);
            vertexes[i][1]=(float)(Math.sin(Math.toRadians(ang))*r+y);
        }
        return vertexes;
    }
    public static double radiusFromMass(double mass){
        return((Math.log(mass)/Math.log(1.1))/30000);
    }
    public float[] getColor(){
        return(color);
    }
}