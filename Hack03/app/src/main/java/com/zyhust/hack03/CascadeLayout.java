package com.zyhust.hack03;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/30.
 */
public class CascadeLayout extends ViewGroup {
    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private List<Integer> listX;
    private  List<Integer> listY;

    public CascadeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CascadeLayout);
        listX = new ArrayList<Integer>();
        listY = new ArrayList<Integer>();

        //获取自定义的属性值CascadeLayout_horizontal_spacing 在attr中定义对应的属性名称
        //类型是declare-styleable
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.CascadeLayout_horizontal_spacing,
                    getResources().getDimensionPixelSize(R.dimen.cacade_horizontal_spacing));
            mVerticalSpacing = a.getDimensionPixelSize(R.styleable.CascadeLayout_vertical_spacing,
                    getResources().getDimensionPixelSize(R.dimen.cacade_vertical_spacing));
        }finally {
            a.recycle();
        }


    }

    //绘制时图的第一步获取每个组件的大小
    //android会根据视图树从上至下遍历视图树，遍历的过程都会向下层传递尺寸和规格
    //onMeasure结束后每个视图都将保存自己的尺寸和规格
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = getPaddingTop();
        final int count = getChildCount();
        for (int i = 0; i < count; i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            width=getPaddingLeft()+mHorizontalSpacing*i;
            listX.add(width);
            listY.add(height);
            width += child.getMeasuredWidth();
            height += mVerticalSpacing;
        }
        height += getChildAt(getChildCount()-1).getMeasuredHeight()+getPaddingBottom();
        setMeasuredDimension(resolveSize(width,widthMeasureSpec),resolveSize(height,heightMeasureSpec));
    }

    //每个父视图通过测量的结果定位所有自视图的位置信息
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i<count;i++){
            View child = getChildAt(i);
            child.layout(listX.get(i),listY.get(i), listX.get(i) + child.getMeasuredWidth(),
                    listX.get(i) + child.getMeasuredHeight());
        }

    }



}
