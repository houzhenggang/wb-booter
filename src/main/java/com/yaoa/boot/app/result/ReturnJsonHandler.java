package com.yaoa.boot.app.result;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Json 视图动态过滤
 * Created by cjh on 2017/2/27.
 */
public class ReturnJsonHandler implements HandlerMethodReturnValueHandler{

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        // 如果有我们自定义的 Json 注解 就用我们这个Handler 来处理
       return returnType.getDeclaringClass() == Result.class || returnType.getMethodAnnotation(Json.class) != null;
    }
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        mavContainer.setRequestHandled(true);
        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annos = returnType.getMethodAnnotations();
        ResultJsonSerializer jsonSerializer = new ResultJsonSerializer();
        for (Annotation a : annos) {
            if (a instanceof Json) {
                Json Json = (Json) a;
                jsonSerializer.filter(Json.type(), Json.include(), Json.exclude());
            }
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String Json = jsonSerializer.writeValueAsString(returnValue);
        response.getWriter().write(Json);
    }
}

