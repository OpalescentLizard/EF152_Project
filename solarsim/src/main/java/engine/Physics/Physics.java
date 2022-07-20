package engine.physics;

import engine.objects.Planets;
import game.Constants;
import engine.animations.CollisionEffect;

import java.lang.reflect.Array;
import java.util.*;
public class Physics {
    public static void step(Map<String,Planets> inPlayBodies,ArrayList<CollisionEffect> collisionEffects){
        
        //Moves bodies according to their velocity
        for(String key:inPlayBodies.keySet()){
            Planets targetPlanet=inPlayBodies.get(key);
            targetPlanet.x=((targetPlanet.x*Constants.fieldSize)+targetPlanet.velocity[0]*Constants.timeStep)/Constants.fieldSize;
            targetPlanet.y=((targetPlanet.y*Constants.fieldSize)+targetPlanet.velocity[1]*Constants.timeStep)/Constants.fieldSize;
        }
        checkCollisions(inPlayBodies,collisionEffects);
        //Accelerates the bodies according to gravitational laws.
        for(String key:inPlayBodies.keySet()){
            Planets targetPlanet=inPlayBodies.get(key);
            double accelX=0;
            double accelY=0;
            for(int j=0;j<targetPlanet.effectedBy.size();j++){
                double xDis=(inPlayBodies.get(targetPlanet.effectedBy.get(j)).x*Constants.fieldSize)-(targetPlanet.x*Constants.fieldSize);
                double yDis=(inPlayBodies.get(targetPlanet.effectedBy.get(j)).y*Constants.fieldSize)-(targetPlanet.y*Constants.fieldSize);
                double r=Math.sqrt(Math.pow(xDis,2)+Math.pow(yDis,2));
                double mass=inPlayBodies.get(targetPlanet.effectedBy.get(j)).mass;
                double g=Constants.universalGravitationalConstant;
                double attraction=(g*mass)/(Math.pow(r,2));
                accelX+=(xDis/r)*attraction;
                accelY+=(yDis/r)*attraction;
            }
            targetPlanet.velocity[0]+=accelX*Constants.timeStep;
            targetPlanet.velocity[1]+=accelY*Constants.timeStep;
        }
    }
    public static void checkCollisions(Map<String,Planets> inPlayBodies,ArrayList<CollisionEffect> collisionEffects){
        Boolean collisions=false;
        do{
            collisions=false;
            for(String key:inPlayBodies.keySet()){
                if(key.split("_")[0].equals("Planet")){
                    String[] lock=checkPlanetCollision(key,inPlayBodies);
                    if(lock[0].equals("Hit_Planet")){
                        combinePlanets(key,lock[1],inPlayBodies,collisionEffects);
                        collisions=true;
                        break;
                    }
                    if(lock[0].equals("Hit_Sun")){
                        planetHitInvalid(key,inPlayBodies,collisionEffects);
                        collisions=true;
                        break;
                    }
                    if(lock[0].equals("Hit_Hazard")){
                        planetHitInvalid(key,inPlayBodies,collisionEffects);
                        collisions=true;
                        break;
                    }
                }
            }
        }while(collisions);
    }
    public static String[] checkPlanetCollision(String key,Map<String,Planets> inPlayBodies){
        for(String lock:inPlayBodies.keySet()){
            if(!(lock.equals(key))){
                Planets targetPlanet=inPlayBodies.get(key);
                Planets checkPlanet=inPlayBodies.get(lock);
                double distance=Math.sqrt(Math.pow(checkPlanet.x-targetPlanet.x,2)+Math.pow(checkPlanet.y-targetPlanet.y,2));
                double space=targetPlanet.r+checkPlanet.r;
                if(distance<space){
                    String[] arr={"Hit_"+lock.split("_")[0],lock};
                    return(arr);
                }
            }
        }
        String[] blank={"",""};
        return(blank);
    }
    public static void combinePlanets(String key1,String key2,Map<String,Planets> inPlayBodies,ArrayList<CollisionEffect> collisionEffects){
        Planets planet1=inPlayBodies.get(key1);
        Planets planet2=inPlayBodies.get(key2);
        float[] newPlanetColor={(planet1.getColor()[0]+planet2.getColor()[0])/2,(planet1.getColor()[1]+planet2.getColor()[1])/2,(planet1.getColor()[2]+planet2.getColor()[2])/2};
        ArrayList<String> newPlanetEffectedBy=new ArrayList<String>();
        newPlanetEffectedBy.addAll(planet1.effectedBy);
        newPlanetEffectedBy.addAll(planet2.effectedBy);
        double newPlanetMass=planet1.mass+planet2.mass;
        double[] newPlanetVelocity={((planet1.velocity[0]*planet1.mass)+(planet2.velocity[0]*planet2.mass))/newPlanetMass,((planet1.velocity[1]*planet1.mass)+(planet2.velocity[1]*planet2.mass))/newPlanetMass};
        double[] newPlanetPosition={(planet1.x+planet2.x)/2,(planet1.y+planet2.y)/2};
        Planets newPlanet=new Planets(newPlanetPosition[0],newPlanetPosition[1],newPlanetEffectedBy,newPlanetColor,newPlanetMass,newPlanetVelocity);
        int keyNumber=1;
        while(inPlayBodies.containsKey("Planet_"+keyNumber)){
            keyNumber++;
        }
        inPlayBodies.remove(key1);
        inPlayBodies.remove(key2);
        inPlayBodies.put("Planet_"+keyNumber,newPlanet);
        collisionEffects.add(new CollisionEffect(newPlanet.x,newPlanet.y));
    }
    public static void planetHitInvalid(String key,Map<String,Planets> inPlayBodies,ArrayList<CollisionEffect> collisionEffects){
        Planets planet=inPlayBodies.get(key);
        collisionEffects.add(new CollisionEffect(planet.x,planet.y));
        inPlayBodies.remove(key);
    }
}
