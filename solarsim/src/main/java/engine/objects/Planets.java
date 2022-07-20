package engine.objects;
import java.util.ArrayList;
import java.lang.Math;
public class Planets{
    public int index;
    public int[] effectedBy;
    public float[] color={0,0,0};
    public float x;
    public float y;
    public float r;
    public double mass;
    public double velocity;
    public Planets(float x,float y,float r,int index,int[] effectedBy,float[] color,double mass,double velocity){
        this.x=x;
        this.y=y;
        this.r=r;
        this.index=index;
        this.effectedBy=effectedBy;
        this.color=color;
        this.mass=mass;
        this.velocity=velocity;
    }
    public float[][] getFilledArcVertexes(double startingAngleDeg, double endAngleDeg, int slices) {
        double arc=endAngleDeg-startingAngleDeg;
        float[][] vertexes=new float[slices][2];
        for(int i=0;i<slices;i++){
            double ang=(arc*i)/slices;
            System.out.println(ang);
            vertexes[i][0]=(float)Math.cos(Math.toRadians(ang))*r+x;
            vertexes[i][1]=(float)Math.sin(Math.toRadians(ang))*r+y;
        }
        return vertexes;
    }
    public float[] getColor(){
        return(color);
    }
}