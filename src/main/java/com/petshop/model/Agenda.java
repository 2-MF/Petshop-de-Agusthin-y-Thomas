package com.petshop.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendas", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"fecha", "hora", "banador_id"}, 
                           name = "uk_agenda_banador_horario"),
           @UniqueConstraint(columnNames = {"fecha", "hora", "mascota_id"}, 
                           name = "uk_agenda_mascota_horario")
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoServicio servicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAgenda estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banador_id", nullable = false)
    private Funcionario banador;

    private String observaciones;

    @PrePersist
    protected void onCreate() {
        if (estado == null) {
            estado = EstadoAgenda.PENDIENTE;
        }
    }
}
