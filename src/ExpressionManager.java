/*
 * Aniket: This is a class that has two functions that assist the Calculator class.
 * The first function is an evaluatePostfix method that reads the postfix String list and
 * evaluates it into an actual value. (Basically, its dope). The second function is an
 * algorithm that converts infix notation to a postfix notation.
 * 
 * P.S. Using the ".equals" method as opposed to "==" screwed me over. A lot.
 */
import java.util.*;
import java.lang.Math;
import java.lang.ArithmeticException;

public class ExpressionManager {
	/*
	 * Evalutes the postfix list of strings into a value. Returns the value.
	 */
  public static int evaluatePostfix(List<String> postfix) {
    Stack<Integer> shitToDo = new Stack<Integer>();
    // Change the stack to a stack of integers
    for (int i = 0; i < postfix.size(); i++) {
    	if (postfix.get(i).equals("+")) {
    		int currentNumber = (shitToDo.pop()) + (shitToDo.pop());
    		shitToDo.push(currentNumber);
    	}
    	else if (postfix.get(i).equals("-")) {
    		int temp = (shitToDo.pop());
    		int currentNumber = (shitToDo.pop()) - temp;
    		shitToDo.push(currentNumber);
    	}
    	else if (postfix.get(i).equals("*")) {
    		int currentNumber = (shitToDo.pop()) * (shitToDo.pop());
    		shitToDo.push(currentNumber);	
    	}
    	else if (postfix.get(i).equals("/")) {
    		int temp = (shitToDo.pop());
    		int currentNumber = (shitToDo.pop()) / temp;
    		shitToDo.push(currentNumber);	
    	}
    	else if (postfix.get(i).equals("^")) {
    		double temp = (shitToDo.pop());
    		double temp2 = (shitToDo.pop());
    		double currentNumber = Math.pow(temp2, temp);
    		int theRealCurrentNumberWhatsup = (int)currentNumber;
    		shitToDo.push(theRealCurrentNumberWhatsup);	
    	}
    	else
    		shitToDo.push(Integer.parseInt(postfix.get(i)));
    }
    return (shitToDo.peek());
 }
  /*
   * Method that is used in the infixToPostfix method. 
   * Basically, it just checks if a string is a number or not.
   */
  public static boolean isNumeric(String str) {  
    try {  
      Integer.parseInt(str);  
    }  
    catch(NumberFormatException nfe) {  
      return false;  
    }  
    return true;  
  }
  /*
   * Method that is also used in the infixToPostfix method.
   * Basically it just checks if a string is an operator or not.
   */
  public static boolean isOperator(String str) {
	  if ((str.equals("+")) || (str.equals("-")) || (str.equals("/")) 
			  || (str.equals("^")) || (str.equals("*")))
		  return true;
	  else
		  return false;
  }
  /*
   * Method that is also used in the infixToPostfix method.
   * All it does is equate an operator to a certain priority.
   * It needs to do this, because it follows the order of operations. 
   */
  public static int priority(String str) {
	  if ((str.equals("+")) || (str.equals("-")))
		  return 1;
	  else if ((str.equals("*")) || (str.equals("/")))
		  return 2;
	  else if (str.equals("^"))
		  return 3;
	  else
		  return 0;
  }
  
  public static boolean isConsecutiveExponents(String str, String str1) {
	  if ((priority(str) == 3) && (priority(str1) == 3))
		  return true;
	  else
		  return false;
  }
  
  
  /*
   * Method that took a while to write.  
   */
  public static List<String> infixToPostfix(List<String> infix) throws ArithmeticException {
	  List<String> postfix = new ArrayList<String>();
	  Stack<String> toDo = new Stack<String>();
	  int operatorCounter = 0;
	  int digitCounter = 0;
	  for (int i = 0; i < infix.size()-1; i++) {
		  String currentStr = infix.get(i);
		  String nextStr = infix.get(i+1);
		// First exception that detects if the user put in anything that isn't usable
		  if ((!isOperator(currentStr)) && (!isNumeric(currentStr)) && (!currentStr.equals("(")) && (!currentStr.equals(")")))
			  throw new ArithmeticException("My mother never teached me Algebra");
		  if (isOperator(currentStr))
			  operatorCounter++;
		  if (isNumeric(currentStr))
			  digitCounter++;
		  if ((isOperator(currentStr)) && (isOperator(nextStr)))
			  throw new ArithmeticException("Do you even Syntax bro?");
		  if ((isNumeric(currentStr)) && (isNumeric(nextStr)))
			  throw new ArithmeticException("Do you even Syntax bro?");  
	  }
	  String Str = infix.get(infix.size()-1);
	  if (isOperator(Str))
		  operatorCounter++;
	  if (isNumeric(Str))
		  digitCounter++;
	  if (operatorCounter != digitCounter-1)
		  throw new ArithmeticException("Syntax Malfunction, step it up buddy");
	  for (int j = 0; j < infix.size(); j++) {
		  // I need this to debug
		  System.out.println("Stack: " + toDo);
		  System.out.println("Working on: " + infix.get(j));
		  System.out.println("Postfix: " + postfix);
		  System.out.println();
		  String currentStr = infix.get(j);
		  if (isNumeric(currentStr))
			  postfix.add(currentStr);
		  else if (currentStr.equals("("))
			  toDo.push(currentStr);
		  else if (currentStr.equals(")")) {
			  while (!toDo.peek().equals("("))
				  postfix.add(toDo.pop()); // Adds every operator
			  toDo.pop(); // Discards the open parenthesis that was added
		  }
		  else if (isOperator(currentStr)) {
			  if ((!toDo.empty()) && (!toDo.peek().equals("("))) {
				while ((!toDo.empty()) && (priority(currentStr) <= priority(toDo.peek())) && 
						(!isConsecutiveExponents(currentStr, toDo.peek())))
				  postfix.add(toDo.pop());
			  }
			  toDo.push(currentStr);  
			}  
	  }
	  while (!toDo.empty()) {
		  postfix.add(toDo.pop()); // Adds every operator
	  }
	  return postfix;
	  
  }
}