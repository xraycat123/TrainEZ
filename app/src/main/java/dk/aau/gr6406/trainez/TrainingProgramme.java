package dk.aau.gr6406.trainez;

/*
  Created by marti on 5/6/2017.
 */


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Created by marti on 4/30/2017.
 */
public class TrainingProgramme {


    private static final String TAG = "trainez";
    private Context context;


    // shared pref needs a context to work in an non activity class
    public TrainingProgramme(Context activityContext) {
        this.context = activityContext;
    }

    public ArrayList<Exercise> getTrainingProgramme() {

        // Make an arraylist. This arraylist is the training program.
        // The listed item has to be sorted by cateogry!
        ArrayList<Exercise> programme = new ArrayList<>();
        programme.add(new Exercise("Tongue stretch", "dummy", 0, "Mouth cleaning", 1));
        programme.add(new Exercise("Tongue in cheek", "dummytwo", 0, "Mouth cleaning", 2));
        programme.add(new Exercise("Tongue in corners", "dummythree", 0, "Mouth cleaning", 3));
        programme.add(new Exercise("Tongue on spoon", "dummyfour", 0, "Speaking and saliva", 4));
        programme.add(new Exercise("Side of tongue on spoon", "dummyfive", 0, "Speaking and saliva", 5));
        programme.add(new Exercise("Yawning", "dummysix", 0, "Relaxing the neck", 6));
        programme.add(new Exercise("Jaw to the side", "dummyseven", 0, "Jaw movement", 7));
        programme.add(new Exercise("Jaw forward", "dummyeight", 0, "Jaw movement", 8));
        programme.add(new Exercise("Breathing", "dummynine", 0, "Swallowing", 9));
        programme.add(new Exercise("Chair lifting", "dummyten", 0, "Swallowing", 10));
        programme.add(new Exercise("Head turning", "dummyelleven", 0, "Swallowing", 11));
        programme.add(new Exercise("Swallowing normally", "dummytwelf", 0, "Swallowing", 12));
        programme.add(new Exercise("Tongue between teeth", "dummythirteen", 0, "Swallowing", 13));
        programme.add(new Exercise("Forceful swallow", "dummyfourteen", 0, "Efficient swallowing", 14));
        programme.add(new Exercise("Lay on back", "dummyfifteen", 0, "Efficient swallowing", 15));



        updateRepetitions(programme);

        return programme;
    }

    public LinkedHashMap<String, Exercise> playProgrammeExercises(){
        LinkedHashMap lhm = new LinkedHashMap();

        int sharedPEntry = 1;

        for (Exercise exercise : getTrainingProgramme()) {
            if(exercise.getRepetitions()!=0){
                lhm.put("e" + sharedPEntry , exercise);
            }
            sharedPEntry++;
        }

        return lhm;
    }





    /**
     * Get an array with the name of the categories.
     *
     * @return
     */
    public String[] getCategoriesArray() {
        String[] categories = new String[getCategories().size()];


        return getCategories().toArray(categories);

    }

    /**
     * Get an ArrayList with the categories.
     *
     * @return
     */

    public ArrayList<String> getCategories() {
        ArrayList<String> uniqueCategories = new ArrayList<>();
        for (Exercise item : getTrainingProgramme()) {
            if (!uniqueCategories.contains(item.getCategory())) {
                uniqueCategories.add(item.getCategory());
            }
        }
        return uniqueCategories;
    }


    /**
     * This method returns an array with the occurence of each category in the training programme.
     *
     * @return
     */
    public ArrayList<Integer> categoryOccurence() {
        ArrayList<Integer> categoryOccurence = new ArrayList<>();
        ArrayList<String> nonUniqueCategories = new ArrayList<>();
        for (Exercise n : getTrainingProgramme()) {
            nonUniqueCategories.add(n.getCategory());
        }
        int counter = 0;
        int occurrences = 0;
        for (Exercise m : getTrainingProgramme()) {
            if (counter == occurrences) {
                occurrences = Collections.frequency(nonUniqueCategories, m.getCategory()) + occurrences;
                categoryOccurence.add(Collections.frequency(nonUniqueCategories, m.getCategory()));
            }
            counter++;
        }
        return categoryOccurence;
    }


    /**
     * This method creates an 2d array with  Exercise objects. Row = category, column = exercise
     * in the category.
     *
     * @return
     */
    public Exercise[][] getGroupedExercises() {
        Exercise[][] groupedExercises = new Exercise[getCategories().size()][];
        int excInProgramme = 0;
        int category = 0;
        for (Integer excInCategory : categoryOccurence()) {
            ArrayList<Exercise> row = new ArrayList<>();
            for (int i = excInProgramme; i < excInCategory + excInProgramme; i++) {
                row.add(getTrainingProgramme().get(i));
            }
            groupedExercises[category] = row.toArray(new Exercise[row.size()]);
            excInProgramme = excInCategory + excInProgramme;
            category++;
        }
        return groupedExercises;
    }

    /**
     * This method updates the exercises in getProgramme.
     *
     * @param programme
     */
    private void updateRepetitions(ArrayList<Exercise> programme) {
        int k = 1;
        SharedPreferences sharedPref = context.getSharedPreferences("RepetitionInfo", Context.MODE_PRIVATE);
        for (Exercise exercise : programme) {
            exercise.setRepetitions(sharedPref.getInt("e" + k, 0));
            k++;
        }
    }


}

