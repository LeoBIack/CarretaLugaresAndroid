package br.usjt.ucsist.cadaluno.ui;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Local;
import br.usjt.ucsist.cadaluno.model.LocalViewModel;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;


public class HomeFragment<buttonAddLocal> extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LocalViewModel localViewModel;
    private List<Local> local;
    private LocalAdapter adapter;
    private ProgressBar progressBar;
    private static Button addLocal;

    private List<Local> localList;

    private FirebaseFirestore db;

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Object RecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new LocalAdapter();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        adapter = new LocalAdapter();
        localViewModel = new ViewModelProvider(this).get(LocalViewModel.class);
        localViewModel.getContatosResponseLiveData().observe(this, new Observer<List<Local>>() {
            @Override
            public void onChanged(List<Local> localList) {
                if (localList != null) {
                    adapter.setResults(localList);
                }
            }
        });

        adapter.setOnItemClickListener(new LocalAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, Local local) {
                replaceFragment(R.id.frameLayout,
                        LocalFragment.newInstance("", local),
                        "CONTATOFRAGMENT",
                        "CONTATO");
            }
        });

//        db = FirebaseFirestore.getInstance();
//
//        db.collection("Locais").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        if (!queryDocumentSnapshots.isEmpty()) {
//
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                            for (DocumentSnapshot d : list) {
//
//                                Local l = d.toObject(Local.class);
//                                local.add(l);
//                            }
//
//                            adapter.notifyDataSetChanged();
//                        }
//
//                    }
//                });
//
//        localList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewLocais);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        localViewModel.getAllLocais();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progressBar);
        addLocal = (Button) view.findViewById(R.id.buttonAddLocal);

        addLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocal();
            }
        });
    }

    public void addLocal() {
        replaceFragment(R.id.frameLayout,
                LocalFragment.newInstance("", null),
                "CONTATOFRAGMENT",
                "CONTATO");
    }

}



