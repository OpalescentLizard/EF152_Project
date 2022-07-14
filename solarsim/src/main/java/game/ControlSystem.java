package game;
import org.lwjgl.glfw.GLFW;

import engine.io.*;

public class ControlSystem implements Runnable{
    public Thread ioPipeline;
    public Window window;
    public long time;
    public void start(){
        ioPipeline=new Thread(this,"display");
        ioPipeline.start();
    }
    public void run(){
        init();
        while(!window.shouldClose()){
            update();
            render();
        }
        terminate();
    }
    private void init(){
        window=new Window(1200,1200,"Main Screen");
        window.createWindow();
        window.startLoop(1);
        time=System.currentTimeMillis();
    }
    private void update(){
        window.update();
        long curTime=System.currentTimeMillis();
        System.out.println(1000/((double)(curTime-time)));
        time=curTime;
    }
    private void render(){
        window.swapBuffers();
    }
    private void terminate(){
        Input.terminate();
        GLFW.glfwTerminate();
    }
    public static void main(String[] args){
        new ControlSystem().start();
    }
    
}
