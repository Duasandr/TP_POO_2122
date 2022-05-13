package com.grupo.house;

import com.grupo.device.SmartDevice;
import exceptions.DispositivoNaoExisteException;
import exceptions.LinhaFormatadaInvalidaException;

import java.io.Serializable;
import java.util.*;

public class Divisao implements Serializable {
    //Variáveis de instância
    private String nome;
    private Map<String,SmartDevice> aparelhos;

    //Construtores

    /**
     * Construtor vazio
     */
    public Divisao(){
        this.aparelhos = new HashMap<>();
    }

    /**
     * Construtor por parâmetros.
     * @param nome Nome da divisão.
     * @param aparelhos Aparelhos presentes na divisão.
     */
    public Divisao(String nome , Collection<SmartDevice> aparelhos){
        this();
        this.nome = nome;
        this.setAparelhos(aparelhos);
    }

    /**
     * Construtor por cópia.
     * @param divisao Divisão a copiar.
     */
    public Divisao(Divisao divisao){
        this(divisao.nome, divisao.aparelhos.values());
    }

    //Métodos de instância

    //Setters
    /**
     * Define o nome da divisão.
     * @param nome Nome da divisão.
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Define os aparelhos presentes na divisão.
     *
     * @param aparelhos Aparelhos a inserir.
     */
    private void setAparelhos(Collection<SmartDevice> aparelhos){
        if(aparelhos != null) {
            HashMap<String, SmartDevice> clone = new HashMap<>(aparelhos.size());

            for (SmartDevice aparelho : aparelhos) {
                clone.put(aparelho.getIdFabricante(), aparelho.clone());
            }
            this.aparelhos = clone;
        }
    }

    //Getters

    /**
     * Devolve o nome da divisão.
     * @return Nome da divisão.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Devolve um conjunto com os aparelhos da divisão.
     * @return Conjunto de aparelhos.
     */
    public Set<SmartDevice> getAparelhos(){
        Set<SmartDevice> copia = new HashSet<>(this.aparelhos.size());
        for (SmartDevice aparelho : this.aparelhos.values()) {
            copia.add(aparelho.clone());
        }
        return copia;
    }

    /**
     * Devolve o consuma de energia da divisão.
     *
     * @return Consumo de energia.
     */
    public double getConsumoEnergia(){
        double consumo = 0.0;
        for (SmartDevice device : this.aparelhos.values()) {
            consumo += device.getConsumoEnergia();
        }
        return consumo;
    }

    public int getNumeroDispositivos(){
        return this.aparelhos.size();
    }

    //Métodos auxiliares

    /**
     * Verifica se um aparelho existe na divisão.
     *
     * @param id_dispositivo Identificador do aparelho.
     * @return true - false
     */
    public boolean contem(String id_dispositivo){
        return this.aparelhos.containsKey(id_dispositivo);
    }

    //Métodos de instância

    /**
     * Altera todos os estados de todos os dispositivos da divisão.
     * @param novo_estado Novo estado a atribuir aos dispositivos.
     */
    public void alteraEstado(SmartDevice.Estado novo_estado){
        for (SmartDevice aparelho : this.aparelhos.values()) {
            aparelho.setEstado(novo_estado);
        }
    }

    /**
     * Altera o estado de um dispositivo em específico.
     * @param id_dispositivo Identificador do dispositivo.
     * @param novo_estado Novo estado a atribuir ao dispositivo.
     * @throws DispositivoNaoExisteException Acontece quando não existe um dispositivo.
     */
    public void alteraEstado(String id_dispositivo , SmartDevice.Estado novo_estado) throws DispositivoNaoExisteException {
        if(contem(id_dispositivo)){
            this.aparelhos.get(id_dispositivo).setEstado(novo_estado);
        }else{
            throw new DispositivoNaoExisteException();
        }
    }

    //Métodos de classe

    /**
     * Transforma uma linha de texto formatada numa nova divisão.
     * @param str String formatada
     * @return Divisao nova
     * @throws LinhaFormatadaInvalidaException Acontece quando a linha de texto está mal formatada.
     */
    public static Divisao parse(String str) throws LinhaFormatadaInvalidaException {
        String[] tokens = str.split("\\[");
        String[] str_dispositivos = tokens[1].split(" ");
        Divisao divisao = new Divisao();

        if(tokens.length == 2) {
            Set<SmartDevice> dispositivos = new HashSet<>(str_dispositivos.length);

            for (String disp : str_dispositivos) {
                dispositivos.add(SmartDevice.parse(disp));
            }

            divisao.setNome(tokens[0]);
            divisao.setAparelhos(dispositivos);
        }else{
            throw new LinhaFormatadaInvalidaException(str);
        }

        return divisao;
    }

    /**
     * Devolve uma cópia da divisão.
     *
     * @return Cópia da divisão.
     */
    @Override
    public Divisao clone(){
        return new Divisao(this);
    }

    /**
     * Representação textual da divisão.
     * @return String.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Divisao{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", aparelhos=").append(aparelhos);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Testa se uma divisão é igual a um objeto.
     *
     * @param o Objeto a comparar.
     * @return true - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisao divisao = (Divisao) o;
        return Objects.equals(nome, divisao.nome) &&
                Objects.equals(aparelhos, divisao.aparelhos);
    }

    /**
     * Devolve um indíce através de uma função hash.
     *
     * @return Índice.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
