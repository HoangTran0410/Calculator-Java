package calculator;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class Calculator {

    public static void main(String[] args) {
        CalculatorFrame frame = new CalculatorFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
//================== Frame =======================

class CalculatorFrame extends JFrame {

    JMenuBar menuBar;
    JMenu menuMode;
    JCheckBoxMenuItem scienceMode;

    public CalculatorFrame() {
        setTitle("Calculator");

        CalculatorPanel panel = new CalculatorPanel();
        add(panel);

        scienceMode = new JCheckBoxMenuItem();
        scienceMode.setText("Science mode");
        scienceMode.setFont(new Font("Arial", 0, 16));
        scienceMode.addItemListener((ItemEvent ie) -> {
            panel.setScienceMode(ie.getStateChange() == 1);
            pack();
            setLocationRelativeTo(null);
        });

        menuMode = new JMenu();
        menuMode.setText("Mode");
        menuMode.setFont(new Font("Arial", 0, 16));
        menuMode.add(scienceMode);

        menuBar = new JMenuBar();
        menuBar.add(menuMode);
        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }
}
//================= Panel ==========================

class CalculatorPanel extends JPanel {

    private JPanel plScreen;
    private JTextField txInput;
    private JTextField txResult;
    private JPanel plBasic;
    private JPanel plScience;
    private HistoryCalculate history = new HistoryCalculate();
    private String ans;

    private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine = manager.getEngineByName("js");

    public CalculatorPanel() {
        setLayout(new BorderLayout());

        // Ans, X, Y, Z
        ans = "0";
        try {
            engine.eval("X=0; Y=0; Z=0");
        } catch (ScriptException e) {
            System.out.println("ERROR when set X,Y value");
        }

        // create panel screen
        plScreen = new JPanel();
        plScreen.setLayout(new BorderLayout());

        // add the input display to panel
        txInput = new JTextField();
        txInput.setBackground(Color.decode("#333333"));
        txInput.setForeground(Color.white);
        txInput.setFont(new Font(Font.MONOSPACED, Font.BOLD, 33));
        txInput.setHorizontalAlignment(JTextField.LEFT);
        txInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txInput.setCaretColor(Color.white);
        txInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == 13 || ke.getKeyCode() == 10) {
                    calculate();
                }
            }
        });

        plScreen.add(txInput, BorderLayout.NORTH);

        // add the result display to panel
        txResult = new JTextField();
        txResult.setBackground(Color.decode("#333333"));
        txResult.setForeground(Color.white);
        txResult.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
        txResult.setHorizontalAlignment(JTextField.RIGHT);
        txResult.setEditable(false);
        txResult.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        plScreen.add(txResult, BorderLayout.CENTER);

        // add panel to frame
        add(plScreen, BorderLayout.NORTH);

        ActionListener alInsert = new InsertAction();
        ActionListener alCommand = new CommandAction();

        // ===== Basic Panel =====
        plBasic = new JPanel();
        plBasic.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        plBasic.setLayout(new GridLayout(6, 5, 5, 5));

        // add the buttons in a 5 x 5 grid
        plBasic.add(new MyButton("←", "Move cursor to Left", alCommand, "#337cac", "#ffffff"));
        plBasic.add(new MyButton("→", "Move cursor to Right", alCommand, "#337cac", "#ffffff"));
        plBasic.add(new MyButton("Off", "Turn Off", alCommand, "#f0595f", "#dddddd"));
        plBasic.add(new MyButton("↑", "Previous history", alCommand, "#337cac", "#ffffff"));
        plBasic.add(new MyButton("↓", "Next history", alCommand, "#337cac", "#ffffff"));

        plBasic.add(new MyButton("(", "Open bracket", alInsert, "#337cac", "#ffffff"));
        plBasic.add(new MyButton(")", "Close bracket", alInsert, "#337cac", "#ffffff"));
        plBasic.add(new MyButton("+/-", "Switch sign", alCommand, "#337cac", "#ffffff"));
        plBasic.add(new MyButton("%", "Percent", alInsert, "#337cac", "#ffffff"));
        plBasic.add(new MyButton(",", "Comma", alInsert, "#337cac", "#ffffff"));

        plBasic.add(new MyButton("7", "", alInsert));
        plBasic.add(new MyButton("8", "", alInsert));
        plBasic.add(new MyButton("9", "", alInsert));
        plBasic.add(new MyButton("DEL", "Delete", alCommand, "#ffffff", "#f0595f"));
        plBasic.add(new MyButton("AC", "Clear", alCommand, "#ffffff", "#f0595f"));

        plBasic.add(new MyButton("4", "", alInsert));
        plBasic.add(new MyButton("5", "", alInsert));
        plBasic.add(new MyButton("6", "", alInsert));
        plBasic.add(new MyButton("+", "Plus", alInsert, "#337cac", "#ffffff"));
        plBasic.add(new MyButton("-", "Minus", alInsert, "#337cac", "#ffffff"));

        plBasic.add(new MyButton("1", "", alInsert));
        plBasic.add(new MyButton("2", "", alInsert));
        plBasic.add(new MyButton("3", "", alInsert));
        plBasic.add(new MyButton("×", "Multiply", alInsert, "#337cac", "#ffffff"));
        plBasic.add(new MyButton("÷", "Divide", alInsert, "#337cac", "#ffffff"));

        plBasic.add(new MyButton("0", "", alInsert));
        plBasic.add(new MyButton(".", "Dot", alInsert));
        plBasic.add(new MyButton("E", "×10^", alInsert));
        plBasic.add(new MyButton("Ans", "Last Answer", alInsert));
        plBasic.add(new MyButton("=", "Calculate", alCommand, "#ffffff", "#2e86c0"));

        add(plBasic, BorderLayout.CENTER);

        // ===== Science Panel =====
        plScience = new JPanel();
        plScience.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        plScience.setLayout(new GridLayout(6, 5, 5, 5));
        plScience.setVisible(false);

        plScience.add(new MyButton("^2", "Square", alCommand));
        plScience.add(new MyButton("^n", "Square level n", alInsert));
        plScience.add(new MyButton("sin", "Sin (in radians)", alInsert));
        plScience.add(new MyButton("cos", "Cos (in radians)", alInsert));
        plScience.add(new MyButton("tan", "Tan (in radians)", alInsert));

        plScience.add(new MyButton("√", "Square root of 2", alInsert));
        plScience.add(new MyButton("3√", "Square root of 3", alInsert));
        plScience.add(new MyButton("aSin", "arcSin (in radians)", alInsert));
        plScience.add(new MyButton("aCos", "arcCos (in radians)", alInsert));
        plScience.add(new MyButton("aTan", "arcTan (in radians)", alInsert));

        plScience.add(new MyButton("℮", "Constant e (Euler's number)", alInsert));
        plScience.add(new MyButton("π", "Constant PI", alInsert));
        plScience.add(new MyButton("Sinh", "Sinh (in radians)", alInsert));
        plScience.add(new MyButton("Cosh", "Cosh (in radians)", alInsert));
        plScience.add(new MyButton("Tanh", "Tanh (in radians)", alInsert));

        plScience.add(new MyButton("Log", "Logarit base 10", alInsert));
        plScience.add(new MyButton("Ln", "Logarit base e", alInsert));
        plScience.add(new MyButton("Rand", "Random 0.0 - 1.0", alInsert));
        plScience.add(new MyButton("Deg", "Convert toDegrees", alInsert));
        plScience.add(new MyButton("Rad", "Convert toRadians", alInsert));

        plScience.add(new MyButton("Hex", "Convert base 10 -> 16", alCommand));
        plScience.add(new MyButton("Bin", "Convert base 10 -> 2", alCommand));
        plScience.add(new MyButton("Oct", "Convert base 10 -> 8", alCommand));
        plScience.add(new MyButton("", ""));
        plScience.add(new MyButton("", ""));

        plScience.add(new MyButton("X", "Variable X", alInsert));
        plScience.add(new MyButton("Y", "Variable Y", alInsert));
        plScience.add(new MyButton("Z", "Variable Z", alInsert));
        plScience.add(new MyButton("", ""));
        plScience.add(new MyButton("", ""));

        add(plScience, BorderLayout.WEST);
    }

    public void addText(String text) {
        int p = txInput.getCaretPosition();
        String current = txInput.getText();
        String added = current.substring(0, p) + text + current.substring(p);
        txInput.setText(added);
        txInput.setCaretPosition(p + text.length());
        txResult.setText("");
    }

    private void deleteText() {
        int p = txInput.getCaretPosition();
        String current = txInput.getText();
        String added = current.substring(0, p - 1) + current.substring(p);
        txInput.setText(added);
        txInput.setCaretPosition(p - 1);
        txResult.setText("");
    }

    public void setScienceMode(Boolean on) {
        plScience.setVisible(on);
    }

    // ==================== Calculating ===================
    private void calculate() {
        String bt = txInput.getText();
        if (!bt.equals("")) {
            history.add(bt);
            String b = convert(txInput.getText());
            String result = calculateString(b);
            txResult.setText("= " + result);
            ans = result;
        }
    }

    private String calculateString(String b) {
        try {
            Object r = engine.eval(b);
            String result = String.valueOf(r);
            return result;

        } catch (ScriptException ex) {
            txResult.setText("ERROR");
            return "ERROR";
        }
    }

    private String convert(String b) {
        String[] replacement = {
            "Rad", "java.lang.Math.toRadians",
            "Deg", "java.lang.Math.toDegrees",
            "%", "/100",
            "×", "*",
            "÷", "/",
            "3√", "java.lang.Math.cbrt",
            "√", "java.lang.Math.sqrt",
            "sin", "java.lang.Math.sin",
            "cos", "java.lang.Math.cos",
            "tan", "java.lang.Math.tan",
            "aSin", "java.lang.Math.asin",
            "aCos", "java.lang.Math.acos",
            "aTan", "java.lang.Math.atan",
            "Sinh", "java.lang.Math.sinh",
            "Cosh", "java.lang.Math.cosh",
            "Tanh", "java.lang.Math.tanh",
            "Ln", "java.lang.Math.log",
            "Log", "java.lang.Math.log10",
            "Pow", "java.lang.Math.pow",
            "Rand", "java.lang.Math.random()",
            "℮", "java.lang.Math.E",
            "π", "java.lang.Math.PI"
        };

        b = b.replaceAll("Ans", ans);
        for (int i = 0; i < replacement.length; i += 2) {
            b = b.replaceAll(replacement[i], replacement[i + 1]);
        }

        return b;
    }

    //================== Insert CLass ====================
    private class InsertAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            
            txInput.requestFocus();

            if (command.equals("X") || command.equals("Y") || command.equals("Z")) {
                if (txInput.getText().length() == 0) {
                    addText(command + "=");
                } else {
                    addText(command);
                }
            } else {
                String[] replacement = {
                    "Rad", "Rad()",
                    "Deg", "Deg()",
                    "3√", "3√()",
                    "√", "√()",
                    "sin", "sin()",
                    "cos", "cos()",
                    "tan", "tan()",
                    "aSin", "aSin()",
                    "aCos", "aCos()",
                    "aTan", "aTan()",
                    "Sinh", "Sinh()",
                    "Cosh", "Cosh()",
                    "Tanh", "Tanh()",
                    "Log", "Log()",
                    "Ln", "Ln()",
                    "^n", "Pow(x,n)"
                };

                Boolean added = false;
                for (int i = 0; i < replacement.length; i += 2) {
                    if (command.equals(replacement[i])) {
                        addText(replacement[i + 1]);
                        txInput.setCaretPosition(txInput.getCaretPosition() - 1);
                        added = true;
                        break;
                    }
                }

                if (!added) {
                    addText(command);
                }
            }
        }
    }

    //================== Command Class =====================
    private class CommandAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            String current = txInput.getText();

            if (command.equals("Off")) {
                int choose = JOptionPane.showConfirmDialog(plBasic.getRootPane(), "Bạn có chắc muốn tắt Calculator?");
                if (choose == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }

            } else if (command.equals("↑")) {
                txInput.setText(history.getPre());
                txResult.setText("");

            } else if (command.equals("↓")) {
                txInput.setText(history.getNext());
                txResult.setText("");

            } else if (current.length() > 0) {
                switch (command) {
                    case "AC":
                        history.resetIndex();
                        txInput.setText("");
                        txResult.setText("");
                        break;

                    case "DEL":
                        deleteText();
                        break;

                    case "←":
                        int currentPos = txInput.getCaretPosition();
                        if (currentPos > 0) {
                            currentPos--;
                        }
                        txInput.setCaretPosition(currentPos);
                        txInput.requestFocus();
                        break;

                    case "→":
                        int currentPos2 = txInput.getCaretPosition();
                        if (currentPos2 < txInput.getText().length()) {
                            currentPos2++;
                        }
                        txInput.setCaretPosition(currentPos2);
                        txInput.requestFocus();
                        break;

                    case "+/-":
                        if (current.charAt(0) == '-') {
                            String deletedMinus = current.substring(1);
                            txInput.setText(deletedMinus);
                        } else {
                            txInput.setText("-" + current);
                        }
                        break;

                    case "^2":
                        txResult.setText(calculateString("Math.pow(" + current + ", 2)"));
                        break;

                    case "Hex":
                        history.add(current);
                        try {
                            String cal = calculateString(convert(current));
                            int c = Integer.parseInt(cal);
                            String result = hex(c);

                            txResult.setText(result);

                        } catch (NumberFormatException e) {
                            txResult.setText("ERROR (only int -> hex)");
                        }
                        break;

                    case "Bin":
                        history.add(current);
                        try {
                            String cal = calculateString(convert(current));
                            int kq = Integer.parseInt(cal);
                            String result1 = Integer.toBinaryString(kq);
                            txResult.setText(result1);

                        } catch (NumberFormatException e) {
                            txResult.setText("ERROR (only int -> bin)");
                        }
                        break;

                    case "Oct":
                        history.add(current);
                        try {
                            String cal = calculateString(convert(current));
                            int c = Integer.parseInt(cal);
                            String result2 = Integer.toOctalString(c);
                            txResult.setText(result2);
                        } catch (NumberFormatException e) {
                            txResult.setText("ERROR (only int -> oct)");
                        }
                        break;

                    case "=":
                        calculate();
                        break;

                    default:
                        break;
                }
            }
        }
    }

    // =================== HEX =======================
    public static String hex(int n) {
        // call toUpperCase() if that's required
        return String.format("%s", Integer.toHexString(n)).replace(' ', '0');
    }

    public static String hex(float f) {
        // change the float to raw integer bits(according to the OP's requirement)
        return hex(Float.floatToRawIntBits(f));
    }

    //================== History Class =====================
    private class HistoryCalculate {

        private ArrayList<String> arrHistory;
        private int indexHistory;

        public HistoryCalculate() {
            arrHistory = new ArrayList<>(0);

            Date d = new Date();
            arrHistory.add("Author: HoangTran");
            arrHistory.add("App open at " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds());

            indexHistory = arrHistory.size();
        }

        public void resetIndex() {
            indexHistory = arrHistory.size();
        }

        public void add(String h) {
            arrHistory.add(h);
            resetIndex();
        }

        public String getPre() {
            indexHistory--;
            if (indexHistory < 0) {
                indexHistory = 0;
            }
            return arrHistory.get(indexHistory);
        }

        public String getNext() {
            indexHistory++;
            if (indexHistory > arrHistory.size() - 1) {
                indexHistory = arrHistory.size() - 1;
            }
            return arrHistory.get(indexHistory);
        }
    }
}

// ====================== Custom Button ========================
class MyButton extends JButton {
    
    public MyButton() {
        setVisible(false);
    }

    public MyButton(String label, String tooltip) {
        defaultSetting(label, tooltip);
    }

    public MyButton(String label, String tooltip, String color, String bgColor) {
        defaultSetting(label, tooltip, color, bgColor);
    }

    public MyButton(String label, String tooltip, ActionListener listener) {
        defaultSetting(label, tooltip);
        addActionListener(listener);
    }

    public MyButton(String label, String tooltip, ActionListener listener, String color, String bgColor) {
        defaultSetting(label, tooltip, color, bgColor);
        addActionListener(listener);
    }

    private void defaultSetting(String label, String tooltip) {
        setPreferredSize(new Dimension(90, 75));
        setText(label);
        setToolTipText(tooltip);
        setFont(new Font("Consolas", Font.BOLD, 26));
        setBackground(Color.decode("#ffffff"));
        setForeground(Color.decode("#000000"));
        setFocusable(false);
    }

    private void defaultSetting(String label, String tooltip, String color, String bgColor) {
        defaultSetting(label, tooltip);
        setBackground(Color.decode(bgColor));
        setForeground(Color.decode(color));
    }

    public void setBgColor(String c) {
        setBackground(Color.decode(c));
    }

    public void setColor(String c) {
        setForeground(Color.decode(c));
    }
}
