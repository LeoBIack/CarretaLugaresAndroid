package br.usjt.ucsist.cadaluno.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContatoService {

    @GET("/api/7e8d64977cba48e682cbd28e0508123f/contato2")
    Call<List<Contato>> getAllContatos();

    @POST("/api/7e8d64977cba48e682cbd28e0508123f/contato2")
    Call<ResponseBody> salvarContato(
            @Body
                    Contato contato);

    @PUT("/api/7e8d64977cba48e682cbd28e0508123f/contato2/{id}")
    Call<ResponseBody> alterarContato(
            @Path("id") Long id,
            @Body ContatoPut contatoPut);

    @DELETE("/api/7e8d64977cba48e682cbd28e0508123f/contato2/{id}")
    Call<ResponseBody> deletarContato(
            @Path("id") Long id);
}
