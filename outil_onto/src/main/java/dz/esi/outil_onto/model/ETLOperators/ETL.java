package dz.esi.outil_onto.model.ETLOperators;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.jena.ontology.Individual;
import dz.esi.outil_onto.model.ETLMetadata.DSA;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.sparql.core.DatasetImpl;
import org.apache.jena.util.iterator.ExtendedIterator;

import dz.esi.outil_onto.controller.Controller;
import dz.esi.outil_onto.model.DataSources.DataSource;
import dz.esi.outil_onto.model.DataSources.ExtSource;
import dz.esi.outil_onto.model.DataSources.IntSource;
import dz.esi.outil_onto.model.DataSources.IntermSource;
import dz.esi.outil_onto.model.DataSources.TypeSource;
import dz.esi.outil_onto.model.OntologyManager.Jena;

// Ensemble des requêtes SPARQL possibles et des fonctions permettant d'exécuter ces requêtes sur des sources
public class ETL {
	private static final TypeSource External = null;
	private static final TypeSource Internal = null;
	private static final TypeSource Intermediate = null;
	
	//préfixes les plus utilisés avec dbpedia, déjà chargés pour les requêtes
	public static String prefix="Prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "Prefix foaf: <http://xmlns.com/foaf/0.1/>"
			+ "Prefix dbo: <http://dbpedia.org/ontology/>"
			+ "Prefix dbr: <http://dbpedia.org/resource/>"
			+ "Prefix dbp: <http://dbpedia.org/property/>"
			+ "Prefix xsd: <http://www.w3.org/2001/XMLSchema#>"
			+ "Prefix dt:  <http://dbpedia.org/datatype/>"
			+ "Prefix dc: <http://purl.org/dc/elements/1.1/>"
			+ "Prefix j.0: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/>"
			+ "Prefix ont: <http://www.co-ode.org/ontologies/ont.owl#>"
			+ "Prefix owl: <http://www.w3.org/2002/07/owl#>"
			+ "Prefix protege: <http://protege.stanford.edu/plugins/owl/protege#>"
			+ "Prefix vocabulary: <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/vocabulary/>"
			+ "Prefix xsp: <http://www.owl-ontologies.com/2005/08/07/xsp.owl#>"
			+ "Prefix swrlb: <http://www.w3.org/2003/11/swrlb#>"
			+ "Prefix swrl: <http://www.w3.org/2003/11/swrl#>"
			+ "Prefix rev: <http://purl.org/stuff/rev#>";
	public static int tripInserted=0;
	
	//ajout d'un préfixe après la construction
	public static void addPrefix(String pre)
		{prefix+=pre;}
	
	//Fonctions pour exécuter les requêtes sparql sur une source qui renvoient toutes un Dataset
	
	// execute la requete sur une source extérieure 
	public static Dataset Run (String squery, String sour)
	{
		Query query2 = QueryFactory.create(squery);
        QueryExecution qexec2 = QueryExecutionFactory.sparqlService(sour, query2);
        Dataset results2 = qexec2.execConstructDataset();
        return results2;
	}
	//execute la requête sur la réunion de deux datasets (ex:Merge, Join)
	public static Dataset Run(String squery, Dataset d1, Dataset d2)
	{
		Dataset d=DatasetFactory.createTxnMem();
		RDFConnection conn=RDFConnectionFactory.connect(d);
		conn.loadDataset(d1);
		conn.loadDataset(d2);
		Model mod = conn.queryConstruct(squery);
		Dataset rep= new DatasetImpl(mod);
		return rep;
	}
	//execute la requête sur un dataset (ex:Extract, Filter)
	public static Dataset Run(String squery, Dataset d1)
	{
		Dataset d=DatasetFactory.createTxnMem();
		RDFConnection conn=RDFConnectionFactory.connect(d);
		conn.loadDataset(d1);
		Model mod = conn.queryConstruct(squery);
		Dataset rep= new DatasetImpl(mod);
		return rep;
		}
	
		
	// fucntion converting an RDF model to string format 
	public static String convertModelToString(Model m){
		String res="";
		
		StmtIterator stmtIterator= m.listStatements();
		while(stmtIterator.hasNext()){
			Statement s = stmtIterator.nextStatement();
			Resource sub= s.getSubject();
			Property pro=s.getPredicate();
			Resource obj=(Resource) s.getObject();
			res =res + sub.getLocalName() +" | "+pro.getLocalName() + " | "+obj.getLocalName()+"\n";
			
		}
		return res;
	}
	
	
	//Detect the type of the ETL operator using the Resource, and return a new object of an ETL operation
	public static ETLOperation verifyType(Resource op){
		typeETLOp type = null;
		
		if (op.toString().contains("retrieve")){
			Retrieve opp = new Retrieve();
			opp.setType(typeETLOp.Retrieve);
			return opp;
		}else{
		if (op.toString().contains("union")){
			Union opp = new Union();
			opp.setType(typeETLOp.Union);
			return opp;
		}else{
		if (op.toString().contains("load")){
			Load opp = new Load();
			opp.setType(typeETLOp.Load);
			return opp;
		}else{
		if (op.toString().contains("agregate")){
			Agregate opp = new Agregate();
			opp.setType(typeETLOp.Agregate);
			return opp;
		}else{
		if (op.toString().contains("convert")){
			Convert opp = new Convert();
			opp.setType(typeETLOp.Convert);
			return opp;
		}else{
		if (op.toString().contains("extract")){
			Extract opp = new Extract();
			opp.setType(typeETLOp.Extract);
			return opp;
		}else{
		if (op.toString().contains("join")){
			Join opp = new Join();
			opp.setType(typeETLOp.Join);
			return opp;
		}else{
		if (op.toString().contains("merge")){
			Merge opp = new Merge();
			opp.setType(typeETLOp.Merge);
			return opp;
		}else {
			System.out.println("The operation is unknown!");
			ETLOperation opp = new ETLOperation();
			return opp;
		}}}}}}}}
	}
	
	// Detect the type of the data source and return a new DataSource object with the appropriate type
	public static DataSource sourceType(ETLOperation op){
		if (op.Position.equals("1")){
			if(verifyExternalResource(op.Resource)){
				ExtSource Ext = new ExtSource(TypeSource.External,removeBrackets(op.Resource));
				return Ext;
			}else{
				OntModel data=Controller.ontology;
				IntSource Int = new IntSource(TypeSource.Internal,data);
				return Int;
			}
		}else{
			IntermSource Interm = new IntermSource(TypeSource.Intermediate);
			return Interm;
		}
	}
	
	// Detect the type of the data source and return a new DataSource object with the appropriate type for ETL integration
	public static DataSource sourceTypeIntExt(ETLOperation op){
		Resource res; 
		if (op.Resource != null){
			if(verifyExternal(op.Resource)){
				ExtSource Ext = new ExtSource(TypeSource.External,op.Resource);
				return Ext;
			}else{
				OntModel data=Controller.ontology;
				IntSource Int = new IntSource(TypeSource.Internal,data);
				return Int;
			}
		}
		return null;
	}
	
	//LOD external resources in the ontology are defined between <...>, This function will verify if the resource is and internal or external source
	public static boolean verifyExternal(String resource){
		boolean Ext=false;
		if ((resource.contains("<"))&&(resource.contains(">"))){
			Ext=true;
		}
		return false;
	}
	
	//Verify a ressource if it's external by verifing if it is an individu of "ExternalClass"
		public static boolean verifyExternalResource(String resource){
			OntClass ExternalClass = Controller.ontology.getOntClass("http://protege.stanford.edu/plugins/owl/protege#ExternalClass");
			
			ExtendedIterator instances = ExternalClass.listInstances();
		      
			  while (instances.hasNext())
		      {
		        Individual thisInstance = (Individual) instances.next();
		        if (thisInstance.toString().equals(resource)== true){
		        	System.out.println("Indiv :"+thisInstance.toString());
		        	return true;
		        }
		      }
			return false;
		}
	
	// return the resource wethout <..>
	public static String removeBrackets(String resource){
		String res[];
		res= resource.split("<",2);
		resource=res[1];
		res= resource.split(">",2);
		resource=res[0];
		return resource;
	}
	
	// Order the liste of ETL operations by using position
	public static ArrayList<ETLOperation> orderETLOperations(ArrayList<ETLOperation> liste){
		ArrayList<ETLOperation> op = new ArrayList<ETLOperation>();
		int i=1;
		System.out.println("la taille :"+liste.size());
		while(i<liste.size()){
			for (ETLOperation m:liste){
				System.out.println(" i ===== :"+i);
				if (Integer.parseInt(m.Position) ==i){
					op.add(m);
					System.out.println(" j'ai trouv� i ===== :"+i);
					i++;
				}
			}
		}
		return op;
	}
	// chaque fonction prend en entrée les données nécessaires de la requête (nom de classe, de propriété,...) et renvoie sous forme de 
	// chaine de caractères la requête SPARQL à exécuter.
	public static String Aggregate (AggFunction function, String classe, String prop1, String prop2, String prop)
	{
			return prefix+"Construct {?a "+prop+" ?b}{Select ("+function+"(?c) as ?b) ?a where {?d a "+classe+";"+prop1+" ?c;"+prop2+" ?a} group by ?a}";
	}
	
	public static String Count (String classe, String prop1,String prop) 
	{
		return prefix + "Construct {?a "+prop+" ?b} {Select (count(?c) as ?b) ?a where {?c a "+classe+";"+prop1+" ?a} group by ?a}";
	}
	
	public static String Filter (String classe,String prop,String cond)
	{
		return prefix+"Construct {?instance ?r ?o} where {?instance rdf:type "+classe+
				";?r ?o; " +prop+" ?P . FILTER (?P"+cond+")}";
	}
	
	public static String Convert(String function, String classe, String prop, String type, String propName) {
		return prefix+"Construct {?a "+propName+" ?b} {select ?a ("+function+"(?c) as ?b) "
				+ "where {?a a "+classe+";"+prop+" ?c. filter(datatype(?c)="+type+")}}";
	}
	
	
	public static String Retrieve(String classe)
	{
		return prefix+" Construct where {?s rdf:type <"+classe+">.} ";
	}


	public static String Merge (String classe1, String classe2,String classe)
	{
		return prefix+"Construct {?s rdf:type "+classe+"} Where {{?s rdf:type "+classe1+
				"} Union {?s rdf:type " + classe2+"}}";
	}

	public static String Union(ArrayList<String> classes)
	{
		String query="";
		query = query+ prefix+"Construct {?s rdf:type <http://www.co-ode.org/ontologies/ont.owl#ProductType> } Where {";
		for (String m:classes) {
			query= query + "{?s rdf:type <" +m+"> } Union";
		}
		String res[];
		res= query.split("Union");
		int l = res.length;
		query="";
		for(int i=0;i<l-1;i++){
			query= query+res[i]+"union";
		}
		query=query+res[l-1];
		query= query + "}";
		return query;
	}

	public static String Join(String classe1, String classe2, String prop)
	{
		return prefix+"Construct where {?n "+prop+" ?s; rdf:type "+classe1+".?s rdf:type "+classe2+"}";
	}
	
	public static String Extract(String classe, String prop)
	{
		return prefix+ "Construct {?s <"+prop+"> ?p} where {?s a <"+classe+">; <"+prop+"> ?p} ";
	}

	// Trois fonction d'enregistrement (instances de classes, ObjectProperty, DatatypeProperty). Elles exécutent une requête SELECT
	// puis l'enregistrement dans l'ontologie est faite via la classe Jena. On compte le nombre d'éléments ajoutés.
	public static void Store(OntModel onto, String classe, String name, Dataset data)
	{
		String squery= prefix+"Select distinct ?"+name+" where {?"+name+" rdf:type "+classe+"}";
		Query query2 = QueryFactory.create(squery);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2,data);
		ResultSet res = qexec2.execSelect();
		tripInserted=0;
		while (res.hasNext()) 
		{
			QuerySolution qs=res.next();
			if (Jena.addInstance(onto,qs.get(name).toString() , name))
				tripInserted+=1;
		}
	}
	public static void StoreP(OntModel onto,String name, String name2, String prop,Dataset data)
	{
		String squery= prefix+"Select distinct ?"+name+" ?"+name2+" where {?"+name+" "+prop+" ?"+name2+"}";
		Query query2 = QueryFactory.create(squery);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2,data);
		ResultSet res = qexec2.execSelect();
		tripInserted=0;
			while (res.hasNext()) 
		{
			QuerySolution qs=res.next();
			if (Jena.addProperty(onto, qs.get(name).toString(),prop,qs.get(name2).toString(),name,name2))
				tripInserted+=1;
		}
	}
	public static void StoreD(OntModel onto,String name, String prop,Dataset data)
	{
		String squery= prefix+"Select distinct ?"+name+" ?a where {?"+name+" "+prop+" ?a}";
		Query query2 = QueryFactory.create(squery);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2,data);
		ResultSet res = qexec2.execSelect();
		tripInserted=0;
		while (res.hasNext()) 
		{
			QuerySolution qs=res.next();
			if (Jena.addDataProperty(onto, qs.get(name).toString(),prop,qs.get("a").toString(),name))
				tripInserted+=1;
		}
	}
	
	// fonction d'affichage sur la console, pas nécessaire au programme.
	/*public static void CAfficher(Dataset data) {
		String squery= prefix+"Select ?s ?p ?o where {?s ?p ?o} limit 50";
		Query query2 = QueryFactory.create(squery);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2,data);
		ResultSet res = qexec2.execSelect();
		int iCount=0;
        while (res.hasNext()) 
        {
        	QuerySolution qs = res.next();
        	Iterator<String> itVars = qs.varNames();
        	iCount++;
        	System.out.println("Result " + iCount + ": ");
        	String szVar = null;
        	String szVal = null;
        	while (itVars.hasNext()) 
        	{
        		szVar = itVars.next().toString();
        		szVal = qs.get(szVar).toString();                
        		System.out.println("[" + szVar + "]: " + szVal);
        	}
        }
	}*/

	// fonctions d'affichage, avec limite par défault à 50 résultats, avec une surchage permettant de choisir le nombre de résultats
	public static String Afficher(Dataset data) {
		String squery= prefix+"Select ?s ?p ?o where {?s ?p ?o} limit 50";
		Query query2 = QueryFactory.create(squery);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2,data);
		ResultSet res = qexec2.execSelect();
		int iCount=0;
		String result="";
        while (res.hasNext()) 
        {
        	QuerySolution qs = res.next();
        	Iterator<String> itVars = qs.varNames();
        	iCount++;
        	result+="Result " + iCount + ": \n";
        	String szVar = null;
        	String szVal = null;
        	while (itVars.hasNext()) 
        	{
        		szVar = itVars.next().toString();
        		szVal = qs.get(szVar).toString();                
        		result+="[" + szVar + "]: " + szVal+"\n";
        	}
        }
        return result;
	}
	public static String Afficher(Dataset data, int nb) {
		String squery= prefix+"Select ?s ?p ?o where {?s ?p ?o} limit "+nb;
		Query query2 = QueryFactory.create(squery);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2,data);
		ResultSet res = qexec2.execSelect();
		int iCount=0;
		String result="";
        while (res.hasNext()) 
        {
        	QuerySolution qs = res.next();
        	Iterator<String> itVars = qs.varNames();
        	iCount++;
        	result+="Result " + iCount + ": \n";
        	String szVar = null;
        	String szVal = null;
        	while (itVars.hasNext()) 
        	{
        		szVar = itVars.next().toString();
        		szVal = qs.get(szVar).toString();                
        		result+="[" + szVar + "]: " + szVal+"\n";
        	}
        }
        return result;
	}
	
	
}

