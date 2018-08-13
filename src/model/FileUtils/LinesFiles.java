package model.FileUtils;

public class LinesFiles {
    public Long lines = 0L;
    public Long files = 0L;

    public LinesFiles(){

    }

    public LinesFiles(Long lines, Long files){
        this.lines = lines;
        this.files = files;
    }

    public void appendLinesNFiles(LinesFiles moreLinesFiles){
        lines += moreLinesFiles.lines;
        files += moreLinesFiles.files;
    }
}
