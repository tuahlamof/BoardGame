package com.project.huiyu.killertry;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class CheckRoleFragment extends Fragment {

    private ImageButton roleCard;
    private TextView roleCardText;
    private boolean isBack = true;

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
        final int roleCode = UserInfo.getUserInstance().getRoleCode();
        roleCard = (ImageButton) view.findViewById(R.id.role_card);
        roleCardText = (TextView) view.findViewById(R.id.role_card_text);

        roleCard.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (isBack) {
                        if (roleCode == 1) {
                            roleCard.setBackground(getResources().getDrawable(R.drawable.townfolks));
                            roleCardText.setText("Town Folk");
                        } else if (roleCode == 2) {
                            roleCard.setBackground(getResources().getDrawable(R.drawable.wifi_reconnect));
                            roleCardText.setText("Werewolf");
                        } else if (roleCode == 3){
                            roleCard.setBackground(getResources().getDrawable(R.drawable.fortune_teller));
                            roleCardText.setText("Prophet");
                        } else if (roleCode == 4) {
                            roleCard.setBackground(getResources().getDrawable(R.drawable.witch));
                            roleCardText.setText("Witch");
                        } else {
                            roleCard.setBackground(getResources().getDrawable(R.drawable.defender));
                            roleCardText.setText("Guard");
                        }
                    } else {
                        roleCard.setBackground(getResources().getDrawable(R.drawable.card_back));
                        roleCardText.setText("Tap to check your role");
                    }
                    isBack = !isBack;
                }
        });
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
