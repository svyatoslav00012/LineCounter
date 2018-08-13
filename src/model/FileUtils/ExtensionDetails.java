package model.FileUtils;

public class ExtensionDetails {
    public String extension;
    public Long lines = 0L;
    public Long files = 0L;

    public LinesFiles getLinesFiles(){
        return new LinesFiles(lines, files);
    }
}
