package GUI;

import com.petebevin.markdown.MarkdownProcessor;
import com.sun.corba.se.impl.protocol.JIDLLocalCRDImpl;
import org.jsoup.Jsoup;
import sun.plugin.javascript.JSContext;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Catalog extends JScrollPane{
    private MarkdownProcessor processor;
    private ArrayList<Integer> arrayList;
    private JList list;
    private DefaultListModel model;

    public Catalog(){
        DefaultListModel model=new DefaultListModel();
        this.model=model;
        this.processor=new MarkdownProcessor();
        this.arrayList=new ArrayList<>();
        this.list=new JList(model);
        this.list.setFont(new Font("TimesRoman", Font.PLAIN,18));
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getViewport().add(this.list);
        this.setBounds(5,40,200,480);
    }

    public void update(String markdown) throws IOException {
        this.model.removeAllElements();
        this.arrayList.clear();
        BufferedReader content=new BufferedReader(new StringReader(markdown));
        String temp="";
        int row=0;
        int i=0;
        Pattern pattern=Pattern.compile("<h[1-5]>.*");
        while((temp=content.readLine())!=null) {
            temp = this.processor.markdown(temp);
            if (pattern.matcher(temp).find()) {
                temp=Jsoup.parse(temp).body().text();
                this.arrayList.add(row);
                this.model.add(i++,temp);
            }
            row++;

        }
    }

    public JList getList(){
        return this.list;
    }

    public ArrayList<Integer> getRowArr(){
        return this.arrayList;
    }
}
