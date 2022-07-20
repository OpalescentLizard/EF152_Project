package engine.animations;

import game.Constants;
public class CollisionEffect {
    double x;
    double y;
    int sliceRate;
    int timeLeft=Constants.collisionAnimationTimings[0];
    int phase=0;
    public CollisionEffect(double x,double y){
        this.x=x;
        this.y=y;
        this.sliceRate=Constants.collisionsAnimationSliceRate;
    }
    public int update(){
        timeLeft-=1;
        if(timeLeft<=0){
            phase+=1;
            timeLeft=Constants.collisionAnimationTimings[phase];
        }
        return(phase);
    }
    public float[][][] getVertexes(){
        double scale=Constants.collisionAnimationScale;
        double[] sizeOptions=Constants.collisionAnimationSizes;
        double size=sizeOptions[phase];
        double arc=360;
        float[][][] vertexes=new float[2][sliceRate][2];
        for(int i=0;i<sliceRate;i++){
            double ang=(arc*i)/sliceRate;
            double n=4;
            double m=5;
            double k=0.5;
            double r=(Math.cos((2*Math.asin(k)+Math.PI*m)/(2*n)))/(Math.cos((2*Math.asin(k*Math.cos(n*ang))+Math.PI*m)/(2*n)));
            vertexes[0][i][0]=(float)(Math.cos(Math.toRadians(ang))*r*size+x);
            vertexes[0][i][1]=(float)(Math.sin(Math.toRadians(ang))*r*size+y);
        }
        for(int i=0;i<sliceRate;i++){
            vertexes[1][i][0]=(float)((vertexes[0][i][0])*scale+(x-x*scale));
            vertexes[1][i][1]=(float)((vertexes[0][i][1])*scale+(y-y*scale));
        }
        return(vertexes);
    }
}
