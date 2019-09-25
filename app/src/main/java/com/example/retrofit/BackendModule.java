package com.example.retrofit;

import com.example.retrofit.POJO.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendModule {
	private static BackendModule sInstance;

	private final Retrofit mRetrofit;
	private final Backend mBackend;

	private State mState = State.IDLE;
	private List<User> mUsers = new ArrayList<>();

	private Listener mListener;

	public static void createInstance() {
		sInstance = new BackendModule();
	}

	public static BackendModule getInstance() {
		return sInstance;
	}

	private BackendModule() {
		mRetrofit = new Retrofit.Builder()
				.baseUrl("http://jsonplaceholder.typicode.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		mBackend = mRetrofit.create(Backend.class);
	}

	private void changeState(final State newState) {
		mState = newState;

		if (mListener != null) {
			mListener.onStateChanged(mState);
		}
	}

	public void loadPosts() {
		if (mState != State.IDLE) {
			return;
		}

		changeState(State.LOADING);

		mBackend.listPosts().enqueue(new Callback<List<User>>() {

			@Override
			public void onResponse(final Call<List<User>> call, final Response<List<User>> response) {
				mUsers = response.body();
				if (mListener != null) {
					mListener.onPostsLoaded(response.body());
				}
				changeState(State.IDLE);
			}

			@Override
			public void onFailure(final Call<List<User>> call, final Throwable t) {
				if (mListener != null) {
					mListener.onPostsLoadingFailed(t);
				}
				changeState(State.IDLE);
			}
		});
	}

	public void setListener(final Listener listener) {
		mListener = listener;
		if (mListener != null) {
			mListener.onStateChanged(mState);
		}
	}

	public List<User> getUsers() {
		return mUsers;
	}

	public enum State {
		IDLE, LOADING
	}

	public interface Listener {

		void onStateChanged(State state);

		void onPostsLoaded(List<User> users);

		void onPostsLoadingFailed(Throwable t);
	}
}
