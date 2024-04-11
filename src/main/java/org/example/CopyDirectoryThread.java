package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

class CopyDirectoryThread extends Thread {
    private File sourceDir;
    private File destinationDir;

    public CopyDirectoryThread(File sourceDir, File destinationDir) {
        this.sourceDir = sourceDir;
        this.destinationDir = destinationDir;
    }

    @Override
    public void run() {
        try {
            copyDirectory(sourceDir.toPath(), destinationDir.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyDirectory(Path source, Path destination) throws IOException {
        Files.walk(source)
                .forEach(sourcePath -> {
                    try {
                        Path destinationPath = destination.resolve(source.relativize(sourcePath));
                        if (Files.isDirectory(sourcePath)) {
                            Files.createDirectories(destinationPath);
                        } else {
                            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
