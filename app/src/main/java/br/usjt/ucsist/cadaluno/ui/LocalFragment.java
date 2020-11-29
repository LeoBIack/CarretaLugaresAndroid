package br.usjt.ucsist.cadaluno.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Local;
import br.usjt.ucsist.cadaluno.model.LocalViewModel;
import br.usjt.ucsist.cadaluno.util.ImageUtil;

import static android.app.Activity.RESULT_OK;

public class LocalFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private Local mParam2;
    private LocalViewModel localViewModel;
    private Local localCorrente;
    private EditText editTextNomeRef;
    private EditText editTextDescricao;
    private static Button salvarlocal;
    private static TextView linkfoto;
    private ImageView foto;
    private static Button voltar;

    private FirebaseFirestore db;

    public LocalFragment() {
        // Required empty public constructor
    }


    public static LocalFragment newInstance(String param1, Local param2) {
        LocalFragment fragment = new LocalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = (Local) getArguments().getSerializable(ARG_PARAM2);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
        }

        localViewModel = new ViewModelProvider(this).get(LocalViewModel.class);
        localViewModel.getSalvoSucesso().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean sucesso) {
                String mensagem = "O local não foi salvo";
                if (sucesso) {
                    mensagem = "Local salvo com sucesso!";
                    limpar();
                }
                Toast.makeText(getActivity(), mensagem, Toast.LENGTH_SHORT).show();

            }
        });

        if (mParam2 != null) {
            localCorrente = mParam2;
            editTextNomeRef.setText(localCorrente.getNomeRef());
            editTextDescricao.setText(localCorrente.getDescricao());
            foto.setImageBitmap(ImageUtil.decode(localCorrente.getImagem()));
        }

    }

    private void limpar() {
        editTextNomeRef.setText("");
        editTextDescricao.setText("");
        foto.setImageResource(R.drawable.ic_place_holder);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locais, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        localCorrente = new Local();

        editTextNomeRef = view.findViewById(R.id.editTextNomeL);
        editTextDescricao = view.findViewById(R.id.editTextDescricaoL);
        salvarlocal = view.findViewById(R.id.buttonSalvarL);
        linkfoto = view.findViewById(R.id.linkFoto);
        foto = view.findViewById(R.id.imagemContato);
        voltar = (Button) view.findViewById(R.id.buttonVoltarHome);


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltar();

            }
        });


        linkfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });


        salvarlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

    }

    private void tirarFoto() {
        dispatchTakePictureIntent();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
            localCorrente.setImagem(ImageUtil.encode(imageBitmap));
            Log.d("IMAGEMBITMAPENCODED-->", localCorrente.getImagem());
        }
    }

    public boolean validateInputs(String nomeRef, String descricao) {
        return true;
    }

    public void salvar() {

        if (mParam2 == null) {
            localViewModel.salvarContato(localCorrente);

        } else {
            localViewModel.alterarContato(localCorrente);
        }

//        String nomeRef = editTextNomeRef.getText().toString().trim();
//        String descricao = editTextDescricao.getText().toString().trim();
//        if (validateInputs(nomeRef, descricao)) {
//
//            CollectionReference dbLocais = db.collection("Locais");
//
//            Local local = new Local();

//            dbLocais.add(local)
//                    .addOnSuccessListener((new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(LocalFragment.this, "Local adicionado", Toast.LENGTH_LONG).show();
//                        }
//                    }))
//                    .addOnFailureListener((new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(LocalFragment.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                        }
//                    }));
    }

//    }

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

    public void voltar() {
        replaceFragment(R.id.frameLayout,
                HomeFragment.newInstance("", ""),
                "HOMEFRAGMENT",
                "HOME");
    }

}
