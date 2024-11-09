package com.flight.order.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lwwwwaaa
 * @since 2022/10/18
 */
@Component
public class SeatChange {
    public List<String> getSeat(List<String> list, Integer type) {
        int rule_f_x = 2; // 头等舱有多少行
        int rule_f_y = 4; // 头等舱有多少列

        int rule_y_x = 20; // 经济舱有多少行
        int rule_y_y = 6; // 经济舱有多少列

        if (type == 1) { // 经济舱
            String[] seat_number = new String[120]; // 座位号
            String[] prefix = new String[6]; // 座位前缀
            prefix[0] = "A";
            prefix[1] = "B";
            prefix[2] = "C";
            prefix[3] = "J";
            prefix[4] = "K";
            prefix[5] = "L";
            // 实例化矩阵
            Boolean flag = false;// 判断是否有此座位
            for (int i = 0; i < rule_y_x; i++) { // 遍历每行
                for (int j = 0; j < rule_y_y; j++) { // 遍历每列
                    for (int k = 0; k < list.size(); k++) {
                        if ((prefix[j] + (i+11)).equals(list.get(k))) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) { // 座位被选了
                        seat_number[rule_y_y * i + j] = "0";
                    } else {// 空座位
                        seat_number[rule_y_y * i + j] = prefix[j] + (i+11);
                    }
                    flag = false;
                }
            }
            return Arrays.asList(seat_number);
        } else { // 头等舱
            String[] seat_number = new String[8]; // 座位号
            String[] prefix = new String[4]; // 座位前缀
            prefix[0] = "A";
            prefix[1] = "C";
            prefix[2] = "J";
            prefix[3] = "L";
            // 实例化矩阵
            Boolean flag = false;// 判断是否有此座位
            for (int i = 0; i < rule_f_x; i++) { // 遍历每行
                for (int j = 0; j < rule_f_y; j++) { // 遍历每列
                    for (int k = 0; k < list.size(); k++) {
                        if ((prefix[j] + (i+11)).equals(list.get(k))) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) { // 座位被选了
                        seat_number[rule_f_y * i + j] = "0";
                    } else {// 空座位
                        seat_number[rule_f_y * i + j] = prefix[j] + (i+11);
                    }
                    flag = false;
                }
            }
            return Arrays.asList(seat_number);
        }
    }
}
