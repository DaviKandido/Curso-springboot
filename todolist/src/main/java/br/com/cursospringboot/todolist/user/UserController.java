package br.com.cursospringboot.todolist.user;

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
  public void create(@RequestBody UserModel entity) {
      System.out.println(entity.getUsername());
  }
  
  
}
