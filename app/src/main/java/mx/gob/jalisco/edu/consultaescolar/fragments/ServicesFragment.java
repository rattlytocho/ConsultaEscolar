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

public class ServicesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<Card> items;
    CardsAdapter adapter;
    RecyclerView recycler;
    RecyclerView.LayoutManager lManager;

    public ServicesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ServicesFragment newInstance(String param1, String param2) {
        ServicesFragment fragment = new ServicesFragment();
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

        View view = inflater.inflate(R.layout.fragment_services, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);
        items.add(new Card("http://inscripciones.jalisco.gob.mx/inscribe/","Asignación de Estudiantes ciclo escolar 2016-2017",R.drawable.asignacion_baja));
        items.add(new Card("http://consultaescolar.jalisco.gob.mx/escolar/login","Consulta de calificaciones",R.drawable.consulta_calificaciones_baja));
        items.add(new Card("http://consultaescolar.jalisco.gob.mx/escolar/certificado","Descarga tu certificado",R.drawable.imprime_certificado_baja));
        items.add(new Card("http://inscripciones.jalisco.gob.mx/inscribe/","Inscripciones",R.drawable.inscripciones_baja));

        adapter = new CardsAdapter(items, getContext());
        recycler.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
