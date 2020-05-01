//
// !!! Do NOT Change anything in this file
//
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Comparator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class PA02
{
  public static void main(String[] args)
  {
    System.out.println("Welcome to CS310 PA02: Tree Dictionary");
    String filename=""; //JSON file
    String keyword=""; //field that defines keywords
    String name=""; //field that defines the names
    int max_records=1000000;

    if(args.length<=4)
    {
        System.err.println("Usage: java PA02 -name name_tag -keyword keyword_tag -max max_num_records *.json");
        return;
    }

    for(int i=0;i<args.length;i++)
    {
      if(args[i].compareTo("-name")==0) name=args[++i];
      else if(args[i].compareTo("-keyword")==0) keyword=args[++i];
      else if(args[i].compareTo("-max")==0) max_records=Integer.parseInt(args[++i]);
      else filename=args[i];
    }

    if(filename.length()==0){ System.err.println("! Error: json file is not provided"); return;}
    if(name.length()==0){ System.err.println("! Error: name tag is not provided"); return;}
    if(keyword.length()==0){ System.err.println("! Error: keyword tag is not provided"); return;}
    if(max_records<1) max_records=1;

    PA02 pa=new PA02();
    if(pa.build(filename,name,keyword,max_records)==false)
    {
      System.err.println("! Error: Failed to build dictionary.");
      return;
    }
    pa.run();
    return;
  }

  TreeDictionary<String> dic=new TreeDictionary<String>();

  @SuppressWarnings("unchecked")
  public boolean build(String filename, String name, String keyword, int max_records)
  {
    //convert json file to DynamicArray<Record>
    DynamicArray<Record<String>> records = new DynamicArray<Record<String>>();

    try
    {
      JSONParser parser = new JSONParser();
      JSONArray json = (JSONArray) parser.parse(new FileReader(filename));
      
      for (Object o : json)
      {
        JSONObject r = (JSONObject) o;
        JSONArray keywords = (JSONArray) r.get(keyword);
        Record<String> record=new Record<String>();
        record.Name=(String) r.get(name);
        for (Object k : keywords)
        {
          record.Keywords.insert((String)k);
        }//end for K
        records.insert(record);

        if(records.size()>=max_records) break; //too many records
      }//end for o
      System.out.println("Read "+records.size()+" records from "+filename);
    }
    catch(ParseException e) {
      e.printStackTrace();
      return false;
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }
    catch(IOException e) {
      e.printStackTrace();
      return false;
    }

    //ask the tree to build itself
    dic.build(records);
    return true;
  }

  public void run()
  {
    Scanner scan = new Scanner(System.in);
    while(true)
    {
        printOptions();
        String opt = scan.next();
        if(opt.equals("q") || opt.equals("Q")){ System.out.println("Bye~"); return; }
        handleOptions(opt);
    }
  }

  private void printOptions()
  {
      System.out.println("Options:");
      System.out.println("\tf: find records");
      System.out.println("\tp: print the tree");
      System.out.println("\tq: quit");
      System.out.print("> ");
  }

  private void handleOptions(String opt)
  {
    Scanner scan = new Scanner(System.in);

    if(opt.equals("f"))
    {
        System.out.print("> Provide keywords (seprate by ,): ");
        LinkedList<String> keywords = new LinkedList<String>();
        String tmp=scan.nextLine();
        for(String key : tmp.split(";|,"))
        {
          keywords.insert(key.trim());
        }

        DynamicArray<Record<String>> results=dic.find(keywords);
        if(results==null)
        {
          System.out.println("> No records found");
          return;
        }
        System.out.println("> Found "+results.size()+" records");
        for(Record<String> record:results)
        {
          System.out.println("> ... "+record);
        }
    }
    else if(opt.equals("p"))
    {
        System.out.println("> "+dic.toString());
    }
    else
    {
        System.err.println("! Error: Unknown option: "+opt);
    }
  }


}
