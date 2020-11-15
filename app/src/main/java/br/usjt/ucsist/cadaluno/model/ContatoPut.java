package br.usjt.ucsist.cadaluno.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContatoPut implements Serializable {

    @SerializedName("nomeRef")
    @Expose
    private String nomeRef;
    @SerializedName("descricao")
    @Expose
    private String descricao;
    @SerializedName("imagem")
    @Expose
    private String imagem;

    public ContatoPut(String nomeRef, String descricao, String imagem) {
        this.nomeRef = nomeRef;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getNomeRef() {
        return nomeRef;
    }

    public void setNomeRef(String nomeRef) { this.nomeRef = nomeRef;}

    public String getDescricao() {
        return descricao;
    }

    public void setEmail(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}

