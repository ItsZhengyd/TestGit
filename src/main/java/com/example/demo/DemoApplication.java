package com.example.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		new DemoApplication().envTest();
	}

	public void envTest() {
		this.poll(5, TimeUnit.SECONDS);
	}

	/**
	 * 轮询方法
	 *
	 * @param timeout 时间
	 * @param unit    时间单位
	 * @return
	 */
	public synchronized Object poll(long timeout, TimeUnit unit) {
		while (true) {
			try {
				unit.timedWait(this, timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Console.log("{}test", DateUtil.now());
		}
	}

}
