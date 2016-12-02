package GUI;
import com.petebevin.markdown.MarkdownProcessor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class Editor extends JScrollPane{
    private JTextPane editor;
    private MarkdownProcessor processor;
    private JScrollBar scrollBar;

    public Editor(DisplayPane displayPane,Catalog catalog) throws IOException {
        this.processor=new MarkdownProcessor();
        this.editor = new JTextPane();
        this.scrollBar=this.getVerticalScrollBar();
        this.editor.setFont(new Font("TimesRoman", Font.PLAIN,14));
        this.getViewport().add(this.editor);
        this.setBounds(5,40,300,480);

        this.editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    generateHTML(displayPane);
                    catalog.update(editor.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    generateHTML(displayPane);
                    catalog.update(editor.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    generateHTML(displayPane);
                    catalog.update(editor.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        catalog.getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index=catalog.getList().getSelectedIndex();
                if(index<0) return;
                ArrayList<Integer> arrayList=catalog.getRowArr();
                System.out.println(catalog.getList().getSelectedIndex());
                scrollBar.setValue(arrayList.get(index)*19);
            }
        });
    }

    //generate from text with format of markdown to HTML
    public void generateHTML(DisplayPane displayPane) throws IOException {
        String markdown=this.editor.getText();
        displayPane.setContent(this.processor.markdown(markdown));
    }

    public JTextPane getEditor(){
        return this.editor;
    }

}
