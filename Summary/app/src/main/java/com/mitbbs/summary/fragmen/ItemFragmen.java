package com.mitbbs.summary.fragmen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitbbs.summary.R;

/**
 * Created by jc on 2017/8/11.
 */
public class ItemFragmen extends Fragment {

    public static ItemFragmen newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title",title);
        ItemFragmen fragment = new ItemFragmen();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null);
        TextView tv = (TextView) view.findViewById(R.id.text);
        Bundle bundle = getArguments();
        tv.setText(bundle.getString("title"));
        return view;
    }
}
