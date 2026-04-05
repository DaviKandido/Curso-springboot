package br.com.cursospringboot.todolist.task;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


  /**
   * ID
   * Usuário (ID_Usuário)
   * Descrição
   * Título
   * Data de Inicio
   * Data de Término
   * Prioridade
   * 
   */

@Entity
@Data
public class TaskModel {


  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private String description;

  @Column(length = 50)
  private String title;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;
  private String priority;

  @Column(name = "user_id")
  private UUID userId;


  @CreationTimestamp
  private LocalDate createdAt;

  @UpdateTimestamp
  private LocalDate updatedAt;


  public void setTitle(String title) throws Exception {
    if(title.length() > 50) {
      throw new Exception("Error: Title maior que 50 caracteres.");
    }
    this.title = title;
  }

}

