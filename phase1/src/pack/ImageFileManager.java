package pack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class responsible for managing ImageFile objects.
 */
public class ImageFileManager {

    private static ArrayList<pack.ImageFile> imageFiles = pack.ReadWrite.deserializeImageFiles();

    /**
     * For each image in the format jpg, jpeg, gif, png and bmp in the directory creates an ImageFile of those images,
     * adds each ImageFile to  an ArrayList<ImageFile> imagesInDirectory, updates the static ArrayList<ImageFile>
     * imageFiles with all the ImageFiles created, returns imagesInDirectory
     *
     * @param directory directory of the imageFiles
     * @return ArrayList<ImageFile> of images in the directory
     */
    public static ArrayList<pack.ImageFile> findImages(String directory) {

        File folder = new File(directory);
        File[] files = folder.listFiles();
        ArrayList<pack.ImageFile> imagesInDirectory = new ArrayList<pack.ImageFile>();

        if (files != null) {
            for (int i = 0; i < files.length; i = i + 1) {

                if (files[i].isFile()) {
                    String name = files[i].getName();
                    String path = files[i].getPath();
                    int indexOfPeriod = files[i].getName().lastIndexOf(".");
                    String type = name.substring(indexOfPeriod + 1, files[i].getName().length());
                    Boolean present = false;
                    int index = -1;
                    for(int count = 0; count < imageFiles.size(); count++){
                        if(imageFiles.get(count).getPath().equals(path)){
                            present = true;
                            index = count;
                        }
                    }
                    if (type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("jpg") ||
                            type.equalsIgnoreCase("gif") || type.equalsIgnoreCase("png") ||
                            type.equalsIgnoreCase("bmp")) {
                        pack.ImageFile image = new pack.ImageFile(path);
                        if(present){
                            imagesInDirectory.add(imageFiles.get(index));
                        }
                        else {
                            imageFiles.add(image);
                            imagesInDirectory.add(image);
                        }

                    }
                }
            }
        }
        return imagesInDirectory;
    }

    /**
     * Returns the ArrayList<ImageFile> imageFiles containing all ImageFiles ever created.
     *
     * @return the ArrayList containing all the ImageFiles
     */
    public static ArrayList<pack.ImageFile> getImageFiles() {
        return imageFiles;
    }



    /**
     * Updates the variable path of the ImageFile to reflect its name. Moves the ImageFile to the path directory as
     * defined in the variable path of the ImageFile.
     * @param imageFile an ImageFile to move
     */
    public static void moveImageFile(pack.ImageFile imageFile) {

        String oldPath = imageFile.getPath();
        imageFile.setPath();
        String newPath = imageFile.getPath();

        try {
            Files.move(Paths.get(oldPath), Paths.get(newPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeName(newPath, oldPath);

    }

    /**
     * Updates the variable path of the ImageFile with the new directory. Moves the ImageFile to the indicated
     * directory.
     * @param imageFile an ImageFile to move
     * @param directory the directory the ImageFile will move to
     */
    public static void moveImageFile(pack.ImageFile imageFile, String directory){

        String oldPath = imageFile.getPath();
        imageFile.setPathToNewDirectory(directory);
        String newPath = imageFile.getPath();

        try {
            Files.move(Paths.get(oldPath), Paths.get(newPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the File name in the OS so that the File name changes to the new file name.
     * @param newFileName the new name of the File in the OS
     * @param oldFileName the old name of the File in the OS
     */
    private static void changeName(String newFileName, String oldFileName){
        File newFile  = new File(newFileName);
        File oldFile = new File(oldFileName);
        oldFile.renameTo(newFile);

    }




}