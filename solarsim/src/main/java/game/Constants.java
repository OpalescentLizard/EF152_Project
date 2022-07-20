package game;

public class Constants {
    public static float[] backgroundColor={0,0,0};
    public static float[] starColor={1.0f,1.0f,1.0f};
    public static float[] sunColor={1.0f,1.0f,0};
    public static float[] hazardColor={1.0f,0,0};
    public static float starSize=0.005f;
    public static int numberStars=60;
    public static int updatesPerFrame=1;
    public static double timeStep=3600*24/*In seconds*/;
    public static double universalGravitationalConstant=6.6743*Math.pow(10,-11);
    public static double fieldSize=1.496*Math.pow(10,11)/*1 Au*/*5;
    public static int[] collisionAnimationTimings={12,20,15,10};
    public static float[] collisionAnimationColor1={0.789f,0.196f,0.063f};
    public static float[] collisionAnimationColor2={0.949f,0.667f,0.208f};
    public static int collisionsAnimationSliceRate=30;
    public static double collisionAnimationScale=0.65;
    public static double[] collisionAnimationSizes={0.03,0.02,0.035,0.015};
    public static int planetSliceRate=12;
    public static int starSliceRate=12;
}
