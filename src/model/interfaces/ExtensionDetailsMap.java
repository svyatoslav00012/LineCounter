package model.interfaces;

import model.FileUtils.ExtensionDetails;
import model.FileUtils.LinesFiles;

import java.util.Set;

public interface ExtensionDetailsMap {

    void incExtensionDetails(ExtensionDetails extensionDetails);

    LinesFiles get(String extension);

    Set<String> getExtensions();
}
