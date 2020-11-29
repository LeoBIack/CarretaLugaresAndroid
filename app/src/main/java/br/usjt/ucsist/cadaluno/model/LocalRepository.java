package br.usjt.ucsist.cadaluno.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalRepository {

    private static final String LOCAIS_SERVICE_BASE_URL = "http://10.0.2.2:8080";

    private LocalService localService;
    private MutableLiveData<List<Local>> contatosResponseMutableLiveData;
    private MutableLiveData<Boolean> salvoSucessoMutableLiveData;

    public LocalRepository() {
        contatosResponseMutableLiveData = new MutableLiveData<>();
        salvoSucessoMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        localService = new retrofit2.Retrofit.Builder()
                .baseUrl(LOCAIS_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocalService.class);

    }

    public void getLocais() {
        localService.getAllLocais()
                .enqueue(new Callback<List<Local>>() {
                    @Override
                    public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultato-->"+response.body());
                            contatosResponseMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Local>> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        contatosResponseMutableLiveData.postValue(null);
                    }
                });
    }

    public LiveData<List<Local>> getAllLocais() {
        return contatosResponseMutableLiveData;
    }

    public LiveData<Boolean> getSalvoSucesso() {
        return salvoSucessoMutableLiveData;
    }

    public void salvarContato(Local local){

        localService.salvarLocal(local)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "tenho resultado-->"+response.body());
                            salvoSucessoMutableLiveData.postValue(new Boolean(true));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("RESPOSTA", "Local falhou ao salvar->"+t.getMessage());
                        salvoSucessoMutableLiveData.postValue(new Boolean(false));
                    }
                });

    }

    public void alterarContato(Local local){

        LocalPut localPut = new LocalPut(local.getNomeRef(), local.getDescricao(),
                 local.getImagem());

        localService.alterarLocal(local.getId(), localPut)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                            Log.d("RESPOSTA", "Novo local foi Adicionado!-->"+response.body());
                            salvoSucessoMutableLiveData.postValue(new Boolean(true));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("RESPOSTA", "FALHOU->"+t.getMessage());
                        salvoSucessoMutableLiveData.postValue(new Boolean(false));
                    }
                });
    }

    public Call<ResponseBody> deletarLocal(Local local){
        return localService.deletarLocal(local.getId());
    }


}
