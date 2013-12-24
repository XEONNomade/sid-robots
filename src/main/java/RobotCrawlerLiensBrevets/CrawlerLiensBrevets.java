/*###############################################################################################################
                                   Projet SID : Domotique
                                   Robot : Extraction des liens des brevets
                                   Bibliotheque : JSoup
                                   Equipe de projet SID :
                                          LOUBNA LABRAKCH
                                          HAMADI CHAIMAE
                                          LACHAAB FADWA
                                          EL AMRANI MUSTAPHA
                                          AIT OUAKRIM ABDALLAH
#################################################################################################################*/


package RobotCrawlerLiensBrevets;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class CrawlerLiensBrevets {


	
	
//fichier pour ajout des liens des brevets
public static void CreerFichierLiensBrevts(String lien){
	  try{
		   //il faut mettre le chemin de fichier (depant de votre machine)
            String fichier = "D:\\SynergieStage\\downloads\\Dropbox\\LPEE\\robots\\src\\main\\java\\RobotCrawlerLiensBrevets\\brevets.txt";
            PrintWriter fich;
            fich = new PrintWriter(new BufferedWriter(new FileWriter(fichier, true))); 
            fich.println(lien);
            fich.close();
         }catch(Exception e){
		    System.out.println("erreur fichier : "+e.getMessage());
	                        }
                                                      }

//Test
 public static void main(String [] args){
       
	    Document doc=null;
        String url=null;
        //parcours toutes les pages de recherche WIPO
	    for(int j=1;j<=48;j++){
			  System.out.println("------------------------------------(page de recherche N�"+j+")--------------------------------");
			  try {
				     url="http://patentscope.wipo.int/search/fr/result.jsf?currentNavigationRow="+j+"&query=FP:(Domotique)&office=&sortOption=Pub%20Date%20Desc&prevFilter=&maxRec=475";
					 doc = Jsoup.connect(url).get();
		             System.out.println("Bien connect� au page de recherche  :"+url);
                  } catch (IOException e) {
				             System.out.println("erreur de connexion : "+ e.getMessage());
			                              }
			     //extraire toutes les liens de page de recherche
			     Elements links = doc.select("a");
			     for(int i=0;i<links.size();i++){
			    	  //cherche les liens commencent par detail.jsf
			          	if(links.get(i).attr("href").startsWith("detail.jsf")){
			          		String complien ="http://patentscope.wipo.int/search/fr/detail.jsf?"+links.get(i).attr("href").substring(links.get(i).attr("href").indexOf("?")+1,links.get(i).attr("href").length());
                            System.out.println("http://patentscope.wipo.int/search/fr/"+links.get(i).attr("href").substring(links.get(i).attr("href").indexOf("?")+1,links.get(i).attr("href").length()));
                            CreerFichierLiensBrevts("http://patentscope.wipo.int/search/fr/"+links.get(i).attr("href").substring(links.get(i).attr("href").indexOf("?")+1,links.get(i).attr("href").length()));
                                                                              }
			          	                        }
		                   }      
	          
	                                 } 
	}


	

