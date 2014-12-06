import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Calculator extends JFrame {

  private static final int buttonWidth=120, buttonHeight=40;
  private static final Font font = new java.awt.Font("SansSerif", 0, 22); 
  private static final Dimension buttonDimension = new Dimension(buttonWidth,buttonHeight);
  private static final String[] keyLabels = {"1","2","3","+","4","5","6","-","7","8","9","*",
     "(","0",")","/"};

  // all of the GUI components
  private JPanel contentPane;
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private JPanel jPanel4 = new JPanel();

  private JTextField resultField = new JTextField();
  
  private JButton[] keys = new Key[keyLabels.length];
   
  private JButton powerButton = new Key("^", buttonDimension);
  private JButton equalsButton = new Key("=", buttonDimension);
  private JButton clearButton = new JButton("clear");

  private JTextField postfixField = new JTextField();
  private JLabel postfixLabel = new JLabel("postfix:");
  private JLabel resultLabel = new JLabel("result:");
  private JTextField infixField = new JTextField();
  private JLabel infixLabel = new JLabel("infix:");

  boolean packFrame = false;

  //Construct the application
  public Calculator() {
     enableEvents(AWTEvent.WINDOW_EVENT_MASK);

     // add the components
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(jPanel1, BorderLayout.CENTER);
    contentPane.add(jPanel2,  BorderLayout.NORTH);
    this.setSize(new Dimension(4*(buttonWidth+10)-10, 400));
    this.setTitle("Calculator");

    infixField.setFont(font);
    infixField.setHorizontalAlignment(SwingConstants.RIGHT);
    postfixField.setFont(font);
    postfixField.setHorizontalAlignment(SwingConstants.RIGHT);
    resultField.setFont(font);
    resultField.setHorizontalAlignment(SwingConstants.RIGHT);

    clearButton.setPreferredSize(new Dimension(2*buttonWidth+10, buttonHeight));
    clearButton.setFont(font);

    infixLabel.setHorizontalAlignment(SwingConstants.LEFT);
    resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
    postfixLabel.setHorizontalAlignment(SwingConstants.LEFT);

    //  Place digit and operator buttons in center panel
    
    for(int i=0; i<keyLabels.length; i++){
       keys[i] = new Key(keyLabels[i], buttonDimension);
       jPanel1.add(keys[i],null);
    }

    jPanel1.add(clearButton, null);
    jPanel1.add(powerButton, null);
    jPanel1.add(equalsButton, null);

    //  Divide north panel into left and right panels
    jPanel2.setLayout(new BorderLayout());
    jPanel2.add(jPanel3, BorderLayout.WEST);
    jPanel2.add(jPanel4, BorderLayout.CENTER);
    jPanel3.setLayout(new GridLayout(3,1));
    jPanel4.setLayout(new GridLayout(3,1));

    //  Place output labels in northwest panel
    postfixLabel.setFont(font);
    resultLabel.setFont(font);
    infixLabel.setFont(font);
    jPanel3.add(infixLabel, null);
    jPanel3.add(postfixLabel, null);
    jPanel3.add(resultLabel, null);

    //  Place output fields in northcenter panel
    postfixField.setFont(font);
    infixField.setFont(font);
    jPanel4.add(infixField, null);     
    jPanel4.add(postfixField, null);
    jPanel4.add(resultField, null);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      this.pack();
    }
    else {
      this.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    this.setVisible(true);

    clearButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
         resultField.setText("");
         postfixField.setText("");
         infixField.setText("");
      }
    });
    
    equalsButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent event){
         String infixString = infixField.getText();
         StringTokenizer tokenizer = new StringTokenizer(infixString,"+-*^/() ",true);
         java.util.List<String> infix = new ArrayList<String>();
         while(tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();
            if(!token.equals(" "))
               infix.add(token);
         }
         try {
         java.util.List<String> postfix = ExpressionManager.infixToPostfix(infix);
         postfixField.setText(tokenString(postfix));
         int value = ExpressionManager.evaluatePostfix(postfix);
         resultField.setText(""+value);
         }
         catch (ArithmeticException exc){
        	 resultField.setText(exc.getMessage());
         }
      }
    });
    
    infixField.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent e){
          String infixString = infixField.getText();
          StringTokenizer tokenizer = new StringTokenizer(infixString,"+-*^/() ",true);
          java.util.List<String> infix = new ArrayList<String>();
          while(tokenizer.hasMoreTokens()){
             String token = tokenizer.nextToken();
             if(!token.equals(" "))
                infix.add(token);
          }
          try {
          java.util.List<String> postfix = ExpressionManager.infixToPostfix(infix);
          postfixField.setText(tokenString(postfix));
          
          int value = ExpressionManager.evaluatePostfix(postfix);
          resultField.setText(""+value);
          }
          catch (ArithmeticException exc){
        	  resultField.setText(exc.getMessage());
          }
         
       }
    });

    postfixField.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent e){
          String postfixString = postfixField.getText();
          StringTokenizer tokenizer = new StringTokenizer(postfixString,"+-*^/() ",true);
          java.util.List<String> postfix = new ArrayList<String>();
          while(tokenizer.hasMoreTokens()){
             String token = tokenizer.nextToken();
             if(!token.equals(" "))
                postfix.add(token);
          }
          postfixField.setText(tokenString(postfix));
          try {
        	  int value = ExpressionManager.evaluatePostfix(postfix);
        	  resultField.setText(""+value); 
          }
          catch (ArithmeticException exc){
        	  resultField.setText(exc.getMessage());
          }
       }
    });   
  }

  private String tokenString(java.util.List<String> tokens){
     String tokenString = "";
     for(String t : tokens)
        tokenString += (t+" ");
     return tokenString.trim();
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  class Key extends JButton {
     String token;
     
     public Key(String token, Dimension dimension){
        this.token = token;
        setText(token);
        setFont(font);
        setPreferredSize(dimension);
        addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
              String infixString = infixField.getText();
              infixField.setText(infixString+Key.this.token);
           }
        });
     }    
  }
  //Main method
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new Calculator();
  }

}