package pack;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.control.Button;
import java.util.ArrayList;

import static java.lang.reflect.Array.get;
import static java.lang.reflect.Array.getLength;
import static java.lang.reflect.Array.set;


/**
 * Class responsible for communication between View and Model.
 */
public class Controller {
    private pack.View view;


    /**
     * Constructor for Controller class
     * @param view View class
     */
    public Controller(pack.View view) {
        this.view = view;
    }

    /**
     * The action taken when the enterButton in View is clicked.
     * @param directoryText directory entered in a TextField
     */
    public void enterStartAction(TextField directoryText){
        view.setFirstWindow();


    }

    /**
     * The action taken when the addZoneButton in View is clicked.
     * @param tagName tag name entered in a TextField
     */
    public void addTagAction(TextField tagName){
        if(!tagName.getCharacters().toString().isEmpty()) {
            view.getCurrentImage().getTagManager().addTag(tagName.getCharacters().toString());

            if (tagName.getCharacters().toString().length() != 0) {
                view.setNewTag(tagName.getCharacters().toString());
            }
        }

    }

    /**
     * The action taken when an image is selected in View.
     */
    public void imageButtonAction (Button imageFileClicked){
        view.getCurrentImageFile(imageFileClicked);

        view.setImagePanel(view.getCurrentImage());


    }

    /**
     * The action taken when the moveFileButton in View is clicked.
     * @param newDirectory directory entered in a TextField
     */
    public void moveFileAction (TextField newDirectory) {
        pack.ImageFileManager.moveImageFile(view.getCurrentImage(), newDirectory.getCharacters().toString());
        view.listOfImagesMethod((newDirectory.getCharacters()).toString());
    }

    /**
     * The action taken when the revertLogsButton in View is clicked.
     */
    public void revertFileAction (TextField textField){




        if (view.getCurrentImage().getAllNames().contains(textField.getCharacters().toString())){
            ArrayList<Tag> emptyList = new ArrayList<Tag>();

            int length = view.getCurrentImage().getOriginalName().length();
            String tagPart = textField.getCharacters().toString().substring(length);
            tagPart = tagPart.trim();
            tagPart = tagPart.substring(1);
            String[] tagNames = tagPart.split("@");
            for (String tagName: tagNames){


                emptyList.add(new Tag("@" + tagName));
            }
            view.getCurrentImage().getTagManager().setTags(emptyList);
            view.getCurrentImage().getTagManager().addTagsVersion();
            view.getCurrentImage().setName();
            ImageFileManager.moveImageFile(view.getCurrentImage());

        }



        view.setRevertBackFile(view.getCurrentImage().getPath(), false);




    }

    /**
     * The action taken when the deleteZoneButton in View is clicked.
     * @param tagToBeDeleted tag name entered in Textfield
     */

    public void deleteTagAction (TextField tagToBeDeleted){
        view.getCurrentImage().getTagManager().deleteTag(tagToBeDeleted.getCharacters().toString());

        String tagNameString = tagToBeDeleted.getCharacters().toString();

        view.setRemoveTag(tagNameString);
    }

    /**
     * The action taken when the Back button in View is clicked.
     */

    public void addTagToTagDatabaseAction(TextField tagName) {
        String tagNameString = tagName.getCharacters().toString();
        if(!tagNameString.isEmpty()) {
            view.getCurrentImage().getTagManager().addTag(tagName.getCharacters().toString());
            view.getCurrentImage().getTagManager().addTagsVersion();
            pack.Tag.addTagToAllTags(tagNameString);
            view.setAddFromSelectTag(tagNameString);
        }

    }

    public  void deleteTagFromTagDatabaseAction(TextField tagName) {
        String tagNameString = tagName.getCharacters().toString();
        view.getCurrentImage().getTagManager().deleteTag(tagName.getCharacters().toString());
        view.getCurrentImage().getTagManager().addTagsVersion();
        pack.Tag.deleteTagFromAllTags(tagNameString);
        view.setRemoveFromSelectTag(tagNameString);
    }

    public void retrieveTagFromTagDatabaseAction(TextField tagName) {
        String tagNameString = tagName.getCharacters().toString();
        for (pack.Tag tag: pack.Tag.getAllTags()) {
            if (tag.getName().equals(tagName.getCharacters().toString())) {
                view.getCurrentImage().getTagManager().addTag(tagName.getCharacters().toString());
                view.getCurrentImage().getTagManager().addTagsVersion();
            }
        }
        view.setRetrieveFromSelectTag(tagNameString);
    }

    /**
     * Method called when the add button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void addTagToHZone() {
        view.setAddZoneButton();
    }

    /**
     * Method called when the MoveFile button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void moveFileToHZone() {
        view.setMoveFileButton();
    }

    /**
     * Method called when the revert/Logs button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void revertFileToHZone() {
        view.setRevertToPreviousAction();
    }
    /**
     * Method called when the delete button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void deleteTagToHZone() {
        view.setDeleteZoneButton();
    }
    /**
     * Method called when the select button is pressed in ActionZone or the first interface of editing buttons seen.
     */
    public void selectTagToHZone() {
        view.setSelectZoneButton();
    }


    /**
     * Method called when the back button is pressed in HZone
     */
    public void goBackButton() {
//        view.getCurrentImage().getTagManager().setTags(view.getCurrentImage().getTagManager().
//                getTagsVersions().lastEntry().getValue());
        view.getCurrentImage().getTagManager().addTagsVersion();
        view.setBackToAction();
        view.getCurrentImage().setName();
        ImageFileManager.moveImageFile(view.getCurrentImage());
        view.getCurrentImage().setPath();
    }

    /**
     * Method called when the back button is pressed in HZone for the select button
     */
    public void goBackSelectButton() {
        view.getCurrentImage().getTagManager().addTagsVersion();
        view.getCurrentImage().setName();
        view.setSelectRevertBackToAction();
        ImageFileManager.moveImageFile(view.getCurrentImage());
        view.getCurrentImage().setPath();
    }

    /**
     * Method called when the back button is pressed in HZone for the Revert/Logs button
     */
    public void goBackRevertButton() {
        view.setSelectRevertBackToAction();

    }
}




