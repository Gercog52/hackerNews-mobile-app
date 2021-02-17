package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.Inflater;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import okhttp3.OkHttpClient;

public class StoryActivity extends AppCompatActivity {
    Gson gson = new Gson();
    OkHttpClient http = new OkHttpClient();
    CommentsApi commentsApi;

    View vStory;
    ViewGroup vCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        vStory = this.findViewById(R.id.story_template_activity_story);
        vCommentList = this.findViewById(R.id.story_comments_list);

        Intent intent = getIntent();
        Story story = gson.fromJson(
            intent.getStringExtra(MainActivity.OPEN_STORY), Story.class
        );

        StoryItem.inflateStoryItem(
            vStory, story
        );

        LayoutInflater inf = getLayoutInflater();

        commentsApi = new CommentsApi(story.kids, gson, http);
        commentsApi.getNextParentComments().observeOn(AndroidSchedulers.mainThread()).subscribeWith(
        new DisposableSingleObserver<ArrayList<Comment>>() {
            @Override
            public void onSuccess(@NonNull ArrayList<Comment> comments) {
                for(Comment comment : comments) {
                    Log.v("test", comment.text);
                    View vComment = CommentItem.inflateCommentTemplate(
                        inf, vCommentList, comment
                    );

                    vCommentList.addView(vComment, vCommentList.getChildCount()-2);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
}