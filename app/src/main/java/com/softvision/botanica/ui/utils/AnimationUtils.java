package com.softvision.botanica.ui.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.softvision.botanica.R;

import java.util.ArrayList;
import java.util.List;

public class AnimationUtils {

    public static AnimationCluster create() {
        return new AnimationCluster();
    }

    /**
     * use this method to create a runnable that will set a view to GONE
     * - for chaining purposes
     * @param view the view to set to GONE
     * @return the runnable created
     */
    public static Runnable setGone(final View view) {
        return new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        };
    }

    /**
     * use this method to create a runnable that will set a view to VISIBLE
     * - for chaining purposes
     * @param view the view to set to VISIBLE
     * @return the runnable created
     */
    public static Runnable setVisible(final View view) {
        return new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        };
    }

    /**
     * Try all manner of things to get a view height including making the view briefly VISIBLE and
     * force measuring it
     * @param view the view to be measured
     * @return
     */
    private static int forceGetViewHeight(View view) {
        Integer height = (Integer) view.getTag(R.id.original_view_height_tag_key);

        if(null == height) {
            height = view.getHeight();
        }

        if(height == 0) {
            //retain view visibility state
            int oldVisibility = view.getVisibility();

            //make view visible and force it to measure itself
            view.setVisibility(View.VISIBLE);
            view.measure(0, 0);
            //get the measured height
            height = view.getMeasuredHeight();

            //put the original visibility back
            view.setVisibility(oldVisibility);
        }

        return height;
    }

    /**
     * Set the layout height of a view to a certain value
     * - this method will store the original height in a tag to make it available
     * in the future if the view is to be set back to its original height
     * @param view the view to be changed
     * @param height the height to be set on the view
     */
    private static void forceSetViewHeight(View view, int height) {
        if(null == view.getTag(R.id.original_view_height_tag_key)) {
            view.setTag(R.id.original_view_height_tag_key, forceGetViewHeight(view));
        }
        view.getLayoutParams().height = height;
        view.requestLayout();
    }

    /**
     * Use this class to chain animation actions
     */
    public static class AnimationCluster implements Animator.AnimatorListener {
        private AnimatorSet animatorSet = new AnimatorSet();
        private AnimatorSet.Builder builder;
        private List<Runnable> beforeRunnables = new ArrayList<>();
        private List<Runnable> afterRunnables = new ArrayList<>();

        public AnimationCluster() {
            animatorSet.addListener(this);
        }

        /**
         * Add an animator to be blayed in this cluster
         * @param animator the animator to be played
         * @return this cluster
         */
        private AnimationCluster play(Animator animator) {
            if(builder == null) {
                builder = animatorSet.play(animator);
            } else {
                builder.with(animator);
            }

            return this;
        }

        /**
         * Roll the view up from the current height to 0
         * @param view the target view
         * @param duration the animation duration
         */
        public AnimationCluster blindUp(View view, long duration) {
            int height = forceGetViewHeight(view);
            ValueAnimator animator = ValueAnimator.ofInt(height, 0).setDuration(duration);
            animator.addUpdateListener(new LayoutHeightUpdater(view));
            play(animator);

            return this;
        }

        /**
         * Roll the view up from the current height to 0
         * @param view the target view
         * @param duration the animation duration
         */
        public AnimationCluster blindDown(View view, long duration) {
            int height = forceGetViewHeight(view);
            ValueAnimator animator = ValueAnimator.ofInt(0, height).setDuration(duration);
            animator.addUpdateListener(new LayoutHeightUpdater(view));
            play(animator);

            return this;
        }

        /**
         * grow the view in from scale 0.1 to scale 1
         * @param view the target text view
         * @param duration the animation duration
         * @return this cluster
         */
        public AnimationCluster grow(View view, long duration) {
            play(ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 1f).setDuration(duration))
            .play(ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 1f).setDuration(duration));
            return this;
        }

        /**
         * shrink the view out from scale 1 to scale 0.1
         * @param view the target text view
         * @param duration the animation duration
         */
        public AnimationCluster shrink(View view, long duration) {
            play(ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f).setDuration(duration))
            .play(ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f).setDuration(duration));
            return this;
        }

        /**
         * fade the view in from alpha 0 to alpha 1
         * @param view the target text view
         * @param duration the animation duration
         * @return this cluster
         */
        public AnimationCluster fadeIn(View view, long duration) {
            play(ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(duration));
            return this;
        }

        /**
         * fade the view out from alpha 1 to alpha 0
         * @param view the target text view
         * @param duration the animation duration
         */
        public AnimationCluster fadeOut(View view, long duration) {
            play(ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(duration));
            return this;
        }

        /**
         * make the text size grow from 0 to current text size
         * @param textView the target text view
         * @param duration the animation duration
         */
        public AnimationCluster textGrow(TextView textView, long duration) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, textView.getTextSize()).setDuration(duration);
            animator.addUpdateListener(new TextSizeUpdater(textView));
            play(animator);
            return this;
        }

        /**
         * make the text size shrink from the current text size to 0
         * @param textView the target text view
         * @param duration the animation duration
         */
        public AnimationCluster textShrink(TextView textView, long duration) {
            ValueAnimator animator = ValueAnimator.ofFloat(textView.getTextSize(), 0).setDuration(duration);
            animator.addUpdateListener(new TextSizeUpdater(textView));
            play(animator);
            return this;
        }

        public AnimationCluster rotateX(View view, long duration, float fromDegrees, float toDegrees) {
            return play(ObjectAnimator.ofFloat(view, "rotationX", fromDegrees, toDegrees).setDuration(duration));
        }

        public AnimationCluster rotateY(View view, long duration, float fromDegrees, float toDegrees) {
            return play(ObjectAnimator.ofFloat(view, "rotationY", fromDegrees, toDegrees).setDuration(duration));
        }

        /**
         * Add a runnable to be executed before the animation starts
         * @param runnable the runnable to be executed
         * @return this cluster
         */
        public AnimationCluster before(Runnable runnable) {
            if(!beforeRunnables.contains(runnable)) {
                beforeRunnables.add(runnable);
            }
            return this;
        }

        /**
         * Add a runnable to be executed after the animation ends
         * @param runnable the runnable to be executed
         * @return this cluster
         */
        public AnimationCluster after(Runnable runnable) {
            if(!afterRunnables.contains(runnable)) {
                afterRunnables.add(runnable);
            }
            return this;
        }

        public void start() {
            animatorSet.start();
        }

        @Override
        public void onAnimationStart(Animator animation) {
            for (Runnable beforeRunnable : beforeRunnables) {
                beforeRunnable.run();
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            for (Runnable afterRunnable : afterRunnables) {
                afterRunnable.run();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            //NOP
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            //NOP
        }
    }

    /**
     * this will update the height of a view during animation
     */
    private static class LayoutHeightUpdater implements ValueAnimator.AnimatorUpdateListener {
        private View target;

        private LayoutHeightUpdater(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            forceSetViewHeight(target, (Integer) animation.getAnimatedValue());
        }
    }

    /**
     * this will update the text size of a text view during animation
     */
    private static class TextSizeUpdater implements ValueAnimator.AnimatorUpdateListener {
        private final TextView textView;

        public TextSizeUpdater(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (Float) animation.getAnimatedValue());
        }
    }
}
