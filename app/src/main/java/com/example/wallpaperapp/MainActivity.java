package com.example.wallpaperapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.wallpaperapp.adapter.ImageAdapter;
import com.example.wallpaperapp.databinding.ActivityMainBinding;
import com.example.wallpaperapp.model.Api.ApiClient;
import com.example.wallpaperapp.model.Api.ApiInterface;
import com.example.wallpaperapp.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ImageAdapter adapter;
    private ArrayList<ImageModel> list;
    private GridLayoutManager manager;
    private int page = 1;
    private ProgressDialog dialog;
    private int pageSize = 30;
    private boolean isLoading;
    private boolean isLastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        list = new ArrayList<>();
        adapter = new ImageAdapter(list);
        manager = new GridLayoutManager(this, 3);

        binding.imageRecycleView.setLayoutManager(manager);
        binding.imageRecycleView.setHasFixedSize(true);
        binding.imageRecycleView.setAdapter(adapter);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();
        getData();
        binding.imageRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem = manager.getChildCount();
                int totalItem = manager.getItemCount();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItem + firstVisibleItemPosition >= totalItem)
                            && firstVisibleItemPosition >= 0 &&
                            totalItem >= pageSize) {
                        page++;
                        dialog.show();
                        getData();
                    }
                }
            }
        });

    }

    private void getData() {
        isLoading = true;

        ApiClient.getApiInterface().getImages(page, 30)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        if (response.body() != null) {
                            list.addAll(response.body());
                            adapter.notifyDataSetChanged();


                        }
                        isLoading = false;
                        dialog.dismiss();
                        if (list.size() > 0) {
                            isLastPage = list.size() < pageSize;
                        } else {
                            isLastPage = true;
                        }
                    }


                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT);
                        dialog.dismiss();
                    }
                });
    }
}