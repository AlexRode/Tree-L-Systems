

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class TreeLSystems extends JPanel {

    private String treeString;
    private int startX, startY;
    private double startAngle;
    private int stepLength;

    private int iterations = 5;

    public TreeLSystems(String treeString, int startX, int startY, double startAngle, int stepLength) {
        this.treeString = evolveString(treeString, iterations); // Evolui a string inicial para a 4ª geração
        this.startX = startX;
        this.startY = startY;
        this.startAngle = startAngle;
        this.stepLength = stepLength;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g, treeString, startX, startY, startAngle, stepLength);
    }

    private void draw(Graphics g, String treeString, int startX, int startY, double startAngle, int stepLength) {
        Stack<State> stateStack = new Stack<>();
        int x = startX;
        int y = startY;
        double angle = startAngle;

        g.setColor(Color.BLACK);

        for (char c : treeString.toCharArray()) {
            switch (c) {
                case 'F':
                    int newX = x + (int) (Math.cos(Math.toRadians(angle)) * stepLength);
                    int newY = y - (int) (Math.sin(Math.toRadians(angle)) * stepLength);
                    g.drawLine(x, y, newX, newY);
                    x = newX;
                    y = newY;
                    break;
                case '+':
                    angle += 25;
                    break;
                case '-':
                    angle -= 25;
                    break;
                case '[':
                    stateStack.push(new State(x, y, angle));
                    break;
                case ']':
                    State state = stateStack.pop();
                    x = state.x;
                    y = state.y;
                    angle = state.angle;
                    break;
            }
        }
    }

    // Método para evoluir a string de acordo com as regras do L-System
    private String evolveString(String startString, int iterations) {
        String currentString = startString;
        for (int i = 0; i < iterations; i++) {
            StringBuilder nextString = new StringBuilder();
            for (char c : currentString.toCharArray()) {
                switch (c) {
                    case 'X':
                        nextString.append("F+[[X]-X]-F[-FX]+X");
                        break;
                    case 'F':
                        nextString.append("FF");
                        break;
                    default:
                        nextString.append(c);
                        break;
                }
            }
            currentString = nextString.toString();
        }
        return currentString;
    }

    public static void main(String[] args) {
        String treeString = "X"; // String inicial

        TreeLSystems fractalTreePanel = new TreeLSystems(treeString, 300, 750, 90, 5); // Ajuste o tamanho do passo conforme necessário

        JFrame frame = new JFrame("Fractal Plant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fractalTreePanel);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Classe State para armazenar o estado atual da caneta
   
}