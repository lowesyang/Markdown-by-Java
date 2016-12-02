package GUI;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.RFonts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;

public class UtilMenu extends JMenuBar {
    private JFileChooser saveJfc,openJfc;

    public UtilMenu(Editor editor,DisplayPane htmlPane){
        this.openJfc=new JFileChooser("./");
        this.saveJfc=new JFileChooser("./");
        this.openJfc.setFileSelectionMode(JFileChooser.FILES_ONLY|JFileChooser.OPEN_DIALOG);
        this.saveJfc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);

        // prevent to choose all types of files
        this.saveJfc.setAcceptAllFileFilterUsed(false);

        //set limit types of files can be chosen
        this.openJfc.addChoosableFileFilter(new LowesFileFilter(".css","css 文件(*.css)"));
        this.openJfc.addChoosableFileFilter(new LowesFileFilter(".md","md 文件(*.md)"));
        this.saveJfc.addChoosableFileFilter(new LowesFileFilter(".docx","docx 文件 (*.docx)"));

        JMenu fileMenu=new JMenu("file");
        JMenu resetMenu=new JMenu("edit");

        JMenuItem importCss=new JMenuItem("Import file",'I');
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

        // import css event
        importCss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openJfc.setDialogTitle("导入.md或.css文件");
                int option=openJfc.showDialog(null,"导入");
                if(option==JFileChooser.APPROVE_OPTION) {
                    File file = openJfc.getSelectedFile();
                    if (file.isFile()) {
                        try {
                            BufferedReader content = new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(file)
                                    )
                            );
                            String temp = "", text = "";
                            while ((temp = content.readLine()) != null) {
                                text += temp+"\n";
                            }
                            if (file.getName().endsWith(".css")) {       //if css
                                htmlPane.importCss(text);        //import css
                                editor.generateHTML(htmlPane);      //refresh htmlPan
                            }
                            else if(file.getName().endsWith(".md")){       //if markdown
                                editor.getEditor().setText(text);
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "请导入.css或.md文件", "警告", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        //export docx event
        exportDocs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveJfc.setSelectedFile(new File("output"));
                saveJfc.setDialogTitle("导出为word文件");
                int option=saveJfc.showDialog(null,"导出");
                if(option==JFileChooser.APPROVE_OPTION) {
                    File file = saveJfc.getSelectedFile();
                    String path = "";
                    if (file.isDirectory()) {
                        path = file.getAbsolutePath() + "/output.docx";
                    } else {
                        System.out.println(file.getAbsolutePath());
                        path = file.getAbsolutePath();
                        if (!path.toLowerCase().endsWith(".docx")) {
                            path += ".docx";
                        }
                    }
                    try {

                        //A4  horizontal direction true
                        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, true);
                        XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
                        wordMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(htmlPane.getXHTML(), null));

                        wordMLPackage.save(new File(path));     //export docx file
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
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

