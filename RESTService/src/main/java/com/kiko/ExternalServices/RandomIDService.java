package com.kiko.ExternalServices;

import java.net.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.XMLConstants;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class RandomIDService {
    
    public static final String usersXMLFile = "./webapps/DATA/users.xml";
    public static final String messagesXMLFile = "./webapps/DATA/messages.xml";
    
    public static String getRandomId() throws IOException
    {
       URL url = new URL("https://www.random.org/integers/?num=1&min=1000&max=9999&col=1&base=10&format=plain&rnd=new");
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       conn.setRequestMethod("GET");
       conn.setRequestProperty("Accept", "application/json");
       
        try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
        {
            String rndID = br.readLine(); 
            br.close();
            
            return rndID;
        }
    }
        
    public static String getNewUserUID(String pass) throws IOException, JDOMException
    {
        String result;
        String fileName = usersXMLFile;
        
        File f = new File(fileName);
        if(!f.exists())
        { 
            String newID = getRandomId();  
            createXML("USER",newID,pass);
            result = newID;
        }
        else
        {
            String newID;
            int idIsUsed = 1;
            
            do
            {
                newID = getRandomId();
                idIsUsed = checkUsedIDsInXML("USER",newID);
            }
            while(idIsUsed == 1);
            
            appendToXML("USER",newID,pass);
            result = newID;
        }
        
        return result;
    }
    
    public static String getNewMessageUID() throws IOException, JDOMException
    {
        String result;
        String fileName = messagesXMLFile;
        
        File f = new File(fileName);
        if(!f.exists())
        { 
            String newID = getRandomId();
            createXML("MESSAGE",newID,null);
            result = newID;
        }
        else
        {
            String newID;
            int idIsUsed = 1;
            
            do
            {
                newID = getRandomId();
                idIsUsed = checkUsedIDsInXML("MESSAGE",newID);
            }
            while(idIsUsed == 1);
            
            appendToXML("MESSAGE",newID,null);
            result = newID;
        }
        
        return result;
    }
    
    public static void createXML(String purpose,String id, String pass) throws IOException
    {        
        if(purpose.equals("USER"))
        {
            String fileName = usersXMLFile;

            Document doc = new Document();
            doc.setRootElement(new Element("users"));

            Element user = new Element("user");

            // Add XML Attribute
            user.setAttribute("id", id);

            //Add Content
            user.addContent(new Element("pass").setText(pass));

            // Append Child to root
            doc.getRootElement().addContent(user);

            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.setFormat(Format.getPrettyFormat());

            try (FileOutputStream f = new FileOutputStream(fileName)) 
            {
                BufferedOutputStream bf = new BufferedOutputStream(f);
                xmlOutputter.output(doc, bf);
            }     
        }
        else if (purpose.equals("MESSAGE"))
        {
            String fileName = messagesXMLFile;

            Document doc = new Document();
            doc.setRootElement(new Element("messages"));

            Element user = new Element("message");

            // Add XML Attribute
            user.setAttribute("id", id);

            // Append Child to root
            doc.getRootElement().addContent(user);

            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.setFormat(Format.getPrettyFormat());

            try (FileOutputStream f = new FileOutputStream(fileName)) 
            {
                BufferedOutputStream bf = new BufferedOutputStream(f);
                xmlOutputter.output(doc, bf);
            }     
        }   
    }
    
    public static void appendToXML(String purpose, String id, String pass) throws IOException, JDOMException 
    {
        SAXBuilder sax = new SAXBuilder();

        // prevent xxe attacks
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        
        if(purpose.equals("USER"))
        {
            String fileName = "";
            fileName = usersXMLFile;
            
            Document doc = sax.build(new File(fileName));

            Element rootNode = doc.getRootElement();
                
            // Add new XML Child Node
            rootNode.addContent(new Element("user").setAttribute("id", id).addContent(new Element("pass").setText(pass)));
            
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.setFormat(Format.getPrettyFormat());

            try (FileOutputStream f = new FileOutputStream(fileName)) 
            {
                BufferedOutputStream bf = new BufferedOutputStream(f);
                xmlOutputter.output(doc, bf);
            }   
        }
        else if (purpose.equals("MESSAGE"))
        {
            String fileName = "";
            fileName = messagesXMLFile;
            
            Document doc = sax.build(new File(fileName));

            Element rootNode = doc.getRootElement();
                
            // Add new XML Child Node
            rootNode.addContent(new Element("message").setAttribute("id", id));
            
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.setFormat(Format.getPrettyFormat());

            try (FileOutputStream f = new FileOutputStream(fileName)) 
            {
                BufferedOutputStream bf = new BufferedOutputStream(f);
                xmlOutputter.output(doc, bf);
            }   
        }
    }
    
    public static int checkUsedIDsInXML(String purpose, String id) throws IOException, JDOMException 
    {
        String fileName = "";
        String childrenName ="";
        
        if(purpose.equals("USER"))
        {
            fileName = usersXMLFile;
            childrenName = "user";
        }
        else if (purpose.equals("MESSAGE"))
        {
            fileName = messagesXMLFile;
            childrenName = "message";
        }
                        
        SAXBuilder sax = new SAXBuilder();

        // prevent xxe attacks
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        Document doc = sax.build(new File(fileName));
        Element rootNode = doc.getRootElement();
        
        //Get Children
        List<Element> listChildrenNode = rootNode.getChildren(childrenName);
         
        int idIsUsed = 0;
        
        // Loop Elements
        for (Element element : listChildrenNode) 
        {
            String userIDs = element.getAttribute("id").getValue();

            if(userIDs.equals(id))
            {
                idIsUsed = 1;
            }
        }
                
        return idIsUsed;
    }
            
}
