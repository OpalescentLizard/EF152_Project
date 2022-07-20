package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width;
    private int height;
    private String title;
    private long window;
    private Input input;
    private float[] backgroundColor={0,0,0};
    private GLFWWindowSizeCallback windowSizeCallback;
    private Boolean windowResized=false;
    private Boolean isFullscreen=false;
    private int[][] normPos={{0},{0}};
    public Window(int width, int height, String title){
        input=new Input();
        this.width=width;
        this.height=height;
        this.title=title;
    }
    //Creates the game window with all the neccessary settings.
    public void createWindow(){
        if(!GLFW.glfwInit()){ //Tryes to actually start the engine.
            System.err.println("ERROR: GLFW failed to initialize");
            return;
        };
        window=GLFW.glfwCreateWindow(this.width,this.height,this.title,isFullscreen?GLFW.glfwGetPrimaryMonitor():0,0); //Creates an actual on screen window and assigns it to the variable.
        if(window==0){
            System.err.println("ERROR: GLFW failed to create window, "+this.width+", "+this.height+", "+this.title+".");
        }
        GLFWVidMode videoMode=GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()); //Checks to make sure that it's displaying on the right monitor
        normPos[0][0]=(videoMode.width()-this.width)/2;//Sets the position of the window in non fullscreen mode.
        normPos[1][0]=(videoMode.height()-this.height)/2;//Would do it in one line, but Java is being screwy. Additionaly, it is important that this is a 2 dimensional array due to a latter function. The second index is always 0 though.
        GLFW.glfwSetWindowPos(window,normPos[0][0],normPos[1][0]); //Centers the window
        GLFW.glfwShowWindow(window); //Displays the window so the viewer can see it
        GLFW.glfwMakeContextCurrent(window); //Makes sure that the window is currently being interacted with (IE, you arn't clicking on the window behind even though this is displayed ontop).
        GL.createCapabilities(); //Allows us to render onto the window

        linkCallbacks();
    }
    //Sets the time interval that the game will loop at, 1 being 60 fps.
    public void startLoop(int frameRateInterval){
        GLFW.glfwSwapInterval(frameRateInterval);
    }
    //This just updates everything to do with the display.
    public void update(){
        GLFW.glfwPollEvents(); //Checks for any inputs
        if(windowResized){
            GL11.glViewport(0,0,width,height); //Resizes the window if the size has been changed
            windowResized=false;
        }
        GL11.glClearColor(backgroundColor[0],backgroundColor[1],backgroundColor[2],1.0f); //Sets the background color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); //Clears the background
    }
    //This will swap out the current display for the new screen set to display.
    public void swapBuffers(){
        GLFW.glfwSwapBuffers(window);
    }
    //Will link call back events to functions that will run when the event happens.
    public void linkCallbacks(){
        windowSizeCallback=new GLFWWindowSizeCallback() {
            public void invoke(long window,int w,int h){
                width=w;
                height=h;
                windowResized=true;
            }
        };
        GLFW.glfwSetWindowSizeCallback(window,windowSizeCallback);
        GLFW.glfwSetKeyCallback(window,input.getKeyboardCallback());
        GLFW.glfwSetMouseButtonCallback(window,input.getMouseButtonsCallback());
        GLFW.glfwSetCursorPosCallback(window,input.getMousePositionCallback());
        GLFW.glfwSetScrollCallback(window,input.getScrollCallback());
    }
    //Allows us to set the background color of the window
    public void setBackgroundColor(float[] Color){
        backgroundColor=Color;
    }
    //Just checks for certain things to determine if the window should close, mainly being whether the X button has been hit:(glfwWindowShouldClose)
    //or if the escape button has been pressed: (GLFW_KEY_ESCAPE).
    public boolean shouldClose(){
        return(GLFW.glfwWindowShouldClose(window)||Input.getKeyPressed(GLFW.GLFW_KEY_ESCAPE));
    }
    //Kills all functions that are running to free the system.
    public void terminate(){
        input.terminate();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
    public void changeFullscreen(){
        isFullscreen=!isFullscreen;
        if(isFullscreen){
            GLFW.glfwGetWindowPos(window,normPos[0],normPos[1]);
            GLFW.glfwSetWindowMonitor(window,GLFW.glfwGetPrimaryMonitor(),0,0,width,height,0);
        }else{
            GLFW.glfwSetWindowMonitor(window,0,normPos[0][0],normPos[1][0],width,height,0);
        }
    }
    public void draw(float[] color,float[][] vertexes){
        GL11.glColor3fv(color);
        GL11.glBegin(vertexes.length);
        for(int i=0;i<vertexes.length;i++){
            GL11.glVertex2f(vertexes[0][0],vertexes[0][1]);
        }
        GL11.glEnd();
    }
}
