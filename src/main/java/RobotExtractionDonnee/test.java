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
public class test {

    private List<String> res = new ArrayList<String>();
    private List<String> res1 = new ArrayList<String>();
    private List<String> res2 = new ArrayList<String>();
     private List<String> res3 = new ArrayList<String>();
    private List<String> elsInterdits = new ArrayList<String>();

    public test() {

        elsInterdits.add("la");
        elsInterdits.add("le");
        elsInterdits.add("les");
        elsInterdits.add("une");
        elsInterdits.add("un");
        elsInterdits.add("de");
        elsInterdits.add("sur");
        elsInterdits.add("voila");
        elsInterdits.add("avec");
        elsInterdits.add("dans");
        elsInterdits.add("ou");
        elsInterdits.add("son");
        elsInterdits.add("sans");
        elsInterdits.add("mais");
        elsInterdits.add("et");
        elsInterdits.add("est");
        elsInterdits.add("ce");
        elsInterdits.add("‡");
        elsInterdits.add("se");
        elsInterdits.add("son");
        elsInterdits.add("ses");
        elsInterdits.add("par");
        elsInterdits.add("pour");
        elsInterdits.add("des");
        elsInterdits.add("en");
        elsInterdits.add("des");
        elsInterdits.add("sa");
      
       






    }

    //********** m√©thode de decoupage:************
    
    public List<String> DecouperMot(String phrase) {
        String s = new String();
        String mot = new String();
        s = phrase;
        int j = 0;
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) != ' ') {

                mot = mot + s.charAt(i);


                if (i == s.length() - 1) {
                    res.add(mot);
                }

            } else {

                res.add(mot);

                mot = "";
            }
        }
        return res;
    }

    //******** methode de elemination des propositions *********
    public List<String> eliminerMot(List<String> res) {
        for (int i = 0; i < res.size(); i++) {

            for (int j = 0; j < elsInterdits.size(); j++) {
                if (res.get(i).toLowerCase().equals(elsInterdits.get(j))) {

                    res.remove(i);
                    i--;

                }
            }

        }
        res1 = res;
        return res1;
    }
    
    //********** methode elemination des chaines vides au cas ou on des doubles espaces ***********
    
    public List<String> eliminerVide(List<String> res){
        
        for(int i=0; i<res.size();i++){
            if(res.get(i).equals("")){
                res.remove(i);
                i--;
                
            }
            
        }
        res3=res;
        return res3;
    }

    // ******* methode d'ajustement de mot: elimination de d' / l' ************
    
    public List<String> ajustementMot(List<String> res) {

        for (int j = 0; j < res.size(); j++) {
            if (res.get(j).startsWith("d'")||res.get(j).startsWith("l'") || res.get(j).startsWith("D'")||res.get(j).startsWith("L'")) {
                res2.add(res.get(j).substring(2));

            } else {
                res2.add(res.get(j));
            }

        }
        return res2;
    }
}
