package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class VooEscalas extends Voo{
   private LocalDateTime datahora;
   private static int proxNum = 3000;
   private int numVoo;
   
   private Duration dur;
   private double d;
   private long duracao;

   private Rota rota;

   private ArrayList<Rota> rotas;
   private Status status;

   public VooEscalas(LocalDateTime dh) {
      super(dh);
      this.datahora = dh;
      this.status = Status.CONFIRMADO;
      rotas = new ArrayList<>(); 
      numVoo = proxNum;
      proxNum+= 1;
   }

   public void adicionaRota(Rota rota){
      this.rota = rota;
      rotas.add(rota);
   }

   public void setStatus(Status novoStatu){
     this.status = novoStatu;
   }

   @Override
   public Rota getRota() {
      return rota;
   }
   
   public ArrayList<Rota> mostrarRotas(){
      return rotas;
   }

   @Override
   public LocalDateTime getDatahora(){
      return datahora;
   }
 
   @Override
   public Duration getDuration() {
      double dT = 0;
      for(Rota rot : rotas){
         d = (Geo.distancia(rot.getOrigem().getLocal(), rot.getDestino().getLocal())/805)*60+30;
         dT += d;
         duracao = (long) dT;
      }
      dur = Duration.ofMinutes(duracao);
      return dur;
   }
   
   public String mostrarVoo() {
      DateTimeFormatter formatter;
      String texto;
      LocalDateTime parseDateTime;
      formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		texto = datahora.format(formatter);
		parseDateTime = LocalDateTime.parse(texto, formatter);

     String r = "Voo com Escalas "+ numVoo +":\n\n";
     
     r += "Detalhes do Voo:\n\n";
     
     r+= "Status: " + status + "\n";
     
     for(Rota rot : rotas){
        d = (Geo.distancia(rot.getOrigem().getLocal(), rot.getDestino().getLocal())/805)*60+30;
        duracao = (long) d;
        r+=  rot.toString();
        r+= "Duracao da Escala: "+Duration.ofMinutes(duracao)+"\n"; 
     }
     
     r+="Data do Voo: "+parseDateTime.format(formatter)+"\n";
     
     r+="Duracao Total: "+getDuration();

     r+= "\n-------------------------------------------------\n";
     
     return r;
   }
}
