package seng202.group2.view;


public enum GraphType {
    
	 SCATTER("Scatter"),
		 	
	
	 
	 
	 
	 
     BAR("Bar"),
			
     
     
     LINE("Line");
		
	

     private String type;

     GraphType(String type) {
         this.type = type;
     }

     public String toString() {
         return type;
     }
     

     


}





