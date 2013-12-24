/*###############################################################################################################
                                   Projet SID : Domotique
                                   Robot : Extraction des donn�es utiles et ajoutent dans le fichier XML
                                   Bibliotheque : JSoup
                                   Equipe de projet SID :
                                          LOUBNA LABRAKCH
                                          HAMADI CHAIMAE
                                          LACHAAB FADWA
                                          EL AMRANI MUSTAPHA
                                          AIT OUAKRIM ABDALLAH
#################################################################################################################*/


package RobotExtractionDonnee;




import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jdom2.*;
import org.jdom2.output.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class CrawlerExtactionDonnee {

	Element racine ;
	Attribute [] name;
	Element  [] table;
	Element database;
	Document doc;
	
	public CrawlerExtactionDonnee(){
		
		 String npub=null,dpub=null,auteurs=null,agents=null,titre=null,pays=null;
		 int cp=0;
         //creer le document XML
		 org.jdom2.Document docxml = new org.jdom2.Document();
		 //creation de la racine database de fichier XML
   	     org.jdom2.Element database = new org.jdom2.Element("database");
   	     //cr�e l'attribut name avec la valeur domotique
   	     org.jdom2.Attribute  dbname = new org.jdom2.Attribute("name","domotique");
   	     //affecte l'attribut � l'�l�ment database
   	     database.setAttribute(dbname);
   	     docxml.setRootElement(database);
        
         int pay=0;
   	    //parcours de toutes les brevets 
         for(int i=0 ; i<=379 ;i++){
	                     
                	      List<String> resultat=new ArrayList<String>();
                          List<String> listauteurs = new ArrayList<String>();
                          List<String> listpays = new ArrayList<String>();
	          System.out.println("------------------------------------("+i+")------------------------------------");
	     try{
	    	             //connect avec le brevet en localhost
	    	             Document doc1 = Jsoup.connect("http://localhost:82/brevets/brevet"+i+".html").get();
	    	             System.out.println("bien connect� au lien brevet");
	    	             //donc existe deux formes de structuration des brevets
	    	             //premier forme
	    
	    	               if((!doc1.select("td[id=detailPCTtableWO]").isEmpty()) ){
                                     //recuperer le numero de publication
	    	            	          npub = doc1.select("td[id=detailPCTtableWO]").first().text();
	    	            	         //recuperer la date de publication
	    	                          dpub = doc1.select("td[id=detailPCTtablePubDate]").first().text();
	    	                         //
	    	                          Elements agentauteur = doc1.select("span[class=notranslate]");
	    	                          Element auteurspays = agentauteur.get(1);
	    	                          //extraire les pays des auteurs
	    	                          pays = auteurspays.toString();
	    	                          String  lespays =  pays.toString().substring(pays.toString().indexOf(">")+1,pays.toString().indexOf("</span>"));
	    	                          StringTokenizer nompays = new StringTokenizer(lespays,".");
	    	                          String chaine=null;
	    	    		                   while (nompays.hasMoreTokens()){
	    	    		                         	chaine = nompays.nextToken();
	    	    		                              if((chaine.indexOf("(") != -1) && (chaine.indexOf(")")!= -1)){
	    	    			                              listpays.add(chaine.substring(chaine.indexOf("(")+1,chaine.indexOf(")")));
	    	    			                                                                                       }
	    	    		                                                  }
	    	                           
	    	    			                                             
	    	                        //extraire des auteurs
	    	                        Elements lesauteurs = agentauteur.get(1).select("b");
	    	                        for(int j=0;j<lesauteurs.size();j++){
	    		                         listauteurs.add(lesauteurs.get(j).text());
	    	                                                           }
	    	                        //le titre
	    	                        titre =doc1.select("span[lang=fr]").first().text();
	    	                        //Decoupage le titre en mots cl�
	    	                        resultat = DecouperMots.DecoupageMots(titre);
	    	                                                                                      }
	    	
	    	  
	    	   if((doc1.select("td[id=detailPCTtableWO]").isEmpty()) && (!doc1.select("span[class=ncDetailLabel]").isEmpty())){
	            
                           Elements elt = doc1.select("table[class=rich-tabpanel-content-position]");
	    	               Element tr  = elt.select("tr").get(7);
	    	               //numero publication
	    	               npub = tr.select("td").get(1).text();
	    	               //date de publication
	    	               dpub = tr.select("td").get(3).text();
	    	               //titre
	    	               titre = doc1.select("span[lang=fr]").first().text();
	    	               //decoupage le titre en mots cl�
	    	               resultat = DecouperMots.DecoupageMots(titre);
	    	               //les auteurs
	    	               Element lesauteursnsep =doc1.select("span[class=notranslate").get(1);
	    	               String  lesauteurssep =  lesauteursnsep.toString().substring(lesauteursnsep.toString().indexOf(">")+1,lesauteursnsep.toString().indexOf("</span>"));
	    	               StringTokenizer nomauteurs = new StringTokenizer(lesauteurssep,"<br/>");
	    	                       while (nomauteurs.hasMoreTokens()){
	    			                    listauteurs.add(nomauteurs.nextToken());
	    			                                                 }
	    		                                                                                                            }
	    	
	                          //creation de fichier XML contient toutes les donn�es de brevet
	    	                 //---------------XML-----------------------
                                 org.jdom2.Element[] table = new org.jdom2.Element[6];
                                 org.jdom2.Element[] column = new org.jdom2.Element[24];
                                 org.jdom2.Attribute [] name = new org.jdom2.Attribute[30];
                            	  for(int k=0;k<table.length;k++){
            		                 table[k] = new org.jdom2.Element("table");
            		                 name[k] = new org.jdom2.Attribute("name","items");
            		                 table[k].setAttribute(name[k]);
            	                                                 }
          	                      for(int k=0;k<column.length;k++){
         		                     column[k] = new org.jdom2.Element("column");
         		                                                  }
           
                                  org.jdom2.Attribute atrnlnpub = new org.jdom2.Attribute("name","nom_long");
                                  column[1].setAttribute(atrnlnpub);
                                  column[1].setText(npub);
                                  table[0].addContent(column[1]);
                                  org.jdom2.Attribute atrnbnpub = new org.jdom2.Attribute("name","numero_brevet");
                                  column[2].setAttribute(atrnbnpub);
                                  column[2].setText(npub);
                                  table[0].addContent(column[2]);
                                  org.jdom2.Attribute atridanachnpub = new org.jdom2.Attribute("name","idAnalyseChamp");
                                  column[3].setAttribute(atridanachnpub);
                                  column[3].setText(Integer.toString(1));
                                  table[0].addContent(column[3]);
              
                                  org.jdom2.Attribute atrnldp = new org.jdom2.Attribute("name","nom_long");
                                  column[5].setAttribute(atrnldp);
                                  column[5].setText(dpub);
                                  table[1].addContent(column[5]);
                                  org.jdom2.Attribute atrnbdp = new org.jdom2.Attribute("name","numero_brevet");
                                  column[6].setAttribute(atrnbdp);
                                  column[6].setText(npub);
                                  table[1].addContent(column[6]);
                                  org.jdom2.Attribute atridanachdp = new org.jdom2.Attribute("name","idAnalyseChamp");
                                  column[7].setAttribute(atridanachdp);
                                  column[7].setText(Integer.toString(3));
                                  table[1].addContent(column[7]);
              
                                  
                                  org.jdom2.Attribute atrnltit = new org.jdom2.Attribute("name","nom_long");
                                  column[9].setAttribute(atrnltit);
                                  column[9].setText(titre);
                                  table[2].addContent(column[9]);
                                  org.jdom2.Attribute atrnbtit = new org.jdom2.Attribute("name","numero_brevet");
                                  column[10].setAttribute(atrnbtit);
                                  column[10].setText(npub);
                                  table[2].addContent(column[10]);
                                  org.jdom2.Attribute atridanachtit = new org.jdom2.Attribute("name","idAnalyseChamp");
                                  column[11].setAttribute(atridanachtit);
                                  column[11].setText(Integer.toString(7));
                                  table[2].addContent(column[11]);
              
                                  docxml.getRootElement().addContent(table[0]);
                                  docxml.getRootElement().addContent(table[1]);
                                  docxml.getRootElement().addContent(table[2]);
           
                                  for(int  mc=0; mc<resultat.size();mc++){
           	                           org.jdom2.Element tablemc = new org.jdom2.Element("table");
           	                           org.jdom2.Attribute attrmc = new org.jdom2.Attribute("name","items");
                                	   tablemc.setAttribute(attrmc);
           	                           org.jdom2.Element[] columnmc = new org.jdom2.Element[4];
           	                                  for(int k=0;k<columnmc.length;k++)
           		                                  columnmc[k] = new org.jdom2.Element("column");
           	  
           	                           org.jdom2.Attribute attriditem = new org.jdom2.Attribute("name","idItem");
           	                           org.jdom2.Attribute attrnl = new org.jdom2.Attribute("name","nom_long");
           	                           org.jdom2.Attribute attrnb = new org.jdom2.Attribute("name","numero_brevet");
           	                           org.jdom2.Attribute attriac = new org.jdom2.Attribute("name","idAnalyseChamp");
           	      
	                                   columnmc[1].setAttribute(attrnl);
	                                   columnmc[1].setText(resultat.get(mc));
	                               tablemc.addContent(columnmc[1]);
	               
	               columnmc[2].setAttribute(attrnb);
	               columnmc[2].setText(npub);
	               tablemc.addContent(columnmc[2]);
	               
	               columnmc[3].setAttribute(attriac);
	               columnmc[3].setText(Integer.toString(4));
	               tablemc.addContent(columnmc[3]);
	               
	               docxml.getRootElement().addContent(tablemc);
                  }
              //---auteurs
              int aut=0;
              for( aut=0; aut<listauteurs.size();aut++){
              	   org.jdom2.Element tablemc = new org.jdom2.Element("table");
              	   org.jdom2.Attribute attrmc = new org.jdom2.Attribute("name","items");
              	   tablemc.setAttribute(attrmc);
              	   org.jdom2.Element[] columnmc = new org.jdom2.Element[4];
              	   for(int k=0;k<columnmc.length;k++)
              		      columnmc[k] = new org.jdom2.Element("column");
              	   org.jdom2.Attribute attriditem = new org.jdom2.Attribute("name","idItem");
              	   org.jdom2.Attribute attrnl = new org.jdom2.Attribute("name","nom_long");
              	   org.jdom2.Attribute attrnb = new org.jdom2.Attribute("name","numero_brevet");
              	   org.jdom2.Attribute attriac = new org.jdom2.Attribute("name","idAnalyseChamp");
              	  /*columnmc[0].setAttribute(attriditem);
   	               columnmc[0].setText(""+aut);
   	               tablemc.addContent(columnmc[0]);*/
   	               
   	               columnmc[1].setAttribute(attrnl);
   	               columnmc[1].setText(listauteurs.get(aut));
   	               tablemc.addContent(columnmc[1]);
   	               
   	               columnmc[2].setAttribute(attrnb);
   	               columnmc[2].setText(npub);
   	               tablemc.addContent(columnmc[2]);
   	               
   	               columnmc[3].setAttribute(attriac);
   	               columnmc[3].setText(Integer.toString(2));
   	               tablemc.addContent(columnmc[3]);
   	               
   	               docxml.getRootElement().addContent(tablemc);
                     }
              
              for( pay=0; pay<listpays.size();pay++){
              	   org.jdom2.Element tablemc = new org.jdom2.Element("table");
              	   org.jdom2.Attribute attrmc = new org.jdom2.Attribute("name","items");
              	   tablemc.setAttribute(attrmc);
              	   org.jdom2.Element[] columnmc = new org.jdom2.Element[4];
              	   for(int k=0;k<columnmc.length;k++)
              		      columnmc[k] = new org.jdom2.Element("column");
              	   org.jdom2.Attribute attriditem = new org.jdom2.Attribute("name","idItem");
              	   org.jdom2.Attribute attrnl = new org.jdom2.Attribute("name","nom_long");
              	   org.jdom2.Attribute attrnb = new org.jdom2.Attribute("name","numero_brevet");
              	   org.jdom2.Attribute attriac = new org.jdom2.Attribute("name","idAnalyseChamp");
              	/*   columnmc[0].setAttribute(attriditem);
   	               columnmc[0].setText(""+pay);
   	               tablemc.addContent(columnmc[0]);*/
   	               
   	               columnmc[1].setAttribute(attrnl);
   	               columnmc[1].setText(listpays.get(pay));
   	               tablemc.addContent(columnmc[1]);
   	               
   	               columnmc[2].setAttribute(attrnb);
   	               columnmc[2].setText(npub);
   	               tablemc.addContent(columnmc[2]);
   	               
   	               columnmc[3].setAttribute(attriac);
   	               columnmc[3].setText(Integer.toString(5));
   	               tablemc.addContent(columnmc[3]);
   	               
   	               docxml.getRootElement().addContent(tablemc);
                     }
              
             //--les pays
              
              listauteurs = null;
              listpays = null;
              npub=null;dpub=null;auteurs=null;agents=null;titre=null;pays=null;
              
	    	  
	    	  
	}catch(IOException io){
		System.out.println("Erreur :" +io.getMessage());
	}
} 
   

	// new XMLOutputter().output(doc, System.out);
	XMLOutputter xmlOutput = new XMLOutputter();

	// display nice nice
	xmlOutput.setFormat(Format.getPrettyFormat());
	try {
		xmlOutput.output(docxml, new FileWriter("C:\\Users\\ABDALLAH_ENSAGINF5\\Desktop\\ProjetSTID\\file.xml"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	System.out.println("File Saved!");
}
		
	
	public static void main(String [] args){
		new CrawlerExtactionDonnee();
	}
}
