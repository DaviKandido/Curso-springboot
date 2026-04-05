package br.com.cursospringboot.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.cursospringboot.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository repository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {


      var servletPath = request.getServletPath();
      if (servletPath.startsWith("/tasks")) {
          
          //pegar a autenticação (Usuario e senha)
          var authorization = request.getHeader("Authorization");
          // Validar Usuário
          System.out.println("Authorization: ");
          var authEncoded = authorization.substring("basic ".length()).trim(); // trim: remove espaco em branco
          
          // Validar Senha
          byte[] authDecode = Base64.getDecoder().decode(authEncoded);
          var authString = new String(authDecode);

          String[] credentiasl = authString.split(":");
          String username = credentiasl[0];
          String password = credentiasl[1];

          var user = repository.findByUsername(username);

          if (user == null || !BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
              System.out.println("User sem autorização");
              response.sendError(401, "User sem autorização");
          }else {
              System.out.println("User com autorização");

              request.setAttribute("user_id", user.getId());
              filterChain.doFilter(request, response);
          }

      }else{
          System.out.println("Autenticação Pulada");
          filterChain.doFilter(request, response);
      }
    }
  
}
