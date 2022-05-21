package com.grupo.controlador;

import com.grupo.consumer.DesligaDispositivo;
import com.grupo.consumer.LigaDispositivo;
import com.grupo.device.SmartBulb;
import com.grupo.device.SmartCamera;
import com.grupo.device.SmartDevice;
import com.grupo.device.SmartSpeaker;
import com.grupo.exceptions.*;
import com.grupo.generator.GeradorAleatorio;
import com.grupo.house.Casa;
import com.grupo.house.Divisao;
import com.grupo.model.Modelo;
import com.grupo.power.Fatura;
import com.grupo.power.FornecedorEnergia;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public
class Controlador {
    private Modelo modelo;

    public
    Controlador ( Modelo modelo ) {
        this.modelo = modelo;
    }

    private static
    List < String > lerFicheiro ( String path ) throws IOException {
        return Files.readAllLines ( Paths.get ( path ) , StandardCharsets.UTF_8 );
    }

    private static
    Modelo carregaEstado ( String path ) throws IOException, ClassNotFoundException {
        FileInputStream   fis = new FileInputStream ( path );
        ObjectInputStream ois = new ObjectInputStream ( fis );
        Modelo            s   = ( Modelo ) ois.readObject ( );
        ois.close ( );
        return s;
    }

    private
    void carregaFornecedores ( String path ) throws IOException {
        this.modelo.setFornecedores ( lerFicheiro ( path ) );
    }

    private
    void carregaCasas ( String path ) throws IOException, LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        this.modelo.setCasas ( lerFicheiro ( path ) );
    }

    public
    void guardaEstado ( String path ) throws IOException {
        FileOutputStream   fos = new FileOutputStream ( path );
        ObjectOutputStream oos = new ObjectOutputStream ( fos );
        oos.writeObject ( this.modelo );
        oos.flush ( );
        oos.close ( );
    }

    private
    void carregaFicheiroBinario ( String path ) throws IOException, ClassNotFoundException {
        this.modelo = carregaEstado ( path );
    }

    private
    void carregaDadosFicheiroTexto ( String[] paths ) throws IOException, LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        carregaFornecedores ( paths[ 2 ] );
        carregaCasas ( paths[ 3 ] );
        this.modelo.verificaContrato ( );
    }

    private
    void geraDadosAleatorios ( GeradorAleatorio gerador ) {
        this.modelo.setFornecedores ( gerador.getFornecedores ( ) );
        this.modelo.setCasas ( gerador.getCasas ( ) );
        this.modelo.verificaContrato ( );
    }


    public
    String status ( String nome , Iterator < ? > list ) {
        StringBuilder sb = new StringBuilder ( );
        sb.append ( nome ).append ( ':' ).append ( '\n' );
        while ( list.hasNext ( ) ) {
            sb.append ( "\t" ).append ( list.next ( ).toString ( ) ).append ( "\n" );
        }
        sb.append ( "}\n" );
        return sb.toString ( );
    }

    private
    String status ( ) {
        return status ( "Fornecedores" , this.modelo.getFornecedor ( ) ) +
                status ( "Casas" , this.modelo.getCasa ( ) ) +
                status ( "Faturas" , this.modelo.getFatura ( ) ) +
                LocalDateTime.now ( ).plusDays ( this.modelo.getDias ( ) );
    }

    public
    void addHandler ( String[] args ) throws OpcaoInvalidaException, CasaInexistenteException, SmartDeviceInvalidoException, LinhaFormatadaInvalidaException, TonalidadeInvalidaException, EstadoInvalidoException, DivisaoNaoExisteException {
        if ( args.length > 1 ) {
            switch ( args[ 1 ] ) {
                case "-h" -> {
                    this.modelo.adiciona ( Casa.parse ( args[ 2 ] ) );
                    this.modelo.verificaContrato ( );
                }
                case "-f" -> this.modelo.adiciona ( FornecedorEnergia.parse ( args[2] ) );
                case "-div" -> this.modelo.adiciona ( args[ 2 ] , Divisao.parse ( args[ 3 ] ) );
                case "-dev" -> this.modelo.adiciona ( args[ 2 ] , args[ 3 ] , SmartDevice.parse ( args[ 4 ].split ( ":" ) ) );
                default -> throw new OpcaoInvalidaException ( args[ 0 ] );
            }
        }
        else {
            throw new OpcaoInvalidaException ( "man para qualquer duvida" );
        }
    }

    public
    void loaderHandler ( String[] args ) throws IOException, ClassNotFoundException, LinhaFormatadaInvalidaException, OpcaoInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        if ( args.length > 1 ) {
            switch ( args[ 1 ] ) {
                case "-bin" -> carregaFicheiroBinario ( args[ 2 ] );
                case "-txt" -> carregaDadosFicheiroTexto ( args );
                case "-rand" -> geraDadosAleatorios ( new GeradorAleatorio ( ) );
                default -> throw new OpcaoInvalidaException ( args[ 0 ] );
            }
        }
        else {
            throw new OpcaoInvalidaException ( "man para qualquer duvida" );
        }
    }

    public
    String statusHandler ( String[] args ) throws OpcaoInvalidaException {
        if ( args.length > 1 ) {
            return switch ( args[ 1 ] ) {
                case "-all" -> status ( );
                case "-f" -> status ( "Fornecedores" , this.modelo.getFornecedor ( ) );
                case "-h" -> status ( "Casas" , this.modelo.getCasa ( ) );
                case "-b" -> status ( "Faturas" , this.modelo.getFatura ( ) );
                default -> throw new OpcaoInvalidaException ( args[ 0 ] );
            };
        }
        else {
            throw new OpcaoInvalidaException ( "man para qualquer duvida" );
        }
    }

    public
    void modifyHandler ( String[] args ) throws OpcaoInvalidaException, CasaInexistenteException, DivisaoNaoExisteException {
        if ( args.length > 1 ) {
            switch ( args[ 1 ] ) {
                case "-allon" -> this.modelo.foreachDispositivo ( new LigaDispositivo ( ) );
                case "-allof" -> this.modelo.foreachDispositivo ( new DesligaDispositivo ( ) );
                case "-divon" -> this.modelo.foreachDispositivo ( args[ 2 ] , args[ 3 ] , new LigaDispositivo ( ) );
                case "-divof" -> this.modelo.foreachDispositivo ( args[ 2 ] , args[ 3 ] , new DesligaDispositivo ( ) );
                default -> throw new OpcaoInvalidaException ( args[ 0 ] );
            }
        }
        else {
            throw new OpcaoInvalidaException ( "man para qualquer duvida" );
        }
    }

    public
    String mvpHandler ( String[] args ) throws OpcaoInvalidaException, DadosInsuficientesException {
        if ( args.length > 1 ) {
            if ( this.modelo.getDias ( ) > 0 ) {
                return switch ( args[ 1 ] ) {
                    case "-h" -> mvp ( this.modelo.getFatura ( ) );
                    case "-f" -> mvpForncedor ( this.modelo.getFornecedor ( ) );
                    default -> throw new OpcaoInvalidaException ( args[ 0 ] );
                };
            }
            else {
                throw new DadosInsuficientesException ( "Whoops" );
            }
        }
        else {
            throw new OpcaoInvalidaException ( "man para qualquer duvida" );
        }
    }

    public
    void devHandler ( String[] args ) throws OpcaoInvalidaException, CasaInexistenteException, DivisaoNaoExisteException, DispositivoNaoExisteException, TonalidadeInvalidaException, NaoEBulbException, NaoECamException, NaoESpeakException {
        if ( args.length > 3 ) {
            AbstractMap.SimpleEntry < String, SmartDevice > dev = this.modelo.removeDispositivo ( args[ 2 ] , args[ 3 ] );
            switch ( args[ 1 ] ) {
                case "-on" -> {
                    dev.getValue ( ).setEstado ( SmartDevice.Estado.LIGADO );
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-off" -> {
                    dev.getValue ( ).setEstado ( SmartDevice.Estado.DESLIGADO );
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-price" -> {
                    dev.getValue ( ).setPrecoInstalacao ( Double.parseDouble ( args[ 4 ] ) );
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-tone" -> {
                    if ( dev.getValue ( ) instanceof SmartBulb ) {
                        ( ( SmartBulb ) dev.getValue ( ) ).setTonalidade ( SmartBulb.parseTonalidade ( args[ 4 ] ) );
                    }
                    else {
                        throw new NaoEBulbException ( args[ 3 ] );
                    }
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-dim" -> {
                    if ( dev.getValue ( ) instanceof SmartBulb ) {
                        ( ( SmartBulb ) dev.getValue ( ) ).setDimensao ( Double.parseDouble ( args[ 4 ] ) );
                    }
                    else {
                        throw new NaoEBulbException ( args[ 3 ] );
                    }
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-res" -> {
                    if ( dev.getValue ( ) instanceof SmartCamera ) {
                        ( ( SmartCamera ) dev.getValue ( ) ).setResolucao ( Integer.parseInt ( args[ 4 ] ) );
                    }
                    else {
                        throw new NaoECamException ( args[ 3 ] );
                    }
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-file" -> {
                    if ( dev.getValue ( ) instanceof SmartCamera ) {
                        ( ( SmartCamera ) dev.getValue ( ) ).setTamanhoFicheiro ( Double.parseDouble ( args[ 4 ] ) );
                    }
                    else {
                        throw new NaoECamException ( args[ 3 ] );
                    }
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-vol" -> {
                    if ( dev.getValue ( ) instanceof SmartSpeaker ) {
                        ( ( SmartSpeaker ) dev.getValue ( ) ).setVolume ( Integer.parseInt ( args[ 4 ] ) );
                    }
                    else {
                        throw new NaoESpeakException ( args[ 3 ] );
                    }
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-max" -> {
                    if ( dev.getValue ( ) instanceof SmartSpeaker ) {
                        ( ( SmartSpeaker ) dev.getValue ( ) ).setVolumeMaximo ( Integer.parseInt ( args[ 4 ] ) );
                    }
                    else {
                        throw new NaoESpeakException ( args[ 3 ] );
                    }
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                case "-cha" -> {
                    if ( dev.getValue ( ) instanceof SmartSpeaker ) {
                        ( ( SmartSpeaker ) dev.getValue ( ) ).setCanal ( args[ 4 ] );
                    }
                    else {
                        throw new NaoESpeakException ( args[ 3 ] );
                    }
                    this.modelo.adiciona ( args[ 2 ] , dev.getKey ( ) , dev.getValue ( ) );
                }
                default -> throw new OpcaoInvalidaException ( args[ 0 ] );
            }
        }
    }

    public
    void avancaNoTempo ( long dias ) {
        LocalDateTime inicio = LocalDateTime.now ( ).plusDays ( this.modelo.getDias ( ) );
        this.modelo.atualizaDias ( dias );
        LocalDateTime fim = LocalDateTime.now ( ).plusDays ( this.modelo.getDias ( ) );
        this.modelo.emiteFaturas ( inicio , fim );
    }

    private
    String darOrdenacao ( LocalDateTime inicio , LocalDateTime fim ) {
        Map < Double, Set < String > > o = this.modelo.getFatura ( inicio , fim );
        return o.values ( ).toString ( );
    }

    public
    String darHandler ( String[] args ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "yyyy-MM-dd" );
        return darOrdenacao ( LocalDateTime.from ( LocalDate.parse ( args[ 1 ] , formatter ).atStartOfDay ( ) ) , LocalDateTime.from ( LocalDate.parse ( args[ 2 ] , formatter ).atStartOfDay ( ) ) );
    }

    public
    String billHandler ( String[] args ) {
        return bills ( args[ 1 ] , this.modelo.getFatura ( args[ 1 ] ) );
    }

    public
    void deloreanHandler ( String[] args ) {
        avancaNoTempo ( Long.parseLong ( args[ 1 ] ) );
    }

    public
    String mvp ( Iterator < Fatura > faturas ) {
        Fatura mvp         = faturas.next ( );
        double max_despesa = 0.0;

        while ( faturas.hasNext ( ) ) {
            Fatura fatura = faturas.next ( );

            if ( fatura.getData ( ).truncatedTo ( ChronoUnit.DAYS ).equals ( LocalDateTime.now ( ).plusDays ( this.modelo.getDias ( ) ).truncatedTo ( ChronoUnit.DAYS ) ) ) {
                double despesa = fatura.getTotalAPagar ( );

                if ( despesa > max_despesa ) {
                    max_despesa = despesa;
                    mvp         = fatura;
                }
            }
        }

        return "Casa: " + mvp.getMorada ( ) + "\nDespesa: " + max_despesa;
    }

    public
    String mvpForncedor ( Iterator < FornecedorEnergia > fornecedores ) {
        FornecedorEnergia mvp         = fornecedores.next ( );
        double            max_despesa = mvp.getFaturacao ( );

        while ( fornecedores.hasNext ( ) ) {
            FornecedorEnergia fornecedor = fornecedores.next ( );
            double            despesa    = fornecedor.getFaturacao ( );

            if ( despesa > max_despesa ) {
                max_despesa = despesa;
                mvp         = fornecedor;
            }
        }

        return "Fornecedor: " + mvp.getNome ( ) + "\nFaturação: " + max_despesa;
    }

    public
    String bills ( String nome , Iterator < Fatura > faturas ) {
        StringBuilder sb = new StringBuilder ( nome + "{\n" );

        while ( faturas.hasNext ( ) ) {
            sb.append ( faturas.next ( ).toString ( ) ).append ( "\n" );
        }
        sb.append ( "}" );

        return sb.toString ( );
    }
}
