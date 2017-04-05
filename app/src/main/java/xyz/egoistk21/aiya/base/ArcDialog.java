package xyz.egoistk21.aiya.base;

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

import xyz.egoistk21.aiya.R;

/**
 * Created by EGOISTK on 2017/3/28.
 */

public class ArcDialog extends Dialog {

    protected ArcDialog(@NonNull Context context) {
        super(context);
    }

    public ArcDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;
        private String title, subTitle, negativeButtonText, positiveButtonText;
        private DialogInterface.OnClickListener negativeOnclickListener, positiveOnclickListener;

        public Builder(Context context) {
            this.context = context;
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

        public ArcDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ArcDialog arcDialog = new ArcDialog(context, R.style.ArcDialog);
            View rootView = inflater.inflate(R.layout.dialog_single_picker, null);
            arcDialog.addContentView(rootView, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            if (title != null) {
                ((TextView) rootView.findViewById(R.id.tv_dialog_title)).setText(title);
            }
            if (subTitle != null) {
                ((TextView) rootView.findViewById(R.id.tv_dialog_sub_title)).setText(subTitle);
            } else {
                rootView.findViewById(R.id.tv_dialog_sub_title).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                Button button = (Button) rootView.findViewById(R.id.btn_dialog_negative);
                button.setText(negativeButtonText);
                if (negativeOnclickListener != null) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeOnclickListener.onClick(arcDialog, DialogInterface.BUTTON_NEGATIVE);
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
                            positiveOnclickListener.onClick(arcDialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                rootView.findViewById(R.id.btn_dialog_positive).setVisibility(View.GONE);
            }
            arcDialog.setContentView(rootView);
            return arcDialog;
        }

    }
}
