package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnowAsTv, placeOfOriginTv ;
    private TextView descriptionTv , ingredientTv;
    private ImageView ingredientsIv;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void initialize(){
        alsoKnowAsTv = findViewById(R.id.also_known_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientTv = findViewById(R.id.ingredients_tv);
        ingredientsIv = findViewById(R.id.image_iv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        StringBuilder alsoKnownAsString  = new StringBuilder();
        for (int i = 0 ; i <alsoKnownAs.size() ; i++){
          alsoKnownAsString.append(alsoKnownAs.get(i));
          if (alsoKnownAs.size() > 1 && alsoKnownAs.size() != i+1)
          alsoKnownAsString.append(" , ");
        }

        alsoKnowAsTv.setText(alsoKnownAsString.toString());
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());

        List<String> ingredients = sandwich.getIngredients();
        StringBuilder ingredientsString = new StringBuilder();
        for (int i = 0 ; i < ingredients.size() ; i++){
            ingredientsString.append(ingredients.get(i));
            if (ingredients.size() >1 && ingredients.size() != i+1)
            ingredientsString.append(" , ");
        }
        ingredientTv.setText(ingredientsString.toString());
    }
}
