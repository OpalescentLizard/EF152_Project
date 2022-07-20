package engine.level_constructor;
import org.lwjgl.glfw.GLFW;
import engine.io.*;
import engine.objects.Planets;
import java.util.ArrayList;
public class Level1{
    public Window window;
    public ArrayList<Planets> inPlayPlanets=new ArrayList<Planets>();;
    public int slices=10;
    public Level1(Window window){
        this.window=window;
    }
    public void start(){
        while(!window.shouldClose()){
            if(Input.getKeyPressed(GLFW.GLFW_KEY_F11)){
                window.changeFullscreen();
            }
            createPlanets();
            drawPlanets();
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

    }
    //Will call all systems that need to be updated, mainly physics and the game window.
    private void update(){
        window.update();
    }
    //Will force the window to render the new updates.
    private void render(){
        window.swapBuffers();
    }
}