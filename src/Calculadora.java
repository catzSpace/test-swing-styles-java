import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;


public class Calculadora extends JFrame implements ActionListener{


  int boardWidth = 400;
  int boardHeight = 620;
  
  // Elements
  JPanel panel;
  JTextField resultPanel;
  JPanel buttonsPanel;
  JButton themeButton;
  JTextField opView;
  ArrayList<JButton> botones;

  String operador = "";
  double numero1 = 0;
  boolean nuevaEntrada = true;

  // triger theme
  boolean isLightTheme = false;

  // General
  Color customRightSimbols = new Color(251, 139, 161);
  Color customTopSimbols = new Color(70, 244, 230);

  // Theme light
  Color customLightBg = new Color(255, 255, 255);
  Color customLightPanel = new Color(248, 248, 248);
  Color customLightFg = new Color(0,0,0);

  // Theme Dark 
  Color customDarkBg = new Color(34, 37, 45);
  Color customDarkPanel = new Color(40, 43, 50);
  Color customDarkFg = new Color(252, 253, 253);

  String[] buttonValues = {
        "sen", "cos", "tan", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "AC", "0", ".", "="
  };


  public Calculadora(){

    botones = new ArrayList<>();

    setTitle("Calculadora");
    setSize(boardWidth, boardHeight);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setBackground(customDarkBg);

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
    panel.setBackground(customDarkBg);
    add(panel, BorderLayout.NORTH);

    themeButton = new JButton("☼");
    themeButton.setFont(new Font("Arial", Font.BOLD, 30));
    themeButton.setBackground(customDarkPanel);
    themeButton.setForeground(customDarkFg);
    themeButton.setBorderPainted(false);  
    themeButton.setFocusPainted(false);  
    themeButton.setContentAreaFilled(false); 
    themeButton.addActionListener(e -> cambiarTema());
    themeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    resultPanel = new JTextField();
    resultPanel.setHorizontalAlignment(JTextField.RIGHT);
    resultPanel.setBackground(customDarkBg);
    resultPanel.setForeground(customDarkFg);
    resultPanel.setFont(new Font("Arial", Font.BOLD, 24));
    resultPanel.setEditable(false);
    resultPanel.setBorder(null);

    opView = new JTextField();
    opView.setHorizontalAlignment(JTextField.RIGHT);
    opView.setBackground(customDarkBg);
    opView.setForeground(customDarkFg);
    opView.setFont(new Font("Arial", Font.BOLD, 14));
    opView.setEditable(false);
    opView.setBorder(null);


    panel.add(themeButton);
    panel.add(Box.createVerticalStrut(10));
    panel.add(opView, BorderLayout.NORTH);
    panel.add(Box.createVerticalStrut(10));
    panel.add(resultPanel, BorderLayout.NORTH);

    buttonsPanel = new JPanel();
    buttonsPanel.setBackground(customDarkPanel);
    buttonsPanel.setLayout(new GridLayout(5, 4, 20, 20));
    buttonsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

    // Numbers buttons
    for (String value : buttonValues) {
      JButton btn = new JButton(value);
      btn.setFont(new Font("Arial", Font.BOLD, 22));
      btn.addActionListener(this);
      buttonsPanel.add(btn);

      if (value.equals("AC")){
        btn.setBackground(customDarkBg);
        btn.setForeground(customTopSimbols);
      } else if (value.matches("sen|cos|tan")){
        btn.setBackground(customDarkBg);
        btn.setForeground(customTopSimbols);
      } else if (value.matches("[÷×\\-+=]")){
        btn.setBackground(customDarkBg);
        btn.setForeground(customRightSimbols);
      } else {
        btn.setBackground(customDarkBg);
        btn.setForeground(customDarkFg);
      }

      btn.setBorderPainted(false);  
      btn.setFocusPainted(false);  
      btn.setContentAreaFilled(false);
      botones.add(btn);
    }

    add(buttonsPanel, BorderLayout.CENTER);
    setVisible(true);
  }

  private void cambiarTema() {
    isLightTheme  = !isLightTheme;

    for (JButton boton : botones) {
      if (isLightTheme) {
        if (boton.getText().matches("[0-9.]")){
          boton.setBackground(customLightBg);
          boton.setForeground(customLightFg);
        }
        getContentPane().setBackground(customLightBg);
        resultPanel.setBackground(customLightBg);
        resultPanel.setForeground(customLightFg);
        buttonsPanel.setBackground(customLightPanel);
        panel.setBackground(customLightBg);
        opView.setBackground(customLightBg);
        opView.setForeground(customLightFg);
        themeButton.setBackground(customLightBg);
        themeButton.setForeground(customLightFg);
        themeButton.setText("☾");
      } else {
        if (boton.getText().matches("[0-9.]")){
          boton.setBackground(customDarkBg);
          boton.setForeground(customDarkFg);
        }
        getContentPane().setBackground(customDarkBg);
        resultPanel.setBackground(customDarkBg);
        resultPanel.setForeground(customDarkFg);
        buttonsPanel.setBackground(customDarkPanel);
        panel.setBackground(customDarkBg);
        opView.setBackground(customDarkBg);
        opView.setForeground(customDarkFg);
        themeButton.setBackground(customDarkBg);
        themeButton.setForeground(customDarkFg);
        themeButton.setText("☼");
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
        String botonPresionado = e.getActionCommand();

        if (botonPresionado.matches("[0-9.]")) {
            if (nuevaEntrada) {
                resultPanel.setText(botonPresionado);
                nuevaEntrada = false;
            } else {
                resultPanel.setText(resultPanel.getText() + botonPresionado);
            }
        } else if (botonPresionado.matches("[÷×\\-+]")) {
            numero1 = Double.parseDouble(resultPanel.getText());
            operador = botonPresionado;
            nuevaEntrada = true;
        } else if (botonPresionado.matches("sen|cos|tan")) {
            double valor = Double.parseDouble(resultPanel.getText());
            double resultado = 0;
            switch (botonPresionado) {
                case "sen": {
                  opView.setText("sen(" + String.valueOf(valor) + ")");
                  resultado = Math.sin(Math.toRadians(valor)); 
                  break;
                }
                case "cos": {
                  opView.setText("cos(" + String.valueOf(valor) + ")");
                  resultado = Math.cos(Math.toRadians(valor)); 
                  break;
                }
                case "tan": {
                  opView.setText("tan(" + String.valueOf(valor) + ")");
                  resultado = Math.tan(Math.toRadians(valor)); 
                  break;
                }
            }
            resultPanel.setText(String.valueOf(resultado));
        } else if (botonPresionado.equals("=")) {
            double numero2 = Double.parseDouble(resultPanel.getText());
            double resultado = 0;
            switch (operador) {
                case "+": {
                  opView.setText(String.valueOf(numero1) + " + " + String.valueOf(numero2));
                  resultado = numero1 + numero2; 
                  break;
                }
                case "-": {
                  opView.setText(String.valueOf(numero1) + " - " + String.valueOf(numero2));
                  resultado = numero1 - numero2; 
                  break;
                }
                case "×": {
                  opView.setText(String.valueOf(numero1) + " × " + String.valueOf(numero2));
                  resultado = numero1 * numero2; 
                  break;
                }
                case "÷": {
                  opView.setText(String.valueOf(numero1) + " ÷ " + String.valueOf(numero2));
                  resultado = (numero2 != 0) ? numero1 / numero2 : 0; 
                  break;
                }
            }
            resultPanel.setText(String.valueOf(resultado));
            nuevaEntrada = true;
        } else if (botonPresionado.equals("AC")) {
            resultPanel.setText("");
            opView.setText("");
            operador = "";
            numero1 = 0;
            nuevaEntrada = true;
        }
    }

  public static void main(String[] args){
    new Calculadora();
  }

}
