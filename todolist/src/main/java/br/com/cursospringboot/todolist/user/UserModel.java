package br.com.cursospringboot.todolist.user;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "tb_users")
@Data // Coloca os gatters e settes
public class UserModel {

  @Id
  @GeneratedValue(generator = "UUID")
  private  UUID id;

  @Column(unique = true)
  private  String username;
  private  String name;

  @Column(unique = true)
  private  String email;
  private  String password;


  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDate createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDate updatedAt;

}
