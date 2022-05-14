package com.grupo.house;

import com.grupo.device.SmartDevice;
import com.grupo.exceptions.*;

import java.io.Serializable;
import java.util.*;


public class Casa implements Serializable {
    //Variáveis de instância

    private String morada;
    private String proprietario;
    private String fornecedor;
    private int nif_proprietario;
    private Map<String , Divisao> divisoes;
    private long ultima_fatura;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Casa(){
    }

    /**
     * Construtor por parâmetros
     * @param proprietario Nome do proprietário.
     * @param nif_proprietario Número de identificação fiscal do proprietário.
     * @param divisoes Divisões da casa.
     */
    public Casa(String proprietario,String morada , int nif_proprietario , Collection<Divisao> divisoes , String fornecedor , long fatura){
        this.proprietario = proprietario;
        this.nif_proprietario = nif_proprietario;
        this.fornecedor = fornecedor;
        this.morada = morada;
        this.divisoes = new HashMap<>();
        this.ultima_fatura = fatura;
        this.setDivisoes(divisoes);
    }

    public Casa(Casa casa){
        this(casa.proprietario , casa.morada, casa.nif_proprietario, casa.divisoes.values(), casa.fornecedor , casa.ultima_fatura);
    }

    //Métodos de instância

    //Setters

    /**
     * Define a morada da casa.
     * @param morada Morada da casa.
     */
    public void setMorada(String morada) {
        this.morada = morada;
    }

    /**
     * Define o nome do proprietário.
     * @param proprietario Nome.
     */
    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Define o fornecedor de energia da casa.
     * @param fornecedor Nome do fornecedor de energia.
     */
    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    /**
     * Define o número de identificação fiscal.
     * @param nif_proprietario Nif do proprietário.
     */
    public void setNifProprietario(int nif_proprietario) {
        this.nif_proprietario = nif_proprietario;
    }

    /**
     * Define as divisões da casa.
     * @param divisoes Divisões a copiar.
     */
    public void setDivisoes(Collection<Divisao> divisoes) {
        if(divisoes != null) {
            HashMap<String, Divisao> copia = new HashMap<>(divisoes.size());

            for (Divisao divisao : divisoes) {
                copia.put(divisao.getNome(), divisao.clone());
            }
            this.divisoes = copia;
        }
    }

    //Getters

    /**
     * Devolve a morada da casa.
     * @return Morada da casa.
     */
    public String getMorada() {
        return morada;
    }

    /**
     * Devolve o nome do proprietário da casa.
     *
     * @return Nome do proprietário.
     */
    public String getProprietario(){
        return this.proprietario;
    }

    /**
     * Devolve o número de identificação fiscal do proprietário.
     *
     * @return NIF do proprietário.
     */
    public int getNifProprietario() {
        return this.nif_proprietario;
    }

    /**
     * Devolve uma cópia das divisões da casa.
     *
     * @return Conjunto que contem as divisões da casa.
     */
    public Set<Divisao> getDivisoes(){
        Set<Divisao> copia = new HashSet<>(this.divisoes.size());
        for (Divisao divisao : this.divisoes.values()) {
            copia.add(divisao.clone());
        }
        return copia;
    }

    /**
     * Devolve o número de dispositivos na casa.
     * @return Número de dispositivos
     */
    public int getNumeroDispositivos(){
        int total = 0;
        for (Divisao divisao : this.divisoes.values()) {
            total += divisao.getNumeroDispositivos();
        }
        return total;
    }

    /**
     * Devolve o consumo de uma divisão em watts.
     * @return Energia gasta em watts.
     */
    public double getConsumoEnergia(){
        double consumo = 0.0;
        for (Divisao divisao : this.divisoes.values()) {
            consumo += divisao.getConsumoEnergia();
        }
        return consumo;
    }

    /**
     * Devolve o nome do fornecedor de energia.
     * @return Fornecedor de energia.
     */
    public String getFornecedor() {
        return this.fornecedor;
    }

    //Métodos auxiliares

    /**
     * testa se uma divisão existe na casa.
     *
     * @param divisao Nome da divisão.
     * @return true - false
     */
    private boolean existeDivisao(String divisao){
        return this.divisoes.containsKey(divisao);
    }

    /**
     * Devolve o nome da divisão onde se encontra o aparelho.
     *
     * @param id_dispositivo Identificador do aparelho.
     * @return  Divisão ou null caso não exista o aparelho.
     */
    public String ondeEsta(String id_dispositivo) throws DispositivoNaoExisteException {
        boolean found = false;
        Divisao divisao = null;

        Iterator<Divisao> i = this.divisoes.values().iterator();
        while(i.hasNext() && !found){
            divisao = i.next();
            found = divisao.contem(id_dispositivo);
        }

        if (divisao == null){
            throw new DispositivoNaoExisteException(id_dispositivo);
        }

        return divisao.getNome();
    }

    //Métodos de instância

    /**
     * Altera todos os estados de todos os dispositivos da divisão.
     * @param novo_estado Novo estado a atribuir aos dispositivos.
     */
    public void alteraEstado(SmartDevice.Estado novo_estado){
        for (Divisao divisao : this.divisoes.values()) {
            divisao.alteraEstado(novo_estado);
        }
    }

    /**
     * Altera todos os estados de todos os dispositivos da divisão.
     * @param novo_estado Novo estado a atribuir aos dispositivos.
     */
    public void alteraEstadoDivisao(String divisao , SmartDevice.Estado novo_estado) throws DivisaoNaoExisteException {
        if(this.divisoes.containsKey(divisao)){
            this.divisoes.get(divisao).alteraEstado(novo_estado);
        }else{
            throw new DivisaoNaoExisteException(divisao);
        }
    }

    /**
     * Altera o estado de um dispositivo em específico.
     * @param id_dispositivo Identificador do dispositivo.
     * @param novo_estado Novo estado a atribuir ao dispositivo.
     * @throws DispositivoNaoExisteException Acontece quando não existe um dispositivo.
     */
    public void alteraEstado(String id_dispositivo , SmartDevice.Estado novo_estado) throws DispositivoNaoExisteException {
        String local = ondeEsta(id_dispositivo);
        if(local != null){
            this.divisoes.get(local).alteraEstado(id_dispositivo,novo_estado);
        }
    }

    /**
     * Guarda o identificador da fatura emitida..
     */
    public void guardaFatura(long id_fatura){
        this.ultima_fatura=id_fatura;
    }

    /**
     * Identificador da última fatura emitida.
     * @return Identificador da última fatura.
     */
    public long ultimaFatura(){
        return ultima_fatura;
    }

    /**
     * Remove um dispositivo da divisão da casa.
     * @param id_dispositivo String
     * @param divisao String
     * @return SmartDevice removido
     * @throws DispositivoNaoExisteException Quando não existe
     * @throws DivisaoNaoExisteException Quando não existe
     */
    public SmartDevice removeDispositivo(String id_dispositivo , String divisao) throws DispositivoNaoExisteException, DivisaoNaoExisteException {
        SmartDevice dev;
        if(this.divisoes.containsKey(divisao)){
            dev = this.divisoes.get(divisao).removeDispositivo(id_dispositivo);
        }else{
            throw new DivisaoNaoExisteException(divisao);
        }
        return dev;
    }

    public void adicionaDispositivo(String div , SmartDevice device) throws DispositivoNaoExisteException {
        if(this.divisoes.containsKey(div)){
            this.divisoes.get(div).adicionaDispositivo(device);
        }else{
            throw new DispositivoNaoExisteException(div);
        }
    }

    //Métodos de Object

    /**
     * Testa se uma casa é igual a um objeto.
     * @param o Objeto a comparar.
     * @return true - false
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return this.nif_proprietario == casa.nif_proprietario &&
                this.morada.equals(casa.morada) &&
                this.proprietario.equals(casa.proprietario) &&
                this.divisoes.equals(casa.divisoes);
    }

    /**
     * Devolve uma cópia da casa.
     * @return Cópia da casa.
     */
    @Override
    public Casa clone(){
        return new Casa(this);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Casa.class.getSimpleName() + "[", "]")
                .add("morada='" + morada + "'")
                .add("proprietario='" + proprietario + "'")
                .add("fornecedor='" + fornecedor + "'")
                .add("nif_proprietario=" + nif_proprietario)
                .add("divisoes=" + divisoes)
                .add("id_faturas=" + ultima_fatura)
                .toString();
    }

    /**
     * Representação textual de uma casa.
     * @return String.
     */



    @Override
    public int hashCode() {
        return Objects.hash(proprietario, nif_proprietario , morada);
    }

    public static Casa parse(String str) throws LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        String[] tokens = str.split("\\{");
        String[] tokens_casa = tokens[0].split(";");
        String[] tokens_divisoes = tokens[1].split("\\|");
        Casa casa = new Casa();

        if(tokens.length == 4) {
            casa.morada = tokens_casa[0];
            casa.proprietario = tokens_casa[1];
            casa.fornecedor = tokens_casa[2];
            casa.nif_proprietario = Integer.parseInt(tokens_casa[3]);

            Set<Divisao> divisoes = new HashSet<>();

            for (String divisao : tokens_divisoes) {
                divisoes.add(Divisao.parse(divisao));
            }

            casa.setDivisoes(divisoes);
        }else{
            throw new LinhaFormatadaInvalidaException(str);
        }

        return casa;
    }
}
