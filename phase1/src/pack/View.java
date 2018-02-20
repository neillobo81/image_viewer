package pack;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.paint.Color.DARKGREY;
import java.io.FileInputStream;


public class View extends Application implements EventHandler<ActionEvent> {

    private Button enterButton;
    private Button addZoneButton = new Button("Add Tag");
    private Button moveFileButton = new Button("Move File");
    private Button revertLogsButton = new Button("Revert/Logs");
    private Button deleteZoneButton = new Button("Delete Tag");
    private Button selectZoneButton = new Button("Select Tag");
    private Button backButton;
    private Button makeNewTagButton;
    private Button deleteTagButton;
    private Button selectBackButton;
    private Button revertBackButton;
    private Button selectAddTagButton = new Button("Add");
    private Button selectRetrieveTagButton = new Button("Retrieve");
    private Button selectDeleteTagButton = new Button("Delete");
    private Button moveFileTagButton;
    private Button revertLogTagButton;
    private TextField enterText;
    private TextField newTagNameText;
    private TextField deleteTagNameText;
    private TextField selectTagNameText;
    private TextField moveFileText;
    private TextField revertLogText;
    private Stage stage;
    private Scene firstScene;
    private Scene newScene;
    private FileInputStream input;
    private Image image;
    private ImageView imageView;
    private ImageView imagePanel;
    private ArrayList<Button> buttonChoiceList;
    private ArrayList<pack.ImageFile> allImages;
    private Pane imagePanelBox = new Pane();
    private VBox listOfImages = new VBox();
    private VBox previousFileNames = new VBox();
    private VBox previousTagUnivNames = new VBox();
    private HBox tagsPanelBox = new HBox();
    private HBox actionZone = new HBox();
    private HBox ZoneHBox;
    private ScrollPane scrollableListOfImages = new ScrollPane();
    private ScrollPane scrollableListOfTags = new ScrollPane();
    private SplitPane imageAndActionPanel = new SplitPane();
    private pack.Controller controller;
    private SplitPane sidePanel;
    private Canvas imageListCanvas;
    private Canvas imageSelectListCanvas;
    private static ImageFile currentImage;


    public static void main(String[] args) {
        pack.ReadWrite.createInitialFiles();
        launch(args);
    }
    /**The window that is first made when the application is opened.
     *@param primaryStage the stage that every scene and gui component is displayed on
     */

    @Override
    public void start(Stage primaryStage) {
        controller = new pack.Controller(this);
        stage = primaryStage;
        stage.setTitle("Photoviewer");

        HBox horizontalLayout = new HBox();
        Label file_d = new Label("Please enter your file path directory:");
        enterText = new TextField();
        enterText.setPromptText("Enter Path");
        enterText.setFocusTraversable(false);
        enterText.setOnAction(evt -> controller.enterStartAction(enterText));

        enterButton = new Button("Enter");
        enterButton.setOnAction(evt -> controller.enterStartAction(enterText));


        horizontalLayout.getChildren().addAll(file_d, enterText, enterButton);
        horizontalLayout.setSpacing(10);
        horizontalLayout.setAlignment(Pos.CENTER);
        horizontalLayout.setPadding(new Insets(10, 10, 10, 10));
        HBox.setMargin(file_d, new Insets(10));
        HBox.setMargin(enterButton, new Insets(10));
        firstScene = new Scene(horizontalLayout, 700, 200);


        stage.setScene(firstScene);
        stage.setTitle("Photoviewer");
        stage.sizeToScene();
        stage.show();
    }

    /**Return the pack.ImageFile that was clicked on the list of all images displayed
     * @Button imagePressed: the button that was pressed.
     */
    public pack.ImageFile getCurrentImageFile(Button imagePressed) {
        int numIndexButton = buttonChoiceList.indexOf(imagePressed);
        currentImage = allImages.get(numIndexButton);
        return allImages.get(numIndexButton);
    }

    /*Returning the imageFile that represents the images we are working with
     */
    public pack.ImageFile getCurrentImage() {
        return currentImage;
    }

    /**Return the part of the name of the filepath that lists all the tags in the image
     */
    public  String getNewName(){
        String nameFromTags = "";
        for(int count = 1; count < tagsPanelBox.getChildren().size(); count++){
            nameFromTags += " " +((Button)tagsPanelBox.getChildren().get(count)).getText();
        }
        return nameFromTags;
    }

    /**Change the Canvas on the top right corner of the second Scene so that the user knows what is going on in the
     * application and has a clear understanding of what is needed of them.
     * @param  canvasString: The text that is displayed on the Canvas
     */
    public void changeCanvas(String canvasString) {
        imageSelectListCanvas = new Canvas(newScene.getWidth() * 0.35, newScene.getHeight() * 0.15);
        GraphicsContext graphics = imageSelectListCanvas.getGraphicsContext2D();

        graphics.strokeText(canvasString, imageSelectListCanvas.getWidth() * 0.2,
                imageSelectListCanvas.getHeight() / 2);
        graphics.setStroke(DARKGREY);
        sidePanel.getItems().remove(scrollableListOfImages);
        sidePanel.getItems().remove(imageListCanvas);
        sidePanel.getItems().addAll(imageSelectListCanvas, scrollableListOfImages);

    }


    /**
     * The GUI change when the enter button for the add/select/delete/movefile/revertLogs buttons have been selected.
     *
     * @param mode    the type of button clicked
     * @param display what has to be displayed in the inner menu
     */

    public void setActionTime(Label display, String mode) {
        for (int a = 0; a < ZoneHBox.getChildren().size(); a++) {
            ZoneHBox.getChildren().remove(a);
        }

        imageAndActionPanel.getItems().remove(actionZone);
        backButton = new Button("Back");
        backButton.setOnAction(evt -> controller.goBackButton());

        if (mode.equals("select")) {
            changeCanvas("List Of Tags Ever Created");
            selectBackButton.setOnAction(evt -> controller.goBackSelectButton());
            ArrayList<pack.Tag> allTags = pack.Tag.getAllTags();
            previousTagUnivNames = new VBox();
            for (int i = 0; i < allTags.size(); i++) {
                previousTagUnivNames.getChildren().add(new Button(allTags.get(i).getName()));
                ((Button)previousTagUnivNames.getChildren().get(i)).setPrefWidth(newScene.getWidth()*0.35);
            }
            previousTagUnivNames.setPadding(new Insets(10, 10, 10, 10));
            previousTagUnivNames.setFillWidth(true);
            previousTagUnivNames.setSpacing(5);
            previousTagUnivNames.setAlignment(Pos.CENTER);
            scrollableListOfImages.setContent(previousTagUnivNames);
            selectTagNameText.setPromptText("Tag name");
            selectTagNameText.setFocusTraversable(false);
            selectAddTagButton.setOnAction(evt -> controller.addTagToTagDatabaseAction(selectTagNameText));
            selectRetrieveTagButton.setOnAction(evt -> controller.retrieveTagFromTagDatabaseAction(selectTagNameText));
            selectDeleteTagButton.setOnAction(evt -> controller.deleteTagFromTagDatabaseAction(selectTagNameText));
            ZoneHBox.getChildren().addAll(display, selectTagNameText, selectAddTagButton, selectDeleteTagButton,
                    selectRetrieveTagButton, selectBackButton);
        } else if (mode.equals("add")) {
            makeNewTagButton = new Button("Enter");
            newTagNameText.setPromptText("Tag name");
            newTagNameText.setFocusTraversable(false);
            newTagNameText.setOnAction(evt -> controller.addTagAction(newTagNameText));
            makeNewTagButton.setOnAction(evt -> controller.addTagAction(newTagNameText));
            ZoneHBox.getChildren().addAll(display, newTagNameText, makeNewTagButton, backButton);
        } else if (mode.equals("delete")) {
            deleteTagButton = new Button("Enter");
            deleteTagNameText.setPromptText("Tag name");
            deleteTagNameText.setFocusTraversable(false);
            deleteTagNameText.setOnAction(evt -> controller.deleteTagAction(deleteTagNameText));
            deleteTagButton.setOnAction(evt -> controller.deleteTagAction(deleteTagNameText));
            ZoneHBox.getChildren().addAll(display, deleteTagNameText, deleteTagButton, backButton);
        } else if (mode.equals("move")) {
            moveFileTagButton = new Button("Enter");
            moveFileText.setPromptText("New directory");
            moveFileText.setFocusTraversable(false);
            moveFileText.setOnAction(evt -> controller.moveFileAction(moveFileText));
            moveFileTagButton.setOnAction(evt -> controller.moveFileAction(moveFileText));
            ZoneHBox.getChildren().addAll(display, moveFileText, moveFileTagButton, backButton);
        } else {
            revertBackButton = new Button("Back");
            revertBackButton.setOnAction(evt -> controller.goBackRevertButton());
            revertLogTagButton = new Button("Enter");
            revertLogText.setPromptText("Tag name");
            revertLogText.setFocusTraversable(false);
            changeCanvas("Log Of All Past Saved Files\n (Please type in exactly the Old Name found\n to revert)");
            scrollableListOfImages.setContent(previousFileNames);
            previousTagUnivNames.setSpacing(5);
            revertLogText.setOnAction(evt -> controller.revertFileAction(revertLogText));
            revertLogTagButton.setOnAction(evt -> controller.revertFileAction(revertLogText));
            ZoneHBox.getChildren().addAll(display, revertLogText, revertLogTagButton, revertBackButton);
        }

        ZoneHBox.setSpacing(10);
        ZoneHBox.setAlignment(Pos.CENTER);
        ZoneHBox.setPadding(new Insets(10, 10, 10, 10));
        imageAndActionPanel.getItems().add(ZoneHBox);
        imageAndActionPanel.setDividerPosition(0, .75);
        imageAndActionPanel.setDividerPosition(1, .80);
    }

    /**
     * The UI changed when a picture is selected from the list of images in the directory.
     * @param image the imageFile that has the path of the button selected
     */
    public void setImagePanel(ImageFile image) {
        try {
            input = new FileInputStream(image.getPath());
            imagePanel = new ImageView(new Image(input));
            imagePanelBox.getChildren().add(imagePanel);
            imagePanel.setFitWidth(newScene.getWidth() * 0.65);
            imagePanel.setFitHeight(newScene.getHeight() * 0.75);
            imagePanelBox.setPadding(new Insets(10, 10, 10, 20));
            for (int a = tagsPanelBox.getChildren().size() - 1; a > 0; a--) {
                tagsPanelBox.getChildren().remove(a);
            }
            for (Tag tag: currentImage.getTagManager()) {
                tagsPanelBox.getChildren().add(new Button(tag.getName()));
            }
            input.close();
            }
         catch (IOException fileNotFound) {
            //http://code.makery.ch/blog/javafx-dialogs-official/ used this website to uderstand and derive alert
            // code from
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            stage.setScene(firstScene);
            fileNotFound.printStackTrace();

        }
    }

    /**
     * Return to the GUI when the back button is selected in the inner submenu for the classes add/delete/select
     */
    public void setBackToAction() {
        setRevertBackFile(getNewName(), true);
        imageAndActionPanel.getItems().remove(ZoneHBox);
        imageAndActionPanel.getItems().add(actionZone);
        imageAndActionPanel.setDividerPosition(0, .75);
        imageAndActionPanel.setDividerPosition(1, .80);
        tagsPanelBox = new HBox();

    }

    /**
     * Set the GUI when the back button is selected in the inner submenu for the select button.
     */
    public void setSelectRevertBackToAction(){
        scrollableListOfImages.setContent(listOfImages);
        sidePanel.getItems().remove(scrollableListOfImages);
        sidePanel.getItems().remove(imageSelectListCanvas);
        sidePanel.getItems().addAll(imageListCanvas, scrollableListOfImages);
        setBackToAction();
    }

    /**
     * Set the GUI when the user presses the Revert/logs button.
     */
    public void setRevertToPreviousAction() {
        if(imagePanelBox.getChildren().size() >= 1){
            Label revertNameLabel = new Label("Revert Back to:");
            revertLogText = new TextField();
            backButton = new Button("Back");
            ZoneHBox = new HBox();
            for(int count = previousFileNames.getChildren().size()-1; count >= 0; count--){
                previousFileNames.getChildren().remove(previousFileNames.getChildren().get(count));
            }
            for(int count = 0; count < currentImage.getAllNames().size(); count++){
                previousFileNames.getChildren().add(new Button(currentImage.getNameLog().get(count)));
            }
            setActionTime(revertNameLabel, "revert");
        }
    }

    /**
     * Set the GUI when the Move File button is selected
     */
    public void setMoveFileButton() {
        if(imagePanelBox.getChildren().size() >= 1) {
            Label moveFileTagLabel = new Label("New directory:");
            moveFileText = new TextField();
            backButton = new Button("Back");
            ZoneHBox = new HBox();

            setActionTime(moveFileTagLabel, "move");
        }
    }

    /**
     * Set the GUI when the Select button is selected
     */
    public void setSelectZoneButton() {
        if(imagePanelBox.getChildren().size() >= 1) {
            Label selectTagsLabel = new Label("Select:");
            selectTagNameText = new TextField();
            selectBackButton = new Button("Back");
            ZoneHBox = new HBox();

            setActionTime(selectTagsLabel, "select");
        }
    }

    /**
     * Set the Gui when the Add button is selected
     */
    public void setAddZoneButton() {
        if(imagePanelBox.getChildren().size() >= 1) {
            Label addTagsLabel = new Label("Add a new Tag Name:");
            newTagNameText = new TextField();
            backButton = new Button("Back");
            ZoneHBox = new HBox();

            setActionTime(addTagsLabel, "add");
        }
    }

    /*Set the GUI when the enter button is selected in the inner submenu of the add button
     *@ param addTag: The new tag that is to be created.
     */
    public void setNewTag(String addTag) {
        Boolean alreadyThere = false;
        for (int count = 0; count < tagsPanelBox.getChildren().size(); count++) {
            if (((Button) (tagsPanelBox.getChildren().get(count))).getText().equals(addTag)) {
                alreadyThere = true;
            }
        }
        if (!alreadyThere) {
            if (addTag.charAt(0) != '@') {
                tagsPanelBox.getChildren().add(new Button("@" + addTag));
            } else {
                tagsPanelBox.getChildren().add(new Button(  addTag));
            }
        }
    }

    /**
     * Set the GUI when the Delete button is selected in the first menu
     */
    public void setDeleteZoneButton() {
        if(imagePanelBox.getChildren().size() >= 1) {
            Label deleteTagsLabel = new Label("Delete a Tag:");
            deleteTagNameText = new TextField();
            backButton = new Button("Back");
            ZoneHBox = new HBox();

            setActionTime(deleteTagsLabel, "delete");
        }
    }

    /**
     * Set the GUI to change to remove a tag button in the interface once the enter button for this is pressed in the
     * delete menu in the first submenu
     * @param removeTag: the string of the tag that has to be removed
     */
    public void setRemoveTag(String removeTag) {
        for (int i = 0; i < tagsPanelBox.getChildren().size(); i++) {
            if ((((Button) (tagsPanelBox.getChildren().get(i))).getText()).equals(removeTag)) {
                tagsPanelBox.getChildren().remove(tagsPanelBox.getChildren().get(i));
            }
        }

    }

    /**
     * Set the GUI to retrieve a tag button in the interface once the Retrieve button in the submenu of
     * the select button is pressed
     *
     * @param retrieveTag the string that wil be added to the the list of tags for the image without being added to
     *                    the universal list of tags
     */

    public void setRetrieveFromSelectTag(String retrieveTag) {
        setNewTag(retrieveTag);
    }

    /**
     * Set the GUI to remove a tag button in the interface for the universal list of tags
     *
     * @param removeUniversalTag the string that wil be removed from the universal Tag list
     */
    public void setRemoveFromSelectTag(String removeUniversalTag) {
        ArrayList<pack.Tag> allTags = pack.Tag.getAllTags();
        for (int i = 0; i < allTags.size(); i++) {
            if ((((Button) (previousTagUnivNames.getChildren().get(i))).getText()).equals(removeUniversalTag)) {
                previousTagUnivNames.getChildren().remove(previousTagUnivNames.getChildren().get(i));
                i = allTags.size() + 3;
            }
        }

    }

    /**
     * Set the GUI to add a tag button in the interface once the enter button for the select button
     *
     * @param addTag the string that wil be added from the universal Tag list
     */
    public void setAddFromSelectTag(String addTag) {
        Boolean alreadyThere= false;
        for(int count = 0; count < previousTagUnivNames.getChildren().size(); count++){
            if(((Button)previousTagUnivNames.getChildren().get(count)).getText().equals(addTag)){
                alreadyThere = true;
            }
        }
        if(!alreadyThere) {
            if (addTag.charAt(0) != '@') {
                previousTagUnivNames.getChildren().add(new Button("@" + addTag));
            }
            else{
                previousTagUnivNames.getChildren().add(new Button(addTag));
            }
            int allTagSize = previousTagUnivNames.getChildren().size();
            ((Button) previousTagUnivNames.getChildren().get(allTagSize - 1)).setPrefWidth(newScene.getWidth() * 0.35);
            setNewTag(addTag);
        }
    }

    /**
     * Sets the GUI to change to revert the file to any log made
     *
     * @param newName the string that wil be new name of the file
     * @param changeScroll whether the tags of the image should be changed or not
     */
    public void setRevertBackFile(String newName, Boolean changeScroll) {

        if(!changeScroll){
            for(int count = tagsPanelBox.getChildren().size()-1; count > 0; count--){
                tagsPanelBox.getChildren().remove(count);
            }
            for(Tag tag: currentImage.getTagManager()){
                tagsPanelBox.getChildren().add(new Button(tag.getName()));
            }

        }
        String newNameFile = "";
        for (int i = 0; i < listOfImages.getChildren().size(); i++) {
            if (i % 2 == 1 && (((Button) listOfImages.getChildren().get(i)).getText().equals(currentImage.getPath()))) {
                newNameFile = currentImage.getPath().substring(0, currentImage.getInitialDot()-1) + " " +
                        getNewName() + "." + currentImage.getImageType();
                ((Button) listOfImages.getChildren().get(i)).setText(newNameFile);
            }

        }
        scrollableListOfImages.setContent(listOfImages);

    }

    /**
     * Sets the GUI to change to move the file to any log made
     *
     * @param moveFilePath the string that represents the new directory that has to be searched
     */
    public void setMoveFileTag(String moveFilePath) {
        allImages = pack.ImageFileManager.findImages((moveFilePath));
        int numOfImages = allImages.size();
        buttonChoiceList = new ArrayList<Button>(numOfImages);

        listOfImages.setSpacing(10);
        listOfImages.setPadding(new Insets(10, 10, 10, 10));


        try {
            for (int i = 0; i < numOfImages; i++) {
                input = new FileInputStream(allImages.get(i).getPath());
                image = new Image(input);
                imageView = new ImageView(image);
                buttonChoiceList.add(new Button(allImages.get(i).getPath()));
                buttonChoiceList.get(i).setOnAction(evt -> controller.imageButtonAction((Button) (evt.getSource())));
                listOfImages.getChildren().addAll(imageView, buttonChoiceList.get(i));
                imageView.setFitWidth(newScene.getWidth() * 0.35);
                input.close();
            }
        } catch (IOException noFile) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            stage.setScene(firstScene);
        }

        scrollableListOfImages.setContent(listOfImages);

    }

    /**
     * Set the GUI that results from when the directory is entered for the first time the program is started
     */
    public void setFirstWindow() {
        // got the idea for this code from
        // https://stackoverflow.com/questions/23160573/javafx-stage-setoncloserequest-without-function
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                pack.ReadWrite.serializeImageFiles(ImageFileManager.getImageFiles());
            }
        });
        {
        }
        SplitPane parent_layout = new SplitPane();
        parent_layout.setOrientation(Orientation.HORIZONTAL);
        newScene = new Scene(parent_layout, 1000, 700);

        addZoneButton = new Button("Add Tag");
        addZoneButton.setOnAction(evt -> controller.addTagToHZone());
        deleteZoneButton.setOnAction(evt -> controller.deleteTagToHZone());
        selectZoneButton.setOnAction(evt -> controller.selectTagToHZone());
        moveFileButton.setOnAction(evt -> controller.moveFileToHZone());
        revertLogsButton.setOnAction(evt -> controller.revertFileToHZone());

        //ended of the check here

        actionZone.getChildren().addAll(addZoneButton, deleteZoneButton, selectZoneButton, moveFileButton,
                revertLogsButton);
        actionZone.setSpacing(10);
        actionZone.setAlignment(Pos.CENTER);

        //this is the tagsPanel box where everyone will see the tags on the picture
        tagsPanelBox.getChildren().add(new Button("Tags:"));
        scrollableListOfTags.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollableListOfTags.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollableListOfTags.setContent(tagsPanelBox);


        imageAndActionPanel.setOrientation(Orientation.VERTICAL);
        imageAndActionPanel.getItems().addAll(imagePanelBox, tagsPanelBox, actionZone);
        imageAndActionPanel.setDividerPosition(0, .75);
        imageAndActionPanel.setDividerPosition(1, .80);


        listOfImagesMethod((enterText.getCharacters()).toString());


        //https://www.youtube.com/watch?v=IyenGPFjBqw used this to understand how to use scrollPane

        scrollableListOfImages.setContent(listOfImages);
        sidePanel = new SplitPane();
        sidePanel.setOrientation(Orientation.VERTICAL);

        // Used https://docs.oracle.com/javafx/2/canvas/jfxpub-canvas.htm to understand how to mess with
        // GraphicsContext. Did not copy any code from here though.

        imageListCanvas = new Canvas(newScene.getWidth() * 0.35, newScene.getHeight() * 0.15);
        GraphicsContext graphics = imageListCanvas.getGraphicsContext2D();

        graphics.strokeText("List Of Images from Directory", imageListCanvas.getWidth() * 0.2,
                imageListCanvas.getHeight() / 2);
        graphics.setStroke(DARKGREY);
        sidePanel.getItems().addAll(imageListCanvas, scrollableListOfImages);


        parent_layout.getItems().addAll(imageAndActionPanel, sidePanel);
        parent_layout.setDividerPosition(0, 0.65);

        stage.setScene(newScene);
    }

    /**
     * Sets the GUI to display a list of images
     *
     * @param filePathDirectory the String that will hold the directory path that the program will serach for images in.
     */

    public void listOfImagesMethod(String filePathDirectory){
        allImages = pack.ImageFileManager.findImages(filePathDirectory);
        int numOfImages = allImages.size();
        buttonChoiceList = new ArrayList<Button>(numOfImages);

        listOfImages.setSpacing(10);
        listOfImages.setPadding(new Insets(10, 10, 10, 10));


        try {
            for (int i = 0; i < numOfImages; i++) {
                input = new FileInputStream(allImages.get(i).getPath());
                image = new Image(input);
                imageView = new ImageView(image);
                buttonChoiceList.add(new Button(allImages.get(i).getPath()));
                buttonChoiceList.get(i).setOnAction(evt -> controller.imageButtonAction((Button) (evt.getSource())));
                listOfImages.getChildren().addAll(imageView, buttonChoiceList.get(i));
                imageView.setFitWidth(newScene.getWidth() * 0.35);
                input.close();
            }
        } catch (IOException noFile) {
            //http://code.makery.ch/blog/javafx-dialogs-official/ used this website to uderstand and derive alert
            // code from
            noFile.printStackTrace();
            stage.setScene(firstScene);
        }
    }




    // Used this method to create events whenever a button is pressed
    @Override
    public void handle(ActionEvent event) {

    }
}