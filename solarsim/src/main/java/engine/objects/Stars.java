package engine.objects;
import java.util.ArrayList;
import java.lang.Math;
public class Stars{
    public float[] color={0,0,0};
    public float x;
    public float y;
    public float r;
    public Stars(float x,float y,float r,float[] color){
        this.x=x;
        this.y=y;
        this.r=r;
        this.color=color;
    }
    public float[][] getFilledArcVertexes(int sliceRate) {
        double arc=360;
        float[][] vertexes=new float[sliceRate][2];
        for(int i=0;i<sliceRate;i++){
            double ang=(arc*i)/sliceRate;
            vertexes[i][0]=(float)Math.cos(Math.toRadians(ang))*r+x;
            vertexes[i][1]=(float)Math.sin(Math.toRadians(ang))*r+y;
        }
        return vertexes;
    }
    public float[] getColor(){
        return(color);
    }
}