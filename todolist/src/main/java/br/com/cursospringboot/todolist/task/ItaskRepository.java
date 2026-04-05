package br.com.cursospringboot.todolist.task;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  ItaskRepository extends JpaRepository<TaskModel, UUID> {
  List<TaskModel> findByUserId(UUID user_id);

  List<TaskModel> findByUserIdAndEndDateAfter(UUID user_id, LocalDate date);

  List<TaskModel> findByUserIdAndEndDateBefore(UUID user_id, LocalDate date);

  List<TaskModel> findByUserIdAndEndDateBetween(UUID user_id, LocalDate startDate, LocalDate endDate);

  TaskModel findByIdAndUserId(UUID id, UUID user_id);
}
