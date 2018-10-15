package com.dididi.pocket.core.fakedata;

import com.dididi.pocket.core.R;
import com.dididi.pocket.core.entity.User;

import java.util.HashMap;

/**
 * @author dididi
 * @describe 假用户数据
 * @since 15/10/2018
 */

public class FakeUser {
    private static final HashMap<String, User> USER_MAP = new HashMap<>();

    public static User getUser(String userId){
        return USER_MAP.get(userId);
    }

    /**
     * 假数据初始化 不用管他什么意思
     */
    public static void init(){
        User dididi = new User()
                .setEmail("ycycyc512@163.com")
                .setGender("male")
                .setId(1)
                .setName("dididi")
                .setPhone("15869107076")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman01));
        User lalala = new User()
                .setEmail("abcdefg@163.com")
                .setGender("male")
                .setId(2)
                .setName("张三")
                .setPhone("15869154321")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman02));
        User hahaha = new User()
                .setEmail("kdkels@163.com")
                .setGender("male")
                .setId(3)
                .setName("李四")
                .setPhone("15869154322")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman03));
        User wawawa = new User()
                .setEmail("dwiij@163.com")
                .setGender("male")
                .setId(4)
                .setName("王五")
                .setPhone("15869107777")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman04));
        User sasasa = new User()
                .setEmail("jdkkls@163.com")
                .setGender("male")
                .setId(5)
                .setName("老王")
                .setPhone("15869154328")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman05));
        User dadada = new User()
                .setEmail("kdkels@163.com")
                .setGender("male")
                .setId(6)
                .setName("tony老师")
                .setPhone("15869154324")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman06));
        User mememe = new User()
                .setEmail("ykdll@163.com")
                .setGender("female")
                .setId(7)
                .setName("二丫")
                .setPhone("15869117076")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman01));
        User huhuhu = new User()
                .setEmail("dada@163.com")
                .setGender("female")
                .setId(8)
                .setName("小芳")
                .setPhone("15869155321")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman02));
        User tatata = new User()
                .setEmail("tatata@163.com")
                .setGender("female")
                .setId(9)
                .setName("小红")
                .setPhone("15869254322")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman03));
        User bababa = new User()
                .setEmail("bababa@163.com")
                .setGender("female")
                .setId(10)
                .setName("小绿")
                .setPhone("15869107778")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman04));
        User gagaga = new User()
                .setEmail("gagaga@163.com")
                .setGender("female")
                .setId(11)
                .setName("小花")
                .setPhone("15868154328")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman05));
        User xixixi = new User()
                .setEmail("xixixi@163.com")
                .setGender("female")
                .setId(12)
                .setName("小草")
                .setPhone("15869155324")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman06));
        USER_MAP.put(String.valueOf(dididi.getId()),dididi);
        USER_MAP.put(String.valueOf(lalala.getId()),lalala);
        USER_MAP.put(String.valueOf(hahaha.getId()),hahaha);
        USER_MAP.put(String.valueOf(wawawa.getId()),wawawa);
        USER_MAP.put(String.valueOf(sasasa.getId()),sasasa);
        USER_MAP.put(String.valueOf(dadada.getId()),dadada);
        USER_MAP.put(String.valueOf(mememe.getId()),mememe);
        USER_MAP.put(String.valueOf(huhuhu.getId()),huhuhu);
        USER_MAP.put(String.valueOf(tatata.getId()),tatata);
        USER_MAP.put(String.valueOf(bababa.getId()),bababa);
        USER_MAP.put(String.valueOf(gagaga.getId()),gagaga);
        USER_MAP.put(String.valueOf(xixixi.getId()),xixixi);
    }
}
