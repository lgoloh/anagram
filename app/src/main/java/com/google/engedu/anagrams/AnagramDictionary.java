/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet = new HashSet<String>();
    HashMap<String, ArrayList<String>> lettersToWords = new HashMap<String, ArrayList<String>>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            String sorted_word = sortletters(word);
            if (!wordSet.contains(sorted_word)) {
                wordSet.add(sorted_word);
            }
            if (lettersToWords.get(sorted_word) == null) {
                ArrayList<String> value = new ArrayList<String>();
                lettersToWords.put(sorted_word, value);
                if (sorted_word.equals(word))
                    lettersToWords.get(sorted_word).add(sorted_word);
            } else {
                lettersToWords.get(sorted_word).add(word);
            }

        }
    }

    public boolean isGoodWord(String word, String base)
    {

        if ((wordSet.contains(sortletters(word))) && (word.indexOf(base) == -1))
            return true;
        else return false;
    }


    public ArrayList<String> getAnagrams(String targetWord)
    {
        String key = sortletters(targetWord);
        return (lettersToWords.get(key));
    }


    private String sortletters(String word)
    {
        char[] result = word.toCharArray();
        Arrays.sort(result);
        return String.valueOf(result);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word)
    {
        ArrayList<String> result = new ArrayList<String>();
        String key = sortletters(word);
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0; i < alphabet.length; i++)
        {
            String inter_key = key + alphabet[i];
            String new_key = sortletters(inter_key);
            if (lettersToWords.get(new_key) != null)
            {
                ArrayList<String> new_anagrams = lettersToWords.get(new_key);
                for (int j = 0; j < new_anagrams.size(); j++)
                    result.add(new_anagrams.get(j));
            }

        }
        return result;
    }

    public String pickGoodStarterWord() {
        for (int i = 0; i < getAnagrams("opts").size(); i++)
            Log.v("my", getAnagrams("opts").get(i));
        Log.v("my", String.valueOf(isGoodWord("poster", "post")));
        Log.v("my", String.valueOf(isGoodWord("spots", "post")));
        Log.v("my", String.valueOf(isGoodWord("lamp post", "post")));
        Log.v("my", String.valueOf(isGoodWord("nonstop", "post")));
        Log.v("my", String.valueOf(isGoodWord("apostrophe", "post")));
        for (int i = 0; i < getAnagramsWithOneMoreLetter("opts").size(); i++)
            Log.v("my", getAnagramsWithOneMoreLetter("opts").get(i));

        return "post";
    }
}

