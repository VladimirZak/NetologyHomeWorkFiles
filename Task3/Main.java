package Files.Task3;

import Files.Task2.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        final String zipPath = "/home/admiral/Games/savegames/save.zip"; // Необходимо указать путь к архиву
        final String pathDirSavegames = "/home/admiral/Games/savegames"; // Необходимо указать путь к директории Savegames

        openZip(zipPath, pathDirSavegames);
        GameProgress gameProgress = openProgress(pathDirSavegames + "/save2.dat");
        System.out.println(gameProgress);
    }

    public static GameProgress openProgress(String path) {
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean openZip(String pathZip, String path) {
        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip));
            ZipEntry entry;
            String fileName;
            while ((entry = zin.getNextEntry()) != null) {
                fileName = entry.getName();
                FileOutputStream fos = new FileOutputStream(fileName);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fos.write(c);
                }
                fos.flush();
                zin.closeEntry();
                fos.close();
            }
            zin.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
