import GUI.*;

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
        right.setLayout(null);

        JLabel editLabel=new JLabel("编辑器",JLabel.CENTER);
        editLabel.setBounds(0,0,300,50);
        JLabel displayLabel=new JLabel("预览",JLabel.CENTER);
        displayLabel.setBounds(0,0,300,50);
        JLabel catalogLabel=new JLabel("目录",JLabel.CENTER);
        catalogLabel.setBounds(0,0,200,50);

        Catalog catalog=new Catalog();
        DisplayPane displayPane=new DisplayPane();
        Editor editor=new Editor(displayPane,catalog);

        left.add(editLabel);
        left.add(editor);
        middle.add(displayLabel);
        middle.add(displayPane);
        right.add(catalogLabel);
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


