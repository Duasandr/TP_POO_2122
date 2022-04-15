package com.grupo.power;

import com.grupo.house.Casa;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;

public class Fatura {
    //Variáveis de instância

    private long id;
    private String cliente;
    private String morada;
    private int nif_cliente;
    private String fornecedor;
    private double total_consumo;
    private double preco_kw;
    private double imposto;
    private double desconto;
    private double total_a_pagar;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    //Variáveis de classe

    private static long proximo_id = 0;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Fatura(){
        this.id = proximo_id++;
    }

    /**
     * Construtor por parâmetros.
     * @param cliente
     * @param morada
     * @param nif_cliente
     * @param fornecedor
     * @param total_consumo
     * @param preco_kw
     * @param imposto
     * @param desconto
     * @param total_a_pagar
     */
    public Fatura(String cliente,String morada,int nif_cliente,String fornecedor,double total_consumo,double preco_kw,double imposto,double desconto,double total_a_pagar,LocalDateTime inicio , LocalDateTime fim){
        this.id = proximo_id++;
        this.cliente = cliente;
        this.morada = morada;
        this.nif_cliente = nif_cliente;
        this.fornecedor = fornecedor;
        this.total_consumo = total_consumo;
        this.preco_kw = preco_kw;
        this.imposto = imposto;
        this.desconto = desconto;
        this.total_a_pagar = total_a_pagar;
        this.inicio = inicio;
        this.fim = fim;
    }

    /**
     * Construtor por parâmetors.
     * @param casa
     * @param inicio
     * @param fim
     */
    public Fatura(Casa casa , FornecedorEnergia fornecedor ,LocalDateTime inicio , LocalDateTime fim){
        this.id = proximo_id++;
        this.cliente = casa.getProprietario();
        this.morada = casa.getMorada();
        this.nif_cliente = casa.getNifProprietario();
        this.fornecedor = fornecedor.getNome();
        this.total_consumo = casa.getConsumoEnergia();
        this.preco_kw = fornecedor.getValorBase();
        this.imposto = fornecedor.getImposto();
        this.imposto = fornecedor.getImposto();
        this.desconto = fornecedor.calculaDesconto(casa.getConsumoEnergia(), casa.getNumeroDispositivos());
        this.total_a_pagar = this.preco_kw * this.total_consumo * 0.001 * this.desconto;
        this.inicio = inicio;
        this.fim = fim;
        casa.guardaFatura(this.id);
        fornecedor.guardaFatura(this.id,total_a_pagar);
    }

    /**
     * Construtor por cópia.
     * @param fatura Fatura a copiar.
     */
    public Fatura(Fatura fatura){
        this.id = fatura.id;
        this.cliente = fatura.cliente;
        this.morada = fatura.morada;
        this.nif_cliente = fatura.nif_cliente;
        this.fornecedor = fatura.fornecedor;
        this.total_consumo = fatura.total_consumo;
        this.preco_kw = fatura.preco_kw;
        this.imposto = fatura.imposto;
        this.desconto = fatura.desconto;
        this.total_a_pagar = fatura.total_a_pagar;
        this.inicio = fatura.inicio;
        this.fim = fatura.fim;
    }

    public long getId() {
        return this.id;
    }

    public String getFornecedor() {
        return this.fornecedor;
    }

    public double getTotalAPagar() {
        return this.total_a_pagar;
    }

    public LocalDateTime getInicio() {
        return this.inicio;
    }

    public LocalDateTime getFim() {
        return this.fim;
    }

    public double getTotalConsumo() {
        return total_consumo;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fatura fatura = (Fatura) o;
        return this.id == fatura.id &&
                nif_cliente == fatura.nif_cliente &&
                Double.compare(fatura.total_consumo, total_consumo) == 0 &&
                Double.compare(fatura.preco_kw, preco_kw) == 0 &&
                Double.compare(fatura.imposto, imposto) == 0 &&
                Double.compare(fatura.desconto, desconto) == 0 &&
                Double.compare(fatura.total_a_pagar, total_a_pagar) == 0 &&
                Objects.equals(cliente, fatura.cliente) && Objects.equals(morada, fatura.morada) &&
                Objects.equals(fornecedor, fatura.fornecedor);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id,cliente, morada, nif_cliente, fornecedor, total_consumo, preco_kw, imposto, desconto, total_a_pagar);
    }

    /**
     *
     * @return
     */
    @Override
    public Fatura clone(){
        return new Fatura(this);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Fatura{");
        sb.append("id=").append(id);
        sb.append(", cliente='").append(cliente).append('\'');
        sb.append(", morada='").append(morada).append('\'');
        sb.append(", nif_cliente=").append(nif_cliente);
        sb.append(", fornecedor='").append(fornecedor).append('\'');
        sb.append(", total_consumo=").append(total_consumo);
        sb.append(", preco_kw=").append(preco_kw);
        sb.append(", imposto=").append(imposto);
        sb.append(", desconto=").append(desconto);
        sb.append(", total_a_pagar=").append(total_a_pagar);
        sb.append(", inicio=").append(inicio);
        sb.append(", fim=").append(fim);
        sb.append('}');
        return sb.toString();
    }
}
