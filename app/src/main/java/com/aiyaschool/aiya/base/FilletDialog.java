package com.aiyaschool.aiya.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.aiyaschool.aiya.R;


/**
 * Created by EGOISTK on 2017/3/28.
 */

public class FilletDialog extends Dialog {


    private FilletDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private int resourceId;
        private String title, subTitle, negativeButtonText, positiveButtonText;
        private DialogInterface.OnClickListener negativeOnclickListener, positiveOnclickListener;

        public Builder(Context context, int resourceId) {
            this.context = context;
            this.resourceId = resourceId;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeOnclickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveOnclickListener = listener;
            return this;
        }

        public FilletDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final FilletDialog filletDialog = new FilletDialog(context, R.style.FilletDialog);
            View rootView = inflater.inflate(resourceId, null);
            filletDialog.addContentView(rootView, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            if (title != null) {
                ((TextView) rootView.findViewById(R.id.tv_dialog_title)).setText(title);
            }
            if (subTitle != null) {
                ((TextView) rootView.findViewById(R.id.tv_dialog_sub_title)).setText(subTitle);
            } else if (rootView.findViewById(R.id.tv_dialog_sub_title) != null){
                rootView.findViewById(R.id.tv_dialog_sub_title).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                Button button = (Button) rootView.findViewById(R.id.btn_dialog_negative);
                button.setText(negativeButtonText);
                if (negativeOnclickListener != null) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeOnclickListener.onClick(filletDialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                rootView.findViewById(R.id.btn_dialog_negative).setVisibility(View.GONE);
            }
            if (positiveButtonText != null) {
                Button button = (Button) rootView.findViewById(R.id.btn_dialog_positive);
                button.setText(positiveButtonText);
                if (positiveOnclickListener != null) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveOnclickListener.onClick(filletDialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                rootView.findViewById(R.id.btn_dialog_positive).setVisibility(View.GONE);
            }
            filletDialog.setContentView(rootView);
            return filletDialog;
        }

    }
}
