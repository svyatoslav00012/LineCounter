package model.interfaces;

import java.io.File;
import java.util.Set;

public interface LinesCounter {

    ExtensionDetailsMap countLines(File file);

    ExtensionDetailsMap countLinesOnlyForExtensions(File file, Set<String> extensions);

    ExtensionDetailsMap countLinesIgnoreExtensions(File file, Set<String> extensionsToIgnore);

}
