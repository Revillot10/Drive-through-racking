/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planificaciondecosecha;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class PlanificacionDeCosecha {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            new metodos();
        } catch (IOException ex) {
            Logger.getLogger(PlanificacionDeCosecha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
