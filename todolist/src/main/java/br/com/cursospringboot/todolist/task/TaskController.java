package br.com.cursospringboot.todolist.task;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursospringboot.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;




@RestController
@RequestMapping("/tasks")
public class TaskController {
  
  @Autowired
  private ItaskRepository repository;


  @PostMapping("/")  
  public ResponseEntity<Object> create(@RequestBody TaskModel task, HttpServletRequest request) {
    System.out.println("user_id: " + request.getAttribute("user_id"));

    // pegar o user_id
    var user_id = request.getAttribute("user_id");
    task.setUserId((UUID) user_id);

    // Validação de data
    if(!this.validateDateInTask(task.getStartDate(), task.getEndDate())){
      System.out.println("Data de inicio invalida");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data(s) invalida");
    }

    TaskModel taskCreated = this.repository.save(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);

  }


  private boolean validateDateInTask(LocalDate startDate, LocalDate endDate) {
      var today = LocalDate.now();

      if (today.isAfter(startDate)) {System.out.println("today: " + today + " < startDate: + "  + startDate); return false;}
      if (today.isAfter(endDate)) { System.out.println("today: " + today + " < endDate: + "  + endDate); return false;}
      if (startDate.isAfter(endDate)){ System.out.println("startDate: " + startDate + " > endDate: " + endDate); return false;}

      return true;
  }


  @GetMapping("/")  
  public ResponseEntity<Object> list(HttpServletRequest request) {

    var user_id = request.getAttribute("user_id");

    var tasks = this.repository.findByUserId((UUID) user_id);

    if (tasks.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma tarefa encontrada");
    }

    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@RequestBody TaskModel taskModel, HttpServletRequest request,@PathVariable UUID id) {

    var taskFound = this.repository.findById(id).orElse(null);

    if (taskFound == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa nao encontrada");
    }

    var userId = request.getAttribute("user_id");
    if(!taskFound.getUserId().equals(userId)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tarefa nao pertence ao usuario");
    }

    Utils.copyNonNullProperties(taskModel, taskFound);
    TaskModel taskUpdated = this.repository.save(taskFound);

    return ResponseEntity.ok().body(taskUpdated);
  }
}
