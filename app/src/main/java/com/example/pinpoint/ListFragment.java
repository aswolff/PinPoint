package com.example.pinpoint;

import android.os.Bundle;
import com.example.pinpoint.Shop;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.pinpoint.model.Ticker;

import java.util.LinkedList;

public class ListFragment extends Fragment{
    ListView listView;

    public ListFragment(){
        // empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentRoot = inflater.inflate(R.layout.tickerlist_display, container, false);
        listView = fragmentRoot.findViewById(R.id.ticker_listView);
        LinkedList<Ticker> tickers = new LinkedList<>();
        tickers.add(new Ticker("EBAY", "https://www.ebay.com/"));
        tickers.add(new Ticker("TARGET", "https://www.target.com/"));
        tickers.add(new Ticker("WALMART", "https://www.walmart.com/?&adid=22222222220220085369&wmlspartner=wmtlabs&wl0=e&wl1=g&wl2=c&wl3=521205638070&wl4=aud-1185573580265:kwd-27665750&wl5=1015213&wl6=&wl7=&wl8=&veh=sem&gclid=Cj0KCQjwof6WBhD4ARIsAOi65ahqXwFS9LqlVdu27q0xKS7U3ISSphccT9wIx7RzgoSk9ioeaSAagFMaAvmOEALw_wcB&gclsrc=aw.ds"));
        tickers.add(new Ticker("BEST BUY", "https://www.bestbuy.com/"));


        ArrayAdapter<Ticker> adapter =  new ArrayAdapter<Ticker>(getActivity(), android.R.layout.simple_list_item_1, tickers);
        listView.setAdapter(adapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Shop.aurl = tickers.get(position).getUrl();
                InfoWebFragment.mwebView.loadUrl(Shop.aurl);
                //Toast.makeText(getContext().getApplicationContext(), "position", Toast.LENGTH_SHORT).show();
            }
        });
        return fragmentRoot;
    }
}
