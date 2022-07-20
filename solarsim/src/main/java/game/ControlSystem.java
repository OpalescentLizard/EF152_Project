package game;

import engine.io.*;
import engine.level_constructor.*;;

public class ControlSystem implements Runnable{
    public Thread ioPipeline;
    public Window window;
    //Kicks of the program by initializing the start function. Perferably, this should go untouched.
    public static void main(String[] args){
        new ControlSystem().start();
    }
    //Actually starts the control system. This, however, manages the addition of a new thread and not the start of the game.
    public void start(){
        ioPipeline=new Thread(this,"display");
        ioPipeline.start();
    }
    //Initializes and runs the game in two steps, update which checks all inputs and physics and makes sure everything is in sync,
    //then render which updates what's actually displayed on screen. Once the screen is closed, it will terminate everything.
    public void run(){
        init();
        Level1 level1=new Level1(window);
        boolean shouldContinue=level1.start();
        if(shouldContinue){
            Constants.backgroundColor[0]=1.0f;
            while(!window.shouldClose()){
                window.update();
                window.swapBuffers();
            }
        }
        window.terminate();
    }
    //Creates the window class and sets all variables to what's needed. The window class will be the main hub for all functions for the game.
    private void init(){
        window=new Window(1200,1200,"Main Screen");
        window.createWindow();
        window.startLoop(1);
        window.setBackgroundColor(Constants.backgroundColor);
    }
}
