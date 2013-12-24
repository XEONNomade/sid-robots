/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RobotExtractionDonnee;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CHAM
 */
public class DecouperMots {

    /**
     * @param args the command line arguments
     */
    
    
    
    
    
    public static List<String> DecoupageMots(String phrase) {
       
        test t=new test();
      //String phrase="bonjour DE le monde voila  d'equilibre SANS D'UNE son l'intrus";
        
       List<String> listS=new ArrayList<String>();
        
        List<String> resultat=new ArrayList<String>();
        List<String> resultat2=new ArrayList<String>();
        List<String> resultat3=new ArrayList<String>();
         List<String> resultat4=new ArrayList<String>();
        
        resultat=t.DecouperMot(phrase);
        // System.out.println("decoupage ******: ");
       /* for(int i=0; i<resultat.size();i++){
        System.out.println(resultat.get(i));    
        }*/
        
        
      // System.out.println("elimination *******: ");
        
       // resultat2=t.eliminerMot(resultat);
        
        
        /* for(int i=0; i<resultat2.size();i++){
        System.out.println(resultat2.get(i));    
        }*/
       //   System.out.println("Elimination des chaines vides : ******");
         resultat4=t.eliminerVide(resultat);
         
      /*   for(int i=0; i<resultat4.size();i++){
             System.out.println(resultat4.get(i));
       }*/
     
 //       System.out.println("Resultat final : ******");
       
       resultat3=t.ajustementMot(resultat4);
              
       resultat2 = t.eliminerMot(resultat3);
  
     
     return resultat2;
         
    }
}
