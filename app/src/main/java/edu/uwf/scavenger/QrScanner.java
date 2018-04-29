package edu.uwf.scavenger;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.zxing.integration.android.IntentIntegrator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QrScanner.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QrScanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrScanner extends Fragment {

    public String scanRes;
    public View thisView;
    private OnFragmentInteractionListener mListener;
    private Button buttonScan;

    public QrScanner() {
        // Required empty public constructor
    }

     /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QrScanner.
     */
    public QrScanner newInstance() {
        QrScanner fragment = new QrScanner();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_qr_scanner, container, false);
        thisView = view;
        //View objects
        buttonScan = view.findViewById(R.id.buttonScan);

        //attaching onclick listener
        buttonScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //initiating the qr code scan
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();
        }});

        if(scanRes != null)
        {
            handleScanResult();
        }

        return view;
    }

    private RequestHandler rh;

    public void handleScanResult()
    {
        rh = new RequestHandler(thisView, (ListView)thisView.findViewById(R.id.qr_list));

        try {
            rh.execute(scanRes);
        } catch (Exception e) {
            e.printStackTrace();
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
