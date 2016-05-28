package com.harinivaskumarrp.java.library;

import java.util.Random;

public class Joke {

    private static String[] mJokes = {
            "When you break the law - you pay Fines,\n but when you don't - you pay Taxes\n",
            "Any married man should forget his mistakes,\n there's no use in two persons remembering the same thing :P\n",
            "Isn't it great to live in the 21st century?\n Where deleting the history has become more important than making it.\n",
            "Marriage is a Workshop, where Man works & Woman shops\n",
            "During a lesson little Johnny yawns extremely wide.\n" +
                    "Teacher tries to make a Joke.\n" +
                    "Teacher : Johnny, don't swallow me.\n" +
                    "Johnny : Don't worry, teacher! I don't eat Pork.\n"
    };

    private static Random mRandom;

    private static int getRandomNumber(){
        mRandom = new Random();
        return mRandom.nextInt(mJokes.length);
    }

    public static String getJoke() {
        return mJokes[getRandomNumber()];
    }

    public static void main(String args[]){
        System.out.println("Here is the Joke - \n\n" + Joke.getJoke());
    }
}