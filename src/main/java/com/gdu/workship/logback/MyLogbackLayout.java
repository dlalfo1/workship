package com.gdu.workship.logback;

import java.text.SimpleDateFormat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

public class MyLogbackLayout extends LayoutBase<ILoggingEvent> {

	@Override
	public String doLayout(ILoggingEvent event) {
		
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("[");
		sbuf.append(new SimpleDateFormat("yy/MM/dd_HH:mm:ss").format(event.getTimeStamp()));
		sbuf.append("][");
    sbuf.append(String.format("%-5s", event.getLevel())); // Log Level
    sbuf.append("][");
    sbuf.append(event.getLoggerName());                   // Logger 이름
    sbuf.append("]");
    if(event.getLoggerName().equals("jdbc.sqlonly")) {    // SQL문은
    	sbuf.append(CoreConstants.LINE_SEPARATOR + "    "); // 줄 바꿈 후에 출력한다.
    } else {
    	sbuf.append(" - ");
    }
    sbuf.append(event.getFormattedMessage());             // Log 내용
    sbuf.append(CoreConstants.LINE_SEPARATOR);            // 줄 바꿈
    return sbuf.toString();

	}
	
}
