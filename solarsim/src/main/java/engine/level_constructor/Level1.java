package engine.level_constructor;

import engine.io.*;
import engine.objects.*;
import engine.physics.*;
import engine.animations.*;
import game.Constants;

import java.util.*;
import org.lwjgl.glfw.GLFW;

public class Level1{
    public Window window;
    public Map<String,Planets> inPlayBodies=new HashMap<String,Planets>();
    public ArrayList<Stars> starField=new ArrayList<Stars>();
    public ArrayList<CollisionEffect> collisionEffects=new ArrayList<CollisionEffect>();
    public boolean quedToPlacePlanet=false;
    public boolean quedToReleaseLeftClick=false;
    public boolean quedToReleaseSpace=false;
    public boolean quedToSetVelocity=false;
    public double tempPlanetMass=Constants.lowestPlanetMass;
    public double[] tempPlanetPos={0,0};
    public Level1(Window window){
        this.window=window;
    }
    //Starts the level and loops it untill some condition is met or the game is closed.
    public boolean start(){
        createStarField();
        createPlanets();
        while(true){
            if(window.shouldClose()){
                return(false);
            }
            if(Input.getKeyPressed(GLFW.GLFW_KEY_F11)){
                window.changeFullscreen();
            }
            if(!checkForPlanets()){
                return(true);
            }
            update();
            render();
        }
    }
    //This will draw all needed planets in the level.
    private void drawPlanets(){
        for (String key:inPlayBodies.keySet()) {
            Planets planet=inPlayBodies.get(key);
            window.draw(planet.getColor(),planet.getFilledArcVertexes(Constants.planetSliceRate),6);
        }
    }
    //A method to create each of the planets we need.
    public void createPlanets(){
        //A template for making a planet. I would replace everything in here, don't keep the template itself
        float[] sampleSunColor=Constants.sunColor/*Fractions of an rgb value, with 1 being 255*/;
        ArrayList<String> sampleSunEffectedBy=new ArrayList<String>()/*list of planets that effect this one gravitationaly.*/;
        double sampleSunMass=Constants.sunMass/*in Kg*/;
        double[] sampleSunVelocity={0,0}/*in m/s*/;
        double[] sampleSunPosition={0,0}/*The postion of the planet, with 0 being the center of the scree, 1 being the right and top edge respectivly, and -1 being the left and bottom edge respectivly. All mesurments are between 1 and 0. To get true value, just multiply by the field size constant in the constants list*/;
        Planets sampleSun=new Planets(sampleSunPosition[0],sampleSunPosition[1],sampleSunEffectedBy,sampleSunColor,sampleSunMass,sampleSunVelocity);
        inPlayBodies.put("Sun_1"/*This is the key. Make sure it is planet for planets, sun for suns, and hazard for hazards.*/,sampleSun);

        float[] samplePlanetColor={0,0,1.0f}/*Fractions of an rgb value, with 1 being 255*/;
        ArrayList<String> samplePlanetEffectedBy=new ArrayList<String>()/*list of planets that effect this one gravitationaly.*/;
        samplePlanetEffectedBy.add("Sun_1");
        double samplePlanetMass=Constants.lowestPlanetMass/*in Kg*/;
        double[] samplePlanetVelocity={0,29800}/*in m/s*/;
        double[] samplePlanetPosition={-0.2,0}/*The postion of the planet, with 0 being the center of the scree, 1 being the right and top edge respectivly, and -1 being the left and bottom edge respectivly. All mesurments are between 1 and 0. To get true value, just multiply by the field size constant in the constants list*/;
        Planets samplePlanet=new Planets(samplePlanetPosition[0],samplePlanetPosition[1],samplePlanetEffectedBy,samplePlanetColor,samplePlanetMass,samplePlanetVelocity);
        inPlayBodies.put("Planet_1"/*This is the key. Make sure it is planet for planets, sun for suns, and hazard for hazards.*/,samplePlanet);

        float[] samplePlanetColor2={0,1.0f,0}/*Fractions of an rgb value, with 1 being 255*/;
        ArrayList<String> samplePlanetEffectedBy2=new ArrayList<String>()/*list of planets that effect this one gravitationaly.*/;
        samplePlanetEffectedBy2.add("Sun_1");
        double samplePlanetMass2=Constants.lowestPlanetMass/*in Kg*/;
        double[] samplePlanetVelocity2={0,29800}/*in m/s*/;
        double[] samplePlanetPosition2={0.2,0}/*The postion of the planet, with 0 being the center of the scree, 1 being the right and top edge respectivly, and -1 being the left and bottom edge respectivly. All mesurments are between 1 and 0. To get true value, just multiply by the field size constant in the constants list*/;
        Planets samplePlanet2=new Planets(samplePlanetPosition2[0],samplePlanetPosition2[1],samplePlanetEffectedBy2,samplePlanetColor2,samplePlanetMass2,samplePlanetVelocity2);
        inPlayBodies.put("Planet_2"/*This is the key. Make sure it is planet for planets, sun for suns, and hazard for hazards.*/,samplePlanet2);
    }
    public void createStarField(){
        for(int i=0;i<Constants.numberStars;i++){
            float[] starPosition={(float)Math.random()*((Math.random()>0.5)?1:-1),(float)Math.random()*((Math.random()>0.5)?1:-1)};
            Stars sampleStar=new Stars(starPosition[0],starPosition[1],Constants.starSize,Constants.starColor);
            starField.add(sampleStar);
        }
    }
    //Will call all systems that need to be updated, mainly physics and the game window.
    private void update(){
        window.update();
        handleInputs();
        if(!(Constants.pauseOnPlanetAdd&&(quedToSetVelocity||quedToPlacePlanet))){
            Physics.step(inPlayBodies,collisionEffects);
        }
        drawTempPlanet();
        drawStarField();
        drawPlanets();
        dawCollisions();
    }
    private void handleInputs(){
        if(Input.getButtonPressed(0)){
            quedToReleaseLeftClick=true;
        }
        if(quedToReleaseLeftClick&&!Input.getButtonPressed(0)){
            if(quedToPlacePlanet){
                createNewPlanetPhase1();
                quedToPlacePlanet=false;
                quedToSetVelocity=true;
            }else if(quedToSetVelocity){
                createNewPlanetPhase2();
                quedToSetVelocity=false;
            }
            quedToReleaseLeftClick=false;
        }
        if(Input.getKeyPressed(32)){
            quedToReleaseSpace=true;
        }
        if(quedToReleaseSpace&&!Input.getKeyPressed(32)){
            quedToPlacePlanet=!quedToPlacePlanet;
            quedToSetVelocity=false;
            quedToReleaseSpace=false;
        }
    }
    //Draws stars onto the background, for fun. Currently doesn't actually do anything
    private void drawStarField(){
        for(int i=0;i<starField.size();i++){
            Stars star=starField.get(i);
            window.draw(star.getColor(),star.getFilledArcVertexes(Constants.starSliceRate),6);
        }
    }
    private void dawCollisions(){
        for (int i=0;i<collisionEffects.size();i++){
            CollisionEffect effect=collisionEffects.get(i);
            float[][][] vertexes=effect.getVertexes();
            window.draw(Constants.collisionAnimationColor1,vertexes[0],6);
            window.draw(Constants.collisionAnimationColor2,vertexes[1],6);
            if(effect.update()>2){
                collisionEffects.remove(i);
            }
        }
    }
    private void drawTempPlanet(){
        if(quedToPlacePlanet||quedToSetVelocity){
            double x=0;
            double y=0;
            if(!quedToSetVelocity){
                double[] mousePos=Input.getMousePos();
                x=(mousePos[0]/(window.getWidth()/2))-1;
                y=(1-(mousePos[1]/(window.getHeight()/2)))-(1/2);
            }else{
                x=tempPlanetPos[0];
                y=tempPlanetPos[1];
            }
            window.draw(Constants.tempPlanetColor,getTempPlanetVertexes(x,y),2);
        }
        if(quedToSetVelocity){
            double[] mousePos=Input.getMousePos();
            float startx=(float)tempPlanetPos[0];
            float starty=(float)tempPlanetPos[1];
            float endx=(float)(mousePos[0]/(window.getWidth()/2))-1;
            float endy=(float)(1-(mousePos[1]/(window.getHeight()/2)))-(1/2);
            double clip=(Planets.radiusFromMass(tempPlanetMass)/Math.sqrt(Math.pow(endx-startx,2)+Math.pow(endy-starty,2)));
            startx=startx+(float)((endx-startx)*clip);
            starty=starty+(float)((endy-starty)*clip);
            float[][] pos={{startx,starty},{endx,endy}};
            window.draw(Constants.tempPlanetColor,pos,2);
        }
    }
    public void createNewPlanetPhase1(){
        double[] mousePos=Input.getMousePos();
        tempPlanetPos[0]=(mousePos[0]/(window.getWidth()/2))-1;
        tempPlanetPos[1]=(1-(mousePos[1]/(window.getHeight()/2)))-(1/2);
    }
    public void createNewPlanetPhase2(){
        double[] mousePos=Input.getMousePos();
        float startx=(float)tempPlanetPos[0];
        float starty=(float)tempPlanetPos[1];
        float endx=(float)(mousePos[0]/(window.getWidth()/2))-1;
        float endy=(float)(1-(mousePos[1]/(window.getHeight()/2)))-(1/2);
        double mag=Math.sqrt(Math.pow(endx-startx,2)+Math.pow(endy-starty,2));
        double[] tempPlanetVelocity={((endx-startx)*Constants.velMagnitude),((endy-starty)*Constants.velMagnitude)};

        float[] tempPlanetColor={(float)Math.random(),(float)Math.random(),(float)Math.random()}/*Fractions of an rgb value, with 1 being 255*/;
        ArrayList<String> tempPlanetEffectedBy=new ArrayList<String>()/*list of planets that effect this one gravitationaly.*/;
        tempPlanetEffectedBy.add("Sun_1");
        Planets tempPlanet=new Planets(tempPlanetPos[0],tempPlanetPos[1],tempPlanetEffectedBy,tempPlanetColor,tempPlanetMass,tempPlanetVelocity);
        int id=1;
        while(inPlayBodies.containsKey("Planet_"+id)){
            id++;
        }
        inPlayBodies.put("Planet_"+id/*This is the key. Make sure it is planet for planets, sun for suns, and hazard for hazards.*/,tempPlanet);
        tempPlanetMass=Constants.lowestPlanetMass;
        tempPlanetPos[0]=0;
        tempPlanetPos[1]=0;
        System.out.println(tempPlanetVelocity[0]+" "+tempPlanetVelocity[1]);

    }
    //Will force the window to render the new updates.
    private void render(){
        window.swapBuffers();
    }
    private float[][] getTempPlanetVertexes(double x,double y){
        double r=Planets.radiusFromMass(tempPlanetMass);
        int sliceRate=Constants.planetSliceRate;
        double arc=360;
        float[][] vertexes=new float[sliceRate][2];
        for(int i=0;i<sliceRate;i++){
            double ang=(arc*i)/sliceRate;
            vertexes[i][0]=(float)(Math.cos(Math.toRadians(ang))*r+x);
            vertexes[i][1]=(float)(Math.sin(Math.toRadians(ang))*r+y);
        }
        return vertexes;
    }
    private boolean checkForPlanets(){
        int numPlanets=0;
        for (String key:inPlayBodies.keySet()){
            if(key.split("_")[0].equals("Planet")){
                numPlanets++;
            }
        }
        return(numPlanets>0);
    }
}