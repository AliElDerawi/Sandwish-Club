package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.sandwichclub.utils.Contracts.JSON_ORIGIN_OF_SANDWICH;
import static com.udacity.sandwichclub.utils.Contracts.JSON_SANDWICH_ALTERNATE_NAME;
import static com.udacity.sandwichclub.utils.Contracts.JSON_SANDWICH_DESCRIPTION;
import static com.udacity.sandwichclub.utils.Contracts.JSON_SANDWICH_IMAGE;
import static com.udacity.sandwichclub.utils.Contracts.JSON_SANDWICH_INGREDIENTS;
import static com.udacity.sandwichclub.utils.Contracts.JSON_SANDWICH_NAME;
import static com.udacity.sandwichclub.utils.Contracts.JSON_TABLE_NAME;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich mSandwich;
        String mainName = "";
        List<String> alsoKnownAs = null;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = null;


        try {
            if (json.length() > 0) {
                JSONObject mSandwichData = new JSONObject(json);
                if (mSandwichData.has(JSON_TABLE_NAME)) {
                    /* I think here, since every object is exist in the string.xml then there is no need
                     for that check.
                    */
                    JSONObject name = mSandwichData.optJSONObject(JSON_TABLE_NAME);
                    mainName = name.optString(JSON_SANDWICH_NAME);
                    JSONArray alsoKnownAsArray = name.optJSONArray(JSON_SANDWICH_ALTERNATE_NAME);
                    alsoKnownAs = new ArrayList<>();
                    for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                        alsoKnownAs.add(alsoKnownAsArray.getString(i));
                    }
                }
                placeOfOrigin = mSandwichData.optString(JSON_ORIGIN_OF_SANDWICH);
                description = mSandwichData.optString(JSON_SANDWICH_DESCRIPTION);
                image = mSandwichData.optString(JSON_SANDWICH_IMAGE);
                JSONArray ingredientsArray = mSandwichData.optJSONArray(JSON_SANDWICH_INGREDIENTS);
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
