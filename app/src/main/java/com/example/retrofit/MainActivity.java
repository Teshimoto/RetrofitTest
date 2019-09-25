package com.example.retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit.POJO.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int PERMISSION_CODE = 123;

    private final BackendModule mBackendModule = BackendModule.getInstance();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mPostsList;
    private PostsAdapter mAdapter;

    private final BackendModule.Listener mListener = new BackendModule.Listener() {

        @Override
        public void onStateChanged(final BackendModule.State state) {
            switch (state) {
                case IDLE:
                    mSwipeRefreshLayout.setRefreshing(false);
                    mPostsList.setVisibility(View.VISIBLE);
                    mAdapter.setUsers(mBackendModule.getUsers());
                    break;

                case LOADING:
                    mPostsList.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
            }
        }

        @Override
        public void onPostsLoaded(final List<User> users) {
            // do nothing
        }

        @Override
        public void onPostsLoadingFailed(final Throwable t) {
            Toast.makeText(MainActivity.this, "Error loading posts", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error loading posts", t);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionInternet = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET);
        if (permissionInternet == PackageManager.PERMISSION_GRANTED) {
            mBackendModule.loadPosts();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.INTERNET},
                    PERMISSION_CODE);
        }

        mSwipeRefreshLayout = findViewById(R.id.view_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mBackendModule.loadPosts();
            }
        });

        mAdapter = new PostsAdapter();
        mPostsList = findViewById(R.id.view_posts);
        mPostsList.setLayoutManager(new LinearLayoutManager(this));
        mPostsList.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mBackendModule.setListener(mListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBackendModule.setListener(null);
    }

    private class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

        private final LayoutInflater mInflater = LayoutInflater.from(MainActivity.this);
        private List<User> mUsers = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            return new ViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.titleTextView.setText(mUsers.get(position).userName);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        public void setUsers(final List<User> users) {
            mUsers = users;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView titleTextView;

            public ViewHolder(final View itemView) {
                super(itemView);

                titleTextView = itemView.findViewById(android.R.id.text1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    mBackendModule.loadPosts();
                } else {
                    throw new RuntimeException("Need internet permission");
                }
                return;
        }
    }
}
