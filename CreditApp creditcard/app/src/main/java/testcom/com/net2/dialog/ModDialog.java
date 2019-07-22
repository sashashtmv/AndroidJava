package testcom.com.net2.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;

import testcom.com.net2.R;
import testcom.com.net2.model.DialogClickListener;
import testcom.com.net2.model.Mod;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModDialog extends AlertDialog {

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.dialogTitle)
    TextView title;
    @BindView(R.id.dialogDesc)
    TextView description;
    @BindView(R.id.downloadButton)
    Button downloadButton;

    private Mod mod;
    private DialogClickListener dialogClickListener;
    private ArrayList<String> pictures;

    private Context context;

    public ModDialog(@NonNull Context context, Mod mod) {
        super(context);
        this.mod = mod;
        this.context = context;
        this.dialogClickListener = (DialogClickListener) context;
        pictures = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog_layout);
        ButterKnife.bind(this);
        String t = mod.title.replace("_", " ");
        title.setText(t);
        description.setText(mod.description);
    }
}
