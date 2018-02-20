package pack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Class responsible for managing Tag objects.
 */
public class TagManager implements Iterable<pack.Tag>, Serializable {

    /*
    Stores the current Tag objects in this TagManager.
     */
    private ArrayList<pack.Tag> tags;

    /*
    Stores the sets of Tag objects in this TagManager's tag history where
    the key is an integer representing the order each set of Tag objects
    was added in and the value is an arraylist of the set of Tag
    objects at that number.
     */
    private TreeMap<Integer, ArrayList<pack.Tag>> tagsVersions;

    /*
    Stores the value of the key in tagsVersions.
     */
    private int keyValue;

    /**
     * Constructs a new TagManager object.
     */
    public TagManager() {
        tags = new ArrayList<>();
        tagsVersions = new TreeMap<>();
        keyValue = 0;
    }

    /**
     * Adds the Tag with the specified tagName to this TagManager and to the
     * database of all Tag objects.
     *
     * @param tagName the name of the Tag
     */
    public void addTag(String tagName) {
        if (tagName.length() > 0) {
            boolean flag = true;

            for (pack.Tag tag : tags) {
                if (tag.getName().equals("@" + tagName)) {
                    flag = false;
                }
            }

            if (flag) {
                tags.add(new pack.Tag("@" + tagName));
            }

            pack.Tag.addTagToAllTags(tagName);
        }
    }

    /**
     * Deletes the Tag with the specified tagName from this TagManager.
     *
     * @param tagName the name of the Tag
     */
    public void deleteTag(String tagName) {
        ArrayList<pack.Tag> tagsCopy = new ArrayList<>();

        for (pack.Tag tag : tags) {
            if (!tag.getName().equals("@" + tagName)) {
                tagsCopy.add(tag.clone());
            }
        }

        tags = tagsCopy;
    }

    /**
     * Saves the current set of Tag objects into this TagManager's tag history.
     */
    public void addTagsVersion() {
        boolean same = true;

        if (keyValue > 0 && tagsVersions.get(keyValue - 1).size() == tags.size()) {
            for (int i = 0; i < tags.size(); i++) {
                if (!tagsVersions.get(keyValue - 1).get(i).getName().equals(tags.get(i).getName())) {
                    same = false;
                }
            }
        }
        else {
            same = false;
        }

        if ((tagsVersions.size() == 0 && tags.size() != 0) || (keyValue > 0 && !same)) {
            ArrayList<pack.Tag> temp = new ArrayList<>();

            for (pack.Tag tag : tags) {
                temp.add(tag.clone());
            }

            tagsVersions.put(keyValue, temp);
            keyValue += 1;
        }
    }

    /**
     * Sets this TagManager's current Tag objects to the Tag objects in tagArrayList.
     *
     * @param tagArrayList the Tag objects to set this TagManager to
     */
    public void setTags(ArrayList<pack.Tag> tagArrayList){
        ArrayList<pack.Tag> newTags = new ArrayList<pack.Tag>();

        for (pack.Tag tag: tagArrayList) {
            newTags.add(tag.clone());
        }

        this.tags = newTags;
    }

    /**
     * Returns the sets of Tag objects in this TagManager's tag history.
     *
     * @return the sets of Tag objects in this TagManager's tag history
     */
    public TreeMap<Integer, ArrayList<pack.Tag>> getTagsVersions() {
        TreeMap<Integer, ArrayList<pack.Tag>> returnTreeMap = new TreeMap<>();

        for (Integer i: tagsVersions.keySet()) {
            ArrayList<pack.Tag> tagsVersionsArrayList = tagsVersions.get(i);
            ArrayList<pack.Tag> temp = new ArrayList<>();

            for (pack.Tag tag : tagsVersionsArrayList) {
                temp.add(tag.clone());
            }

            returnTreeMap.put(i, temp);
        }

        return returnTreeMap;
    }

    /**
     * Returns an iterator of this TagManager.
     *
     * @return an iterator of this TagManager
     */
    @Override
    public Iterator<pack.Tag> iterator() {
        return new TagManagerIterator();
    }

    /**
     * Class responsible for iterating over Tag objects stored in
     * this TagManager.
     */
    private class TagManagerIterator implements Iterator<pack.Tag> {

        /*
        Stores the current index of this TagManagerIterator.
         */
        private int index = 0;

        /**
         * Returns true iff this TagManagerIterator has an object
         * at its current index.
         *
         * @return whether or not this TagManager has an object at
         * its current index
         */
        @Override
        public boolean hasNext() {
            return index < tags.size();
        }

        /**
         * Returns the next Tag in this TagManagerIterator.
         *
         * @return the next Tag in this TagManagerIterator
         */
        @Override
        public pack.Tag next() {
            pack.Tag nextTag = tags.get(index).clone();
            index++;

            return nextTag;
        }
    }
}
