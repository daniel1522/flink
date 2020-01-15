package com.evolution.flinktest.adapter.filter;

import android.view.View;
import android.widget.Filter;

import com.evolution.flinktest.adapter.CharacterAdapter;
import com.evolution.flinktest.model.Character;

import java.util.ArrayList;
import java.util.List;

import static com.evolution.flinktest.MainActivity.cRecyclerView;
import static com.evolution.flinktest.MainActivity.errorImage;
import static com.evolution.flinktest.MainActivity.errorTxt;

public class CustomFilter extends Filter {

    CharacterAdapter adapter;
    List<Character> filterList;

    public CustomFilter(List<Character> filterList, CharacterAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<Character> filteredCharacters = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getName().toUpperCase().contains(constraint) ||
                        filterList.get(i).getGender().toUpperCase().contains(constraint) ||
                        filterList.get(i).getType().toUpperCase().contains(constraint) ||
                        filterList.get(i).getOrigin().toUpperCase().contains(constraint) ||
                        filterList.get(i).getLocation().toUpperCase().contains(constraint) ||
                        filterList.get(i).getStatus().toUpperCase().contains(constraint) ||
                        filterList.get(i).getSpecies().toUpperCase().contains(constraint)) {
                    filteredCharacters.add(filterList.get(i));
                }
            }
            results.count = filteredCharacters.size();
            results.values = filteredCharacters;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.characters = (ArrayList<Character>) results.values;
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() < 1) {
            cRecyclerView.setVisibility(View.GONE);
            errorImage.setVisibility(View.VISIBLE);
            errorTxt.setVisibility(View.VISIBLE);
        } else {
            cRecyclerView.setVisibility(View.VISIBLE);
            errorImage.setVisibility(View.GONE);
            errorTxt.setVisibility(View.GONE);
        }
    }
}