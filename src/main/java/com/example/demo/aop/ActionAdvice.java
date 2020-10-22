package com.example.demo.aop;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ActionAdvice {
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Map handleException(HttpServletRequest request, Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 400);
        map.put("msg", ex.toString());
        return map;
    }

    @InitBinder
    public void handleConvert(WebDataBinder binder) {
        //把String类型的参数先trim再绑定
        binder.registerCustomEditor(String.class,
                new StringTrimmerEditor(true));
//        Date类型的参数会先格式化在绑定
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

//    把值绑定到Model中，使全局@RequestMapping可以获取到该值
// 在@RequestMapping的接口中使用@ModelAttribute("author")获取
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "xidfd");
    }
}
