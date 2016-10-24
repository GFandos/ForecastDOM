/**
 * Created by 47989768s on 03/10/16.
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;


import javax.xml.crypto.dsig.XMLObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Forecast {

    public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException {

        File inputFile = new File("forecast.xml");

        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);

        NodeList nLoc = doc.getElementsByTagName("location");
        Element location = (Element)nLoc.item(0);

        //Obtenemos la ciudad
        String city = location.getElementsByTagName("name").item(0).getTextContent();

        System.out.println("Ciudad: " + city + "\n");

        NodeList nTime = doc.getElementsByTagName("time");

        for(int i = 0; i < nTime.getLength(); ++i) {

            Element time = (Element)nTime.item(i);

            //Obtenemos el intervalo de tiempo
            String from = time.getAttribute("from");
            String to = time.getAttribute("to");

            String weather = translate(((Element) ((Element) nTime.item(i)).getElementsByTagName("symbol").item(0)).getAttribute("name"));
            String temperature = ((Element) ((Element) nTime.item(i)).getElementsByTagName("temperature").item(0)).getAttribute("value");
            String windSpeedS = ((Element) ((Element) nTime.item(i)).getElementsByTagName("windSpeed").item(0)).getAttribute("mps");

            float windSpeedKph = Float.parseFloat(windSpeedS);
            windSpeedKph *= 3.6;

            System.out.println("Desde " + from + " hasta " + to + " tendremos una temperatura de " + temperature + "ºC, " + weather + " y una velocidad del viento de " + windSpeedKph + "kph\n");

        }

    }

    public static String translate(String en) {

        String sp = en;

        if(en.compareTo("sky is clear") == 0) sp = "cielo despejado";
        else if(en.compareTo("few clouds") == 0) sp = "cielo algo nublado";
        else if(en.compareTo("scattered clouds") == 0) sp = "cielo con nubes dispersas";
        else if(en.compareTo("broken clouds") == 0) sp = "cielo con nubes rotas";
        else if(en.compareTo("shower rain") == 0) sp = "llovizna";
        else if(en.compareTo("light rain") == 0) sp = "llovizna";
        else if(en.compareTo("rain") == 0) sp = "lluvia";
        else if(en.compareTo("thunderstorm") == 0) sp = "tormenta";
        else if(en.compareTo("snow") == 0) sp = "nieve";
        else if(en.compareTo("mist") == 0) sp = "niebla";
        else if(en.compareTo("overcast clouds") == 0) sp = "cielo nublado";

        return sp;

    }

}
