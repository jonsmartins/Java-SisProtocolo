package br.com.uva;
public class Usuarios{
    String usuario;
    String senha;
    Usuarios(String usuario, String senha){
        this.usuario = usuario;
        this.senha = senha;
    }
    Usuarios(){

    }
    public String getNome(){
        return this.usuario;
    }
    public String getSenha(){
        return this.senha;
    }

}