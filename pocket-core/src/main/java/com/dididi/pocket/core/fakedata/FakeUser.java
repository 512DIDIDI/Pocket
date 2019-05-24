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
    private static final HashMap<String, User> USER_MAP_BY_NAME = new HashMap<>();

    public static User getUser(String userId){
        return USER_MAP.get(userId);
    }

    public static User getUserByName(String userName){
        return USER_MAP_BY_NAME.get(userName);
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
                .setAvatar(String.valueOf(R.drawable.avatarman01))
                .setUserSig("eJxlj81OwkAYRfd9ikm3Gp0fhlYTFv1hUTOltBAT2EzazlRGoB3KVEDjuxuqxibeb3lOvpv7YQEA7CVb3OVl2XS14eaipQ0egQ3t2z*otRI8N5y04h*UZ61ayfPKyLaHiFKKIRw6SsjaqEr9GEJdb8CPYsv7ku8HIwgxQmM4GirqpYfxNA2i4FIeXnfd1D8n46fCcTaxO-PTchUxj3VwcQqSKiaFu0vX757yn7NwW7C1nnfYy-QpuEfxbEXc5aFhSN*QKNzoZJ-NCQvTyWRQadRe-i5yKXIIeRjQN9keVVP3AoaIIkzgNbb1aX0BM*Ncdg__");
        User lalala = new User()
                .setEmail("abcdefg@163.com")
                .setGender("male")
                .setId(2)
                .setName("lalala")
                .setPhone("15869154321")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman02))
                .setUserSig("eJxlj01LxDAURff9FaFbxUnSZtoKLmLIQnBEmc4Hsym1yaSZ2rQ0aXAQ-7tMVSx43-Ic3uV*BACAMH9c35RV1Y3GFe7cyxDcghCG13*w77UoSldEg-gH5XuvB1mURyeHCSJCCIZw7mghjdNH-WO8lZebcSuaYir5fhBDiBFawniuaDXBFd*wB8pZdJ8Y7*vd6gov-e6EGetP9YHRzLfC7DuUqBi1KbVUc7pIq6rKtwvm2dk*KcXrnG*zVzWOL5ZIu8maPFt7-SxUczerdLqVv4tSghKSRTPq5WB1ZyYBQ0QQjuAlYfAZfAFgB113");
        User hahaha = new User()
                .setEmail("kdkels@163.com")
                .setGender("male")
                .setId(3)
                .setName("hahaha")
                .setPhone("15869154322")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman03))
                .setUserSig("eJxlj8tOwzAQRff5ishbEIyTOlB2hiJR4ZJCqtJmY0WJ3VgUxzgmfSD*nTaAsMSd5Tmaq-sRhGGIZiw7K8qyedeOu50RKLwKEaDTP2iMqnjheGyrf1BsjbKCF9IJ20NMCIkAfEdVQjsl1Y9RF8fzeFu98L7k*8EAIMI4gYGvqFUPJ7fLm-HjaBrPH5ruuc3yWrMML3KG4yfZuby9Y3R6v1xtKE4F3WcLOq5p2iaMWg01va6G8cn5nlI5Gb3BbGvWgslSbhi*SLp1Oi*9Sqdexe*iS3IQcOLRTthWNboXIsAERzEcg4LP4Atqjl1B");
        User wawawa = new User()
                .setEmail("dwiij@163.com")
                .setGender("male")
                .setId(4)
                .setName("wawawa")
                .setPhone("15869107777")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman04))
                .setUserSig("eJxlj01Pg0AQhu-8CsLZ6CxbKDXxsJilNf1Qg632RLawlEnDh7CArfG-N0WNmzhzfJ7MO**nYZqm9bIIr0Ucl22hInWspGXemhZYV3*wqjCJhIponfyD8qPCWkYiVbIeIHEcxwbQHUxkoTDFH6MXl9V4kxyiIeT7wAjAJsSFka7gfoBLvr5-mG3Y*6pMd-489tWzy5-Ga5*JUwOsnHX95LDPvD4L5tNiCww5Q2BBuMo92fGN6qYsoGzX3yxayt-4Kd9mIWtJ-OgvxSvcaZEKc-nbyHPI2KX6Q52sGyyLQbCBOMSmcBnL*DLOfjNdJA__");
        User sasasa = new User()
                .setEmail("jdkkls@163.com")
                .setGender("male")
                .setId(5)
                .setName("sasasa")
                .setPhone("15869154328")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman05))
                .setUserSig("eJxlj1FPgzAUhd-5FU1fNaYtdBsme4ANE3UmTsZ0vBBsO6wLpUBnKMb-boaakXju4-flnpxPBwAAN6v4KmesOiqTGasFBNcAInh5hlpLnuUmcxv*D4pOy0Zk*d6IZoCYUkoQGjuSC2XkXv4abX66EW-5IRtKfh54CBGMJ8gbK7IY4EO0XtzemGWy4KR8jqPtDic7376lMQ45L1VNerJiicKvjC2pTQMZHC6ql-Suf8qttznS8N2r*3t-qrt6Hc2KtCQ*Kix*DFvZBfP5qNLIUvwtmlE8nVB3RD9E08pKDQJBmGLiolOg8*V8A6IJXZo_");
        User dadada = new User()
                .setEmail("kdkels@163.com")
                .setGender("male")
                .setId(6)
                .setName("dadada")
                .setPhone("15869154324")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarman06))
                .setUserSig("eJxlj1FPgzAUhd-5FYRn41poGZjsATc2G7cYhkayl6ahZbkihUCVqfG-m6HGJp77*H25J*fDcV3Xu9-ml6Is2xdtuHnrlOdeuR7yLv5g14HkwvCgl-*gOnXQKy4qo-oJYkqpj5DtgFTaQAU-hhTns-ggaz6VfD8gCPkYh4jYChwnuEuzJUsPzfUqL-Q7qfrmQaftM67JbA232T5i6ak*sJ3YiG1YxvMEkjHKctLGd4EMB5OwpHxartmG3jyWrIBqVo1yrHVO98XquFhYlQYa9bsoongehrFFX1U-QKsnwUeYYj9A53jOp-MFNyJczw__");
        User mememe = new User()
                .setEmail("ykdll@163.com")
                .setGender("female")
                .setId(7)
                .setName("mememe")
                .setPhone("15869117076")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman01))
                .setUserSig("eJxlj11PgzAUhu-5FQ3XRtvy7R2BTUGJw6*JNw2jBTotK6WbGON-N0ONJJ5z*Tw573k-DACAeX99d1pW1W7faaLfJTPBOTChefIHpeSUlJpYiv6DbJRcMVLWmqkJIsdxMIRzh1PWaV7zH0Ow4874QF-IFPJ9wIYQI*RCe67wZoLZooiSPMI3g9wUj5dJvBEuD2*zi*VDF-ZtkUqdVlfp1noO4zcRxDRP2nC1Frm3bruzXvkjq6TH6ixA9dhs1euTXC37uAyivrEXdD*L1Fyw30a*gzzXnz90YGrgu24SMEQOwhY8jml8Gl-gxF5h");
        User huhuhu = new User()
                .setEmail("dada@163.com")
                .setGender("female")
                .setId(8)
                .setName("huhuhu")
                .setPhone("15869155321")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman02))
                .setUserSig("eJxlj8FugkAURfd8BWFLU96MjGATF4y2iUiDVk0NG0JgqK*kOB0HqW367420RpLetzwn7*Z*GaZpWutodZvl*b6pdapPUljmnWmBdXOFUmKRZjodqOIfFB8SlUizUgvVQcIYowB9BwtRayzxz9g15*vxQ1GlXcnvAxeAEjIEt6-gSwcf75eT2ZRUbL3kq6nNh-4Em5KPFoud62yyuE2ECOSopW4so9J*CDB49V1MuK-CcsO3YTKPcw5*CMfMfv6cOZ4X2u-VPH9yom07HvcqNb6JyyKfEc8D2qNHoQ64rzuBAmGEDuAcy-g2fgCSb1sx");
        User tatata = new User()
                .setEmail("tatata@163.com")
                .setGender("female")
                .setId(9)
                .setName("tatata")
                .setPhone("15869254322")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman03))
                .setUserSig("eJxlj1FPgzAUhd-5FaSvGm27tWxLfIA5zQRDtrlJn0gHhVWwkNI5tsX-boYaSTz38ftyT87Zsm0bvASrG54k1V6Z2BxrAeyJDSC4-oN1LdOYm3ig039QtLXUIuaZEbqDiBCCIew7MhXKyEz*GIZfrsebtIi7ku8HQwgxQhQO*4rMO-g8W0-n9*3Ky50TVf6auWzZvvLReDEN-MILii193DgGV0QdkuPec*XM3eXhyZ*H1W1YHwheiGVZarVj5UPzxKIIjyPOsm1A3*BVdderNPJd-C4aEeQ4iPboh9CNrFQnYIgIwgN4CbA*rS*zmF3D");
        User bababa = new User()
                .setEmail("bababa@163.com")
                .setGender("female")
                .setId(10)
                .setName("bababa")
                .setPhone("15869107778")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman04))
                .setUserSig("eJxlj9FOgzAUhu95CsKtxrRliDPZBcxlxW3OZY4xbpqulHligKYrsGl8dzPUSOI5l9*X85--w7Jt23mZr2*4EFVdGmbOSjr2ve0g5-oPKgUZ44a5OvsH5UmBloznRuoOYs-zCEJ9BzJZGsjhx9jzy-b4MXtjXcj3gQFCBONbNOgrcOjgYrIaR5SmlM402qaHtTt5KJ6SM4QSVL64Skm92i8f-WYodiqIkwDCKZ23QgTP1Wm5rWk7fjXDMJlu2h2NONWbWROLd1IEUYzb0agXaaCQv43uPOz7bv*hRuojVGUnEIQ9TFx0Gcf6tL4AgVldmw__");
        User gagaga = new User()
                .setEmail("gagaga@163.com")
                .setGender("female")
                .setId(11)
                .setName("gagaga")
                .setPhone("15868154328")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman05))
                .setUserSig("eJxlj01Pg0AQhu-8CsJVY2cpaxsTDxawNEgq5ePgZbOBZbPSAlnWIjb*dy3VuIkzt3mezJv3ZJimaaVPyQ0tivatUUSNHbPMO9MC6-oPdp0oCVVkLst-kL13QjJCK8XkBBHG2AbQHVGyRolK-BicnlfjfVmTKeTywAGwEboFR1cEn2DkZ*4m9obnOOP*LC1z8QiRu4-jsOG79YoFGa1Cnqb1uHRRkPc133zfP4Yhn8E*H7FfrJPtoRpDlTxwL2qPK8ntYOtfvb5kynPutUglDuy30RKjxQLrnY5M9qJtJsEGhJE9h-NYxqfxBZcUXaU_");
        User xixixi = new User()
                .setEmail("xixixi@163.com")
                .setGender("female")
                .setId(12)
                .setName("xixixi")
                .setPhone("15869155324")
                .setAddress("某省某市某街某单元某室")
                .setAvatar(String.valueOf(R.drawable.avatarwoman06))
                .setUserSig("eJxlj0tPg0AUhff8CsJWY*bBAHFHJ9YSRaOUpKzIyEzh9kFxmFKI6X83RU1JPHf5fbkn58uybdtZPid3oigOx9rkZmiUY9-bDnJur7BpQObC5FTLf1D1DWiVi7VReoSYMUYQmjogVW1gDb9GD5eb8FZu87Hk54GLEMHYQ*5UgXKE8UPGozd*CvwSNOP65X0uYq-fp4vitd2F7pA2YRemteiSdBmTR36KqjAu5by6*TiuirLcLGgW0TD79GY1SQZunuh2tttUJgDDZTqpNLBXf4sChn3fCya0U7qFQz0KBGGGCUWXONbZ*gYChF5g");
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
        USER_MAP_BY_NAME.put(String.valueOf(dididi.getName()),dididi);
        USER_MAP_BY_NAME.put(String.valueOf(lalala.getName()),lalala);
        USER_MAP_BY_NAME.put(String.valueOf(hahaha.getName()),hahaha);
        USER_MAP_BY_NAME.put(String.valueOf(wawawa.getName()),wawawa);
        USER_MAP_BY_NAME.put(String.valueOf(sasasa.getName()),sasasa);
        USER_MAP_BY_NAME.put(String.valueOf(dadada.getName()),dadada);
        USER_MAP_BY_NAME.put(String.valueOf(mememe.getName()),mememe);
        USER_MAP_BY_NAME.put(String.valueOf(huhuhu.getName()),huhuhu);
        USER_MAP_BY_NAME.put(String.valueOf(tatata.getName()),tatata);
        USER_MAP_BY_NAME.put(String.valueOf(bababa.getName()),bababa);
        USER_MAP_BY_NAME.put(String.valueOf(gagaga.getName()),gagaga);
        USER_MAP_BY_NAME.put(String.valueOf(xixixi.getName()),xixixi);
    }
}
