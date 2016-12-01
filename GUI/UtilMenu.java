package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class UtilMenu extends JMenuBar {
    public UtilMenu(){
        JMenu fileMenu=new JMenu("file");
        JMenu resetMenu=new JMenu("edit");

        JMenuItem importCss=new JMenuItem("Import CSS",'I');
        JMenuItem exportDocs=new JMenuItem("Export as docx",'E');
        JMenuItem exitItem=new JMenuItem("exit");

        importCss.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
        exportDocs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));

        JMenuItem clearCss=new JMenuItem("Clear CSS",'L');
        JMenuItem reset=new JMenuItem("Reset",'R');
        clearCss.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));

        fileMenu.add(importCss);
        fileMenu.add(exportDocs);
        fileMenu.add(exitItem);

        resetMenu.add(clearCss);
        resetMenu.add(reset);

        this.add(fileMenu);
        this.add(resetMenu);

//        bind event
        importCss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        exportDocs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
