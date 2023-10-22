package com.example.yu.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Request {
    //@YuRequestMapping("post:/customer_create")
    private String requestMethod;
    private String requestPath;
}
