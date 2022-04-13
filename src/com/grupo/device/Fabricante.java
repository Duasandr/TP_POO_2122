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

    public SmartBulb produzSmartBulb(String id , int potencia , double preco_instalacao , double dimensao){
        return new SmartBulb(id, "desligado" , potencia , preco_instalacao, "neutra" , dimensao);
    }

    public SmartSpeaker produzSmartSpeaker(String id , int potencia , double preco_instalacao , int max_vol){
        return new SmartSpeaker(id, "desligado" ,potencia, preco_instalacao, 0, "", max_vol);
    }

    public SmartCamera produzSmartCamera(String id ,int potencia , double preco_instalacao , int res , double tamanho_ficheiro){
        return new SmartCamera(id, "desligado",potencia, preco_instalacao, res, tamanho_ficheiro);
    }
}
