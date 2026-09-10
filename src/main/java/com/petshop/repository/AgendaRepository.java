package com.petshop.repository;

import com.petshop.model.Agenda;
import com.petshop.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    List<Agenda> findByBanador(Funcionario banador);

    List<Agenda> findByBanadorIdAndFecha(Long banadorId, LocalDate fecha);

    List<Agenda> findByFecha(LocalDate fecha);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.fecha = :fecha")
    long countByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.fecha = :fecha AND a.estado != 'CANCELADO'")
    long countActivosByFecha(@Param("fecha") LocalDate fecha);
}
