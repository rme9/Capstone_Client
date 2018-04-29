package edu.uwf.scavenger;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestHandler extends AsyncTask<String, Void,  List<Building>> {
    static String inputLine;
    static int i = 0;
    static int buildingNum = 0;

    public List<Building> objects;
    public View contextView;
    public ListView loadView;

    public RequestHandler(View context, ListView loadView) {
        contextView = context;
        this.loadView = loadView;
    }

    @Override
    protected List<Building> doInBackground(String... strings) {
        try {
            URL url = new URL("http://node-express-env.hvwzjgwfbd.us-east-1.elasticbeanstalk.com/" + strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            String content = "";
            while ((inputLine = in.readLine()) != null) {
                content += inputLine;
            }
            in.close();

            List<Building> b = deserialize(content.toString());

            objects = b;

            return b;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }



    protected void onPostExecute(List<Building> objLst) {
        try {
            ListView list = loadView;//contextView.findViewById(R.id.search_list);
            String[] ListElements = new String[]{};
           // ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
            final List<String> ListElementsArrayList = new ArrayList<String>(Arrays.asList(new String[]{}));

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (contextView.getContext(), android.R.layout.simple_list_item_1, ListElementsArrayList);
            //Creates an adapter to handle the action on the list
//            adapter.clear();
//            ListElementsArrayList.clear();

            //sets the adapter for the list as adapter
            list.setAdapter(adapter);

            for (Building b : objLst) {
                String locType;

                switch (b.info.Location_type) {
                    case "o":
                        locType = "Office";
                        break;
                    case "c":
                        locType = "Classroom";
                        break;
                    case "l":
                        locType = "Lab";
                        break;
                    default:
                        locType = "Other";
                        break;
                }

                String item =
                        "\nBuilding Number : " + b.getBuilding_id() +
                                "\nRoom Number : " + b.getRoom() +
                                "\nDescription : " + b.getDescription() +
                                "\nLocation type : " + locType;

                ListElementsArrayList.add(item);
            }

            adapter.notifyDataSetChanged();


        } catch (Exception ex) {
            String m = "Error";
        }
    }

    private void runOnUiThread(Runnable runnable) {
    }


    public List<Building> deserialize(String jsonStr) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            // Allowing the serialization of static fields
            gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);

            Gson serializer = gsonBuilder.create();

            List p  = new ArrayList<Building>();
            Type lt = new TypeToken<List<Building>>(){}.getType();
            p = new Gson().fromJson(jsonStr, lt);

            return p;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public void RunQuery(String URL) {
        doInBackground();
//            Thread thread = new Thread(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    try
//                    {
//                        java.net.URL url;
//                        // get URL content
//                        url = new URL("http://node-express-env.hvwzjgwfbd.us-east-1.elasticbeanstalk.com/building/004");
//                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                        con.setRequestMethod("GET");
//
//                        // open the stream and put it into BufferedReader
////                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
////                String response = "";
////                while ((inputLine = br.readLine()) != null) {
////                    System.out.println(inputLine);
////                    response += inputLine;
////                    ArrayList<Building> buildingList = start();
////                    printList(buildingList);
////                   // return buildingList;
////                }
//
//                        BufferedReader in = new BufferedReader(
//                                new InputStreamReader(con.getInputStream()));
//                        String inputLine;
//                        StringBuffer content = new StringBuffer();
//                        while ((inputLine = in.readLine()) != null) {
//                            content.append(inputLine);
//                        }
//                        in.close();
//
////                br.close();
//                        // return null;
//
//                    }catch (Exception ex)
//                    {
//                        ex.printStackTrace();
//                    }
//                    // return null;
//                }
//            });
//
//            //thread.setPriority();
//            thread.run();
        }

        static ArrayList<Building> start() {
            ArrayList<Building> buildingList = new ArrayList<>();
            while (i < inputLine.length()) {
                Building toAdd = id();
                if (toAdd != null) {
                    buildingList.add(toAdd);
                    buildingNum++;
                }
            }
            return buildingList;
        }

        static void printList(ArrayList<Building> List) {
            for (int i = 0; i < buildingNum; i++) {
                System.out.println(List.get(i).getBuilding_id());
            }
        }

        static String Parse() {
            for (i = i; i < inputLine.length(); i++) {
                char c = inputLine.charAt(i);
                if (c == '\"') {
                    i++;
                    c = inputLine.charAt(i);
                    String item = "";
                    for (int j = i + 1; j < inputLine.length() && c != '\"'; j++) {
                        item = item + c;
                        c = inputLine.charAt(j);
                        i = j;
                    }
                    i++;
                    return item;
                } else {
                    //System.out.println("Missing: \"");
                }
            }
            return null;
        }

        static Building id() {
            Building building = new Building();
            while (i < inputLine.length()) {
                String s = Parse();
                if(s == null)
                {
                    return null;
                }
                //System.out.print(s);
                if (s.equals("building_id")) {
                    //System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    if (s.equals("S")) {
                        // System.out.print(s);
                        // System.out.print(" : Found");
                        s = Parse();
                        if(s == null)
                        {
                            return null;
                        }
                        building.setBuilding_id(s);
                    }
                } else if (s.equals("info")) {
                    //System.out.print(" : Found");
                } else if (s.equals("Location_type")) {
                    //System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    if (s.equals("S")) {
                        //System.out.print(s);
                        // System.out.print(" : Found");
                        s = Parse();
                        if(s == null)
                        {
                            return null;
                        }
                        building.setLocation_type(s);
                    }
                } else if (s.equals("room#")) {
                    //System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    if (s.equals("S")) {
                        // System.out.print(" : Found");
                        s = Parse();
                        if(s == null)
                        {
                            return null;
                        }
                        building.setRoom(s);
                    }
                } else if (s.equals("Description")) {
                    //System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    if (s.equals("S")) {
                        //System.out.print(" : Found");
                        s = Parse();
                        if(s == null)
                        {
                            return null;
                        }
                        building.setDescription(s);
                    }
                } else if (s.equals("_id")) {
                    //System.out.print(" : Found");
                    s = Parse();
                    if(s == null)
                    {
                        return null;
                    }
                    if (s.equals("S")) {
                        // System.out.print(" : Found");
                        s = Parse();
                        if(s == null)
                        {
                            return null;
                        }
                        building.set_id(s);
                        return building;
                    }
                } else {
                    //System.out.print(" : Not Found");
                }
                //System.out.println("");
            }
            return null;
        }


}


