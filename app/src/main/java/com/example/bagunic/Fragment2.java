package com.example.bagunic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    ArrayList<Actor> actors;
    ListView customListView;
    private static CustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.mainfragment2, container, false);

        //data를 가져와서 어답터와 연결해 준다. 서버에서 가져오는게 대부분 이다.
        actors = new ArrayList<>();
        actors.add(new Actor("Bagunic A set.", "https://m.aprilkitchen.co.kr/web/product/big/20200603/68506144f4aac1ac67ae1804fd690319.jpg", "오리지널 바구니"));
        actors.add(new Actor("Bagunic B set.", "https://mblogthumb-phinf.pstatic.net/MjAxODA1MDFfNDUg/MDAxNTI1MTgwMDE4MzUz._AnV3oUpBcGT2lQBfIbF57A7-7TGcHCXhkHhX7f0HJ4g.EAstCfJtEDCZw0TpCb9UEKCpAVJRjsKtLR60TTRitYMg.JPEG.i_love_living/%EA%BD%83%EB%B0%94%EA%B5%AC%EB%8B%88%EB%A7%8C%EB%93%A4%EA%B8%B0DSC08136.JPG?type=w800", "멋을 강조한 바구니"));
        actors.add(new Actor("Bagunic C set.", "https://previews.123rf.com/images/radstyle/radstyle1301/radstyle130100002/17472041-%EC%9E%A5%EB%82%9C%EA%B0%90-%EB%B0%94%EA%B5%AC%EB%8B%88.jpg", "어린이와 함께 하는 바구니"));

        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        customAdapter = new CustomAdapter(getContext(),actors);
        customListView.setAdapter(customAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                //각 아이템을 분간 할 수 있는 position과 뷰
                String selectedItem = (String) view.findViewById(R.id.textView_name).getTag().toString();
                Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
                if(position == 0){
                    Intent intent = new Intent(getActivity(), RentalActivity.class);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }
}


//data class ArrayList
class Actor {
    private String name;
    private String summary;
    private String thumb_url;

    public Actor(String name, String thumb_url, String summary) {
        this.name = name;
        this.summary = summary;
        this.thumb_url = thumb_url;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getThumb_url() {
        return thumb_url;
    }
}