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
            vertexes[0][i][0]=(float)(Math.cos(Math.toRadians(ang))*size+x);
            vertexes[0][i][1]=(float)(Math.sin(Math.toRadians(ang))*size+y);
        }
        for(int i=0;i<sliceRate;i++){
            double ang=(arc*i)/sliceRate;
            vertexes[1][i][0]=(float)(Math.cos(Math.toRadians(ang))*size*scale+x);
            vertexes[1][i][1]=(float)(Math.sin(Math.toRadians(ang))*size*scale+y);
        }
        return(vertexes);
    }
}
