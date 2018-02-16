package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich mSandwich;
        String mainName = "";
        List<String> alsoKnownAs = null;
//      Why initialize it as null, it is not null by default?
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = null;


        try {
            if (json.length() > 0) {
                JSONObject mSandwichData = new JSONObject(json);
                if (mSandwichData.has("name")) {
                    /* I think here, since every object is exist in the string.xml then there is no need
                     for that check.
                    */
                    JSONObject name = mSandwichData.getJSONObject("name");
                    mainName = name.getString("mainName");
                    JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
                    alsoKnownAs = new ArrayList<>();
                    for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                        alsoKnownAs.add(alsoKnownAsArray.getString(i));
                    }
                }
                placeOfOrigin = mSandwichData.getString("placeOfOrigin");
                description = mSandwichData.getString("description");
                image = mSandwichData.getString("image");
                JSONArray ingredientsArray = mSandwichData.getJSONArray("ingredients");
                ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(ingredientsArray.getString(i));
                }

                mSandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

                return mSandwich;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
