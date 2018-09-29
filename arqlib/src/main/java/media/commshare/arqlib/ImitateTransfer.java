package media.commshare.arqlib;

/**
 * Created by linSir on 16/10/5.用来模拟传输过程(udp)
 */
public class ImitateTransfer {

    /**
     * 用来模拟传输过程中的各种情况
     * 1.无差错情况（60%） 状态码：100
     * 2.出现差错
     * ①.接收到了，做校验时发现有错（20%） 状态码：101
     * ②.传输过程中丢失数据（20%） 状态码：102
     * 3.确认丢失和确认迟到
     * ①.确认丢失（20%）状态码：103
     * ②.确认迟到（20%）状态码：104
     */

    public static String transfer_send() {//发送数据的三种情况

        int x = (int) (Math.random() * 10);//0-9
        switch (x) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return "100";
            case 6:
            case 7:
                return "101";
            case 8:
            case 9:
                return "102";
        }
        return "100";
    }


    public static String transfer_confirm() {//发送确认命令的三种情况

        int x = (int) (Math.random() * 10);//0-9
        switch (x) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return "100";
            case 6:
            case 7:
                return "103";
            case 8:
            case 9:
                return "104";
        }
        return "100";
    }
}

/*
作者：关玮琳linSir
链接：https://www.jianshu.com/p/9f8ec5667fa6
來源：简书
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。*/
