package com.monashfriendfinder.friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.monashfriendfinder.FriendListViewAdapter;
import com.monashfriendfinder.R;
import com.monashfriendfinder.baiduMap.MapActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FriendListFragment extends Fragment {

    private Unbinder unbinder;
    private ListView listView;
    private String A[][]=new String[][]{
            {"Jarry", "Fisher", "male","1995/03/09","FIT5183","China","full-time","The Shawshank Redemption"},
            {"Mike","Steven","male","1997/07/07","FIT5187","China","full-time","The Avengers 4"}};

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_friendlist,null);
        unbinder = ButterKnife.bind(this, view);
        listView = (ListView)view.findViewById(R.id.friend_listview);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new FriendListViewAdapter(getActivity(), list));

        //设置listView的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Log.d("---=====---------96", String.valueOf(position));
                //这里position就表示的Person实例化对象在personList里面的位置,例如当你点击第2个item的时候，position的值就为1.（因为从0开始计数）
                int posititonNum = position;
                Intent intent = new Intent(getActivity(),FriendDetailActivity.class);
                intent.putExtra("friendList_positionNum",posititonNum);//给下一个活动传参数positionNum
                //避免重复请求数据库，此处需要将在FriendDetailActivity中需要展示的参数一并传过去
                //可将getData()中请求得到的数据存储在二维数组A中，根据position对应取A[i][]
                intent.putExtra("firstName",A[posititonNum][0]);
                intent.putExtra("familyName",A[posititonNum][1]);
                intent.putExtra("gender",A[posititonNum][2]);
                intent.putExtra("DoB",A[posititonNum][3]);
                intent.putExtra("unit",A[posititonNum][4]);
                intent.putExtra("nationality",A[posititonNum][5]);
                intent.putExtra("studyMode",A[posititonNum][6]);
                intent.putExtra("favoriteMovie",A[posititonNum][7]);
                startActivity(intent);
                //finish();
            }
        });

        return view;
    }

    /*@OnClick(value = R.id.friend_detail)
    void showDetail(){
        Intent i = new Intent();
        i.setClass(getActivity(),FriendDetailActivity.class);
        startActivity(i);
    }*/

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(value = R.id.change_map_view)
    public void changeMapView(){
        Intent i = new Intent();
        i.setClass(getActivity(), MapActivity.class);
        i.putExtra("request", "friendlist");
        startActivity(i);
    }

    //从数据库中取得数据加入更新到ListView
    //此处为测试数据，根据xml布局以及调用map所需参数来向数据请求
    public List<Map<String, Object>> getData(){
        String strName[]=new String[]{"Jarry Fisher","Mike Steven"};
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("info",strName[i]);
            list.add(map);
        }
        return list;
    }
}
