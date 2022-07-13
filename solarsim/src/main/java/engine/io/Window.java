package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
    private int width;
    private int height;
    private String title;
    private long window;
    public Window(int width, int height, String title){
        this.width=width;
        this.height=height;
        this.title=title;
    }
    public void createWindow(){
        if(!GLFW.glfwInit()){
            System.err.println("ERROR: GLFW failed to initialize");
            return;
        };
        window=GLFW.glfwCreateWindow(this.width,this.height,this.title,0,0);
        if(window==0){
            System.err.println("ERROR: GLFW failed to create window, "+this.width+", "+this.height+", "+this.title+".");
        }
        GLFWVidMode videoMode=GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window,(videoMode.width()-this.width)/2,(videoMode.height()-this.height)/2);
        GLFW.glfwShowWindow(window);
        GLFW.glfwMakeContextCurrent(window);
    }
    public void startLoop(int frameRateInterval){
        GLFW.glfwSwapInterval(frameRateInterval);
    }
    public void update(){
        GLFW.glfwPollEvents();
    }
    public void swapBuffers(){
        GLFW.glfwSwapBuffers(window);
    }
    public boolean shouldClose(){
        return(GLFW.glfwWindowShouldClose(window));
    }
}
