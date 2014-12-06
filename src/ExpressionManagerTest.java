import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;


public class ExpressionManagerTest {

	@Test
	public void evaluatePostfixtest() {
		List<String> postfix = new ArrayList<String>();
		postfix.add("25");
		int value = ExpressionManager.evaluatePostfix(postfix);
		assertEquals("value of 25",value,25);
		postfix.add("5");
		postfix.add("+");
		value = ExpressionManager.evaluatePostfix(postfix);
		assertEquals("value of 30",value,30);
		postfix.add("3");
		postfix.add("4");
		postfix.add("1");
		postfix.add("+");
		postfix.add("*");
		postfix.add("/");
		value = ExpressionManager.evaluatePostfix(postfix);
		assertEquals("value of 2",value,2);
	}
	
	@Test
	public void evaluateinfixToPostfixtest() {
		List<String> infix = new ArrayList<String>();
		infix.add("25");
		infix.add("*");
		infix.add("30");
		List<String> postfix = ExpressionManager.infixToPostfix(infix);
		assertEquals("value of 25*30",ExpressionManager.evaluatePostfix(postfix),25*30);
		List<String> infix2 = new ArrayList<String>();
		infix2.add("5");
		infix2.add("+");
		infix2.add("3");
		infix2.add("^");
		infix2.add("(");
		infix2.add("2");
		infix2.add("-");
		infix2.add("1");
		infix2.add(")");
		infix2.add("^");
		infix2.add("(");
		infix2.add("3");
		infix2.add("*");
		infix2.add("1");
		infix2.add(")");
		List<String> postfix2 = ExpressionManager.infixToPostfix(infix2);
		assertEquals("value of 5+3^(2-1)^(3*1)",ExpressionManager.evaluatePostfix(postfix2),8);
	}
}
