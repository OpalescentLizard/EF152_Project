package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {
    private boolean[] keys=new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttons=new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double[] mousePos={0,0};
    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mousePosition;
    private GLFWMouseButtonCallback mouseButtons;
    public Input(){
        keyboard=new GLFWKeyCallback() {
            public void invoke(long window,int key,int scancode,int action,int mods){
                keys[key]=(action!=GLFW.GLFW_RELEASE);
            }
        };
        mousePosition=new GLFWCursorPosCallback() {
            public void invoke(long window,double xPos,double yPos){
                mousePos[0]=xPos;
                mousePos[1]=yPos;
            }
        };
        mouseButtons=new GLFWMouseButtonCallback() {
            public void invoke(long window,int button,int action,int mods){
                buttons[button]=(action!=GLFW.GLFW_RELEASE);
            }
        };
    }
    public double[] getMousePos(){
        return(mousePos);
    }
    public boolean getKeyPressed(int id){
        return(keys[id]);
    }
    public boolean getButtonPressed(int id){
        return(buttons[id]);
    }
}
