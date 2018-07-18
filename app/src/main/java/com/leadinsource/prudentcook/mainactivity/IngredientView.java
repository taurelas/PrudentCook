package com.leadinsource.prudentcook.mainactivity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leadinsource.prudentcook.R;


/* https://stackoverflow.com/a/10783069/3886459 */

public class IngredientView extends LinearLayout {

    private TextView text;
    private ImageButton button;

    public IngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.ingredientview, this);
    }

    public IngredientView(Context context, AttributeSet attrs, CharSequence ingredientName) {
        super(context, attrs);
        setText(ingredientName);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text = findViewById(R.id.textView);
        button = findViewById(R.id.button);
    }

    public void setText(CharSequence name) {
        text.setText(name);
    }

    public CharSequence getText() {
        return text.getText();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        button.setOnClickListener(l);
        text.setOnClickListener(null);
    }
}
