package org.ramonaza.officialramonapp.uifragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.EventInfoWrapper;

/**
 * Created by ilanscheinkman on 1/29/15.
 */
public class GeneralEventFragment extends Fragment{

    private static final String EVENT_DATA = "org.ramonaza.officialramonapp.EVENT_DATA";

        public GeneralEventFragment() {}

        public static GeneralEventFragment newInstance(EventInfoWrapper event){
            GeneralEventFragment fragment=new GeneralEventFragment();
            Bundle args=new Bundle();
            args.putParcelable(EVENT_DATA,event);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            ActionBar actionBar=getActivity().getActionBar();
            View rootView = inflater.inflate(R.layout.fragment_event_data, container, false);
            TextView tView=(TextView) rootView.findViewById(R.id.EventPageTextView);
            LinearLayout layout=(LinearLayout) rootView.findViewById(R.id.EventPageScrollLayout);
            final EventInfoWrapper myEvent=getArguments().getParcelable(EVENT_DATA);
            actionBar.setTitle(myEvent.getName());
            String displayText=String.format("<b><u>%s</u></b><br><br>Description: %s<br>Bring: %s<br>Meet: %s<br>Planned By: %s<br>", myEvent.getName(), myEvent.getDesc(), myEvent.getBring(), myEvent.getMeet(), myEvent.getPlanner());
            tView.setTextSize(22);
            tView.setText(Html.fromHtml(displayText));
            Button dirButton=new Button(getActivity());
            dirButton.setText("Directions");
            dirButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String uri=String.format("google.navigation:q=%s", myEvent.getMapsLocation().replace(" ", "+"));
                        Intent navIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(navIntent);
                    }catch (ActivityNotFoundException activityException){
                        Log.d("Directions to:" + myEvent.getMapsLocation(), "Failed", activityException);
                    }
                }
            });
            layout.addView(dirButton);
        return rootView;
    }
}
