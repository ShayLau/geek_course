package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.service.IAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 账户服务
 *
 * @author ShayLau
 * @date 2022/3/13 10:04 PM
 */
@RequestMapping("/account")
@RestController
public class AccountController {

    @Resource
    private IAccountService accountService;

    @GetMapping("initAmountData")
    public void initAmountData() {
        accountService.initAmountData();
    }

    @PostMapping("transferAmount")
    public void transferAmount(double money) {
        accountService.transferAmount(money);

    }
}

