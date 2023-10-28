package com.example.yu.framework.servlet;

import com.example.yu.framework.bean.Data;
import com.example.yu.framework.bean.Handler;
import com.example.yu.framework.bean.Param;
import com.example.yu.framework.bean.View;
import com.example.yu.framework.helper.BeanHelper;
import com.example.yu.framework.helper.ConfigHelper;
import com.example.yu.framework.helper.ControllerHelper;
import com.example.yu.framework.init.ProgramInit;
import com.example.yu.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * request forwarder
 * <p>
 * urlPatterns = "/*": This indicates that the servlet should handle all requests to the application.
 * <p>
 * loadOnStartup = 0: This attribute specifies the order in which the servlet should be loaded.
 * A value of 0 indicates that the servlet should be loaded on the application's startup.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 初始化相关 Helper 类
        ProgramInit.doInit();
        // 获取 ServletContext 对象（用于注册 Servlet）
//        ServletContext servletContext = servletConfig.getServletContext();
        // 注册处理 JSP 的 Servlet
//        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
//        // 注册处理静态资源的默认 Servlet
//        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
//        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    /**
     * Receives standard HTTP requests from the public service method and dispatches them to the doXXX methods defined in this class.
     *
     * 1、从 HttpServletRequest 中获取请求方法和请求路径
     * 2、获取 YuRequestMapping Handler处理器 TODO:适配器方式实现找处理器
     * 3、从 HttpServletRequest 中获取请求参数：
     *             Post - request.getParameterNames()
     *             Get - String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
     *             封装成入参Param
     * 4、调用 处理器
     * 5、根据返回值类型（Data、View）进行处理
     *
     *
     *
     * @param request   the {@link HttpServletRequest} object that
     *                  contains the request the client made of
     *                  the servlet
     *
     * @param response  the {@link HttpServletResponse} object that
     *                  contains the response the servlet returns
     *                  to the client
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler!=null){
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            //get and create request Params
            Map<String, Object> paramMap = new HashMap<String, Object>();

            //POST?
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }

            //GET?
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length==2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);

            Method actionMethod = handler.getYuAutowiredMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

            // 处理 Action 方法返回值
            if (result instanceof Data) {
                //
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

//            if (result instanceof View) {
//                //
//                View view = (View) result;
//                String path = view.getPath();
//                if (StringUtil.isNotEmpty(path)) {
//                    if (path.startsWith("/")) {
//                        response.sendRedirect(request.getContextPath() + path);
//                    } else {
//                        Map<String, Object> model = view.getModel();
//                        for (Map.Entry<String, Object> entry : model.entrySet()) {
//                            request.setAttribute(entry.getKey(), entry.getValue());
//                        }
//                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
//                    }
//                }
//            }

        }
    }
}
