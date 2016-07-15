package mx.gob.jalisco.edu.consultaescolar.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mx.gob.jalisco.edu.consultaescolar.R;
import mx.gob.jalisco.edu.consultaescolar.adapters.CardsAdapter;
import mx.gob.jalisco.edu.consultaescolar.objects.Card;



public class SchoolFragment extends Fragment {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        private String mParam1;
        private String mParam2;

        private OnFragmentInteractionListener mListener;

        List<Card> items;
        CardsAdapter adapter;
        RecyclerView recycler;
        RecyclerView.LayoutManager lManager;

        public SchoolFragment() {
            // Required empty public constructor
        }

        public static SchoolFragment newInstance(String param1, String param2) {
            SchoolFragment fragment = new SchoolFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            items = new ArrayList<>();

            View view = inflater.inflate(R.layout.fragment_school, container, false);

            recycler = (RecyclerView) view.findViewById(R.id.recycler);

            lManager = new LinearLayoutManager(getActivity());
            recycler.setLayoutManager(lManager);
            items.add(new Card("http://escuelatransparente.se.jalisco.gob.mx","Búsqueda de escuelas",R.drawable.busqueda_esc_baja));
            items.add(new Card("http://estudiaen.jalisco.gob.mx/suma-por-la-paz/","Reporte casos acoso escolar",R.drawable.reporte_acoso_baja));

            adapter = new CardsAdapter(items, getContext());
            recycler.setAdapter(adapter);

            return view;
        }

        public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onFragmentInteraction(uri);
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
    }
