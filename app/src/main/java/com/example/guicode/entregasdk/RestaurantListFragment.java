package com.example.guicode.entregasdk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guicode.entregasdk.DataSources.CustomAdapter;
import com.example.guicode.entregasdk.DataSources.RestaurantList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView LIST;
    private ArrayList<RestaurantList> LISTINFO;
    private CustomAdapter ADAPTER;
    private View root;
    private Context globalContext = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public RestaurantListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantListFragment newInstance(String param1, String param2) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        LISTINFO = new ArrayList<RestaurantList>();
        if(root==null){
            root=inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        }
        // Inflate the layout for this fragment

        //loadDataFromApi();

        loadComponents();

        return root;

    }
    private void loadDataFromApi()
    {
        AsyncHttpClient client= new AsyncHttpClient();
        String ip=Utils.restaurant;

        client.get(ip,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response)
                    {
                        Toast.makeText(globalContext.getApplicationContext(),"Cargando datos ",Toast.LENGTH_SHORT).show();
                        JSONArray List=(JSONArray) response;
                        JSONObject itemJson;

                        for(int i=0;i<List.length();i++)
                        {
                            try {
                                //Toast.makeText(getApplicationContext(),"Entrado al for "+i,Toast.LENGTH_SHORT).show();
                                itemJson=List.getJSONObject(i);

                                String name=itemJson.getString("name");
                                String phone=itemJson.getString("phone");
                                String nit=itemJson.getString("nit");
                                String street=itemJson.getString("street");
                                String img = itemJson.getString("restaurantPhoto");
                                String id = itemJson.getString("_id");
                                RestaurantList A=new RestaurantList(name,phone,nit,street,img,id);
                                LISTINFO.add(A);


                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        ADAPTER=new CustomAdapter(globalContext,LISTINFO);
                        LIST.setAdapter(ADAPTER);
                    }
                }
        );
    }
    private void loadComponents() {

        LIST=root.findViewById(R.id.Restaurant_List_To_Api);
        //RestaurantList l=new RestaurantList("prueba1","prueba2","prueba3","prueba4");
        //LISTINFO.add(l);
        //ADAPTER=new CustomAdapter(globalContext,LISTINFO);
        //Toast.makeText(globalContext.getApplicationContext(),"Cargando datos ",Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setContextGlobal(Context root) {
        globalContext=root;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
