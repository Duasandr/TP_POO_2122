package com.grupo.device;

public class Fabricante {
    //Variáveis de instância
    private String nome;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Fabricante(){
        this("null");
    }

    /**
     * Construtor por parâmetros.
     * @param nome Nome do fabricante.
     */
    public Fabricante(String nome){
        this.setNome(nome);
    }

    /**
     * Construtor por cópia.
     * @param fab Fabricante a copiar.
     */
    public Fabricante(Fabricante fab){
        this(fab.getNome());
    }

    //Métodos de instância

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SmartBulb produzSmartBulb(String id , boolean estado ,int potencia , double preco_instalacao , int tone ,double dimensao){
        return new SmartBulb(id, estado ,potencia, preco_instalacao, tone, dimensao);
    }

    public SmartSpeaker produzSmartSpeaker(String id , boolean estado , int potencia , double preco_instalacao , int vol , String canal , int max_vol){
        return new SmartSpeaker(id, estado ,potencia, preco_instalacao, vol, canal, max_vol);
    }

    public SmartCamera produzSmartCamera(String id , boolean estado ,int potencia , double preco_instalacao , int res , Double tamanho_ficheiro){
        return new SmartCamera(id, estado ,potencia, preco_instalacao, res, tamanho_ficheiro);
    }
}
