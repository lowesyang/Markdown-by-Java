package GUI;

import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.RFonts;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
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
        this.openJfc.setAcceptAllFileFilterUsed(false);
        this.saveJfc.setAcceptAllFileFilterUsed(false);

        //set limit types of files can be chosen
        this.openJfc.addChoosableFileFilter(new LowesFileFilter(".css","css 文件(*.css)"));
        this.saveJfc.addChoosableFileFilter(new LowesFileFilter(".docx","docx 文件 (*.docx)"));

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

        // import css event
        importCss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openJfc.showDialog(null,"导入");
                File file=openJfc.getSelectedFile();
                if(file.isFile()){
                    try {
                        BufferedReader cssData=new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(file)
                                )
                        );
                        String temp="",cssText="";
                        while((temp=cssData.readLine())!=null){
                            cssText+=temp;
                        }
                        htmlPane.importCss(cssText);        //import css
                        editor.generateHTML(htmlPane);      //refresh htmlPan

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"请导入.css文件","警告",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //export docx event
        exportDocs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveJfc.setSelectedFile(new File("output"));
                saveJfc.showDialog(null,"导出");
                File file=saveJfc.getSelectedFile();
                String path="";
                if(file.isDirectory()){
                    path=file.getAbsolutePath()+"/output.docx";
                }
                else if(file.isFile()){
                    path=file.getAbsolutePath();
                    if(!path.toLowerCase().endsWith(".docx")){
                        path+=".docx";
                    }
                }
                else return;
                File nwFile=new File(path);
                try {

                    //A4  horizontal direction true
                    WordprocessingMLPackage wordMLPackage=WordprocessingMLPackage.createPackage(PageSizePaper.A4,true);
                    configFont(wordMLPackage);

                } catch (Exception e1) {
                    e1.printStackTrace();
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

    //config Chinese font-family
    private void configFont(WordprocessingMLPackage wordMLPackage) throws Exception {
        Mapper fontMapper = new IdentityPlusMapper();
        wordMLPackage.setFontMapper(fontMapper);

        String fontFamily = "SimSun";

        URL simsunUrl = this.getClass().getResource("/org/noahx/html2docx/simsun.ttc"); //加载字体文件（解决linux环境下无中文字体问题）
        PhysicalFonts.addPhysicalFont(simsunUrl);
        PhysicalFont simsunFont = PhysicalFonts.get(fontFamily);
        fontMapper.put(fontFamily, simsunFont);

        RFonts rfonts = Context.getWmlObjectFactory().createRFonts(); //设置文件默认字体
        rfonts.setAsciiTheme(null);
        rfonts.setAscii(fontFamily);
        wordMLPackage.getMainDocumentPart().getPropertyResolver()
                .getDocumentDefaultRPr().setRFonts(rfonts);
    }
}

