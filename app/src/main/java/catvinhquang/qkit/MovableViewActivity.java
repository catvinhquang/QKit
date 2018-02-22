package catvinhquang.qkit;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MovableViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView smileEmoji = new ImageView(this);
        smileEmoji.setImageResource(R.drawable.smile_emoji);
        smileEmoji.setOnTouchListener(new MovableTouch(smileEmoji));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(Utils.dpToPx(this, 50), Utils.dpToPx(this, 50));
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);

        RelativeLayout rootView = new RelativeLayout(this);
        rootView.addView(smileEmoji, lp);
        setContentView(rootView);

        Toast.makeText(this, "Let's move smile emoji!", Toast.LENGTH_SHORT).show();
    }

    class MovableTouch implements View.OnTouchListener {

        View view;
        float downX, downY;
        boolean isMoved;

        public MovableTouch(View v) {
            this.view = v;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    isMoved = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    final int TOUCH_SLOP = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
                    float deltaX = event.getX() - downX;
                    float deltaY = event.getY() - downY;
                    if (isMoved || Math.abs(deltaX) > TOUCH_SLOP || Math.abs(deltaY) > TOUCH_SLOP) {
                        float newTranslationX = view.getTranslationX() + deltaX;
                        view.setTranslationX(newTranslationX);
                        float newTranslationY = view.getTranslationY() + deltaY;
                        view.setTranslationY(newTranslationY);
                        isMoved = true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (isMoved)
                        view.animate().translationX(0).translationY(0).setDuration(300).start();
                    break;
            }

            return true;
        }

    }

}
