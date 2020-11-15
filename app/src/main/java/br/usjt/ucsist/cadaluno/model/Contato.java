package br.usjt.ucsist.cadaluno.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contato implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("nome")
    @Expose
    private String nomeRef;
    @SerializedName("descricao")
    @Expose
    private String descricao;
    @SerializedName("imagem")
    @Expose
    private String imagem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeRef() {
        return nomeRef;
    }

    public void setNomeRef(String nomeRef) {
        this.nomeRef = nomeRef;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}