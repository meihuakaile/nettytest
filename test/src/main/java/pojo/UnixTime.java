package pojo;

import lombok.Data;

import java.util.Date;

/**
 * UnixTime
 *
 * @author chenliclchen
 * @date 17-10-25 下午3:49
 */
@Data
public class UnixTime {
    private int value;

    public UnixTime(){
        this((int)(System.currentTimeMillis() / 1000L + 2208988800L) );
    }
    public UnixTime(int value){
        this.value = value;
    }
    @Override
    public String toString(){
        return new Date((value - 2208988800L) * 1000L).toString();
    }
}
