package Model;

public class Complexity {
	int N;
	int L;
	
	
	public Complexity(){
		
	}
	
	public float calcComplexity(String x){
		//Find subset of what all is possible
		
		//if it contains one digit,
        if( x.matches("(?=.*[0-9]).*") ){
        	 N += 10;
        }
           
        
        //if it contains one lower case letter, add 2 to total score
        if( x.matches("(?=.*[a-z]).*") ){
        	 N += 10;
        }
           
        
        //if it contains one upper case letter, add 2 to total score
        if( x.matches("(?=.*[A-Z]).*") ){
            N += 26;    
        }
        
        
        //if it contains one special character, add 2 to total score
        if( x.matches("(?=.*[~!@#$%^&*()_-]).*") ){
        	 N += 13;
        }
        
       float result = (float) (x.length() * (Math.log10(N)/Math.log10(2)));
       N = 0;
       
       return result;
           
	}
	
	

}
