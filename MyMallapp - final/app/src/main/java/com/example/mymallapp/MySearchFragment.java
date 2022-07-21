    package com.example.mymallapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MySearchFragment extends Fragment {
    private String user;
    private TextView search_text_noproducts;
    private RecyclerView search_items_recycler_view;
    private EditText search_bar;
    private ImageButton search_cross_btn;
    private ProgressBar prog;
    public MySearchFragment() {
    }
    private Button viewAllAddressBtn;
    public static final int MANAGE_ADDRESS=1;
    //private TextView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_search, container, false);
        search_bar=(EditText) view.findViewById(R.id.search_bar_edit);
        System.out.println("ananan");
        //search_bar.setText("asdasd");
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("onKeyDown1");
                //Toast.makeText(getContext(),"onkey",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("onKeyDown2");
                //.makeText(getContext(),"onkey2",Toast.LENGTH_LONG).show();
                //func();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("onKeyDown3");
                //Toast.makeText(getContext(),"onkey3",Toast.LENGTH_LONG).show();
                func();
            }
            public void func(){
                search_cross_btn.setVisibility(View.VISIBLE);
                prog.setVisibility(View.VISIBLE);
                search_text_noproducts.setVisibility(View.GONE);
                search_items_recycler_view.setVisibility(View.GONE);
            }
        });
        System.out.println("ananan22");
        prog=view.findViewById(R.id.search_progressbar);
        //search_items_recycler_view=view.findViewById(R.id.search_items_recycler_view);
        search_text_noproducts=view.findViewById(R.id.search_text_noproducts);
        search_cross_btn=view.findViewById(R.id.search_cross_btn);
        search_cross_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_bar.setText("");
                search_cross_btn.setVisibility(View.INVISIBLE);
                prog.setVisibility(View.GONE);
                search_items_recycler_view.setVisibility(View.GONE);
            }
        });
       /* search_bar.setKeyListener(new KeyListener() {
            @Override
            public int getInputType() {
                return 0;
            }

            @Override
            public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
                func();
                System.out.println("onKeyDown");
                return true;
            }

            @Override
            public boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
                func();
                System.out.println("onKeyUp");
                return true;
            }

            @Override
            public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
                func();
                System.out.println("onKeyOther");
                return false;
            }

            @Override
            public void clearMetaKeyState(View view, Editable editable, int i) {
                func();
                System.out.println("clearMetaKeyState");
            }
            public void func(){
                search_cross_btn.setVisibility(View.VISIBLE);
                prog.setVisibility(View.VISIBLE);
                search_text_noproducts.setVisibility(View.GONE);
                search_items_recycler_view.setVisibility(View.GONE);
//                Thread th=new Thread();
//                try {
//                    th.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
*/
        return view;
    }

}