package com.project.huiyu.killertry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class CheckRoleFragment extends Fragment {

    private ImageView roleCard;

    public CheckRoleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CheckRoleFragment newInstance(String param1) {
        Log.d("Mydebugger", "Check the role.");
        CheckRoleFragment fragment = new CheckRoleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_role, container, false);
        int roleCode = UserInfo.getUserInstance().getRoleCode();
        //int roleCode = 4;
        roleCard = (ImageView) view.findViewById(R.id.role_card);
        if (roleCode == 1) {
            roleCard.setImageDrawable(getResources().getDrawable(R.drawable.townfolks));
        } else if (roleCode == 2) {
            roleCard.setImageDrawable(getResources().getDrawable(R.drawable.wifi_reconnect));
        } else if (roleCode == 3){
            roleCard.setImageDrawable(getResources().getDrawable(R.drawable.fortune_teller));
        } else if (roleCode == 4) {
            roleCard.setImageDrawable(getResources().getDrawable(R.drawable.witch));
        } else {
            roleCard.setImageDrawable(getResources().getDrawable(R.drawable.defender));
        }
        //roleCard.setText(roleCode);
        return view;
    }



//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
