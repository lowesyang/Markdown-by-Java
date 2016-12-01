package GUI;

import com.petebevin.markdown.MarkdownProcessor;

import javax.swing.*;
import javax.swing.text.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Catalog extends JScrollPane{
    private JTextPane editor;
    private MarkdownProcessor processor;
    private ArrayList<String[]> arrayList;

    public Catalog(Editor editPane){
        this.processor=new MarkdownProcessor();
        this.arrayList=new ArrayList<>();
        this.setBounds(0,10,100,500);
        this.editor=editPane.getEditor();
    }

    public void update() throws IOException {
        String markdown=this.editor.getText();
        BufferedReader content=new BufferedReader(new StringReader(markdown));
        String temp="";
        int row=0;
        while((temp=content.readLine())!=null){
            temp=this.processor.markdown(temp);
            if(temp.matches("<h[1-5]>")){
                String[] set={temp,String.valueOf(row)};
                this.arrayList.add(set);
                row++;
            }

            // TODO: 2016/12/2 make catalog
        }
    }
}
