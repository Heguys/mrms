package com.hxminco.mrms.comm.utils;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * Created by Employee on 2017/7/11.
 */
public class JAXBUtils {
    public static <T> String marshaller(Class<T> type,T chart) throws JAXBException {
        JAXBContext jaxbContext= JAXBContext.newInstance(type);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(chart,baos);
        return baos.toString();

    }

    public static <T> T unmarshaller(String str, Class<T> type) throws JAXBException {
        JAXBContext jAXBContext = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
        T t = (T)unmarshaller.unmarshal(new StringReader(str));
        return t;
    }
}
