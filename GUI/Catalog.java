package GUI;

import com.petebevin.markdown.MarkdownProcessor;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Catalog extends JScrollPane{
    private JScrollBar scrollBar;
    private MarkdownProcessor processor;
    private ArrayList<String[]> arrayList;

    public Catalog(){
        this.processor=new MarkdownProcessor();
        this.scrollBar=this.getVerticalScrollBar();
        this.arrayList=new ArrayList<>();
        this.setBounds(0,10,100,500);
    }

    public void update(String markdown) throws IOException {
        BufferedReader content=new BufferedReader(new StringReader(markdown));
        String temp="";
        int row=0;
        while((temp=content.readLine())!=null){
            temp=this.processor.markdown(temp);
            System.out.println(temp);
            System.out.println(temp.matches("<h[1-5]>.*"));
            if(temp.matches("<h[1-5]>.*")){
                System.out.println(temp);

                String[] set={temp,String.valueOf(row)};
                this.arrayList.add(set);
                row++;
            }
            else continue;

            JLabel label=new JLabel();
            label.setText(String.format("<html>%s</html>",temp));
            int finalRow = row;
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    scrollBar.setValue(finalRow);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            this.getViewport().add(label);
        }
    }
}
