package engine.objects;
import java.util.ArrayList;
public class Planets{
    private float y;
    private float r;
    private int index;
    private int[] effectedBy;
    private float[] color={0,0,0};
    private float x;
    public Planets(float x,float y,float r,int index,int[] effectedBy,float[] color){
        this.x=x;
        this.y=y;
        this.r=r;
        this.index=index;
        this.effectedBy=effectedBy;
        this.color=color;
    }
    public float[][] getFilledArcVertexes(double startingAngleDeg, double endAngleDeg, int slices) {
        double arc=endAngleDeg-startingAngleDeg;
        ArrayList<Float> VertexesTemp=new ArrayList<Float>();
        
        return vertexes;
    }
    public float[] getColor(){
        return(color);
    }
}