package engine.level_constructor;
import org.lwjgl.glfw.GLFW;
import engine.io.*;
import engine.objects.*;
import java.util.ArrayList;
import engine.Physics.*;
import game.Constants;
public class Level1{
    public Window window;
    public ArrayList<Planets> inPlayPlanets=new ArrayList<Planets>();
    public ArrayList<Stars> starField=new ArrayList<Stars>();
    public int slices=12;
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
            update();
            render();
        }
    }
    //This will draw all needed planets in the level.
    private void drawPlanets(){
        for(int i=0;i<inPlayPlanets.size();i++){
            Planets planet=inPlayPlanets.get(i);
            window.draw(planet.getColor(),planet.getFilledArcVertexes(0,360,slices));
        }
    }
    //A method to create each of the planets we need.
    public void createPlanets(){
        //A template for making a planet. I would replace everything in here, don't keep the template itself
        float[] samplePlanetColor={1.0f,1.0f,1.0f}/*Fractions of an rgb value, with 1 being 255*/;
        int[] samplePlanetEffectedBy={}/*list of planets that effect this one gravitationaly.*/;
        double samplePlanetMass=10/*in Kg*/;
        double samplePlanetVelocity=0/*in m/s*/;
        float[] samplePlanetPosition={0,0}/*The postion of the planet, with 0 being the center of the scree, 1 being the right and top edge respectivly, and -1 being the left and bottom edge respectivly. All mesurments are between 1 and 0. To get true value, just multiply by the field size constant in the constants list*/;
        float samplePlanetDisplaySize=0.1f/*As a fraction of the screen's size*/;
        int samplePlanetIndex=0/*This has to be the same index as the planet's index in inPlayPlanets*/;
        Planets samplePlanet=new Planets(samplePlanetPosition[0],samplePlanetPosition[1],samplePlanetDisplaySize,samplePlanetIndex,samplePlanetEffectedBy,samplePlanetColor,samplePlanetMass,samplePlanetVelocity);
        inPlayPlanets.add(samplePlanet);

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
        Physics.step(inPlayPlanets);
        drawStarField();
        drawPlanets();
    }
    //Draws stars onto the background, for fun. Currently doesn't actually do anything
    private void drawStarField(){
        for(int i=0;i<starField.size();i++){
            Stars star=starField.get(i);
            window.draw(star.getColor(),star.getFilledArcVertexes(0,360,slices));
        }
    }
    //Will force the window to render the new updates.
    private void render(){
        window.swapBuffers();
    }
}