//package com.dididi.pocket.core.ui.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.view.Gravity;
//import android.view.View;
//import android.view.WindowManager;
//
//import com.bumptech.glide.Glide;
//import com.dididi.pocket.core.R;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//
///**
// * @author dididi(yechao)
// * @describe 悬浮着的圆圈dialog
// * @since 06/11/2018
// */
//
//public class FloatCircleDialog extends Dialog {
//
//    private View mDialog;
//    private CircleImageView mImage;
//    private Context mContext;
//
//    /**
//     * 判断焦点x位置
//     */
//    private float mFocusX;
//    /**
//     * 判断焦点y位置
//     */
//    private float mFocusY;
//    /**
//     * 判断是否被拖拽
//     */
//    private boolean isDrag = false;
//
//    public FloatCircleDialog(@NonNull Context context) {
//        super(context);
//        this.mContext = context;
//        init(context);
//    }
//
//    public FloatCircleDialog(@NonNull Context context, int themeResId) {
//        super(context, themeResId);
//        this.mContext = context;
//        init(context);
//    }
//
//    public static FloatCircleDialog getInstance(Context context) {
//        return new FloatCircleDialog(context, R.style.float_circle_dialog);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getWindow() == null) {
//            throw new RuntimeException("getWindow为空 请检查FloatCircleDialog");
//        }
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
//        getWindow().setAttributes(params);
//    }
//
//    private void init(Context context) {
//        mDialog = View.inflate(context, R.layout.float_circle_dialog, null);
//        mImage = mDialog.findViewById(R.id.float_circle_dialog);
//    }
//
//    public FloatCircleDialog setImage(String image) {
//        Glide.with(mContext)
//                .load(image)
//                .into(mImage);
//        return this;
//    }
//}
