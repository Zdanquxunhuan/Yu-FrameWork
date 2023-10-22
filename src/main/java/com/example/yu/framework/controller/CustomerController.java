package com.example.yu.framework.controller;

/**
 * 处理客户管理相关请求
 */
//@Controller
public class CustomerController {

    // @YuAutowired
    // private CustomerService customerService;


    /**
     * 进入 客户列表 界面
     */
    // @YuRequestMapping("get:/customer")
    // public View index(Param param) {
    // List<Customer> customerList = customerService.getCustomerList();
    // return new View("customer.jsp").addModel("customerList", customer-
    // List);
    // }

    /**
     * 显示客户基本信息
     */
    // @YuRequestMapping("get:/customer_show")
    // public View show(Param param) {
    // long id = param.getLong("id");
    // Customer customer = customerService.getCustomer(id);
    // return new View("customer_show.jsp").addModel("customer", customer);
    // }

    /**
     * 进入 创建客户 界面
     */
    //@YuRequestMapping("get:/customer_create")
    // public View create(Param param) {
    // return new View("customer_create.jsp");
    // }

    /**
     * 处理 创建客户 请求
     */
    // @YuRequestMapping("post:/customer_create")
    // public Data createSubmit(Param param) {
    // Map<String, Object> fieldMap = param.getMap();
    // boolean result = customerService.createCustomer(fieldMap);
    // return new Data(result);
    // }

    /**
     * 进入 编辑客户 界面
     */
    // @YuRequestMapping("get:/customer_edit")
    // public View edit(Param param) {
    // long id = param.getLong("id");
    // Customer customer = customerService.getCustomer(id);
    // return new View("customer_edit.jsp").addModel("customer", customer);
    // }

    /**
     * 处理 编辑客户 请求
     */
    // @YuRequestMapping("put:/customer_edit")
    // public Data editSubmit(Param param) {
    // long id = param.getLong("id");
    // Map<String, Object> fieldMap = param.getMap();
    // boolean result = customerService.updateCustomer(id, fieldMap);
    // return new Data(result);
    // }

    /**
     * 处理 删除客户 请求
     */
    // @YuRequestMapping("delete:/customer_edit")
    // public Data delete(Param param) {
    // long id = param.getLong("id");
    // boolean result = customerService.deleteCustomer(id);
    // return new Data(result);

}
