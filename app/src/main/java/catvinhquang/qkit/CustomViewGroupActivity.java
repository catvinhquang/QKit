package catvinhquang.qkit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class CustomViewGroupActivity extends Activity {

    static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        setContentView(new ParentView(this));
    }

    class ParentView extends RelativeLayout {

        ScrollView verticalScrollView;
        HorizontalScrollView horizontalScrollView;

        public ParentView(Context context) {
            super(context);

            horizontalScrollView = new HorizontalScrollView(getContext());
            horizontalScrollView.setBackgroundColor(0xffffffff);
            LayoutParams lp1 = new LayoutParams(screenWidth, screenHeight / 4);
            lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            addView(horizontalScrollView, lp1);

            DrawView horizontalContent = new DrawView(getContext(), true);
            horizontalScrollView.addView(horizontalContent, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

            verticalScrollView = new ScrollView(getContext());
            verticalScrollView.setBackgroundColor(0xffffffff);
            LayoutParams lp2 = new LayoutParams(screenWidth, screenHeight / 4);
            lp2.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(verticalScrollView, lp2);

            DrawView verticalContent = new DrawView(getContext(), false);
            verticalScrollView.addView(verticalContent, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            // draw parent's content first
            canvas.drawColor(0xff373F51);

            paint.setColor(0xffEEC643);
            paint.setTextSize(Utils.dpToPx(getContext(), 20));
            String title = "Hello! I am a ViewGroup :)";
            canvas.drawText(title, screenWidth / 2f - paint.measureText(title) / 2f, 150, paint);

            // draw children's content
            super.dispatchDraw(canvas);

            // draw parent's content last
            paint.setColor(0x80000000);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            paint.setColor(0xffffffff);
            String text = "[TOPMOST CONTENT]";
            canvas.drawText(text, screenWidth / 2f - paint.measureText(text) / 2f, screenHeight / 4, paint);
        }

    }

    class DrawView extends View {

        static final int maxSize = 5000;
        static final String horizontalContent = "Hello! Scroll to right, please!";
        static final String verticalContent = "Hello! Scroll to down, please!";

        boolean horizontalOrVertical;

        public DrawView(Context context, boolean horizontalOrVertical) {
            super(context);
            this.horizontalOrVertical = horizontalOrVertical;
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            paint.setColor(Color.RED);
            if (horizontalOrVertical)
                canvas.drawText(horizontalContent, screenWidth / 2 - paint.measureText(horizontalContent) / 2, screenHeight / 8, paint);
            else
                canvas.drawText(verticalContent, screenWidth / 2 - paint.measureText(verticalContent) / 2, screenHeight / 8, paint);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (horizontalOrVertical)
                setMeasuredDimension(maxSize, MeasureSpec.getSize(heightMeasureSpec));
            else
                setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), maxSize);
        }

    }

}
