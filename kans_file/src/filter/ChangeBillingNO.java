package filter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import common.util.StringUtils;
import controller.ScheduleController;
import service.billing.IBillingService;
import service.billing.impl.BillingService;

public class ChangeBillingNO implements ServletContextListener {

	IBillingService billingService;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	// 启动时改变流水号的最大值
	public void contextInitialized(ServletContextEvent arg0) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(arg0.getServletContext());
		billingService = (IBillingService) webApplicationContext.getBean("billingService");
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String max = billingService.getLastBillingNO(date);
		if (StringUtils.isNotEmpty(max)) {
			ScheduleController.i = Integer.parseInt(max.substring(8));
		}
	}

}
