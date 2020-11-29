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

public interface LocalService {

    @GET("/api/7e8d64977cba48e682cbd28e0508123f/contato2")
    Call<List<Local>> getAllLocais();

    @POST("/api/7e8d64977cba48e682cbd28e0508123f/contato2")
    Call<ResponseBody> salvarLocal(
            @Body
                    Local local);

    @PUT("/api/7e8d64977cba48e682cbd28e0508123f/contato2/{id}")
    Call<ResponseBody> alterarLocal(
            @Path("id") Long id,
            @Body LocalPut localPut);

    @DELETE("/api/7e8d64977cba48e682cbd28e0508123f/contato2/{id}")
    Call<ResponseBody> deletarLocal(
            @Path("id") Long id);
}
