package com.example.hackernews;

import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.LeadingMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

public class StoryItem {
    static public View inflateStoryItem(LayoutInflater inf, ViewGroup parent, Story story) {
        View storyView = inf.inflate(R.layout.new_story_item, parent, false);
        return inflateStoryItem(storyView, story);
    }
    static public View inflateStoryItem(
            LayoutInflater inf, ViewGroup parent, Story story, View.OnClickListener handleExit
    ) {
        View storyView = inf.inflate(R.layout.new_story_item, parent, false);
        return inflateStoryItem(
            inflateStoryItem(storyView, story), handleExit
        );
    }
    @SuppressLint("SetTextI18n")
    static public View inflateStoryItem(View storyView, Story story) {
        TextView vStoryTitle      = storyView.findViewById(R.id.story_title);
        TextView vStoryLikes      = storyView.findViewById(R.id.story_likes);
        TextView vStoryComments   = storyView.findViewById(R.id.story_comments);
        TextView vStoryAgo        = storyView.findViewById(R.id.story_ago);
        TextView vStoryBy         = storyView.findViewById(R.id.story_by);
        long currentDate          = new Date().getTime();

        SpannableString storyTitle = new SpannableString(story.title);
        LeadingMarginSpan startStrMargin = new LeadingMarginSpan.Standard(50, 0);
        storyTitle.setSpan(
                startStrMargin, 0, storyTitle.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        vStoryTitle.setText(storyTitle);
        vStoryLikes.setText(story.score.toString());

        if(story.descendants != null) {
            vStoryComments.setText(story.descendants.toString());
        } else {
            vStoryComments.setText("0");
        }

        vStoryAgo.setText(" | " + story.getTimeAgo(currentDate) + " | ");
        vStoryBy.setText("by " + story.by);

        return storyView;
    }
    @SuppressLint("SetTextI18n")
    static public View inflateStoryItem(
        View storyView, View.OnClickListener handleExit
    ) {
        TextView vStoryBy         = storyView.findViewById(R.id.story_by);
        TextView vToStoryActivity = storyView.findViewById(R.id.to_story_activity);

        vStoryBy.setText(vStoryBy.getText() + " |");

        vToStoryActivity.setOnClickListener(handleExit);
        vToStoryActivity.setVisibility(View.VISIBLE);

        return storyView;
    }
}
