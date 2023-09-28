package com.example.speaksleek;

import java.util.ArrayList;
import java.util.List;

public class InformalToFormalDataset {
    private List<String> informalSentences;
    private List<String> formalSentences;

    public InformalToFormalDataset() {
        informalSentences = new ArrayList<>();
        formalSentences = new ArrayList<>();

        // Add your dataset here
        addPair("Can't believe it!", "I find it hard to believe!");
        addPair("Gotcha!", "I understand now!");
        addPair("Can't wait to see you!", "I am eagerly anticipating our meeting.");
        addPair("OMG, this is amazing!", "Oh my goodness, this is quite impressive.");
        addPair("I wanna go there.", "I would like to visit that place.");
        addPair("Let's grab some food.", "Shall we procure some nourishment?");
        addPair("What's up?", "How are you doing today?");
        addPair("LOL, that's so funny!", "I had a hearty laugh, that's very amusing.");
        addPair("I'm gonna be late.", "I will arrive tardy.");
        addPair("Thanks a lot!", "Thank you very much for your assistance.");
        addPair("Can't believe it!", "I find it hard to believe!");
        addPair("Gotcha!", "I understand now!");
        addPair("Let's hang out later.", "Would you be available for a social gathering later?");
        addPair("Wanna grab a coffee?", "Would you like to have some coffee?");
        addPair("This is awesome!", "This is truly impressive!");
        addPair("No way!", "I find that hard to believe!");
        addPair("I'm so tired.", "I am quite fatigued.");
        addPair("You're kidding, right?", "You must be joking, correct?");
        addPair("What's the deal?", "Could you please explain the situation?");
        addPair("OMG, you're here!", "Oh my goodness, I'm delighted that you've arrived.");
        addPair("It's a piece of cake.", "It's quite simple.");
        addPair("I gotta go.", "I must depart.");
        addPair("Thanks a bunch!", "Thank you very much!");
        addPair("Let's catch up.", "We should get together and chat.");
        addPair("I'm broke.", "I have no money at the moment.");
        addPair("I'm so hungry.", "I'm feeling quite famished.");
        addPair("I'm really sorry.", "I sincerely apologize.");


        // Add more pairs as needed
    }

    public void addPair(String informal, String formal) {
        informalSentences.add(informal);
        formalSentences.add(formal);
    }

    public List<String> getInformalSentences() {
        return informalSentences;
    }

    public List<String> getFormalSentences() {
        return formalSentences;
    }

    public int size() {
        // Return the size of the dataset
        return informalSentences.size();
    }

    public String getFormalSentence(int i) {
        // Get the formal sentence at index i
        if (i >= 0 && i < formalSentences.size()) {
            return formalSentences.get(i);
        }
        return null; // Return null if the index is out of bounds
    }

    public String getInformalSentence(int i){
        if(i>=0 && i < informalSentences.size()){
            return informalSentences.get(i);
        }
        return null; // Return null if the index is out of bounds
    }
}
