package model.FileUtils;

import model.interfaces.ExtensionDetailsMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SynchronizedExtensionDetailsMap implements ExtensionDetailsMap {

    private Map<String, LinesFiles> extensionsMap = new HashMap<>();

    @Override
    public synchronized void incExtensionDetails(ExtensionDetails extensionDetails){
        LinesFiles currentLinesFiles = nullToZero(
                extensionsMap.get(extensionDetails.extension)
        );
        LinesFiles moreLinesFiles = extensionDetails.getLinesFiles();
        currentLinesFiles.appendLinesNFiles(moreLinesFiles);
        extensionsMap.put(extensionDetails.extension, currentLinesFiles);
    }

    @Override
    public LinesFiles get(String extension) {
        return extensionsMap.getOrDefault(extension, new LinesFiles());
    }

    @Override
    public Set<String> getExtensions() {
        return extensionsMap.keySet();
    }

    public String toString(){
        Set<String> allExtensions = extensionsMap.keySet();
        StringBuffer sb = new StringBuffer();

        sb.append("=== " + allExtensions.size() + " extension(s) ===\n");
        for(String extension : allExtensions) {
            LinesFiles linesFiles = extensionsMap.get(extension);
            sb.append(extension + " lines=" + linesFiles.lines + " files=" + linesFiles.files + "\n");
        }
        sb.append("==============================");

        return sb.toString();
    }

    private LinesFiles nullToZero(LinesFiles linesFiles){
        return linesFiles == null ? new LinesFiles() : linesFiles;
    }
}
