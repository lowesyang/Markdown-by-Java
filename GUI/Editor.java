package GUI;
import com.petebevin.markdown.MarkdownProcessor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.IOException;


public class Editor extends JScrollPane{
    private JTextPane editor;
    private MarkdownProcessor processor;

    public Editor(DisplayPane displayPane) throws IOException {
        this.processor=new MarkdownProcessor();
        this.editor = new JTextPane();
        this.editor.setFont(new Font("TimesRoman", Font.PLAIN,14));
        this.getViewport().add(this.editor);
        this.setBounds(2,10,380,520);

        this.editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    generateHTML(displayPane);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    generateHTML(displayPane);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    generateHTML(displayPane);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    //generate from text with format of markdown to HTML
    public void generateHTML(DisplayPane displayPane) throws IOException {
        String markdown=this.editor.getText();
        displayPane.getContent(this.processor.markdown(markdown));
    }


}
