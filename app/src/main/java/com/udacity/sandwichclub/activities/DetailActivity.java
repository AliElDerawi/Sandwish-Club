package com.udacity.sandwichclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.GlideApp;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @BindView(R.id.also_known_tv) TextView alsoKnowAsTv;
    @BindView(R.id.origin_tv) TextView placeOfOriginTv;
    @BindView(R.id.description_tv) TextView descriptionTv;
    @BindView(R.id.ingredients_tv) TextView ingredientTv;
    @BindView(R.id.image_iv) ImageView ingredientsIv;

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

        GlideApp.with(this)
                .load(sandwich.getImage())
                .error(R.mipmap.ic_launcher)
                 .placeholder(R.drawable.placeholder_sandwish)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void initialize(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
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
