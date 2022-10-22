package floristeria;

import java.util.HashMap;

public class Ramo {

    HashMap<String, Integer> ramo;

    public Ramo() {
        ramo = new HashMap<String, Integer>() {
            {
                put("Rosa", 0);
                put("Lirio", 0);
                put("Clavel", 0);
            }
        };
    }

    public void añadirRosa() {
        this.ramo.put("Rosa", this.ramo.get("Rosa") + 1);
    }

    public void añadirLirio() {
        this.ramo.put("Lirio", this.ramo.get("Lirio") + 1);
    }

    public void añadirClavel() {
        this.ramo.put("Clavel", this.ramo.get("Clavel") + 1);
    }
    
    public Integer verRosa(){
        return this.ramo.get("Rosa");
    }
    
    public Integer verLirio(){
        return this.ramo.get("Lirio");
    }
    
    public Integer verClavel(){
        return this.ramo.get("Clavel");
    }

}
