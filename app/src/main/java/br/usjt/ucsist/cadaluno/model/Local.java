package br.usjt.ucsist.cadaluno.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Local implements Serializable {

    @SerializedName("_id")
    @Expose
    private Long id;
    @SerializedName("nomeRef")
    @Expose
    private String nomeRef;
    @SerializedName("descricao")
    @Expose
    private String descricao;
    @SerializedName("imagem")
    @Expose
    private String imagem;
    @SerializedName("lat")
    @Expose
    private int lat;
    @SerializedName("longi")
    @Expose
    private int longi;
    @SerializedName("data")
    @Expose
    private int data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLongi() {
        return longi;
    }

    public void setLongi(int longi) {
        this.longi = longi;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}