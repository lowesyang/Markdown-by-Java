package GUI;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class LowesFileFilter extends FileFilter{
    private String ends;        //suffix of file name
    private String description;        //description of files

    public LowesFileFilter(String ends,String description){
        this.ends=ends;
        this.description=description;
    }

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()) return true;
        String fileName=f.getName();
        return fileName.toLowerCase().endsWith(this.ends);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public String getEnds(){
        return this.ends;
    }
}
