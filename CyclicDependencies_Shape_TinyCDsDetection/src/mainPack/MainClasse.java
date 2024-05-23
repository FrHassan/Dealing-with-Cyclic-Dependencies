package mainPack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainClasse {

	public static void main(String[] args) {
		CyclicDependencyHandler dpmc = new CyclicDependencyHandler(); 
		
		// Create the architecture associated graph.
		
    	/*
    	
    	Map<String, List<String>> graph = new HashMap<>();
            	
		graph.put("Order", Arrays.asList("Payment", "Inventory", "Inventory"));
		graph.put("Payment", Arrays.asList("Order", "Notification"));
		graph.put("Notification", Arrays.asList("Order", "Loyalty"));
		graph.put("Loyalty", Arrays.asList("Order"));
		graph.put("Inventory", Arrays.asList("Shipping"));
		graph.put("Shipping", Arrays.asList("Order"));
		
		//Specify the services forming cyclic dependency in the graph.
		Set<String> cyclicDependency = new HashSet<>(Arrays.asList("Order", 
																	"Payment", 
																	"Notification", 
																	"Loyalty", 
																	"Inventory", 
																	"Shipping"));
        
		*/
		
		
    	Map<String, List<String>> graph = new HashMap<>();
    	
		graph.put("Offers", Arrays.asList("Products", "Account"));
		graph.put("Account", Arrays.asList("Offers", "Products", "Orders", "ShippingAPI", "Transactions"));
		graph.put("Products", Arrays.asList("Account"));
		graph.put("Orders", Arrays.asList("Account", "Transactions", "ShippingAPI"));
		graph.put("ShippingAPI", Arrays.asList("Orders"));
		graph.put("Transactions", Arrays.asList("Orders", "Account", "Payment"));
		graph.put("Payment", Arrays.asList("Transactions"));
		
		//Specify the services forming cyclic dependency in the graph.
		Set<String> cyclicDependency = new HashSet<>(Arrays.asList("Offers", 
																	"Account", 
																	"Products", 
																	"Orders", 
																	"ShippingAPI", 
																	"Transactions", 
																	"Payment"));
		
		
		//Generate dependency matrix
        dpmc.generateDependencyMatrixForAcyclicDependency(graph, cyclicDependency);
        dpmc.printDependencyMatrix();
        
        
        // Print shape identification metrics
        System.out.println("getSize : "+dpmc.getSize());
        System.out.println("getRATIO : "+dpmc.getRATIO());
        System.out.println("getDENSE : "+dpmc.getDENSE());
        System.out.println("getSTAR : "+dpmc.getSTAR());
        System.out.println("getCHAIN : "+dpmc.getCHAIN());
        System.out.println("getBACKREF : "+dpmc.getBACKREF());
        System.out.println("getHUB: " + dpmc.getHUB());        

        //Print shape result
        System.out.println("shapeClassfication: " + dpmc.shapeClassfication());        

        
        //Detect Tiny cycles that contribute to the cyclic dependency.
        List<List<String>> tinyCyclicDependencies = dpmc.detectTinyCyclicDependencies(graph);
        System.out.println("Tiny Cycles : " + tinyCyclicDependencies);
        
	}	   
}
	

	
	
	
	

