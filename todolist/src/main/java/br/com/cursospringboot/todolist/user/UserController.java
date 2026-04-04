package br.com.cursospringboot.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Modificadores
 *  public
 *  private
 *  protected
 */


@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository repository;

  /**
   * String (text)
   * Integer (int) numeros inteiros
   * Long (long) numeros inteiros
   * Short (short) numeros inteiros
   * Byte (byte) numeros inteiros
   * Double (double) numeros 0.0000
   * Float (float) numeros 0.00
   * Char (char) apenas um caracter A C
   * Date (date) data
   * Boolean (boolean) true ou false
   * Void
   */


  /**
   * Body
   * 
   */

  @PostMapping("/")
  public ResponseEntity create(@RequestBody UserModel entity) {
      UserModel user = this.repository.findByUsername(entity.getUsername());

      if (user != null) {
          System.out.println("User already exists");
          //return ResponseEntity.status(400).build();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
      }

      var hashedPassword = BCrypt.withDefaults()
                                  .hashToString(12, entity.getPassword()
                                  .toCharArray());

      entity.setPassword(hashedPassword);

      UserModel userCreated = this.repository.save(entity);
      return ResponseEntity.status(201).body(userCreated);
  }
  
  
}
