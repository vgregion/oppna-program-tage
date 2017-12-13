package se.goteborg.retursidan.interceptor;

import org.junit.Test;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ExpireInterceptorTest {

    @Test
    public void groupAdsByEmail() throws Exception {
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();

        p1.setEmail("p1@mail.com");
        p2.setEmail("p2@mail.com");
        p3.setEmail("p3@mail.com");

        Advertisement a1 = new Advertisement();
        a1.setContact(p1);
        a1.setId(1);

        Advertisement a2 = new Advertisement();
        a2.setContact(p2);
        a2.setId(2);

        Advertisement a3 = new Advertisement();
        a3.setContact(p3);
        a3.setId(3);

        Advertisement a4 = new Advertisement();
        a4.setContact(p1);
        a4.setId(4);

        Advertisement a5 = new Advertisement();
        a5.setContact(p2);
        a5.setId(5);

        Advertisement a6 = new Advertisement();
        a6.setContact(p3);
        a6.setId(6);

        Advertisement a7 = new Advertisement();
        a7.setContact(p1);
        a7.setId(6);

        List<Advertisement> advertisements = Arrays.asList(a1, a2, a3, a4, a5, a6, a7);
        Map<String, List<Advertisement>> stringListMap = ExpireInterceptor.groupAdsByEmail(advertisements);

        List<Advertisement> a1s = stringListMap.get("p1@mail.com");
        assertTrue(a1s.contains(a1));
        assertTrue(a1s.contains(a4));
        assertTrue(a1s.contains(a7));

        List<Advertisement> a2s = stringListMap.get("p2@mail.com");
        assertTrue(a2s.contains(a2));
        assertTrue(a2s.contains(a5));

        List<Advertisement> a3s = stringListMap.get("p3@mail.com");
        assertTrue(a3s.contains(a3));
        assertTrue(a3s.contains(a6));
    }

}