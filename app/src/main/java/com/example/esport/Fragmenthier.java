package com.example.esport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import java.util.List;


public class Fragmenthier extends Fragment {
    public static String EXTRA_MESSAGE3;

    private List<String> list;
    public Fragmenthier (List<String> list){
        this.list = list;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] menuItems = new String[list.size()];

        for (int i = 0; i < list.size(); i++ ){
            menuItems[i] = list.get(i);
        }

        View view = inflater.inflate(R.layout.fragementhier_layout,container,false);


        ListView listView = (ListView) view.findViewById(R.id.listview);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menuItems);

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplication(),Match.class);
                intent.putExtra(EXTRA_MESSAGE3, parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

        return view;
    }
}
