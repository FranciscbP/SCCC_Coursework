package com.kiko.travellers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Travellers {
    
    private final static String RestServer_IPort = "20.254.32.68:8080";
    
    //Main UI Variables
    JFrame frame = null;
    JPanel container = null;
    JPanel panel = null;
    JLabel userIDTextLbl = null;
    JLabel userIDLbl = null;
    JButton generateIDBtn = null;
    JButton loadPrevIDBtn = null;
    DefaultListModel <String> listModel = null;
    JLabel tripsLbl = null;
    JList tripsList = null;
    JButton loadTripsBtn = null;
    JButton openTripProposalUIBtn = null;
    JLabel tripDetailsLbl = null;
    JTextArea tripDetailsText = null;
    
    //SumitTrip UI
    JFrame submitTripFrame = null;
    JPanel submitTripContainer = null;
    JLabel submitTripLocationLbl = null;
    JTextField submitTripLocationTxt = null;
    JLabel submitTripStartDateLbl = null;
    JFormattedTextField submitTripStartDateTxt = null;
    JLabel submitTripEndDateLbl = null;
    JFormattedTextField submitTripEndDateTxt = null;
    JButton submitTripMessageProposalBtn = null;
    
    JButton submitTravelIntentBtn = null;
    JButton loadTripDetailsBtn = null;
    JButton getTravelIntentsBtn = null;
    JButton travelIntentSent = null;
    
    String currentUserID;
    JsonObject tripOffersJsonObject = new JsonObject();
    
    public Travellers()
    {
        initUI();
    }
    
    //Create Main Page
    public void initTripProposalPage()
    {
        //Creating the Frame     
        submitTripFrame = new JFrame("Submit Trip Proposal");
        //submitTripFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        submitTripFrame.setSize(400, 250);
        submitTripFrame.setLocationRelativeTo(null);
        submitTripFrame.setResizable(false);
        
        submitTripContainer = new JPanel();
        submitTripContainer.setBackground(new java.awt.Color(26, 29, 57));
        submitTripContainer.setLayout(null);
        
        submitTripLocationLbl = new JLabel("Location: ");
        submitTripLocationLbl.setForeground(Color.WHITE);
        submitTripLocationLbl.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        submitTripLocationLbl.setBounds(20,2,100,50);
        submitTripContainer.add(submitTripLocationLbl);
        
        submitTripLocationTxt = new JTextField("Address");
        submitTripLocationTxt.setForeground(Color.GRAY);
        submitTripLocationTxt.setFont(new java.awt.Font("Dialog", 0, 12));
        submitTripLocationTxt.setBounds(120,20,250,20);
        submitTripContainer.add(submitTripLocationTxt);
                
        submitTripLocationTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (submitTripLocationTxt.getText().equals("Address")) 
                {
                    submitTripLocationTxt.setText("");
                    submitTripLocationTxt.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (submitTripLocationTxt.getText().isEmpty()) {
                    submitTripLocationTxt.setForeground(Color.GRAY);
                    submitTripLocationTxt.setText("Address");
                }
            }
        });
                
        submitTripStartDateLbl = new JLabel("Start Date: ");
        submitTripStartDateLbl.setForeground(Color.WHITE);
        submitTripStartDateLbl.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        submitTripStartDateLbl.setBounds(20,36,100,50);
        submitTripContainer.add(submitTripStartDateLbl);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        submitTripStartDateTxt = new JFormattedTextField(format);
        submitTripStartDateTxt.setText("YYYY-MM-DD");
        submitTripStartDateTxt.setForeground(Color.GRAY);
        submitTripStartDateTxt.setFont(new java.awt.Font("Dialog", 0, 12));
        submitTripStartDateTxt.setBounds(120,55,250,20);
        submitTripContainer.add(submitTripStartDateTxt);
        
        submitTripStartDateTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (submitTripStartDateTxt.getText().equals("YYYY-MM-DD")) 
                {
                    submitTripStartDateTxt.setText("");
                    submitTripStartDateTxt.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (submitTripStartDateTxt.getText().isEmpty()) {
                    submitTripStartDateTxt.setForeground(Color.GRAY);
                    submitTripStartDateTxt.setText("YYYY-MM-DD");
                }
            }
        });
        
        submitTripEndDateLbl = new JLabel("End Date: ");
        submitTripEndDateLbl.setForeground(Color.WHITE);
        submitTripEndDateLbl.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        submitTripEndDateLbl.setBounds(20,73,100,50);
        submitTripContainer.add(submitTripEndDateLbl);

        submitTripEndDateTxt = new JFormattedTextField(format);
        submitTripEndDateTxt.setText("YYYY-MM-DD");
        submitTripEndDateTxt.setForeground(Color.GRAY);
        submitTripEndDateTxt.setFont(new java.awt.Font("Dialog", 0, 12));
        submitTripEndDateTxt.setBounds(120,90,250,20);
        submitTripContainer.add(submitTripEndDateTxt);
        
        submitTripEndDateTxt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (submitTripEndDateTxt.getText().equals("YYYY-MM-DD")) 
                {
                    submitTripEndDateTxt.setText("");
                    submitTripEndDateTxt.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (submitTripEndDateTxt.getText().isEmpty()) {
                    submitTripEndDateTxt.setForeground(Color.GRAY);
                    submitTripEndDateTxt.setText("YYYY-MM-DD");
                }
            }
        });
        
        submitTripMessageProposalBtn = new JButton("Submit Trip Message Proposal");
        submitTripMessageProposalBtn.setFocusPainted(false);
        submitTripMessageProposalBtn.setBounds(70,150,250,30);
        submitTripMessageProposalBtn.setForeground(Color.WHITE);
        submitTripMessageProposalBtn.setBackground(new java.awt.Color(32, 36, 69));
        submitTripMessageProposalBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        submitTripContainer.add(submitTripMessageProposalBtn);
        submitTripMessageProposalBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    submitTripBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
                
        submitTripFrame.getContentPane().add(submitTripContainer);
        submitTripFrame.setVisible(true);
    }           
    
    public void initUI()
    {
        frame = new JFrame("Travellers - Find Travel Buddies");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
                
        container = new JPanel();
        container.setBackground(new java.awt.Color(26, 29, 57));
        container.setLayout(null);
                        
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(32, 36, 69));
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panel.setBounds(20, 20, 250, 50);
        panel.setLayout(null);
        container.add(panel);
        
        userIDTextLbl = new JLabel("User ID: ");
        userIDTextLbl.setForeground(Color.WHITE);
        userIDTextLbl.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        userIDTextLbl.setBounds(10,-1,100,50);
        panel.add(userIDTextLbl);
        
        userIDLbl = new JLabel("0");               
        userIDLbl.setForeground(Color.WHITE);
        userIDLbl.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        userIDLbl.setBounds(90,-1,100,50);
        panel.add(userIDLbl);
        
        generateIDBtn = new JButton("Generate ID");
        generateIDBtn.setFocusPainted(false);
        generateIDBtn.setBounds(310,30,160,30);
        generateIDBtn.setForeground(Color.WHITE);
        generateIDBtn.setBackground(new java.awt.Color(32, 36, 69));
        generateIDBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(generateIDBtn);
        
        generateIDBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    getIDBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
        
        loadPrevIDBtn = new JButton("Load Previous ID");
        loadPrevIDBtn.setFocusPainted(false);
        loadPrevIDBtn.setBounds(500,30,160,30);
        loadPrevIDBtn.setForeground(Color.WHITE);
        loadPrevIDBtn.setBackground(new java.awt.Color(32, 36, 69));
        loadPrevIDBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(loadPrevIDBtn);
        
        loadPrevIDBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    loadIDBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
        
        tripsLbl = new JLabel("Trips");
        tripsLbl.setForeground(Color.WHITE);
        tripsLbl.setFont(new java.awt.Font("Dialog", 0, 18));
        tripsLbl.setBounds(25, 70, 200, 70);
        container.add(tripsLbl);
        
        listModel = new DefaultListModel();
        tripsList = new JList(listModel);
        tripsList.setBackground(new java.awt.Color(32, 36, 69));
        tripsList.setForeground(Color.WHITE);
        tripsList.setFont(new java.awt.Font("Dialog", 0, 14));
        tripsList.setBounds(20,120, 250, 230);
        container.add(tripsList);
        
        loadTripsBtn = new JButton("Load Trip Proposals");
        loadTripsBtn.setEnabled(false);
        loadTripsBtn.setFocusPainted(false);
        loadTripsBtn.setBounds(35,370,200,30);
        loadTripsBtn.setForeground(Color.WHITE);
        loadTripsBtn.setBackground(new java.awt.Color(32, 36, 69));
        loadTripsBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(loadTripsBtn);
        
        loadTripsBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    loadTripsBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
        
        openTripProposalUIBtn = new JButton("Submit Trip Proposal");
        openTripProposalUIBtn.setEnabled(false);
        openTripProposalUIBtn.setFocusPainted(false);
        openTripProposalUIBtn.setBounds(35,410,200,30);
        openTripProposalUIBtn.setForeground(Color.WHITE);
        openTripProposalUIBtn.setBackground(new java.awt.Color(32, 36, 69));
        openTripProposalUIBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(openTripProposalUIBtn);
        
        openTripProposalUIBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    openTripSubmitionUiBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
        
        tripDetailsLbl = new JLabel("Trip Details");
        tripDetailsLbl.setForeground(Color.WHITE);
        tripDetailsLbl.setFont(new java.awt.Font("Dialog", 0, 18));
        tripDetailsLbl.setBounds(310, 70, 200, 70);
        container.add(tripDetailsLbl);
                
        tripDetailsText = new JTextArea();
        tripDetailsText.setBackground(new java.awt.Color(32, 36, 69));
        tripDetailsText.setForeground(Color.WHITE);
        tripDetailsText.setFont(new java.awt.Font("Dialog", 0, 14));
        tripDetailsText.setEnabled(false);        
        //container.add(tripDetailsText);
                
        JScrollPane scroll = new JScrollPane (tripDetailsText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(310, 120, 350, 230);
       
       container.add(scroll);
        
        loadTripDetailsBtn = new JButton("Load Trip Details");
        loadTripDetailsBtn.setEnabled(false);
        loadTripDetailsBtn.setFocusPainted(false);
        loadTripDetailsBtn.setBounds(360,370,250,30);
        loadTripDetailsBtn.setForeground(Color.WHITE);
        loadTripDetailsBtn.setBackground(new java.awt.Color(32, 36, 69));
        loadTripDetailsBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(loadTripDetailsBtn);
        
        loadTripDetailsBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    loadTripDetailsBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
        
        submitTravelIntentBtn = new JButton("Submit Travel Intent");
        submitTravelIntentBtn.setEnabled(false);
        submitTravelIntentBtn.setFocusPainted(false);
        submitTravelIntentBtn.setBounds(360,410,250,30);
        submitTravelIntentBtn.setForeground(Color.WHITE);
        submitTravelIntentBtn.setBackground(new java.awt.Color(32, 36, 69));
        submitTravelIntentBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(submitTravelIntentBtn);
        
        submitTravelIntentBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    submitTravelIntentBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
        
        getTravelIntentsBtn = new JButton("Get Travel Intents");
        getTravelIntentsBtn.setVisible(false);
        getTravelIntentsBtn.setEnabled(false);
        getTravelIntentsBtn.setFocusPainted(false);
        getTravelIntentsBtn.setBounds(360,410,250,30);
        getTravelIntentsBtn.setForeground(Color.WHITE);
        getTravelIntentsBtn.setBackground(new java.awt.Color(32, 36, 69));
        getTravelIntentsBtn.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(getTravelIntentsBtn);
        
        getTravelIntentsBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    getTravelIntentsBtnActionPerformed(e);
                }
                catch(IOException exception){}
            }
        });
        
        travelIntentSent = new JButton("Travel Intent Submited!");
        travelIntentSent.setVisible(false);
        travelIntentSent.setEnabled(false);
        travelIntentSent.setFocusPainted(false);
        travelIntentSent.setBounds(360,410,250,30);
        travelIntentSent.setForeground(Color.WHITE);
        travelIntentSent.setBackground(new java.awt.Color(32, 36, 69));
        travelIntentSent.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        container.add(travelIntentSent);
                
        frame.getContentPane().add(container);
        frame.setVisible(true);
    }
        
    private void getIDBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        getRandomUUID();
    } 
    
    private void loadIDBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        loadPreviousID();
    } 
       
    private void loadTripsBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        loadTrips();
    } 
    
    private void openTripSubmitionUiBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        openSubmitTripUi();
    } 
    
    private void submitTripBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        submitTripProposalMessage();
    } 
        
    private void submitTravelIntentBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        submitTravelIntent();
    } 
    
    private void loadTripDetailsBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        loadTripDetails();
    } 
        
        private void getTravelIntentsBtnActionPerformed(ActionEvent e) throws IOException 
    {                                          
        getTravelIntents();
    } 
    
    //Get Random ID
    public void getRandomUUID() throws IOException
    {
        String password;
        
        do
        {
            password = JOptionPane.showInputDialog("Enter Password for New User ID");
            if(password.length() < 4)
            {
                JOptionPane.showMessageDialog(null,"The Password is too Short!");
            }
        }
        while(password.length() < 4);
        
        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/getRandomID?purpose=USER&password="+password);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        String rndID;
         try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
         {
            rndID = br.readLine(); 
            br.close();
    
            JOptionPane.showMessageDialog(null,"Please Remember your User Credentials!");
            currentUserID = rndID;
            userIDLbl.setText(rndID);
            generateIDBtn.setEnabled(false);
            loadPrevIDBtn.setEnabled(false);
            loadTripsBtn.setEnabled(true);
            openTripProposalUIBtn.setEnabled(true);            
         }
    }
    
    //Load previous ID
    public void loadPreviousID() throws IOException
    {
        String id = JOptionPane.showInputDialog("Enter ID");
        String password = JOptionPane.showInputDialog("Enter Password");

        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/loadUser?id="+id+"&password="+password);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

         try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
         {
            String loadID = br.readLine(); 
            br.close();
                          
            if(loadID.equals("true"))
            {
                currentUserID = id;
                userIDLbl.setText(id);
                generateIDBtn.setEnabled(false);
                loadPrevIDBtn.setEnabled(false);
                loadTripsBtn.setEnabled(true);
                openTripProposalUIBtn.setEnabled(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid User Credentials!");
            }
         }
    }
    
    public void loadTrips() throws IOException
    {             
        String line;
        StringBuilder responseContent = new StringBuilder();  
        
        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/queryTravelOffers");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
        {
            while ((line = br.readLine()) != null) 
            {
                responseContent.append(line);
            }
        
            br.close();
        }
        
        String response = responseContent.toString();
        
        if(!response.equals("No Travel Offers!"))
        {
            JsonElement jsonElement = JsonParser.parseString(response);
            tripOffersJsonObject = jsonElement.getAsJsonObject();        
            JsonArray jsonArray = tripOffersJsonObject.getAsJsonArray("travelOffers");

            int size = jsonArray.size();

            if(size!= 0)
            {               
                listModel.clear();
                for(int i = 0; i < size; i ++)
                {
                    JsonElement offer = jsonArray.get(i);
                    String location = offer.getAsJsonObject().get("location").getAsString();
                    String startDate = offer.getAsJsonObject().get("tripStartDate").getAsString();                

                    listModel.addElement( location + ": " + startDate);
                }
                tripsList.setSelectedIndex(0);     
                loadTripDetailsBtn.setEnabled(true);
            }
        }
        
    }
    
    public void openSubmitTripUi() throws IOException
    {
        initTripProposalPage();
    }
    
    public void submitTripProposalMessage() throws IOException
    {
        String newMessageId = getRndMessageId();
        
        String location = submitTripLocationTxt.getText();
        String startDate = submitTripStartDateTxt.getText();
        String endDate = submitTripEndDateTxt.getText();
        
        Boolean validAddress = checkIfAddressExists(location);
        
        if(validAddress)
        {
            LocalDate today = LocalDate.now();
            LocalDate tommorrow = today.plusDays(1);
            LocalDate inTenDays = today.plusDays(10);

            LocalDate startD = LocalDate.parse(startDate);
            LocalDate endD = LocalDate.parse(endDate);
            
            if(startD.isBefore(inTenDays) && startD.isAfter(today) && startD.isBefore(endD))
            {
                String messageProposalJson = "{\"userId\": "+ currentUserID +", \"msgId\": "+ newMessageId +", \"location\": \""+ location +"\", \"tripStartDate\": \""+ startDate +"\", \"tripEndDate\": \""+ endDate + "\" }";

                URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/submitTravelOffer");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);
                conn.setDoInput(true);

                try(OutputStream os = conn.getOutputStream()) 
                {
                    os.write(messageProposalJson.getBytes("UTF-8")); 
                    os.close();		
                }

                try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                      StringBuilder response = new StringBuilder();
                      String responseLine = null;
                      while ((responseLine = br.readLine()) != null) {
                          response.append(responseLine.trim());
                      }
                      System.out.println(response.toString());
                  }
                submitTripFrame.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Please Insert a valid Date between "+tommorrow+" and " +inTenDays+ "!");
            }  
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Please Insert a valid Location!");
        }
        
    }
    
    public Boolean checkIfAddressExists(String address)throws IOException
    {
        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/checkIfAddressExists?address="+address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        String result;
        Boolean exists = false;
         try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
         {
            result = br.readLine(); 
            br.close();                
         }
                         
        if(result.equals("true"))
        {
            exists = true;
        }
        else if (result.equals("false"))
        {
            exists = false;
        }
         
        return exists;
    }
    
    public String getRndMessageId() throws IOException
    {
        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/getRandomID?purpose=MESSAGE");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        String rndID;
         try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
         {
            rndID = br.readLine(); 
            br.close();
         }
         return rndID;
    }

    public void submitTravelIntent()throws IOException
    {
        String intentMessage = JOptionPane.showInputDialog("Please Insert Intent Message");

        JsonArray jsonArray = tripOffersJsonObject.getAsJsonArray("travelOffers");
        JsonElement offer = jsonArray.get(tripsList.getSelectedIndex());
        
        String messageId = offer.getAsJsonObject().get("msgId").getAsString();        
        
        String messageProposalJson = "{\"userId\": "+ currentUserID +", \"msgId\": "+ messageId +", \"intentMessage\": \""+intentMessage +"\"}";
        
        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/submitTravelIntent");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        
        try(OutputStream os = conn.getOutputStream()) 
        {
            os.write(messageProposalJson.getBytes("UTF-8")); 
            os.close();		
        }
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
              StringBuilder response = new StringBuilder();
              String responseLine = null;
              while ((responseLine = br.readLine()) != null) {
                  response.append(responseLine.trim());
              }
              System.out.println(response.toString());
          }
        
        submitTravelIntentBtn.setVisible(false);
        submitTravelIntentBtn.setEnabled(false);
        travelIntentSent.setVisible(true);
        submitTravelIntentBtn.setEnabled(true);
    }
    
    public void loadTripDetails() throws IOException
    { 
        JsonArray jsonArray = tripOffersJsonObject.getAsJsonArray("travelOffers");
        JsonElement offer = jsonArray.get(tripsList.getSelectedIndex());
        String location = offer.getAsJsonObject().get("location").getAsString();
        String startDate = offer.getAsJsonObject().get("tripStartDate").getAsString();
        String endDate = offer.getAsJsonObject().get("tripEndDate").getAsString();
        String weather = offer.getAsJsonObject().get("weather").getAsJsonObject().get("weatherCondition").getAsString();
        String temperature = offer.getAsJsonObject().get("weather").getAsJsonObject().get("averageTemperature").getAsString(); 
        String userId = offer.getAsJsonObject().get("userId").getAsString();

        tripDetailsText.setText(" Location: " + location +"\n\n");
        tripDetailsText.append(" Start Date: " + startDate +"\n");
        tripDetailsText.append(" End Date: " + endDate +"\n\n");
        tripDetailsText.append(" Weather ("+ startDate +"): " + weather +"\n");
        tripDetailsText.append(" Average Temperature ("+ startDate +"): " + temperature +"ºC\n\n");

        
        if(userId.equals(currentUserID))
        {
            getTravelIntentsBtn.setVisible(true);
            getTravelIntentsBtn.setEnabled(true);
            submitTravelIntentBtn.setVisible(false);
            submitTravelIntentBtn.setEnabled(false);
            
        }
        else
        {       
            Boolean alreadySentIntent = checkIfAlreadySentTravelIntent();
            
            getTravelIntentsBtn.setVisible(false);
            getTravelIntentsBtn.setEnabled(false);
            
            if(!alreadySentIntent)
            {
                submitTravelIntentBtn.setVisible(true);
                submitTravelIntentBtn.setEnabled(true);
                travelIntentSent.setVisible(false);
                travelIntentSent.setEnabled(false);
            }
            else
            {                
                submitTravelIntentBtn.setVisible(false);
                submitTravelIntentBtn.setEnabled(false);
                travelIntentSent.setVisible(true);
                travelIntentSent.setEnabled(false);
            }
        }
        
    }
    
    public boolean checkIfAlreadySentTravelIntent() throws IOException
    {
        String line;
        StringBuilder responseContent = new StringBuilder();  
        
        JsonArray jsonArray = tripOffersJsonObject.getAsJsonArray("travelOffers");
        JsonElement offer = jsonArray.get(tripsList.getSelectedIndex());      
        String messageId = offer.getAsJsonObject().get("msgId").getAsString();  
        
        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/queryTravelIntents?msgId="+messageId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
        {
            while ((line = br.readLine()) != null) 
            {
                responseContent.append(line);
            }
        
            br.close();
        }
                
        String response = responseContent.toString();
        
        if(!response.equals("No Travel Intents!"))
        {
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonArray resultArray = jsonElement.getAsJsonArray();

            int size = resultArray.size();

            Boolean asSent = false;

            if(size!= 0)
            {               
                for(int i = 0; i < size; i ++)
                {
                    JsonElement resultElement = resultArray.get(i);
                    String userId = resultElement.getAsJsonObject().get("userId").getAsString();

                    if(userId.equals(currentUserID))
                    {
                        asSent = true;
                    }
                }        
            }

            return asSent;
        }
        else
        {
            return false;
        }
        
        
    }
    
    public void getTravelIntents() throws IOException
    {
        String line;
        StringBuilder responseContent = new StringBuilder();  
        
        JsonArray jsonArray = tripOffersJsonObject.getAsJsonArray("travelOffers");
        JsonElement offer = jsonArray.get(tripsList.getSelectedIndex());      
        String messageId = offer.getAsJsonObject().get("msgId").getAsString();  
        
        URL url = new URL("http://"+ RestServer_IPort +"/RESTService/api/orchestrator/queryTravelIntents?msgId="+messageId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))))
        {
            while ((line = br.readLine()) != null) 
            {
                responseContent.append(line);
            }
        
            br.close();
        }
        
        String response = responseContent.toString();
        
        if(!response.equals("No Travel Intents!"))
        {
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonArray resultArray = jsonElement.getAsJsonArray();

            int size = resultArray.size();

            loadTripDetails();

            if(size!= 0)
            {   
                tripDetailsText.append("-----------------------------------------------------------\n");
                tripDetailsText.append(" Number of Travel Intents: " + size +"\n\n");
                for(int i = 0; i < size; i ++)
                {
                    JsonElement resultElement = resultArray.get(i);
                    String userId = resultElement.getAsJsonObject().get("userId").getAsString();
                    String intentMessage = resultElement.getAsJsonObject().get("intentMessage").getAsString();                

                    tripDetailsText.append(" User: "+ userId+ " - Message: " + intentMessage+"\n");
                }        
            }
            else
            {
                tripDetailsText.append(" Number of Travel Intents: 0\n");
            }  
        }
    }
    
    public static void main(String[] args) 
    {
        new Travellers();        
    }
    
    
}
