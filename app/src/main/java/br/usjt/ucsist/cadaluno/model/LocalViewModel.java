package br.usjt.ucsist.cadaluno.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LocalViewModel extends AndroidViewModel {

    private LocalRepository localRepository;
    private LiveData<List<Local>> contatosResponseLiveData;
    private LiveData<Boolean> salvoComSucessoLiveData;

    public LocalViewModel(@NonNull Application application) {
        super(application);
        Log.d("RESPOSTA", "Criação de ViewModel");
        localRepository = new LocalRepository();
        contatosResponseLiveData = localRepository.getAllLocais();
        salvoComSucessoLiveData = localRepository.getSalvoSucesso();
    }

    public void getAllLocais() {
        localRepository.getLocais();
    }

    public LiveData<List<Local>> getContatosResponseLiveData() {
        return contatosResponseLiveData;
    }

    public LiveData<Boolean> getSalvoSucesso() {
        return salvoComSucessoLiveData;
    }

    public void salvarContato(Local local) {
        localRepository.salvarContato(local);
    }

    public void alterarContato(Local local) {
        localRepository.alterarContato(local);
    }
}
