package interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {
 
  public LoggedInterceptor() {
  }

  @AroundInvoke
  public Object logMethodEntry(InvocationContext invocationContext)
          throws Exception {
      System.out.println("Entering method: "
              + invocationContext.getMethod().getName() + " in class "
              + invocationContext.getMethod().getDeclaringClass().getName());

      return invocationContext.proceed();
  }
  
}
