package dz.esi.outil_onto.model.OntologyManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.rdf.model.*;
import dz.esi.outil_onto.controller.*;


//Class OpenOWL
public class openOWL {
 static  String  s="";
 public static String path ="";
 
 	//Connexion
	public static  OntModel OpenConnectOWL(){
	  OntModel mode = null;
	  mode = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );
	  java.io.InputStream in = FileManager.get().open(path); // be sure file into c:\
	  if (in == null) {
	      throw new IllegalArgumentException("Fichier ontology introuvable");
	  }
	      return  (OntModel) mode.read(in, "");
	  }
	
   // Connecté au OWL File et retourner le Jena ResultSet
   static ResultSet ExecSparQl(String Query){
	   		  org.apache.jena.query.Query query = QueryFactory.create(Query);
              QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
              org.apache.jena.query.ResultSet results = qe.execSelect();
              return results;
   }
   
   // Connecté au OWL File et retourner le String
    public static  String GetResultAsString(String Query){
      try {
    	  Query query = QueryFactory.create(Query);
	    	  if (query == null)
			   {
	    		  System.out.println("There is an error in the syntax of the query");
			   } else {
				   
	                QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
	                ResultSet results = qe.execSelect();
	                if(results.hasNext()){
	                   ByteArrayOutputStream go = new ByteArrayOutputStream ();
	                   ResultSetFormatter.out((OutputStream)go ,results, query);
	                   s = new String(go.toByteArray(), "UTF-8");
	                 }
	                else{
	                    // si rien trouvé => pour le test 
	                    s = "rien";
	                }
			   }
    	  		
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(openOWL.class.getName()).log(Level.SEVERE, null, ex);
      }
       return s;
    }
  
}
//End
