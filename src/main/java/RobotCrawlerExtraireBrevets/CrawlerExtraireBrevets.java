/*########################################################################################
                                   Projet SID : Domotique
                                   Robot : Extraction des brevets
                                   Bibliotheque : JSoup
                                   Equipe de projet SID :
                                          LOUBNA LABRAKCH
                                          HAMADI CHAIMAE
                                          LACHAAB FADWA
                                          EL AMRANI MUSTAPHA
                                          AIT OUAKRIM ABDALLAH
########################################################################################## */

package RobotCrawlerExtraireBrevets;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CrawlerExtraireBrevets {
	
	
    //creer fichier html pour le brevet
	public static void CreerBrevet(int k ,String html){
		try{
			 
	         String fichier = "C:\\wamp\\www\\Brevet\\brevet"+k+".html";
	         PrintWriter fich;
	         fich = new PrintWriter(new BufferedWriter(new FileWriter(fichier, true))); 
	         fich.println(html);
	         fich.close();
	       }catch(Exception e){
			    e.printStackTrace();
		                      }
	                                                  }
	
	
	//Test
	public static void main(String [] args){
	     BufferedReader lecteurBuffer = null; 
	     String ligne=null;
	     //ouverture de fichier brevets.txt pour la lecture
	     try { 
	         lecteurBuffer = new BufferedReader(new FileReader("D:\\SynergieStage\\downloads\\Dropbox\\LPEE\\robots\\src\\main\\java\\RobotCrawlerLiensBrevets\\brevets.txt")); 
	         } 
	     catch(FileNotFoundException exc) 
	         { 
	            System.out.println("Erreur d'ouverture"); 
	         } 
	     
	     
         try {
        	//  int cp=380;
        	   int cp=0;
        	   //parcours les liens de fichier brevets.txt
	             while ((ligne = lecteurBuffer.readLine()) != null){
	        	          System.out.println("---Lien N�:"+cp);
	        	          System.out.println("Lien de brevet :"+ligne);
	        	             try{
	        	            	//connexion au lien brevet
	        	    	        Document doc1 = Jsoup.connect(ligne).get();
	        	    	        System.out.println("bien connect� au lien");
	        	  
	        	                Element div = doc1.select("form").attr("id","detailTabForm").attr("enctype","application/x-www-form-application/x-www-form-urlencoded").last();
	        	                    CreerBrevet(cp,"<html><body><table>");
	    	                        CreerBrevet(cp,div.toString());
	     	                        CreerBrevet(cp,"</table><body></html>");
                               }catch(IOException e){
	        	    	                System.out.println("erreur de connexion au lien :"+ e.getMessage());
	        	                                   }
	        	                        cp++;
	        	      
	                                                             }
	
	                      lecteurBuffer.close();
			 } catch (IOException e) {
				System.out.println("erreur : "+e.getMessage());
			} 
	 } 
                                          }
