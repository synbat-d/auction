package kz.redmadbot.auction.exceptionHandling;

import org.springframework.stereotype.Component;

@Component
public class InfoMessage {
    private String info;

    public InfoMessage() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
