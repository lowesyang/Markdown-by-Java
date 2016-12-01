import GUI.Catalog;
import GUI.DisplayPane;
import GUI.Editor;
import GUI.UtilMenu;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame frame=new JFrame("MyFrame");
        Container rootPane=frame.getContentPane();
        frame.setLayout(new BoxLayout(rootPane,BoxLayout.X_AXIS));

        JPanel left=new JPanel();
        JPanel middle=new JPanel();
        JPanel right=new JPanel();
        left.setLayout(null);
        middle.setLayout(null);
        right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));

        DisplayPane displayPane=new DisplayPane();
        Editor editor=new Editor(displayPane);
        Catalog catalog=new Catalog(editor);

        left.add(editor);
        middle.add(displayPane);
        right.add(catalog);

        UtilMenu menuBar=new UtilMenu(editor,displayPane);
        frame.setJMenuBar(menuBar);

        rootPane.add(left);
        rootPane.add(middle);
        rootPane.add(right);

        frame.setSize(1000,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


