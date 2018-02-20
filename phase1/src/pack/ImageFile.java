package pack;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Timestamp;

/**
 * Class for image files.
 */
public class ImageFile implements Serializable {
    private String name;
    private String path;
    private pack.TagManager tagManager;
    private ArrayList<String> nameLog;
    private ArrayList<String> allNames;
    private String originalName;
    private int initialDot;
    /**
     * Constructor for ImageFile.
     *
     * @param path path directory for the image
     */
    public ImageFile(String path) {
        this.path = path;
        this.tagManager = new pack.TagManager();
        int indexOfLastSlash = this.path.lastIndexOf("/");
        int indexOfPeriod = this.path.lastIndexOf(".");
        this.originalName = path.substring(indexOfLastSlash + 1, indexOfPeriod);
        this.name = path.substring(indexOfLastSlash + 1, indexOfPeriod);
        nameLog = new ArrayList<String>();
        allNames = new ArrayList<String>();
        initialDot = this.getPath().lastIndexOf(this.getImageType());
    }

    /**
     * Returns the name of this ImageFile.
     *
     * @return name of this ImageFile
     */
    public String getName() {

        return this.name;
    }

    public String getOriginalName(){return this.originalName;}


    /**
     * Returns the initialDot of this ImageFile.
     *
     */
    public int getInitialDot() {

        return initialDot;
    }

    /**
     * Returns the path directory of this ImageFile.
     *
     * @return path directory of this ImageFile
     */
    public String getPath() {

        return this.path;
    }

    /**
     * Updates the name of this ImageFile to reflect current Tags on it. Updates the log of names nameLog with the new
     * name and time stamp. Updates the ArrayList allNames with the new name.
     */
    public void setName() {

        String newName = "";
        String oldName = this.name;
        newName = this.originalName;

        if (this.getTagManager().getTagsVersions().size() != 0){


        for (pack.Tag tag : this.getTagManager().getTagsVersions().lastEntry().getValue()) {

            newName = newName + " " + tag.getName();
        }}

        this.name = newName;
        if (!(allNames.contains(this.name))){ allNames.add(this.name);}
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        nameLog.add("Old Name: " + oldName + ", " + "New Name: " + newName + ", " + "Time Stamp:" + timestamp.toString());
    }

    /**
     * Sets the name of the ImageFile to be the String parameter name.
     * @param name new name of the ImageFile
     */

    public void setName(String name){this.name = name;}



    /**
     * Returns the TagManager for this ImageFile.
     *
     * @return the TagManager for this ImageFile
     */
    public pack.TagManager getTagManager() {
        return this.tagManager;
    }

    /**
     * Returns the image type (jpg, gif, png etc) of this ImageFile.
     *
     * @return the image type of this ImageFile
     */
    public String getImageType() {
        int indexOfPeriod = this.path.lastIndexOf(".");
        return this.path.substring(indexOfPeriod + 1, this.path.length());
    }

    /**
     * Updates the path of the ImageFile to reflect its new name i.e. after setName() is called.
     */
    public void setPath() {
        int indexOfLastSlash = this.path.lastIndexOf("/");
        this.path = this.path.substring(0, indexOfLastSlash + 1) + this.name + "." + this.getImageType();


    }

    /**
     * Updates the path of the ImageFile with the input directory.
     * @param directory directory where ImageFile will be stored
     */
    public void setPathToNewDirectory(String directory){
        this.path = directory + "/" + this.name + "." + this.getImageType();;
    }

    /**
     * Returns an ArrayList of all the names the ImageFile has ever had.
     *
     * @return an ArrayList of all the names for the ImageFile
     */
    public ArrayList<String> getAllNames() {
        return allNames;
    }

    /**
     * Returns an ArrayList that includes all the names the ImageFile had and time stamps for every time the name of
     * the ImageFile changed.
     *
     * @return ArrayList of names and time stamps
     */
    public ArrayList<String> getNameLog() {
        return nameLog;
    }


}