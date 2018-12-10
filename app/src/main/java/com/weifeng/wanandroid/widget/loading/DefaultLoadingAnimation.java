package com.weifeng.wanandroid.widget.loading;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;


/**
 * ┏┛ ┻━━━━━┛ ┻┓
 * ┃　　　　　　 ┃
 * ┃　　　━　　　┃
 * ┃　┳┛　  ┗┳　┃
 * ┃　　　　　　 ┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　 ┃
 * ┗━┓　　　┏━━━┛
 * * ┃　　　┃   神兽保佑
 * * ┃　　　┃   代码无BUG！
 * * ┃　　　┗━━━━━━━━━┓
 * * ┃　　　　　　　    ┣┓
 * * ┃　　　　         ┏┛
 * * ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
 * * * ┃ ┫ ┫   ┃ ┫ ┫
 * * * ┗━┻━┛   ┗━┻━┛
 *
 * @author qigengxin
 * @since 2018-04-18 17:32
 */
public class DefaultLoadingAnimation extends BaseLoadingAnim {

    private static final int STATE_IDEL = 0;
    private static final int STATE_START = 1;
    private static final int STATE_START_PAUSED = 2;
    private static final int STATE_DISMISS_PAUSE = 3;
    private static final int STATE_DISMISS = 4;

    private ImageView bindImageView;
    private LiquidBallDrawable drawable;
    private Context context;
    private int frame;
    private float[] positions, radius;
    private LiquidBallDrawable.Ball animBall;
    private int moveDir = 1;
    private int state = STATE_IDEL;

    public DefaultLoadingAnimation(Context context) {
        this.context = context;
        // init Data
        drawable = new LiquidBallDrawable();
        drawable.setVisibilityCallback(visibilityCallback);
        drawable.setMaxAttractDistance(dpToPx(7));
        drawable.setWidth(dpToPx(100));
        drawable.setHeight(dpToPx(25));
        // 添加小球
        int ballCount = 3, distance = dpToPx(20);
        for (int i = 0; i < ballCount; ++i) {
            LiquidBallDrawable.Ball ball = new LiquidBallDrawable.Ball(
                    drawable.getIntrinsicWidth() / 2f + (i - ballCount / 2) * distance,
                    drawable.getIntrinsicHeight() / 2f, dpToPx(2f));
            ball.color = Color.rgb(253, 155, 147);
            ball.canTransform = false;
            drawable.addBall(ball);
        }
        // 动画球
        animBall = new LiquidBallDrawable.Ball();
        animBall.r = dpToPx(6);
        animBall.color = Color.rgb(254, 90, 76);
        drawable.addBall(animBall);
        positions = new float[29];
        radius = new float[29];
        float startX = drawable.getIntrinsicWidth() / 2f - ballCount / 2 * distance - dpToPx(15f);
        animBall.x = startX;
        animBall.y = drawable.getIntrinsicHeight() / 2;
        // 28
        positions[0] = startX;
        positions[1] = startX + dpToPx(1.5f);
        positions[2] = startX + dpToPx(3f);
        positions[3] = startX + dpToPx(5f);
        positions[4] = startX + dpToPx(7f);
        positions[5] = startX + dpToPx(10f);
        positions[6] = startX + dpToPx(12f);
        positions[7] = startX + dpToPx(15f);
        positions[8] = startX + dpToPx(17f);
        positions[9] = startX + dpToPx(21f);
        positions[10] = startX + dpToPx(27f);
        positions[11] = startX + dpToPx(36f);
        positions[12] = startX + dpToPx(42f);
        positions[13] = startX + dpToPx(48f);
        positions[14] = startX + dpToPx(56f);
        positions[15] = startX + dpToPx(59f);
        positions[16] = startX + dpToPx(61f);
        positions[17] = startX + dpToPx(64f);
        positions[18] = startX + dpToPx(66f);
        positions[19] = startX + dpToPx(67f);
        positions[20] = startX + dpToPx(68f);
        positions[21] = startX + dpToPx(69f);
        positions[22] = startX + dpToPx(70f);
        positions[23] = startX + dpToPx(71f);
        positions[24] = startX + dpToPx(72f);
        positions[25] = startX + dpToPx(73f);
        positions[25] = startX + dpToPx(74f);
        positions[26] = startX + dpToPx(74f);
        positions[27] = positions[26];
        positions[28] = positions[27];
        for (int i = 0; i < radius.length; ++i) {
            if (i >= 4 && i <= 19) {
                radius[i] = 1.1f * animBall.r;
            } else {
                radius[i] = animBall.r;
            }
        }
        radius[2] = 1.02f * animBall.r;
        radius[3] = 1.05f * animBall.r;
        radius[19] = 1.05f * animBall.r;
        radius[20] = 1.02f * animBall.r;
    }

    @Override
    public void start() {
        checkBind();
        switch (state) {
            case STATE_IDEL:
            case STATE_DISMISS:
            case STATE_START_PAUSED:
            case STATE_DISMISS_PAUSE:
                newState(STATE_START);
                break;
        }
    }

    @Override
    public void end() {
        checkBind();
        switch (state) {
            case STATE_START:
            case STATE_START_PAUSED:
            case STATE_DISMISS_PAUSE:
                newState(STATE_DISMISS);
                break;
        }
    }

    @Override
    public void reset() {
        checkBind();
        frame = 0;
        moveDir = 1;
        newState(STATE_IDEL);
        updatePositionByFrame();
        drawable.invalidateSelf();
    }

    @Override
    public void bind(ImageView imageView) {
        if (bindImageView != null) {
            reset();
            bindImageView.removeOnAttachStateChangeListener(listener);
            bindImageView.setImageDrawable(null);
            bindImageView.removeCallbacks(runnable);
            bindImageView = null;
        }
        if (imageView != null) {
            bindImageView = imageView;
            bindImageView.addOnAttachStateChangeListener(listener);
            bindImageView.setImageDrawable(drawable);
        }
    }

    private void checkBind() {
        if (bindImageView == null) {
            throw new UnsupportedOperationException("must bind img first");
        }
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            boolean needUpdate = false;
            switch (state) {
                case STATE_START:
                    updatePositionByFrame();
                    increaseFrame();
                    needUpdate = true;
                    break;
                case STATE_DISMISS:
                    updatePositionByFrame();
                    if (frame == 0) {
                        newState(STATE_IDEL);
                    } else {
                        increaseFrame();
                        needUpdate = true;
                    }
                    break;
            }
            if (bindImageView.isShown()) {
                drawable.invalidateSelf();
                if (needUpdate && bindImageView != null && bindImageView.getVisibility() == View.VISIBLE) {
                    ViewCompat.postOnAnimation(bindImageView, this);
                }
            } else {
                // 不可见
                pause();
            }
        }
    };

    private boolean isVisible() {
        checkBind();
        View view = bindImageView;
        do {
            if (view.getVisibility() != View.VISIBLE) {
                return false;
            }
            if (view.getParent() instanceof View) {
                view = (View) view.getParent();
            } else {
                view = null;
            }
        } while (view != null);
        return true;
    }

    private void increaseFrame() {
        frame += moveDir;
        if (frame < 0 || frame >= positions.length) {
            moveDir *= -1;
            frame += moveDir;
        }
    }

    private void updatePositionByFrame() {
        animBall.x = positions[frame];
        animBall.r = radius[frame];
    }

    private void pause() {
        switch (state) {
            case STATE_START:
                newState(STATE_START_PAUSED);
                break;
            case STATE_DISMISS:
                newState(STATE_DISMISS_PAUSE);
                break;
        }
    }

    private void resume() {
        checkBind();
        switch (state) {
            case STATE_START_PAUSED:
                newState(STATE_START);
                break;
            case STATE_DISMISS_PAUSE:
                newState(STATE_DISMISS);
                break;
        }
    }

    private View.OnAttachStateChangeListener listener = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            resume();
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            pause();
        }
    };

    private LiquidBallDrawable.VisibilityCallback visibilityCallback = new LiquidBallDrawable.VisibilityCallback() {
        @Override
        public void onVisibilityChange(boolean visible) {
            if (visible) {
                resume();
            } else {
                pause();
            }
        }
    };

    private void newState(int newState) {
        if (state == newState) {
            return;
        }
        int oldState = state;
        state = newState;
        if (oldState == STATE_IDEL && state == STATE_START) {
            notifyAnimStart();
        } else if (oldState == STATE_DISMISS && state == STATE_IDEL) {
            notifyAnimEnd();
        }
        if ((newState == STATE_START || newState == STATE_DISMISS) && bindImageView != null) {
            bindImageView.removeCallbacks(runnable);
            bindImageView.post(runnable);
        }
    }

    private int dpToPx(float dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
