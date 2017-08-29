import com.cretin.domain.Product;

import java.util.LinkedHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class MyHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("cartmap", new LinkedHashMap<Product, Integer>());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
