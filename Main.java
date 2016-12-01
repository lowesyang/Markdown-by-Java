import GUI.DisplayPane;
import GUI.Editor;
import GUI.UtilMenu;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame  frame=new JFrame("MyFrame");
        frame.setLayout(new GridLayout(1,2));

        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        panel1.setLayout(null);
        panel2.setLayout(null);

        DisplayPane displayPane=new DisplayPane();
        panel1.add(displayPane);

        Editor editor=new Editor(displayPane);
        panel2.add(editor);

        UtilMenu menuBar=new UtilMenu(editor,displayPane);
        frame.setJMenuBar(menuBar);

        frame.add(panel1);
        frame.add(panel2);

        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


