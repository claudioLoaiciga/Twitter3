package com.example.twitter3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.twitter3.helper.MyPreferenceManager;
import com.example.twitter3.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by sonu on 17/01/18.
 */

public class UserTimelineFragment extends Fragment {

    private Context context;
    private RecyclerView userTimelineRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TweetTimelineRecyclerViewAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public UserTimelineFragment() {
    }

    public static UserTimelineFragment newInstance() {

        Bundle args = new Bundle();

        UserTimelineFragment fragment = new UserTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_timeline_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSwipeRefreshLayout(view);
        setUpRecyclerView(view);
        loadUserTimeline();
    }

    /**
     * method to set up recycler view
     *
     * @param view of the fragment
     */
    private void setUpRecyclerView(@NonNull View view) {
        userTimelineRecyclerView = view.findViewById(R.id.user_timeline_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);//it should be Vertical only
        userTimelineRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * method to load user timeline over recycler view
     */
    private void loadUserTimeline() {
        MyPreferenceManager myPreferenceManager = new MyPreferenceManager(context);

        //build UserTimeline
        UserTimeline userTimeline = new UserTimeline.Builder()
                .userId(myPreferenceManager.getUserId())//User ID of the user to show tweets for
                .screenName(myPreferenceManager.getScreenName())//screen name of the user to show tweets for
                .includeReplies(true)//Whether to include replies. Defaults to false.
                .includeRetweets(true)//Whether to include re-tweets. Defaults to true.
                .maxItemsPerRequest(50)//Max number of items to return per request
                .build();

        /*  =============   If you don't want to login the user and still you want to see the User Timeline
        then you can pass any SCREEN NAME and see the timeline. Enable the below code to do this.   ===============  */

       /* UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("sonusurender0")//any screen name
                .includeReplies(true)//Whether to include replies. Defaults to false.
                .includeRetweets(true)//Whether to include re-tweets. Defaults to true.
                .maxItemsPerRequest(50)//Max number of items to return per request
                .build();*/

        //now build adapter for recycler view
        adapter = new TweetTimelineRecyclerViewAdapter.Builder(context)
                .setTimeline(userTimeline)//set the created timeline
                //action callback to listen when user like/unlike the tweet
                .setOnActionCallback(new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        //do something on success response
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        //do something on failure response
                    }
                })
                //set tweet view style
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();

        //finally set the created adapter to recycler view
        userTimelineRecyclerView.setAdapter(adapter);
    }

    /**
     * set up swipe refresh layout
     *
     * @param view of the fragment
     */
    private void setUpSwipeRefreshLayout(View view) {

        //find the id of swipe refresh layout
        swipeRefreshLayout = view.findViewById(R.id.user_swipe_refresh_layout);

        //implement refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //return if adapter is null
                if (adapter == null)
                    return;

                //make set refreshing true
                swipeRefreshLayout.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        //on success response make refreshing false
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(context, "Tweets refreshed.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Toast or some other action
                        Toast.makeText(context, "Failed to refresh tweets.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}