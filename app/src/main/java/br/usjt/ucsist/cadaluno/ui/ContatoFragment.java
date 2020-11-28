package br.usjt.ucsist.cadaluno.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentHostCallback;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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


import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Contato;
import br.usjt.ucsist.cadaluno.model.ContatoViewModel;
import br.usjt.ucsist.cadaluno.util.ImageUtil;

import static android.app.Activity.RESULT_OK;

public class ContatoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private String mParam1;
    private Contato mParam2;
    private ContatoViewModel contatoViewModel;
    private Contato contatoCorrente;
    private EditText editTextNomeRef;
    private EditText editTextDescricao;
    private static Button salvarlocal;
    private static TextView linkfoto;
    private ImageView foto;
    private static Button voltar;


    public ContatoFragment() {
        // Required empty public constructor

    }


    public static ContatoFragment newInstance(String param1, Contato param2) {
        ContatoFragment fragment = new ContatoFragment();
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
            mParam2 = (Contato) getArguments().getSerializable(ARG_PARAM2);
        }


        contatoViewModel = new ViewModelProvider(this).get(ContatoViewModel.class);
        contatoViewModel.getSalvoSucesso().observe(this, new Observer<Boolean>() {
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
            contatoCorrente = mParam2;
            editTextNomeRef.setText(contatoCorrente.getNomeRef());
            editTextDescricao.setText(contatoCorrente.getDescricao());
            foto.setImageBitmap(ImageUtil.decode(contatoCorrente.getImagem()));
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

        contatoCorrente = new Contato();

        editTextNomeRef = view.findViewById(R.id.editTextNomeL);
        editTextDescricao = view.findViewById(R.id.editTextDescricaoL);
        salvarlocal = view.findViewById(R.id.buttonSalvarL);
        linkfoto = view.findViewById(R.id.linkFoto);
        foto = view.findViewById(R.id.imagemContato);
        voltar=(Button)view.findViewById(R.id.buttonVoltarHome);


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
            contatoCorrente.setImagem(ImageUtil.encode(imageBitmap));
            Log.d("IMAGEMBITMAPENCODED-->", contatoCorrente.getImagem());
        }
    }

    public void salvar() {

        if (mParam2 == null) {
            contatoViewModel.salvarContato(contatoCorrente);

        } else {
            contatoViewModel.alterarContato(contatoCorrente);
        }

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

    public void voltar() {
        replaceFragment(R.id.frameLayout,
                HomeFragment.newInstance("", ""),
                "HOMEFRAGMENT",
                "HOME");
    }

}
