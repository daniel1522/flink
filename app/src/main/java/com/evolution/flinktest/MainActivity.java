package com.evolution.flinktest;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.evolution.flinktest.adapter.CharacterAdapter;
import com.evolution.flinktest.async.CharactersAsyncTask;
import com.evolution.flinktest.async.LoadMoreAsyncTask;
import com.evolution.flinktest.model.Character;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static RecyclerView cRecyclerView;
    public static CharacterAdapter cAdapter;
    public static List<Character> characters;
    public static ImageView errorImage;
    public static TextView errorTxt;
    public static TextView noNetTxt;
    public static LinearLayoutManager layoutManager;
    public static boolean loading = true;
    public static String currentUrl = "";
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadData();
    }

    private void init() {
        cRecyclerView = findViewById(R.id.cRecyclerView);
        cRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            Log.v("...", currentUrl);
                            loadMore();
                        }
                    }
                } else {
                    loading = true;
                }
            }
        });
        errorImage = findViewById(R.id.error_image);
        errorTxt = findViewById(R.id.error_find);
        noNetTxt = findViewById(R.id.error_network);
        getSupportActionBar().setElevation(0);
    }

    private void loadData() {
        CharactersAsyncTask charactersAsyncTask = new CharactersAsyncTask(this);
        charactersAsyncTask.execute();
    }

    private void loadMore() {
        LoadMoreAsyncTask loadMoreAsyncTask = new LoadMoreAsyncTask(this);
        loadMoreAsyncTask.execute(currentUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();
        swipeRefreshLayout = findViewById(R.id.swipe);
        searchView.setQueryHint(getResources().getString(R.string.search));
        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(android.R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(android.R.color.white));
        searchEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                cAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

}
