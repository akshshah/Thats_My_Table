package com.example.logintest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class ShowAllCategoriesDialog extends DialogFragment {
    private static final String TAG = "ShowAllCategoriesDialog";
    private SelectCuisine selectCuisine;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_show_all_categories,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("All Cuisines");

        ListView listView = view.findViewById(R.id.categoriesListView);
        Utils utils = new Utils(getActivity());
        final ArrayList<String> cuisines = utils.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,cuisines);

        listView.setAdapter(adapter);

        try{
            selectCuisine = (SelectCuisine) getActivity();
        }catch (Exception e){
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectCuisine.onSelectCuisine(cuisines.get(position));
            }
        });

        return builder.create();
    }
    public interface SelectCuisine{
        void onSelectCuisine(String cuisine);
    }
}
