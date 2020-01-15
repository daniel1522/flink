package com.evolution.flinktest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.evolution.flinktest.DetailActivity;
import com.evolution.flinktest.R;
import com.evolution.flinktest.adapter.filter.CustomFilter;
import com.evolution.flinktest.model.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> implements Filterable {

    public List<Character> characters;
    private Context mContext;
    private CustomFilter filter;

    public CharacterAdapter(Context context, List<Character> characters) {
        this.mContext = context;
        this.characters = characters;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        CharacterViewHolder characterViewHolder = new CharacterViewHolder(view);
        return characterViewHolder;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder characterViewHolder, int i) {
        characterViewHolder.bindMovement(characters.get(i));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(characters, this);
        }
        return filter;
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        private ImageView cImage;
        private TextView cName;
        private TextView cStatus;
        private TextView cSpecie;
        private Context context;

        public CharacterViewHolder(final View itemView) {
            super(itemView);
            cImage = (ImageView) itemView.findViewById(R.id.c_image);
            cName = (TextView) itemView.findViewById(R.id.c_name);
            cStatus = (TextView) itemView.findViewById(R.id.c_status);
            cSpecie = (TextView) itemView.findViewById(R.id.c_specie);
            context = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("name", characters.get(adapterPosition).getName());
                    intent.putExtra("status", characters.get(adapterPosition).getStatus());
                    intent.putExtra("species", characters.get(adapterPosition).getSpecies());
                    intent.putExtra("type", characters.get(adapterPosition).getType());
                    intent.putExtra("gender", characters.get(adapterPosition).getGender());
                    intent.putExtra("origin", characters.get(adapterPosition).getOrigin());
                    intent.putExtra("location", characters.get(adapterPosition).getLocation());
                    intent.putExtra("created", characters.get(adapterPosition).getCreated());
                    intent.putExtra("image", characters.get(adapterPosition).getImage());
                    context.startActivity(intent);
                }
            });
        }

        public void bindMovement(Character character) {
            cName.setText(character.getName());
            cStatus.setText(character.getStatus());
            cSpecie.setText(character.getSpecies());
            Picasso.get().load(character.getImage()).into(cImage);
        }
    }
}