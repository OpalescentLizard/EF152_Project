package engine.Physics;

import engine.objects.Planets;
import java.util.ArrayList;
public class Physics {
    public static ArrayList<Planets> step(ArrayList<Planets> inPlayPlanets){
        for(int i=0;i<inPlayPlanets.size();i++){
            Planets targetPlanet=inPlayPlanets.get(i);
            for(int j=0;j<targetPlanet.effectedBy.length;j++){
                
            }
        }
        return(inPlayPlanets);
    }
}
