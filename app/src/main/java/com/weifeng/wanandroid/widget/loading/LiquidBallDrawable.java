package com.weifeng.wanandroid.widget.loading;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
 * 模仿液珠行为的 drawable
 *
 * @author qigengxin
 * @since 2018-04-18 14:04
 */
public class LiquidBallDrawable extends Drawable{

    public interface VisibilityCallback {
        /**
         * Called when the drawable's visibility changes.
         *
         * @param visible whether or not the drawable is visible
         */
        void onVisibilityChange(boolean visible);
    }

    public static class Ball {
        // U圆心坐标
        public float x, y;
        // 半径
        public float r;
        // 颜色
        public int color;
        // 是否可以吸引变形
        public boolean canTransform = true;
        // 最后绘制时候的颜色
        int finalColor;

        public Ball() {
        }

        public Ball(float x, float y, float r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }

    private List<Ball> ballList = new ArrayList<>();
    private Paint ballPaint = new Paint();
    private int defaultColor;
    // 吸引出来的小球最小比例
    private float attractBallRatio = 0.5f;
    // 开始链接的最大距离 in px
    private float maxAttractDistance = 100;
    // 控制点相对于两个圆切线的焦点的伸缩比例（1 的时候相当于二次贝塞尔曲线）
    private float lenRatio = 0.5f;
    // 吸引时候的角度关于切线角度的增量（弧度制，0 的时候 贝塞尔曲线就是两个圆的切线）
    private float attractAngel = 0.5f;
    // 链接时候的角度关于切线角度的增量（弧度制，0 的时候 贝塞尔曲线就是两个圆的切线）
    private float contactAngel = 0.3f;
    // 吸引状态下相对于与半径的移动比例
    private float maxOverMoveRatio = 0.5f;
    // 默认宽高
    private int width = -1;
    private int height = -1;
    // visibility callback
    private VisibilityCallback visibilityCallback;

    public LiquidBallDrawable() {
        ballPaint.setColor(Color.WHITE);
        ballPaint.setAntiAlias(true);
    }

    public int getBallCount() {
        return ballList.size();
    }

    public Ball getBall(int position) {
        return ballList.get(position);
    }

    public void addBall(Ball ball) {
        ballList.add(ball);
        invalidateSelf();
    }

    public void removeBall(Ball ball) {
        ballList.remove(ball);
        invalidateSelf();
    }

    public void setVisibilityCallback(VisibilityCallback visibilityCallback) {
        this.visibilityCallback = visibilityCallback;
    }

    // getter and setter
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public float getAttractBallRatio() {
        return attractBallRatio;
    }

    public void setAttractBallRatio(float attractBallRatio) {
        this.attractBallRatio = attractBallRatio;
    }

    public float getMaxAttractDistance() {
        return maxAttractDistance;
    }

    public void setMaxAttractDistance(float maxAttractDistance) {
        this.maxAttractDistance = maxAttractDistance;
    }

    public float getLenRatio() {
        return lenRatio;
    }

    public void setLenRatio(float lenRatio) {
        this.lenRatio = lenRatio;
    }

    public float getAttractAngel() {
        return attractAngel;
    }

    public void setAttractAngel(float attractAngel) {
        this.attractAngel = attractAngel;
    }

    public float getContactAngel() {
        return contactAngel;
    }

    public void setContactAngel(float contactAngel) {
        this.contactAngel = contactAngel;
    }

    public float getMaxOverMoveRatio() {
        return maxOverMoveRatio;
    }

    public void setMaxOverMoveRatio(float maxOverMoveRatio) {
        this.maxOverMoveRatio = maxOverMoveRatio;
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        if (visibilityCallback != null) {
            visibilityCallback.onVisibilityChange(visible);
        }
        return super.setVisible(visible, restart);
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        for (Ball ball : ballList) {
            ball.finalColor = ball.color == 0 ? defaultColor : ball.color;
        }
        processColor();
        int size = ballList.size();
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size; ++j) {
                contact(canvas, ballList.get(i), ballList.get(j));
            }
        }
        drawBall(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        ballPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        ballPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    private Ball tmpBall = new Ball();
    protected void contact(Canvas canvas, Ball a, Ball b) {
        float centerDis = getDistance(a, b);
        float dis = centerDis - a.r - b.r;
        if (dis > maxAttractDistance || a.r <= 0 || b.r <= 0) {
            return;
        }

        float disRatio = getDisRatio(a, b, centerDis);
        float k = maxOverMoveRatio * (a.r * (a.canTransform ? 1 : 0) + b.r * (b.canTransform ? 1 : 0));
        float crossRatio = maxAttractDistance/(maxAttractDistance + k);
        if (isBallContact(a, b, centerDis)) {
            // 连体
            float dR = (float) Math.acos((a.r - b.r) / centerDis);
            // from -1 to 1
            float ratio = (disRatio - crossRatio) / (1 - crossRatio);
            ratio = (ratio - 0.5f) / 0.5f;
            if (dis < 0) {
                // from 1 to 2
                if (a.r > b.r) {
                    float c = a.r - b.r;
                    ratio = 2 - (centerDis - c) / b.r / 2;
                } else {
                    float c = b.r - a.r;
                    ratio = 2 - (centerDis - c) / a.r / 2;
                }
            }
            float ra = dR + contactAngel * ratio, rb = dR - ratio * contactAngel;
            rb = rb < 0 ? 0 : rb;
            ra = ra > Math.PI ? (float) Math.PI : ra;
            drawLiquid(canvas, a, b, ra, rb, lenRatio);
        } else {
            // 吸引
            for (int i = 0; i < 2; ++i) {
                Ball from, to;
                if (i == 0) {
                    from = a; to = b;
                } else {
                    from = b; to = a;
                }
                if (!from.canTransform) {
                    continue;
                }
                float cosO = (to.x - from.x) / centerDis;
                float sinO = (to.y - from.y) / centerDis;
                tmpBall.x = from.r * disRatio * cosO * maxOverMoveRatio + from.x + from.r * (1 - attractBallRatio) * cosO;
                tmpBall.y = from.r * disRatio * sinO * maxOverMoveRatio + from.y + from.r * (1 - attractBallRatio) * sinO;
                tmpBall.r = from.r * attractBallRatio;
                tmpBall.color = from.color;
                tmpBall.finalColor = from.finalColor;
                ballPaint.setColor(tmpBall.finalColor);
                canvas.drawCircle(tmpBall.x, tmpBall.y, tmpBall.r, ballPaint);

                float tmpDis = getDistance(from, tmpBall);
                float dR = (float) Math.acos((from.r - tmpBall.r) / tmpDis);
                float ratio = (crossRatio - disRatio) / crossRatio;
                // from o to 1
                float ra = dR + attractAngel * ratio, rb = dR - attractAngel * ratio;
                rb = rb < 0 ? 0 : rb;
                ra = ra > Math.PI ? (float) Math.PI : ra;
                drawLiquid(canvas, from, tmpBall, ra, rb, lenRatio);
            }
        }
    }

    protected boolean isBallContact(Ball a, Ball b, float ballCenterDistance) {
        // 两圆的边界距离
        float disRatio = getDisRatio(a, b, ballCenterDistance);
        float dis = ballCenterDistance - a.r - b.r;
        float ballOverA = a.r * disRatio * maxOverMoveRatio * (a.canTransform ? 1 : 0);
        float ballOverB = b.r * disRatio * maxOverMoveRatio * (b.canTransform ? 1 : 0);
        if (ballOverA + ballOverB > dis) {
            // 连体了
            int mixColor = mixColor(a.finalColor, b.finalColor, b.r*b.r / (a.r*a.r + b.r*b.r));
            a.finalColor = b.finalColor = mixColor;
        }
        return ballOverA + ballOverB > dis;
    }

    protected float getDisRatio(Ball a, Ball b, float ballCenterDistance) {
        float dis = ballCenterDistance - a.r - b.r;
        float disRatio = dis / maxAttractDistance;
        disRatio = 1 - disRatio;
        disRatio = disRatio < 0 ? 0 : disRatio;
        disRatio = disRatio > 1 ? 1 : disRatio;
        return disRatio;
    }

    protected void processColor() {
        int size = ballList.size();
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size; ++j) {
                Ball a = ballList.get(i), b = ballList.get(j);
                float centerDis = getDistance(a, b);
                float dis = centerDis - a.r - b.r;
                dis = dis < 0 ? 0 : dis;
                if (dis > maxAttractDistance || a.r <= 0 || b.r <=0 ) {
                    continue;
                }
                // 两圆的边界距离
                if (isBallContact(a, b, centerDis)) {
                    // 连体了
                    int mixColor = mixColor(a.finalColor, b.finalColor, b.r*b.r / (a.r*a.r + b.r*b.r));
                    a.finalColor = b.finalColor = mixColor;
                }
            }
        }
    }

    protected void drawBall(Canvas canvas) {
        for (Ball ball : ballList) {
            ballPaint.setColor(ball.finalColor);
            canvas.drawCircle(ball.x, ball.y, ball.r, ballPaint);
        }
    }

    // 两个圆根据 角度 rA 和 角度 rB （相对于连线的同一个角度上）的切线交点，把此交点*lenRatio 作为三次贝塞尔的控制点
    private PointF[] tmpPoints;
    private PointF tmpPointA = new PointF(), tmpPointB = new PointF();
    private Path tmpPath = new Path();
    protected void drawLiquid(Canvas canvas, Ball a, Ball b, float rA, float rB, float lenRatio) {
        float dis = getDistance(a, b);
        float cosTheta = (b.x - a.x) / dis;
        float sinTheta = (b.y - a.y) / dis;
        float theta = (float) Math.acos(cosTheta);
        if (sinTheta < 0) {
            theta = -theta;
        }
        if (tmpPoints == null) {
            tmpPoints = new PointF[8];
            for (int i = tmpPoints.length - 1; i >= 0; --i) {
                tmpPoints[i] = new PointF();
            }
        }
        tmpPoints[0].x = a.x + a.r * (float) Math.cos(theta + rA);
        tmpPoints[0].y = a.y + a.r * (float) Math.sin(theta + rA);
        tmpPoints[1].x = a.x + a.r * (float) Math.cos(theta - rA);
        tmpPoints[1].y = a.y + a.r * (float) Math.sin(theta - rA);
        float gama = theta;
        tmpPoints[2].x = b.x + b.r * (float) Math.cos(gama + rB);
        tmpPoints[2].y = b.y + b.r * (float) Math.sin(gama + rB);
        tmpPoints[3].x = b.x + b.r * (float) Math.cos(gama - rB);
        tmpPoints[3].y = b.y + b.r * (float) Math.sin(gama - rB);
        // 计算控制点
        tmpPointA.set(a.x, a.y); tmpPointB.set(b.x, b.y);
        calcCrossPoint(tmpPoints[0], tmpPointA, tmpPoints[2], tmpPointB, tmpPoints[4]);
        tmpPoints[5].x = tmpPoints[2].x + (tmpPoints[4].x - tmpPoints[2].x) * lenRatio;
        tmpPoints[5].y = tmpPoints[2].y + (tmpPoints[4].y - tmpPoints[2].y) * lenRatio;
        tmpPoints[4].x = tmpPoints[0].x + (tmpPoints[4].x - tmpPoints[0].x) * lenRatio;
        tmpPoints[4].y = tmpPoints[0].y + (tmpPoints[4].y - tmpPoints[0].y) * lenRatio;
        calcCrossPoint(tmpPoints[1], tmpPointA, tmpPoints[3], tmpPointB, tmpPoints[6]);
        tmpPoints[7].x = tmpPoints[3].x + (tmpPoints[6].x - tmpPoints[3].x) * lenRatio;
        tmpPoints[7].y = tmpPoints[3].y + (tmpPoints[6].y - tmpPoints[3].y) * lenRatio;
        tmpPoints[6].x = tmpPoints[1].x + (tmpPoints[6].x - tmpPoints[1].x) * lenRatio;
        tmpPoints[6].y = tmpPoints[1].y + (tmpPoints[6].y - tmpPoints[1].y) * lenRatio;
        // 绘制
        tmpPath.reset();
        tmpPath.moveTo(tmpPoints[0].x, tmpPoints[0].y);
        tmpPath.cubicTo(tmpPoints[4].x, tmpPoints[4].y, tmpPoints[5].x, tmpPoints[5].y, tmpPoints[2].x, tmpPoints[2].y);
        tmpPath.lineTo(tmpPoints[3].x, tmpPoints[3].y);
        tmpPath.cubicTo(tmpPoints[7].x, tmpPoints[7].y, tmpPoints[6].x, tmpPoints[6].y, tmpPoints[1].x, tmpPoints[1].y);
        tmpPath.close();
        ballPaint.setColor(a.finalColor);
        canvas.drawPath(tmpPath, ballPaint);
        // debug
//        ballPaint.setColor(Color.GREEN);
//        for (int i = 0; i < 4; ++i) {
//            PointF pointF = tmpPoints[i];
//            canvas.drawCircle(pointF.x, pointF.y, 5, ballPaint);
//        }
//        ballPaint.setColor(Color.BLUE);
//        for (int i = 4; i < tmpPoints.length; ++i) {
//            PointF pointF = tmpPoints[i];
//            canvas.drawCircle(pointF.x, pointF.y, 5, ballPaint);
//        }
    }

    protected void calcCrossPoint(PointF _0, PointF a, PointF _1, PointF b, PointF res) {
        float dax = _0.x - a.x, day = _0.y - a.y;
        float dbx = _1.x - b.x, dby = _1.y - b.y;
        float k1 = dax, k2 = day, b1 = dax * _0.x + _0.y*day;
        float k3 = dbx, k4 = dby, b2 = dbx * _1.x + _1.y*dby;
        if (k1 == 0 || sgn(k4-k3*k2/k1, 1e-2f) == 0) {
            res.x = (_0.x + _1.x) / 2;
            res.y = (_0.y + _1.y) / 2;
        } else {
            res.y = (b2 - k3*b1/k1)/(k4-k3*k2/k1);
            res.x = (b1 - k2*res.y)/k1;
        }
    }

    protected int sgn(float v, float eps) {
        return (v > eps ? 1 : 0) - (v < -eps ? 1 : 0);
    }

    /**
     * mix two color color = colorA * (1 - value) + colorB * value
     * @param colorA
     * @param colorB
     * @param value
     * @return
     */
    protected int mixColor(int colorA, int colorB, @FloatRange(from = 0, to = 1) float value){
        int oldA = (colorA >> 24) & 0xff;
        int oldR = (colorA >> 16) & 0xff;
        int oldG = (colorA >> 8) & 0xff;
        int oldB = colorA & 0xff;

        int newA = (colorB >> 24) & 0xff;
        int newR = (colorB >> 16) & 0xff;
        int newG = (colorB >> 8) & 0xff;
        int newB = colorB & 0xff;

        int mixAlpha = (int) (oldA*(1-value) + newA*value);
        int mixR = (int) (oldR * (1 - value) + newR * value);
        int mixG = (int) (oldG * (1 - value) + newG * value);
        int mixB = (int) (oldB * (1 - value) + newB * value);

        return Color.argb(mixAlpha, mixR, mixG, mixB);
    }

    protected float getDistance(Ball a, Ball b) {
        return getDistance(a.x, a.y, b.x, b.y);
    }

    protected float getDistance(float xa, float ya, float xb, float yb) {
        return (float) Math.sqrt((xa-xb)*(xa-xb) + (ya-yb)*(ya-yb));
    }
}
